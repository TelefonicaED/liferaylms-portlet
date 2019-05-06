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

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.el.ELException;

import com.liferay.lms.InscriptionException;
import com.liferay.lms.course.inscriptiontype.InscriptionType;
import com.liferay.lms.course.inscriptiontype.InscriptionTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseCompetence;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.Schedule;
import com.liferay.lms.model.UserCompetence;
import com.liferay.lms.service.CourseCompetenceLocalServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.ScheduleLocalServiceUtil;
import com.liferay.lms.service.UserCompetenceLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
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
	
	private static Log log = LogFactoryUtil.getLog(CourseImpl.class);
	
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
	
	public boolean isRegistrationOnDate(){
		Date now = new Date();
		return now.before(getEndDate()) && now.after(getStartDate());
	}
	
	
	@Override
	public void setTitle(String title) {
		if(Validator.isNotNull(title)){
			title = title.replaceAll("\"", "\'");	
		}
		super.setTitle(title);
	}
	
	@Override
	public void setTitle(String title, Locale locale) {
		if(Validator.isNotNull(title)){
			title = title.replaceAll("\"", "\'");
		}
		super.setTitle(title, locale);
	}
	
	@Override
	public void setTitle(String title, Locale locale, Locale defaultLocale) {
		if(Validator.isNotNull(title)){
			title = title.replaceAll("\"", "\'");
		}
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
	
	public boolean isLocked(User user){
		PermissionChecker permissionChecker = null;
		try {
			permissionChecker = PermissionCheckerFactoryUtil.create(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isLocked(user, permissionChecker);
		
	}
	
	public boolean isLocked(User user, PermissionChecker permissionChecker){
		
		log.debug("CourseImpl::isLocked::isClosed:" + this.isClosed());
		
		//Si el curso está cerrado
		if(this.isClosed()){
			return true;
		}
		
		//Si perteneces a la comunidad
		try {
			if(!UserLocalServiceUtil.hasGroupUser(this.getGroupCreatedId(), user.getUserId())){
				log.debug("CourseImpl::isLocked::hasGroupUser:" + false);
				return true;
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("CourseImpl::isLocked::hasGroupUser:" + true);
		
		//Si tienes permiso para acceder al curso
		if(!permissionChecker.hasPermission(this.getGroupCreatedId(), Course.class.getName(), this.getCourseId() , ActionKeys.ACCESS)){
			log.debug("CourseImpl::isLocked::hasPermissionAccess:" + false);
			return true;
		}
		log.debug("CourseImpl::isLocked::hasPermissionAccess:" + true);

		//Comprobamos si estás en alguna convocatoria y que esté abierta
		Date startDate = this.getExecutionStartDate();
		Date endDate = this.getExecutionEndDate();
		List<Team> teams = null;
		try {
			teams = TeamLocalServiceUtil.getUserTeams(user.getUserId(), this.getGroupCreatedId());
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(teams!=null && teams.size()>0){
			log.debug("CourseImpl::isLocked::hasTeams:" + true);
			Schedule schedule = null;
			for(Team team : teams){
				try {
					schedule = ScheduleLocalServiceUtil.getScheduleByTeamId(team.getTeamId());
					if(schedule!=null){
						startDate=schedule.getStartDate();
						endDate = schedule.getEndDate();
						log.debug("CourseImpl::isLocked::teams::startDate:" + schedule.getStartDate());
						log.debug("CourseImpl::isLocked::teams::endDate:" + schedule.getEndDate());
						break;
					}
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}
		
		Date now = new Date();
		
		if(Validator.isNotNull(startDate) && Validator.isNotNull(endDate) && (startDate.after(now) || endDate.before(now))){
			log.debug("CourseImpl::isLocked::teams::dates:" + false);
			return true;
		}
		log.debug("CourseImpl::isLocked::teams::dates:" + true);
		
		//Comprobamos si tiene fechas para realizar el curso
		CourseResult courseResult = null;
		try {
			courseResult = CourseResultLocalServiceUtil.getByUserAndCourse(this.getCourseId(), user.getUserId());
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        if(courseResult!=null && ((courseResult.getAllowFinishDate()!=null && courseResult.getAllowFinishDate().before(now)) ||(courseResult.getAllowStartDate()!=null && courseResult.getAllowStartDate().after(now)))){
        	log.debug("CourseImpl::isLocked::allowdates::startDate:" + courseResult.getAllowStartDate());
        	log.debug("CourseImpl::isLocked::allowdates::endDate:" + courseResult.getAllowFinishDate());
			return true;
		}
        
        log.debug("CourseImpl::isLocked::allowdates:" + true);
		
		return false;
	}
	
	public boolean canEnroll(long userId, boolean checkCompetences, Locale locale, PermissionChecker permissionChecker) throws PortalException, InscriptionException, SystemException {
		log.debug("canEnroll: " + getCourseId() + " - " + userId + " - " + checkCompetences);
		//1.Comprobamos que no estÃ© ya inscrito
		if(!GroupLocalServiceUtil.hasUserGroup(userId, getGroupCreatedId())) {
			Date now = new Date();
			
			if(permissionChecker.hasPermission(this.getGroupCreatedId(), Course.class.getName(), this.getCourseId() , "REGISTER")){
				//2. Fecha actual dentro del periodo de inscripcion
				if((getStartDate().before(now) && getEndDate().after(now))){
	
					//3.Comprobamos que se cumplan todos los prerequisitos
					boolean isPassed = true;
					if(checkCompetences){
						List<CourseCompetence> listCourseCompetences = CourseCompetenceLocalServiceUtil.findBycourseId(getCourseId(), true);
						int i = 0;
						UserCompetence userCompentece = null; 
						while(isPassed && listCourseCompetences.size() > i) {
							userCompentece = UserCompetenceLocalServiceUtil.findByUserIdCompetenceId(userId, listCourseCompetences.get(i).getCompetenceId());
							isPassed = userCompentece != null;
							i++;
						}
					}
					if(isPassed) {
						// 4. El mÃƒÂ¡ximo de inscripciones del curso no ha sido superado
						if(getMaxusers()<=0 || CourseLocalServiceUtil.countStudentsFromCourse(this.getCourseId(), this.getCompanyId(), null, null, null, null, WorkflowConstants.STATUS_APPROVED, null, true) < getMaxusers()){
							//5. Comprobamos el tipo de inscripciÃ³n
							Group group = GroupLocalServiceUtil.getGroup(getGroupCreatedId());
							if(group.getType()==GroupConstants.TYPE_SITE_OPEN){
								log.debug("puede inscribirse");
								return true;
							}else{
								if(group.getType()==GroupConstants.TYPE_SITE_RESTRICTED){
									if(MembershipRequestLocalServiceUtil.hasMembershipRequest(userId, group.getGroupId(), MembershipRequestConstants.STATUS_PENDING)){
										throw new InscriptionException("status-pending", LanguageUtil.get(locale, "course.pending"));
									}else if(MembershipRequestLocalServiceUtil.hasMembershipRequest(userId, group.getGroupId(), MembershipRequestConstants.STATUS_DENIED)){
										throw new InscriptionException("status-denied", LanguageUtil.get(locale, "course.denied"));
									}else{
										log.debug("puede inscribirse");
										return true;
									}
								}else{
									if(group.getType()==GroupConstants.TYPE_SITE_PRIVATE){
										//DeberÃ­a lanzar una excepciÃ³n indicando que es privado y que no se puede
										log.debug("exception: " + LanguageUtil.get(locale, "inscription.error.site-private"));
										throw new InscriptionException("site-private", LanguageUtil.get(locale, "inscription.error.site-private"));
									}
								}
							}
						}else {
							log.debug("exception: " + LanguageUtil.get(locale, "inscription.error.max-users"));
							throw new InscriptionException("max-users", LanguageUtil.get(locale, "inscription-error-max-users"));
						}
					}else {
						//DeberÃ­a lanzar una excepciÃ³n indicando que no se cumplen los prerequisitos
						log.debug("exception: " + LanguageUtil.get(locale, "inscription.error.competences"));
						throw new InscriptionException("competences", LanguageUtil.get(locale, "competence.block"));
					}
				}else {
					//DeberÃ­a lanzar una excepciÃ³n indicando que estÃ¡ en periodo de ejecuciÃ³n
					log.debug("exception: " + LanguageUtil.get(locale, "inscription.error.registration-dates"));
					throw new InscriptionException("registration-dates", LanguageUtil.get(locale, "inscripcion.date.pass"));
				}
			}else {
				log.debug("exception: " + LanguageUtil.get(locale, "inscription.error.permission-register"));
				throw new InscriptionException("permission-register", LanguageUtil.get(locale, "inscription.error.permission-register"));
			}
			log.debug("no puede inscribirse");
			return false;
		}else {
			log.debug("no estoy inscrito");
			return false;
		}
	}
	
	public boolean canUnsubscribe(long userId, PermissionChecker permissionChecker) throws PortalException, SystemException {
		Date now = new Date();
			
		if (GroupLocalServiceUtil.hasUserGroup(userId, getGroupCreatedId()) && getStartDate().before(now) && 
				getEndDate().after(now) && permissionChecker.hasPermission(this.getGroupCreatedId(), Course.class.getName(), this.getCourseId() , "REGISTER")) {
			CourseResult courseResult = CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(getCourseId(), userId);
			Group group = GroupLocalServiceUtil.getGroup(getGroupCreatedId());

			InscriptionTypeRegistry inscriptionTypeRegistry = new InscriptionTypeRegistry();
			InscriptionType inscriptionType = inscriptionTypeRegistry.getInscriptionType(getInscriptionType());
			
			return courseResult == null || courseResult.getPassedDate() == null || (group.getType() == GroupConstants.TYPE_SITE_OPEN) && inscriptionType.canUnsubscribe();
		}else {
			return false;
		}
	}
}