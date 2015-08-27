package com.liferay.lms.social;

import com.liferay.lms.model.SCORMContent;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;

public class ScormContentActivityInterpreter extends
	BaseSocialActivityInterpreter {
	static String[] classnames={SCORMContent.class.getName()};
	@Override
	public String[] getClassNames() {
		return classnames;
	}

	@Override
	protected SocialActivityFeedEntry doInterpret(SocialActivity activity,
			ThemeDisplay themeDisplay) throws Exception {
		return null;
	}
}
