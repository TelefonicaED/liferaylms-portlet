package com.liferay.lms.hook.events;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import com.liferay.lms.NoSuchPrefsException;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.expando.DuplicateColumnNameException;
import com.liferay.portlet.expando.DuplicateTableNameException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBCategory;

public class StartupAction extends SimpleAction {

	public Role courseTeacher;
	public Role courseEditor;
	public Role courseCreator;
	public LayoutSetPrototype layoutSetPrototype;
	private Log _log = LogFactoryUtil.getLog(StartupAction.class); 
	
	@Override
	public void run(String[] ids) throws ActionException {
		try {
			for (int i = 0; i < ids.length; i++) {
				doRun(GetterUtil.getLong(ids[i]));
			}
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	public void doRun(long companyId) throws Exception {
		LmsPrefs lmsPrefs=null;
		
		try{
			lmsPrefs=LmsPrefsLocalServiceUtil.getLmsPrefs(companyId);
		}
		catch(NoSuchPrefsException e){
			lmsPrefs=null;
		}
		if(lmsPrefs==null){
			createDefaultRoles(companyId);
			createDefaultSiteTemplate(companyId);
			createDefaultPreferences(companyId);
		}
		
		ExpandoTable table3 = getExpandoTable(companyId, User.class.getName(), ExpandoTableConstants.DEFAULT_TABLE_NAME);
		if (table3 != null) {
				createExpandoColumn(table3, "deregister-mail", ExpandoColumnConstants.BOOLEAN,
							ExpandoColumnConstants.INDEX_TYPE_TEXT, ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_CHECKBOX, false, true);
		}
		
	}

	public LmsPrefs createDefaultPreferences(long companyId) 
	{
		LmsPrefs lmsPrefs=LmsPrefsLocalServiceUtil.createLmsPrefs(companyId);
		lmsPrefs.setTeacherRole(courseTeacher.getRoleId());
		lmsPrefs.setEditorRole(courseEditor.getRoleId());
		lmsPrefs.setLmsTemplates(Long.toString(layoutSetPrototype.getLayoutSetPrototypeId()));
		try {
			LmsPrefsLocalServiceUtil.updateLmsPrefs(lmsPrefs);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return lmsPrefs;
		
	}

	public void createDefaultSiteTemplate(long companyId) throws PortalException, SystemException 
	{
		boolean exists = false;
		long layoutSetPrototypeId = 0;
		String course = LanguageUtil.get(LocaleUtil.getDefault(), "course");
		if(_log.isDebugEnabled())
			_log.debug("locale default: " + LocaleUtil.getDefault() + " course: " + course);
		for(LayoutSetPrototype lay:LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypes(0, LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypesCount())){
			if( lay.getCompanyId() == companyId && course.equalsIgnoreCase(lay.getName(LocaleUtil.getDefault())) ) {
				exists=true;
				layoutSetPrototypeId = lay.getLayoutSetPrototypeId();
			}
		}
		
		if(!exists){
			long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);
			Map<Locale, String> nameMap = new HashMap<Locale, String>();
			Locale[] langsAvailables = LanguageUtil.getAvailableLocales();
			_log.debug("avalilables locales: " + langsAvailables.length);
			for(Locale lang : langsAvailables){
				if(_log.isDebugEnabled())
					_log.debug(String.format("Language: %s text: %s", lang, LanguageUtil.get(lang, "model.resource.com.liferay.lms.model.Course")));
				
				nameMap.put(lang, LanguageUtil.get(lang, "model.resource.com.liferay.lms.model.Course"));
			}
			layoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(defaultUserId, companyId, nameMap, "course", true,true,new ServiceContext());
			InputStream larStream=this.getClass().getClassLoader().getResourceAsStream("/course.lar");
			LayoutLocalServiceUtil.importLayouts(defaultUserId,layoutSetPrototype.getGroup().getGroupId() , 
					layoutSetPrototype.getLayoutSet().isPrivateLayout(), getLayoutSetPrototypeParameters(), larStream);
		} else {
			layoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(layoutSetPrototypeId);
		}
		
	}
	private static Map<String, String[]> getLayoutSetPrototypeParameters() 
	{
		Map<String, String[]> parameterMap = new LinkedHashMap<String, String[]>();
        
		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
			//new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
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
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});
		parameterMap.put(
			PortletDataHandlerKeys.USER_PERMISSIONS,
			new String[] {Boolean.FALSE.toString()});
		return parameterMap;
	}	
	public void createDefaultRoles(long companyId) throws PortalException, SystemException {
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);
		int scope = ResourceConstants.SCOPE_GROUP_TEMPLATE;
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
		Map<Locale, String> titleMap = new HashMap<Locale, String>();
		java.util.List<ResourceAction> actions=null;
		
