package com.liferay.lms;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.SurveyResult;
import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.SurveyResultLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.lms.threads.ExportSurveyStatisticsContentThread;
import com.liferay.lms.threads.ExportSurveyStatisticsThreadMapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;


/**
 * Portlet implementation class SurveyActivity
 */
public class SurveyActivity extends QuestionsAdmin  {

	private static Log log = LogFactoryUtil.getLog(SurveyActivity.class);

	protected String viewJSP = null;
	
	public void init() throws PortletException{
		viewJSP = getInitParameter("view-jsp");
	}
	
	
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		if(ParamUtil.getBoolean(renderRequest, "activityStarted", false)){
			String jsp = renderRequest.getParameter("view");
			if(log.isDebugEnabled())log.debug("VIEW "+jsp);
			include(viewJSP, renderRequest, renderResponse);
			
		}
		
	}
	
	protected void include(String path, RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
			// do nothing
			// _log.error(path + " is not a valid include");
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}

	public void correct(ActionRequest actionRequest,ActionResponse actionResponse)throws Exception {		

		int score = 100;
		long actId=ParamUtil.getLong(actionRequest,"actId",0 );

		boolean isTablet = ParamUtil.getBoolean(actionRequest,"isTablet" );
		
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		Enumeration<String> params=actionRequest.getParameterNames();
		java.util.Hashtable<TestQuestion, TestAnswer> resultados=new java.util.Hashtable<TestQuestion, TestAnswer>();
		java.util.Hashtable<TestQuestion, String> respuestaLibre = new java.util.Hashtable<TestQuestion, String>();
		ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivityTry.class.getName(), actionRequest);
		LearningActivityTry learningTry =LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId, serviceContext);
		LearningActivityTry larntry=LearningActivityTryLocalServiceUtil.getLearningActivityTry(learningTry.getLatId());

		//Comprobar si el usuario se dejo alguna encuesta abierta
		if (larntry.getEndDate() == null )
		{
			while(params.hasMoreElements())
			{
				String param=params.nextElement();
				if(param.startsWith("question_"))
				{
					String squestionId=param.substring("question_".length());
					long questionId=Long.parseLong(squestionId);
					new TestQuestionLocalServiceUtil();
					TestQuestion question=TestQuestionLocalServiceUtil.getTestQuestion(questionId);

					// Preparamos para guardar respuesta del usuario
					SurveyResult surveyResult = SurveyResultLocalServiceUtil.createSurveyResult(CounterLocalServiceUtil.increment(SurveyResult.class.getName()));
					String respuesta = actionRequest.getParameter(param);
					List<TestAnswer> testAnswerList = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId); 
					if(testAnswerList != null && testAnswerList.size() > 0 ){
						for( TestAnswer t : testAnswerList){
							if( respuesta.equalsIgnoreCase(String.valueOf(t.getAnswerId()))){
								String textoRespuesta = t.getAnswer();								
								try{
									respuesta = textoRespuesta.substring(textoRespuesta.indexOf(">") + 1, textoRespuesta.indexOf("</"));
								}catch(Exception e){
									log.error(e.getMessage());
									respuesta = textoRespuesta;
								}
								resultados.put(question, t);						
								//Guardar la encuesta para las estadisticas.
								surveyResult.setActId(actId);
								surveyResult.setLatId(learningTry.getLatId());
								surveyResult.setQuestionId(questionId);
								surveyResult.setAnswerId(Long.valueOf(t.getAnswerId()));
								surveyResult.setUserId(themeDisplay.getUserId());
								surveyResult.setFreeAnswer(respuesta);
								SurveyResultLocalServiceUtil.updateSurveyResult(surveyResult, true);
								break;
							}
						}
					} else {
						surveyResult.setActId(actId);
						surveyResult.setLatId(learningTry.getLatId());
						surveyResult.setQuestionId(questionId);
						surveyResult.setAnswerId(0);// PARA TEXTO LIBRE EL answerId = 0
						surveyResult.setUserId(themeDisplay.getUserId());
						surveyResult.setFreeAnswer(respuesta);
						SurveyResultLocalServiceUtil.updateSurveyResult(surveyResult, true);								
						respuestaLibre.put(question, respuesta);
					}
				}
			}

			//Crear xml para guardar las respuestas
			Element resultadosXML=SAXReaderUtil.createElement("results");
			Document resultadosXMLDoc=SAXReaderUtil.createDocument(resultadosXML);
			for(TestQuestion question:resultados.keySet())
			{
				TestAnswer answer=resultados.get(question);
				if(answer != null){
					Element questionXML=SAXReaderUtil.createElement("question");
					questionXML.addAttribute("id", Long.toString(question.getQuestionId()));
					Element answerXML=SAXReaderUtil.createElement("answer");
					answerXML.addAttribute("id", Long.toString(answer.getAnswerId()));
					questionXML.add(answerXML);
					resultadosXML.add(questionXML);
				}
			}

			for(TestQuestion question:respuestaLibre.keySet())
			{
				String respuestaFree = respuestaLibre.get(question);
				Element questionXML=SAXReaderUtil.createElement("question");
				questionXML.addAttribute("id", Long.toString(question.getQuestionId()));
				Element answerXML=SAXReaderUtil.createElement("answer");
				answerXML.addAttribute("respuestaFree", respuestaFree);
				questionXML.add(answerXML);
				resultadosXML.add(questionXML);					
			}
			//Guardar los resultados
			//LearningActivityTry larntry=LearningActivityTryLocalServiceUtil.getLearningActivityTry(latId);

			larntry.setResult(score);
			larntry.setTryResultData(resultadosXMLDoc.formattedString());
			larntry.setEndDate(new java.util.Date(System.currentTimeMillis()));
			LearningActivityTryLocalServiceUtil.updateLearningActivityTry(larntry);
		}
		actionResponse.setRenderParameters(actionRequest.getParameterMap());
		actionRequest.setAttribute("resultados", resultados);

		actionResponse.setRenderParameter("jspPage", "/html/surveyactivity/view.jsp");

		if(isTablet)actionResponse.setRenderParameter("isTablet", Boolean.toString(true));
		
	}

	public void edit(ActionRequest actionRequest,ActionResponse actionResponse)throws Exception {

		actionResponse.setRenderParameters(actionRequest.getParameterMap());
		if(ParamUtil.getLong(actionRequest, "actId", 0)==0)
		{
			actionResponse.setRenderParameter("jspPage", "/html/surveyactivity/view.jsp");
		}
	}

	public void editactivity(ActionRequest actionRequest, ActionResponse actionResponse) throws PortalException, SystemException, Exception {
		long actId = ParamUtil.getInteger(actionRequest, "resId");
		AssetRendererFactory laf = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
		if (laf != null) {
			AssetRenderer assetRenderer = laf.getAssetRenderer(actId, 0);

			String urlEdit = assetRenderer.getURLEdit((LiferayPortletRequest) actionRequest, (LiferayPortletResponse) actionResponse).toString();
			actionResponse.sendRedirect(urlEdit);
		}
		SessionMessages.add(actionRequest, "asset-renderer-not-defined");
	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException, IOException {
		long actId=0;

		if(ParamUtil.getBoolean(renderRequest, "actionEditingDetails", false)){

			actId=ParamUtil.getLong(renderRequest, "resId", 0);
			renderResponse.setProperty("clear-request-parameters",Boolean.TRUE.toString());
		}
		else{
			actId=ParamUtil.getLong(renderRequest, "actId", 0);
		}

		if(actId==0)// TODO Auto-generated method stub
		{
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
		else
		{
			LearningActivity activity;
			try {
				activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);


				long typeId=activity.getTypeId();

				if(typeId==4)
				{
					
					log.debug("STARTED ACTIVITY "+ParamUtil.getBoolean(renderRequest, "activityStarted", false));
					if(ParamUtil.getBoolean(renderRequest, "activityStarted", false) && !ParamUtil.getBoolean(renderRequest, "actionEditing",false)  && !ParamUtil.getBoolean(renderRequest, "actionEditingDetails",false)){
						String jsp = renderRequest.getParameter("view");
						if(log.isDebugEnabled())log.debug("VIEW "+jsp);
						include(viewJSP, renderRequest, renderResponse);
						
					}else{
						renderRequest.setAttribute("showOrderQuestions", true);
						super.render(renderRequest, renderResponse);
					}	
				}
				else
				{
					renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
				}
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}			
		}
	}

	public void  serveResource(ResourceRequest request, ResourceResponse response)throws PortletException, IOException {

		String action = ParamUtil.getString(request, "action");
		long actId = ParamUtil.getLong(request, "resId",0); 
		
		if (action.equals("stadisticsReport")){
			String filePath = ParamUtil.getString(request, "file", null);
			String fileName =  ParamUtil.getString(request, "fileName");
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
			String uuid = ParamUtil.getString(request, "UUID");
			log.debug("Entra en serve Resource: Action "+action);
			boolean creatingThread = ParamUtil.getBoolean(request, "creatingThread");
			if(creatingThread){
				uuid=null;
			}

			if(filePath!=null){
				File file = new File(filePath);
				int length   = 0;			 
				response.setContentType(ParamUtil.getString(request, "contentType"));
				response.setContentLength((int)file.length());

				response.addProperty(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

				OutputStream out = response.getPortletOutputStream();

				byte[] byteBuffer = new byte[4096];
				DataInputStream in = new DataInputStream(new FileInputStream(file));

				// reads the file's bytes and writes them to the response stream
				while ((in != null) && ((length = in.read(byteBuffer)) != -1)){
					out.write(byteBuffer,0,length);
				}		

				out.flush();
				out.close();
				in.close();

			}else{
				JSONObject oreturned = JSONFactoryUtil.createJSONObject();
				response.setContentType("application/json");

				if(Validator.isNotNull(uuid)){

					boolean finished = ExportSurveyStatisticsThreadMapper.hiloFinished(uuid);
					oreturned.put("threadF", finished);
					log.debug("- not finished");
					if(finished){
						log.debug("+++FINISHED["+uuid+"]+++");
						oreturned.put("file", ExportSurveyStatisticsThreadMapper.getFileUrl(uuid));		
						oreturned.put("fileName", ExportSurveyStatisticsThreadMapper.getFileName(uuid));	
						oreturned.put("contentType", "application/vnd.ms-excel");
						ExportSurveyStatisticsThreadMapper.unlinkHiloExcel(uuid);
						uuid=null;
					}
					oreturned.put("UUID", uuid);
				}else{
					String idHilo = UUID.randomUUID().toString();
					log.debug("idHilo: " + idHilo);				

					ExportSurveyStatisticsContentThread hilo = new ExportSurveyStatisticsContentThread(actId, idHilo,  getPortletConfig(), themeDisplay.getLocale());
					ExportSurveyStatisticsThreadMapper.addHilo(idHilo, hilo);
					oreturned.put("UUID", idHilo);

				}	
				oreturned.put("action", action);
				PrintWriter out = response.getWriter();
				out.print(oreturned.toString());
				out.flush();
				out.close();
			}
		}else{
			super.serveResource(request, response);
		}
	}

}
