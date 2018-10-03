package com.liferay.lms.threads;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import au.com.bytecode.opencsv.CSVReader;

import com.liferay.lms.model.AsynchronousProcessAudit;
import com.liferay.lms.model.Course;
import com.liferay.lms.service.AsynchronousProcessAuditLocalServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;

public class ImportEditionsThread extends ReportThread{
	
	private static Log log = LogFactoryUtil.getLog(ImportEditionsThread.class);
	
	protected ServiceContext serviceContext;
	protected long editionLayoutId;
	protected long parentCourseId;
	
	public ImportEditionsThread(ThemeDisplay themeDisplay, ServiceContext serviceContext, String idThread, File importFile,
			long editionLayoutId, long parentCourseId) throws PortalException, SystemException  {
		super(themeDisplay, idThread, importFile);
		this.serviceContext = serviceContext;
		this.editionLayoutId = editionLayoutId;
		this.parentCourseId = parentCourseId;
		if(log.isDebugEnabled()){
			log.debug(" :: ImportEditionsThread :: editionLayoutId :: " + editionLayoutId);
			log.debug(" :: ImportEditionsThread :: parentCourseId :: " + parentCourseId);
		}
	}

	@Override
	protected void generateCSV() throws IOException {
		File file = FileUtil.createTempFile("csv");
		InputStream csvFile = new FileInputStream(importFile);
		
		try (
			InputStreamReader fstream = new InputStreamReader(csvFile, StringPool.UTF8);
			CSVReader reader = new CSVReader(fstream,CharPool.SEMICOLON);
			LineNumberReader lineReader = new LineNumberReader (new FileReader (importFile));
			BufferedWriter bw  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
			){
			
			lineReader.skip(Long.MAX_VALUE);
			totalLines = lineReader.getLineNumber();
			lineReader.close();
			
			if(log.isDebugEnabled())
				log.debug(":: countRows :: " + totalLines);
			if(totalLines<1){
				bw.append("ERROR: " + LanguageUtil.get(themeDisplay.getLocale(), "course-admin.editions.import-export.error-no-lines") + "\n");
				if(log.isDebugEnabled())
					log.debug(" :: ERROR:: NO LINES");
			} else {
				Course course = CourseLocalServiceUtil.getCourse(parentCourseId);
				if(Validator.isNull(course)){
					bw.append("ERROR: " + LanguageUtil.get(themeDisplay.getLocale(), "course-admin.editions.import-export.error-no-parent-course") + "\n");
					if(log.isDebugEnabled())
						log.debug(" :: importEdition :: ERROR PARENT COURSE NOT FOUND");
				} else {
					bw.append('\ufeff');
					
					String[] strLine = null;
					line = 1;
					boolean isFirstLine = Boolean.TRUE;
					boolean isCorrectHeader = Boolean.TRUE;
					Group group = null;
					String friendlyURL = StringPool.BLANK;
		
					while ((strLine = reader.readNext()) != null && isCorrectHeader) {
						if (!isFirstLine) {
							if(checkLength(strLine, bw)) importEdition(strLine, bw, group, course, friendlyURL);
							else {
								bw.append("ERROR: " + LanguageUtil.get(themeDisplay.getLocale(), "course-admin.editions.import-export.error.incorrect-line-length") + 
										"[" + LanguageUtil.get(themeDisplay.getLocale(), "line") + "=" + line +"] \n");
								if(log.isDebugEnabled())
									log.debug(" :: importEdition :: ERROR INCORRECT LINE LENGTH (line " + line + ")");
							}
							progress = ((line-1)*100)/totalLines;
						}else{
							if(!strLine[0].startsWith("sep=")){
								isFirstLine = Boolean.FALSE;
								isCorrectHeader = checkLength(strLine, bw);
								if(!isCorrectHeader){
								bw.append("ERROR: " + LanguageUtil.get(themeDisplay.getLocale(), "course-admin.editions.import-export.error.header-incorrect") + "\n");
									if(log.isDebugEnabled())
										log.debug(" :: importEdition :: ERROR INCORRECT HEADER LENGTH ");
								}
								
							}else{
								totalLines = totalLines -1;
								log.info("--SEPARATOR "+strLine[0]);
							}
						}
						line++;
					}
				}
			}
			int numEditions = totalLines-1;
			bw.append(LanguageUtil.get(themeDisplay.getLocale(), "course-admin.editions.import-export.imported-editions") + ": " + countRegistered + " \n");
			bw.append(LanguageUtil.get(themeDisplay.getLocale(), "course-admin.editions.import-export.total-editions") + ": " + numEditions  + "\n");
			bw.close();
			fileName = file.getAbsolutePath();
		}catch (Exception e) {
			e.printStackTrace();
			ReportThreadMapper.unlinkThread(getIdThread());
		}
	}
	
