package com.liferay.lms.threads;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.lms.util.LmsPrefsPropsValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.announcements.EntryDisplayDateException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;



public class ImportUsersCourseThread extends Thread {
	private static Log log = LogFactoryUtil.getLog(ImportUsersCourseThread.class);
	private boolean isFinished = false;	
	private int progress=0;
	private static ThemeDisplay themeDisplay;
	private String idHilo;
	int totalLines=0;
	static int usersInscripted=0;
	static int line;	
	static List<String> errors;
	static List<String> warnings;
	InputStream csvFile;
	ServiceContext serviceContext;
	String fileName;
	File file;
	String fileReport;
	static PortletConfig portletConfig;
	private ActionRequest request;
	long courseId = 0;
	long roleId = 0;	
		
	static String sc_uuid = "";


	public ImportUsersCourseThread(ThemeDisplay themeDisplay,String idHilo, PortletConfig portletConfig,
			String fileName, File file, ServiceContext serviceContext, InputStream csvFile, 
			PortletPreferences portletPreferences, ActionRequest request) throws PortalException, SystemException{
		this.idHilo = idHilo;
		ImportUsersCourseThread.themeDisplay = themeDisplay;		
		this.portletConfig = portletConfig;
		this.fileName =  fileName;
		this.file = file;
		this.serviceContext = serviceContext;
		this.csvFile = csvFile;
		this.request = request;
		//this.serviceContext = serviceContext;
		
		long courseId = ParamUtil.getLong(request, "courseId", 0);
		long roleId = ParamUtil.getLong(request, "roleId", 0);
		
		this.courseId = courseId;
		this.roleId = roleId;
		
		sc_uuid = this.idHilo;
		errors = new ArrayList<String>();
		warnings = new ArrayList<String> ();
	}

	public void run() {
		// Presenta en pantalla informacion sobre este hilo en particular
		try {
			progress = 0;
			totalLines=0;
			usersInscripted=0;
			procesarFichero();
			fileReport = createReport(errors, warnings, totalLines, usersInscripted, new Date(), new Date(), themeDisplay.getCompanyId(), file);
			log.debug("fileReport: " + fileReport);
			isFinished = true;
			progress = 100;
			
		}catch (Exception e) {
			e.printStackTrace();
			ImportUsersCourseThreadMapper.unlinkThread(idHilo);
		} 
	}


	private void procesarFichero(){
		log.debug("procesarFichero 1 - fileName: " + fileName);

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);
		long courseId = ParamUtil.getLong(uploadRequest, "courseId", 0);
		long roleId = ParamUtil.getLong(uploadRequest, "roleId", 0);
		log.debug("procesarFichero 1 - courseId: " + courseId);
		log.debug("procesarFichero 1 - roleId: " + roleId);
		
