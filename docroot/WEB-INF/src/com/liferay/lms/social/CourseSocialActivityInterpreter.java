package com.liferay.lms.social;

import com.liferay.lms.model.Course;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;

public class CourseSocialActivityInterpreter extends
		BaseSocialActivityInterpreter {
	static String[] classnames={Course.class.getName()};
	@Override
	public String[] getClassNames() {
		
		return classnames;
	}

	@Override
	protected SocialActivityFeedEntry doInterpret(SocialActivity activity,
			ThemeDisplay themeDisplay) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