	private void importEdition(String[] dataEdition, BufferedWriter bw, Group group, Course course,  String friendlyURL) throws IOException {
		if(log.isDebugEnabled()) {
			log.debug(" :: importEdition :: dataEdition[0] :: editionName  :: " + dataEdition[0]);
			log.debug(" :: importEdition :: dataEdition[1] :: editionFriendlyURL :: " + dataEdition[1]);
			log.debug(" :: importEdition :: dataEdition[1] :: startInscriptionDate :: " + dataEdition[2]);
			log.debug(" :: importEdition :: dataEdition[2] :: endInscriptionDate :: " + dataEdition[3]);
			log.debug(" :: importEdition :: dataEdition[3] :: startExecutionDate :: " + dataEdition[4]);
			log.debug(" :: importEdition :: dataEdition[4] :: endExecutionDate :: " + dataEdition[5]);
		}
		
		//Comprobar que ningun campo obligatorio venga vacío
		if(notEmptyFields(bw, dataEdition[0], dataEdition[2], dataEdition[3], dataEdition[4], dataEdition[5])){
			//Comprobar que no hay otra edición con ese nombre
			if(isSingleEdition(bw, group, course, dataEdition[0])){
				//Si la friendlyURL no viene informada se construye a partir de la friendlyURL del curso
				friendlyURL = (Validator.isNull(dataEdition[1]) || dataEdition[1].equals(StringPool.BLANK)) ? course.getFriendlyURL()+"-"+dataEdition[0].replace(" ", "-") : dataEdition[1];
				//Comprobar que no existe otra edición con la misma friendlyURL
				if(isSingleFriendlyURL(bw, group, friendlyURL)){
					//Convertir fechas
					Date startInscriptionDate = convertStringIntoDate(dataEdition[2]);
					Date endInscriptionDate = convertStringIntoDate(dataEdition[3]);
					Date startExecutionDate = convertStringIntoDate(dataEdition[4]);
					Date endExecutionDate = convertStringIntoDate(dataEdition[5]);
					//Si alguna fecha convertida es null es porque el formato de fecha no era el correcto
					if(Validator.isNull(startInscriptionDate) || Validator.isNull(endInscriptionDate) || Validator.isNull(startExecutionDate) || Validator.isNull(endExecutionDate)){
						if(log.isDebugEnabled())
							log.debug(" :: importEdition :: ERROR :: HAY UN ERROR DE FORMATO EN ALGUNA DE LAS FECHAS (line " + line + ")");
						bw.append("ERROR: " + LanguageUtil.get(themeDisplay.getLocale(), "course-admin.editions.import-export.error.incorrect-format-date") + 
								"[" + LanguageUtil.get(themeDisplay.getLocale(), "line") + "=" + line +"] \n");
					} else {
						//Comprobar que las fechas fin son posteriores a las de inicio
						if(endDatesAfterStartDates(bw, startInscriptionDate, endInscriptionDate, startExecutionDate, endExecutionDate)){
							//Tras todas las comprobaciones se crea la nueva edición
							AsynchronousProcessAudit process = AsynchronousProcessAuditLocalServiceUtil.addAsynchronousProcessAudit(themeDisplay.getCompanyId(), themeDisplay.getUserId(), Course.class.getName(), "liferay/lms/createEdition");
							Message message=new Message();
							message.put("asynchronousProcessAuditId", process.getAsynchronousProcessAuditId());
							message.put("parentCourseId", parentCourseId);
							message.put("newEditionName",dataEdition[0]);
							message.put("themeDisplay",themeDisplay);
							message.put("startDate",startInscriptionDate);
							message.put("endDate",endInscriptionDate);
							message.put("startExecutionDate",startExecutionDate);
							message.put("endExecutionDate",endExecutionDate);
							message.put("editionFriendlyURL",friendlyURL);
							message.put("isLinked",Boolean.FALSE);
							message.put("serviceContext",serviceContext);
							message.put("editionLayoutId", editionLayoutId);
							MessageBusUtil.sendMessage("liferay/lms/createEdition", message);
							countRegistered++;
						}
					}
				}
			}
		}
	}
	
