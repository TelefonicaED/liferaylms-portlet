/**
 * Copyright (c)2013 Telefonica Learning Services. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.lms.model.impl;

import java.util.List;
import java.util.Locale;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;

/**
 * The extended model implementation for the Course service. Represents a row in the &quot;Lms_Course&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.model.Course} interface.
 * </p>
 *
 * @author TLS
 */
public class CourseImpl extends CourseBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a course model instance should use the {@link com.liferay.lms.model.Course} interface instead.
	 */
	
	private String imageURL;
	private List<AssetCategory> assetCategoryIds;
	private List<AssetTag> assetTagIds;
	private Group groupsel;
	
	public CourseImpl() {
		imageURL = null;
		assetCategoryIds = null;
		assetTagIds = null;
		groupsel = null;
	}
	
	public Course getParentCourse() throws PortalException, SystemException
	{
	    if(this.getParentCourseId()!=0)
	    {
	    	return CourseLocalServiceUtil.getCourse(this.getParentCourseId());
	    }
	    else
	    {
	    	return null;
	    }
	}
	
	
	@Override
	public void setTitle(String title) {
		title = title.replaceAll("\"", "\'");
		super.setTitle(title);
	}
	
	@Override
	public void setTitle(String title, Locale locale) {
		title = title.replaceAll("\"", "\'");
		super.setTitle(title, locale);
	}
	
	@Override
	public void setTitle(String title, Locale locale, Locale defaultLocale) {
		title = title.replaceAll("\"", "\'");
		super.setTitle(title, locale, defaultLocale);
	}
	
	public String getImageURL(ThemeDisplay themeDisplay){
		if(Validator.isNotNull(imageURL))
			return imageURL;
		
		if (Validator.isNotNull(getIcon())) {
			long logoId = getIcon();
			FileEntry fileEntry;
			try {
				fileEntry = DLAppLocalServiceUtil.getFileEntry(logoId);
				imageURL = DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK);
				return imageURL;
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 
		if(groupsel == null){
			try {
				groupsel = GroupLocalServiceUtil.getGroup(getGroupCreatedId());
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
		}
		if(groupsel.getPublicLayoutSet().getLogo()){
			long logoId = groupsel.getPublicLayoutSet().getLogoId();
			imageURL = "/image/layout_set_logo?img_id=" + logoId;
			return imageURL;
		}else {
			imageURL = themeDisplay.getPathThemeImages()+"/file_system/large/course.png";
			return imageURL;
		}
	}
	
	public double getAverageScore(){
		try {
			RatingsStats ratingsStats = RatingsStatsLocalServiceUtil.getStats(Course.class.getName(), getCourseId());
			return ratingsStats.getAverageScore();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public List<AssetCategory> getAssetCategoryIds(){
		if(assetCategoryIds != null){
			return assetCategoryIds;
		}
		try {
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), getCourseId());
			assetCategoryIds = assetEntry.getCategories();
			return assetCategoryIds;
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<AssetTag> getAssetTagIds(){
		if(assetTagIds != null){
			return assetTagIds;
		}
		try {
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), getCourseId());
			assetTagIds = assetEntry.getTags();
			return assetTagIds;
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Group getGroup(){
		if(groupsel != null){
			return groupsel;
		}
		try {
			groupsel = GroupLocalServiceUtil.getGroup(getGroupCreatedId());
			return groupsel;
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}