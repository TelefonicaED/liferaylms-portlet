package com.liferay.lms.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PrefsPropsUtil;

public class LmsPrefsPropsValues {
	
	public static boolean getUsersExtendedData(long companyId) {
		boolean usersExtendedData = LmsConstant.PREFS_USERS_EXTENDED_DATA_DEFAULT;
		try {
			usersExtendedData = PrefsPropsUtil.getBoolean(companyId, LmsConstant.PREFS_USERS_EXTENDED_DATA, LmsPropsValues.USERS_EXTENDED_DATA);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return usersExtendedData;
	}
}
