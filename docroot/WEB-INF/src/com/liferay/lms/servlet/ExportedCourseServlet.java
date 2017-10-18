package com.liferay.lms.servlet;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * Servlet implementation class CompetenceCertificateServlet
 */

public class ExportedCourseServlet extends HttpServlet {
	private static Log log = LogFactoryUtil.getLog(ExportedCourseServlet.class);
	
	private static final long serialVersionUID = 1L;
 
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
			
		String uri = URLDecoder.decode(request.getRequestURI(), "UTF-8");
		log.debug("uri:"+uri);
		uri = uri.substring(uri.indexOf("exports/courses/") + "exports/courses/".length());		
		String[] params = uri.split("/");
		long companyId = GetterUtil.getLong(params[0]);
		long groupId = GetterUtil.getLong(params[1]);
		String name = GetterUtil.getString(params[2]);
		
		log.debug("groupId:"+groupId);
		log.debug("name:"+name);
				
		if (Validator.isNull(name)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		File lar = new File(PropsUtil.get("liferay.home")+"/data/lms_exports/courses/"+companyId+"/"+groupId+"/"+name);
		
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


	}
	
}
