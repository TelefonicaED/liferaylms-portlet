package com.liferay.lms.course.diploma;

import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
//import java.lang.reflect.Method;

public class CourseDiplomaClp implements CourseDiploma {

	private static Log log = LogFactoryUtil.getLog(CourseDiplomaClp.class);
	private ClassLoaderProxy clp;
	
	public CourseDiplomaClp(ClassLoaderProxy clp) {
		this.clp = clp;
	}
		
	@Override
	public String getSpecificDiplomaContent(){
		Object returnObj = null;

		try {
			MethodKey getNameMethod = new MethodKey(clp.getClassName(), "getSpecificDiplomaContent");
			returnObj = clp.invoke(new MethodHandler(getNameMethod));
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
	public String getPortletId(){
		Object returnObj = null;

		try {
			MethodKey getNameMethod = new MethodKey(clp.getClassName(), "getPortletId");
			returnObj = clp.invoke(new MethodHandler(getNameMethod));
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
	public String saveDiploma(UploadRequest uploadRequest,Long courseId) throws Exception{
		Object returnObj = null;
		try {
			MethodKey getNameMethod = new MethodKey(clp.getClassName(), "saveDiploma", UploadRequest.class,  Long.class);
			returnObj = clp.invoke(new MethodHandler(getNameMethod,uploadRequest, courseId));
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
	public String copyCourseDiploma(long oldCourseId, long newCourseId) throws Exception{
		Object returnObj = null;
		try {
			MethodKey getNameMethod = new MethodKey(clp.getClassName(), "copyCourseDiploma", long.class,  long.class);
			returnObj = clp.invoke(new MethodHandler(getNameMethod,oldCourseId, newCourseId));
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
	public String updateUserDiploma(Long courseResutlId){
		Object returnObj = null;
		try {
			MethodKey getNameMethod = new MethodKey(clp.getClassName(), "updateUserDiploma", Long.class);
			returnObj = clp.invoke(new MethodHandler(getNameMethod, courseResutlId));
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
