/**
 * 2012 TELEFONICA LEARNING SERVICES. All rights reserved.
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

package com.liferay.lms.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.portlet.PortletPreferences;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.learningactivity.courseeval.CourseEval;
import com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseCompetence;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.Schedule;
import com.liferay.lms.model.UserCompetence;
import com.liferay.lms.service.CourseCompetenceLocalServiceUtil;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.UserCompetenceLocalServiceUtil;
import com.liferay.lms.service.base.CourseLocalServiceBaseImpl;
import com.liferay.lms.service.persistence.CourseFinderUtil;
import com.liferay.lms.util.CourseParams;
import com.liferay.lms.views.CourseResultView;
import com.liferay.portal.kernel.dao.orm.CustomSQLParam;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.ModelHintsConstants;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.comparator.UserFirstNameComparator;
import com.liferay.portal.util.comparator.UserLastNameComparator;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.social.model.SocialActivityCounterDefinition;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.model.SocialActivitySetting;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivitySettingLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivitySettingServiceUtil;
import com.liferay.util.LmsLocaleUtil;



/**
 * The implementation of the course local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CourseLocalService} interface.
 * </p>
 * 
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CourseLocalServiceUtil} to access the course local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.CourseLocalServiceBaseImpl
 * @see com.liferay.lms.service.CourseLocalServiceUtil
 */
public class CourseLocalServiceImpl extends CourseLocalServiceBaseImpl { 
	
	
	final String DEGREE  = "\\u00B0";
	
	Log log = LogFactoryUtil.getLog(CourseLocalServiceImpl.class);
	
	public java.util.List<Course> getCoursesOfGroup(long groupId) throws SystemException{
		return coursePersistence.findByGroupId(groupId);
	}
	
	public java.util.List<Course> getOpenCoursesOfGroup(long groupId) throws SystemException{
		return coursePersistence.findByGroupId(groupId);
	}
	
	public java.util.List<Course> getCourses(long companyId) throws SystemException{
		return coursePersistence.findByCompanyId(companyId);
	}
	
	public long countByGroupId(long groupId) throws SystemException{
		return coursePersistence.countByGroupId(groupId);
	}
	
	public Course fetchByGroupCreatedId(long groupId) throws SystemException{
		return coursePersistence.fetchByGroupCreatedId(groupId);
	}
	
	public Course addCourse (String title, String description,String summary,String friendlyURL, Locale locale,
			java.util.Date createDate,java.util.Date startDate,java.util.Date endDate,long layoutSetPrototypeId,int typesite,ServiceContext serviceContext, long calificationType, int maxUsers,boolean isFromClone)
			throws SystemException, PortalException {
		return addCourse(title, description, summary, friendlyURL, locale, createDate, startDate, endDate, layoutSetPrototypeId, typesite, 0, calificationType, maxUsers, serviceContext,isFromClone);
	}
	
	public List<Course> getUserCourses(long userId) throws PortalException, SystemException{
		
		User usuario= userLocalService.getUser(userId);
		List<Group> groups= GroupLocalServiceUtil.getUserGroups(usuario.getUserId());
		List<Course> results=new java.util.ArrayList<Course>();

		for(Group groupCourse:groups){
			Course course=courseLocalService.fetchByGroupCreatedId(groupCourse.getGroupId());
			if(course!=null){
				results.add(course);
			}
		}
		return results;

	}
	
	
	public List<Course> getOpenedUserCourses(long userId) throws PortalException, SystemException{		
		 return courseFinder.getExistingUserCourses(userId,-1,-1);
	}
	
	public List<Course> getOpenedUserCourses(long userId, int start, int end) throws PortalException, SystemException{
		 return courseFinder.getExistingUserCourses(userId,start,end);
	}
	
	public int countOpenedUserCourses(long userId) throws PortalException, SystemException{
		 return courseFinder.countExistingUserCourses(userId);
	}
	
	public List<Course> getPublicCoursesByCompanyId(Long companyId){
		
		Long classNameId = ClassNameLocalServiceUtil.getClassNameId(Course.class.getName());
		
		if(classNameId!=null){
			try {
				DynamicQuery dq = DynamicQueryFactoryUtil.forClass(AssetEntry.class);
				dq.add(PropertyFactoryUtil.forName("companyId").eq(companyId));
				dq.add(PropertyFactoryUtil.forName("classNameId").eq(classNameId));
				dq.add(PropertyFactoryUtil.forName("visible").eq(true));
				dq.setProjection(ProjectionFactoryUtil.distinct(ProjectionFactoryUtil.property("classPK")));
				List<Long> results = (List<Long>)assetEntryLocalService.dynamicQuery(dq);

				List<Course> courses = new ArrayList<Course>();
				for(Long courseId : results){
					Course course = coursePersistence.fetchByPrimaryKey(courseId);
					if(course!=null)
						courses.add(course);
				}
				
				return courses;
				
			} catch (SystemException e) {
				if(log.isDebugEnabled())e.printStackTrace();
			}
		}
		
		return null;
	}
	


	public List<Course> getPublicCoursesByCompanyId(Long companyId, int start, int end){
		
		Long classNameId = ClassNameLocalServiceUtil.getClassNameId(Course.class.getName());
		
		if(classNameId!=null){
			try {
				DynamicQuery dq = DynamicQueryFactoryUtil.forClass(AssetEntry.class);
				dq.add(PropertyFactoryUtil.forName("companyId").eq(companyId));
				dq.add(PropertyFactoryUtil.forName("classNameId").eq(classNameId));
				dq.add(PropertyFactoryUtil.forName("visible").eq(true));
				dq.setProjection(ProjectionFactoryUtil.distinct(ProjectionFactoryUtil.property("classPK")));
				List<Long> results = (List<Long>)assetEntryLocalService.dynamicQuery(dq,start, end);
	
				List<Course> courses = new ArrayList<Course>();
				for(Long courseId : results){
					Course course = coursePersistence.fetchByPrimaryKey(courseId);
					if(course!=null)
						courses.add(course);
				}
				
				return courses;
				
			} catch (SystemException e) {
				if(log.isDebugEnabled())e.printStackTrace();
			}
		}
		
		return null;
	}


	public Course addCourse (String title, String description,String summary,String friendlyURL, Locale locale,
		java.util.Date createDate,java.util.Date startDate,java.util.Date endDate,long layoutSetPrototypeId,int typesite, long CourseEvalId, long calificationType, int maxUsers,ServiceContext serviceContext,boolean isfromClone)
		throws SystemException, PortalException {
		
		Course course = addCourse (title, description,summary,friendlyURL, locale,
				createDate,startDate,endDate,null,null,layoutSetPrototypeId,typesite, CourseEvalId, calificationType, maxUsers,
				 serviceContext, isfromClone);

		
		return course;
	}
	
	public Course addCourse (String title, String description,String summary,String friendlyURL, Locale locale,
			Date createDate,Date startDate,Date endDate, Date executionStartDate, Date executionEndDate, long layoutSetPrototypeId,int typesite, long CourseEvalId, long calificationType, int maxUsers,ServiceContext serviceContext,boolean isfromClone)
			throws SystemException, PortalException {
		Map<Locale, String> titleMap = new HashMap<Locale, String>();
		titleMap.put(locale, title);
		return addCourse(titleMap, description, summary, friendlyURL, locale, createDate, startDate, endDate, executionStartDate, executionEndDate, 
						layoutSetPrototypeId, typesite, CourseEvalId, calificationType, maxUsers, serviceContext, isfromClone);
	}

