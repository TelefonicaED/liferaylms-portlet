package com.liferay.lms.listeners;

import java.util.List;

import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.model.P2pActivityCorrections;
import com.liferay.lms.model.SurveyResult;
import com.liferay.lms.model.UserCompetence;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.lms.service.SurveyResultLocalServiceUtil;
import com.liferay.lms.service.UserCompetenceLocalServiceUtil;
import com.liferay.portal.ModelListenerException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.User;




public class UserListener extends BaseModelListener<User>  {

	private static Log log = LogFactoryUtil.getLog(UserListener.class);
	
	/* (non-Javadoc)
	 * @see com.liferay.portal.model.BaseModelListener#onAfterUpdate(com.liferay.portal.model.BaseModel)
	 */
	


	@Override
	public void onAfterRemove(User user) throws ModelListenerException {
		log.debug(":::: ON AFTER REMOVE USER...");

		log.debug("Se ha borrado el usuario:"+user.getScreenName());
		List<LearningActivityTry> tries = LearningActivityTryLocalServiceUtil.getByUserId(user.getUserId());
		for(LearningActivityTry lat:tries){
			log.debug("Borrando LearningActivityTry del user:"+user.getScreenName());
			try {
				LearningActivityTryLocalServiceUtil.deleteLearningActivityTry(lat);
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
		
		List<ModuleResult> moduleResults = ModuleResultLocalServiceUtil.getByUserId(user.getUserId());
		for(ModuleResult result:moduleResults){
			log.debug("Borrando ModuleResult del user:"+user.getScreenName());
			try {
				ModuleResultLocalServiceUtil.deleteModuleResult(result);
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
		
		List<LearningActivityResult> activityResults = LearningActivityResultLocalServiceUtil.getByUserId(user.getUserId());
		for(LearningActivityResult result:activityResults){
			log.debug("Borrando LearningActivityResult del user:"+user.getScreenName());
			try {
				LearningActivityResultLocalServiceUtil.deleteLearningActivityResult(result);
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
		
		List<CourseResult> courseResults = CourseResultLocalServiceUtil.getByUserId(user.getUserId());
		for(CourseResult result:courseResults){
			log.debug("Borrando CourseResult del user:"+user.getScreenName());
			try {
				CourseResultLocalServiceUtil.deleteCourseResult(result);
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
		
		List<SurveyResult> surveyResults = SurveyResultLocalServiceUtil.getByUserId(user.getUserId());
		for(SurveyResult result:surveyResults){
			log.debug("Borrando SurveyResult del user:"+user.getScreenName());
			try {
				SurveyResultLocalServiceUtil.deleteSurveyResult(result);
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
		
		List<UserCompetence> userCompetences = UserCompetenceLocalServiceUtil.findBuUserId(user.getUserId());
		for(UserCompetence userCompetence:userCompetences){
			log.debug("Borrando UserCompetence del user:"+user.getScreenName());
			try {
				UserCompetenceLocalServiceUtil.deleteUserCompetence(userCompetence);
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
		
		List<P2pActivity> activityies = P2pActivityLocalServiceUtil.findByUserId(user.getUserId());
		for(P2pActivity activity:activityies){
			log.debug("Borrando P2pActivity del user:"+user.getScreenName());
			try {
				P2pActivityLocalServiceUtil.deleteP2pActivity(activity);
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
		
		List<P2pActivityCorrections> corrections = P2pActivityCorrectionsLocalServiceUtil.getByUserId(user.getUserId());
		for(P2pActivityCorrections correction:corrections){
			log.debug("Borrando P2pActivityCorrections del user:"+user.getScreenName());
			try {
				P2pActivityCorrectionsLocalServiceUtil.deleteP2pActivityCorrections(correction);
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
		
		log.debug(":::: FIN ON AFTER REMOVE USER...");
	}

}