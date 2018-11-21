package com.liferay.lms.views;

import java.util.ArrayList;
import java.util.List;

import com.liferay.lms.model.Competence;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseCompetence;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.service.CompetenceLocalServiceUtil;
import com.liferay.lms.service.CourseCompetenceLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;

public class CourseResultView {
	
	public static final int STATUS_NOT_ATTEMPED = 0;
	public static final int STATUS_INCOMPLETE = 1;
	public static final int STATUS_FAILED = 2;
	public static final int STATUS_PASSED = 3;

	private CourseView course;
	private boolean passed;
	private long result;
	private int statusUser;
	private List<Long> competenceIdList;
	
	public CourseResultView(Course course, CourseResult courseResult, ThemeDisplay themeDisplay){
		setCourse(new CourseView(course, themeDisplay));
		if(courseResult != null){
			setPassed(courseResult.isPassed());
			setResult(courseResult.getResult());
			List<Long> competenceIdList = new ArrayList<Long>();
			if (courseResult.isPassed()) {
				// Obtener las competencias asignadas al superar el curso
				List<CourseCompetence> courseCompetenceList = new ArrayList<CourseCompetence>();
				try {
					courseCompetenceList = CourseCompetenceLocalServiceUtil.findBycourseId(course.getCourseId(), Boolean.FALSE);
					for(CourseCompetence courseCompetence : courseCompetenceList) {
						Competence competence = null;
						try {
							competence = CompetenceLocalServiceUtil.fetchCompetence(courseCompetence.getCompetenceId());
						} catch (Exception e) {
							
						}
						if (Validator.isNotNull(competence) && competence.getGenerateCertificate()) {
							competenceIdList.add(competence.getCompetenceId());
						}
					}
				} catch (Exception e) {
					
				}
			}
			setCompetenceIdList(competenceIdList);
		}else{
			setPassed(false);
			setResult(0);
			setCompetenceIdList(new ArrayList<Long>());
		}
	}
	
	public CourseResultView(CourseView courseView, long result, int statusUser){
		setCourse(courseView);
		setResult(result);
		setStatusUser(statusUser);
		setPassed(statusUser == STATUS_PASSED);

		List<Long> competenceIdList = new ArrayList<Long>();
		if (statusUser == STATUS_PASSED) {
			// Obtener las competencias asignadas al superar el curso
			List<CourseCompetence> courseCompetenceList = new ArrayList<CourseCompetence>();
			try {
				courseCompetenceList = CourseCompetenceLocalServiceUtil.findBycourseId(course.getCourseId(), Boolean.FALSE);
				for(CourseCompetence courseCompetence : courseCompetenceList) {
					Competence competence = null;
					try {
						competence = CompetenceLocalServiceUtil.fetchCompetence(courseCompetence.getCompetenceId());
					} catch (Exception e) {
						
					}
					if (Validator.isNotNull(competence) && competence.getGenerateCertificate()) {
						competenceIdList.add(competence.getCompetenceId());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		setCompetenceIdList(competenceIdList);
	}
	
	public CourseView getCourse() {
		return course;
	}
	
	public void setCourse(CourseView course) {
		this.course = course;
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public long getResult() {
		return result;
	}

	public void setResult(long result) {
		this.result = result;
	}

	public int getStatusUser() {
		return statusUser;
	}

	public void setStatusUser(int statusUser) {
		this.statusUser = statusUser;
	}

	public List<Long> getCompetenceIdList() {
		return competenceIdList;
	}

	public void setCompetenceIdList(List<Long> competenceIdList) {
		this.competenceIdList = competenceIdList;
	}

}