	public Course addCourse (Map<Locale,String> titleMap, String description,String summary,String friendlyURL, Locale locale,
			Date createDate,Date startDate,Date endDate, Date executionStartDate, Date executionEndDate, long layoutSetPrototypeId,int typesite, long CourseEvalId, long calificationType, int maxUsers,ServiceContext serviceContext,boolean isfromClone)
			throws SystemException, PortalException {
		LmsPrefs lmsPrefs=lmsPrefsLocalService.getLmsPrefsIni(serviceContext.getCompanyId());
		long userId=serviceContext.getUserId();
		Course course = coursePersistence.create(counterLocalService.increment(Course.class.getName()));
		String title = null;
		if(titleMap.containsKey(locale)){
			title = titleMap.get(locale);
		}else{
			//Cogemos el primero
			Entry<Locale, String> entry = titleMap.entrySet().iterator().next();
			title = entry.getValue();
		}
		
		try{
			
			//Se asegura que la longitud de friendlyURL no supere el maximo
			int maxLength  = GetterUtil.getInteger(
								ModelHintsUtil.getHints(Group.class.getName(), "friendlyURL").get("max-length"),
								GetterUtil.getInteger(ModelHintsConstants.TEXT_MAX_LENGTH));
			
			if(Validator.isNull(friendlyURL)) {
				friendlyURL = StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(title);
				if(friendlyURL.length()>maxLength) {
					friendlyURL = friendlyURL.substring(0, maxLength);
				}
				for(int i=0;;i++){
					Group exist = groupLocalService.fetchFriendlyURLGroup(serviceContext.getCompanyId(), friendlyURL);
					if (Validator.isNotNull(exist)){
						String iString = String.valueOf(i);
						if(friendlyURL.length()+iString.length()>maxLength) {
							if(iString.length()>maxLength) {
								throw new SystemException();
							}
							friendlyURL =friendlyURL.substring(0, maxLength-iString.length())+iString;
						}
						else {
							friendlyURL =friendlyURL+iString;
						}
					}else{
						break;
					}
				}				
			}
			
			friendlyURL = StringPool.SLASH+friendlyURL.replaceAll("[^a-zA-Z0-9_-]+", "");
			course.setCompanyId(serviceContext.getCompanyId());
			course.setGroupId(serviceContext.getScopeGroupId());
			course.setUserId(userId);
			course.setUserName(userLocalService.getUser(userId).getFullName());
			course.setFriendlyURL(friendlyURL);
			course.setDescription(description,locale, locale);
			course.setTitleMap(titleMap);
			course.setCreateDate(createDate);
			course.setModifiedDate(createDate);
			course.setStartDate(startDate);
			course.setEndDate(endDate);
			course.setExecutionStartDate(executionStartDate);
			course.setExecutionEndDate(executionEndDate);
			course.setStatus(WorkflowConstants.STATUS_APPROVED);
			course.setExpandoBridgeAttributes(serviceContext);
			course.setCourseEvalId(CourseEvalId);
			course.setCalificationType(calificationType);
			course.setMaxusers(maxUsers);
			
			course = LmsLocaleUtil.checkDefaultLocale(Course.class, course, "description");
			//creating group
			Group group = groupLocalService.addGroup(userLocalService.getDefaultUser(serviceContext.getCompanyId()).getUserId(),
					null, 0, title,summary,typesite,friendlyURL,true,true,serviceContext);
			course.setGroupCreatedId(group.getGroupId());
			course.setFriendlyURL(group.getFriendlyURL());
			course.setIsLinked(false);

			coursePersistence.update(course, true);
			
			resourceLocalService.addResources(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), userId,Course.class.getName(), course.getPrimaryKey(), false,true, true);
			AssetEntry assetEntry=assetEntryLocalService.updateEntry(userId, course.getGroupId(), Course.class.getName(),
					course.getCourseId(), course.getUuid(),0, serviceContext.getAssetCategoryIds(),
					serviceContext.getAssetTagNames(), true, executionStartDate, executionEndDate,new java.util.Date(System.currentTimeMillis()), null,
					ContentTypes.TEXT_HTML, course.getTitle(), course.getDescription(locale), summary, null, null, 0, 0,null, false);
			assetLinkLocalService.updateLinks(
					userId, assetEntry.getEntryId(), serviceContext.getAssetLinkEntryIds(),
					AssetLinkConstants.TYPE_RELATED);
			
			//Añadimos el rol Teacher al usuario que crea el blog
			long[] usuarios = new long[]{userId};
			boolean teacherRoleToCreator = GetterUtil.getBoolean(PropsUtil.get("lms.course.add.teacherRoleToCreator"));
			boolean editorRoleToCreator = GetterUtil.getBoolean(PropsUtil.get("lms.course.add.editorRoleToCreator"));

			if(teacherRoleToCreator||editorRoleToCreator){
				groupLocalService.addUserGroups(userId, new long[] { group.getGroupId() });
			}

			if(teacherRoleToCreator){
				userGroupRoleLocalService.addUserGroupRoles(usuarios,
						course.getGroupCreatedId(), lmsPrefs.getTeacherRole());
			}

			if(editorRoleToCreator){
				userGroupRoleLocalService.addUserGroupRoles(usuarios,
						course.getGroupCreatedId(), lmsPrefs.getEditorRole());
			}			
			LayoutSetPrototype lsProto=layoutSetPrototypeLocalService.getLayoutSetPrototype(layoutSetPrototypeId);
			//importLayouts(getAdministratorUser(serviceContext.getCompanyId()).getUserId(), group, lsProto);
			
			importLayouts(userId, group, lsProto);
			
			
			CourseEval courseEval = new CourseEvalRegistry().getCourseEval(CourseEvalId);
			if(courseEval!=null) {
				courseEval.setExtraContent(course, "ADD_COURSE", serviceContext);
			}

			/* activamos social activity para la comunidad creada */ 		
			List<SocialActivitySetting> actSettings=SocialActivitySettingLocalServiceUtil.getActivitySettings(lsProto.getGroup().getGroupId());
			for(SocialActivitySetting actSetting:actSettings)
			{
			//Activamos las activity settings que est�n activadas en la plantilla
				SocialActivitySettingLocalServiceUtil.updateActivitySetting(group.getGroupId(), actSetting.getClassName(), true);	
				List<SocialActivityDefinition> sads=SocialActivitySettingServiceUtil.getActivityDefinitions(lsProto.getGroup().getGroupId(), actSetting.getClassName());
				for(SocialActivityDefinition sad:sads)
				{
				
					java.util.Collection<SocialActivityCounterDefinition> sacdl=sad.getActivityCounterDefinitions();
					List<SocialActivityCounterDefinition> sacdlnew=new java.util.ArrayList<SocialActivityCounterDefinition>();
					for(SocialActivityCounterDefinition sacd:sacdl)
					{
						SocialActivityCounterDefinition sacdn=sacd.clone();
						sacdlnew.add(sacdn);
					}
					SocialActivitySettingServiceUtil.updateActivitySettings(group.getGroupId(), actSetting.getClassName(), sad.getActivityType(), sacdlnew);
				}
			}
			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(Course.class);
			indexer.reindex(course);
		}catch(PortalException e){
			if(log.isInfoEnabled()){
				log.info("CourseLocalServiceImpl.addCourse(): " + e + "message: " + e.getMessage());
			}
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			if(log.isInfoEnabled()){
				log.info("CourseLocalServiceImpl.addCourse(): " + e + "message: " + e.getMessage());
			}
			e.printStackTrace();
			throw new PortalException(e.getMessage());
			
		}
		
		
		// METODO METIDO POR MIGUEL
		if(!isfromClone){
			Module newModule;
			try {
				
				
				newModule = ModuleLocalServiceUtil.createModule(CounterLocalServiceUtil.increment(Module.class.getName()));
				newModule.setTitle(LanguageUtil.get(locale,"com.liferay.lms.model.module"), locale);
				newModule.setDescription(LanguageUtil.get(locale,"description"), locale);
				newModule.setCreateDate(createDate);
				newModule.setCompanyId(course.getCompanyId());
				newModule.setGroupId(course.getGroupCreatedId());
				newModule.setUserId(course.getUserId());
				newModule.setOrdern(newModule.getModuleId());				
				newModule.setStartDate(executionStartDate);
				newModule.setEndDate(executionEndDate);
				
				
				ModuleLocalServiceUtil.addModule(newModule);
				
				
				 try {
				    	Role siteMember = RoleLocalServiceUtil.fetchRole(newModule.getCompanyId(), RoleConstants.SITE_MEMBER);
				    	ResourcePermissionLocalServiceUtil.setResourcePermissions(newModule.getCompanyId(), 
				    			Module.class.getName(),ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(newModule.getModuleId()),  siteMember.getRoleId(),  new String[]{"VIEW","ACCESS"});
				   
				    	
				    	
				    	
				    	
						resourceLocalService.addResources(
								newModule.getCompanyId(), newModule.getGroupId(), newModule.getUserId(),
						Module.class.getName(), newModule.getPrimaryKey(), false,
						true, true);
					} catch (PortalException e) {
						if(log.isDebugEnabled())e.printStackTrace();
						if(log.isInfoEnabled())log.info(e.getMessage());
						throw new SystemException(e);
					}
				
				log.debug("    + Module : " + newModule.getTitle(Locale.getDefault()) +"("+newModule.getModuleId()+")" );
				ModuleLocalServiceUtil.updateModule(newModule);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		////////////////////////////
		//auditing
		AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), userId, AuditConstants.ADD, null);
		return course;
		
	}

	@Indexable(type=IndexableType.REINDEX)
	public Course addCourse (String title, String description,String summary,String friendlyURL, Locale locale,
			java.util.Date createDate,java.util.Date startDate,java.util.Date endDate,
		ServiceContext serviceContext, long calificationType)
			throws SystemException, 
			PortalException {
		LmsPrefs lmsPrefs=lmsPrefsLocalService.getLmsPrefsIni(serviceContext.getCompanyId());
		long layoutSetPrototypeId=Long.valueOf(lmsPrefs.getLmsTemplates());
		Course course = addCourse (title, description,summary,friendlyURL, locale,
				createDate,startDate,endDate,layoutSetPrototypeId,GroupConstants.TYPE_SITE_PRIVATE,
				 serviceContext, calificationType,0,false);
	return course;
	}

	@Indexable(type=IndexableType.REINDEX)
	public Course addCourse (String title, String description,String friendlyURL, Locale locale,
			java.util.Date createDate,java.util.Date startDate,java.util.Date endDate,
		ServiceContext serviceContext, long calificationType)
			throws SystemException, 
			PortalException {
		
		Course course = this.addCourse(title, description, description, friendlyURL, locale, createDate, startDate, endDate, serviceContext, calificationType);
		
	
		return course;
	}
	
	
	@SuppressWarnings("unused")
	private static LayoutSetPrototype fetchLayoutSetPrototypeByDescription(String description,long companyId) throws SystemException,PortalException
	{
		LayoutSetPrototype lspRes = null;
		for(LayoutSetPrototype lsp:LayoutSetPrototypeLocalServiceUtil.search(companyId, true,0,Integer.MAX_VALUE, null))	
		{
			if(description.equals(lsp.getDescription()))
			{
				lspRes = lsp;
				break;
			}
		}
		return lspRes;
	}	
	
	private void importLayouts(long userId,Group grupo,LayoutSetPrototype lsProto) throws PortalException, SystemException{
			LayoutSet ls = lsProto.getLayoutSet();	
			
			Map<String, String[]> parameterMap =getLayoutSetPrototypeParameters();

			
		File fileIni= layoutLocalService.exportLayoutsAsFile(ls.getGroupId(), true,
				null,parameterMap, null, null);
	
		try
		{
		layoutLocalService.importLayouts(userId, grupo.getPublicLayoutSet().getGroupId(), 
			grupo.getPublicLayoutSet().isPrivateLayout(),
			parameterMap, fileIni);
		}
		catch(Exception e)
		{
		}
		FileUtil.delete(fileIni);
		
	}
	
	private static Map<String, String[]> getLayoutSetPrototypeParameters() {
		Map<String, String[]> parameterMap = new LinkedHashMap<String, String[]>();
        
		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
			//new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
				PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
				new String[] {PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE});
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
				PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
				new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
				PortletDataHandlerKeys.LOGO,
				new String[] {Boolean.FALSE.toString()});
			parameterMap.put(
				PortletDataHandlerKeys.PERFORM_DIRECT_BINARY_IMPORT,
				new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			//new String[] {Boolean.FALSE.toString()});
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			//new String[] {Boolean.FALSE.toString()});
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
				PortletDataHandlerKeys.THEME,
				new String[] {Boolean.FALSE.toString()});
			parameterMap.put(
				PortletDataHandlerKeys.THEME_REFERENCE,
				new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});
		parameterMap.put(
			PortletDataHandlerKeys.USER_PERMISSIONS,
			new String[] {Boolean.FALSE.toString()});
		return parameterMap;
	}
	
	public void setVisible(long courseId,boolean visible) throws PortalException, SystemException{
	    assetEntryLocalService.updateVisible(Course.class.getName(), courseId, visible);
	}
	
	@Indexable(type=IndexableType.REINDEX)
	public Course modCourse (Course course,String summary, 
			ServiceContext serviceContext)
			throws SystemException, PortalException {
				return this.modCourse(course, summary, serviceContext, true);
			}

	@Indexable(type=IndexableType.REINDEX)
	public Course modCourse (Course course, 
			ServiceContext serviceContext)
			throws SystemException, PortalException {
			return this.modCourse(course, "", serviceContext,true);
	}
	
	@Indexable(type=IndexableType.REINDEX)
	public Course modCourse (Course course,String summary, 
			ServiceContext serviceContext, boolean visible)
			throws SystemException, PortalException {
		return this.modCourse(course,summary, serviceContext, visible, false);
	}
	
	
	@Indexable(type=IndexableType.REINDEX)
	public Course modCourse (Course course,String summary, 
			ServiceContext serviceContext, boolean visible, boolean allowDuplicateName)
			throws SystemException, PortalException {
		
	
		int numberUsers = UserLocalServiceUtil.getGroupUsersCount(course.getGroupCreatedId());
		if(course.getMaxusers()>0&&numberUsers>course.getMaxusers()){
			if(log.isDebugEnabled()){
				log.debug("Throws exception max users violated");
			}
			throw new PortalException("maxUsers "+numberUsers);
		}
	
		course.setModifiedDate(new java.util.Date(System.currentTimeMillis()));
		course.setExpandoBridgeAttributes(serviceContext);
		Locale locale=new Locale(serviceContext.getLanguageId());
		
		Group theGroup=GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
		try{
			theGroup = GroupLocalServiceUtil.updateFriendlyURL(theGroup.getGroupId(), course.getFriendlyURL());
		}catch(Exception e){
			throw new PortalException("friendlyURL");
		}
		
		coursePersistence.update(course, true);
		long userId=serviceContext.getUserId();
		
		String groupName = course.getTitle(locale,true);
		if(allowDuplicateName){
			if(GroupLocalServiceUtil.fetchGroup(course.getCompanyId(), groupName)!=null){
				groupName = course.getTitle(locale,true)+" ("+course.getCourseId()+")";
			}
		}
		
		theGroup.setName(groupName);
		theGroup.setDescription(summary);
		
		int type=GroupConstants.TYPE_SITE_OPEN;
		try{
			if (serviceContext.getAttribute("type") != null) {
				type = Integer.valueOf(serviceContext.getAttribute("type").toString());
			}
		}catch(NumberFormatException nfe){	
			log.debug(nfe);
		}
		
		theGroup.setType(type);
		groupLocalService.updateGroup(theGroup);

		CourseEval courseEval = new CourseEvalRegistry().getCourseEval(course.getCourseEvalId());
		if(courseEval!=null) {
			courseEval.setExtraContent(course, Constants.UPDATE, serviceContext);
		}
		
		AssetEntry assetEntry=assetEntryLocalService.updateEntry(
				userId, course.getGroupId(), Course.class.getName(),
				course.getCourseId(), course.getUuid(),0, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(), visible, null, null,
				new java.util.Date(System.currentTimeMillis()), null,
				ContentTypes.TEXT_HTML, course.getTitle(), course.getDescription(locale), summary, null, null, 0, 0,
				null, false);
        
		assetLinkLocalService.updateLinks(
				userId, assetEntry.getEntryId(), serviceContext.getAssetLinkEntryIds(),
				AssetLinkConstants.TYPE_RELATED);
		//auditing
		AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.UPDATE, null);
		
		return course;
		
	}
			
	@Indexable(type=IndexableType.REINDEX)
	public Course closeCourse(long courseId) throws SystemException,
	PortalException {
	
		Course course=CourseLocalServiceUtil.getCourse(courseId);
		if(!course.getClosed()){
			course.setClosed(true);
			course.setModifiedDate(new Date());
			Group courseGroup=GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
			courseGroup.setActive(false);
			GroupLocalServiceUtil.updateGroup(courseGroup);
			coursePersistence.update(course, true);		
			AssetEntry courseAsset=AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId());
			courseAsset.setVisible(false);
			AssetEntryLocalServiceUtil.updateAssetEntry(courseAsset);

			CourseEval courseEval=new CourseEvalRegistry().getCourseEval(course.getCourseEvalId());
			if(Validator.isNotNull(courseEval)) {
				courseEval.onCloseCourse(course);
			}
			
			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), 
									courseId, PrincipalThreadLocal.getUserId(), AuditConstants.CLOSE, null);

		}

		return course;
	}

	@Indexable(type=IndexableType.REINDEX)
	public Course openCourse(long courseId) throws SystemException,
	PortalException {
	
		Course course=CourseLocalServiceUtil.getCourse(courseId);
		if(course.getClosed()){
			course.setClosed(false);
			course.setModifiedDate(new Date());
			Group courseGroup=GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
			courseGroup.setActive(true);
			GroupLocalServiceUtil.updateGroup(courseGroup);
			coursePersistence.update(course, true);	
			AssetEntry courseAsset=AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId());
			courseAsset.setVisible(true);
			AssetEntryLocalServiceUtil.updateAssetEntry(courseAsset);

			CourseEval courseEval=new CourseEvalRegistry().getCourseEval(course.getCourseEvalId());
			if(Validator.isNotNull(courseEval)) {
				courseEval.onOpenCourse(course);
			}

			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), 
					course.getCourseId(), PrincipalThreadLocal.getUserId(), AuditConstants.OPEN, null);
		}

		return course;
	}
	
	public Course deleteCourse(Course course) throws SystemException {
		
		try {
			// Remove from lucene
			Indexer indexer = IndexerRegistryUtil.getIndexer(Course.class);
			indexer.delete(course);
		} catch (Exception e2) {e2.printStackTrace();}

		try {
			// Cambia el titulo y la friendlyurl y desactiva el grupo
			Group theGroup = GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
			theGroup.setName("desactivado(" + course.getGroupCreatedId() + ")");
			theGroup.setFriendlyURL(course.getFriendlyURL() + "_desac_"+ + course.getCourseId());
			theGroup.setActive(false);

			GroupLocalServiceUtil.updateGroup(theGroup);
		} catch (Exception e1) {e1.printStackTrace();}

		try {
			assetEntryLocalService.deleteEntry(Course.class.getName(),course.getCourseId());
		} catch (Exception e) {e.printStackTrace();}
		
		try {
			resourceLocalService.deleteResource(course.getCompanyId(), Course.class.getName(),ResourceConstants.SCOPE_INDIVIDUAL, course.getPrimaryKey());
		} catch (Exception e) {e.printStackTrace();}
	
		
		try {
			coursePersistence.remove(course);
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}

	@Indexable(type = IndexableType.DELETE)
	public Course deleteCourse(long courseId) throws SystemException,PortalException {
		try {
			this.deleteCourse(CourseLocalServiceUtil.getCourse(courseId));
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	//Siguiendo el indice IX_5DE0BE11 de la tabla group_
	public boolean existsCourseName(long companyId, long classNameId, long liveGroupId, String name){
		boolean res = false;
		
		try {
			Group group = GroupLocalServiceUtil.getGroup(companyId, name);
			if(group != null && group.getClassNameId() == classNameId && group.getLiveGroupId() == liveGroupId ){
				res = true;
			}
			
		} catch (Exception e) {
			res = false;
		}

		return res;
	}
	
	public Course getCourseByGroupCreatedId(long groupCreatedId) throws SystemException{
		return coursePersistence.fetchByGroupCreatedId(groupCreatedId);
	}
	
	@SuppressWarnings("unchecked")
	public boolean existsCourseName(Long companyId, Long courseId, String groupName) throws SystemException, PortalException {
		
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(Group.class, PortalClassLoaderUtil.getClassLoader());
		consulta.add(PropertyFactoryUtil.forName("name").eq(groupName));
		consulta.add(PropertyFactoryUtil.forName("companyId").eq(companyId));
		if (courseId != null) {
			Course course = CourseLocalServiceUtil.getCourse(courseId);
			consulta.add(PropertyFactoryUtil.forName("groupId").ne(course.getGroupCreatedId()));
		}
		
		List<Group> list = (List<Group>)GroupLocalServiceUtil.dynamicQuery(consulta);
		
		if(!list.isEmpty() && list.size() > 0){
			return true;
		}
		
		return false;
	}
	
	public List<Course> findByCompanyId(Long companyId) throws SystemException{
		return coursePersistence.findByCompanyId(companyId);
	}
	
	public long[] getTeachersAndEditorsIdsFromCourse(Course course){
		long[] userIds = null;
		

		try {
			LmsPrefs prefs = LmsPrefsLocalServiceUtil.getLmsPrefs(course.getCompanyId());
			LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();
			long teacherRoleId = RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
			long editorRoleId = RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
			userParams.put("usersGroups", course.getGroupCreatedId());
			userParams.put("inCourseRoleIds", new CustomSQLParam("WHERE User_.userId IN "
		              + " (SELECT UserGroupRole.userId " + "  FROM UserGroupRole "
		              + "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId IN (?,?)))", new Long[] {
		            		 course.getGroupCreatedId(), teacherRoleId, editorRoleId}));
			
			List<User> users = UserLocalServiceUtil.search(course.getCompanyId(), "", 
					WorkflowConstants.STATUS_APPROVED, userParams, 
					QueryUtil.ALL_POS, QueryUtil.ALL_POS,(OrderByComparator)null);
			if(users != null){
				userIds = new long[users.size()];
				for(int i = 0; i < users.size(); i++){
					userIds[i] = users.get(i).getUserId();
				}
			}
			
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userIds;
	}
	
	/**
	 * Método para buscar cursos
	 */
	public List<Course> searchCourses(long companyId, String freeText, String language, int status, long parentCourseId, long groupId, LinkedHashMap<String, Object> params, int start, int end,
			OrderByComparator obc){
		return courseFinder.findByKeywords(companyId, freeText, language, status, parentCourseId, groupId, params, start, end, obc);
	}
	
	/**
	 * Método para buscar cursos
	 */
	public int countCourses(long companyId, String freeText, String language, int status, long parentCourseId, long groupId, LinkedHashMap<String, Object> params){
		return courseFinder.countByKeywords(companyId, freeText, language, status, parentCourseId, groupId, params);
	}
	
	/**
	 * Método para buscar cursos
	 */
	public List<Course> searchCourses(long companyId, String title, String description, String language, int status, long parentCourseId, long groupId, 
			LinkedHashMap<String, Object> params, boolean andOperator, int start, int end,
			OrderByComparator obc){
		return courseFinder.findByC_T_D_S_PC_G(companyId, title, description, language, status, parentCourseId, groupId, params, andOperator, start, end, obc);
	}
	
	/**
	 * Método para buscar cursos
	 */
	public int countCourses(long companyId, String title, String description, String language, int status, long parentCourseId, long groupId, LinkedHashMap<String, Object> params, 
			boolean andOperator){
		return courseFinder.countByC_T_D_S_PC_G(companyId, title, description, language, status, parentCourseId, groupId, params, andOperator);
	}
	
	public List<Course> getByTitleStatusCategoriesTags(String freeText, int status, long[] categories, long[] tags, long companyId, long groupId, long userId, 
			String language, boolean isAdmin, boolean andOperator, int start, int end){
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if(tags != null && tags.length > 0){
			params.put(CourseParams.PARAM_TAGS, tags);
		}
		if(categories != null && categories.length > 0){
			params.put(CourseParams.PARAM_AND_CATEGORIES, categories);
		}
		params.put(CourseParams.PARAM_SEARCH_PARENT_AND_CHILD_COURSES, true);
		if(!isAdmin){
			params.put(CourseParams.PARAM_PERMISSIONS_ADMIN, userId);
		}
		return courseFinder.findByKeywords(companyId, freeText, language, status, 0, groupId, params, start, end, null);
	}
	
	public int countByTitleStatusCategoriesTags(String freeText, int status, long[] categories, long[] tags, long companyId, long groupId, long userId, String language, boolean isAdmin, boolean andOperator){
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if(tags != null && tags.length > 0){
			params.put(CourseParams.PARAM_TAGS, tags);
		}
		if(categories != null && categories.length > 0){
			params.put(CourseParams.PARAM_AND_CATEGORIES, categories);
		}
		params.put(CourseParams.PARAM_SEARCH_PARENT_AND_CHILD_COURSES, true);
		if(!isAdmin){
			params.put(CourseParams.PARAM_PERMISSIONS_ADMIN, userId);
		}
		return courseFinder.countByKeywords(companyId, freeText, language, status, 0, groupId, params);
	}
	
	public List<Course> getParentCoursesByTitleStatusCategoriesTags(String freeText, int status, long[] categories, long[] tags, long companyId, long groupId, long userId, String language, boolean isAdmin, boolean andOperator, int start, int end){
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if(tags != null && tags.length > 0){
			params.put(CourseParams.PARAM_TAGS, tags);
		}
		if(categories != null && categories.length > 0){
			params.put(CourseParams.PARAM_AND_CATEGORIES, categories);
		}
		if(!isAdmin){
			params.put(CourseParams.PARAM_PERMISSIONS_ADMIN, userId);
		}
		return courseFinder.findByKeywords(companyId, freeText, language, status, 0, groupId, params, start, end, null);
	}
	
	public int countParentCoursesByTitleStatusCategoriesTags(String freeText, int status, long[] categories, long[] tags, long companyId, long groupId, long userId, String language, 
			boolean isAdmin, boolean andOperator){
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if(tags != null && tags.length > 0){
			params.put(CourseParams.PARAM_TAGS, tags);
		}
		if(categories != null && categories.length > 0){
			params.put(CourseParams.PARAM_AND_CATEGORIES, categories);
		}
		if(!isAdmin){
			params.put(CourseParams.PARAM_PERMISSIONS_ADMIN, userId);
		}
		return courseFinder.countByKeywords(companyId, freeText, language, status, 0, groupId, params);
	}
	
	public List<Course> getParentCoursesByTitleStatusCategoriesTagsTemplates(String freeText, int status, long[] categories, long[] tags, String templates, long companyId, 
			long groupId, long userId, String language, boolean isAdmin, boolean andOperator, int start, int end){
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if(tags != null && tags.length > 0){
			params.put(CourseParams.PARAM_TAGS, tags);
		}
		if(categories != null && categories.length > 0){
			params.put(CourseParams.PARAM_AND_CATEGORIES, categories);
		}
		if(templates != null && templates.length() > 0){
			params.put(CourseParams.PARAM_TEMPLATES, templates);
		}
		if(!isAdmin){
			params.put(CourseParams.PARAM_PERMISSIONS_ADMIN, userId);
		}
		return courseFinder.findByKeywords(companyId, freeText, language, status, 0, groupId, params, start, end, null);
	}
	
	public int countParentCoursesByTitleStatusCategoriesTagsTemplates(String freeText, int status, long[] categories, long[] tags, String templates, long companyId, 
			long groupId, long userId, String language, boolean isAdmin, boolean andOperator){
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if(tags != null && tags.length > 0){
			params.put(CourseParams.PARAM_TAGS, tags);
		}
		if(categories != null && categories.length > 0){
			params.put(CourseParams.PARAM_AND_CATEGORIES, categories);
		}
		if(templates != null && templates.length() > 0){
			params.put(CourseParams.PARAM_TEMPLATES, templates);
		}
		if(!isAdmin){
			params.put(CourseParams.PARAM_PERMISSIONS_ADMIN, userId);
		}
		return courseFinder.countByKeywords(companyId, freeText, language, status, 0, groupId, params);
	}
	
	public List<Course> getChildCoursesByTitle(String freeText, long parentCourseId, int status, long companyId, long groupId, long userId, String language, boolean isAdmin, 
			boolean andOperator, int start, int end){
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if(!isAdmin){
			params.put(CourseParams.PARAM_PERMISSIONS_ADMIN, userId);
		}
		return courseFinder.findByKeywords(companyId, freeText, language, status, parentCourseId, groupId, params, start, end, null);
	}
	
	public int countChildCoursesByTitle(String freeText, long parentCourseId, int status, long companyId, long groupId, long userId, String language, boolean isAdmin, boolean andOperator){
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if(!isAdmin){
			params.put(CourseParams.PARAM_PERMISSIONS_ADMIN, userId);
		}
		return courseFinder.countByKeywords(companyId, freeText, language, status, parentCourseId, groupId, params);
	}
	
	public List<Course> getCoursesCatalogByTitleCategoriesTags(String freeText, long[] categories, long[] tags, long companyId, long groupId, long userId, String language, int start, int end){
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if(tags != null && tags.length > 0){
			params.put(CourseParams.PARAM_TAGS, tags);
		}
		if(categories != null && categories.length > 0){
			params.put(CourseParams.PARAM_CATEGORIES, categories);
		}
		params.put(CourseParams.PARAM_PERMISSIONS_VIEW, null);
		params.put(CourseParams.PARAM_VISIBLE, true);
		return CourseFinderUtil.findByKeywords(companyId, freeText, language, WorkflowConstants.STATUS_APPROVED, 0, groupId, params, start, end, null);
	}
	
	public int countCoursesCatalogByTitleCategoriesTags(String freeText, long[] categories, long[] tags, long companyId, long groupId, long userId, String language){
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if(tags != null && tags.length > 0){
			params.put(CourseParams.PARAM_TAGS, tags);
		}
		if(categories != null && categories.length > 0){
			params.put(CourseParams.PARAM_CATEGORIES, categories);
		}
		params.put(CourseParams.PARAM_PERMISSIONS_VIEW, null);
		params.put(CourseParams.PARAM_VISIBLE, true);
		return CourseFinderUtil.countByKeywords(companyId, freeText, language, WorkflowConstants.STATUS_APPROVED, 0, groupId, params);
	}
	
	public List<CourseResultView> getMyCourses(long groupId, long userId, ThemeDisplay themeDisplay, String orderByColumn, String orderByType, int start, int end){
		return CourseFinderUtil.getMyCourses(groupId, userId, null, themeDisplay, orderByColumn, orderByType, start, end);
	}
	
	public int countMyCourses(long groupId, long userId, ThemeDisplay themeDisplay){
		return CourseFinderUtil.countMyCourses(groupId, userId, null, themeDisplay);
	}
	
	public List<CourseResultView> getMyCourses(long groupId, long userId, LinkedHashMap<String, Object> params, ThemeDisplay themeDisplay, String orderByColumn, String orderByType, int start, int end){
		return CourseFinderUtil.getMyCourses(groupId, userId, params, themeDisplay, orderByColumn, orderByType, start, end);
	}
	
	public int countMyCourses(long groupId, long userId, LinkedHashMap<String, Object> params, ThemeDisplay themeDisplay){
		return CourseFinderUtil.countMyCourses(groupId, userId, params, themeDisplay);
	}
	
	public boolean hasUserTries(long courseId, long userId){
		return CourseFinderUtil.hasUserTries(courseId, userId);
	}
	
	
	public List<Course> getPublicCoursesByCompanyId(Long companyId, int limit){
		
		Long classNameId = ClassNameLocalServiceUtil.getClassNameId(Course.class.getName());
		
		if(classNameId!=null){
			try {
				DynamicQuery dq = DynamicQueryFactoryUtil.forClass(AssetEntry.class);
				dq.add(PropertyFactoryUtil.forName("companyId").eq(companyId));
				dq.add(PropertyFactoryUtil.forName("classNameId").eq(classNameId));
				dq.add(PropertyFactoryUtil.forName("visible").eq(true));
				dq.setProjection(ProjectionFactoryUtil.distinct(ProjectionFactoryUtil.property("classPK")));
				List<Long> results = (List<Long>)assetEntryLocalService.dynamicQuery(dq,0,limit);

				List<Course> courses = new ArrayList<Course>();
				for(Long courseId : results){
					Course course = coursePersistence.fetchByPrimaryKey(courseId);
					if(course!=null)
						courses.add(course);
				}
				
				return courses;
				
			} catch (SystemException e) {
				if(log.isDebugEnabled())e.printStackTrace();
			}
		}
		
		return null;
	}

	
	
	public List<Course> getChildCourses(long courseId) throws SystemException
	{
		return coursePersistence.findByParentCourseId(courseId);
	}
	
	public List<Course> getChildCourses(long courseId, int start, int end) {
		List<Course> childCourses = new ArrayList<Course>();
		try{
			childCourses = coursePersistence.findByParentCourseId(courseId, start, end);
		}catch(Exception e){
			e.printStackTrace();
		}
		return childCourses;
	}
	
	@Deprecated
	public List<Course> getOpenOrRestrictedChildCourses(long courseId) {
		List<Course> childCourses = new ArrayList<Course>();
		try{
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			int[] types = {GroupConstants.TYPE_SITE_OPEN, GroupConstants.TYPE_SITE_RESTRICTED};
			params.put(CourseParams.PARAM_TYPE, types);
			Locale locale = LocaleUtil.getDefault();
			childCourses = courseFinder.findByKeywords(0, null, locale.getDisplayLanguage(), WorkflowConstants.STATUS_APPROVED, courseId, 0, params, -1, -1, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return childCourses;
	}
	
	
	public int countChildCourses(long courseId) {
		int childCoursesCount = 0;
		try{
			childCoursesCount = coursePersistence.countByParentCourseId(courseId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return childCoursesCount;
	}
	
	@Deprecated
	public int countOpenOrRestrictedChildCourses(long courseId) {
		int childCoursesCount = 0;
		try{
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			int[] types = {GroupConstants.TYPE_SITE_OPEN, GroupConstants.TYPE_SITE_RESTRICTED};
			params.put(CourseParams.PARAM_TYPE, types);
			Locale locale = LocaleUtil.getDefault();
			childCoursesCount = courseFinder.countByKeywords(0, null, locale.getDisplayName(), WorkflowConstants.STATUS_APPROVED, courseId, 0, params);
		}catch(Exception e){
			e.printStackTrace();
		}
		return childCoursesCount;
	}
	
	
	public List<Course> getCoursesParents(long groupId) throws SystemException
	{
		return coursePersistence.filterFindByGroupIdParentCourseId(groupId, 0);
	}
	
	public void addStudentToCourseWithDates(long courseId,long userId,Date allowStartDate,Date allowFinishDate) throws PortalException, SystemException
	{
		Course course=courseLocalService.getCourse(courseId);
		;
			User user = userLocalService.fetchUser(userId);
			if (!GroupLocalServiceUtil.hasUserGroup(user.getUserId(), course.getGroupCreatedId())) {
				GroupLocalServiceUtil.addUserGroups(user.getUserId(), new long[] { course.getGroupCreatedId() });
				//sendEmail(user,course);
			}
			
			UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { user.getUserId() },
					course.getGroupCreatedId(), RoleLocalServiceUtil.getRole(user.getCompanyId(), RoleConstants.SITE_MEMBER).getRoleId());
			CourseResult courseResult=courseResultLocalService.getCourseResultByCourseAndUser(courseId, user.getUserId());
			if(courseResult==null)
			{
				courseResultLocalService.create(courseId, user.getUserId(), allowStartDate, allowFinishDate);
			}
			else
			{
				courseResult.setAllowStartDate(allowStartDate);
				courseResult.setAllowFinishDate(allowFinishDate);
				courseResultLocalService.updateCourseResult(courseResult);
			}
			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), userId, AuditConstants.REGISTER, null);		 
		
	}

	public void editUserInscriptionDates(long courseId,long userId,Date allowStartDate,Date allowFinishDate) throws PortalException, SystemException
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		Course course=courseLocalService.getCourse(courseId);
		
			User user = userLocalService.getUser(userId);
			if (!GroupLocalServiceUtil.hasUserGroup(user.getUserId(), course.getGroupCreatedId())) {
				return;
			}		
			CourseResult courseResult=courseResultLocalService.getCourseResultByCourseAndUser(courseId, user.getUserId());
			if(courseResult==null)
			{
				courseResultLocalService.create(courseId, user.getUserId(), allowStartDate, allowFinishDate);
			}
			else
			{
				courseResult.setAllowStartDate(allowStartDate);
				courseResult.setAllowFinishDate(allowFinishDate);
				courseResultLocalService.updateCourseResult(courseResult);
			}
			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.UPDATE, null);		 
		
	}
	
	/**
	 * Se van a realizar las siguientes comprobaciones:
	 * - Curso cerrado
	 * - Que pertenezcas a la comunidad
	 * - Que tengas permiso para acceder al curso
	 * - Comprobar que tenga una convocatoria en fecha
	 * - Que el usuario tenga fechas propias para realizarlo y estén en fecha
	 * @param courseId Id el curso
	 * @param user Usuario
	 * @return true si el curso está bloqueado, false en caso contrario
	 */
	
	public boolean  isLocked(Course course, User user){

		return course.isLocked(user);
	}
	
	/**
	 * Comprueba si un usuario puede acceder a los cursos/modulos/actividades bloqueadas
	 * @param groupCreatedId id del grupo creado para el curso
	 * @param user usuario
	 * @return true en caso de que pueda acceder a bloqueados
	 */
	
	public boolean canAccessLock(long groupCreatedId, User user){
		//Si es administrador
		if(PortalUtil.isOmniadmin(user.getUserId())){
			return true;
		}

		PermissionChecker permissionChecker = null;
		try {
			permissionChecker = PermissionCheckerFactoryUtil.create(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Si tiene permiso ACCESSLOCK
		
		if(permissionChecker != null && permissionChecker.hasPermission(groupCreatedId, "com.liferay.lms.model",groupCreatedId,"ACCESSLOCK")){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns the last module date in course, because the course end date is for enrollments.
	 * 
	 * @param courseId Course Identifier
	 * @return Course last module date.
	 */
	public Date getLastModuleDateInCourse (long courseId){
		Date lastModuleDate = null;
		
		try{
			Course course = CourseLocalServiceUtil.fetchCourse(courseId);
			if(course!=null){
				for(Module module:ModuleLocalServiceUtil.findAllInGroup(course.getGroupCreatedId())){
					if(lastModuleDate==null){
						lastModuleDate=module.getEndDate();
					} else if(module.getEndDate()!=null && lastModuleDate.before(module.getEndDate())){
						lastModuleDate=module.getEndDate();
					}
				}
			}		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return lastModuleDate;
	}
	
	
	/**
	 * Returns the first module date in course, because the course end date is for enrollments.
	 * 
	 * @param courseId Course Identifier
	 * @return Course last module date.
	 */
	public Date getFirstModuleDateInCourse (long courseId){
		Date firstModuleDate = null;
		
		try{
			Course course = CourseLocalServiceUtil.fetchCourse(courseId);
			if(course!=null){
				for(Module module:ModuleLocalServiceUtil.findAllInGroup(course.getGroupCreatedId())){
					if(firstModuleDate==null){
						firstModuleDate=module.getStartDate();
					} else if(module.getStartDate()!=null && firstModuleDate.after(module.getStartDate())){
						firstModuleDate=module.getStartDate();
					}
				}
			}		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return firstModuleDate;
	}
	
	public String getImageURL(Course course, ThemeDisplay themeDisplay){
		return course.getImageURL(themeDisplay);
	}
	
	
	/**
	 * Service that validates the course inscription as it is validated in web.
	 * 
	 * @param courseId
	 * @param userId 
	 * @return ok or error and the error description.
	 * @throws PortalException
	 * @throws SystemException
	 */
	public String addStudentToCourseByUserId(long courseId, long userId, long teamId, ServiceContext serviceContext) throws PortalException, SystemException {
		String result="ok";
		try{
			Course course=CourseLocalServiceUtil.getCourse(courseId);
			int i = 0;
			boolean enoughCompetences = true;
			CourseCompetence courseCompetence = null;
			if(userId > 0){
				//1. Si no estÃ¡ ya inscrito
				if(!GroupLocalServiceUtil.hasUserGroup(userId,course.getGroupCreatedId())){
					Group group = GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
					
					//2. Fecha actual dentro del periodo de inscripcion
					Date now=new Date(System.currentTimeMillis());
					Date startDate = course.getStartDate();
					Date endDate = course.getEndDate();
					if(teamId>0){
						Schedule sch = scheduleLocalService.getScheduleByTeamId(teamId);	
						if(sch!=null){
							startDate = sch.getStartDate();
							endDate = sch.getEndDate();
						}
					}
					
					if((startDate.before(now) && endDate.after(now))){
						//3. Control de competencias 
						List<CourseCompetence> courseCompetences = CourseCompetenceLocalServiceUtil.findBycourseId(course.getCourseId(), true);
						//Busco si al usuario le falta alguna competencia que es necesaria para la inscripcion al curso
						while (i < courseCompetences.size() && enoughCompetences){
							courseCompetence = courseCompetences.get(i);
							UserCompetence uc = UserCompetenceLocalServiceUtil.findByUserIdCompetenceId(userId, courseCompetence.getCompetenceId());
							if(uc == null){
								enoughCompetences = false;
								log.debug("Al usuario le falta la competencia obligatoria con id: " + courseCompetence.getCompetenceId() + " para poder ser inscrito al curso");
							}
							i++;
						}
						if(enoughCompetences){
							// 4. El mÃ¡ximo de inscripciones del curso no ha sido superado
							if(course.getMaxusers()<=0 || UserLocalServiceUtil.getGroupUsersCount(course.getGroupCreatedId()) < course.getMaxusers()){
								if(group.getType()==GroupConstants.TYPE_SITE_OPEN){
									Role sitemember=RoleLocalServiceUtil.getRole(course.getCompanyId(), RoleConstants.SITE_MEMBER) ;
									
									if(teamId>0){
				            			long[] userIds = new long[1];
				            			userIds[0] = userId;	
				            			if(!UserLocalServiceUtil.hasTeamUser(teamId, userId)){
				            				UserLocalServiceUtil.addTeamUsers(teamId, userIds);	
				            			}			
				            		}
									
									GroupLocalServiceUtil.addUserGroups(userId, new long[]{group.getGroupId()});
									UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { userId }, group.getGroupId(), sitemember.getRoleId());
									SocialActivityLocalServiceUtil.addActivity(userId, group.getGroupId(), Group.class.getName(), group.getGroupId(), com.liferay.portlet.social.model.SocialActivityConstants.TYPE_SUBSCRIBE, "", userId);
									User u = UserLocalServiceUtil.getUser(userId);
									log.debug("Inscribimos usuario con id: " + u.getUserId() + " (" + u.getScreenName() + ")" + " en la comunidad con id: " + group.getGroupId() + " (" + group.getName() + ")");
								}else{
									if(group.getType()==GroupConstants.TYPE_SITE_RESTRICTED){
										if(!MembershipRequestLocalServiceUtil.hasMembershipRequest(userId, group.getGroupId(), MembershipRequestConstants.STATUS_PENDING)){
											MembershipRequestLocalServiceUtil.addMembershipRequest(userId, group.getGroupId(), "Enroll petition", serviceContext);
											SocialActivityLocalServiceUtil.addActivity(userId, group.getGroupId(), Group.class.getName(), group.getGroupId(), com.liferay.portlet.social.model.SocialActivityConstants.TYPE_SUBSCRIBE, "", userId);
											log.debug("Lanzamos peticion de inscripcion del usuario " + userId + " en la comunidad con id: " + group.getGroupId() + " (" + group.getName() + ")");
											result="warning-restricted-course";
										}
									}else{
										if(group.getType()==GroupConstants.TYPE_SITE_PRIVATE){
											log.debug("Curso privado, no se pueden inscribir usuarios");
											result="error-private-course";
										}
									}
								}
							}else{
								log.debug("El curso esta completo, el maximo numero de inscripciones ha sido alcanzado");
								result = "error-complete-course";
							}
						}else{
							log.debug("El usuario no tiene las competencias necesarias del curso");
							result = "error-competences";						}
					}else{
						log.debug("El curso " + courseId + " se encuentra cerrado.");
						log.debug("Fecha Incicio " + course.getStartDate());
						log.debug("Fecha Fin " + course.getEndDate());
						result =  "error-course-closed";
					}
				}else{
					log.debug("el usuario: " + userId + ", ya pertenece al curso: " + courseId);
					result="error-user-suscribed";
				}
			}else{
				log.debug("Usuario no valido, userId: " + userId);
				result="error-not-valid-user";
			}
		}catch (PortalException e){
			result = e.getMessage();
			e.printStackTrace();
		}catch (SystemException e){
			result = e.getMessage();
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 
	 * @param groupId
	 * @param userId
	 * @param teamId
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public boolean validateAddUserToCourse(long groupId, long userId, long teamId) throws PortalException, SystemException {
		boolean result=false;
		try{
			Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(groupId);
			int i = 0;
			boolean enoughCompetences = true;
			CourseCompetence courseCompetence = null;
			if(userId > 0){
				//1. Si no estÃ¡ ya inscrito
				if(!GroupLocalServiceUtil.hasUserGroup(userId,course.getGroupCreatedId())){
					Group group = GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
					
					//2. Fecha actual dentro del periodo de inscripcion
					Date now=new Date(System.currentTimeMillis());
					Date startDate = course.getStartDate();
					Date endDate = course.getEndDate();
					if(teamId>0){
						Schedule sch = scheduleLocalService.getScheduleByTeamId(teamId);	
						if(sch!=null){
							startDate = sch.getStartDate();
							endDate = sch.getEndDate();
						}
					}
					
					if((startDate.before(now) && endDate.after(now))){
						//3. Control de competencias 
						List<CourseCompetence> courseCompetences = CourseCompetenceLocalServiceUtil.findBycourseId(course.getCourseId(), true);
						//Busco si al usuario le falta alguna competencia que es necesaria para la inscripcion al curso
						while (i < courseCompetences.size() && enoughCompetences){
							courseCompetence = courseCompetences.get(i);
							UserCompetence uc = UserCompetenceLocalServiceUtil.findByUserIdCompetenceId(userId, courseCompetence.getCompetenceId());
							if(uc == null){
								enoughCompetences = false;
								log.debug("Al usuario le falta la competencia obligatoria con id: " + courseCompetence.getCompetenceId() + " para poder ser inscrito al curso");
							}
							i++;
						}
						if(enoughCompetences){
							// 4. El mÃ¡ximo de inscripciones del curso no ha sido superado
							if(course.getMaxusers()<=0 || UserLocalServiceUtil.getGroupUsersCount(course.getGroupCreatedId()) < course.getMaxusers()){
								if(group.getType()==GroupConstants.TYPE_SITE_OPEN){
									result=true;
								}else if(group.getType()==GroupConstants.TYPE_SITE_RESTRICTED){
									if(!MembershipRequestLocalServiceUtil.hasMembershipRequest(userId, group.getGroupId(), MembershipRequestConstants.STATUS_PENDING)){
										result=true;
									}								
								}
							}
						}
					}
				}
			}
		}catch (PortalException e){
			e.printStackTrace();
		}catch (SystemException e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	public List<Group> getDistinctCourseGroups(long companyId){
		return courseFinder.getDistinctCourseGroups(companyId);
	}
	
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/*****************************************METODOS PARA BÚSQUEDA DE EDITORES*****************************************************/
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	
	/**
	 * Usar este método para la búsqueda de editores de un curso
	 * @param courseId id del curso
	 * @param companyId id de company
	 * @param screenName nombre de usuario
	 * @param firstName nombre
	 * @param lastName apellido
	 * @param emailAddress direccion de correo
	 * @param status estado del usuario (WorkflowConstants)
	 * @param teamIds array de long con los ids de los equipos
	 * @param andOperator true si queremos que coincidan screenname, firstname, lastname y emailaddress, false en caso contrario
	 * @param start inicio de la lista
	 * @param end fin de la lista
	 * @param obc orden de la lista
	 * @return List<User> estudiantes del curso
	 */
	public List<User> getEditorsFromCourse(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, long[] teamIds, boolean andOperator, 
									int start, int end, OrderByComparator obc){
		return courseFinder.findEditors(courseId, companyId, screenName, firstName, lastName, emailAddress, status, teamIds, andOperator, start, end, obc);
	}
	
	/**
	 * Usar este método para contar los editores de un curso
	 * @param courseId id del curso
	 * @param companyId id de company
	 * @param screenName nombre de usuario
	 * @param firstName nombre
	 * @param lastName apellido
	 * @param emailAddress direccion de correo
	 * @param status estado del usuario (WorkflowConstants)
	 * @param teamIds array de long con los ids de los equipos
	 * @param andOperator true si queremos que coincidan screenname, firstname, lastname y emailaddress, false en caso contrario
	 * @return
	 */
	public int countEditorsFromCourse(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, long[] teamIds, boolean andOperator){
		return courseFinder.countEditors(courseId, companyId, screenName, firstName, lastName, emailAddress, status, teamIds, andOperator);
	}
	
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/***************************************METODOS PARA BÚSQUEDA DE PROFESORES****************************************************/
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	
	/**
	 * Usar este método para la búsqueda de profesores de un curso
	 * @param courseId id del curso
	 * @param companyId id de company
	 * @param screenName nombre de usuario
	 * @param firstName nombre
	 * @param lastName apellido
	 * @param emailAddress direccion de correo
	 * @param status estado del usuario (WorkflowConstants)
	 * @param teamIds array de long con los ids de los equipos
	 * @param andOperator true si queremos que coincidan screenname, firstname, lastname y emailaddress, false en caso contrario
	 * @param start inicio de la lista
	 * @param end fin de la lista
	 * @param obc orden de la lista
	 * @return List<User> estudiantes del curso
	 */
	public List<User> getTeachersFromCourse(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, long[] teamIds, boolean andOperator, 
									int start, int end, OrderByComparator obc){
		return courseFinder.findTeachers(courseId, companyId, screenName, firstName, lastName, emailAddress, status, teamIds, andOperator, start, end, obc);
	}
	
	/**
	 * Usar este método para contar los profesores de un curso
	 * @param courseId id del curso
	 * @param companyId id de company
	 * @param screenName nombre de usuario
	 * @param firstName nombre
	 * @param lastName apellido
	 * @param emailAddress direccion de correo
	 * @param status estado del usuario (WorkflowConstants)
	 * @param teamIds array de long con los ids de los equipos
	 * @param andOperator true si queremos que coincidan screenname, firstname, lastname y emailaddress, false en caso contrario
	 * @return
	 */
	public int countTeachersFromCourse(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, long[] teamIds, boolean andOperator){
		return courseFinder.countTeachers(courseId, companyId, screenName, firstName, lastName, emailAddress, status, teamIds, andOperator);
	}
	
	public List<User> getTeachersFromCourse(long courseId) {
		List<User> users = null;
		try{
			
			Course course = courseLocalService.fetchCourse(courseId);
						
			users = courseFinder.findTeachers(courseId, course.getCompanyId(), null, null, null, null, WorkflowConstants.STATUS_APPROVED, null, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return users;
		
	}
	
	@Deprecated
	public List<User> getTeachersFromCourse(Course course, long teacherRoleId) {
		return courseFinder.findTeachers(course.getCourseId(), course.getCompanyId(), null, null, null, null, WorkflowConstants.STATUS_APPROVED, null, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}
	
	
	@Deprecated
	public List<User> getTeachersFromCourseTeams(Course course, long teacherRoleId, long userId){
		List<User> users = null;
		try{
			
			//Primero comprobamos si el usuario pertenece a algún equipo
			List<Team> userTeams = null;
			try {
				userTeams=TeamLocalServiceUtil.getUserTeams(userId, course.getGroupCreatedId());
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(userTeams != null && userTeams.size() > 0){
				log.debug("CourseTeachers::Pedimos los profesores de los equipos");
				String teamIds = "";
				for(int i = 0; i < userTeams.size();i++){
					teamIds += userTeams.get(i).getTeamId() + ",";
				}
				if(teamIds.length() > 0) teamIds = teamIds.substring(0, teamIds.length()-1);
				
				LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();
				userParams.put("usersGroups", course.getGroupCreatedId());
				userParams.put("userGroupRole", new Long[]{course.getGroupCreatedId(), teacherRoleId});
				userParams.put("userTeamIds", new CustomSQLParam("INNER JOIN users_teams ON user_.userId = users_teams.userId "
						+ "WHERE users_teams.teamId IN (" + teamIds + ")", null));
				
				users = UserLocalServiceUtil.search(course.getCompanyId(), "", 
						WorkflowConstants.STATUS_APPROVED, userParams, 
						QueryUtil.ALL_POS, QueryUtil.ALL_POS,(OrderByComparator)null);
			}
			 
			if(users == null){
				log.debug("CourseTeachers::Pedimos los profesores");
				users = CourseLocalServiceUtil.getTeachersFromCourse(course, teacherRoleId);
			}
						

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return users;
	}
	
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/***************************************METODOS PARA BÚSQUEDA DE ESTUDIANTES****************************************************/
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	
	/**
	 * Usar este método para la búsqueda de estudiantes de un curso
	 * @param courseId id del curso
	 * @param companyId id de company
	 * @param screenName nombre de usuario
	 * @param firstName nombre
	 * @param lastName apellido
	 * @param emailAddress direccion de correo
	 * @param status estado del usuario (WorkflowConstants)
	 * @param teamIds array de long con los ids de los equipos
	 * @param andOperator true si queremos que coincidan screenname, firstname, lastname y emailaddress, false en caso contrario
	 * @param start inicio de la lista
	 * @param end fin de la lista
	 * @param obc orden de la lista
	 * @return List<User> estudiantes del curso
	 */
	public List<User> getStudentsFromCourse(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, long[] teamIds, boolean andOperator, 
									int start, int end, OrderByComparator obc){
		return courseFinder.findStudents(courseId, companyId, screenName, firstName, lastName, emailAddress, status, teamIds, andOperator, start, end, obc);
	}
	
	/**
	 * Usar este método para contar los estudiantes de un curso
	 * @param courseId id del curso
	 * @param companyId id de company
	 * @param screenName nombre de usuario
	 * @param firstName nombre
	 * @param lastName apellido
	 * @param emailAddress direccion de correo
	 * @param status estado del usuario (WorkflowConstants)
	 * @param teamIds array de long con los ids de los equipos
	 * @param andOperator true si queremos que coincidan screenname, firstname, lastname y emailaddress, false en caso contrario
	 * @return
	 */
	public int countStudentsFromCourse(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, long[] teamIds, boolean andOperator){
		return courseFinder.countStudents(courseId, companyId, screenName, firstName, lastName, emailAddress, status, teamIds, andOperator);
	}
	
	public int getStudentsFromCourseCount(long courseId) throws SystemException, PortalException{
		return getStudentsFromCourseCount(courseId, 0);
		
	}
	public int getStudentsFromCourseCount(long courseId, long teamId) throws SystemException, PortalException{
		return getStudentsFromCourseCount(courseId, 0,null,null,null,null,true);
	}
	
	public int getStudentsFromCourseCount(long courseId, long teamId, String firstName, String lastName, String screenName, String emailAddress, boolean andComparator) throws SystemException, PortalException{
		
		Course course = CourseLocalServiceUtil.getCourse(courseId);	
		long[] teamIds = null;
		if(teamId > 0){
			teamIds = new long[1];
			teamIds[0] = teamId;
		}
		return countStudentsFromCourse(courseId, course.getCompanyId(), screenName, firstName, lastName, emailAddress, WorkflowConstants.STATUS_APPROVED, teamIds, andComparator);
	}
	
	public List<User> getStudentsFromCourse(Course course) {		
		return getStudentsFromCourse(course.getCompanyId(), course.getGroupCreatedId());
	}
	
	public List<User> getStudentsFromCourse(long companyId, long courseGroupCreatedId) {
		return getStudentsFromCourse(companyId, courseGroupCreatedId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, 0, null, null, null, null, true);
	}
	
	public List<User> getStudentsFromCourse(long companyId, long courseGroupCreatedId, long teamId){
		
		return getStudentsFromCourse(companyId, courseGroupCreatedId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, teamId, null, null, null, null, true);

	}
	
	public List<User> getStudentsFromCourse(long companyId, long courseGroupCreatedId, int start, int end,long teamId, String firstName, String lastName, String screenName, String emailAddress, boolean andOperator) {
		
		try {
			long[] teamIds = null;
			if(teamId > 0){
				teamIds = new long[1];
				teamIds[0] = teamId;
			}
			Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(courseGroupCreatedId);
			OrderByComparator obc = null;
			PortletPreferences portalPreferences = PortalPreferencesLocalServiceUtil.getPreferences(companyId, companyId, 1);
			if(Boolean.parseBoolean(portalPreferences.getValue("users.first.last.name", "false"))){
				obc = new UserLastNameComparator(true);
			}else{
				obc = new UserFirstNameComparator(true);
			}
			return getStudentsFromCourse(course.getCourseId(), companyId, screenName, firstName, lastName, emailAddress, WorkflowConstants.STATUS_APPROVED, teamIds, andOperator, start, end, obc);

		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public int getStudentsFromCourseCount(long companyId, long courseGroupCreatedId, long teamId, String firstName, String lastName, String screenName, String emailAddress, boolean andOperator) {
		
		try {
			Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(courseGroupCreatedId);
			long[] teamIds = null;
			if(teamId > 0){
				teamIds = new long[1];
				teamIds[0] = teamId;
			}
			return countStudentsFromCourse(course.getCourseId(), companyId, screenName, firstName, lastName, emailAddress, WorkflowConstants.STATUS_APPROVED, teamIds, andOperator);
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return 0;
		}
	}
	
	public List<User> getStudents(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, long[] teamIds, boolean andOperator, int start, int end,OrderByComparator comparator){
		return getStudentsFromCourse(courseId, companyId, screenName,firstName, lastName, emailAddress, WorkflowConstants.STATUS_APPROVED, teamIds, andOperator, start, end, comparator);
	}
	
	public int countStudents(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, long[] teamIds, boolean andOperator){
		return countStudentsFromCourse(courseId, companyId, screenName,firstName, lastName, emailAddress, WorkflowConstants.STATUS_APPROVED, teamIds, andOperator);
	}
	
	public List<User> getStudents(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, boolean andOperator, int start, int end,OrderByComparator comparator){
		return getStudentsFromCourse(courseId, companyId, screenName,firstName, lastName, emailAddress, WorkflowConstants.STATUS_APPROVED, null, andOperator, start, end, comparator);
	}
	
	public int countStudents(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress,boolean andOperator){
		return countStudentsFromCourse(courseId, companyId, screenName,firstName, lastName, emailAddress, WorkflowConstants.STATUS_APPROVED, null, andOperator);
	}
	
	public List<User> getStudents(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, long teamId, boolean andOperator, int start, int end,OrderByComparator comparator){
		long[] teamIds = null;
		if(teamId > 0){
			teamIds = new long[1];
			teamIds[0] = teamId;
		}
		return getStudentsFromCourse(courseId, companyId, screenName,firstName, lastName, emailAddress, status, teamIds, andOperator, start, end, comparator);
	}
	
	public int countStudents(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, long teamId,boolean andOperator){
		long[] teamIds = null;
		if(teamId > 0){
			teamIds = new long[1];
			teamIds[0] = teamId;
		}
		return countStudentsFromCourse(courseId, companyId, screenName,firstName, lastName, emailAddress, status, teamIds, andOperator);
	}
	
	public int countStudentsStatus(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, boolean andOperator){
		return countStudentsFromCourse(courseId, companyId, screenName,firstName, lastName, emailAddress, status, null, andOperator);
	}
	
	public List<AssetEntry> getMostRecentCourseEntries(long groupId, String orderBy, String orderByType,int start, int end){
		List<AssetEntry> results = new ArrayList<AssetEntry>();
		try {
			AssetEntryQuery query =new AssetEntryQuery();
			query.setClassName(Course.class.getName());
			long[] groupIds=new long[1];
			groupIds[0]=groupId;
			query.setGroupIds(groupIds);
			query.setExcludeZeroViewCount(false);
			query.setEnablePermissions(true);
			query.setOrderByCol1(orderBy);
			query.setOrderByType1(orderByType);
			query.setStart(start);
			query.setEnd(end);
			results = AssetEntryLocalServiceUtil.getEntries(query);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}
}
