package com.liferay.lms.learningactivity;

import java.io.IOException;
import java.io.Serializable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.xml.DocumentException;


public abstract class BaseLearningActivityType implements LearningActivityType, Serializable {

	@Override
	public String getMesageEditDetails() {
		return "edit-activity-details";
	}

	@Override
	public boolean allowsBank() {
		return false;
	}
	@Override
	public boolean allowsDeleteBank(){
		return true;
	}

	@Override
	public boolean isDone(LearningActivity learningActivity, long userId)throws SystemException, PortalException {
		LearningActivityResult lar=LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivity.getActId(), userId);
		if(lar==null)
		{
			return false;
		}
		else
		{
			return lar.getEndDate()!=null;
		}
	}

	@Override
	public boolean hasEditDetails() {
		return true;
	}

	@Override
	public boolean hasDeleteTries() {
		return false;
	}
	
	@Override
	public String getUrlIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean gradebook() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public long getDefaultScore() {		
		return 0;
	}

	@Override
	public long getDefaultTries() {
		return 0;
	}

	@Override
	public String getDefaultFeedbackCorrect() {
		
		return "";
	}

	@Override
	public String getDefaultFeedbackNoCorrect() {
		return "";
	}

	@Override
	public boolean isScoreConfigurable() {
		return false;
	}

	@Override
	public boolean isTriesConfigurable() {
		return false;
	}

	@Override
	public boolean isFeedbackCorrectConfigurable() {
		return false;
	}

	@Override
	public boolean isFeedbackNoCorrectConfigurable() {
		return false;
	}
	
	
	@Override
	public boolean hasMandatoryDates() {
		return false;
	}
	
	@Override
	public String getExpecificContentPage() {
		return null;
	}
	
	@Override
	public void setExtraContent(UploadRequest uploadRequest,PortletResponse portletResponse,LearningActivity learningActivity) throws PortalException,SystemException,DocumentException,IOException, NumberFormatException, Exception {
	}
	
	@Override
	public boolean especificValidations(UploadRequest uploadRequest,PortletResponse portletResponse) {
		return true;
	}
	
	@Override
	public void afterInsertOrUpdate(UploadRequest uploadRequest,PortletResponse portletResponse,LearningActivity learningActivity) throws PortalException,SystemException {
	}

	public Object invokeMethod(
			String name, String[] parameterTypes, Object[] arguments)
		throws Throwable {
		return this.invokeMethod(name, parameterTypes, arguments);
	}
	
	@Override
	public void deleteResources(ActionRequest actionRequest,ActionResponse actionResponse,LearningActivity larn) throws PortalException,SystemException,DocumentException,IOException{
	}

}
