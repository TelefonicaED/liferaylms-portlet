package com.liferay.lms.service.persistence;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.impl.CourseImpl;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

public class CourseFinderImpl extends BasePersistenceImpl<Course> implements CourseFinder{
	
	Log log = LogFactoryUtil.getLog(CourseFinderImpl.class);
	 
	public static final String FIND_BY_TITLE_STATUS =
		    CourseFinder.class.getName() +
		        ".findAdministratorByT_S";
	public static final String COUNT_BY_TITLE_STATUS =
		    CourseFinder.class.getName() +
		        ".countAdministratorByT_S";
	public static final String JOIN_BY_ASSET_ENTRY =
		    CourseFinder.class.getName() +
		        ".joinC_ByAssetEntry";
	public static final String JOIN_BY_ASSET_CATEGORY =
		    CourseFinder.class.getName() +
		        ".joinC_ByAssetCategory";
	public static final String JOIN_BY_ASSET_TAG =
		    CourseFinder.class.getName() +
		        ".joinC_ByAssetTag";
	public static final String JOIN_BY_RESOURCE_PERMISSION =
		    CourseFinder.class.getName() +
		        ".joinC_ByResourcePermission";
	public static final String WHERE_BY_RESOURCE_PERMISSION =
		    CourseFinder.class.getName() +
		        ".whereC_ByResourcePermission";
	public static final String WHERE_BY_AND_ASSET_CATEGORY =
		    CourseFinder.class.getName() +
		        ".whereC_ByAndAssetCategory";
	public static final String WHERE_BY_AND_ASSET_TAG =
		    CourseFinder.class.getName() +
		        ".whereC_ByAndAssetTag";
	public static final String WHERE_BY_OR_ASSET_CATEGORY =
		    CourseFinder.class.getName() +
		        ".whereC_ByOrAssetCategory";
	public static final String WHERE_BY_OR_ASSET_TAG =
		    CourseFinder.class.getName() +
		        ".whereC_ByOrAssetTag";
	public static final String FIND_STUDENTS = 
			CourseFinder.class.getName() +
				".findStudents";
	public static final String COUNT_STUDENTS = 
			CourseFinder.class.getName() +
				".countStudents";
	public static final String JOIN_BY_RESOURCE_PERMISSION_VIEW = 
			CourseFinder.class.getName() + 
				".joinC_ByResourcePermissionView";
	public static final String WHERE_C_CATALOG = 
			CourseFinder.class.getName() + 
				".whereC_Catalog";
	public static final String WHERE_BY_RESOURCE_PERMISSION_VIEW =
		    CourseFinder.class.getName() +
		        ".whereC_ByResourcePermissionView";
	
	
	@SuppressWarnings("unchecked")
	public List<Course> findByT_S_C_T(String freeText, int status, long[] categories, long[] tags, long companyId, long groupId, long userId, String language, boolean isAdmin, boolean andOperator, int start, int end){
		Session session = null;
		
		try{
			
			if(freeText != null && freeText.length() > 0)
				freeText = "%" + freeText + "%";
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(FIND_BY_TITLE_STATUS);
			
			sql = replaceLanguage(sql, language);
			
			sql = replaceAssetEntry(sql, categories, tags);

			sql = replaceCategory(sql, categories);

			sql = replaceTag(sql, tags);
			
			sql = replaceResourcePermission(sql, isAdmin, companyId, userId);

			if(start < 0 && end < 0){
				sql = sql.replace("LIMIT [$START$], [$END$]", "");
			}else{
				sql = sql.replace("[$START$]", String.valueOf(start));
				sql = sql.replace("[$END$]", String.valueOf(start+end));
			}
			
			log.debug("sql: " + sql);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("Lms_Course", CourseImpl.class);
			
			QueryPos qPos = QueryPos.getInstance(q);
			log.debug("companyId: " + companyId);
			qPos.add(companyId);
			qPos.add(companyId);
			log.debug("groupId: " + groupId);
			qPos.add(groupId);
			qPos.add(groupId);
			log.debug("freeText: " + freeText);
			qPos.add(freeText);
			qPos.add(status);
			log.debug("status: " + status);
			qPos.add(andOperator);
			log.debug("andOperator: " + andOperator);
			qPos.add(freeText);
			qPos.add(freeText);
			qPos.add(freeText);
			qPos.add(status);
			qPos.add(status);
			qPos.add(andOperator);
			qPos.add(freeText);
			qPos.add(freeText);
			qPos.add(status);
			
			List<Course> listCourse = (List<Course>) q.list();
			
			log.debug("listCourse: " + listCourse.size());
					
			return listCourse;
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return new ArrayList<Course>();
	}
	
	private String replaceResourcePermission(String sql, boolean isAdmin,long companyId, long userId) throws PortalException {
		/**Si no es administrador filtramos por permisos **/
		if(!isAdmin){
			sql = sql.replace("[$JOINRESOURCEPERMISSION$]", CustomSQLUtil.get(JOIN_BY_RESOURCE_PERMISSION));
			sql = sql.replace("[$COMPANYID$]", String.valueOf(companyId));
			sql = sql.replace("[$ACTIONPUBLISH$]", String.valueOf(ResourceActionLocalServiceUtil.getResourceAction(Course.class.getName(), "PUBLISH").getBitwiseValue()));
			sql = sql.replace("[$ACTIONCOURSEEDITOR$]", String.valueOf(ResourceActionLocalServiceUtil.getResourceAction(Course.class.getName(), "COURSEEDITOR").getBitwiseValue()));
			sql = sql.replace("[$ACTIONASSIGN_MEMBERS$]", String.valueOf(ResourceActionLocalServiceUtil.getResourceAction(Course.class.getName(), "ASSIGN_MEMBERS").getBitwiseValue()));
			sql = sql.replace("[$ACTIONDELETE$]", String.valueOf(ResourceActionLocalServiceUtil.getResourceAction(Course.class.getName(), ActionKeys.DELETE).getBitwiseValue()));
			sql = sql.replace("[$ACTIONPERMISSION$]", String.valueOf(ResourceActionLocalServiceUtil.getResourceAction(Course.class.getName(), ActionKeys.PERMISSIONS).getBitwiseValue()));
			sql = sql.replace("[$ACTIONUPDATE$]", String.valueOf(ResourceActionLocalServiceUtil.getResourceAction(Course.class.getName(), ActionKeys.UPDATE).getBitwiseValue()));
			sql = sql.replace("[$USERID$]", String.valueOf(userId));
			sql = sql.replace("[$CLASSNAMEIDUSERGROUP$]", String.valueOf(ClassNameLocalServiceUtil.getClassNameId(UserGroup.class)));
			sql = sql.replace("[$CLASSNAMEIDORGANIZATION$]", String.valueOf(ClassNameLocalServiceUtil.getClassNameId(Organization.class)));
			sql = sql.replace("[$CLASSNAMEIDGROUP$]", String.valueOf(ClassNameLocalServiceUtil.getClassNameId(Group.class)));
			
			sql = sql.replace("[$WHERERESOURCEPERMISSION$]", CustomSQLUtil.get(WHERE_BY_RESOURCE_PERMISSION));
		}else{
			sql = sql.replace("[$JOINRESOURCEPERMISSION$]", "");
			sql = sql.replace("[$WHERERESOURCEPERMISSION$]", "");
		}
		
		return sql;
	}

	private String replaceTag(String sql, long[] tags) {
		/** Sustituimos los tags si buscamos por ellos **/
		if(tags != null && tags.length > 0){
			String tagIds = "";
			String joins = "";
			String wheres = "";
			
			for(int i = 0; i < tags.length; i++){
				joins += CustomSQLUtil.get(JOIN_BY_ASSET_TAG);
				wheres += CustomSQLUtil.get(WHERE_BY_AND_ASSET_TAG);

				wheres = wheres.replace("[$TAGIDS$]", String.valueOf(tags[i]));	
				
				joins = joins.replace("[$i$]", String.valueOf(i));
				wheres = wheres.replace("[$i$]", String.valueOf(i));
				
				tagIds += tags[i] + ",";
			}
			tagIds = tagIds.substring(0, tagIds.length()-1);
			
			sql = sql.replace("[$JOINASSETTAGS$]", joins);
			
			sql = sql.replace("[$WHEREANDASSETTAG$]", wheres);
			sql = sql.replace("[$WHEREORASSETTAG$]", CustomSQLUtil.get(WHERE_BY_OR_ASSET_TAG));
				
			sql = sql.replace("[$TAGIDS$]", tagIds);
		}else{
			sql = sql.replace("[$JOINASSETTAGS$]", "");
			sql = sql.replace("[$WHEREANDASSETTAG$]", "");
			sql = sql.replace("[$WHEREORASSETTAG$]", "");
		}
		return sql;
	}

	private String replaceCategory(String sql, long[] categories) {
		/**Ahora sustituimos las categorias si se busca por ellas**/
		if(categories != null && categories.length > 0){
			String categoryIds = "";
			String joins = "";
			String wheres = "";
			for(int i = 0; i < categories.length; i++){
				joins += CustomSQLUtil.get(JOIN_BY_ASSET_CATEGORY);

				wheres += CustomSQLUtil.get(WHERE_BY_AND_ASSET_CATEGORY);
				
				wheres = wheres.replace("[$CATEGORYIDS$]", String.valueOf(categories[i]));	
				
				joins = joins.replace("[$i$]", String.valueOf(i));
				wheres = wheres.replace("[$i$]", String.valueOf(i));
				categoryIds += categories[i] + ",";
			}
			
			categoryIds = categoryIds.substring(0, categoryIds.length()-1);
			
			sql = sql.replace("[$JOINASSETCATEGORIES$]", joins);
			sql = sql.replace("[$WHEREANDASSETCATEGORY$]", wheres);
			
			sql = sql.replace("[$WHEREORASSETCATEGORY$]", CustomSQLUtil.get(WHERE_BY_OR_ASSET_CATEGORY));
			sql = sql.replace("[$CATEGORYIDS$]", categoryIds);	

		}else{
			sql = sql.replace("[$JOINASSETCATEGORIES$]", "");
			sql = sql.replace("[$WHEREANDASSETCATEGORY$]", "");
			sql = sql.replace("[$WHEREORASSETCATEGORY$]", "");
		}
		return sql;
	}

	private String replaceAssetEntry(String sql, long[] categories, long[] tags) {
		/**Si buscamos por categorias o por tags añadimos el join con los asset entry y luego los correspondientes**/
		if((categories != null && categories.length > 0) || (tags != null && tags.length > 0)){
			sql = sql.replace("[$JOINASSET$]", CustomSQLUtil.get(JOIN_BY_ASSET_ENTRY));
			sql = sql.replace("[$CLASSNAMECOURSEID$]", String.valueOf(ClassNameLocalServiceUtil.fetchClassNameId(Course.class)));
		}else{
			sql = sql.replace("[$JOINASSET$]", " ");
		}
		
		return sql;
	}

	private String replaceLanguage(String sql, String language) {
		/**Reemplazamos el lenguage**/
		return sql.replace("[$LANGUAGE$]", language);
	}

	public int countByT_S_C_T(String freeText, int status, long[] categories, long[] tags, long companyId, long groupId, long userId, String language, boolean isAdmin, boolean andOperator){
		Session session = null;
		
		try{
			session = openSession();
			
			if(freeText != null && freeText.length() > 0)
				freeText = "%" + freeText + "%";
			
			String sql = CustomSQLUtil.get(COUNT_BY_TITLE_STATUS);

			sql = replaceLanguage(sql, language);
			
			sql = replaceAssetEntry(sql, categories, tags);
			
			sql = replaceCategory(sql, categories);
			
			sql = replaceTag(sql, tags);
			
			sql = replaceResourcePermission(sql, isAdmin, companyId, userId);
			
			log.debug("sql: " + sql);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);
			
			QueryPos qPos = QueryPos.getInstance(q);
			log.debug("companyId: " + companyId);
			qPos.add(companyId);
			qPos.add(companyId);
			log.debug("groupId: " + groupId);
			qPos.add(groupId);
			qPos.add(groupId);
			log.debug("freeText: " + freeText);
			qPos.add(freeText);
			qPos.add(status);
			log.debug("status: " + status);
			qPos.add(andOperator);
			log.debug("andOperator: " + andOperator);
			qPos.add(freeText);
			qPos.add(freeText);
			qPos.add(freeText);
			qPos.add(status);
			qPos.add(status);
			qPos.add(andOperator);
			qPos.add(freeText);
			qPos.add(freeText);
			qPos.add(status);
			
			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return 0;
	}
	
	public List<User> findStudents(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress,boolean andOperator, int start, int end,OrderByComparator obc){
		Session session = null;
		
		try{
			
			/** Para la query es necesario si no es null o vacío que añade los porcentajes, y si es vacío ponerlo a null*/
			
			if(Validator.isNotNull(screenName)){
				screenName = "%" + screenName + "%";
			}else{
				screenName = null;
			}
			if(Validator.isNotNull(firstName)){
				firstName = "%" + firstName + "%";
			}else{
				firstName = null;
			}
			if(Validator.isNotNull(lastName)){
				lastName = "%" + lastName + "%";
			}else{
				lastName = null;
			}
			if(Validator.isNotNull(emailAddress)){
				emailAddress = "%" + emailAddress + "%";
			}else{
				emailAddress = null;
			}
			
			log.debug("ScreenName:"+screenName);
			log.debug("firstName:"+firstName);
			log.debug("lastName:"+lastName);
			log.debug("emailAddress:"+emailAddress);
			
			session = openSessionLiferay();
			
			String sql = CustomSQLUtil.get(FIND_STUDENTS);
			
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			
			if (obc != null && obc.getOrderBy() != null && !obc.getOrderBy().equals("")) {
				sql = sql.replace("[$ORDERBY$]", obc.toString());
			}else{
				sql = sql.replace("[$ORDERBY$]", "u.lastName, u.firstName, u.middleName ");
			}
			
			log.debug("sql: " + sql);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("User_",PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portal.model.impl.UserImpl"));
			
			//Obtenemos el rol de editor del curso y profesor
			LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(companyId);
			long teacherRoleId = prefs.getTeacherRole();
			long editorRoleId = prefs.getEditorRole();
			
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(teacherRoleId);
			log.debug("teacherRoleId: " + teacherRoleId);
			qPos.add(editorRoleId);
			log.debug("editorRoleId: " + editorRoleId);
			qPos.add(courseId);
			log.debug("courseId: " + courseId);
			qPos.add(WorkflowConstants.STATUS_APPROVED);
			log.debug("WorkflowConstants.STATUS_APPROVED: " + WorkflowConstants.STATUS_APPROVED);
			
			qPos.add(firstName);
			log.debug("firstName: " + firstName);
			qPos.add(firstName);
			qPos.add(lastName);
			log.debug("lastName: " + lastName);
			qPos.add(lastName);
			qPos.add(screenName);
			log.debug("screenName: " + screenName);
			qPos.add(screenName);
			qPos.add(emailAddress);
			log.debug("emailAddress: " + emailAddress);
			qPos.add(emailAddress);
			
			qPos.add(start);
			qPos.add((start+end));
			
			List<User> listUsers = (List<User>) q.list();
			
			log.debug("listUsers: " + listUsers.size());
					
			return listUsers;
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	    	closeSessionLiferay(session);
	    }
	
	    return new ArrayList<User>();
	}
	
	public int countStudents(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress,boolean andOperator){
		Session session = null;
		
		try{
			
			if(Validator.isNotNull(screenName)){
				screenName = "%" + screenName + "%";
			}else{
				screenName = null;
			}
			if(Validator.isNotNull(firstName)){
				firstName = "%" + firstName + "%";
			}else{
				firstName = null;
			}
			if(Validator.isNotNull(lastName)){
				lastName = "%" + lastName + "%";
			}else{
				lastName = null;
			}
			if(Validator.isNotNull(emailAddress)){
				emailAddress = "%" + emailAddress + "%";
			}else{
				emailAddress = null;
			}	
			
			log.debug("ScreenName:"+screenName);
			log.debug("firstName:"+firstName);
			log.debug("lastName:"+lastName);
			log.debug("emailAddress:"+emailAddress);
			
			session = openSessionLiferay();
			
			String sql = CustomSQLUtil.get(COUNT_STUDENTS);
			
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);
			
			//Obtenemos el rol de editor del curso y profesor
			LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(companyId);
			long teacherRoleId = prefs.getTeacherRole();
			long editorRoleId = prefs.getEditorRole();
			
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(teacherRoleId);
			qPos.add(editorRoleId);
			qPos.add(courseId);
			qPos.add(WorkflowConstants.STATUS_APPROVED);

			qPos.add(firstName);
			qPos.add(firstName);
			qPos.add(lastName);
			qPos.add(lastName);
			qPos.add(screenName);
			qPos.add(screenName);
			qPos.add(emailAddress);
			qPos.add(emailAddress);
			
			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	    	closeSessionLiferay(session);
	    }
	
	    return 0;
	}
	
	public List<Course> findByCatalog(String freeText, long[] categories, long[] tags, long companyId, long groupId, long userId, String language, int start, int end){
		Session session = null;
		
		try{
			
			if(freeText != null && freeText.length() > 0)
				freeText = "%" + freeText + "%";
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(FIND_BY_TITLE_STATUS);
			
			sql = replaceLanguage(sql, language);
			
			sql = sql.replace("[$JOINASSET$]", CustomSQLUtil.get(JOIN_BY_ASSET_ENTRY));
			sql = sql.replace("[$CLASSNAMECOURSEID$]", String.valueOf(ClassNameLocalServiceUtil.fetchClassNameId(Course.class)));

			sql = replaceCategory(sql, categories);

			sql = replaceTag(sql, tags);
			
			sql = replaceResourcePermissionView(sql, companyId, userId);

			if(start < 0 && end < 0){
				sql = sql.replace("LIMIT [$START$], [$END$]", "");
			}else{
				sql = sql.replace("[$START$]", String.valueOf(start));
				sql = sql.replace("[$END$]", String.valueOf(start+end));
			}
			
			log.debug("sql: " + sql);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("Lms_Course", CourseImpl.class);
			
			QueryPos qPos = QueryPos.getInstance(q);
			log.debug("companyId: " + companyId);
			qPos.add(companyId);
			qPos.add(companyId);
			log.debug("groupId: " + groupId);
			qPos.add(groupId);
			qPos.add(groupId);
			log.debug("freeText: " + freeText);
			qPos.add(freeText);
			qPos.add(WorkflowConstants.STATUS_APPROVED);
			qPos.add(true);
			qPos.add(freeText);
			qPos.add(freeText);
			qPos.add(freeText);
			qPos.add(WorkflowConstants.STATUS_APPROVED);
			qPos.add(WorkflowConstants.STATUS_APPROVED);
			qPos.add(true);
			qPos.add(freeText);
			qPos.add(freeText);
			qPos.add(WorkflowConstants.STATUS_APPROVED);
			
			List<Course> listCourse = (List<Course>) q.list();
			
			log.debug("listCourse: " + listCourse.size());
					
			return listCourse;
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return new ArrayList<Course>();
	}
	
	public int countByCatalog(String freeText, long[] categories, long[] tags, long companyId, long groupId, long userId, String language){
		Session session = null;
		
		try{
			session = openSession();
			
			if(freeText != null && freeText.length() > 0)
				freeText = "%" + freeText + "%";
			
			String sql = CustomSQLUtil.get(COUNT_BY_TITLE_STATUS);

			sql = replaceLanguage(sql, language);
			
			sql = sql.replace("[$JOINASSET$]", CustomSQLUtil.get(JOIN_BY_ASSET_ENTRY));
			sql = sql.replace("[$CLASSNAMECOURSEID$]", String.valueOf(ClassNameLocalServiceUtil.fetchClassNameId(Course.class)));
			
			sql = replaceCategory(sql, categories);
			
			sql = replaceTag(sql, tags);
			
			sql = replaceResourcePermissionView(sql, companyId, userId);
			
			log.debug("sql: " + sql);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);
			
			QueryPos qPos = QueryPos.getInstance(q);
			log.debug("companyId: " + companyId);
			qPos.add(companyId);
			qPos.add(companyId);
			log.debug("groupId: " + groupId);
			qPos.add(groupId);
			qPos.add(groupId);
			log.debug("freeText: " + freeText);
			qPos.add(freeText);
			qPos.add(WorkflowConstants.STATUS_APPROVED);
			qPos.add(true);
			qPos.add(freeText);
			qPos.add(freeText);
			qPos.add(freeText);
			qPos.add(WorkflowConstants.STATUS_APPROVED);
			qPos.add(WorkflowConstants.STATUS_APPROVED);
			qPos.add(true);
			qPos.add(freeText);
			qPos.add(freeText);
			qPos.add(WorkflowConstants.STATUS_APPROVED);
			
			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return 0;
	}
	
	private String replaceResourcePermissionView(String sql,long companyId, long userId) throws PortalException {

		sql = sql.replace("[$JOINRESOURCEPERMISSION$]", CustomSQLUtil.get(JOIN_BY_RESOURCE_PERMISSION_VIEW));
		sql = sql.replace("[$COMPANYID$]", String.valueOf(companyId));
		sql = sql.replace("[$ACTIONVIEW$]", String.valueOf(ResourceActionLocalServiceUtil.getResourceAction(Course.class.getName(), "VIEW").getBitwiseValue()));
		sql = sql.replace("[$USERID$]", String.valueOf(userId));
		sql = sql.replace("[$CLASSNAMEIDUSERGROUP$]", String.valueOf(ClassNameLocalServiceUtil.getClassNameId(UserGroup.class)));
		sql = sql.replace("[$CLASSNAMEIDORGANIZATION$]", String.valueOf(ClassNameLocalServiceUtil.getClassNameId(Organization.class)));
		sql = sql.replace("[$CLASSNAMEIDGROUP$]", String.valueOf(ClassNameLocalServiceUtil.getClassNameId(Group.class)));
		
		sql = sql.replace("[$WHERERESOURCEPERMISSION$]", CustomSQLUtil.get(WHERE_BY_RESOURCE_PERMISSION_VIEW) + " " + CustomSQLUtil.get(WHERE_C_CATALOG));
		try {
			sql = sql.replace("[$ROLEMEMBER$]", String.valueOf(RoleLocalServiceUtil.getRole(companyId, RoleConstants.SITE_MEMBER).getRoleId()));
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sql = sql.replace("[$ROLEGUEST$]", String.valueOf(RoleLocalServiceUtil.getRole(companyId, RoleConstants.GUEST).getRoleId()));
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sql;
	}
	
	private final Class<?> getPortalClass(String className) {
		ClassLoader portalCl = PortalClassLoaderUtil.getClassLoader();

		try {
			return portalCl.loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	private SessionFactory getPortalSessionFactory() {
		String sessionFactory = "liferaySessionFactory";

		SessionFactory sf = (SessionFactory) PortalBeanLocatorUtil
				.getBeanLocator().locate(sessionFactory);

		return sf;
	}

	public void closeSessionLiferay(Session session) {
		getPortalSessionFactory().closeSession(session);
	}

	public Session openSessionLiferay() throws ORMException {
		return getPortalSessionFactory().openSession();
	}
}
