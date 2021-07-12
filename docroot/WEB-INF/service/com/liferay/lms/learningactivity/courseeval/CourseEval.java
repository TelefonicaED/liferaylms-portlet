package com.liferay.lms.learningactivity.courseeval;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.ModuleResult;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.service.ServiceContext;

public interface CourseEval 
{
	public void updateCourse(Course course, ModuleResult moduleResult) throws SystemException;
	public boolean updateCourse(Course course, long userId) throws SystemException;
	public boolean updateCourse(Course course) throws SystemException;
	public boolean hasModuleResultCalculator();
	public long calculateModuleResult(long moduleId,long userId) throws SystemException,PortalException;
	public String getName();
	public String getName(Locale locale);
	public long getTypeId();
	public boolean getNeedPassAllModules();
	public boolean getFailOnCourseCloseAndNotQualificated();
	public boolean getNeedPassPuntuation();
	public long getPassPuntuation(Course course) throws DocumentException;
    /**
     * Copia los datos propios de la evaluacion de un curso a otro
     * 
     * @param course curso del que se van a copiar los datos
     * @param newCourse nuevo curso
     * @param correlationModules map con la asociacion de los ids de los modulos del curso origen con los del nuevo
     *            curso
     * @param correlationActivities map con la asociacion de los ids de las actividades del curso origen con las del
     *            nuevo curso
     * @throws SystemException
     */
	public void cloneCourseEval(Course course, Course newCourse, HashMap<Long,Long> correlationModules, HashMap<Long,Long> correlationActivities) throws SystemException;
	public void cloneCourseEval(Course course, Course newCourse) throws SystemException;

	public String getExpecificContentPage();
	public void setExtraContent(Course course, String actionId, ServiceContext serviceContext) 
			throws PortalException, SystemException;
	public boolean especificValidations(UploadRequest uploadRequest,PortletResponse portletResponse);
	String getPortletId();
	
	public void onOpenCourse(Course course) throws SystemException;
	public void onCloseCourse(Course course) throws SystemException;

	public JSONObject getEvaluationModel(Course course)
			throws PortalException, SystemException, DocumentException,
			IOException;

	public void setEvaluationModel(Course course, JSONObject model)
				throws PortalException, SystemException, DocumentException,
				IOException;

}
