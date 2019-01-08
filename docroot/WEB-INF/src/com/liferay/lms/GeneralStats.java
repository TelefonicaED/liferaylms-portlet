package com.liferay.lms;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class GeneralStats
 */
public class GeneralStats extends MVCPortlet {
	private static Log log = LogFactoryUtil.getLog(GeneralStats.class);
	
	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {
		String action = ParamUtil.getString(resourceRequest, "action");
		long[] courseIds=ParamUtil.getLongValues(resourceRequest, "courseIds");
		ThemeDisplay themeDisplay  =(ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		if(log.isDebugEnabled()){
			log.debug("::generalstats:: action :: " + action);
			log.debug("::generalstats:: courseIds.length :: " + courseIds.length);
		}
		
		if(action.equals("export")){
			try
			{
			String charset = LanguageUtil.getCharset(themeDisplay.getLocale());
			if (Validator.isNull(charset)) {
				charset = LanguageUtil.getCharset(LocaleUtil.getDefault());
			}
			charset = StringPool.UTF8;
			resourceResponse.setCharacterEncoding(charset);
			resourceResponse.setContentType("text/csv;charset="+charset);
			resourceResponse.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=generalstats."+Long.toString(System.currentTimeMillis())+".csv");
			if (StringPool.UTF8.equals(charset)) {
		        byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
		        resourceResponse.getPortletOutputStream().write(b);
			}
	        CSVWriter writer = new CSVWriter(new OutputStreamWriter(resourceResponse.getPortletOutputStream(),charset),';');
	        String[] linea=new String[8];
	        linea[0]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.name");
	        linea[1]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.registered");
	        linea[2]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.starts.course");
	        linea[3]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.ends.course");
	        linea[4]=LanguageUtil.get(themeDisplay.getLocale(),"closed");
	        linea[5]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.marks.average");
	        linea[6]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulecounter");
	        linea[7]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.activitiescounter");
	        writer.writeNext(linea);
	        long[] userExcludedIds = null;
	        for(long courseId:courseIds)
	        {
	        	Course course=CourseLocalServiceUtil.getCourse(courseId);
	        	userExcludedIds = CourseLocalServiceUtil.getTeachersAndEditorsIdsFromCourse(course);
		        linea=new String[8];
		        linea[0]=course.getTitle(themeDisplay.getLocale());
		        		        
				long registered=CourseLocalServiceUtil.countStudents(courseId, themeDisplay.getCompanyId(), null, null, null, null, false);
				long iniciados = (registered > 0) ? CourseResultLocalServiceUtil.countStudentsByCourseIdUserExcludedIdsStarted(courseId, userExcludedIds) : 0;
				long finalizados = (registered > 0) ? CourseResultLocalServiceUtil.countStudentsByCourseIdUserExcludedIdsFinished(courseId, userExcludedIds) : 0;
				double avgResult=0;
				if(finalizados>0){
					avgResult=CourseResultLocalServiceUtil.avgResultByCourseIdUserExcludedIds(course.getCourseId(), true, userExcludedIds);
				}
				long activitiesCount=LearningActivityLocalServiceUtil.countLearningActivitiesOfGroup(course.getGroupCreatedId());
				long modulesCount=ModuleLocalServiceUtil.countByGroupId(course.getGroupCreatedId());
				String closed=course.getClosed()?LanguageUtil.get(themeDisplay.getLocale(),"yes"):LanguageUtil.get(themeDisplay.getLocale(),"no");
				
				linea[1]=Long.toString(registered);
				linea[2]=Long.toString(iniciados);
				linea[3]=Long.toString(finalizados);
				DecimalFormat df = new DecimalFormat("#.#");
				linea[4]=closed;
				linea[5]=df.format(avgResult);
				linea[6]=Long.toString(modulesCount);
				linea[7]=Long.toString(activitiesCount);
		        writer.writeNext(linea);
		        //resourceResponse.getPortletOutputStream().write(b);
	        }
	        writer.flush();
			writer.close();
			resourceResponse.getPortletOutputStream().flush();
			resourceResponse.getPortletOutputStream().close();
			
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
