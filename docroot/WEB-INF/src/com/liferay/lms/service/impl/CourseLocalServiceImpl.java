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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.learningactivity.courseeval.CourseEval;
import com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.base.CourseLocalServiceBaseImpl;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
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
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.ModelHintsConstants;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.social.model.SocialActivityCounterDefinition;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.model.SocialActivitySetting;
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
	private static final int DEFAULT_EVALUATIONS = 3;
	
	Log log = LogFactoryUtil.getLog(CourseLocalServiceImpl.class);
	
	public java.util.List<Course> getCoursesOfGroup(long groupId) throws SystemException
	{
		return coursePersistence.findByGroupId(groupId);
	}
	public java.util.List<Course> getOpenCoursesOfGroup(long groupId) throws SystemException
	{
		return coursePersistence.findByGroupId(groupId);
	}
	public java.util.List<Course> getCourses(long companyId) throws SystemException
	{
		return coursePersistence.findByCompanyId(companyId);
	}
	public long countByGroupId(long groupId) throws SystemException
	{
		return coursePersistence.countByGroupId(groupId);
	}
	public Course fetchByGroupCreatedId(long groupId) throws SystemException
	{
		return coursePersistence.fetchByGroupCreatedId(groupId);
	}
	
	public Course addCourse (String title, String description,String summary,String friendlyURL, Locale locale,
			java.util.Date createDate,java.util.Date startDate,java.util.Date endDate,long layoutSetPrototypeId,int typesite,ServiceContext serviceContext, long calificationType, int maxUsers,boolean isFromClone)
			throws SystemException, PortalException {
		return addCourse(title, description, summary, friendlyURL, locale, createDate, startDate, endDate, layoutSetPrototypeId, typesite, 0, calificationType, maxUsers, serviceContext,isFromClone);
	}
	
	public java.util.List<Course> getUserCourses(long userId) throws PortalException, SystemException
	{
		User usuario= userLocalService.getUser(userId);
		java.util.List<Group> groups= GroupLocalServiceUtil.getUserGroups(usuario.getUserId());
		java.util.List<Course> results=new java.util.ArrayList<Course>();
		
		for(Group groupCourse:groups)
		{
			
			
			Course course=courseLocalService.fetchByGroupCreatedId(groupCourse.getGroupId());
			if(course!=null)
			{
				results.add(course);
			}
		}
		return results;

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

	public Course addCourse (String title, String description,String summary,String friendlyURL, Locale locale,
			java.util.Date createDate,java.util.Date startDate,java.util.Date endDate,long layoutSetPrototypeId,int typesite, long CourseEvalId, long calificationType, int maxUsers,ServiceContext serviceContext,boolean isfromClone)
			throws SystemException, PortalException {
		LmsPrefs lmsPrefs=lmsPrefsLocalService.getLmsPrefsIni(serviceContext.getCompanyId());
		long userId=serviceContext.getUserId();
		Course course = coursePersistence.create(counterLocalService.increment(Course.class.getName()));
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
			course.setDescription(description,locale);
			course.setTitle(title,locale);
			course.setCreateDate(createDate);
			course.setModifiedDate(createDate);
			course.setStartDate(startDate);
			course.setEndDate(endDate);
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

			coursePersistence.update(course, true);
			
			resourceLocalService.addResources(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), userId,Course.class.getName(), course.getPrimaryKey(), false,true, true);
			AssetEntry assetEntry=assetEntryLocalService.updateEntry(userId, course.getGroupId(), Course.class.getName(),
					course.getCourseId(), course.getUuid(),0, serviceContext.getAssetCategoryIds(),
					serviceContext.getAssetTagNames(), true, null, null,new java.util.Date(System.currentTimeMillis()), null,
					ContentTypes.TEXT_HTML, course.getTitle(), course.getDescription(locale), summary, null, null, 0, 0,null, false);
			assetLinkLocalService.updateLinks(
					userId, assetEntry.getEntryId(), serviceContext.getAssetLinkEntryIds(),
					AssetLinkConstants.TYPE_RELATED);
			
			//A�adimos el rol Teacher al usuario que crea el blog
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
			throw e;
		}catch(Exception e){
			if(log.isInfoEnabled()){
				log.info("CourseLocalServiceImpl.addCourse(): " + e + "message: " + e.getMessage());
			}
			throw new PortalException(e.getMessage());
			
		}
		
		
		// METODO METIDO POR MIGUEL
		if(!isfromClone){
			Module newModule;
			try {
				
				
				newModule = ModuleLocalServiceUtil.createModule(CounterLocalServiceUtil.increment(Module.class.getName()));
				newModule.setTitle(LanguageUtil.get(locale,"com.liferay.lms.model.module"), locale);
				newModule.setDescription(LanguageUtil.get(locale,"description"), locale);
				
				newModule.setCompanyId(course.getCompanyId());
				newModule.setGroupId(course.getGroupCreatedId());
				newModule.setUserId(course.getUserId());
				newModule.setOrdern(newModule.getModuleId());
				
				/*
				Calendar start = Calendar.getInstance();
				start.setTimeInMillis(module.getStartDate().getTime() + TimeUnit.MILLISECONDS.convert(days, TimeUnit.DAYS));
				Calendar stop = Calendar.getInstance();
				stop.setTimeInMillis(module.getEndDate().getTime() + TimeUnit.MILLISECONDS.convert(days, TimeUnit.DAYS));
				*/
				
				//System.out.println(" startDate: "+ start.getTime() +"   -> "+module.getStartDate());
				//System.out.println(" stopDate : "+ stop.getTime()  +"   -> "+module.getEndDate());
				
				newModule.setStartDate(startDate);
				newModule.setEndDate(endDate);
				
				
				ModuleLocalServiceUtil.addModule(newModule);
				
				System.out.println("    + Module : " + newModule.getTitle(Locale.getDefault()) +"("+newModule.getModuleId()+")" );
				
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

		//auditing
		AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.ADD, null);
		return course;
	}

	@Indexable(type=IndexableType.REINDEX)
	public Course addCourse (String title, String description,String friendlyURL, Locale locale,
			java.util.Date createDate,java.util.Date startDate,java.util.Date endDate,
		ServiceContext serviceContext, long calificationType)
			throws SystemException, 
			PortalException {
		
				Course course = this.addCourse(title, description, description, friendlyURL, locale, createDate, startDate, endDate, serviceContext, calificationType);
				
				//auditing
				AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.ADD, null);
				
				return course;
			}
	
	private static User getAdministratorUser(long companyId) throws PortalException, SystemException
	{
		// El nombre del rol "Administrator" no puede cambiar a trav�s del UI, es un caso excepcional de s�lo lectura
		// Sin embargo pueden haber varios administradores (con el rol "Administrator"),
		// hacemos lo siguiente: devolvemos el que tenga userName "test" y as� tenderemos
		// a devolver siempre el mismo, si no hay un administrador "test" (pues puede cambiarse) devolvemos el primero.
		long adminRoleId = RoleLocalServiceUtil.getRole(companyId, "Administrator").getRoleId();
		List<User> adminList = UserLocalServiceUtil.getRoleUsers(adminRoleId);
		for(User user : adminList)
		{
			if (user.getScreenName().equals("test"))
				return user;
		}
		return adminList.get(0); // Devolvemos el primero.
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
	
	private void importLayouts(long userId,Group grupo,LayoutSetPrototype lsProto) throws PortalException, SystemException
	{
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
	private static Map<String, String[]> getLayoutSetPrototypeParameters() 
	{
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
//		parameterMap.put(
//				PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
//				new String[] {Boolean.FALSE.toString()});
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
	public void setVisible(long courseId,boolean visible) throws PortalException, SystemException
	{
	    assetEntryLocalService.updateVisible(Course.class.getName(), courseId, visible);
	}
	
	@Indexable(type=IndexableType.REINDEX)
	public Course modCourse (Course course,String summary, 
			ServiceContext serviceContext)
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
			coursePersistence.update(course, true);
			long userId=serviceContext.getUserId();
			Group theGroup=GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
			theGroup.setName(course.getTitle(locale, true));
			theGroup.setDescription(summary);
			
			int type=GroupConstants.TYPE_SITE_OPEN;
			try{
				if (serviceContext.getAttribute("type") != null) {
					type = Integer.valueOf(serviceContext.getAttribute("type").toString());
				}
			}catch(NumberFormatException nfe){				
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
					serviceContext.getAssetTagNames(), true, null, null,
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
	public Course modCourse (Course course, 
			ServiceContext serviceContext)
			throws SystemException, PortalException {
			course.setExpandoBridgeAttributes(serviceContext);
			
			course = this.modCourse(course, "", serviceContext);

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
			theGroup.setFriendlyURL(course.getFriendlyURL() + "_desac");
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
			assetEntryLocalService.deleteEntry(LearningActivity.class.getName(),course.getCourseId());
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
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(Course.class, classLoader)
				.add(PropertyFactoryUtil.forName("groupCreatedId").eq(groupCreatedId));
	
		List<Course> list = (List<Course>)coursePersistence.findWithDynamicQuery(consulta);
		
		if(!list.isEmpty() && list.size()>0){
			return list.get(0);
		}

		return null;
		
		//return coursePersistence.fetchByGroupCreatedId(groupCreatedId);
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
	
	
	public List<User> getStudentsFromCourse(Course course) {		
		return getStudentsFromCourse(course.getCompanyId(), course.getGroupCreatedId());
	}
	
	public List<User> getStudentsFromCourse(long companyId, long courseGropupCreatedId) {
		List<User> students = new ArrayList<User>();
		List<User> usersExcluded = new ArrayList<User>();
		
		LmsPrefs prefs;
		try {
			prefs = LmsPrefsLocalServiceUtil.getLmsPrefs(companyId);
			
			long teacherRoleId=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
			long editorRoleId=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
			
			List<User> users = UserLocalServiceUtil.getGroupUsers(courseGropupCreatedId);
			
			List<UserGroupRole> teachers=UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroupAndRole(courseGropupCreatedId, teacherRoleId);
			for(UserGroupRole teacher: teachers) {
				usersExcluded.add(teacher.getUser());
			}
			List<UserGroupRole> editors=UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroupAndRole(courseGropupCreatedId, editorRoleId);
			for(UserGroupRole editor: editors) {
				usersExcluded.add(editor.getUser());
			}
			if(Validator.isNotNull(usersExcluded)) {
				for(User user: users) {
					if( !(usersExcluded.contains(user)) && !(students.contains(user)))
						students.add(user);
				}
			}
			
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		return students;
	}
}