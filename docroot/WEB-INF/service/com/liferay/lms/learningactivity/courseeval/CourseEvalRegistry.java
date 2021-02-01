package com.liferay.lms.learningactivity.courseeval;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;


public class CourseEvalRegistry 
{
	
	protected static final String LMS_ACTIVITIES_LIST_PORTLET_ID =  PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());	
	
	private static CourseEval[] _getCourseEvals(){
		Properties properties = PropsUtil.getProperties("lms.course.courseeval", true);
		CourseEval[] courseEvals = new CourseEval[properties.size()];
		int currentCourseEval = 0;
		for (Object key:properties.keySet()) {
			String type=properties.getProperty(key.toString());
			try {	
				CourseEval courseEval = (CourseEval)getPortletClassLoader().loadClass(type).newInstance();
				courseEvals[currentCourseEval++]=courseEval;
			} catch (ClassNotFoundException e) {
				try {
					String [] context = ((String) key).split("\\.");
					if (Validator.isNotNull(context) && context.length == 2) {
						context[1] = LMS_ACTIVITIES_LIST_PORTLET_ID;
					}
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(type, true, 
						PortletClassLoaderUtil.getClassLoader(context[1])).newInstance(), type, 
						PortletClassLoaderUtil.getClassLoader(context[1]));
					courseEvals[currentCourseEval++]=new CourseEvalClp(classLoaderProxy);
				} catch (Throwable throwable) {
				}
			} catch (ClassCastException e) {
				try {
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(type, true, 
						getPortletClassLoader()).newInstance(), type, 
						getPortletClassLoader());
					courseEvals[currentCourseEval++]=new CourseEvalClp(classLoaderProxy);
				} catch (Throwable throwable) {
				}
			} catch (Throwable throwable) {
			}
		}
		
		if(properties.size()==currentCourseEval) {
			return courseEvals;
		}
		else {
			return Arrays.copyOf(courseEvals,currentCourseEval);
		}
	}
	
	private static ClassLoader _portletClassLoader;
	private static ClassLoader getPortletClassLoader() {
		if(_portletClassLoader==null) {
			ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
			if(currentClassLoader.equals(PortalClassLoaderUtil.getClassLoader())) {
				_portletClassLoader=PortletClassLoaderUtil.getClassLoader(LMS_ACTIVITIES_LIST_PORTLET_ID);
			}
			else {
				_portletClassLoader=currentClassLoader;
			}
		}
		return _portletClassLoader;
	}
	
	public CourseEvalRegistry() {
		_courseEvals =  _courseEvalThreadLocal.get();
		if((Validator.isNull(_courseEvals))||
			(_courseEvals.isEmpty())||
			(!(_courseEvals.get(0) instanceof CourseEval))) {
				CourseEval[] courseEvals = _getCourseEvals();
			_courseEvals=new UnmodifiableList<CourseEval>(Arrays.asList(courseEvals));
			_courseEvalThreadLocal.set(_courseEvals);
			
		}
	}
		
	public CourseEval getCourseEval(long typeId) {
		for (CourseEval courseEval : _courseEvals) {
			if(courseEval.getTypeId()==typeId){
				return courseEval;
			}
		}	
		return null;
	}

	public List<CourseEval> getCourseEvals() {
		return _courseEvals;
	}

	private List<CourseEval> _courseEvals;
	
	private static ThreadLocal<List<CourseEval>>
		_courseEvalThreadLocal =
			new AutoResetThreadLocal<List<CourseEval>>(
					CourseEvalRegistry.class + "._courseEvalThreadLocal");
}
