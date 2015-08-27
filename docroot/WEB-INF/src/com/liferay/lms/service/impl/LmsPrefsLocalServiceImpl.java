/**
 * 2012 TELEFONICA LEARNING SERVICES. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.lms.service.impl;

import com.liferay.lms.NoSuchPrefsException;
import com.liferay.lms.hook.events.StartupAction;
import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry;
import com.liferay.lms.learningactivity.courseeval.CourseEval;
import com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.base.LmsPrefsLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * The implementation of the lms prefs local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.LmsPrefsLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.LmsPrefsLocalServiceUtil} to access the lms prefs local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.LmsPrefsLocalServiceBaseImpl
 * @see com.liferay.lms.service.LmsPrefsLocalServiceUtil
 */
public class LmsPrefsLocalServiceImpl extends LmsPrefsLocalServiceBaseImpl 
{

	
	public LmsPrefs getLmsPrefsIni(long companyId) throws SystemException {
		LmsPrefs lmsPrefs=null;
		
		lmsPrefs=lmsPrefsPersistence.fetchByPrimaryKey(companyId);
		
		if(lmsPrefs==null)
		{
			StartupAction stAction=new StartupAction();
			try
			{
				stAction.createDefaultRoles(companyId);
				stAction.createDefaultSiteTemplate(companyId);
				lmsPrefs=lmsPrefsPersistence.create(companyId);
				lmsPrefs.setTeacherRole(stAction.courseTeacher.getRoleId());
				lmsPrefs.setEditorRole(stAction.courseEditor.getRoleId());
				lmsPrefs.setLmsTemplates(Long.toString(stAction.layoutSetPrototype.getLayoutSetPrototypeId()));
				lmsPrefs.setUsersResults(1000);
				lmsPrefsPersistence.update(lmsPrefs, true);
				setActivities(lmsPrefs);
				setScoreTranslators(lmsPrefs);
				setCourseEvals(lmsPrefs);
			} catch (PortalException e) 
			{
				throw new SystemException(e);
			} catch (RuntimeException e) 
			{
				throw new SystemException(e);
			}

		}		
		
		return lmsPrefs;
		
	}
	
	public LmsPrefs getStrictLmsPrefsIni(long companyId) throws PortalException, SystemException {
		return lmsPrefsPersistence.findByPrimaryKey(companyId);
	}

	private void setActivities(LmsPrefs lmsPrefs) throws SystemException {
		if(lmsPrefs.getActivities()==null ||lmsPrefs.getActivities().length()==0)
		{
			LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
			java.util.List<LearningActivityType> lats=learningActivityTypeRegistry.getLearningActivityTypes();
			java.util.Map<Long, LearningActivityType> mapLats = new java.util.HashMap<Long, LearningActivityType>();
			
			for (LearningActivityType lat : lats) {
				mapLats.put(lat.getTypeId(), lat);
			}
			
			java.util.List<Long> actids = new java.util.ArrayList<Long>();
			if (PropsUtil.contains("lms.learningactivity.order.default")) 
			{
				String [] defaultOrderArray = PropsUtil.getArray("lms.learningactivity.order.default");
				if (Validator.isNotNull(defaultOrderArray)) 
				{
					for (int i = 0; i < defaultOrderArray.length; i++) {
						String defaultOrderActIdString = defaultOrderArray[i];
						if (Validator.isNumber(defaultOrderActIdString)) {
							Long defaultOrderActId = Long.valueOf(defaultOrderActIdString);
							if (defaultOrderActId >= 0 && mapLats.get(defaultOrderActId) != null) {
								actids.add(defaultOrderActId);
							}
						}
					}
				}
			}
						
			for (int i = 0; i < lats.size(); i++)
			{
				Long typeId = lats.get(i).getTypeId();
				if (!actids.contains(typeId)) {
					actids.add(typeId);
				}				
			}
			lmsPrefs.setActivities(StringUtil.merge(actids));
			lmsPrefsPersistence.update(lmsPrefs, true);
		}
	}
	
	private void setScoreTranslators(LmsPrefs lmsPrefs) throws SystemException {
		if(lmsPrefs.getScoretranslators()==null || lmsPrefs.getScoretranslators().length()==0){
			CalificationTypeRegistry calificationTypeRegistry = new CalificationTypeRegistry();
			java.util.List<CalificationType> cals = calificationTypeRegistry.getCalificationTypes();
			Long[] calIds = new Long[cals.size()];
			for(int i=0;i<calIds.length;i++){
				calIds[i]=cals.get(i).getTypeId();
			}	
		}
	}
	
	private void setCourseEvals(LmsPrefs lmsPrefs) throws SystemException {
		if(lmsPrefs.getCourseevals()==null || lmsPrefs.getCourseevals().length()==0){
			CourseEvalRegistry courseEvalRegistry = new CourseEvalRegistry();
			java.util.List<CourseEval> cEvals = courseEvalRegistry.getCourseEvals();
			Long[]cEvalIds = new Long[cEvals.size()];
			for(int i=0;i<cEvalIds.length;i++){
				cEvalIds[i]=cEvals.get(i).getTypeId();
			}
		}
	}
	
	
}