		Course course = null;
		try {
			course = CourseLocalServiceUtil.getCourse(courseId);
		} catch (PortalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<Long> users = new ArrayList<Long>();

		if(fileName==null || StringPool.BLANK.equals(fileName)){
			//SessionErrors.add(request, "courseadmin.importuserrole.csv.fileRequired");
			errors.add(LanguageUtil.get(portletConfig, themeDisplay.getLocale(),"courseadmin.importuserrole.csv.fileRequired"));
		}
		//Comprobar que el size del fichero no sea mayor de 2mb.
		else if(file.length()> 2 * 1024 * 1024){
			errors.add(LanguageUtil.get(portletConfig, themeDisplay.getLocale(),"courseadmin.importuserrole.csv.badFormat.size"));
		}
		else{ 
			if (!fileName.endsWith(".csv")) { 
				errors.add(LanguageUtil.get(portletConfig, themeDisplay.getLocale(),"courseadmin.importuserrole.csv.badFormat"));
			}
			else {
				CSVReader reader = null; 
				try {					
					reader = new CSVReader(new InputStreamReader(new FileInputStream(file), StringPool.UTF8), CharPool.SEMICOLON);
					
					String[] currLine;
					int line = 0;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
					Calendar cal = Calendar.getInstance();
					Date allowStartDate;
					Date allowFinishDate;
					
					//Comprobamos el tipo de importaci�n
					String authType = PropsUtil.get(PropsKeys.COMPANY_SECURITY_AUTH_TYPE);
					try {
						Company company = CompanyLocalServiceUtil.getCompany(themeDisplay.getCompanyId());
						if (Validator.isNotNull(company)) {
							authType = company.getAuthType();
						}
					} catch (PortalException e) {
						log.error("Se ha producido un error al obtener el tipo de login de usuario (authType) para companyId=" + themeDisplay.getCompanyId(), e);
					} catch (SystemException e) {
						log.error("Se ha producido un error al obtener el tipo de login de usuario (authType) para companyId=" + themeDisplay.getCompanyId(), e);
					}
					
					int posDate = !LmsPrefsPropsValues.getUsersExtendedData(themeDisplay.getCompanyId()) || PortalPermissionUtil.contains(
			        		themeDisplay.getPermissionChecker(), LmsConstant.ACTION_VIEW_USER_EXTENDED) ? 3:1;
					
					
					while ((currLine = reader.readNext()) != null) {
						log.debug("dentro while");
						totalLines++;
						if(currLine.length > 0 && (line++ > 0)) {
							//Comprobamos errores
							if(Validator.isNull(currLine[0])){
								if (CompanyConstants.AUTH_TYPE_SN.equalsIgnoreCase(authType)) {
									warnings.add(LanguageUtil.format(
											portletConfig,
											themeDisplay.getLocale(),
											"courseadmin.importuserrole.csvError.user-name-bad-format", //Importaci�n por screenName
											new Object[] { line }, false));
								}else if(CompanyConstants.AUTH_TYPE_EA.equalsIgnoreCase(authType)){
									warnings.add(LanguageUtil.format(
											portletConfig,
											themeDisplay.getLocale(),
											"courseadmin.importuserrole.csvError.email-address-bad-format", //Importaci�n por screenName
											new Object[] { line }, false));
								}else{
									warnings.add(LanguageUtil.format(
											portletConfig,
											themeDisplay.getLocale(),
											"courseadmin.importuserrole.csvError.user-id-bad-format", //Importaci�n por screenName
											new Object[] { line }, false));
								}
							}else{
								
								String userIdStr = currLine[0];
								
								if (!userIdStr.equals(StringPool.BLANK)){
									
									try {
										User user = null;
										if(log.isDebugEnabled())log.debug("Identificador:: " + userIdStr);
										if (CompanyConstants.AUTH_TYPE_SN.equalsIgnoreCase(authType)) {
											user = UserLocalServiceUtil.getUserByScreenName(themeDisplay.getCompanyId(), userIdStr.trim());
										}else if(CompanyConstants.AUTH_TYPE_EA.equalsIgnoreCase(authType)){
											user = UserLocalServiceUtil.getUserByEmailAddress(themeDisplay.getCompanyId(), userIdStr.trim());
										}else{
											user = UserLocalServiceUtil.getUser(Long.parseLong(userIdStr.trim()));
										}
										
										if(user != null){
											if(log.isDebugEnabled())log.debug("User Name:: " + user.getFullName() );
											UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { user.getUserId() }, course.getGroupCreatedId(), roleId);
											log.debug("despues de addUserGroupRoles");
											
											if(!GroupLocalServiceUtil.hasUserGroup(user.getUserId(), course.getGroupCreatedId())){
												log.debug("no esta inscrito en el curso, se inscribe");
												GroupLocalServiceUtil.addUserGroups(user.getUserId(), new long[] { course.getGroupCreatedId() });
												usersInscripted++;
												log.debug("despues de addUserGroups");
											}else{
												log.debug("ya esta inscrito en el curso");
												warnings.add(LanguageUtil.format(portletConfig,themeDisplay.getLocale(),"courseadmin.importuserrole.user-is-registered",
														new Object[] { line, userIdStr.trim() }, false));
											}
											
		
											users.add(user.getUserId());
											
											String allowStartDateStr = "";
											String allowEndDateStr = "";
											try{
												allowStartDateStr = currLine[posDate];
												allowEndDateStr = currLine[posDate+1];
											}catch (ArrayIndexOutOfBoundsException ex){
												log.debug("ArrayIndexOutOfBoundsException");
												allowStartDateStr = "";
												allowEndDateStr = "";												
											}
											
											if(allowStartDateStr.trim().length() >0){
												try{
													cal.setTime(sdf.parse(allowStartDateStr));
												}catch(ParseException e){
													try{
														cal.setTime(sdf2.parse(allowStartDateStr));
													}catch(ParseException e1){
														warnings.add(LanguageUtil.format(portletConfig,themeDisplay.getLocale(),"courseadmin.importuserrole.date-bad-format",
															new Object[] { line, userIdStr.trim() }, false));
													}
												}
												int startMonth = cal.get(Calendar.MONTH);
												int startYear = cal.get(Calendar.YEAR);
												int startDay = cal.get(Calendar.DATE);
												allowStartDate = PortalUtil.getDate(startMonth, startDay, startYear,0, 0, user.getTimeZone(),new EntryDisplayDateException());
											}else{
												allowStartDate=null;
											}
											if(allowEndDateStr.trim().length() >0){
												try{
													cal.setTime(sdf.parse(allowEndDateStr));
												}catch(ParseException e){													
													try{
														cal.setTime(sdf2.parse(allowEndDateStr));
													}catch(ParseException e1){
														warnings.add(LanguageUtil.format(portletConfig,themeDisplay.getLocale(),"courseadmin.importuserrole.date-bad-format",
															new Object[] { line, userIdStr.trim() }, false));
													}
												}
												int stopMonth = cal.get(Calendar.MONTH);
												int stopYear = cal.get(Calendar.YEAR);
												int stopDay = cal.get(Calendar.DATE);
												 allowFinishDate = PortalUtil.getDate(stopMonth, stopDay, stopYear,0, 0, user.getTimeZone(),new EntryDisplayDateException());
											
											}else{
												allowFinishDate=null;
											}
											
											if(Validator.isNotNull(allowStartDate) && Validator.isNotNull(allowFinishDate) ){												
												
												CourseResult courseResult=CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(courseId, user.getUserId());
												if(courseResult==null){
													courseResult = CourseResultLocalServiceUtil.addCourseResult(themeDisplay.getUserId(), courseId, user.getUserId(), allowStartDate, allowFinishDate);
												}else{
													courseResult.setAllowStartDate(allowStartDate);
													courseResult.setAllowFinishDate(allowFinishDate);

													CourseResultLocalServiceUtil.updateCourseResult(courseResult);
												}
																								
											}
											
										}else{
											log.debug("Usuario no encontrado");
											warnings.add(LanguageUtil.format(portletConfig,themeDisplay.getLocale(),"courseadmin.importuserrole.csvError.user-id-not-found",
													new Object[] { line, userIdStr.trim() }, false));
										}
									} catch (NumberFormatException e) {
										log.debug("NumberFormatException");
										warnings.add(LanguageUtil.format(portletConfig,themeDisplay.getLocale(),"courseadmin.importuserrole.csvError.user-id-bad-format", new Object[] { line }, false));
									} catch (PortalException e) {
										log.debug("PortalException");
										warnings.add(LanguageUtil.format(portletConfig,themeDisplay.getLocale(),"courseadmin.importuserrole.csvError.user-id-not-found",
												new Object[] { line, userIdStr.trim() }, false));
									} catch (Exception e){
										log.debug("Exception");
										e.printStackTrace();
										warnings.add(LanguageUtil.get(portletConfig, themeDisplay.getLocale(),"courseadmin.importuserrole.csvError"));
									}
								}
							}
						}
					}

				} catch (FileNotFoundException e) {
					log.debug("FileNotFoundException");
					errors.add(LanguageUtil.get(portletConfig, themeDisplay.getLocale(),"courseadmin.importuserrole.csvError.empty-file"));
				}catch(Exception e){
					log.debug("Exception");
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				log.debug("totalLines: " + totalLines);
				log.debug("errors.size(): " + errors.size());
				log.debug("warnings.size(): " + warnings.size());
				log.debug("usersInscripted: " + usersInscripted);

			}	
		}		
	}
	
	

