package com.tls.lms.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class LiferaylmsUtil {
	
	public static final int defaultStartYear = 2012; //porque es necesario mantener los valores antiguos para los cursos antiguos
	public static final int defaultEndYear = Calendar.getInstance().get(Calendar.YEAR) + 8;
    public static final String CHARSET_UTF_8        = "UTF-8".intern();
    public static final String CHARSET_X_ISO_10646_UCS_4_3412 = "X-ISO-10646-UCS-4-3412".intern(); // Malformed UTF-32
    public static final String CHARSET_UTF_16BE     = "UTF-16BE".intern();
    public static final String CHARSET_UTF_16LE     = "UTF-16LE".intern();
    public static final String CHARSET_UTF_32BE     = "UTF-32BE".intern();
    public static final String CHARSET_UTF_32LE     = "UTF-32LE".intern();
    public static final String CHARSET_X_ISO_10646_UCS_4_2143 = "X-ISO-10646-UCS-4-2143".intern();

	public static void setPermission(ThemeDisplay themeDisplay, String classname, Role role, String[] actionIds, long primaryKey) throws Exception{
		Resource resource =  ResourceLocalServiceUtil.getResource(themeDisplay.getCompanyId(), classname, ResourceConstants.SCOPE_INDIVIDUAL, Long.toString(primaryKey));
		if(ResourceBlockLocalServiceUtil.isSupported(classname)){
			Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();
			roleIdsToActionIds.put(role.getRoleId(), actionIds);
			ResourceBlockServiceUtil.setIndividualScopePermissions(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), resource.getName(),
					primaryKey, roleIdsToActionIds);
		}else{
			Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();
			roleIdsToActionIds.put(role.getRoleId(), actionIds);
			ResourcePermissionServiceUtil.setIndividualResourcePermissions(
					themeDisplay.getScopeGroupId(), themeDisplay.getCompanyId(), resource.getName(),
					Long.toString(primaryKey), roleIdsToActionIds);	
		}
	}

	public static List<LearningActivity> getVisibleActivities(ThemeDisplay themeDisplay,
			List<LearningActivity> res, PermissionChecker permissionChecker)
			throws PortalException, SystemException {
		List<LearningActivity> res2 = null;
		if(res != null && res.size()>0){
			res2 = new ArrayList<LearningActivity>();
			res2.addAll(res);
			Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
			for(java.util.ListIterator<LearningActivity> itr = res2.listIterator(); itr.hasNext();){
				LearningActivity activity = itr.next();
				try {
					if(!ResourcePermissionLocalServiceUtil.hasResourcePermission(activity.getCompanyId(), LearningActivity.class.getName(), 
							ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(activity.getActId()),siteMemberRole.getRoleId(), ActionKeys.VIEW)
							&& !permissionChecker.hasPermission(activity.getGroupId(), LearningActivity.class.getName(), activity.getActId() , "CORRECT"))
						itr.remove();
				} catch (SystemException e) {
					e.printStackTrace();
				}
			}
		}
		return res2;
	}
	
	public static String getEncodingTypeOfFile(final byte[] buf, int offset, int length) {
		
		String detectedCharset = new String();

		if (length > 3) {
			int b1 = buf[offset] & 0xFF;
			int b2 = buf[offset + 1] & 0xFF;
			int b3 = buf[offset + 2] & 0xFF;
			int b4 = buf[offset + 3] & 0xFF;

			switch (b1) {
			case 0xEF:
				if (b2 == 0xBB && b3 == 0xBF) {
					detectedCharset = CHARSET_UTF_8;
				}
				break;
			case 0xFE:
				if (b2 == 0xFF && b3 == 0x00 && b4 == 0x00) {
					detectedCharset = CHARSET_X_ISO_10646_UCS_4_3412;
				} else if (b2 == 0xFF) {
					detectedCharset = CHARSET_UTF_16BE;
				}
				break;
			case 0x00:
				if (b2 == 0x00 && b3 == 0xFE && b4 == 0xFF) {
					detectedCharset = CHARSET_UTF_32BE;
				} else if (b2 == 0x00 && b3 == 0xFF && b4 == 0xFE) {
					detectedCharset = CHARSET_X_ISO_10646_UCS_4_2143;
				}
				break;
			case 0xFF:
				if (b2 == 0xFE && b3 == 0x00 && b4 == 0x00) {
					detectedCharset = CHARSET_UTF_32LE;
				} else if (b2 == 0xFE) {
					detectedCharset = CHARSET_UTF_16LE;
				}
				break;
			} // swich end
		}
		return detectedCharset;
	}
	 
}
