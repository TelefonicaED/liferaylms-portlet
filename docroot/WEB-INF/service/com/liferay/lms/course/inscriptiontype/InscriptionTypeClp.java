package com.liferay.lms.course.inscriptiontype;

import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.service.ServiceContext;

public class InscriptionTypeClp implements InscriptionType {

	private ClassLoaderProxy clp;
	
	public InscriptionTypeClp(ClassLoaderProxy clp) {
		this.clp = clp;
	}
		
	public java.lang.Object invokeMethod(java.lang.String name,
			java.lang.String[] parameterTypes, java.lang.Object[] arguments)
			throws java.lang.Throwable {
		throw new UnsupportedOperationException();
	}
	
	@SuppressWarnings("deprecation")
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
	
	public String getTitle(Locale locale){
		Object returnObj = null;

		try {
			MethodKey getTitleMethod = new MethodKey(clp.getClassName(), "getTitle", Locale.class);
			returnObj = clp.invoke(new MethodHandler(getTitleMethod, locale));
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
			MethodKey getDescriptionMethod = new MethodKey(clp.getClassName(), "getDescription", Locale.class); 
			returnObj = clp.invoke(new MethodHandler(getDescriptionMethod, locale));
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
	
	public String getPortletId(){
		Object returnObj = null;

		try {
			MethodKey getSuffixMethod = new MethodKey(clp.getClassName(), "getPortletId"); 
			returnObj = clp.invoke(new MethodHandler(getSuffixMethod));
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

	public String enrollUser(long courseId, long userId, long teamId, ServiceContext serviceContext) throws PortalException, SystemException {
		Object returnObj = null;

		try {
			MethodKey translateMethod = new MethodKey(clp.getClassName(), "enrollUser", long.class, long.class, long.class, ServiceContext.class); 
			returnObj = clp.invoke(new MethodHandler(translateMethod, courseId, userId, teamId, serviceContext));
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

	public boolean unsubscribeUser(Course course, long userId) throws PortalException, SystemException{
		Object returnObj = null;

		try {
			MethodKey translateMethod = new MethodKey(clp.getClassName(), "unsubscribeUser", Course.class, long.class); 
			returnObj = clp.invoke(new MethodHandler(translateMethod, course, userId));
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

		return ((Boolean)returnObj);
	}
	
	public boolean canUnsubscribe() {
		Object returnObj = null;

		try {
			MethodKey translateMethod = new MethodKey(clp.getClassName(), "canUnsubscribe"); 
			returnObj = clp.invoke(new MethodHandler(translateMethod));
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

		return ((Boolean)returnObj);
	}

	@Override
	public String getSpecificContentPage() {
		Object returnObj = null;

		try {
			MethodKey getExpecificContentPageMethod = new MethodKey(clp.getClassName(), "getSpecificContentPage"); 
			returnObj = clp.invoke(new MethodHandler(getExpecificContentPageMethod));
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

	@Override
	public String setExtraContent(UploadRequest uploadRequest,PortletResponse portletResponse,Course course) {
		Object returnObj = null;

		try {
			MethodKey setExtraContentMethod = new MethodKey(clp.getClassName(), "setExtraContent", UploadRequest.class, PortletResponse.class, Course.class); 
			returnObj = clp.invoke(new MethodHandler(setExtraContentMethod, uploadRequest, portletResponse, course));
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

	@Override
	public Set<Integer> getGroupTypesAvailable() {
		Object returnObj = null;

		try {
			MethodKey getGroupTypesAvailableMethod = new MethodKey(clp.getClassName(), "getGroupTypesAvailable"); 
			returnObj = clp.invoke(new MethodHandler(getGroupTypesAvailableMethod));
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

		return ((Set<Integer>)returnObj);
	}
	
	
	@Override
	public boolean isActive(long companyId) {
		Object returnObj = null;

		try {
			MethodKey isActiveMethod = new MethodKey(clp.getClassName(), "isActive", long.class); 
			returnObj = clp.invoke(new MethodHandler(isActiveMethod, companyId));
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

		return ((Boolean)returnObj);
	}
	
	
}
