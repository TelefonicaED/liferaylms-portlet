/**
 * 2013 TELEFONICA LEARNING SERVICES. All rights reserved.
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

import java.util.List;

import com.liferay.lms.model.SurveyResult;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.SurveyResultLocalServiceUtil;
import com.liferay.lms.service.base.SurveyResultLocalServiceBaseImpl;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;


/**
 * The implementation of the survey result local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.SurveyResultLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.SurveyResultLocalServiceUtil} to access the survey result local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.SurveyResultLocalServiceBaseImpl
 * @see com.liferay.lms.service.SurveyResultLocalServiceUtil
 */
public class SurveyResultLocalServiceImpl
	extends SurveyResultLocalServiceBaseImpl {
	
	@SuppressWarnings("unchecked")
	public java.util.List<SurveyResult> getSurveyResultByActId(long actId) throws SystemException
	{ 
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(SurveyResult.class, classLoader)
				.add(PropertyFactoryUtil.forName("actId").eq(actId));
		
		return surveyResultPersistence.findWithDynamicQuery(query);
	}
	
	public double getPercentageByQuestionIdAndAnswerId(long questionId, long answerId) throws SystemException
	{ 
		double res = 0;
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(SurveyResult.class, classLoader)
				.add(PropertyFactoryUtil.forName("questionId").eq(new Long(questionId)))
				.add(PropertyFactoryUtil.forName("answerId").eq(new Long(answerId)))
				.setProjection(ProjectionFactoryUtil.count("answerId"));

		@SuppressWarnings("unchecked")
		List<Long> results = SurveyResultLocalServiceUtil.dynamicQuery(query);
		
		long total = getTotalAnswersByQuestionId(questionId);
		
		if(total > 0 && results.size()>0){
			res = results.get(0) / (double)total * 100;
		}
				
		return res;
	}
	
	public long getTotalAnswersByQuestionId(long questionId) throws SystemException
	{ 
		long res = new Long(0);

		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(SurveyResult.class, classLoader)
				.add(PropertyFactoryUtil.forName("questionId").eq(new Long(questionId)))
				.setProjection(ProjectionFactoryUtil.count("questionId"));
		
		@SuppressWarnings("unchecked")
		List<Long> results = SurveyResultLocalServiceUtil.dynamicQuery(query);
		
		if(results.size()>0)
			res = results.get(0);
		
		return res;
	}
}