package com.liferay.lms.clean;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

public class CleanLearningActivity {
	Log log = LogFactoryUtil.getLog(CleanLearningActivity.class);
	

	public void createInstance(long companyId,long groupId,long userId, long moduleId, long actId){
		
	}

	public void endInstance(){
		
	}
	
	public boolean processTry(LearningActivityTry lat){
	
		//Audit
		LearningActivityResult res= null;
		try {
			res = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(lat.getActId(), lat.getUserId());
		} catch (SystemException e) {
			e.printStackTrace();
		}
		

		LearningActivity larn = null;
		try {
			larn = LearningActivityLocalServiceUtil.getLearningActivity(lat.getActId());
		} catch (PortalException e) {
			
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		try {
			LearningActivityTryLocalServiceUtil.deleteUserTries(lat.getActId(), lat.getUserId());
		} catch (PortalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		if(res!=null){
			res.setResult(0);
			res.setPassed(false);
			res.setEndDate(null);
			try {
				LearningActivityResultLocalServiceUtil.updateLearningActivityResult(res);
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
		
		if(larn!=null&&larn.getWeightinmodule()>0){
			ModuleResult mr = null;
			try {
				mr = ModuleResultLocalServiceUtil.getByModuleAndUser(larn.getModuleId(), lat.getUserId());
			} catch (SystemException e) {
				if(log.isInfoEnabled())log.info(e.getMessage());
				if(log.isDebugEnabled())e.printStackTrace();
			}
			if(mr!=null){
				mr.setPassed(false);
				try {
					ModuleResultLocalServiceUtil.updateModuleResult(mr);
				} catch (SystemException e) {
					if(log.isInfoEnabled())log.info(e.getMessage());
					if(log.isDebugEnabled())e.printStackTrace();
				}
			}
		}
		
		return true;
	}

}