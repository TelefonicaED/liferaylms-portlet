package com.liferay.lms.learningactivity.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.portlet.ResourceResponse;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.beanutils.BeanComparator;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.SurveyResult;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.SurveyResultLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.UserLocalServiceUtil;

public class LearningActivityUtils {
	
	static Log log = LogFactoryUtil.getLog(LearningActivityUtils.class);
	
	public static String getExcelTripartitaResults(ResourceResponse response, long actId) throws IOException {
		
		String nombreArchivo = "";
		
		try{
			
			//Creamos fichero excel
			WorkbookSettings wbSettings = new WorkbookSettings();
			WritableWorkbook workbook = Workbook.createWorkbook(response.getPortletOutputStream(), wbSettings);
			workbook.createSheet("Report", 0);
			WritableSheet excelSheet = workbook.getSheet(0);
			
			//Creamos formatos
			WritableCellFormat cabecera = new WritableCellFormat();
			WritableCellFormat contenido = new WritableCellFormat();
			cabecera.setFont(new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD));
			contenido.setFont(new WritableFont(WritableFont.ARIAL, 11));

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
			cabeceras[0]="Usuario";
			cabeceras[1]="Curso";
			cabeceras[2]="M\u00f3dulo";
			cabeceras[3]="Actividad";
			
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
			
			int numeroFila = 0;
			int numeroColumna = 0;
			
			//Pintamos cabecera
			for (String valor:cabeceras){
				addLabel(excelSheet, cabecera, numeroColumna, numeroFila, valor);
				numeroColumna++;
			}
			
			//Reiniciamos columnas
			numeroColumna = 0;
			
			for(Long questionId: questionOrder){
				
				//Por cada pregunta, traemos sus respuestas
				List<SurveyResult> listaRespuestas = SurveyResultLocalServiceUtil
														.getSurveyResultByActId(actId);
				
				//Empezamos por la fila 1
				numeroFila = 1;

				if(listaRespuestas!=null && listaRespuestas.size()!=0){
					for(SurveyResult answer:listaRespuestas)
					{
						String screenName = "";
						try {
							screenName = UserLocalServiceUtil.getUser(answer.getUserId()).getScreenName();
							if(screenName.endsWith("f")){
								screenName = screenName.substring(0, screenName.length()-1);
							}
						}catch(NoSuchUserException nsue){
							log.error(nsue.getMessage());
						}
						
						if(answer.getQuestionId() == questionId){
							// La primera vez pintamos los valores 
							// "Id", "Curso", "Módulo" y "Actividad"
							if(numeroColumna == 0){
								addLabel(excelSheet, contenido, 0, numeroFila, String.valueOf(screenName));
								addLabel(excelSheet, contenido, 1, numeroFila, HtmlUtil.extractText(curso));
								addLabel(excelSheet, contenido, 2, numeroFila, HtmlUtil.extractText(modulo));
								addLabel(excelSheet, contenido, 3, numeroFila, HtmlUtil.extractText(actividad));
								addLabel(excelSheet, contenido, 4, numeroFila, HtmlUtil.extractText(answer.getFreeAnswer()));
							}
							else {
								addLabel(excelSheet, contenido, numeroColumna+numExtraCols, numeroFila, HtmlUtil.extractText(answer.getFreeAnswer()));
							}
							numeroFila++;//Fila nueva
						}
					}
					numeroColumna++;//Columna nueva
				}
			}
			
			nombreArchivo = "Respuestas";
			nombreArchivo += (StringPool.UNDERLINE);
			nombreArchivo += (HtmlUtil.extractText(actividad));
			nombreArchivo += (StringPool.UNDERLINE);
			nombreArchivo += new SimpleDateFormat("yyyyMMdd").format(new Date());
			
			//Rellenamos cabecera respuesta
			response.setCharacterEncoding(StringPool.UTF8);
			response.setContentType(ContentTypes.APPLICATION_VND_MS_EXCEL);
			response.addProperty(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName="+nombreArchivo+".xls");
			
			workbook.write();
			workbook.close();
			
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		} finally{
		}
		
		return nombreArchivo;
	}
	
	private static void addLabel(WritableSheet sheet, WritableCellFormat cellFormat,
			 int column, int row, String value) 
							 throws WriteException, RowsExceededException {
		Label label = new Label(column, row, value, cellFormat);
		CellView cv = new CellView();
		cv.setAutosize(true);
		sheet.setColumnView(column, cv);
		sheet.addCell(label);
	}

}
