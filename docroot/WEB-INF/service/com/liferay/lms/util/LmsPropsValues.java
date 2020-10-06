package com.liferay.lms.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

public class LmsPropsValues {
	public static final boolean USERS_EXTENDED_DATA = GetterUtil.getBoolean(PropsUtil.get(LmsConstant.PREFS_USERS_EXTENDED_DATA));
}
