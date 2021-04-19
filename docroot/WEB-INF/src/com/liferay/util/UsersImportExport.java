package com.liferay.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Locale;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.lms.threads.ImportCsvThreadMapper;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

public class UsersImportExport {

	public static void importUsersFromCsvExample(ResourceRequest resourceRequest, ResourceResponse resourceResponse){
		
		_log.debug(":: Example import file ::");
		
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String authType = PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_TYPE);
		_log.debug("::authType:: " + authType);
		
		File file = FileUtil.createTempFile("csv");
		byte b[] = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
		
		try(FileOutputStream bw = new FileOutputStream(file);){
			
			bw.write(b);
			bw.write("sep=;\n".getBytes());
			
			try(CSVWriter writer = new CSVWriter(new OutputStreamWriter(bw, StringPool.UTF8), CharPool.SEMICOLON)){
		
				_log.debug("::Init CSVWriter::");
				
				String [] headers = new String[2];
				
				User user = themeDisplay.getUser();
				String userHeader = StringPool.BLANK;
				String userColumn = StringPool.BLANK;
				
				if(authType.equalsIgnoreCase(CompanyConstants.AUTH_TYPE_SN)){
					userHeader = LanguageUtil.get(themeDisplay.getLocale(), "screenname");
					userColumn = user.getScreenName();
				} else if(authType.equalsIgnoreCase(CompanyConstants.AUTH_TYPE_EA)){
					userHeader = LanguageUtil.get(themeDisplay.getLocale(), "email");
					userColumn = user.getEmailAddress();
				} else {
					userHeader = LanguageUtil.get(themeDisplay.getLocale(), "userId");
					userColumn = String.valueOf(user.getUserId());
				}
				
				headers[0] = userHeader;
				headers[1] = LanguageUtil.get(themeDisplay.getLocale(), "courseadmin.import.csv.courseId");
				
				_log.debug("::headers:: " + headers[0] + " - " + headers[1]);
				
				writer.writeNext(headers);
				
				String [] values = new String[headers.length];
				values[0] = userColumn;
				values[1] = String.valueOf(0);
				
				_log.debug("::values:: " + values[0] + " - " + values[1]);
				
				writer.writeNext(values);
				
		        InputStream in = new FileInputStream(file);
		        HttpServletResponse httpRes = PortalUtil.getHttpServletResponse(resourceResponse);
		        HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(resourceRequest);
		        ServletResponseUtil.sendFile(httpReq,httpRes, LanguageUtil.get(themeDisplay.getLocale(), "example") + ".csv", in, ContentTypes.TEXT_CSV_UTF8);
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
	
	public static void importUsersFromCsv(ResourceRequest resourceRequest, ResourceResponse resourceResponse){
		
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String uuid = ParamUtil.getString(resourceRequest, "UUID", null);
		String filePath = ParamUtil.getString(resourceRequest, "file", null);	
		String importType = ParamUtil.getString(resourceRequest, "importType", null);
		
		if(_log.isDebugEnabled()){
			_log.debug("::importFile:: uuid :: " + uuid);
			_log.debug("::importFile:: filePath :: " + filePath);
			_log.debug("::importFile:: importType :: " + importType);
		}
		
		if(Validator.isNotNull(filePath)){
			
			downloadImportCsvFile(filePath, importType, resourceResponse, themeDisplay.getLocale());

		} else {
			
			resourceResponse.setContentType("application/json");
			JSONObject oreturned = JSONFactoryUtil.createJSONObject();
			
			_log.debug("importFile::uuid::"+uuid);
			
			if(Validator.isNotNull(uuid)){
				
				boolean finished = ImportCsvThreadMapper.threadFinished(uuid);
				oreturned.put("finished", finished);
				
				if(finished){
					
					_log.debug("importFile::FINISHED["+uuid+"]");
					oreturned.put("filePath", ImportCsvThreadMapper.getFileUrl(uuid));
					ImportCsvThreadMapper.unlinkThread(uuid);
					uuid = null;
			
				} else {
					
					oreturned.put("progress", ImportCsvThreadMapper.getProgress(uuid));
				}
				
				oreturned.put("UUID", uuid);
				oreturned.put("importType", importType);
			}
			
			try {
				
				PrintWriter out = resourceResponse.getWriter();
				out.print(oreturned.toString());
				out.flush();
				out.close();
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void downloadImportCsvFile(String filePath, String importType, ResourceResponse response, Locale locale){
		
		_log.debug("downloadFile::contentType::text/csv");
		
		File file = new File(filePath);
		int length   = 0;			 
		
		//TODO Cambiar el nombre del archivo dependiendo del tipo de importacion
		String fileName = "result";
		
		response.addProperty(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" +
				LanguageUtil.get(locale, fileName) + ".csv\"");
		
		response.setContentType("text/csv");
		response.setContentLength((int)file.length());

		try(OutputStream out = response.getPortletOutputStream();
				DataInputStream in = new DataInputStream(new FileInputStream(file));){

			byte[] byteBuffer = new byte[4096];

			// reads the file's bytes and writes them to the response stream
			while ((in != null) && ((length = in.read(byteBuffer)) != -1)){
				out.write(byteBuffer,0,length);
			}		

			out.flush();
			out.close();
			in.close();
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	
	private static Log _log = LogFactoryUtil.getLog(UsersImportExport.class);
}
