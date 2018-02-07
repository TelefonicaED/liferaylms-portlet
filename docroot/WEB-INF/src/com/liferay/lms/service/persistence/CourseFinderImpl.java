package com.liferay.lms.service.persistence;


import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.impl.CourseImpl;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.views.CourseResultView;
import com.liferay.lms.views.CourseView;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
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
	public static final String JOIN_BY_TEMPLATES =
		    CourseFinder.class.getName() +
		        ".joinC_ByTemplates";
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
	public static final String WHERE_BY_AND_ASSET_TAG_IN =
			CourseFinder.class.getName() +
			".whereC_ByAndAssetTagIn";
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
	public static final String FIND_COURSE_ASSET_TAGS =
			 CourseFinder.class.getName() +
				".findCoursesAssetTagsByT_S";
	public static final String COUNT_CATEGORY_COURSES =
			 CourseFinder.class.getName() +
				".countAssetCategoryCourses";
	public static final String COUNT_TAG_COURSES =
			 CourseFinder.class.getName() +
				".countAssetTagCourses";
	public static final String WHERE_SCREEN_NAME =
		    CourseFinder.class.getName() +
		        ".whereScreenName";
	public static final String WHERE_FIRST_NAME =
		    CourseFinder.class.getName() +
		        ".whereFistName";
	public static final String WHERE_LAST_NAME =
		    CourseFinder.class.getName() +
		        ".whereLastName";
	public static final String WHERE_EMAIL_ADDRESS =
		    CourseFinder.class.getName() +
		        ".whereEmailAddress";	
	public static final String HAS_USER_TRIES =
			 CourseFinder.class.getName() +
				".hasUserTries";	
	public static final String MY_COURSES =
			 CourseFinder.class.getName() +
				".myCourses";
	public static final String COUNT_MY_COURSES =
			 CourseFinder.class.getName() +
				".countMyCourses";
	public static final String EXISTING_USER_COURSES =
			 CourseFinder.class.getName() +
				".getExistingUserCourses";
	public static final String WHERE_TITLE_DESCRIPTION_CATEGORIES_TAGS_AND =
			 CourseFinder.class.getName() + 
			 	".whereC_BytTitleDescriptionCategoriesTagsAND";
	public static final String WHERE_TITLE_DESCRIPTION_CATEGORIES_TAGS_OR =
			 CourseFinder.class.getName() + 
			 	".whereC_BytTitleDescriptionCategoriesTagsOR";	
	public static final String WHERE_PARENT_COURSE_NULL =
			 CourseFinder.class.getName() +
				".whereParentCourseNull";
	public static final String WHERE_PARENT_COURSE =
			 CourseFinder.class.getName() +
				".whereParentCourse";
	public static final String OPEN_OR_RESTRICTED_CHILD_COURSES = 
			CourseFinder.class.getName() + ".getOpenOrRestrictedCoursesByParentId";
	public static final String COUNT_OPEN_OR_RESTRICTED_CHILD_COURSES = 
			CourseFinder.class.getName() + ".countOpenOrRestrictedCoursesByParentId";
	public static final String GET_DISTINCT_COURSE_GROUPS = 
			CourseFinder.class.getName() + ".getDistinctCourseGroups";
	
	
	public List<Course> findByT_S_C_T_T(String freeText, int status, long[] categories, long[] tags, String templates, long companyId, long groupId, long userId, String language, boolean isAdmin, boolean searchParentCourses, boolean andOperator, int start, int end){
		return findByT_S_C_T_T(freeText, -1, status, categories, tags, templates, companyId, groupId, userId, language, isAdmin, searchParentCourses, andOperator, start, end);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Course> findByT_S_C_T_T(String freeText, long parentCourseId, int status, long[] categories, long[] tags, String templates, long companyId, long groupId, long userId, String language, boolean isAdmin, boolean searchParentCourses, boolean andOperator, int start, int end){
		Session session = null;
		
		try{
			
			if(freeText != null && freeText.length() > 0)
				freeText = "%" + freeText + "%";
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(FIND_BY_TITLE_STATUS);
			
			sql = replaceWhereTitleDescriptionCategoriesTags(sql, freeText, status, categories, tags, andOperator);
			
			sql = replaceLanguage(sql, language);
					
			sql = replaceAssetEntry(sql, categories, tags);

			sql = replaceCategory(sql, categories);

			sql = replaceTag(sql, tags);

			sql = replaceTemplates(sql, templates);
			
			sql = replaceResourcePermission(sql, isAdmin, companyId, userId);
			
			sql = replaceSearchParentCourse(sql, searchParentCourses,parentCourseId);

			if(start < 0 && end < 0){
				sql = sql.replace("LIMIT [$START$], [$END$]", "");
			}else{
				sql = sql.replace("[$START$]", String.valueOf(start));
				sql = sql.replace("[$END$]", String.valueOf(end-start));
			}
			
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				log.debug("companyId: " + companyId);
				log.debug("groupId: " + groupId);
				log.debug("freeText: " + freeText);
				log.debug("andOperator: " + andOperator);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("Lms_Course", CourseImpl.class);
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(companyId);
			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(groupId);
			if(searchParentCourses){
				if(parentCourseId>0){
					qPos.add(parentCourseId);
				}
			}
			
			List<Course> listCourse = (List<Course>) q.list();
			
			return listCourse;
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return new ArrayList<Course>();
	}
	
	private String replaceSearchParentCourse(String sql, boolean searchParentCourses, long parentCourseId) {
		/** Sustituimos los tags si buscamos por ellos queda preparado para buscar por = en vez de por IN**/
		if(searchParentCourses){
			if(parentCourseId>0){
				sql = sql.replace("[$WHEREPARENTCOURSE$]", CustomSQLUtil.get(WHERE_PARENT_COURSE));
			}else{
				sql = sql.replace("[$WHEREPARENTCOURSE$]", CustomSQLUtil.get(WHERE_PARENT_COURSE_NULL));	
			}
			
		}else{
			sql = sql.replace("[$WHEREPARENTCOURSE$]", "");
		}
		return sql;
	}
	
	
	private String replaceWhereTitleDescriptionCategoriesTags(String sql, String freeText, int status, long[] categories, long[] tags, boolean andOperator) {
		
		if(andOperator && ((freeText != null && !freeText.equals("")) || status != -1 || (categories != null && categories.length > 0) || (tags != null && tags.length > 0))){
			sql = sql.replace("[$WHERETITLEDESCRIPTIONCATEGORIESTAGSAND$]", CustomSQLUtil.get(WHERE_TITLE_DESCRIPTION_CATEGORIES_TAGS_AND));
			sql = sql.replace("[$FREETEXT$]", String.valueOf(freeText));
			sql = sql.replace("[$STATUS$]", String.valueOf(status));
			sql = sql.replace("[$WHERETITLEDESCRIPTIONCATEGORIESTAGSOR$]", "");
		}else if(!andOperator && ((freeText != null && !freeText.equals("")) || status != -1 || (categories != null && categories.length > 0) || (tags != null && tags.length > 0))){
			sql = sql.replace("[$WHERETITLEDESCRIPTIONCATEGORIESTAGSAND$]", "");
			sql = sql.replace("[$WHERETITLEDESCRIPTIONCATEGORIESTAGSOR$]", CustomSQLUtil.get(WHERE_TITLE_DESCRIPTION_CATEGORIES_TAGS_OR));
			sql = sql.replace("[$FREETEXT$]", String.valueOf(freeText));
			sql = sql.replace("[$STATUS$]", String.valueOf(status));
		}else{
			sql = sql.replace("[$WHERETITLEDESCRIPTIONCATEGORIESTAGSAND$]", "");
			sql = sql.replace("[$WHERETITLEDESCRIPTIONCATEGORIESTAGSOR$]", "");
		}

		return sql;
	}

	private String replaceResourcePermission(String sql, boolean isAdmin,long companyId, long userId) throws PortalException, SystemException {
		/**Si no es administrador filtramos por permisos **/
		if(!isAdmin){
			LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(companyId);
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
			sql = sql.replace("[$ROLEEDITOR$]", String.valueOf(prefs.getEditorRole()));
			sql = sql.replace("[$ROLETEACHER$]", String.valueOf(prefs.getTeacherRole()));
			
			sql = sql.replace("[$WHERERESOURCEPERMISSION$]", CustomSQLUtil.get(WHERE_BY_RESOURCE_PERMISSION));
		}else{
			sql = sql.replace("[$JOINRESOURCEPERMISSION$]", "");
			sql = sql.replace("[$WHERERESOURCEPERMISSION$]", "");
		}
		
		return sql;
	}

	private String replaceTag(String sql, long[] tags) {
		/** Sustituimos los tags si buscamos por ellos queda preparado para buscar por = en vez de por IN**/
		if(tags != null && tags.length > 0){
			String tagIds = "";
			String joins = CustomSQLUtil.get(JOIN_BY_ASSET_TAG);
			String wheres = CustomSQLUtil.get(WHERE_BY_AND_ASSET_TAG_IN);			
			joins = joins.replace("[$i$]", String.valueOf(0));
			wheres = wheres.replace("[$i$]", String.valueOf(0));
			for(int i = 0; i < tags.length; i++){
				tagIds += tags[i] + ",";
			}
			tagIds = tagIds.substring(0, tagIds.length()-1);
			
			
			sql = sql.replace("[$JOINASSETTAGS$]", joins);
			
			sql = sql.replace("[$WHEREANDASSETTAG$]",  wheres);
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
	
	private String replaceTemplates(String sql, String templates) {
		/**Si buscamos por templates**/
		if(Validator.isNotNull(templates)){
			sql = sql.replace("[$JOINTEMPLATES$]", CustomSQLUtil.get(JOIN_BY_TEMPLATES));
			sql = sql.replace("[$TEMPLATES$]", templates);
		}else{
			sql = sql.replace("[$JOINTEMPLATES$]", " ");
		}
		
		return sql;
	}
	

	private String replaceLanguage(String sql, String language) {
		/**Reemplazamos el lenguage**/
		return sql.replace("[$LANGUAGE$]", language);
	}

	public int countByT_S_C_T_T(String freeText, int status, long[] categories, long[] tags, String templates, long companyId, long groupId, long userId, String language, boolean isAdmin, boolean searchParentCourses, boolean andOperator){
		return countByT_S_C_T_T(freeText, -1, status, categories, tags, templates, companyId, groupId, userId, language, isAdmin, searchParentCourses, andOperator);
	}
	
	public int countByT_S_C_T_T(String freeText, long parentCourseId, int status, long[] categories, long[] tags, String templates, long companyId, long groupId, long userId, String language, boolean isAdmin, boolean searchParentCourses, boolean andOperator){
		Session session = null;
		
		try{
			session = openSession();
			
			if(freeText != null && freeText.length() > 0)
				freeText = "%" + freeText + "%";
			
			String sql = CustomSQLUtil.get(COUNT_BY_TITLE_STATUS);
			
			sql = replaceWhereTitleDescriptionCategoriesTags(sql, freeText, status, categories, tags, andOperator);

			sql = replaceLanguage(sql, language);
			
			sql = replaceAssetEntry(sql, categories, tags);
			
			sql = replaceCategory(sql, categories);
			
			sql = replaceTag(sql, tags);
			
			sql = replaceTemplates(sql, templates);
			
			sql = replaceResourcePermission(sql, isAdmin, companyId, userId);
			
			sql = replaceSearchParentCourse(sql, searchParentCourses, parentCourseId);
			
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				log.debug("companyId: " + companyId);
				log.debug("groupId: " + groupId);
				log.debug("freeText: " + freeText);
				log.debug("andOperator: " + andOperator);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);
			
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(companyId);
			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(groupId);
			if(searchParentCourses){
				if(parentCourseId>0){
					qPos.add(parentCourseId);
				}
			}
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
		boolean whereClause = false;
		try{
			
			/** Para la query es necesario si no es null o vacío que añade los porcentajes, y si es vacío ponerlo a null*/
			if(log.isDebugEnabled()){
				log.debug("ScreenName:"+screenName);
				log.debug("firstName:"+firstName);
				log.debug("lastName:"+lastName);
				log.debug("emailAddress:"+emailAddress);
			}
			
			
			session = openSessionLiferay();
			String sql = CustomSQLUtil.get(FIND_STUDENTS);
			
			if(Validator.isNotNull(screenName)){
				sql = sql.replace("[$WHERESCREENNAME$]", CustomSQLUtil.get(WHERE_SCREEN_NAME));
				screenName = "%"+screenName+"%";
				whereClause=true;
			}else{
				sql = sql.replace("[$WHERESCREENNAME$]", "");
			}
			if(Validator.isNotNull(firstName)){
				sql = sql.replace("[$WHEREFIRSTNAME$]", CustomSQLUtil.get(WHERE_FIRST_NAME));
				firstName = "%"+firstName+"%";
				whereClause=true;
			}else{
				sql = sql.replace("[$WHEREFIRSTNAME$]", "");
			}
			if(Validator.isNotNull(lastName)){
				sql = sql.replace("[$WHERELASTNAME$]", CustomSQLUtil.get(WHERE_LAST_NAME));
				lastName = "%"+lastName+"%";
				whereClause=true;
			}else{
				sql = sql.replace("[$WHERELASTNAME$]", "");
			}
			if(Validator.isNotNull(emailAddress)){
				sql = sql.replace("[$WHEREEMAILADDRESS$]", CustomSQLUtil.get(WHERE_EMAIL_ADDRESS));
				emailAddress = "%"+emailAddress+"%";
				whereClause=true;
			}else{
				sql = sql.replace("[$WHEREEMAILADDRESS$]", "");
			}
			
			if(andOperator){
				sql = sql.replace("[$DEFAULT$]", " 1 = 1 ");
			}else{
				if(whereClause){
					sql = sql.replace("[$DEFAULT$]", " 1 = 0 ");	
				}else{
					sql = sql.replace("[$DEFAULT$]", " 1 = 1 ");
				}
			}
			
			
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			
			if (obc != null && obc.getOrderBy() != null && !obc.getOrderBy().equals("")) {
				sql = sql.replace("[$ORDERBY$]", obc.toString());
			}else{
				sql = sql.replace("[$ORDERBY$]", "u.lastName, u.firstName, u.middleName ");
			}
			
			if(start >= 0 && end >= 0){
				sql += " LIMIT " + start + ", " + (end-start);
			}
			
			if(log.isDebugEnabled()) log.debug("sql: " + sql);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("User_",PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portal.model.impl.UserImpl"));
			
			//Obtenemos el rol de editor del curso y profesor
			LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(companyId);
			long teacherRoleId = prefs.getTeacherRole();
			long editorRoleId = prefs.getEditorRole();
			
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(teacherRoleId);
			qPos.add(editorRoleId);
			qPos.add(courseId);
			qPos.add(WorkflowConstants.STATUS_APPROVED);
			
			if(Validator.isNotNull(screenName)){
				qPos.add(screenName);
			}
			
			if(Validator.isNotNull(firstName)){
				qPos.add(firstName);
			}
			if(Validator.isNotNull(lastName)){
				qPos.add(lastName);
			}
			
			if(Validator.isNotNull(emailAddress)){
				qPos.add(emailAddress);
			}
					
			
			if(log.isDebugEnabled()){
				log.debug("editorRoleId: " + editorRoleId);
				log.debug("courseId: " + courseId);
				log.debug("firstName: " + firstName);
				log.debug("lastName: " + lastName);
				log.debug("screenName: " + screenName);
				log.debug("emailAddress: " + emailAddress);	
			}
			
			
			List<User> listUsers = (List<User>) q.list();
			return listUsers;
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	    	closeSessionLiferay(session);
	    }
	
	    return new ArrayList<User>();
	}
	
	public int countStudents(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status,boolean andOperator){
		Session session = null;
		boolean whereClause = false;
		try{
			
			if(log.isDebugEnabled()){
				log.debug("ScreenName:"+screenName);
				log.debug("firstName:"+firstName);
				log.debug("lastName:"+lastName);
				log.debug("emailAddress:"+emailAddress);
			}
						
			session = openSessionLiferay();
			
			String sql = CustomSQLUtil.get(COUNT_STUDENTS);
			

			if(Validator.isNotNull(screenName)){
				sql = sql.replace("[$WHERESCREENNAME$]", CustomSQLUtil.get(WHERE_SCREEN_NAME));
				screenName = "%"+screenName+"%";
				whereClause=true;
			}else{
				sql = sql.replace("[$WHERESCREENNAME$]", "");
			}
			if(Validator.isNotNull(firstName)){
				sql = sql.replace("[$WHEREFIRSTNAME$]", CustomSQLUtil.get(WHERE_FIRST_NAME));
				firstName = "%"+firstName+"%";
				whereClause=true;
			}else{
				sql = sql.replace("[$WHEREFIRSTNAME$]", "");
			}
			if(Validator.isNotNull(lastName)){
				sql = sql.replace("[$WHERELASTNAME$]", CustomSQLUtil.get(WHERE_LAST_NAME));
				lastName = "%"+lastName+"%";
				whereClause=true;
			}else{
				sql = sql.replace("[$WHERELASTNAME$]", "");
			}
			if(Validator.isNotNull(emailAddress)){
				sql = sql.replace("[$WHEREEMAILADDRESS$]", CustomSQLUtil.get(WHERE_EMAIL_ADDRESS));
				emailAddress = "%"+emailAddress+"%";
				whereClause=true;
			}else{
				sql = sql.replace("[$WHEREEMAILADDRESS$]", "");
			}
			
			if(andOperator){
				sql = sql.replace("[$DEFAULT$]", " 1 = 1 ");
			}else{
				if(whereClause){
					sql = sql.replace("[$DEFAULT$]", " 1 = 0 ");	
				}else{
					sql = sql.replace("[$DEFAULT$]", " 1 = 1 ");
				}
			}
			
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
			qPos.add(status);
			qPos.add(status);
			qPos.add(WorkflowConstants.STATUS_ANY);
			
			if(Validator.isNotNull(screenName)){
				qPos.add(screenName);
			}
			if(Validator.isNotNull(firstName)){
				qPos.add(firstName);
			}
			if(Validator.isNotNull(lastName)){
				qPos.add(lastName);
			}			
			if(Validator.isNotNull(emailAddress)){
				qPos.add(emailAddress);
			}
			
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
			
			sql = replaceWhereTitleDescriptionCategoriesTags(sql, freeText, WorkflowConstants.STATUS_APPROVED, categories, tags, true);
			
			sql = replaceLanguage(sql, language);
			
			sql = sql.replace("[$JOINASSET$]", CustomSQLUtil.get(JOIN_BY_ASSET_ENTRY));
			sql = sql.replace("[$CLASSNAMECOURSEID$]", String.valueOf(ClassNameLocalServiceUtil.fetchClassNameId(Course.class)));

			sql = replaceCategory(sql, categories);

			sql = replaceTag(sql, tags);
			
			sql = replaceTemplates(sql, null);
			
			sql = replaceResourcePermissionView(sql, companyId, userId);

			if(start < 0 && end < 0){
				sql = sql.replace("LIMIT [$START$], [$END$]", "");
			}else{
				sql = sql.replace("[$START$]", String.valueOf(start));
				sql = sql.replace("[$END$]", String.valueOf(start+end));
			}
		
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				log.debug("companyId: " + companyId);
				log.debug("groupId: " + groupId);
				log.debug("freeText: " + freeText);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("Lms_Course", CourseImpl.class);
			
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(companyId);
			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(groupId);
			
			List<Course> listCourse = (List<Course>) q.list();
			
			if(log.isDebugEnabled())log.debug("listCourse: " + listCourse.size());
					
			return listCourse;
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return new ArrayList<Course>();
	}
	
	
	public List<Long> findCourseTags(String freeText, long[] categories, long companyId, long groupId, long userId, String language){
		Session session = null;
		
		try{
			
			if(freeText != null && freeText.length() > 0)
				freeText = "%" + freeText + "%";
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(FIND_COURSE_ASSET_TAGS);
			
			
			sql = replaceLanguage(sql, language);
			
			sql = sql.replace("[$JOINASSET$]", CustomSQLUtil.get(JOIN_BY_ASSET_ENTRY));
			sql = sql.replace("[$CLASSNAMECOURSEID$]", String.valueOf(ClassNameLocalServiceUtil.fetchClassNameId(Course.class)));

			sql = replaceCategory(sql, categories);
			
			sql = replaceResourcePermissionView(sql, companyId, userId);

			
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				log.debug("companyId: " + companyId);
				log.debug("groupId: " + groupId);
				log.debug("freeText: " + freeText);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar("tagId", Type.LONG);
			
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(companyId);
			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(groupId);
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
			
			List<Long> listAssetTags = (List<Long>) q.list();
								
			if(log.isDebugEnabled())log.debug("listCourse: " + listAssetTags.size());
					
			return listAssetTags;
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return new ArrayList<Long>();
	}
	
	
	public HashMap<Long,Long> countCategoryCourses(String freeText, long[] categories, long[] tags, long companyId, long groupId, long userId, String language){
		Session session = null;
		
		try{
			
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(COUNT_CATEGORY_COURSES);
			
						
			sql = replaceLanguage(sql, language);
			
			sql = sql.replace("[$JOINASSET$]", CustomSQLUtil.get(JOIN_BY_ASSET_ENTRY));
			sql = sql.replace("[$CLASSNAMECOURSEID$]", String.valueOf(ClassNameLocalServiceUtil.fetchClassNameId(Course.class)));

			sql = replaceCategory(sql, categories);
			
			sql = replaceTag(sql, tags);			
			
			sql = replaceResourcePermissionView(sql, companyId, userId);
			
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				log.debug("companyId: " + companyId);
				log.debug("groupId: " + groupId);
				log.debug("freeText: " + freeText);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar("categoryId", Type.LONG);
			q.addScalar("COUNT_VALUE", Type.LONG);
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(companyId);
			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(groupId);
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
			
		
			
			
			List categoryCourses = q.list();
			if(log.isDebugEnabled())log.debug("categories: " + categoryCourses.size());
			HashMap<Long, Long> courses = new HashMap<Long, Long>();

			String serilizeString=null;
			JSONArray empoyeeJsonArray=null;
			for(Object elemnetObject:categoryCourses){
				serilizeString=JSONFactoryUtil.serialize(elemnetObject);
				empoyeeJsonArray=JSONFactoryUtil.createJSONArray(serilizeString);
				courses.put(empoyeeJsonArray.getLong(0), empoyeeJsonArray.getLong(1));
			}
			
					
			return courses;
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return new HashMap<Long, Long>();
	}
	
	public HashMap<Long,Long> countTagCourses(String freeText, long[] categories, long[] tags, long companyId, long groupId, long userId, String language){
		Session session = null;
		
		try{
			
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(COUNT_TAG_COURSES);
			
						
			sql = replaceLanguage(sql, language);
			
			sql = sql.replace("[$JOINASSET$]", CustomSQLUtil.get(JOIN_BY_ASSET_ENTRY));
			sql = sql.replace("[$CLASSNAMECOURSEID$]", String.valueOf(ClassNameLocalServiceUtil.fetchClassNameId(Course.class)));

			sql = replaceCategory(sql, categories);
			
			sql = replaceTag(sql, tags);
				
			sql = replaceResourcePermissionView(sql, companyId, userId);
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				log.debug("companyId: " + companyId);
				log.debug("groupId: " + groupId);
				log.debug("freeText: " + freeText);
			}
			
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar("tagId", Type.LONG);
			q.addScalar("COUNT_VALUE", Type.LONG);
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(companyId);
			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(groupId);
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
			
			List tagCourses = q.list();
			HashMap<Long, Long> courses = new HashMap<Long, Long>();

			String serilizeString=null;
			JSONArray empoyeeJsonArray=null;
			for(Object elemnetObject:tagCourses){
				serilizeString=JSONFactoryUtil.serialize(elemnetObject);
				empoyeeJsonArray=JSONFactoryUtil.createJSONArray(serilizeString);
				courses.put(empoyeeJsonArray.getLong(0), empoyeeJsonArray.getLong(1));
			}
			
					
			return courses;
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return new HashMap<Long, Long>();
	}
	
	public int countByCatalog(String freeText, long[] categories, long[] tags, long companyId, long groupId, long userId, String language){
		Session session = null;
		
		try{
			session = openSession();
			
			if(freeText != null && freeText.length() > 0)
				freeText = "%" + freeText + "%";
			
			String sql = CustomSQLUtil.get(COUNT_BY_TITLE_STATUS);
			
			sql = replaceWhereTitleDescriptionCategoriesTags(sql, freeText, WorkflowConstants.STATUS_APPROVED, categories, tags, true);

			sql = replaceLanguage(sql, language);
						
			sql = sql.replace("[$JOINASSET$]", CustomSQLUtil.get(JOIN_BY_ASSET_ENTRY));
			sql = sql.replace("[$CLASSNAMECOURSEID$]", String.valueOf(ClassNameLocalServiceUtil.fetchClassNameId(Course.class)));
			
			sql = replaceCategory(sql, categories);
			
			sql = replaceTag(sql, tags);
			
			sql = replaceTemplates(sql, null);
			
			sql = replaceResourcePermissionView(sql, companyId, userId);
			
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				log.debug("companyId: " + companyId);
				log.debug("groupId: " + groupId);
				log.debug("freeText: " + freeText);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);
			
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(companyId);
			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(groupId);
			
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
	
	@SuppressWarnings("unchecked")
	public boolean hasUserTries(long courseId, long userId){
		Session session = null;
		
		try{
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(HAS_USER_TRIES);
		
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				log.debug("courseId: " + courseId);
				log.debug("userId: " + userId);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(userId);
			qPos.add(courseId);
			
			Iterator<BigInteger> itr =  q.iterate();
								
			if (itr.hasNext()) {
				BigInteger hasUserTries = itr.next();

				return hasUserTries.longValue() > 0;
			}
			
			
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
		return false;
	}
	
	public List<CourseResultView> getMyCourses(long groupId, long userId, ThemeDisplay themeDisplay, String orderByColumn, String orderByType, int start, int end){
		Session session = null;
		List<CourseResultView> listMyCourses = new ArrayList<CourseResultView>();
		
		try{
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(MY_COURSES);
			
			sql = replaceLanguage(sql, themeDisplay.getLanguageId());
					
			SimpleDateFormat parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = parseDate.format(new Date());
			
			sql = sql.replace("[$DATENOW$]", date);
			
			if(Validator.isNull(orderByColumn)){
				orderByColumn = "c.courseId";
			}
			
			sql += " ORDER BY " + orderByColumn + " " + orderByType;
			
			if(start >= 0 && end >= 0){
				sql += " LIMIT " + start + "," + end;
			}
		
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				log.debug("groupId: " + groupId);
				log.debug("userId: " + userId);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(userId);
			qPos.add(userId);
			qPos.add(groupId);
			qPos.add(groupId);
			
			Iterator<Object[]> itr =  q.iterate();
							
			Object[] myCourse = null;
			CourseResultView courseResultView = null;
			CourseView courseView = null;
			long result = 0;
			int statusUser = 0;
			while (itr.hasNext()) {
				myCourse = itr.next();

				courseView = new CourseView(((BigInteger)myCourse[0]).longValue(), (String)myCourse[2], ((BigInteger)myCourse[6]).longValue());
				if(Validator.isNotNull(myCourse[5])){
					try{
						FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(((BigInteger)myCourse[5]).longValue());
						courseView.setLogoURL(DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK) );
					}catch(Exception e){}
				}else{
					Group groupCourse= GroupLocalServiceUtil.getGroup(((BigInteger)myCourse[6]).longValue());
					if(groupCourse.getPublicLayoutSet().getLogo()){
						log.debug("Lo tiene el group");
						courseView.setLogoURL("/image/layout_set_logo?img_id=" + groupCourse.getPublicLayoutSet().getLogoId());
					}	
				}
				courseView.setUrl(themeDisplay.getPortalURL()+"/"+themeDisplay.getLocale().getLanguage()+"/web" + (String)myCourse[7]);
				result = ((BigInteger)myCourse[4]).longValue();
				statusUser = Integer.parseInt((String)myCourse[3]);
				courseResultView = new CourseResultView(courseView, result, statusUser);
				
				listMyCourses.add(courseResultView);
			}
			
			
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
		return listMyCourses;
	}
	
	
	public List<Course> getExistingUserCourses(long userId, int start, int end){
		Session session = null;
		List<Course> listExistingCourses = new ArrayList<Course>();
		
		try{
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(EXISTING_USER_COURSES);
			
			if(start < 0 && end < 0){
				sql = sql.replace("LIMIT [$START$], [$END$]", "");
			}else{
				sql = sql.replace("[$START$]", String.valueOf(start));
				sql = sql.replace("[$END$]", String.valueOf(start+end));
			}
			
			
		
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				log.debug("userId: " + userId);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("Lms_Course", CourseImpl.class);
			QueryPos qPos = QueryPos.getInstance(q);			
			qPos.add(userId);			
							
			listExistingCourses = (List<Course>)q.list();
			
			
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
		return listExistingCourses;
	}
	
	public List<Course> getOpenOrRestrictedChildCourses(long parentCourseId, int start, int end){
		Session session = null;
		List<Course> listExistingCourses = new ArrayList<Course>();
		
		try{
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(OPEN_OR_RESTRICTED_CHILD_COURSES);
			
			if(start < 0 && end < 0){
				sql = sql.replace("LIMIT [$START$], [$END$]", "");
			}else{
				sql = sql.replace("[$START$]", String.valueOf(start));
				sql = sql.replace("[$END$]", String.valueOf(start+end));
			}
			
			
		
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				log.debug("parentCourseId: " + parentCourseId);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("Lms_Course", CourseImpl.class);
			QueryPos qPos = QueryPos.getInstance(q);			
			qPos.add(parentCourseId);		
			qPos.add(GroupConstants.TYPE_SITE_OPEN);
			qPos.add(GroupConstants.TYPE_SITE_RESTRICTED);
							
			listExistingCourses = (List<Course>)q.list();
			
			
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
		return listExistingCourses;
	}
	
	
	
	public int countMyCourses(long groupId, long userId, ThemeDisplay themeDisplay){
		Session session = null;

		try{
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(COUNT_MY_COURSES);
			
			SimpleDateFormat parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = parseDate.format(new Date());
			
			sql = sql.replace("[$DATENOW$]", date);
		
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				log.debug("groupId: " + groupId);
				log.debug("userId: " + userId);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(userId);
			qPos.add(userId);
			qPos.add(groupId);
			qPos.add(groupId);
			
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
	
	
	public int countOpenOrRestrictedChildCourses(long parentCourseId){
		Session session = null;

		try{
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(COUNT_OPEN_OR_RESTRICTED_CHILD_COURSES);
			
			
		
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				log.debug("parentCourseId: " + parentCourseId);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar("COUNT_VALUE", Type.LONG);
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(parentCourseId);
			qPos.add(GroupConstants.TYPE_SITE_OPEN);
			qPos.add(GroupConstants.TYPE_SITE_RESTRICTED);
			
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
	
	
	public List<Group> getDistinctCourseGroups(long companyId){
		Session session = null;
		List<Group> distinctCourseGroups = new ArrayList<Group>();
		
		try{
			
			session = openSessionLiferay();
			
			String sql = CustomSQLUtil.get(GET_DISTINCT_COURSE_GROUPS);
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
			}
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("Group_",PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portal.model.impl.GroupImpl"));
			
			QueryPos qPos = QueryPos.getInstance(q);			
			qPos.add(companyId);				
			distinctCourseGroups = (List<Group>)q.list();
			
			
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSessionLiferay(session);
	    }
	
		return distinctCourseGroups;
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