	public static CSVWriter initCsv(FileOutputStream outputStream)
			throws IOException, UnsupportedEncodingException {
		// Necesario para crear el fichero csv.
		byte b[] = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };

		outputStream.write(b);

		CSVWriter writer = new CSVWriter(new OutputStreamWriter(
				outputStream, StringPool.UTF8), CharPool.SEMICOLON);
		return writer;
	}
	
	public static String getAlfanumAleatorio (int longitud) {
		String cadenaAleatoria = "";
		long milis = new java.util.GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);
		int i = 0;
		while ( i < longitud){
			char c = (char)r.nextInt(255);
			if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ) {
				cadenaAleatoria += c;
				i ++;
			}
		}
		return cadenaAleatoria;
	}
	
	private static String getFileCopyName(String reportPath, String fileName) {

		//reportPath > log_importUsers_2015_09_20_125525_ZKC.txt
		String subs = (Validator.isNotNull(reportPath) && reportPath.length()>24 ) 
				? reportPath.substring(reportPath.length() - 25, reportPath.length() - 4)
				: "";
		return subs + StringPool.UNDERLINE + fileName;
	}
	
	
	public final static String BASE_DIR=PropsUtil.get("liferay.home")
			+ System.getProperty("file.separator") +"data" 
			+ System.getProperty("file.separator") +"importUsersCourse";
	
	public final static String CSV_LOCAL_DOWNLOAD_PATH = BASE_DIR + System.getProperty("file.separator") + "csv" + System.getProperty("file.separator");
	
	
	public static String createReport (List<String> errors, List<String> warnings, int totalLines, int usersInscripted, //int countDeactivated, 
			Date initDate, Date endDate, long companyId //, Locale locale,
			,File file
			) throws Exception{
		File report = FileUtil.createTempFile("csv");
		FileOutputStream bw = null;

		try {
			bw = new FileOutputStream(report);
		} 
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		CSVWriter writer = initCsv(bw);		
		writer.writeNext(new String[]{"Lineas procesadas: ",String.valueOf(totalLines)});
		writer.writeNext(new String[]{"Usuarios inscritos: ",String.valueOf(usersInscripted)});
		writer.writeNext(new String[]{"Errores: ",String.valueOf(errors.size())});
		writer.writeNext(new String[]{});
		
		log.debug("errors.size(): " + errors.size());
		
		for(String error: errors){
			String [] lineError = {"Error",error};
			writer.writeNext(lineError);
		}

		log.debug("warnings.size(): " + warnings.size());
		for(String warning: warnings){
			String [] lineWarning = {"Warning",warning};
			writer.writeNext(lineWarning);
		}

		try {
			bw.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
		String  pathLogDir = BASE_DIR + System.getProperty("file.separator") +"logs";
		File dir = new File(pathLogDir);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String fetchCreate = new SimpleDateFormat("yyyy_MM_dd_hhmmss").format(initDate);
		String companyName = "";
		try{
			companyName = CompanyLocalServiceUtil.fetchCompany(companyId).getName()+StringPool.UNDERLINE;
		}catch(Exception e){
			log.error(e.getMessage());
			log.debug(e);
		}
		
		String rutaFile = dir.getAbsolutePath()
      		  + System.getProperty("file.separator")
      		  + "log_importUsersCourse_"
      		  + companyName
      		  + fetchCreate
      		  + StringPool.UNDERLINE + getAlfanumAleatorio(3)
      		  + ".txt";
		
		
		File logBkp = new File(rutaFile);
		FileUtil.copyFile(report, logBkp);
		
		// Guardar copia de seguridad del csv
		File bkp = null;
		if(file != null) {
			File directorioServerLocalBk = new File(CSV_LOCAL_DOWNLOAD_PATH+"bkp"+System.getProperty("file.separator"));
			if(!directorioServerLocalBk.exists())
				directorioServerLocalBk.mkdirs();
			String pathSalida = directorioServerLocalBk.getAbsolutePath() + System.getProperty("file.separator") + getFileCopyName(rutaFile,file.getName());
			bkp =  new File (pathSalida);
			FileUtil.copyFile(file, bkp);
		}

		log.debug("fichero informe: " + report.getPath());
		
		return report.getPath();

	}	
	
	
	public static String getCellValue(Cell cell){
		String value = "";
		if(Validator.isNotNull(cell)){
			if( cell.getCellType()==Cell.CELL_TYPE_NUMERIC ){
				if( HSSFDateUtil.isCellDateFormatted(cell) ){
					value = DateUtil.getDate(cell.getDateCellValue(), "dd/MM/yyyy", Locale.getDefault());
				} else{
					cell.setCellType(Cell.CELL_TYPE_STRING);
					value = cell.getStringCellValue();
				}
			} else if( cell.getCellType()==Cell.CELL_TYPE_STRING ){
				value = cell.getStringCellValue();
			}
		}
		return value;
	}
	
	private List<File> downloadFiles(List<File> files, Row row) throws PortalException, SystemException{
		String url;File file;
		for(int i = 22;i<=28;i++){
			file = null;			
			url = getCellValue(row.getCell(i));

			if(Validator.isNotNull(url)){
				file = downloadfile(url);
				if(Validator.isNotNull(file)){
					files.add(file);
				}
			}
		}

		return files;
	}
	private File downloadfile(String fileURL) throws PortalException, SystemException{
		log.debug("** DOWNLOAD: " + fileURL);
		String urlPortal=  PortalUtil.getPortalURL(themeDisplay);
		if(fileURL.contains(urlPortal)){
			fileURL = fileURL.substring(0,fileURL.lastIndexOf('/'));
			String extension = fileURL.substring(fileURL.lastIndexOf('.')+1,fileURL.length());
			String nombreArchivo = fileURL.substring(fileURL.lastIndexOf('/')+1,fileURL.length());
			log.debug("extension: " + extension);
			File file = FileUtil.createTempFile(extension);
			
			try (InputStream in = new URL(fileURL).openStream();
					OutputStream outputStream = new FileOutputStream(file)) {
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = in.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
			} catch (IOException e) {
				log.error(e.getMessage());
				file = null;
			}	
			String mimetype = 	MimeTypesUtil.getContentType(file);				
			List<DLFileEntry> lista = DLFileEntryLocalServiceUtil.getFileEntriesByMimeType(mimetype);
			for(DLFileEntry arch : lista){
				if(arch.getTitle().equals(nombreArchivo)){
					DLFileEntryLocalServiceUtil.deleteDLFileEntry(arch);	
					break;
				}else{
					log.debug("No se ha encontarado el archivo para borrarlo de la DL ");
				}
			}
			
			return file;
		}else{
		String extension = fileURL.substring(fileURL.lastIndexOf('.')+1,fileURL.length());
		log.debug("extension: " + extension);
		File file = FileUtil.createTempFile(extension);
		try (InputStream in = new URL(fileURL).openStream();
				OutputStream outputStream = new FileOutputStream(file)) {
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = in.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			file = null;
		}
		return file;
		}
		
	}
	
	public List<String> getErrors() {
		return errors;
	}

	public List<String> getWarnings() {
		return warnings;
	}

	public boolean isFinished(){
		return this.isFinished;
	}

	public int getProgress(){
		return this.progress;
	}

	public String getFileName(){
		return this.fileName;
	}

	public String getIdHilo(){
		return this.idHilo;
	}

	public void setProgress(int progress){
		this.progress = progress;
	}

	public int getTotalLines() {
		return totalLines;
	}	
	
	public int getUsersInscripted() {
		return usersInscripted;
	}
	
	public String getFileReport(){
		return this.fileReport;
	}
	
}
