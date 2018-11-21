package com.liferay.lms.learningactivity.calificationtype;

import java.util.Locale;

import javax.portlet.PortletResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

public class CalificationTypeClp implements CalificationType {

	private ClassLoaderProxy clp;
	
	public CalificationTypeClp(ClassLoaderProxy clp) {
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
	
	@SuppressWarnings("deprecation")
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
	
	
	public String getSuffix(){
		Object returnObj = null;

		try {
			MethodKey getSuffixMethod = new MethodKey(clp.getClassName(), "getSuffix"); 
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
	
	public String getSuffix(long groupId){
		Object returnObj = null;

		try { 
			MethodKey getNameMethod = new MethodKey(clp.getClassName(), "getSuffix", long.class);
			returnObj = clp.invoke(new MethodHandler(getNameMethod, groupId));
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
	
	public String translate(Locale locale, double result){
		Object returnObj = null;

		try {
			MethodKey translateMethod = new MethodKey(clp.getClassName(), "translate", Locale.class, double.class); 
			returnObj = clp.invoke(new MethodHandler(translateMethod, locale, result));
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


	public String translate(Locale locale, CourseResult result) {
		Object returnObj = null;

		try {
			MethodKey translateMethod = new MethodKey(clp.getClassName(), "translate", Locale.class, CourseResult.class); 
			returnObj = clp.invoke(new MethodHandler(translateMethod, locale, result));
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


	public String translate(Locale locale, LearningActivityResult result) {
		Object returnObj = null;

		try {
			MethodKey translateMethod = new MethodKey(clp.getClassName(), "translate", Locale.class, LearningActivityResult.class); 
			returnObj = clp.invoke(new MethodHandler(translateMethod, locale, result));
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

	public String translate(Locale locale, ModuleResult result) {
		Object returnObj = null;

		try {
			MethodKey translateMethod = new MethodKey(clp.getClassName(), "translate", Locale.class, ModuleResult.class); 
			returnObj = clp.invoke(new MethodHandler(translateMethod, locale, result));
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

	public String translate(Locale locale, long groupId, double result) {
		Object returnObj = null;

		try {
			MethodKey translateMethod = new MethodKey(clp.getClassName(), "translate", Locale.class, long.class, double.class); 
			returnObj = clp.invoke(new MethodHandler(translateMethod, locale, groupId, result));
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
	public long toBase100(double result) {
		Object returnObj = null;

		try {
			MethodKey translateMethod = new MethodKey(clp.getClassName(), "toBase100", double.class); 
			returnObj = clp.invoke(new MethodHandler(translateMethod, result));
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

	@Override
	public long toBase100(long groupId, double result) {
		Object returnObj = null;

		try {
			MethodKey translateMethod = new MethodKey(clp.getClassName(), "toBase100", long.class, double.class); 
			returnObj = clp.invoke(new MethodHandler(translateMethod, groupId, result));
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
	
	@Override
	public long getMinValue(long groupId) {
		Object returnObj = null;

		try {
			MethodKey getNameMethod = new MethodKey(clp.getClassName(), "getMinValue", long.class);
			returnObj = clp.invoke(new MethodHandler(getNameMethod, groupId));
		}
		catch (Throwable t) {
			t.printStackTrace();
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

	@Override
	public long getMaxValue(long groupId) {
		Object returnObj = null;

		try {
			MethodKey getNameMethod = new MethodKey(clp.getClassName(), "getMaxValue", long.class);
			returnObj = clp.invoke(new MethodHandler(getNameMethod, groupId));
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

	@Override
	public String getExpecificContentPage() {
		Object returnObj = null;

		try {
			MethodKey getExpecificContentPageMethod = new MethodKey(clp.getClassName(), "getExpecificContentPage", Locale.class); 
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
	public String setExtraContent(UploadRequest uploadRequest, PortletResponse portletResponse, Course course) {
		Object returnObj = null;

		try {
			MethodKey getExpecificContentPageMethod = new MethodKey(clp.getClassName(), "setExtraContent", UploadRequest.class, PortletResponse.class, Course.class); 
			returnObj = clp.invoke(new MethodHandler(getExpecificContentPageMethod, uploadRequest, portletResponse, course));
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
