package com.liferay.lms.model;

import java.util.Locale;

import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

public class CourseTypeFactoryClp implements CourseTypeFactory {

	private ClassLoaderProxy clp;
	
	public CourseTypeFactoryClp(ClassLoaderProxy clp) {
		this.clp = clp;
	}
		
	public java.lang.Object invokeMethod(java.lang.String name,
			java.lang.String[] parameterTypes, java.lang.Object[] arguments)
			throws java.lang.Throwable {
		throw new UnsupportedOperationException();
	}
	
	public String getClassName(){
		Object returnObj = null;

		try {
			MethodKey getClassNameId = new MethodKey(clp.getClassName(), "getClassName");
			returnObj = clp.invoke(new MethodHandler(getClassNameId));
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
	
	public long getClassNameId(){
		Object returnObj = null;

		try {
			MethodKey getClassNameId = new MethodKey(clp.getClassName(), "getClassNameId");
			returnObj = clp.invoke(new MethodHandler(getClassNameId));
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
			MethodKey getClassNameId = new MethodKey(clp.getClassName(), "getPortletId");
			returnObj = clp.invoke(new MethodHandler(getClassNameId));
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
	public CourseTypeI getCourseType(Course course) throws PortalException, SystemException {
		Object returnObj = null;

		try {
			MethodKey getCourseType = new MethodKey(clp.getClassName(), "getCourseType", Course.class);
			returnObj = clp.invoke(new MethodHandler(getCourseType, course));
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

		return (CourseTypeI)returnObj;
	}

	@Override
	public long[] getCalificationTypes() {
		Object returnObj = null;

		try {
			MethodKey getCalificationTypes = new MethodKey(clp.getClassName(), "getCalificationTypes");
			returnObj = clp.invoke(new MethodHandler(getCalificationTypes));
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

		return (long[])returnObj;
	}

	@Override
	public long[] getCourseEvals() {
		Object returnObj = null;

		try {
			MethodKey getCourseEvals = new MethodKey(clp.getClassName(), "getCourseEvals");
			returnObj = clp.invoke(new MethodHandler(getCourseEvals));
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

		return (long[])returnObj;
	}

	@Override
	public long[] getInscriptionTypes() {
		Object returnObj = null;

		try {
			MethodKey getInscriptionTypes = new MethodKey(clp.getClassName(), "getInscriptionTypes");
			returnObj = clp.invoke(new MethodHandler(getInscriptionTypes));
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

		return (long[])returnObj;
	}

	@Override
	public long[] getLearningActivities() {
		Object returnObj = null;

		try {
			MethodKey getLearningActivities = new MethodKey(clp.getClassName(), "getLearningActivities");
			returnObj = clp.invoke(new MethodHandler(getLearningActivities));
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

		return (long[])returnObj;
	}

	@Override
	public long[] getTemplates() {
		Object returnObj = null;

		try {
			MethodKey getTemplates = new MethodKey(clp.getClassName(), "getTemplates");
			returnObj = clp.invoke(new MethodHandler(getTemplates));
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

		return (long[])returnObj;
	}

	@Override
	public long[] getEditionTemplates() {
		Object returnObj = null;

		try {
			MethodKey getEditionTemplates = new MethodKey(clp.getClassName(), "getEditionTemplates");
			returnObj = clp.invoke(new MethodHandler(getEditionTemplates));
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

		return (long[])returnObj;
	}
	
	
}
