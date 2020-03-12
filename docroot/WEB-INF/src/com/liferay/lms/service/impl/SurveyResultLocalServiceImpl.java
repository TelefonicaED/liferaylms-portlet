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




import com.liferay.lms.NoSuchSurveyResultException;
import com.liferay.lms.model.SurveyResult;
import com.liferay.lms.service.base.SurveyResultLocalServiceBaseImpl;
import com.liferay.lms.service.persistence.SurveyResultFinderUtil;
import com.liferay.lms.service.persistence.SurveyResultUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;


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
	
	private static Log log = LogFactoryUtil.getLog(SurveyResultLocalServiceImpl.class);
	
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
	
	
	public long countStartedOnlyStudents(long actId, long companyId, long courseGroupCreatedId, List<User> students){
		return surveyResultFinder.countStartedOnlyStudents(actId, companyId, courseGroupCreatedId, students);
	}
	
	public List<SurveyResult> getSurveyResultsByQuestionIdActId(long questionId, long actId) { 
		List<SurveyResult> results = new ArrayList<SurveyResult>();
		try{
			results = SurveyResultUtil.findByQuestionIdActId(questionId, actId);
		}catch(SystemException e){
			e.printStackTrace();
			results=null;
		}
		return results;
	}
	
	
	
	public SurveyResult getSurveyResultByQuestionIdActIdLatId(long questionId, long actId, long latId) { 
		SurveyResult result = null;
		try{
			result = SurveyResultUtil.findByQuestionIdActIdLatId(questionId, actId, latId);
		}catch(SystemException e){
			e.printStackTrace();
		} catch (NoSuchSurveyResultException e) {
			log.info(e.getMessage());
		}
		return result;
	}
	
	public List<Long> getLearningActivityTriesByActId(long actId){
		List<Long> tries = new ArrayList<Long>();
		try{
			tries = SurveyResultFinderUtil.getTriesByActId(actId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return tries;
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
	
	
	public long getCountByQuestionIdAndAnswerId(long questionId, long answerId) throws SystemException
	{ 
		long count = 0;
		
		try {
			count = SurveyResultUtil.countByAnswerIdQuestionId(answerId, questionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
								
		return count;
	}
	
	/**
	 * Metodo depreciado, se utiliza una combinaci�n de los dos anteriores, para evitar consultas a base de datos innecesarias dentro de bucles.
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
	
	public long countStudentsByQuestionId(long questionId, long companyId, long courseGropupCreatedId){
		return SurveyResultFinderUtil.countStudentsByQuestionId(questionId, companyId, courseGropupCreatedId);
	}
	
	public long countByQuestionId(long questionId) throws SystemException{
		return surveyResultPersistence.countByQuestionId(questionId);
	}
	
	public long countStudentsByQuestionIdAndAnswerId(long questionId, long answerId, long companyId, long courseGropupCreatedId){
		return SurveyResultFinderUtil.countStudentsByQuestionIdAndAnswerId(questionId, answerId, companyId, courseGropupCreatedId);
	}


	public long countByQuestionIdAndAnswerId(long questionId, long answerId) throws SystemException{
		return surveyResultPersistence.countByAnswerIdQuestionId(answerId, questionId);
	}}