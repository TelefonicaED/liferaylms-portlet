package com.liferay.lms.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.security.Key;
import java.sql.SQLException;
import java.text.Format;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.lms.model.Competence;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.UserCompetence;
import com.liferay.lms.service.CompetenceLocalServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.UserCompetenceLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
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
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Servlet implementation class CompetenceCertificateServlet
 */

public class ExportedCourseServlet extends HttpServlet {
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
		uri = uri.substring(uri.indexOf("exports/courses/") + "exports/courses/".length());

		String[] params = uri.split("/");
		long groupId = GetterUtil.getLong(params[1]);
		String name = GetterUtil.getString(params[2]);
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
