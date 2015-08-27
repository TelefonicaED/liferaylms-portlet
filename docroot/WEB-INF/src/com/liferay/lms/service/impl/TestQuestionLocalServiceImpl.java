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

import java.util.List;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.learningactivity.questiontype.QuestionType;
import com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.base.TestQuestionLocalServiceBaseImpl;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;

/**
 * The implementation of the test question local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.TestQuestionLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.TestQuestionLocalServiceUtil} to access the test question local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.TestQuestionLocalServiceBaseImpl
 * @see com.liferay.lms.service.TestQuestionLocalServiceUtil
 */
public class TestQuestionLocalServiceImpl
	extends TestQuestionLocalServiceBaseImpl {
	public void importXML(long actId, Document document) throws DocumentException, SystemException, PortalException
	{
		Element rootElement = document.getRootElement();
		for(Element question:rootElement.elements("question"))
		{
			importXMLQuestion(actId,question);
		}
		
	}
	public void importXMLQuestion(long actId, Element question) throws SystemException, PortalException {
		long type = getQuestionType(question);
		if(type != -1){
			QuestionType qt = new QuestionTypeRegistry().getQuestionType(type);
			qt.importXML(actId, question, testAnswerLocalService);
		}
	}
	private long getQuestionType(Element question) {
		long type = -1;
		if("multichoice".equals(question.attributeValue("type")) && "true".equals(question.element("single").getText())) type = 0;
		else if("multichoice".equals(question.attributeValue("type")) && "false".equals(question.element("single").getText())) type = 1;
		else if("essay".equals(question.attributeValue("type")) || "numerical".equals(question.attributeValue("type")) || "shortanswer".equals(question.attributeValue("type"))) type = 2;
		else if("cloze".equals(question.attributeValue("type"))) type = 3;
		else if("draganddrop".equals(question.attributeValue("type"))) type = 4;
		else if("sort".equals(question.attributeValue("type"))) type = 5;
		return type;
	}
	public TestQuestion addQuestion(long actId,String text,long questionType) throws SystemException
	{
		TestQuestion tq =
			testQuestionPersistence.create(counterLocalService.increment(
					TestQuestion.class.getName()));
		tq.setText(text);
		tq.setQuestionType(questionType);
		tq.setActId(actId);
		tq.setWeight(tq.getQuestionId());
		testQuestionPersistence.update(tq, false);
		return tq;
	}
	public java.util.List<TestQuestion> getQuestions(long actid) throws SystemException
	{
	 return testQuestionPersistence.findByac(actid);
	}
	
	public QuestionType initializeQuestionType(long questionType){
		QuestionType qt = null;
		try {
			qt = new QuestionTypeRegistry().getQuestionType(questionType);
		} catch (SystemException e) {}
		return qt;
	}
	public TestQuestion updateQuestionWithoutWeight(long questionId) throws SystemException{
		TestQuestion tq = testQuestionPersistence.fetchByPrimaryKey(questionId);
		if(tq.getWeight()==0){
			tq.setWeight(tq.getQuestionId());
		}
		testQuestionPersistence.update(tq, false);
		return tq;
	}
	public TestQuestion getPreviusTestQuestion(long questionId) throws SystemException
	{
		TestQuestion theTestQuestion=testQuestionPersistence.fetchByPrimaryKey(questionId);
		return getPreviusTestQuestion(theTestQuestion);
	}

	public TestQuestion getPreviusTestQuestion(TestQuestion theQuestion) throws SystemException {
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(TestQuestion.class, classLoader);
		if(theQuestion.getWeight()==0){
			theQuestion.setWeight(theQuestion.getQuestionId());
			Criterion criterion=PropertyFactoryUtil.forName("actId").eq(theQuestion.getActId());
			dq.add(criterion);
			criterion = PropertyFactoryUtil.forName("questionId").gt(theQuestion.getQuestionId());
			Order createOrder = OrderFactoryUtil.getOrderFactory().asc("questionId");
			dq.addOrder(createOrder);
		}
		else{
			Criterion criterion=PropertyFactoryUtil.forName("weight").lt(theQuestion.getWeight());
			dq.add(criterion);
			criterion=PropertyFactoryUtil.forName("actId").eq(theQuestion.getActId());
			dq.add(criterion);
			Order createOrder=OrderFactoryUtil.getOrderFactory().desc("weight");
			dq.addOrder(createOrder);
		}
		java.util.List<TestQuestion> questionsp=(java.util.List<TestQuestion>)testQuestionLocalService.dynamicQuery(dq,0,1);
		if(questionsp!=null&& questionsp.size()>0)
		{
			return questionsp.get(0);
		}
		else
		{
			return null;
		}
	}
	public TestQuestion getNextTestQuestion(long testQuestionId) throws SystemException
	{
		TestQuestion theTestQuestion=testQuestionPersistence.fetchByPrimaryKey(testQuestionId);
		return getNextTestQuestion(theTestQuestion);
	}

	public TestQuestion getNextTestQuestion(TestQuestion theTestQuestion) throws SystemException {
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(TestQuestion.class, classLoader);
		if(theTestQuestion.getWeight()==0){
			theTestQuestion.setWeight(theTestQuestion.getQuestionId());
			Criterion criterion=PropertyFactoryUtil.forName("actId").eq(theTestQuestion.getActId());
			dq.add(criterion);
			criterion = PropertyFactoryUtil.forName("questionId").gt(theTestQuestion.getQuestionId());
			Order createOrder = OrderFactoryUtil.getOrderFactory().asc("questionId");
			dq.addOrder(createOrder);
		}
		else{
			Criterion criterion=PropertyFactoryUtil.forName("weight").gt(theTestQuestion.getWeight());
			dq.add(criterion);
			criterion=PropertyFactoryUtil.forName("actId").eq(theTestQuestion.getActId());
			dq.add(criterion);
			Order createOrder=OrderFactoryUtil.getOrderFactory().asc("weight");
			dq.addOrder(createOrder);
		}
		java.util.List<TestQuestion> questionsp=(java.util.List<TestQuestion>)testQuestionLocalService.dynamicQuery(dq,0,1);
		if(questionsp!=null&& questionsp.size()>0)
		{
			return questionsp.get(0);
		}
		else
		{
			return null;
		}
	}

	public void goUpTestQuestion(long testQuestionId ) throws SystemException
	{
		TestQuestion previusQuestion=getPreviusTestQuestion(testQuestionId);
		if(previusQuestion!=null)
		{
			
			TestQuestion theQuestion=testQuestionPersistence.fetchByPrimaryKey(testQuestionId);
			long priority=theQuestion.getWeight();
			if(priority ==0){
				priority=theQuestion.getQuestionId();
			}
			if(previusQuestion.getWeight()==0){
				previusQuestion.setWeight(previusQuestion.getQuestionId());
			}
			theQuestion.setWeight(previusQuestion.getWeight());
			previusQuestion.setWeight(priority);
			testQuestionPersistence.update(theQuestion, true);
			testQuestionPersistence.update(previusQuestion, true);
			LearningActivity larn;
			try {
				larn = learningActivityLocalService.getLearningActivity(theQuestion.getActId());
				//auditing
				AuditingLogFactory.audit(larn.getCompanyId(), larn.getGroupId(), TestQuestion.class.getName(), 
						testQuestionId, larn.getUserId(), AuditConstants.UPDATE, null);

			} catch (PortalException e) {
			}	
		}
		
	}
	public void goDownTestQuestion(long testQuestionId ) throws SystemException
	{
		TestQuestion nextTestQuestion=getNextTestQuestion(testQuestionId);
		if(nextTestQuestion!=null)
		{
			TestQuestion theTestQuestion=testQuestionPersistence.fetchByPrimaryKey(testQuestionId);
			long priority=theTestQuestion.getWeight();
			if(priority ==0){
				priority=theTestQuestion.getQuestionId();
			}
			if(nextTestQuestion.getWeight()==0){
				nextTestQuestion.setWeight(nextTestQuestion.getQuestionId());
			}
			theTestQuestion.setWeight(nextTestQuestion.getWeight());
			nextTestQuestion.setWeight(priority);
			testQuestionPersistence.update(theTestQuestion, true);
			testQuestionPersistence.update(nextTestQuestion, true);
			LearningActivity larn;
			try {
				larn = learningActivityLocalService.getLearningActivity(theTestQuestion.getActId());
				//auditing
				AuditingLogFactory.audit(larn.getCompanyId(), larn.getGroupId(), TestQuestion.class.getName(), 
						testQuestionId, larn.getUserId(), AuditConstants.UPDATE, null);

			} catch (PortalException e) {
			}
		}
		
	}
	
	public void moveQuestion(long questionId, long previusQuestion, long nextQuestion) throws SystemException {
		TestQuestion actualTestQuestion = (questionId>0)?testQuestionPersistence.fetchByPrimaryKey(questionId):null;
		TestQuestion finalPrevQuestion = (previusQuestion>0)?testQuestionPersistence.fetchByPrimaryKey(previusQuestion):null;
		TestQuestion finalNextQuestion = (nextQuestion>0)?testQuestionPersistence.fetchByPrimaryKey(nextQuestion):null;
		if(actualTestQuestion.getWeight()==0){
			actualTestQuestion.setWeight(actualTestQuestion.getQuestionId());
		}
		if(finalPrevQuestion!=null &&finalPrevQuestion.getWeight()==0){
			finalPrevQuestion.setWeight(finalPrevQuestion.getQuestionId());
		}
		if(finalNextQuestion!=null && finalNextQuestion.getWeight()==0){
			finalNextQuestion.setWeight(finalNextQuestion.getQuestionId());
		}
		//Elemento subido
		if(finalNextQuestion!=null && actualTestQuestion.getWeight() > finalNextQuestion.getWeight()){
			TestQuestion prevAct = getPreviusTestQuestion(actualTestQuestion);
			while(prevAct != null && actualTestQuestion.getWeight() > finalNextQuestion.getWeight()){
				goUpTestQuestion(questionId);
				actualTestQuestion = testQuestionPersistence.fetchByPrimaryKey(questionId);
				prevAct = getPreviusTestQuestion(actualTestQuestion);
			}
		//Elemento bajado
		}else if(finalPrevQuestion!=null && actualTestQuestion.getWeight() < finalPrevQuestion.getWeight()){
			TestQuestion nexMod = getNextTestQuestion(actualTestQuestion);
			while (nexMod != null && actualTestQuestion.getWeight() < finalPrevQuestion.getWeight()){
				goDownTestQuestion(questionId);
				actualTestQuestion = testQuestionPersistence.fetchByPrimaryKey(questionId);
				nexMod = getNextTestQuestion(actualTestQuestion);
			}
		}

	}
	public void checkWeights(long actId) throws PortalException, SystemException{
		List<TestQuestion> questions = testQuestionLocalService.getQuestions(actId);
		for(TestQuestion tq : questions){
			if(tq.getWeight()==0){
				tq.setWeight(tq.getQuestionId());
				testQuestionPersistence.update(tq, true);
			}
		}
	}
}