		courseCreator = RoleLocalServiceUtil.fetchRole(companyId, "courseCreator");
		if(courseCreator == null){
			descriptionMap.put(LocaleUtil.getDefault(), "Course creator, can create courses in a community.");
			titleMap.put(LocaleUtil.getDefault(), "Course creator.");
			courseCreator= RoleLocalServiceUtil.addRole(defaultUserId, companyId, "courseCreator" ,titleMap, descriptionMap, RoleConstants.TYPE_SITE);
			actions= ResourceActionLocalServiceUtil.getResourceActions(Course.class.getName());
			setRolePermissions(courseCreator,Course.class.getName(),actions);
			actions= ResourceActionLocalServiceUtil.getResourceActions("com.liferay.lms.coursemodel");
			setRolePermissions(courseCreator,"com.liferay.lms.coursemodel",actions);
		}

		courseEditor = RoleLocalServiceUtil.fetchRole(companyId, "courseEditor");
		if(courseEditor == null){
			descriptionMap = new HashMap<Locale, String>();
			titleMap = new HashMap<Locale, String>();
			titleMap.put(LocaleUtil.getDefault(), "Profesor");
			descriptionMap.put(LocaleUtil.getDefault(), "Editors can Edit a course.");
			courseEditor= RoleLocalServiceUtil.addRole(defaultUserId, companyId, "courseEditor",titleMap, descriptionMap, RoleConstants.TYPE_SITE);
			actions= ResourceActionLocalServiceUtil.getResourceActions(Module.class.getName()); 
			setRolePermissions(courseEditor,Module.class.getName(),actions);
			actions= ResourceActionLocalServiceUtil.getResourceActions("com.liferay.lms.model");
			setRolePermissions(courseEditor,"com.liferay.lms.model",actions);
			actions= ResourceActionLocalServiceUtil.getResourceActions(LearningActivity.class.getName());
			setRolePermissions(courseEditor,LearningActivity.class.getName(),actions);
			
			//Add permission ASSIGN_MEMBERS by default
			ResourcePermissionLocalServiceUtil.setResourcePermissions(0L, Course.class.getName(), ResourceConstants.SCOPE_GROUP_TEMPLATE, String.valueOf(companyId), courseEditor.getRoleId(), new String[]{ActionKeys.ASSIGN_MEMBERS});
		}
		
