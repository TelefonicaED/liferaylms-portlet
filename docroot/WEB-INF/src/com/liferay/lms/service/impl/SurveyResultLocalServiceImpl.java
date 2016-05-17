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

import java.util.ArrayList;
import java.util.List;

import com.liferay.lms.model.SurveyResult;
import com.liferay.lms.service.base.SurveyResultLocalServiceBaseImpl;
import com.liferay.lms.service.persistence.SurveyResultUtil;
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
	
	public List<SurveyResult> getByUserId(long userId){ 
		List<SurveyResult> results = new ArrayList<SurveyResult>();
		try {
			results = SurveyResultUtil.findByUserId(userId);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public List<SurveyResult> getSurveyResultByActId(long actId) throws SystemException{ 
		
		return SurveyResultUtil.findByActId(actId);
	}
	
	public double getPercentageByQuestionIdAndAnswerId(long questionId, long answerId, long total) throws SystemException
	{ 
		double res = 0;
		long count = 0;
		
		try {
			count = SurveyResultUtil.countByAnswerIdQuestionId(answerId, questionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		if(total > 0 && count>0){
			res = count / (double)total * 100;
		}
				
		return res;
	}
	
	/**
	 * Metodo depreciado, se utiliza una combinación de los dos anteriores, para evitar consultas a base de datos innecesarias dentro de bucles.
	 * @deprecated
	 * @param questionId
	 * @param answerId
	 * @return
	 * @throws SystemException
	 */
	public double getPercentageByQuestionIdAndAnswerId(long questionId, long answerId) throws SystemException
	{ 
		double res = 0;
		long count = 0;
		long total = getTotalAnswersByQuestionId(questionId);
		
		try {
			count = SurveyResultUtil.countByAnswerIdQuestionId(answerId, questionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		if(total > 0 && count>0){
			res = count / (double)total * 100;
		}
				
		return res;
	}
	
	public long getTotalAnswersByQuestionId(long questionId)
			throws SystemException {
		long res = new Long(0);

		try {
			res = SurveyResultUtil.countByQuestionId(questionId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
}