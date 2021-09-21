package com.liferay.lms.learningactivity;

import java.util.HashMap;
import java.util.List;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.util.CourseCopyUtil;

public abstract class QuestionLearningActivityType extends BaseLearningActivityType{

	private static Log log = LogFactoryUtil.getLog(QuestionLearningActivityType.class);
	
	public abstract boolean canExportUserAnswers();
	
	@Override
	public void copyActivity(LearningActivity oldActivity, LearningActivity newActivity, ServiceContext serviceContext){
		try{
			List<TestQuestion> oldQuestions = TestQuestionLocalServiceUtil.getQuestions(oldActivity.getActId());
			TestQuestion newQuestion= null;
			List<TestAnswer> oldAnswers = null;
			TestAnswer newAnswer = null;
			HashMap<Long, Long> questionIdsMap = new HashMap<Long, Long>();
			
			for(TestQuestion oldQuestion:oldQuestions){
				try {
					newQuestion = TestQuestionLocalServiceUtil.getTestQuestion(oldQuestion.getUuid(), newActivity.getActId());
					if(newQuestion == null){
						newQuestion = TestQuestionLocalServiceUtil.addQuestion(newActivity.getActId(), oldQuestion.getText(), oldQuestion.getQuestionType());
						newQuestion.setUuid(oldQuestion.getUuid());
					}
					
					String newTestDescription = CourseCopyUtil.descriptionFilesClone(oldQuestion.getText(),newActivity.getGroupId(),serviceContext.getUserId());
					newQuestion.setText(newTestDescription);
					
					if(oldQuestion.getWeight() != oldQuestion.getQuestionId()){
						newQuestion.setWeight(oldQuestion.getWeight());
						questionIdsMap.put(oldQuestion.getQuestionId(), newQuestion.getQuestionId());
					}
					
					newQuestion.setExtracontent(oldQuestion.getExtracontent());
					newQuestion = TestQuestionLocalServiceUtil.updateTestQuestion(newQuestion, true);
					
					if(log.isDebugEnabled()){
						log.debug("      Test question : " + oldQuestion.getQuestionId() );
						log.debug("      + Test question : " + newQuestion.getQuestionId() );
						log.debug("      + Test question TEXT : " + newTestDescription );
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				
				oldAnswers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(oldQuestion.getQuestionId());
				for(TestAnswer oldAnswer:oldAnswers){
					try {
						newAnswer = TestAnswerLocalServiceUtil.getTestAnswer(oldAnswer.getUuid(), newActivity.getActId());
						if(newAnswer == null){
							newAnswer = TestAnswerLocalServiceUtil.addTestAnswer(newQuestion.getQuestionId(), oldAnswer.getAnswer(), oldAnswer.getFeedbackCorrect(), oldAnswer.getFeedbacknocorrect(), oldAnswer.isIsCorrect());
							newAnswer.setActId(newActivity.getActId());
						}
						newAnswer.setUuid(oldAnswer.getUuid());
						newAnswer.setAnswer(CourseCopyUtil.descriptionFilesClone(oldAnswer.getAnswer(),newActivity.getGroupId(), serviceContext.getUserId()));
						newAnswer.setFeedbackCorrect(oldAnswer.getFeedbackCorrect());
						newAnswer.setFeedbacknocorrect(oldAnswer.getFeedbacknocorrect());
						TestAnswerLocalServiceUtil.updateTestAnswer(newAnswer);
						if(log.isDebugEnabled()){
							log.debug("        Test answer : " + oldAnswer.getAnswerId());
							log.debug("        + Test answer : " + newAnswer.getAnswerId());	
						}
						
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			List<TestQuestion> newQuestions = TestQuestionLocalServiceUtil.getQuestions(newActivity.getActId());
			
			for(TestQuestion question:newQuestions){
				log.debug("orden anterior de la pregunta: " + question.getWeight());
				if(question.getWeight() > 0 && questionIdsMap.containsKey(question.getWeight())){
					log.debug("nuevo orden: " + questionIdsMap.get(question.getWeight()));
					question.setWeight(questionIdsMap.get(question.getWeight()));
					TestQuestionLocalServiceUtil.updateTestQuestion(question);
				}
			}
		} catch(SystemException e){
			e.printStackTrace();
		}
	
	}
}
