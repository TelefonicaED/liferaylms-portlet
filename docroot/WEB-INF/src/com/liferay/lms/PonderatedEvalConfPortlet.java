package com.liferay.lms;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;


public class PonderatedEvalConfPortlet extends MVCPortlet 
{
 static String evalclassNam="com.liferay.lms.learningactivity.courseeval.PonderatedCourseEval";
	public void savePonderation(ActionRequest actionRequest, ActionResponse actionResponse)  throws PortletException, IOException, SystemException 
	{
		ThemeDisplay themeDisplay  =(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
		Enumeration<String> paramse=actionRequest.getParameterNames();
		java.util.List<String> paramsl=Collections.list(paramse);
		paramsl=ListUtil.sort(paramsl);
		Document document = SAXReaderUtil.createDocument();
		Element rootElement = document.addElement("eval");
		long passpuntuation=ParamUtil.getLong(actionRequest, "passpuntuation", 0);
		Element passElement=rootElement.addElement("score");
		passElement.addAttribute("value", Long.toString(passpuntuation));
		
		rootElement.addElement("courseEval").setText(evalclassNam);		
		
		for(String param:paramsl)
		{
			if(param.startsWith("required_")&&!param.endsWith("ox"))
			{
				boolean required=ParamUtil.getBoolean(actionRequest, param,false);
				if(required)
				{
					String actIds=param.substring(9);
					Element requi=rootElement.addElement("required");
					requi.addAttribute("actId", actIds);
				}
			}
			if(param.startsWith("weight_"))
			{
				String actIds=param.substring(7);
				float score=ParamUtil.getFloat(actionRequest, param,0);
				if(score>0)
				{
					Element weight=rootElement.addElement("weight");
					weight.addAttribute("actId", actIds);
					weight.addAttribute("ponderation",Float.toString(score));
				}
			}
		}
		course.setCourseExtraData(document.formattedString());
		CourseLocalServiceUtil.updateCourse(course);
		
	}
}
