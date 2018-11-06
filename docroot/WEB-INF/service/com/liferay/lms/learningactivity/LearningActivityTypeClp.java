package com.liferay.lms.learningactivity;

import java.io.IOException;
//import java.lang.reflect.Method;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletResponse;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityClp;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetRenderer;

public class LearningActivityTypeClp implements LearningActivityType {

	private static Log log = LogFactoryUtil.getLog(LearningActivityTypeClp.class);
	private ClassLoaderProxy clp;
	
	public LearningActivityTypeClp(ClassLoaderProxy clp) {
		this.clp = clp;
	}
		
	public java.lang.Object invokeMethod(java.lang.String name,
			java.lang.String[] parameterTypes, java.lang.Object[] arguments)
			throws java.lang.Throwable {
		throw new UnsupportedOperationException();
	}
		
	public long getDefaultScore() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getDefaultScore", new Object[] {});
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
	
	public long getDefaultTries() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getDefaultTries", new Object[] {});
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
	
	public long getTypeId() {
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
	
	public String getDefaultFeedbackCorrect() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getDefaultFeedbackCorrect", new Object[] {});
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
	
	public String getDefaultFeedbackNoCorrect() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getDefaultFeedbackNoCorrect", new Object[] {});
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
	
	public boolean isScoreConfigurable() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("isScoreConfigurable", new Object[] {});
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

		return ((Boolean)returnObj).booleanValue();
	}
	
	public boolean isTriesConfigurable() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("isTriesConfigurable", new Object[] {});
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

