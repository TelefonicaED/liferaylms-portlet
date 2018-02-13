package com.liferay.lms.learningactivity.questiontype;

import java.lang.reflect.Method;
import java.util.Locale;

import javax.portlet.PortletRequest;

import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityClp;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.TestAnswerLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;

public class QuestionTypeClp implements QuestionType {

	private ClassLoaderProxy clp;
	
	public QuestionTypeClp(ClassLoaderProxy clp) {
		this.clp = clp;
	}
		
	public java.lang.Object invokeMethod(java.lang.String name,
			java.lang.String[] parameterTypes, java.lang.Object[] arguments)
			throws java.lang.Throwable {
		throw new UnsupportedOperationException();
	}
	
	public void setLocale(Locale locale){
		try {
			ClassLoader classLoader = clp.getClassLoader();
			Class questionTypeClass = Class.forName(QuestionType.class.getName(),true, classLoader);
			
			Method method = questionTypeClass.getMethod("setLocale", Locale.class); 
			clp.invoke(new MethodHandler(method, locale));
			
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
	}
	
	public long getTypeId(){
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getTypeId",	new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Long)returnObj).longValue();
	}
	
	public String getName(){
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getName", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}
	
	public String getTitle(Locale locale){
		Object returnObj = null;

		try {
			Method method = QuestionType.class.getMethod("getTitle", Locale.class); 
			returnObj = clp.invoke(new MethodHandler(method, locale));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}
	
	public String getDescription(Locale locale){
		Object returnObj = null;

		try {
			Method method = QuestionType.class.getMethod("getDescription", Locale.class); 
			returnObj = clp.invoke(new MethodHandler(method, locale));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}
	
	public String getURLEdit(){
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getURLEdit", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}
	
	public String getURLBack(){
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getURLBack", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}
	
	public void delete(long questionId) throws PortalException, SystemException{
		try {			
			Method method = QuestionType.class.getMethod("delete", long.class);
			clp.invoke(new MethodHandler(method, questionId));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
	}
	
	public long correct(PortletRequest actionRequest, long questionId){
		Object returnObj = null;

		try {
			Method method = QuestionType.class.getMethod("correct", PortletRequest.class, long.class);
			returnObj = clp.invoke(new MethodHandler(method, actionRequest, questionId));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Long)returnObj);
	}
	
	public String getHtmlView(long questionId, ThemeDisplay themeDisplay, Document document){
		Object returnObj = null;

		try {
			Method method = QuestionType.class.getMethod("getHtmlView", long.class, ThemeDisplay.class, Document.class);
			returnObj = clp.invoke(new MethodHandler(method, questionId, themeDisplay, document));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}
	
	public Element getResults(PortletRequest actionRequest, long questionId){
		Object returnObj = null;

		try {
			Method method = QuestionType.class.getMethod("getResults", PortletRequest.class, long.class);
			returnObj = clp.invoke(new MethodHandler(method, actionRequest, questionId));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Element)returnObj);
	}
	
	public String getHtmlFeedback(Document document,long questionId, long actId, ThemeDisplay themeDisplay){
		Object returnObj = null;

		try {
			Method method = QuestionType.class.getMethod("getHtmlFeedback", Document.class, long.class, long.class, ThemeDisplay.class);
			returnObj = clp.invoke(new MethodHandler(method, document, questionId, actId, themeDisplay));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}
	
	public void exportQuestionAnswers(PortletDataContext context, Element root, long questionId, LearningActivity activity) throws PortalException, SystemException{

		try {
			ClassLoader classLoader = clp.getClassLoader();
			Class laTypeClass = Class.forName(LearningActivityType.class.getName(),true, classLoader);
			Class laClass = Class.forName(LearningActivity.class.getName(),true, classLoader);
			Object learningActivityObj = translateLearningActivity(activity);
			
			Method method = laTypeClass.getMethod("exportQuestionAnswers", PortletDataContext.class, Element.class, long.class, laClass);
			clp.invoke(new MethodHandler(method, context, root, questionId, learningActivityObj));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

	}
	
	public void importQuestionAnswers(PortletDataContext context, Element entryElement, long questionId, long userId, ServiceContext serviceContext) throws SystemException, PortalException{
		try {
			ClassLoader classLoader = clp.getClassLoader();
			Class laTypeClass = Class.forName(LearningActivityType.class.getName(),true, classLoader);
			
			Method method = laTypeClass.getMethod("importQuestionAnswers", PortletDataContext.class, Element.class, long.class, long.class, ServiceContext.class);
			clp.invoke(new MethodHandler(method, context, entryElement, questionId, userId, serviceContext));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

	}
	
	public void importXML(long actId, Element question, TestAnswerLocalService testAnswerLocalService) throws SystemException, PortalException {

		try {
			
			ClassLoader classLoader = clp.getClassLoader();
			Class testAnswerLocalServiceClass = Class.forName(TestAnswerLocalService.class.getName(), true, classLoader);
						
			Method method = QuestionType.class.getMethod("importXML", long.class, Element.class, testAnswerLocalServiceClass);
			clp.invoke(new MethodHandler(method, actId, question, testAnswerLocalService));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
	}

	private Object translateLearningActivity(LearningActivity larn) {
		Object larnObj = null;
		try {
			larnObj = Class.forName(LearningActivityClp.class.getName(), true, clp.getClassLoader()).newInstance();
			
			ClassLoaderProxy clp2 = new ClassLoaderProxy(larnObj, LearningActivityClp.class.getName(), clp.getClassLoader());
			clp2.invoke("setModelAttributes", new Object[] {larn.getModelAttributes()});
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
		return larnObj;
	}

	@Override
	public String getURLNew() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxAnswers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDefaultAnswersNo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Element exportXML(long questionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInline() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isPartialCorrectAvailable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean getPenalize() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public long correct(Element element, long questionId){
		Object returnObj = null;

		try {
			Method method = QuestionType.class.getMethod("correct", Element.class, long.class);
			returnObj = clp.invoke(new MethodHandler(method, element, questionId));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Long)returnObj);
	}

}
