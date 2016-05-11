package com.liferay.lms.learningactivity.questiontype;

import java.util.Locale;

import javax.portlet.ActionRequest;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.TestAnswerLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;

public interface QuestionType 
{
	public void setLocale(Locale locale);
	public long getTypeId();
	public String getName();
	public String getTitle(Locale locale);
	public String getDescription(Locale locale);
	public String getURLEdit();
	public String getURLNew();
	public String getURLBack();
	public void delete(long questionId) throws PortalException, SystemException;
	public boolean correct(ActionRequest actionRequest, long questionId);
	public String getHtmlView(long questionId, ThemeDisplay themeDisplay, Document document);
	public Element getResults(ActionRequest actionRequest, long questionId);
	public String getHtmlFeedback(Document document,long questionId, ThemeDisplay themeDisplay);
	public void exportQuestionAnswers(PortletDataContext context, Element root, long questionId, LearningActivity activity) throws PortalException, SystemException;
	public void importQuestionAnswers(PortletDataContext context, Element entryElement, long questionId, long userId, ServiceContext serviceContext) throws SystemException, PortalException;
	public Element exportXML(long questionId);
	public void importXML(long questionId, Element question, TestAnswerLocalService testAnswerLocalService)throws SystemException, PortalException;
	public int getMaxAnswers();
	public int getDefaultAnswersNo();

}
