package com.liferay.lms.course.adminaction;

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

public class AdminActionTypeClp implements AdminActionType {

	private ClassLoaderProxy clp;
	
	public AdminActionTypeClp(ClassLoaderProxy clp) {
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
	
	public String getName(Locale locale){
		Object returnObj = null;

		try {
			MethodKey getTitleMethod = new MethodKey(clp.getClassName(), "getName", Locale.class);
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
	
	
	public String getHelpMessage(Locale locale){
		Object returnObj = null;

		try {
			MethodKey getTitleMethod = new MethodKey(clp.getClassName(), "getHelpMessage", Locale.class);
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
	
	
	public boolean hasPermission(long userId){
		Object returnObj = null;

		try {
			MethodKey getTitleMethod = new MethodKey(clp.getClassName(), "hasPermission", long.class);
			returnObj = clp.invoke(new MethodHandler(getTitleMethod, userId));
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

	
	public String getIcon(){
		Object returnObj = null;

		try {
			MethodKey getSuffixMethod = new MethodKey(clp.getClassName(), "getIcon"); 
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
	
	
	
	
}
