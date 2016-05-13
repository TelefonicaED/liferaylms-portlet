package com.liferay.lms.servlet;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Key;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.lms.model.Course;

import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.Encryptor;

/**
 * Servlet implementation class CompetenceCertificateServlet
 */

public class ExportedCourseServlet extends HttpServlet {
	private static Log log = LogFactoryUtil.getLog(ExportedCourseServlet.class);
	
	private static final long serialVersionUID = 1L;
	private String hexStringToStringByAscii(String hexString) {
		byte[] bytes = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length() / 2; i++) {
			String oneHexa = hexString.substring(i * 2, i * 2 + 2);
			bytes[i] = Byte.parseByte(oneHexa, 16);
		}
		try {
			return new String(bytes, "ASCII");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportedCourseServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			User user = PortalUtil.getUser(request);
		
		
		if (user == null) {
			String userId = null;
			String companyId = null;
			Cookie[] cookies = ((HttpServletRequest) request).getCookies();
			if (Validator.isNotNull(cookies)) {
				for (Cookie c : cookies) {
					if ("COMPANY_ID".equals(c.getName())) {
						companyId = c.getValue();
					} else if ("ID".equals(c.getName())) {
						userId = hexStringToStringByAscii(c.getValue());
					}
				}
			}

			if (userId != null && companyId != null) {
				try {
					Company company = CompanyLocalServiceUtil
							.getCompany(Long.parseLong(companyId));
					Key key = company.getKeyObj();

					String userIdPlain = Encryptor.decrypt(key, userId);

					user = UserLocalServiceUtil.getUser(Long
							.valueOf(userIdPlain));

					// Now you can set the liferayUser into a thread local
					// for later use or
					// something like that.

				} catch (Exception pException) {
					throw new RuntimeException(pException);
				}
			}
		}
		PermissionChecker permissionChecker = null;
		try {
			permissionChecker = PermissionCheckerFactoryUtil.create(user);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		String uri = URLDecoder.decode(request.getRequestURI(), "UTF-8");
		log.debug("uri:"+uri);
		uri = uri.substring(uri.indexOf("exports/courses/") + "exports/courses/".length());		
		String[] params = uri.split("/");
		long groupId = GetterUtil.getLong(params[1]);
		String name = GetterUtil.getString(params[2]);
		
		log.debug("groupId:"+groupId);
		log.debug("name:"+name);
		
		Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(groupId);
		
		if (course == null || !permissionChecker.hasPermission(groupId, Course.class.getName(), course.getPrimaryKey(), ActionKeys.UPDATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		
		if (Validator.isNull(name)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		File lar = new File(PropsUtil.get("liferay.home")+"/data/lms_exports/courses/"+user.getCompanyId()+"/"+groupId+"/"+name);
		
		if (lar == null || !lar.isFile()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}

		OutputStream os = response.getOutputStream();
		InputStream is = new FileInputStream(lar);
		response.addHeader(HttpHeaders.CONTENT_TYPE, "application/zip");
		response.setContentType("application/zip");
		response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(lar.length()));
		
		byte[] buffer = new byte[1024];
		int i = 0;

		while ((i = is.read(buffer)) > 0) {
			if (i == 1024) {
				os.write(buffer);
			} else {
				os.write(buffer, 0, i);
			}
		}

		os.flush();
		os.close();
		is.close();
			
		} catch(SystemException e) {
			response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} catch (PortalException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}


	}
	
}
