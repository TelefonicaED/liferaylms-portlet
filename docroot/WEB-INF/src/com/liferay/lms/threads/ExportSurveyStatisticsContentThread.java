package com.liferay.lms.threads;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletConfig;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.SurveyResult;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.SurveyResultLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.lms.service.persistence.SurveyResultFinder;
import com.liferay.lms.service.persistence.SurveyResultFinderUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;




public class ExportSurveyStatisticsContentThread extends Thread {
	private Log log = LogFactoryUtil.getLog(ExportSurveyStatisticsContentThread.class);

	private boolean isFinished = false;	
	private long actId;
	private String idHilo = null;
	private String fileName = null; 
	private String filePath = null;
	private PortletConfig portletConfig;
	private Locale locale;
	
	public ExportSurveyStatisticsContentThread(long actId,String idHilo, PortletConfig portletConfig, Locale locale){
		this.actId = actId;
		this.idHilo = idHilo;
		this.portletConfig = portletConfig;
		this.locale =  locale;
	}

	@SuppressWarnings("unchecked")
	public void run() {
		log.debug("::ARRANCAMOS HILO USERS EXPORT EXCEL:::"); 
		// Presenta en pantalla informacion sobre este hilo en particular
		try {
			
			File file = FileUtil.createTempFile("xls");
			FileOutputStream bw = null;
			
			try {
				bw = new FileOutputStream(file);
			} 
			catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			
			
			//Creamos fichero excel
			HSSFWorkbook workbook = new HSSFWorkbook();
		    HSSFSheet sheet = workbook.createSheet(LanguageUtil.get(portletConfig, locale, "report"));
		  
		    
		    //Creamos formatos
		    HSSFFont font = workbook.createFont();
		    font.setFontName(HSSFFont.FONT_ARIAL);
		    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    HSSFCellStyle cabecera = workbook.createCellStyle();
		    cabecera.setFont(font); 
		    HSSFCellStyle contenido = workbook.createCellStyle();
		    HSSFFont fontContenido = workbook.createFont();
		    fontContenido.setFontName(HSSFFont.FONT_ARIAL);
		     contenido.setFont(fontContenido); 
		    
			
			//Creamos la cabecera con las preguntas.
			List<TestQuestion> questionsTitle = TestQuestionLocalServiceUtil.getQuestions(actId);
			List<TestQuestion> listaTotal = ListUtil.copy(questionsTitle);
			BeanComparator beanComparator = new BeanComparator("weight");
			Collections.sort(listaTotal, beanComparator);
			questionsTitle = listaTotal;
			
			//Anadimos x columnas para mostrar id, curso...
			int numExtraCols = 4;
			String[] cabeceras = new String[questionsTitle.size()+numExtraCols];

			//En las columnas extra ponemos la cabecera
			cabeceras[0]=LanguageUtil.get(portletConfig, locale, "id");
			cabeceras[1]=LanguageUtil.get(portletConfig, locale, "course");
			cabeceras[2]=LanguageUtil.get(portletConfig, locale, "module");
			cabeceras[3]=LanguageUtil.get(portletConfig, locale, "activity");
			
			// - Guardamos el orden en que obtenemos las preguntas de la base
			//   de datos para poner las respuestas en el mismo orden.
			// - Generamos cabecera completa.
			Long []questionOrder = new Long[questionsTitle.size()];

			for(int i=numExtraCols; i<questionsTitle.size()+numExtraCols; i++){
				cabeceras[i] = HtmlUtil.extractText(questionsTitle.get(i-numExtraCols).getText());
				questionOrder[i-numExtraCols] = questionsTitle.get(i-numExtraCols).getQuestionId();
			}
			
			//Consultamos los nombres de curso, módulo y actividad
			LearningActivity activity = LearningActivityLocalServiceUtil
											.getLearningActivity(actId);
			
			String curso = CourseLocalServiceUtil.fetchByGroupCreatedId(
								activity.getGroupId()).getTitle();
			String modulo = ModuleLocalServiceUtil.fetchModule(
								activity.getModuleId()).getTitle();
			String actividad = activity.getTitle();
			
			int rowNumber = 0;
			int columnNumber = 0;
			HSSFRow row = sheet.createRow(rowNumber++);
			//Pintamos cabecera
			for (String valor:cabeceras){
				addLabel(sheet, cabecera, columnNumber++, row, valor);
			}
			
			
			//Por cada pregunta, traemos sus respuestas
		
			
			
			
			
			
			//Por cada intento sacamos todas las respuestas
			
			List<Long> tries = SurveyResultLocalServiceUtil.getLearningActivityTriesByActId(actId);
			SurveyResult answer = null;
			rowNumber=1;
			for(Long latId : tries){
				row = sheet.getRow(rowNumber);
				if(row==null){
					row = sheet.createRow(rowNumber);
				}
				// La primera vez pintamos los valores 
				// "Id", "Curso", "Módulo" y "Actividad"
				addLabel(sheet, contenido, 0, row, String.valueOf(rowNumber));
				addLabel(sheet, contenido, 1, row, HtmlUtil.extractText(curso));
				addLabel(sheet, contenido, 2, row, HtmlUtil.extractText(modulo));
				addLabel(sheet, contenido, 3, row, HtmlUtil.extractText(actividad));
				//Reiniciamos columnas
				columnNumber = 0;
				for(Long questionId: questionOrder){
					answer = SurveyResultLocalServiceUtil.getSurveyResultByQuestionIdActIdLatId(questionId, actId, latId);
					
					if(answer!=null){
						addLabel(sheet, contenido, columnNumber+numExtraCols, row, HtmlUtil.extractText(answer.getFreeAnswer()));
					}else{
						addLabel(sheet, contenido, columnNumber+numExtraCols, row, "");
					}
					columnNumber++;
				}
				rowNumber++;
			}
			workbook.write(bw);
			try {
				bw.close();
			} 
		  	catch (IOException e) {
				e.printStackTrace();
			}		
			
			
			fileName = LanguageUtil.get(portletConfig, locale, "answers");
			fileName += (StringPool.UNDERLINE);
			fileName += (HtmlUtil.extractText(actividad));
			fileName += (StringPool.UNDERLINE);
			fileName += new SimpleDateFormat("yyyyMMdd").format(new Date());
			fileName += ".xls";
			filePath=file.getPath();
			isFinished=true;
			
		}catch (Exception e) {
			e.printStackTrace();
			ExportSurveyStatisticsThreadMapper.unlinkHiloExcel(idHilo);
		} 
	}

	private void addLabel(HSSFSheet sheet, HSSFCellStyle style,
			 int column, HSSFRow row, String value) 
					 throws WriteException, RowsExceededException {
		HSSFCell cell = row.createCell(column, HSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
	
	public boolean isFinished(){
		return this.isFinished;
	}

	public String getFileName(){
		return this.fileName;
	}

	public String getFilePath(){
		return this.filePath;
	}

	
	public String getIdHilo(){
		return this.idHilo;
	}

}
