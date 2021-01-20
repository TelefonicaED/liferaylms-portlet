package com.liferay.lms;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.learningactivity.TestLearningActivityType;
import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry;
import com.liferay.lms.learningactivity.questiontype.QuestionType;
import com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.lms.util.LmsConstant;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.tls.lms.util.LiferaylmsUtil;

/**
 * Portlet implementation class ExecActivity
 */
public class ExecActivity extends QuestionsAdmin {
	
	private String viewJSP = null;
	private String resultsJSP = null;
	private String previewJSP = null;
	private static Log log = LogFactoryUtil.getLog(ExecActivity.class);
	
	public void init() throws PortletException {	
		viewJSP = getInitParameter("view-template");
		resultsJSP = getInitParameter("results-template");
		previewJSP = getInitParameter("preview-template");
	}
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException, IOException {
		long actId=0;
		boolean actionEditingDetails = ParamUtil.getBoolean(renderRequest, "actionEditingDetails", false);
		
		renderResponse.setProperty("clear-request-parameters",StringPool.TRUE);
		
		if(actionEditingDetails){
			actId=ParamUtil.getLong(renderRequest, "resId", 0);
		} else{
			actId=ParamUtil.getLong(renderRequest, "actId", 0);
		}

		if(actId==0){
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		} else {
			
			try {
				LearningActivity activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
			
				long typeId = activity.getTypeId();
	
				if(typeId==0){
					log.debug("editamos los detalles");
					renderRequest.setAttribute("showOrderQuestions", true);
					
					super.render(renderRequest, renderResponse);
				} else {
					renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
				}
			} catch (PortalException | SystemException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void doView(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException {
		
		String jsp = renderRequest.getParameter("view");
		
		if(jsp == null || "".equals(jsp)){
			
			long actId=ParamUtil.getLong(renderRequest, "actId", ParamUtil.getLong(renderRequest, "resId", 0));
			
			try {
				LearningActivity activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
				
				ThemeDisplay themeDisplay  =(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
				boolean hasAccessLock = CourseLocalServiceUtil.canAccessLock(themeDisplay.getScopeGroupId(), themeDisplay.getUser());
				Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());
				boolean hasPermissionAccessCourseFinished = LiferaylmsUtil.hasPermissionAccessCourseFinished(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), course.getCourseId(), themeDisplay.getUserId());
				
				if(activity.canAccess(true, themeDisplay.getUser(), themeDisplay.getPermissionChecker(), hasAccessLock, course, hasPermissionAccessCourseFinished)){
					//Comprobamos si la actividad tiene fecha de fin
					log.debug("actId: " + actId);
					LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, themeDisplay.getUserId());
					
					if(learningActivityResult != null && learningActivityResult.getEndDate() != null){
						log.debug("uno");
						showViewResults(renderRequest, renderResponse, activity);
					}else{
					
						boolean improve =ParamUtil.getBoolean(renderRequest, "improve",false);
		
						boolean improving = false;
						LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, themeDisplay.getUserId());
							
						if(result != null){
							int done =  LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(actId,themeDisplay.getUserId());
							LearningActivity act=LearningActivityLocalServiceUtil.getLearningActivity(actId);
							
							if(result.getResult() < 100 && !activity.isLocked(themeDisplay.getUserId()) && LearningActivityResultLocalServiceUtil.userPassed(actId, themeDisplay.getUserId()) && (done < act.getTries() || act.getTries() == 0)){
								improving = true;
							}
						}
						
						if(!improve && LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId()) || hasPermissionAccessCourseFinished){
							log.debug("dos");
							showViewResults(renderRequest, renderResponse,activity);
						}else if (LearningActivityTryLocalServiceUtil.canUserDoANewTry(actId, themeDisplay.getUserId()) 
								//|| permissionChecker.hasPermission(activity.getGroupId(), LearningActivity.class.getName(),actId, ActionKeys.UPDATE)
								//|| permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ACCESSLOCK")
							  		|| improving 
							  		|| hasPermissionAccessCourseFinished){
								
							boolean onlyPreview = Boolean.valueOf(LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(), "showOnlyPreview", "false"));
							long learningActivityTries = activity.getTries();
							int userTries = LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(activity.getActId(), themeDisplay.getUserId());
							boolean userHasTried = Validator.isNotNull(userTries) && userTries>0;
							
							if((learningActivityTries>0 && !userHasTried) || onlyPreview) {
								log.debug("preview");
								showViewPreview(renderRequest, renderResponse);
							}else{
								log.debug("editamos los detalles");
								showViewExam(renderRequest, renderResponse);
							}
						}else {
							log.debug("tres");
							showViewResults(renderRequest, renderResponse, activity);
						}
					
					}
				}else {
					log.debug("no puedes acceder");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("exam".equals(jsp)){
			showViewExam(renderRequest, renderResponse);
		}else if("preview".equals(jsp)){
			showViewPreview(renderRequest, renderResponse);
		}else if("results".equals(jsp)){
			long actId=ParamUtil.getLong(renderRequest, "actId", ParamUtil.getLong(renderRequest, "resId", 0));
			
			try {
				LearningActivity activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
				showViewResults(renderRequest, renderResponse, activity);
			} catch (PortalException | SystemException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void showViewPreview(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		log.debug("preview");
		include(previewJSP, renderRequest, renderResponse);
	}
	
	private void showViewExam(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException{
		log.debug("exam");
		include(viewJSP, renderRequest, renderResponse);
	}
	
	private void showViewResults(RenderRequest renderRequest,RenderResponse renderResponse, LearningActivity activity) throws IOException, PortletException, PortalException, SystemException{
		log.debug("results");
		
		ThemeDisplay themeDisplay  =(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		LearningActivityTry lat = LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(activity.getActId(), themeDisplay.getUserId());
		
		LearningActivityResult lar = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(activity.getActId(), themeDisplay.getUserId());
		boolean showPopUpFinishedResult = lar != null && lar.getEndDate() == null 
				&& PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), LmsConstant.PREFS_SHOW_OPTION_TEST, false) 
				&& activity.isImprove();
		
		if (showPopUpFinishedResult && lar.getResult() ==100) {
		    showPopUpFinishedResult = false;
		    finishActivity(lar, lat);
		}
				
		renderRequest.setAttribute("learningActivity",activity);
		renderRequest.setAttribute("larntry",lat);
		renderRequest.setAttribute("actId",activity.getActId());
		renderRequest.setAttribute("showPopUpFinishedResult", showPopUpFinishedResult);
		
		include(resultsJSP, renderRequest, renderResponse);
	}
	
	public void finishedResult(ActionRequest actionRequest, ActionResponse actionResponse) throws SystemException, PortalException{
		ThemeDisplay themeDisplay  =(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long actId = ParamUtil.getLong(actionRequest, "actId");
		
		LearningActivityResult lar = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, themeDisplay.getUserId());		
		LearningActivityTry lat = LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, themeDisplay.getUserId());
		
		finishActivity(lar, lat);
		
		actionResponse.setRenderParameter("view", "results");
		actionResponse.setRenderParameter("actId", String.valueOf(actId));
	}

	public void correct	(ActionRequest actionRequest,ActionResponse actionResponse)	throws Exception {

		long actId=ParamUtil.getLong(actionRequest, "actId");
		long latId=ParamUtil.getLong(actionRequest,"latId" );
		boolean isTablet = ParamUtil.getBoolean(actionRequest,"isTablet" );
		String navigate = ParamUtil.getString(actionRequest, "navigate");
		boolean isPartial = false;
		if (Validator.isNotNull(navigate)) {
			if (Validator.equals(navigate, "backward") || Validator.equals(navigate, "forward")) {
				isPartial = true;
			}
		}

		LearningActivityTry larntry=LearningActivityTryLocalServiceUtil.getLearningActivityTry(latId);

		//Comprobar que el usuario tenga intentos posibles.
		if (larntry.getEndDate() == null){

			long correctanswers=0,penalizedAnswers=0;
			Element resultadosXML=SAXReaderUtil.createElement("results");
			Document resultadosXMLDoc=SAXReaderUtil.createDocument(resultadosXML);

			long[] questionIds = ParamUtil.getLongValues(actionRequest, "question");

			for (long questionId : questionIds) {
				TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
				QuestionType qt = new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
				resultadosXML.add(qt.getResults(actionRequest, questionId));								
			}

			LearningActivityType lat = new LearningActivityTypeRegistry().getLearningActivityType(TestLearningActivityType.TYPE_ID);
			
			if(log.isDebugEnabled())
				log.debug(String.format("\n\tisPartial: %s\n\tcorrectanswers: %s\n\tpenalizedAnswers: %s\n\tquestionIds.length: %s", isPartial, correctanswers, penalizedAnswers, questionIds.length));
			// penalizedAnswers tiene valor negativo, por eso se suma a correctanswers
			
			
			LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, PortalUtil.getUserId(actionRequest));
			long oldResult=-1;
			if(learningActivityResult!=null) oldResult=learningActivityResult.getResult();

			larntry.setTryResultData(resultadosXMLDoc.formattedString());
			
			long score=isPartial ? 0 : lat.calculateResult(LearningActivityLocalServiceUtil.fetchLearningActivity(larntry.getActId()), larntry) ;
			if(log.isDebugEnabled())
				log.debug("Score: " + score);

			if(score < 0)score = 0;
			
			if (!isPartial) {
				larntry.setResult(score);
				larntry.setEndDate(new java.util.Date(System.currentTimeMillis()));
			}
			
			LearningActivityTryLocalServiceUtil.updateLearningActivityTry(larntry);

			actionResponse.setRenderParameters(actionRequest.getParameterMap());

			if (isPartial) {
				actionResponse.setRenderParameter("improve", ParamUtil.getString(actionRequest, "improve", Boolean.FALSE.toString()));
				if(isTablet)actionResponse.setRenderParameter("isTablet", Boolean.toString(true));
				actionResponse.setRenderParameter("view", "exam");
			} else {
				actionResponse.setRenderParameter("oldResult", Long.toString(oldResult));
				actionResponse.setRenderParameter("correction", Boolean.toString(true));
				actionResponse.setRenderParameter("score", String.valueOf(learningActivityResult.getResult() > score ? learningActivityResult.getResult():score));
				actionResponse.setRenderParameter("tryResultData", resultadosXMLDoc.formattedString());
				if(isTablet)actionResponse.setRenderParameter("isTablet", Boolean.toString(true));
				actionResponse.setRenderParameter("view", "results");
			}
		}else{
			actionResponse.setRenderParameters(actionRequest.getParameterMap());
			actionRequest.setAttribute("actId", actId);
			actionResponse.setRenderParameter("view", "preview");
		}						

	}
	
	/**
	 * Corrección para cuando estamos en modo observador ya que no se tiene que guardar nada en learningactivitytry
	 * @param actionRequest
	 * @param actionResponse
	 * @throws SystemException 
	 * @throws Exception
	 */
	
	public void correctAccessFinished	(ActionRequest actionRequest,ActionResponse actionResponse) throws SystemException{

		long actId=ParamUtil.getLong(actionRequest, "actId");

		boolean isTablet = ParamUtil.getBoolean(actionRequest,"isTablet" );

		long correctanswers=0,penalizedAnswers=0;
		Element resultadosXML=SAXReaderUtil.createElement("results");
		Document resultadosXMLDoc=SAXReaderUtil.createDocument(resultadosXML);

		long[] questionIds = ParamUtil.getLongValues(actionRequest, "question");


		for (long questionId : questionIds) {
			TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
			QuestionType qt = new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
			if(qt.correct(actionRequest, questionId)>0) {
				correctanswers++;
			}else if(question.isPenalize()){
				penalizedAnswers++;
			}
			resultadosXML.add(qt.getResults(actionRequest, questionId));								
		}


		List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(actId);
		long score = (correctanswers-penalizedAnswers)*100/questions.size();
		if(score < 0)score = 0;
		
		
		actionResponse.setRenderParameters(actionRequest.getParameterMap());

		actionResponse.setRenderParameter("correction", Boolean.toString(true));
		if(isTablet)actionResponse.setRenderParameter("isTablet", Boolean.toString(true));
		try {
			//actionResponse.setRenderParameter("tryResultData", resultadosXMLDoc.formattedString());
			actionResponse.setRenderParameter("tryResultData", resultadosXMLDoc.formattedString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		actionResponse.setRenderParameter("score", String.valueOf(score));
		actionResponse.setRenderParameter("view", "results");					

	}

	public void camposExtra(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {

		long actId = ParamUtil.getLong(actionRequest, "actId", 0);
		long randomString=ParamUtil.getLong(actionRequest, "randomString",0);
		String passwordString=ParamUtil.getString(actionRequest, "passwordString",StringPool.BLANK);
		long hourDurationString=ParamUtil.getLong(actionRequest, "hourDurationString",0);
		long minuteDurationString=ParamUtil.getLong(actionRequest, "minuteDurationString",0);
		long secondDurationString=ParamUtil.getLong(actionRequest, "secondDurationString",0);
		long timeStamp = hourDurationString * 3600 + minuteDurationString * 60 + secondDurationString;

		String showCorrectAnswer=ParamUtil.getString(actionRequest, "showCorrectAnswer", "false");
		String showCorrectAnswerOnlyOnFinalTry=ParamUtil.getString(actionRequest, "showCorrectAnswerOnlyOnFinalTry", "false");
		String improve=ParamUtil.getString(actionRequest, "improve", "false");

		long questionsPerPage = ParamUtil.getInteger(actionRequest, "questionsPerPage", 1);
		
		String isMultiple = ParamUtil.getString(actionRequest, "banks-multipleselections", "false");
		String isBank = ParamUtil.getString(actionRequest, "is-bank", "false"); 
		String assetCategoryIds = ParamUtil.getString(actionRequest, "assetCategoryIds", StringPool.BLANK);
		
		if(randomString==0) {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "random", StringPool.BLANK);
		}
		else {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "random", Long.toString(randomString));
		}

		LearningActivityLocalServiceUtil.setExtraContentValue(actId, "password", HtmlUtil.escape(passwordString.trim()));

		if(timeStamp==0) {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "timeStamp", StringPool.BLANK);
		}
		else {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "timeStamp", Long.toString(timeStamp));
		}

		if(showCorrectAnswer.equals("true")) {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "showCorrectAnswer", "true");
		}else if(showCorrectAnswer.equals("false")){
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "showCorrectAnswer", "false");
		}
		
		if(showCorrectAnswerOnlyOnFinalTry.equals("true")) {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "showCorrectAnswerOnlyOnFinalTry", "true");
		}else if(showCorrectAnswerOnlyOnFinalTry.equals("false")){
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "showCorrectAnswerOnlyOnFinalTry", "false");
		}

		if(questionsPerPage == 0) {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "questionsPerPage", StringPool.BLANK);
		}
		else {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "questionsPerPage", Long.toString(questionsPerPage));
		}
		
		if(isBank.equals("true")){
			LearningActivityLocalServiceUtil.setExtraContentValue(actId,"isBank", "true");
		}else if(isBank.equals("false")) {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "isBank", "false");
		}
		
		if(isMultiple.equals("true")){
			LearningActivityLocalServiceUtil.setExtraContentValue(actId,"isMultiple", "true");
		}else if(isMultiple.equals("false")) {
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "isMultiple", "false");
		}
		
		if(!StringPool.BLANK.equals(assetCategoryIds)){
			LearningActivityLocalServiceUtil.setExtraContentValue(actId,"categoriesId", assetCategoryIds);
		}else{
			LearningActivityLocalServiceUtil.setExtraContentValue(actId,"categoriesId", StringPool.BLANK);
		}
		

		SessionMessages.add(actionRequest, "activity-saved-successfully");
		actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/edit.jsp");

	}

	public void edit(ActionRequest actionRequest, ActionResponse actionResponse)
			throws PortalException, SystemException, Exception {

		actionResponse.setRenderParameters(actionRequest.getParameterMap());
		if(ParamUtil.getLong(actionRequest, "actId", 0)==0)// TODO Auto-generated method stub
		{
			actionResponse.setRenderParameter("jspPage", "/html/lmsactivitieslist/view.jsp");
		}
	}
	public void editactivity(ActionRequest actionRequest, ActionResponse actionResponse)
			throws PortalException, SystemException, Exception {
		long actId = ParamUtil.getInteger(actionRequest, "actId");
		// LearningActivity learnact =
		// com.liferay.lms.service.LearningActivityServiceUtil.getLearningActivity(actId);
		AssetRendererFactory laf = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
		if (laf != null) {
			AssetRenderer assetRenderer = laf.getAssetRenderer(actId, 0);

			String urlEdit = assetRenderer.getURLEdit((LiferayPortletRequest) actionRequest, (LiferayPortletResponse) actionResponse).toString();
			actionResponse.sendRedirect(urlEdit);
		}
		SessionMessages.add(actionRequest, "asset-renderer-not-defined");
	}
	
	public void setGrades(ActionRequest request, ActionResponse response) throws IOException, PortletException, SystemException {
		
		ThemeDisplay themeDisplay  =(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		
		boolean correct=true;
		long actId = ParamUtil.getLong(request,"actId"); 
		Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());	
		long studentId = ParamUtil.getLong(request,"studentId");						
		String comments = ParamUtil.getString(request,"comments");
		
		log.debug("actId: "+actId);
		log.debug("studentId: "+studentId);
		log.debug("comments: "+comments);
		
		String firstName = ParamUtil.getString(request, "first-name");
		String lastName = ParamUtil.getString(request, "last-name");
		String screenName = ParamUtil.getString(request, "screen-name");
		String emailAddress = ParamUtil.getString(request, "email-address");
		
		log.debug("firstName: "+firstName);
		log.debug("lastName: "+lastName);
		log.debug("screenName: "+screenName);
		log.debug("emailAddress: "+emailAddress);
		
		response.setRenderParameter("first-name", firstName);
		response.setRenderParameter("last-name", lastName);	
		response.setRenderParameter("screen-name", screenName);
		response.setRenderParameter("email-address", emailAddress);	
		
		CalificationType ct = null;
		double result=0;
		try {	
			result = Double.valueOf(ParamUtil.getString(request,"result").replace(",", "."));
					
			ct = new CalificationTypeRegistry().getCalificationType(course.getCalificationType());			
			
			log.debug("result: "+result);
			
			if(result<0 || result>100){
				correct=false;
				SessionErrors.add(request, "result-bad-format");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			correct=false;
			SessionErrors.add(request, "result-bad-format");
		} catch (Exception e) {
			e.printStackTrace();
			correct=false;
			SessionErrors.add(request, "grades.bad-updating");
		}
		
		if(correct) {
			try {
				LearningActivityTry  learningActivityTry =  LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, studentId);
				if(learningActivityTry==null){
					ServiceContext serviceContext = new ServiceContext();
					serviceContext.setUserId(studentId);
					learningActivityTry =  LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
				}
				learningActivityTry.setEndDate(new Date());
				learningActivityTry.setResult(ct.toBase100(course.getGroupCreatedId(),result));
				learningActivityTry.setComments(comments);
				updateLearningActivityTryAndResult(learningActivityTry);
				
				SessionMessages.add(request, "grades.updating");
			} catch (NestableException e) {
				SessionErrors.add(request, "grades.bad-updating");
			}
		}
		
		response.setRenderParameter("jspPage", "/html/execactivity/test/correction.jsp");
		response.setRenderParameter("actId", String.valueOf(actId));
		response.setRenderParameter("courseId", String.valueOf(course.getCourseId()));
	}
	
    private void finishActivity(LearningActivityResult lar, LearningActivityTry lat) throws SystemException,
        PortalException
    {
        lar.setEndDate(lat.getEndDate());
        LearningActivityResultLocalServiceUtil.updateLearningActivityResult(lar);

        // Recalculamos
        ModuleResultLocalServiceUtil.update(lar);
    }
	
	private void updateLearningActivityTryAndResult(
			LearningActivityTry learningActivityTry) throws PortalException,
			SystemException {
		LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningActivityTry);
		
		LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivityTry.getActId(), learningActivityTry.getUserId());
		if(learningActivityResult.getResult() != learningActivityTry.getResult()) {
			LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(learningActivityTry.getActId());
			learningActivityResult.setResult(learningActivityTry.getResult());
			LearningActivityTypeRegistry registry = new LearningActivityTypeRegistry();
			LearningActivityType learningActivityType = registry.getLearningActivityType(learningActivity.getTypeId());
			learningActivityResult.setPassed(learningActivityType.isPassed(learningActivity, learningActivityTry));
			LearningActivityResultLocalServiceUtil.updateLearningActivityResult(learningActivityResult);
		}
	}
	
	protected void include(String path, RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(path);

		if (portletRequestDispatcher != null) {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}
}