	private boolean checkLength(String[] dataEdition, BufferedWriter bw) {
		return dataEdition.length == 6;
	}
	
	private Date convertStringIntoDate(String stringDate){
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			date = dateFormat.parse(stringDate);
		} catch (ParseException e) {
			date = null;
			if(log.isDebugEnabled())
				log.debug(e.getMessage());
		}
		return date;
	}
	
	private boolean notEmptyFields(BufferedWriter bw, String editionName, String startInscriptionDate, String endInscriptionDate, String startExecutionDate, String endExecutionDate) throws IOException{
		boolean notEmptyFields = Boolean.TRUE;
		if(Validator.isNull(editionName) || editionName.equals(StringPool.BLANK)){
			if(log.isDebugEnabled())
				log.debug(" :: importEdition :: ERROR :: EL NOMBRE DE LA EDICIÓN NO PUEDE VENIR SIN INFORMAR (line " + line + ")");
			bw.append("ERROR: " + LanguageUtil.get(themeDisplay.getLocale(), "course-admin.editions.import-export.error.empty-edition-name") + 
					"[" + LanguageUtil.get(themeDisplay.getLocale(), "line") + "=" + line +"] \n");
			notEmptyFields = Boolean.FALSE;
		}
		if(notEmptyFields && (Validator.isNull(startInscriptionDate)|| startInscriptionDate.equals(StringPool.BLANK) || Validator.isNull(endInscriptionDate)|| endInscriptionDate.equals(StringPool.BLANK))) {
			if(log.isDebugEnabled())
				log.debug(" :: importEdition :: ERROR :: LAS FECHAS DE INSCRIPCIÓN NO PUEDEN VENIR SIN INFORMAR (line " + line + ")");
			bw.append("ERROR: " + LanguageUtil.get(themeDisplay.getLocale(), "course-admin.editions.import-export.error.empty-inscription-dates") + 
					"[" + LanguageUtil.get(themeDisplay.getLocale(), "line") + "=" + line +"] \n");
			notEmptyFields = Boolean.FALSE;
		}
		if (notEmptyFields && (Validator.isNull(startExecutionDate)|| startExecutionDate.equals(StringPool.BLANK) || Validator.isNull(endExecutionDate)|| endExecutionDate.equals(StringPool.BLANK))) {
			if(log.isDebugEnabled())
				log.debug(" :: importEdition :: ERROR :: LAS FECHAS DE EJECUCIÓN NO PUEDEN VENIR SIN INFORMAR (line " + line + ")");
			bw.append("ERROR: " + LanguageUtil.get(themeDisplay.getLocale(), "course-admin.editions.import-export.error.empty-execution-dates") + 
					"[" + LanguageUtil.get(themeDisplay.getLocale(), "line") + "=" + line +"] \n");
			notEmptyFields = Boolean.FALSE;
		}
		return notEmptyFields;
	}
	private boolean isSingleEdition(BufferedWriter bw, Group group, Course course, String editionName) throws IOException{
		boolean isSingleEdition = Boolean.TRUE;
		group = null;
		try{
			group = GroupLocalServiceUtil.getGroup(themeDisplay.getCompanyId(), course.getTitle(themeDisplay.getLocale())+"-"+editionName);
		}catch(PortalException | SystemException e){
			group = null;
			if(log.isDebugEnabled())
				log.debug(e.getMessage());
		}
		if(Validator.isNotNull(group)){
			if(log.isDebugEnabled())
				log.debug(" :: importEdition :: ERROR :: YA EXISTE OTRA EDICIÓN CON ESE NOMBRE (line " + line + ")");
			bw.append("ERROR: " + LanguageUtil.get(themeDisplay.getLocale(), "course-admin.editions.import-export.error.edition-name-already-exists") + 
					"[" + LanguageUtil.get(themeDisplay.getLocale(), "line") + "=" + line +"] \n");
			isSingleEdition = Boolean.FALSE;
		}
		return isSingleEdition;
	}
	private boolean isSingleFriendlyURL(BufferedWriter bw, Group group, String friendlyURL) throws IOException{
		boolean isSingleFriendlyURL = Boolean.TRUE;
		group = null;
		try {
			group = GroupLocalServiceUtil.fetchFriendlyURLGroup(themeDisplay.getCompanyId(), friendlyURL);
		} catch (SystemException e) {
			group = null;
			if(log.isDebugEnabled())
				log.debug(e.getMessage());
		}
		if(Validator.isNotNull(group)){
			if(log.isDebugEnabled())
				log.debug(" :: importEdition :: ERROR :: YA EXISTE OTRA EDICIÓN CON LA MISMA FRIENDLYURL (line " + line + ")");
			bw.append("ERROR: " + LanguageUtil.get(themeDisplay.getLocale(), "course-admin.editions.import-export.error.edition-friendly-url-already-exists") + 
					"[" + LanguageUtil.get(themeDisplay.getLocale(), "line") + "=" + line +"] \n");
			isSingleFriendlyURL = Boolean.FALSE;
		}
		return isSingleFriendlyURL;
	}
	private boolean endDatesAfterStartDates(BufferedWriter bw, Date startInscriptionDate, Date endInscriptionDate, Date startExecutionDate, Date endExecutionDate) throws IOException{
		boolean endDatesAfterStartDates = Boolean.TRUE;
		if(endInscriptionDate.before(startInscriptionDate)){
			if(log.isDebugEnabled())
				log.debug(" :: importEdition :: ERROR :: LA FECHA DE INICIO DE INSCRIPCIÓN NO PUEDE SER POSTERIOR A LA DE FIN DE INSCRIPCIÓN (line " + line + ")");
			bw.append("ERROR: " + LanguageUtil.get(themeDisplay.getLocale(), "course-admin.editions.import-export.error.start-inscription-date-after-end-inscription-date") + 
					"[" + LanguageUtil.get(themeDisplay.getLocale(), "line") + "=" + line +"] \n");
			endDatesAfterStartDates = Boolean.FALSE;
		} 
		if(endDatesAfterStartDates && endExecutionDate.before(startExecutionDate)){
			if(log.isDebugEnabled())
				log.debug(" :: importEdition :: ERROR :: LA FECHA DE INICIO DE EJECUCIÓN NO PUEDE SER POSTERIOR A LA DE FIN DE EJECUCIÓN (line " + line + ")");
			bw.append("ERROR: " + LanguageUtil.get(themeDisplay.getLocale(), "course-admin.editions.import-export.error.start-execution-date-after-end-execution-date") + 
					"[" + LanguageUtil.get(themeDisplay.getLocale(), "line") + "=" + line +"] \n");
			endDatesAfterStartDates = Boolean.FALSE;
		}
		return endDatesAfterStartDates;
	}
}
