package com.liferay.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.lms.BaseCourseAdminPortlet;
import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.threads.ReportThreadMapper;
import com.liferay.lms.util.CourseParams;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.tls.lms.util.CourseOrderByCreationDate;
import com.tls.lms.util.CourseOrderByTitle;

public class EditionsImportExport {
	private static Log log = LogFactoryUtil.getLog(EditionsImportExport.class);
	
	public static void generateImportReport(ResourceRequest request,ResourceResponse response) {
		
		log.debug(":::generateImportReport:::" + request.getResourceID());
		 
		String uuid = ParamUtil.getString(request, "uuid", null);
		String fileName = ParamUtil.getString(request, "file", null);	
		
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		
		if(fileName!=null){
			log.debug(":::generateImportReport:::fileName::"+fileName);
			log.debug(":::generateImportReport:::contentType::text/csv");
			
			File file = new File(fileName);
			int length   = 0;			 
			
			String nameFile = "result";
			
			response.addProperty(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + LanguageUtil.get(themeDisplay.getLocale(), nameFile) + ".csv\"");
			
			response.setContentType("text/csv");
			response.setContentLength((int)file.length());

			try{
				OutputStream out = response.getPortletOutputStream();
	
				byte[] byteBuffer = new byte[4096];
				DataInputStream in = new DataInputStream(new FileInputStream(file));
	
				while ((in != null) && ((length = in.read(byteBuffer)) != -1)){
					out.write(byteBuffer,0,length);
				}		
	
				out.flush();
				out.close();
				in.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}else{
			
			response.setContentType("application/json");
			JSONObject oreturned = JSONFactoryUtil.createJSONObject();
			
			log.debug(":::generateImportReport:::uuid::"+uuid);
			if(uuid!=null){
				boolean finished = ReportThreadMapper.threadFinished(uuid);
				oreturned.put("finished", finished);
				log.debug(":::generateImportReport:::not finished");
				if(finished){
					log.debug(":::generateImportReport:::FINISHED["+uuid+"]");
					oreturned.put("filePath", ReportThreadMapper.getThreadFileName(uuid));
					oreturned.put("registered", ReportThreadMapper.getThreadRegistered(uuid));
					oreturned.put("total", ReportThreadMapper.getThreadLines(uuid)-1);
					oreturned.put("result", ReportThreadMapper.getResult(uuid));
					ReportThreadMapper.unlinkThread(uuid);
				}else{
					oreturned.put("progress", ReportThreadMapper.getProgress(uuid));
				}
			}
			try {
				PrintWriter out = response.getWriter();
				out.print(oreturned.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void generateEditionsExampleFile(ResourceRequest request, ResourceResponse response, ThemeDisplay themeDisplay) {
		if(log.isDebugEnabled()) log.debug(" ::generateEditionsExampleFile:: " + request.getResourceID());
		
		
		File file = FileUtil.createTempFile("csv");
		byte b[] = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
		try(
			FileOutputStream bw = new FileOutputStream(file);
		){
			bw.write(b);
			bw.write("sep=;\n".getBytes());
			try(CSVWriter writer = new CSVWriter(new OutputStreamWriter(bw, StringPool.UTF8), CharPool.SEMICOLON)){
				//Cabeceras de ejemplo
				String [] headers = new String[6];
				headers[0] = LanguageUtil.get(themeDisplay.getLocale(), "course-admin.new-edition-name");
				headers[1] = LanguageUtil.get(themeDisplay.getLocale(), "courseadmin.friendly-url");
				headers[2] = LanguageUtil.get(themeDisplay.getLocale(), "course-admin.start-inscription-date");
				headers[3] = LanguageUtil.get(themeDisplay.getLocale(), "course-admin.end-inscription-date");
				headers[4] = LanguageUtil.get(themeDisplay.getLocale(), "start-execution-date");
				headers[5] = LanguageUtil.get(themeDisplay.getLocale(), "end-execution-date");
				writer.writeNext(headers);
				
				//Valores de ejemplo
				String [] values = new String[headers.length];
				values[0] = LanguageUtil.get(themeDisplay.getLocale(), "course-admin.new-edition-name.example");
				values[1] = LanguageUtil.get(themeDisplay.getLocale(), "courseadmin.friendly-url.example");
				values[2] = LanguageUtil.get(themeDisplay.getLocale(), "course-admin.start-inscription-date.example");
				values[3] = LanguageUtil.get(themeDisplay.getLocale(), "course-admin.end-inscription-date.example");
				values[4] = LanguageUtil.get(themeDisplay.getLocale(), "start-execution-date.example");
				values[5] = LanguageUtil.get(themeDisplay.getLocale(), "end-execution-date.example");
				writer.writeNext(values);
				values[0] = LanguageUtil.get(themeDisplay.getLocale(), "course-admin.new-edition-name.example");
				values[1] = LanguageUtil.get(themeDisplay.getLocale(), "courseadmin.friendly-url.example");
				values[2] = LanguageUtil.get(themeDisplay.getLocale(), "course-admin.start-inscription-date.example");
				values[3] = LanguageUtil.get(themeDisplay.getLocale(), "course-admin.end-inscription-date.example");
				values[4] = LanguageUtil.get(themeDisplay.getLocale(), "start-execution-date.example");
				values[5] = LanguageUtil.get(themeDisplay.getLocale(), "end-execution-date.example");
				writer.writeNext(values);
				
		        InputStream in = new FileInputStream(file);
		        HttpServletResponse httpRes = PortalUtil.getHttpServletResponse(response);
		        HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		        ServletResponseUtil.sendFile(httpReq,httpRes, LanguageUtil.get(themeDisplay.getLocale(), "course-admin.editions.import-export.example-file") + ".csv", in, ContentTypes.TEXT_CSV_UTF8);
		        in.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void generateReportEditions(ResourceRequest request, ResourceResponse response, ThemeDisplay themeDisplay) {
		if(log.isDebugEnabled()) log.debug(" ::exportReportEditions:: " + request.getResourceID());
		
		List<Course> listCourses = getCoursesToExport(request, themeDisplay);
		
		if(log.isDebugEnabled())
			log.debug(" ::exportReportEditions:: listCourses OK :: " + Validator.isNotNull(listCourses));
		
		File file = FileUtil.createTempFile("csv");
		byte b[] = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
		try(
			FileOutputStream bw = new FileOutputStream(file);
		){
			bw.write(b);
			bw.write("sep=;\n".getBytes());
			try(CSVWriter writer = new CSVWriter(new OutputStreamWriter(bw, StringPool.UTF8), CharPool.SEMICOLON)){
				//Cabecera
				String [] headers = new String[6];
				headers[0] = LanguageUtil.get(themeDisplay.getLocale(), "course-admin.new-edition-name");
				headers[1] = LanguageUtil.get(themeDisplay.getLocale(), "courseadmin.friendly-url");
				headers[2] = LanguageUtil.get(themeDisplay.getLocale(), "course-admin.start-inscription-date");
				headers[3] = LanguageUtil.get(themeDisplay.getLocale(), "course-admin.end-inscription-date");
				headers[4] = LanguageUtil.get(themeDisplay.getLocale(), "start-execution-date");
				headers[5] = LanguageUtil.get(themeDisplay.getLocale(), "end-execution-date");
				writer.writeNext(headers);
				
				if(Validator.isNotNull(listCourses) && !listCourses.isEmpty()){
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
					//Valores
					String [] values = new String[headers.length];
					for(Course course:listCourses){
						values = new String[headers.length];
						values[0] = course.getTitle(themeDisplay.getLocale());
						values[1] = course.getFriendlyURL();
						values[2] = df.format(course.getStartDate());
						values[3] = df.format(course.getEndDate());
						values[4] = df.format(course.getExecutionStartDate());
						values[5] = df.format(course.getExecutionEndDate());
						writer.writeNext(values);
					}
				}
				
		        
		        response.setContentType("application/csv");
		        response.setContentLength((int)file.length());
		        
		        response.addProperty(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + LanguageUtil.get(themeDisplay.getLocale(), "course-admin.editions.import-export.csv.file-name")+ ".csv");
		        
		        OutputStream out = response.getPortletOutputStream();
				
				byte[] byteBuffer = new byte[4096];
		        DataInputStream in = new DataInputStream(new FileInputStream(file));
		        int length   = 0;		
		        // reads the file's bytes and writes them to the response stream
		        while ((in != null) && ((length = in.read(byteBuffer)) != -1)){
		        	out.write(byteBuffer,0,length);
		        }		
				
				out.flush();
				out.close();
				in.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static List<Course> getCoursesToExport(ResourceRequest request, ThemeDisplay themeDisplay){
		long courseId = ParamUtil.getLong(request, "courseId", 0);
		String freetext = ParamUtil.getString(request, "freetext", StringPool.BLANK);
		int state = ParamUtil.getInteger(request, "state" ,WorkflowConstants.STATUS_APPROVED);
		long columnId = ParamUtil.getLong(request, "columnId");
		String expandoValue = ParamUtil.getString(request, "expandoValue", "");
		if(log.isDebugEnabled()){
			log.debug(" ::exportReportEditions:: courseId :: " + courseId);
			log.debug(" ::exportReportEditions:: freetext :: " + freetext);
			log.debug(" ::exportReportEditions:: state :: " + state);
			log.debug(" ::exportReportEditions:: columnId :: " + columnId);
			log.debug(" ::exportReportEditions:: expandoValue :: " + expandoValue);
		}
		
		ArrayList<Long> assetCategoryIds = new ArrayList<Long>();
		Enumeration<String> pnames =request.getParameterNames();
		ArrayList<String> tparams = new ArrayList<String>();
		while(pnames.hasMoreElements()){
			String name = pnames.nextElement();
			if(name.length()>16&&name.substring(0,16).equals("assetCategoryIds")){
				tparams.add(name);
				String value = request.getParameter(name);
				if(Validator.isNotNull(value) && !value.equals(StringPool.BLANK)){
					String[] values = value.split(",");
					for(String valuet : values){
						try{
							assetCategoryIds.add(Long.parseLong(valuet));
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}
		}
		long[] categoryIds = ArrayUtil.toArray(assetCategoryIds.toArray(new Long[assetCategoryIds.size()]));
		
		String[] tagsSel = null;
		long[] tagsSelIds = null;
		try {
			ServiceContext sc = ServiceContextFactory.getInstance(request);
			tagsSel = sc.getAssetTagNames();

			if(tagsSel != null){
				long[] groups = new long[]{themeDisplay.getScopeGroupId()};
				tagsSelIds = AssetTagLocalServiceUtil.getTagIds(groups, tagsSel);
			}
		} catch (PortalException | SystemException e) {
			e.printStackTrace();
		} 
		
		String[] templates = BaseCourseAdminPortlet.getCourseTemplates(request.getPreferences(), themeDisplay.getCompanyId());
		
		boolean isAdmin = Boolean.FALSE;
		try {
			isAdmin = RoleLocalServiceUtil.hasUserRole(themeDisplay.getUserId(), RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.ADMINISTRATOR).getRoleId())
					|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, ActionKeys.UPDATE)
					|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, ActionKeys.DELETE)
					|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, ActionKeys.PERMISSIONS)
					|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, "PUBLISH")
					|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, "COURSEEDITOR")
					|| themeDisplay.getPermissionChecker().hasPermission(themeDisplay.getScopeGroupId(), Course.class.getName(), 0, "ASSIGN_MEMBERS");
		} catch (SystemException | PortalException e) {
			e.printStackTrace();
		} 
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if(categoryIds != null && categoryIds.length > 0){
			params.put(CourseParams.PARAM_AND_CATEGORIES, categoryIds);
		}
		if(tagsSelIds != null && tagsSelIds.length > 0){
			params.put(CourseParams.PARAM_TAGS, tagsSelIds);
		}
		if(templates != null && templates.length > 0){
			params.put(CourseParams.PARAM_TEMPLATES, templates);
		}
		if(columnId > 0){
			Object[] expandoValues = {columnId, expandoValue};
			params.put(CourseParams.PARAM_CUSTOM_ATTRIBUTE, expandoValues);
		}
		if(!isAdmin){
			params.put(CourseParams.PARAM_PERMISSIONS_ADMIN, themeDisplay.getUserId());
		}
		
		String orderByCol = ParamUtil.getString(request, "orderByCol");
		String orderByType = ParamUtil.getString(request, "orderByType");
		
		if (Validator.isNull(orderByCol) ||
			Validator.isNull(orderByType)){
			orderByCol = "title";
			orderByType = "asc";
		}
		
		OrderByComparator obc = null;
		if(Validator.isNotNull(orderByCol) && orderByCol.equals("title")){
			obc = new CourseOrderByTitle(themeDisplay, orderByType.equals("asc"));
		}else if(Validator.isNotNull(orderByCol) && orderByCol.equals("createDate")){
			obc = new CourseOrderByCreationDate(orderByType.equals("asc"));
		}
		
		//Para las ediciones groupId = 0
		return CourseLocalServiceUtil.searchCourses(themeDisplay.getCompanyId(), freetext, themeDisplay.getLanguageId(), state, courseId, 0, params, 
				-1,-1, obc);
	}
	
}