		courseTeacher = RoleLocalServiceUtil.fetchRole(companyId, "courseTeacher");
		if(courseTeacher == null){
			descriptionMap = new HashMap<Locale, String>(); 
			titleMap = new HashMap<Locale, String>();
			titleMap.put(LocaleUtil.getDefault(),"Profesor sustituto");
			descriptionMap.put(LocaleUtil.getDefault(), "Teachers.");
			courseTeacher= RoleLocalServiceUtil.addRole(defaultUserId, companyId, "courseTeacher", titleMap, descriptionMap, RoleConstants.TYPE_SITE);
			actions= ResourceActionLocalServiceUtil.getResourceActions(BlogsEntry.class.getName());
			for(ResourceAction raction:actions)
				ResourcePermissionLocalServiceUtil.addResourcePermission(companyId,BlogsEntry.class.getName(),scope, Long.toString(companyId),courseTeacher.getRoleId(),raction.getActionId());
			actions= ResourceActionLocalServiceUtil.getResourceActions(MBCategory.class.getName());
			for(ResourceAction raction:actions)
				ResourcePermissionLocalServiceUtil.addResourcePermission(companyId,MBCategory.class.getName(),scope, Long.toString(companyId),courseTeacher.getRoleId(),raction.getActionId());
			ResourcePermissionLocalServiceUtil.addResourcePermission(companyId,LearningActivity.class.getName(),scope, Long.toString(companyId),courseTeacher.getRoleId(),"CORRECT");
		}

	}
	private static void setRolePermissions(Role role, String name, java.util.List<ResourceAction>actions) throws PortalException, SystemException {
		String[] actionIds=new String[actions.size()];
		int counter=0;
		
		for(ResourceAction raction:actions){
			actionIds[counter]=raction.getActionId();
			counter++;
		}
		
		if (ResourceBlockLocalServiceUtil.isSupported(name)) 
			ResourceBlockLocalServiceUtil.setCompanyScopePermissions(role.getCompanyId(), name, role.getRoleId(),Arrays.asList(actionIds));
		else 
			ResourcePermissionLocalServiceUtil.setResourcePermissions(role.getCompanyId(), name, ResourceConstants.SCOPE_COMPANY, String.valueOf(role.getCompanyId()), role.getRoleId(), actionIds);
		
	}
	
	/**
	 * Creates an Expando column in the table specified and update its
	 * indexType.
	 * 
	 * @param table
	 *            ExpandoTable to create the column/field in
	 * @param fieldName
	 *            The name of the field/column to add
	 * @param type
	 *            The type of the field. See ExpandoColumnConstants class for
	 *            values.
	 * @param indexType
	 *            The index type as defined in ExpandoColumnConstants.
	 */
	private void createExpandoColumn(ExpandoTable table, String fieldName, int type, int indexType, String displayType, Object defaultData, Boolean visibility) {
		// logger.info("Attempting to create Expando field " + fieldName +
		// " for class " + table.getClassName());

		ExpandoColumn column = null;
		try {
			column = ExpandoColumnLocalServiceUtil.addColumn(table.getTableId(), fieldName, type, defaultData);
		} catch (DuplicateColumnNameException duplicateColumnNameException) {
			// Column already exists so retrieve it
			// logger.info("Column already exists trying to retrieve it");

			try {
				column = ExpandoColumnLocalServiceUtil.getColumn(table.getTableId(), fieldName);
				column = ExpandoColumnLocalServiceUtil.updateColumn(column.getColumnId(), fieldName, type, defaultData);
			} catch (SystemException ex) {
				// logger.error("Exception while retrieving the column and unable to update",
				// ex);
			} catch (PortalException e) {
				//e.printStackTrace();
			}
		} catch (PortalException ex) {
			// logger.error("Exception while creating the column", ex);
		} catch (SystemException e) {
			e.printStackTrace();
		} finally {
			// Finally if we managed to get the column we will set its index
			// type
			if (column != null) {
				try {
					UnicodeProperties properties = new UnicodeProperties();
					properties.put(ExpandoColumnConstants.INDEX_TYPE, String.valueOf(indexType));
					if (displayType != null) 
						properties.put(ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE, displayType);
					if (!visibility) 
						properties.put(ExpandoColumnConstants.PROPERTY_HIDDEN, "true");
					column.setTypeSettingsProperties(properties);
					ExpandoColumnLocalServiceUtil.updateExpandoColumn(column);
					// logger.info("Property updated to " + indexType);
				} catch (SystemException ex) {
					// logger.info("System Exception unable to store properties",
					// ex);
				}
			}
		}
	}
	
	/**
	 * Create the expando table given the Company ID, class and table name. If
	 * the table already exists it gets it. If the table name is null the
	 * default table name is used.
	 * 
	 * @param companyId
	 *            Company Id for the portal instance
	 * @param className
	 *            Class to attach the expando attribute to
	 * @param tableName
	 *            Table name for the expando attribute.
	 * @return The created or retreived table. If something goes wrong null is
	 *         returned.
	 */
	private ExpandoTable getExpandoTable(long companyId, String className, String tableName) {
		if (tableName == null) tableName = ExpandoTableConstants.DEFAULT_TABLE_NAME;
		ExpandoTable table = null;

		try {
			table = ExpandoTableLocalServiceUtil.addTable(companyId, className,	tableName);
		} catch (DuplicateTableNameException duplicateTableNameException) {
			try {
				table = ExpandoTableLocalServiceUtil.getTable(companyId, className, tableName);
			} catch (PortalException ex) {
				// If we're here something is really wrong and table will be
				// null
				// logger.error("Exception when getting table", ex);
			} catch (SystemException ex) {
				ex.printStackTrace();
			}
		} catch (PortalException ex) {
			// If we're here something is really wrong and table will be null
			// logger.error("Exception when creating table", ex);
		} catch (SystemException ex) {
			ex.printStackTrace();
		}
		return table;
	}

}