		return ((Boolean)returnObj).booleanValue();
	}
	
	public boolean isFeedbackCorrectConfigurable() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("isFeedbackCorrectConfigurable", new Object[] {});
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

		return ((Boolean)returnObj).booleanValue();
	}
	
	public boolean isFeedbackNoCorrectConfigurable() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("isFeedbackNoCorrectConfigurable", new Object[] {});
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

		return ((Boolean)returnObj).booleanValue();
	}
	
	public String getName() {
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
	
	public String getClassName() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getClassName", new Object[] {});
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
	
	
	@SuppressWarnings("deprecation")
	public AssetRenderer getAssetRenderer(LearningActivity larn) throws SystemException, PortalException {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getAssetRenderer", new Object[] {translateLearningActivity(larn)});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else if (t instanceof SystemException) {
				throw (SystemException)t;
			}
			else if (t instanceof PortalException) {
				throw (PortalException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (AssetRenderer)returnObj;
	}
	
	public String getUrlIcon() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getUrlIcon", new Object[] {});
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
	
	public String getDescription() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getDescription", new Object[] {});
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
	
	public boolean gradebook() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("gradebook", new Object[] {});
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

		return ((Boolean)returnObj).booleanValue();
	}
	
	public boolean hasEditDetails() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("hasEditDetails", new Object[] {});
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

		return ((Boolean)returnObj).booleanValue();
	}

	public boolean hasDeleteTries() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("hasDeleteTries", new Object[] {});
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

		return ((Boolean)returnObj).booleanValue();
	}
	
	public boolean hasMandatoryDates() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("hasMandatoryDates", new Object[] {});
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

		return ((Boolean)returnObj).booleanValue();
	}
	
	public String getExpecificContentPage() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getExpecificContentPage", new Object[] {});
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
	
	public String setExtraContent(UploadRequest uploadRequest, 
			PortletResponse portletResponse,LearningActivity learningActivity) 
			throws PortalException,SystemException,DocumentException,IOException {
		try {
			
			log.info(":::::setExtraContent:::::");
			ClassLoader classLoader = clp.getClassLoader();
			Class learningActivityClass = Class.forName(LearningActivity.class.getName(),true, classLoader);
			MethodKey setExtraContentMethod = new MethodKey(clp.getClassName(), "setExtraContent", UploadRequest.class, PortletResponse.class, learningActivityClass);
			Object learningActivityObj = translateLearningActivity(learningActivity);
			log.info(":::::invoke:::::");
			clp.invoke(new MethodHandler(setExtraContentMethod, uploadRequest, portletResponse, learningActivityObj));
			log.info(":::::invoked:::::");
			ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(learningActivityObj, clp.getClassLoader());
			learningActivity.setModelAttributes((Map<String, Object>) classLoaderProxy.invoke("getModelAttributes", new Object[]{}));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);
			
			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}
			
			if (t instanceof IOException) {
				throw (IOException)t;
			}
			
			if (t instanceof DocumentException) {
				throw (DocumentException)t;
			}
			
			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
		return null;

	}
	
	
	public boolean especificValidations(UploadRequest uploadRequest,PortletResponse portletResponse) {
		Object returnObj = null;

		try {
			MethodKey especificValidationsMethod = new MethodKey(clp.getClassName(), "especificValidations", UploadRequest.class, PortletResponse.class);		    
			returnObj = clp.invoke(new MethodHandler(especificValidationsMethod, uploadRequest, portletResponse));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				t.printStackTrace();
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
		return ((Boolean)returnObj).booleanValue();
	}
	
	public void afterInsertOrUpdate(UploadRequest uploadRequest,PortletResponse portletResponse,LearningActivity learningActivity) 
			throws PortalException,SystemException {
		
		try {
			ClassLoader classLoader = clp.getClassLoader();
			Class learningActivityClass = Class.forName(LearningActivity.class.getName(),true, classLoader);
			
			MethodKey afterInsertOrUpdateMethod = new MethodKey(clp.getClassName(), "afterInsertOrUpdate", UploadRequest.class, PortletResponse.class, learningActivityClass);
			Object learningActivityObj = translateLearningActivity(learningActivity);
			clp.invoke(new MethodHandler(afterInsertOrUpdateMethod, uploadRequest, portletResponse, learningActivityObj));
			ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(learningActivityObj, clp.getClassLoader());
			learningActivity.setModelAttributes((Map<String, Object>) classLoaderProxy.invoke("getModelAttributes", new Object[]{}));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);
			
			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}
						
			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
	}
	
	@Override
	public String getMesageEditDetails() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getMesageEditDetails", new Object[] {});
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
	
	public String getPortletId() {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getPortletId", new Object[] {});
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
	
	public void deleteResources(ActionRequest actionRequest,ActionResponse actionResponse,LearningActivity learningActivity)
			throws PortalException,SystemException,DocumentException,IOException{
		try {
			ClassLoader classLoader = clp.getClassLoader();
			Class learningActivityClass = Class.forName(LearningActivity.class.getName(),true, classLoader);
			
			MethodKey deleteResourcesMethod = new MethodKey(clp.getClassName(), "deleteResources", ActionRequest.class, ActionResponse.class, learningActivityClass);    
			Object learningActivityObj = translateLearningActivity(learningActivity);
			clp.invoke(new MethodHandler(deleteResourcesMethod, actionRequest, actionResponse, learningActivityObj));
			ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(learningActivityObj, clp.getClassLoader());
			learningActivity.setModelAttributes((Map<String, Object>) classLoaderProxy.invoke("getModelAttributes", new Object[]{}));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);
			
			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}
			
			if (t instanceof IOException) {
				throw (IOException)t;
			}
			
			if (t instanceof DocumentException) {
				throw (DocumentException)t;
			}
			
			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

	}

	@Override
	public boolean allowsBank() {
		
		Object returnObj = null;
		try {
			returnObj = clp.invoke("allowsBank", new Object[] {});
		}catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);
			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
	}
			else {
				throw new RuntimeException(t.getClass().getName() + " is not a valid exception");
			}
		}
		
		return ((Boolean)returnObj);
	}

	
	@Override
	public boolean canBeLinked() {
		
		Object returnObj = null;
		try {
			returnObj = clp.invoke("canBeLinked", new Object[] {});
		}catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);
			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
	}
			else {
				throw new RuntimeException(t.getClass().getName() + " is not a valid exception");
			}
		}
		
		return ((Boolean)returnObj);
	}
	
	@Override
	public boolean isDone(LearningActivity learningActivity, long userId)
			throws SystemException, PortalException {
		Object returnObj = null;
		try {
			ClassLoader classLoader = clp.getClassLoader();
			Class learningActivityClass = Class.forName(LearningActivity.class.getName(),true, classLoader);
			MethodKey isDoneMethod = new MethodKey(clp.getClassName(), "isDone", learningActivityClass,Long.class);    
			Object learningActivityObj = translateLearningActivity(learningActivity);
			returnObj = clp.invoke(new MethodHandler(isDoneMethod, learningActivityObj,userId));
			ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(learningActivityObj, clp.getClassLoader());
			learningActivity.setModelAttributes((Map<String, Object>) classLoaderProxy.invoke("getModelAttributes", new Object[]{}));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);
			
			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}
			
			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
		return ((Boolean)returnObj);
	}
	@Override
	public boolean allowsDeleteBank() {
		Object returnObj = null;
		try {
			returnObj = clp.invoke("allowsDeleteBank", new Object[] {});
		}catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);
			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() + " is not a valid exception");
			}
		}
		
		return ((Boolean)returnObj);
	}

	@Override
	public boolean canBeSeenResults() {
		Object returnObj = null;
		try {
			returnObj = clp.invoke("canBeSeenResults", new Object[] {});
		}catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);
			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() + " is not a valid exception");
			}
		}
		
		return ((Boolean)returnObj);
	}
	
	@Override
	public boolean isAutoCorrect() {
		Object returnObj = null;
		try {
			returnObj = clp.invoke("isAutoCorrect", new Object[] {});
		}catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);
			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() + " is not a valid exception");
			}
		}
		
		return ((Boolean)returnObj);
	}

	@Override
	public String importExtraContent(LearningActivity newLarn, Long userId,
			PortletDataContext context, ServiceContext serviceContext,Element actElement) throws PortalException, IOException, DocumentException, SystemException {
		try {
			ClassLoader classLoader = clp.getClassLoader();
			Class learningActivityClass = Class.forName(LearningActivity.class.getName(),true, classLoader);
			MethodKey importExtraContentMethod = new MethodKey(clp.getClassName(), "importExtraContent", learningActivityClass, Long.class , PortletDataContext.class, ServiceContext.class, Element.class);
			Object learningActivityObj = translateLearningActivity(newLarn);
			clp.invoke(new MethodHandler(importExtraContentMethod, learningActivityObj, userId, context,serviceContext, actElement));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);
			
			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}
			
			if (t instanceof IOException) {
				throw (IOException)t;
			}
			
			if (t instanceof DocumentException) {
				throw (DocumentException)t;
			}
			
			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
		return null;

	}

	@Override
	public String addZipEntry(LearningActivity actividad, Long assetEntryId,PortletDataContext context, Element entryElementLoc)
			throws PortalException, SystemException {
		try {			
			log.info(":::::addZipEntry:::::");
			ClassLoader classLoader = clp.getClassLoader();
			Class learningActivityClass = Class.forName(LearningActivity.class.getName(),true, classLoader);
			MethodKey addZipEntryMethod = new MethodKey(clp.getClassName(), "addZipEntry", learningActivityClass, Long.class , PortletDataContext.class, Element.class);
			Object learningActivityObj = translateLearningActivity(actividad);
			log.info(":::::invoke:::::");
			clp.invoke(new MethodHandler(addZipEntryMethod, learningActivityObj, assetEntryId, context, entryElementLoc));
			log.info(":::::invoked:::::");
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);
			
			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}			
			
			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
		return null;
		
	}
	
	@Override
	public long calculateResult(LearningActivity learningActivity, LearningActivityTry lat){
		
		Object returnObj = null;

		try {
			ClassLoader classLoader = clp.getClassLoader();
			Class learningActivityClass = Class.forName(LearningActivity.class.getName(),true, classLoader);
			Class learningActivityTryClass = Class.forName(LearningActivityTry.class.getName(),true, classLoader);
			MethodKey especificValidationsMethod = new MethodKey(clp.getClassName(), "calculateResult", learningActivityClass, learningActivityTryClass);		    
			returnObj = clp.invoke(new MethodHandler(especificValidationsMethod, lat));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				t.printStackTrace();
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
		return ((Long)returnObj).longValue();	
	}

}
