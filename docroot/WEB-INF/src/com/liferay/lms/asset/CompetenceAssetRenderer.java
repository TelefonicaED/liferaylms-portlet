package com.liferay.lms.asset;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import com.liferay.lms.model.Competence;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

public class CompetenceAssetRenderer extends BaseAssetRenderer {

	private Competence _competence;
	AssetEntry assetEntry;
	public CompetenceAssetRenderer (Competence competence) {
		_competence = competence;
		try {
			assetEntry=AssetEntryLocalServiceUtil.getEntry(Competence.class.getName(), competence.getCompetenceId());
		} catch (PortalException e) {
			// TODO Auto-generated catch block
		} catch (SystemException e) {
			// TODO Auto-generated catch block
		}
		}
		public long getClassPK() {
		return _competence.getCompetenceId();
		}
		public long getGroupId() {
		return _competence.getGroupId();
		}
		public String getSummary() { 
		return assetEntry.getDescription();
		} 
		public String getTitle() { 
			
		return assetEntry.getTitle();
		} 
		public long getUserId() {
		return _competence.getUserId();
		}
		public String getUuid() {
		return _competence.getUuid();
		}
		public String render(RenderRequest request, RenderResponse response,
				String template)
				throws Exception {
				return null;
						
						
					}
			public PortletURL getURLEdit(
					LiferayPortletRequest liferayPortletRequest,
					LiferayPortletResponse liferayPortletResponse) throws Exception 
			{
				 HttpServletRequest request =
			            liferayPortletRequest.getHttpServletRequest();

			 ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			            WebKeys.THEME_DISPLAY);
		
			return null;
			}
			@Override
			public String getURLViewInContext(
					LiferayPortletRequest liferayPortletRequest,
					LiferayPortletResponse liferayPortletResponse,
					String noSuchEntryRedirect) throws Exception {
				// TODO Auto-generated method stub
				
					return null;
			}
			
			@Override
			public boolean hasEditPermission(PermissionChecker permissionChecker)
					throws PortalException, SystemException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public String getSummary(Locale locale) {
				
				return _competence.getDescription(locale);
			}
			
			public String getTitle(Locale locale) {
				// TODO Auto-generated method stub
				return _competence.getTitle(locale);
			}
			@Override
			public String getUserName() {
				return null;
			}
   
	

}
