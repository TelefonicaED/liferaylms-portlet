package com.liferay.lms.service.persistence;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.impl.CourseImpl;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.util.CourseParams;
import com.liferay.lms.views.CourseResultView;
import com.liferay.lms.views.CourseView;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.CustomSQLParam;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

public class CourseFinderImpl extends BasePersistenceImpl<Course> implements CourseFinder{
	
	Log log = LogFactoryUtil.getLog(CourseFinderImpl.class);
	 
	public static final String FIND_BY_C_T_D_S_PC_G =
		    CourseFinder.class.getName() +
		        ".findByC_T_D_S_PC_G";
	public static final String COUNT_C_T_D_S_PC_G =
		    CourseFinder.class.getName() +
		        ".countC_T_D_S_PC_G";
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
	public static final String JOIN_BY_RESOURCE_PERMISSION_VIEW =
			CourseFinder.class.getName() + 
				".joinC_ByResourcePermissionView";
	public static final String JOIN_BY_CUSTOM_ATTRIBUTE = 
			CourseFinder.class.getName() + 
				".joinCustomAttribute";
	public static final String FIND_STUDENTS = 
			CourseFinder.class.getName() +
				".findStudents";
	public static final String COUNT_STUDENTS = 
			CourseFinder.class.getName() +
				".countStudents";
	public static final String FIND_TEACHERS = 
			CourseFinder.class.getName() +
				".findTeachers";
	public static final String COUNT_TEACHERS = 
			CourseFinder.class.getName() +
				".countTeachers";
	public static final String WHERE_VISIBLE = 
			CourseFinder.class.getName() + 
				".whereVisible";
	public static final String WHERE_TYPE = 
			CourseFinder.class.getName() + ".whereType";
	public static final String WHERE_TITLE_DESCRIPTION = 
			CourseFinder.class.getName() + ".whereTitleDescription";
	public static final String WHERE_GROUP_ID = 
			CourseFinder.class.getName() + ".whereGroupId";
	public static final String WHERE_STATUS = 
			CourseFinder.class.getName() + ".whereStatus";
	public static final String WHERE_PARENT_COURSE_ID = 
			CourseFinder.class.getName() + ".whereParentCourseId";
	public static final String WHERE_SCREEN_NAME =
		    CourseFinder.class.getName() +
		        ".whereScreenName";
	public static final String WHERE_FIRST_NAME =
		    CourseFinder.class.getName() +
		        ".whereFirstName";
	public static final String WHERE_LAST_NAME =
		    CourseFinder.class.getName() +
		        ".whereLastName";
	public static final String WHERE_EMAIL_ADDRESS =
		    CourseFinder.class.getName() +
		        ".whereEmailAddress";	
	public static final String WHERE_USER_SEARCH = 
			CourseFinder.class.getName() + 
				".whereUserSearch";
	public static final String WHERE_USER_STATUS = 
			CourseFinder.class.getName() + 
				".whereUserStatus";
	public static final String INNER_JOIN_TEAM = 
			CourseFinder.class.getName() + ".innerJoinTeam";
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
	public static final String COUNT_EXISTING_USER_COURSES =
			 CourseFinder.class.getName() +
				".countExistingUserCourses";
	public static final String GET_DISTINCT_COURSE_GROUPS = 
			CourseFinder.class.getName() + ".getDistinctCourseGroups";

	
	public static final String INNER_JOIN_CUSTOM_ATTRIBUTE =
			CourseFinder.class.getName() + ".innerCustomAttribute";
	
	public List<Course> findByKeywords(long companyId, String freeText, String languageId, int status, long parentCourseId, long groupId, LinkedHashMap<String, Object> params, 
			int start, int end, OrderByComparator obc){
		return findByC_T_D_S_PC_G(companyId, freeText, freeText, languageId, status, parentCourseId, groupId, params, false, start, end, obc);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Course> findByC_T_D_S_PC_G(long companyId, String title, String description, String languageId, int status, long parentCourseId, long groupId, 
			LinkedHashMap<String, Object> params, boolean andOperator, int start, int end, OrderByComparator obc){
		
		Session session = null;
		
		if(log.isDebugEnabled()){
			log.debug("CourseFinderImpl:findByC_T_D_S_PC_G");
			log.debug("companyId: " + companyId);
			log.debug("title: " + title);
			log.debug("description: " + description);
			log.debug("languageId: " + languageId);
			log.debug("status: " + status);
			log.debug("parentCourseId: " + parentCourseId);
			log.debug("groupId: " + groupId);
			log.debug("andOperator: " + andOperator);
			log.debug("start: " + start);
			log.debug("end: " + end);
		}
		
		
		try{
			
			if(Validator.isNotNull(title))
				title = "%" + title + "%";
			if(Validator.isNotNull(description))
				description = "%" + description + "%";
			
			if (params == null) {
				params = _emptyLinkedHashMap;
			}
			
			if(Validator.isNotNull(title) || Validator.isNotNull(description)){
				String[] titleDescription = {title, description};
				params.put(PARAM_TITLE_DESCRIPTION, titleDescription);
			}
			
			if(status != WorkflowConstants.STATUS_ANY){
				Boolean closed = status != WorkflowConstants.STATUS_APPROVED;
				params.put(PARAM_STATUS, closed);
			}
			
			if(!params.containsKey(CourseParams.PARAM_SEARCH_PARENT_AND_CHILD_COURSES)){
				params.put(PARAM_PARENT_COURSE_ID, parentCourseId);
			}
			
			if(groupId > 0){
				params.put(PARAM_GROUP_ID, groupId);
			}
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(FIND_BY_C_T_D_S_PC_G);
			
			StringBundler sb = new StringBundler();

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(replaceJoinAndWhere(sql, params, languageId, companyId));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			String orderBy = "";
			if (obc != null) {
				log.debug("obc: " + obc.getOrderBy());
				if(Validator.isNull(obc.getOrderBy()) || "title".equals(obc.getOrderBy())){
					log.debug("obc desc: " + obc.isAscending());
					orderBy = " ORDER BY IF (ExtractValue(lms_Course.title, '//Title[@language-id=\"[$LANGUAGE$]\"]' )='', ExtractValue(lms_Course.title,  '//root[@default-locale]//Title' ), ExtractValue(lms_Course.title, '//Title[@language-id=\"[$LANGUAGE$]\"]' )) ";
					if(!obc.isAscending()) orderBy += " DESC ";
					orderBy = StringUtil.replace(orderBy, "[$LANGUAGE$]", languageId);
				}else{
					log.debug("obc: " + obc.toString());
					orderBy = "ORDER BY " + obc.toString();
				}
			}else{
				log.debug("obc null ");
				orderBy = " ORDER BY IF (ExtractValue(lms_Course.title, '//Title[@language-id=\"[$LANGUAGE$]\"]' )='', ExtractValue(lms_Course.title,  '//root[@default-locale]//Title' ), ExtractValue(lms_Course.title, '//Title[@language-id=\"[$LANGUAGE$]\"]' )) ";
				orderBy = StringUtil.replace(orderBy, "[$LANGUAGE$]", languageId);
			}
			log.debug("order by");
			sb.append(orderBy);

			sql = sb.toString();
			
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			
			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Lms_Course", CourseImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, params);
			
			qPos.add(companyId);
			qPos.add(companyId);
			log.debug("*****QPOS****** CompanyId: " + companyId);
			log.debug("*****QPOS****** CompanyId: " + companyId);
			
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
			}
			
			List<Course> listCourse = (List<Course>) QueryUtil.list(
					q, getDialect(), start, end);
			
			return listCourse;
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return new ArrayList<Course>();
	}
	
	protected String replaceJoinAndWhere(
		String sql, LinkedHashMap<String, Object> params, String languageId, long companyId) throws PortalException, SystemException {

		sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(params, companyId));
		sql = StringUtil.replace(sql, "[$WHERE$]", getWhere(params, languageId));

		return sql;
	}
	
	protected String getJoin(LinkedHashMap<String, Object> params, long companyId) throws PortalException, SystemException {
		if ((params == null) || params.isEmpty()) {
			return StringPool.BLANK;
		}
		
		StringBundler sb = new StringBundler(params.size());
		
		if(params.containsKey(CourseParams.PARAM_CATEGORIES) || params.containsKey(CourseParams.PARAM_TAGS) ||
				params.containsKey(CourseParams.PARAM_AND_CATEGORIES) || params.containsKey(CourseParams.PARAM_AND_TAGS)
				|| params.containsKey(CourseParams.PARAM_VISIBLE)){
			String join = CustomSQLUtil.get(JOIN_BY_ASSET_ENTRY);
			long classNameId = ClassNameLocalServiceUtil.getClassNameId(Course.class.getName());
			join = StringUtil.replace(join, "[$CLASSNAMEID$]", String.valueOf(classNameId));
			sb.append(join);
		}

		Iterator<Map.Entry<String, Object>> itr = params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Object> entry = itr.next();

			String key = entry.getKey();

			Object value = entry.getValue();

			sb.append(getJoin(key, value, companyId));

		}

		return sb.toString();
	}
	
	protected String getJoin(String key, Object value, long companyId) throws PortalException, SystemException {
		String join = StringPool.BLANK;
		
		log.debug("join: " + key + " - " + value);

		if (key.equals(CourseParams.PARAM_CATEGORIES)) {
			join = CustomSQLUtil.get(JOIN_BY_ASSET_CATEGORY);
			join = StringUtil.replace(join, "[$i$]", "");
			if (value instanceof Long){
				Long category = (Long)value;
				join = StringUtil.replace(join, "[$CATEGORYIDS$]", String.valueOf(category));
			}else if(value instanceof long[]){
				long[] categories = (long[])value;
				if(categories.length == 1){
					join = StringUtil.replace(join, "[$CATEGORYIDS$]", String.valueOf(categories[0]));
				}else{
					String inCategories = StringPool.BLANK;
					for(long category: categories){
						inCategories += category + ",";
					}
					if(inCategories.length() > 0) inCategories = inCategories.substring(0, inCategories.length()-1);
					join = StringUtil.replace(join, "[$CATEGORYIDS$]", inCategories);
				}
			}	
		}
		else if (key.equals(CourseParams.PARAM_TAGS)) {
			join = CustomSQLUtil.get(JOIN_BY_ASSET_TAG);
			join = StringUtil.replace(join, "[$i$]", "");
			if (value instanceof Long){
				Long tag = (Long)value;
				join = StringUtil.replace(join, "[$TAGIDS$]", String.valueOf(tag));
			}else if(value instanceof long[]){
				long[] tags = (long[])value;
				if(tags.length == 1){
					join = StringUtil.replace(join, "[$TAGIDS$]", String.valueOf(tags[0]));
				}else{
					String inTags = StringPool.BLANK;
					for(long tag: tags){
						inTags += tag + ",";
					}
					if(inTags.length() > 0) inTags = inTags.substring(0, inTags.length()-1);
					join = StringUtil.replace(join, "[$TAGIDS$]", inTags);
				}
			}	
		}
		else if (key.equals(CourseParams.PARAM_TEMPLATES)) {
			join = CustomSQLUtil.get(JOIN_BY_TEMPLATES);
		}
		else if (key.equals(CourseParams.PARAM_PERMISSIONS_ADMIN)) {
			Long userId = (Long)value;
			join = CustomSQLUtil.get(JOIN_BY_RESOURCE_PERMISSION);
			LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(companyId);
			join = StringUtil.replace(join, "[$JOINRESOURCEPERMISSION$]", CustomSQLUtil.get(JOIN_BY_RESOURCE_PERMISSION));
			join = StringUtil.replace(join, "[$COMPANYID$]", String.valueOf(companyId));
			join = StringUtil.replace(join, "[$ACTIONPUBLISH$]", String.valueOf(ResourceActionLocalServiceUtil.getResourceAction(Course.class.getName(), "PUBLISH").getBitwiseValue()));
			join = StringUtil.replace(join, "[$ACTIONCOURSEEDITOR$]", String.valueOf(ResourceActionLocalServiceUtil.getResourceAction(Course.class.getName(), "COURSEEDITOR").getBitwiseValue()));
			join = StringUtil.replace(join, "[$ACTIONASSIGN_MEMBERS$]", String.valueOf(ResourceActionLocalServiceUtil.getResourceAction(Course.class.getName(), "ASSIGN_MEMBERS").getBitwiseValue()));
			join = StringUtil.replace(join, "[$ACTIONDELETE$]", String.valueOf(ResourceActionLocalServiceUtil.getResourceAction(Course.class.getName(), ActionKeys.DELETE).getBitwiseValue()));
			join = StringUtil.replace(join, "[$ACTIONPERMISSION$]", String.valueOf(ResourceActionLocalServiceUtil.getResourceAction(Course.class.getName(), ActionKeys.PERMISSIONS).getBitwiseValue()));
			join = StringUtil.replace(join, "[$ACTIONUPDATE$]", String.valueOf(ResourceActionLocalServiceUtil.getResourceAction(Course.class.getName(), ActionKeys.UPDATE).getBitwiseValue()));
			join = StringUtil.replace(join, "[$USERID$]", String.valueOf(userId));
			join = StringUtil.replace(join, "[$CLASSNAMEIDUSERGROUP$]", String.valueOf(ClassNameLocalServiceUtil.getClassNameId(UserGroup.class)));
			join = StringUtil.replace(join, "[$CLASSNAMEIDORGANIZATION$]", String.valueOf(ClassNameLocalServiceUtil.getClassNameId(Organization.class)));
			join = StringUtil.replace(join, "[$CLASSNAMEIDGROUP$]", String.valueOf(ClassNameLocalServiceUtil.getClassNameId(Group.class)));
			join = StringUtil.replace(join, "[$ROLEEDITOR$]", String.valueOf(prefs.getEditorRole()));
			join = StringUtil.replace(join, "[$ROLETEACHER$]", String.valueOf(prefs.getTeacherRole()));
		}else if(key.equals(CourseParams.PARAM_PERMISSIONS_VIEW)){
			Long userId = (Long)value;
			join = CustomSQLUtil.get(JOIN_BY_RESOURCE_PERMISSION);
			LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(companyId);
			join = StringUtil.replace(join, "[$JOINRESOURCEPERMISSION$]", CustomSQLUtil.get(JOIN_BY_RESOURCE_PERMISSION_VIEW));
			join = StringUtil.replace(join, "[$COMPANYID$]", String.valueOf(companyId));
			join = StringUtil.replace(join, "[$ACTIONVIEW$]", String.valueOf(ResourceActionLocalServiceUtil.getResourceAction(Course.class.getName(), ActionKeys.VIEW).getBitwiseValue()));
			join = StringUtil.replace(join, "[$USERID$]", String.valueOf(userId));
			join = StringUtil.replace(join, "[$CLASSNAMEIDUSERGROUP$]", String.valueOf(ClassNameLocalServiceUtil.getClassNameId(UserGroup.class)));
			join = StringUtil.replace(join, "[$CLASSNAMEIDORGANIZATION$]", String.valueOf(ClassNameLocalServiceUtil.getClassNameId(Organization.class)));
			join = StringUtil.replace(join, "[$CLASSNAMEIDGROUP$]", String.valueOf(ClassNameLocalServiceUtil.getClassNameId(Group.class)));
			join = StringUtil.replace(join, "[$ROLEEDITOR$]", String.valueOf(prefs.getEditorRole()));
			join = StringUtil.replace(join, "[$ROLETEACHER$]", String.valueOf(prefs.getTeacherRole()));
		}
		else if (key.equals(CourseParams.PARAM_CUSTOM_ATTRIBUTE)) {
			join = CustomSQLUtil.get(JOIN_BY_CUSTOM_ATTRIBUTE);
		}
		else if(key.equals(CourseParams.PARAM_AND_TAGS)){
			if (value instanceof Long){
				join += CustomSQLUtil.get(JOIN_BY_ASSET_TAG);
				join = StringUtil.replace(join, "[$i$]", "");
				Long tag = (Long)value;
				join = StringUtil.replace(join, "[$TAGIDS$]", String.valueOf(tag));
			}else if(value instanceof long[]){
				long[] tags = (long[])value;
				for(int i = 0; i < tags.length; i++){
					join += CustomSQLUtil.get(JOIN_BY_ASSET_TAG);
					join = StringUtil.replace(join, "[$i$]", String.valueOf(i));
					join = StringUtil.replace(join, "[$TAGIDS$]", String.valueOf(tags[i]));
				}
			}
		}
		else if(key.equals(CourseParams.PARAM_AND_CATEGORIES)){
			if (value instanceof Long){
				join += CustomSQLUtil.get(JOIN_BY_ASSET_CATEGORY);
				join = StringUtil.replace(join, "[$i$]", "");
			}else if(value instanceof long[]){	
				long[] categories = (long[])value;
				for(int i = 0; i < categories.length; i++){
					join += CustomSQLUtil.get(JOIN_BY_ASSET_CATEGORY);
					join = StringUtil.replace(join, "[$i$]", String.valueOf(i));
					join = StringUtil.replace(join, "[$CATEGORYIDS$]", String.valueOf(categories[i]));
				}
			}
		}
		else if (value instanceof CustomSQLParam) {
			CustomSQLParam customSQLParam = (CustomSQLParam)value;

			join = customSQLParam.getSQL();
		}

		if (Validator.isNotNull(join)) {
			int pos = join.indexOf("WHERE");

			if (pos != -1) {
				join = join.substring(0, pos);
			}
		}

		return join;
	}
	
	protected String getWhere(LinkedHashMap<String, Object> params, String languageId) {
		if ((params == null) || params.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(params.size());

		Iterator<Map.Entry<String, Object>> itr = params.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Object> entry = itr.next();

			String key = entry.getKey();

			Object value = entry.getValue();

			sb.append(getWhere(key, value, languageId));
		}

		return sb.toString();
	}

	protected String getWhere(String key, Object value, String languageId) {
		String join = StringPool.BLANK;
		
		log.debug("where: " + key + " - " + value);

		if (key.equals(CourseParams.PARAM_TEMPLATES)) {
			join = CustomSQLUtil.get(JOIN_BY_TEMPLATES);
			if(value instanceof String){
				join = StringUtil.replace(join, "IN ([$TEMPLATES$])", "= " + value);
			}else if(value instanceof String[]){
				String[] ids = (String[])value;
				String idsPos = StringPool.BLANK;
				for(int i = 0; i < ids.length; i++){
					idsPos += "?,";
				}
				if(idsPos.length() > 0) idsPos = idsPos.substring(0, idsPos.length()-1);
				join = StringUtil.replace(join, "[$TEMPLATES$]", idsPos);
			}
		}
		else if (key.equals(CourseParams.PARAM_PERMISSIONS_ADMIN)) {
			join = CustomSQLUtil.get(JOIN_BY_RESOURCE_PERMISSION);
		}else if(key.equals(CourseParams.PARAM_PERMISSIONS_VIEW)){
			join = CustomSQLUtil.get(JOIN_BY_RESOURCE_PERMISSION_VIEW);
		}
		else if (key.equals(CourseParams.PARAM_CUSTOM_ATTRIBUTE)) {
			join = CustomSQLUtil.get(JOIN_BY_CUSTOM_ATTRIBUTE);
		}
		else if (key.equals(CourseParams.PARAM_VISIBLE)) {
			join = CustomSQLUtil.get(WHERE_VISIBLE);
		}
		else if (key.equals(CourseParams.PARAM_TYPE)) {
			join = CustomSQLUtil.get(WHERE_TYPE);
			if(value instanceof Integer){
				join = StringUtil.replace(join, "IN [$TYPE$]", "= ?");
			}else if(value instanceof int[]){
				int[] types = (int[])value;
				String typePos = StringPool.BLANK;
				for(long type: types){
					typePos += "?,";
				}
				if(typePos.length() > 0) typePos = typePos.substring(0, typePos.length()-1);
				join = StringUtil.replace(join, "[$TYPE$]", typePos);
			}		
		}
		else if (key.equals(PARAM_TITLE_DESCRIPTION)) {
			join = CustomSQLUtil.get(WHERE_TITLE_DESCRIPTION);
			join = StringUtil.replace(join, "[$LANGUAGE$]", languageId);
		}
		else if (key.equals(PARAM_GROUP_ID)) {
			join = CustomSQLUtil.get(WHERE_GROUP_ID);
		}
		else if (key.equals(PARAM_STATUS)) {
			join = CustomSQLUtil.get(WHERE_STATUS);
		}
		else if (key.equals(PARAM_PARENT_COURSE_ID)) {
			join = CustomSQLUtil.get(WHERE_PARENT_COURSE_ID);
		}
		else if (value instanceof CustomSQLParam) {
			CustomSQLParam customSQLParam = (CustomSQLParam)value;

			join = customSQLParam.getSQL();
		}

		if (Validator.isNotNull(join)) {
			int pos = join.indexOf("WHERE");

			if (pos != -1) {
				join = join.substring(pos + 5, join.length()).concat(" AND ");
			}
			else {
				join = StringPool.BLANK;
			}
		}

		return join;
	}
	
	protected void setJoin(
		QueryPos qPos, LinkedHashMap<String, Object> params) throws PortalException, SystemException {

		if (params == null) {
			return;
		}
		

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();

			Object value = entry.getValue();
						
			log.debug("setJoin: " + key + " - " + value);
			
			if(key.equals(CourseParams.PARAM_CUSTOM_ATTRIBUTE)){
				Object[] paramsValue = (Object[]) value;
				Long columnId = (Long)paramsValue[0];
				String dataValue = (String)paramsValue[1];
				dataValue = "%" + dataValue + "%";
				ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.getColumn(columnId);
				qPos.add(dataValue);
				qPos.add(expandoColumn.getTableId());
				qPos.add(columnId);
				log.debug("*****QPOS****** Custom attribute: " + dataValue);
				log.debug("*****QPOS****** Custom attribute: " + expandoColumn.getTableId());
				log.debug("*****QPOS****** Custom attribute: " + columnId);
			}else if(key.equals(PARAM_PARENT_COURSE_ID)){
				Long valueLong = (Long)value;
				qPos.add(valueLong);
				qPos.add(valueLong);
				log.debug("*****QPOS****** Parent Course Id: " + valueLong);
				log.debug("*****QPOS****** Parent Course Id: " + valueLong);
			}else if(key.equals(PARAM_TITLE_DESCRIPTION)){
				String[] valueArray = (String[])value;
				
				for (String element : valueArray) {
					if (Validator.isNotNull(element)) {
						qPos.add(element);
						qPos.add(element);
						log.debug("*****QPOS****** " + key + ": " + element);
						log.debug("*****QPOS****** " + key + ": " + element);
					}
				}
			
			}else if(!key.equals(CourseParams.PARAM_CATEGORIES) && !key.equals(CourseParams.PARAM_TAGS) &&
					!key.equals(CourseParams.PARAM_AND_TAGS) && !key.equals(CourseParams.PARAM_AND_CATEGORIES) &&
					!key.equals(CourseParams.PARAM_PERMISSIONS_ADMIN) && !key.equals(CourseParams.PARAM_SEARCH_PARENT_AND_CHILD_COURSES)
					&& !key.equals(CourseParams.PARAM_PERMISSIONS_VIEW)){
				if (value instanceof Long) {
					Long valueLong = (Long)value;
	
					if (valueLong != null) {
						qPos.add(valueLong);
						log.debug("*****QPOS****** " + key + ": " + valueLong);
					}
				}
				else if (value instanceof Long[]) {
					Long[] valueArray = (Long[])value;
	
					for (Long element : valueArray) {
						if (element != null) {
							qPos.add(element);
							log.debug("*****QPOS****** " + key + ": " + element);
						}
					}
				}
				else if (value instanceof long[]) {
					long[] valueArray = (long[])value;
	
					for (long element : valueArray) {
						qPos.add(element);
						log.debug("*****QPOS****** " + key + ": " + element);
					}
				}
				else if (value instanceof int[]) {
					int[] valueArray = (int[])value;
	
					for (int element : valueArray) {
						qPos.add(element);
						log.debug("*****QPOS****** " + key + ": " + element);
					}
				}
				else if (value instanceof Long[][]) {
					Long[][] valueDoubleArray = (Long[][])value;
	
					for (Long[] valueArray : valueDoubleArray) {
						for (Long valueLong : valueArray) {
							qPos.add(valueLong);
							log.debug("*****QPOS****** " + key + ": " + valueLong);
						}
					}
				}
				else if (value instanceof String) {
					String valueString = (String)value;
	
					if (Validator.isNotNull(valueString)) {
						qPos.add(valueString);
						log.debug("*****QPOS****** " + key + ": " + valueString);
					}
				}
				else if (value instanceof String[]) {
					String[] valueArray = (String[])value;
	
					for (String element : valueArray) {
						if (Validator.isNotNull(element)) {
							qPos.add(element);
							log.debug("*****QPOS****** " + key + ": " + element);
						}
					}
				}
				else if (value instanceof Boolean) {
					Boolean valueBoolean = (Boolean)value;
	
					if (Validator.isNotNull(valueBoolean)) {
						qPos.add(valueBoolean);
						log.debug("*****QPOS****** " + key + ": " + valueBoolean);
					}
				}
				else if (value instanceof CustomSQLParam) {
					CustomSQLParam customSQLParam = (CustomSQLParam)value;
	
					customSQLParam.process(qPos);
				}
			}
		}
	}

	public int countByKeywords(long companyId, String freeText, String languageId, int status, long parentCourseId, long groupId, LinkedHashMap<String, Object> params){
		return countByC_T_D_S_PC_G(companyId, freeText, freeText, languageId, status, parentCourseId, groupId, params, false);
	}
	
	public int countByC_T_D_S_PC_G(long companyId, String title, String description, String languageId, int status, long parentCourseId, long groupId, 
			LinkedHashMap<String, Object> params, boolean andOperator){
		Session session = null;
		
		int countValue = 0;
		
		try{
			
			if(Validator.isNotNull(title))
				title = "%" + title + "%";
			if(Validator.isNotNull(description))
				description = "%" + description + "%";
			
			if (params == null) {
				params = _emptyLinkedHashMap;
			}
			
			if(Validator.isNotNull(title) || Validator.isNotNull(description)){
				String[] titleDescription = {title, description};
				params.put(PARAM_TITLE_DESCRIPTION, titleDescription);
			}
			
			if(status != WorkflowConstants.STATUS_ANY){
				Boolean closed = status != WorkflowConstants.STATUS_APPROVED;
				params.put(PARAM_STATUS, closed);
			}
			
			if(!params.containsKey(CourseParams.PARAM_SEARCH_PARENT_AND_CHILD_COURSES)){
				params.put(PARAM_PARENT_COURSE_ID, parentCourseId);
			}
			
			if(groupId > 0){
				params.put(PARAM_GROUP_ID, groupId);
			}
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(COUNT_C_T_D_S_PC_G);
			
			StringBundler sb = new StringBundler();

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(replaceJoinAndWhere(sql, params, languageId, companyId));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			sql = sb.toString();
			
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			
			SQLQuery q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, params);
			
			qPos.add(companyId);
			qPos.add(companyId);
			
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				log.debug("parentCourseId: " + parentCourseId);
				log.debug("status: " + status);
				log.debug("companyId: " + companyId);
				log.debug("groupId: " + groupId);
				log.debug("title: " + title);
				log.debug("description: " + description);
				log.debug("andOperator: " + andOperator);
			}
			
			Iterator<Object> itr = q.iterate();

			if (itr.hasNext()) {
				Object count = itr.next();
				
				if (count != null) {
					if(count instanceof Long){
						countValue = ((Long)count).intValue();
					}else if(count instanceof BigInteger){
						countValue = ((BigInteger)count).intValue();
					}else if(count instanceof Integer){
						countValue = (Integer)count;
					}
					
				}
			}
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
	    return countValue;
	}
	

	public List<User> findStudents(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, long[] teamIds,boolean andOperator, 
			int start, int end,OrderByComparator obc){
		Session session = null;
		try{
			
			/** Para la query es necesario si no es null o vacío que añade los porcentajes, y si es vacío ponerlo a null*/
			if(log.isDebugEnabled()){
				log.debug("ScreenName:"+screenName);
				log.debug("firstName:"+firstName);
				log.debug("lastName:"+lastName);
				log.debug("emailAddress:"+emailAddress);
				log.debug("start: " + start);
				log.debug("end: " + end);
				log.debug("order: " + obc != null ? obc.toString() : "null");
			}
			
			
			session = openSessionLiferay();
			String sql = CustomSQLUtil.get(FIND_STUDENTS);
			
			sql = replaceJoinWhereUser(sql, screenName, firstName, lastName, emailAddress, status, teamIds, andOperator);
			
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			
			sql = replaceOrderUser(sql, obc, companyId);
			
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

			if(status != WorkflowConstants.STATUS_ANY){
				qPos.add(status);
			}
			
			qPos = replaceQPosJoinWhereUser(qPos, screenName, firstName, lastName, emailAddress);
					
			
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
	
	public int countStudents(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, long[] teamIds,
			boolean andOperator){
		Session session = null;
		try{
			
			if(log.isDebugEnabled()){
				log.debug("ScreenName:"+screenName);
				log.debug("firstName:"+firstName);
				log.debug("lastName:"+lastName);
				log.debug("emailAddress:"+emailAddress);
			}
						
			session = openSessionLiferay();
			
			String sql = CustomSQLUtil.get(COUNT_STUDENTS);
			
			sql = replaceJoinWhereUser(sql, screenName, firstName, lastName, emailAddress, status, teamIds, andOperator);
			
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			
			log.debug("sql: " + sql);
			
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
			if(status != WorkflowConstants.STATUS_ANY){
				qPos.add(status);
			}
			
			qPos = replaceQPosJoinWhereUser(qPos, screenName, firstName, lastName, emailAddress);
			
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
	
	public List<User> findTeachers(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, long[] teamIds,boolean andOperator, 
			int start, int end,OrderByComparator obc){
		//Obtenemos el rol de editor del curso y profesor
		long roleId = 0;
		try {
			LmsPrefs prefs = LmsPrefsLocalServiceUtil.getLmsPrefs(companyId);
			roleId = prefs.getTeacherRole();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return findTeachersEditors(courseId, companyId, screenName, firstName, lastName, emailAddress, status, teamIds, roleId, andOperator, start, end, obc);
	}
	
	public List<User> findEditors(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, long[] teamIds,boolean andOperator, 
			int start, int end,OrderByComparator obc){
		//Obtenemos el rol de editor del curso y profesor
		long roleId = 0;
		try {
			LmsPrefs prefs = LmsPrefsLocalServiceUtil.getLmsPrefs(companyId);
			roleId = prefs.getEditorRole();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return findTeachersEditors(courseId, companyId, screenName, firstName, lastName, emailAddress, status, teamIds, roleId, andOperator, start, end, obc);
	}
	
	private List<User> findTeachersEditors(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, long[] teamIds,
			long roleId, boolean andOperator, int start, int end,OrderByComparator obc){
		Session session = null;
		
		try{
			
			/** Para la query es necesario si no es null o vacío que añade los porcentajes, y si es vacío ponerlo a null*/
			if(log.isDebugEnabled()){
				log.debug("ScreenName:"+screenName);
				log.debug("firstName:"+firstName);
				log.debug("lastName:"+lastName);
				log.debug("emailAddress:"+emailAddress);
				log.debug("start: " + start);
				log.debug("end: " + end);
			}
			
			
			session = openSessionLiferay();
			String sql = CustomSQLUtil.get(FIND_TEACHERS);
			
			sql = replaceJoinWhereUser(sql, screenName, firstName, lastName, emailAddress, status, teamIds, andOperator);
			
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			
			sql = replaceOrderUser(sql, obc, companyId);
			
			if(start >= 0 && end >= 0){
				sql += " LIMIT " + start + ", " + (end-start);
			}
			
			if(log.isDebugEnabled()) log.debug("sql: " + sql);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("User_",PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portal.model.impl.UserImpl"));
			
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(roleId);
			qPos.add(courseId);

			if(status != WorkflowConstants.STATUS_ANY){
				qPos.add(status);
			}
			
			qPos = replaceQPosJoinWhereUser(qPos, screenName, firstName, lastName, emailAddress);	
			
			if(log.isDebugEnabled()){
				log.debug("roleId: " + roleId);
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
	
	public int countTeachers(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, long[] teamIds,boolean andOperator){
		//Obtenemos el rol de editor del curso y profesor
		long roleId = 0;
		try {
			LmsPrefs prefs = LmsPrefsLocalServiceUtil.getLmsPrefs(companyId);
			roleId = prefs.getTeacherRole();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return countTeachersEditors(courseId, companyId, screenName, firstName, lastName, emailAddress, status, teamIds, roleId, andOperator);
	}
	
	public int countEditors(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, long[] teamIds,boolean andOperator){
		//Obtenemos el rol de editor del curso y profesor
		long roleId = 0;
		try {
			LmsPrefs prefs = LmsPrefsLocalServiceUtil.getLmsPrefs(companyId);
			roleId = prefs.getEditorRole();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return countTeachersEditors(courseId, companyId, screenName, firstName, lastName, emailAddress, status, teamIds, roleId, andOperator);
	}
	
	public int countTeachersEditors(long courseId, long companyId, String screenName, String firstName, String lastName, String emailAddress, int status, long[] teamIds, long roleId,
			boolean andOperator){
		Session session = null;
		try{
			
			if(log.isDebugEnabled()){
				log.debug("ScreenName:"+screenName);
				log.debug("firstName:"+firstName);
				log.debug("lastName:"+lastName);
				log.debug("emailAddress:"+emailAddress);
			}
						
			session = openSessionLiferay();
			
			String sql = CustomSQLUtil.get(COUNT_TEACHERS);
			
			sql = replaceJoinWhereUser(sql, screenName, firstName, lastName, emailAddress, status, teamIds, andOperator);
			
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);
			
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(roleId);
			qPos.add(courseId);
			if(status != WorkflowConstants.STATUS_ANY){
				qPos.add(status);
			}
			
			qPos = replaceQPosJoinWhereUser(qPos, screenName, firstName, lastName, emailAddress);
			
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
	
	private QueryPos replaceQPosJoinWhereUser(QueryPos qPos, String screenName, String firstName, String lastName, String emailAddress) {
		
		if(Validator.isNotNull(screenName)){
			screenName = "%"+screenName+"%";
			qPos.add(screenName);
		}
		if(Validator.isNotNull(firstName)){
			firstName = "%"+firstName+"%";
			qPos.add(firstName);
		}
		if(Validator.isNotNull(lastName)){
			lastName = "%"+lastName+"%";
			qPos.add(lastName);
		}			
		if(Validator.isNotNull(emailAddress)){
			emailAddress = "%"+emailAddress+"%";
			qPos.add(emailAddress);
		}
		
		return qPos;
	}


	private String replaceOrderUser(String sql, OrderByComparator obc, long companyId) {
		if (obc != null && obc.getOrderBy() != null && !obc.getOrderBy().equals("")) {
			sql = sql.replace("[$ORDERBY$]", obc.toString());
		}else{
			try {
				PortletPreferences prefs = PortalPreferencesLocalServiceUtil.getPreferences(companyId, companyId, 1);
				if(Boolean.parseBoolean(prefs.getValue("users.first.last.name", "false"))){
					sql = sql.replace("[$ORDERBY$]", "u.lastName, u.firstName, u.middleName ");
				}else{
					sql = sql.replace("[$ORDERBY$]", "u.firstName, u.middleName, u.lastName ");
				}
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				sql = sql.replace("[$ORDERBY$]", "u.lastName, u.firstName, u.middleName ");
			}
		}
		
		return sql;
	}


	private String replaceJoinWhereUser(String sql, String screenName, String firstName, String lastName, String emailAddress, int status, long[] teamIds, boolean andOperator){
		boolean whereClause = false;
		if(teamIds != null && teamIds.length > 0){
			sql = StringUtil.replace(sql, "[$JOINTEAM$]", CustomSQLUtil.get(INNER_JOIN_TEAM));
			String teams = "";
			for(int i = 0; i < teamIds.length;i++){
				teams += teamIds[i] + ",";
			}
			if(teams.length() > 0){
				teams = teams.substring(0, teams.length()-1);
			}
			sql = StringUtil.replace(sql, "[$TEAMIDS$]", teams);
		}else{
			sql = sql.replace("[$JOINTEAM$]", "");
		}
		
		String sqlWhere = CustomSQLUtil.get(WHERE_USER_SEARCH);
		if(Validator.isNotNull(screenName)){
			sqlWhere = sqlWhere.replace("[$WHERESCREENNAME$]", CustomSQLUtil.get(WHERE_SCREEN_NAME));
			whereClause=true;
		}else{
			sqlWhere = sqlWhere.replace("[$WHERESCREENNAME$]", "");
		}
		if(Validator.isNotNull(firstName)){
			sqlWhere = sqlWhere.replace("[$WHEREFIRSTNAME$]", CustomSQLUtil.get(WHERE_FIRST_NAME));
			whereClause=true;
		}else{
			sqlWhere = sqlWhere.replace("[$WHEREFIRSTNAME$]", "");
		}
		if(Validator.isNotNull(lastName)){
			sqlWhere = sqlWhere.replace("[$WHERELASTNAME$]", CustomSQLUtil.get(WHERE_LAST_NAME));
			whereClause=true;
		}else{
			sqlWhere = sqlWhere.replace("[$WHERELASTNAME$]", "");
		}
		if(Validator.isNotNull(emailAddress)){
			sqlWhere = sqlWhere.replace("[$WHEREEMAILADDRESS$]", CustomSQLUtil.get(WHERE_EMAIL_ADDRESS));
			whereClause=true;
		}else{
			sqlWhere = sqlWhere.replace("[$WHEREEMAILADDRESS$]", "");
		}
		if(status != WorkflowConstants.STATUS_ANY){
			sql = StringUtil.replace(sql, "[$WHERESTATUS$]", CustomSQLUtil.get(WHERE_USER_STATUS));
		}else{
			sql = StringUtil.replace(sql, "[$WHERESTATUS$]", "");
		}

		if(whereClause && !andOperator){
			sqlWhere = sqlWhere.replace("[$DEFAULT$]", " 1 = 0 ");	
			sql = StringUtil.replace(sql, "[$WHERESEARCH$]", sqlWhere);
		}else if(whereClause && andOperator){
			sqlWhere = sqlWhere.replace("[$DEFAULT$]", " 1 = 1 ");	
			sql = StringUtil.replace(sql, "[$WHERESEARCH$]", sqlWhere);
		}else{
			sql = StringUtil.replace(sql, "[$WHERESEARCH$]", "");
		}
		
		return sql;
	}
	
	private String replaceLanguage(String sql, String language) {
		/**Reemplazamos el lenguage**/
		return sql.replace("[$LANGUAGE$]", language);
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
	
	public List<CourseResultView> getMyCourses(long groupId, long userId, LinkedHashMap<String, Object> params, ThemeDisplay themeDisplay, String orderByColumn, String orderByType, int start, int end){
		Session session = null;
		List<CourseResultView> listMyCourses = new ArrayList<CourseResultView>();
		
		try{
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(MY_COURSES);
			
			StringBundler sb = new StringBundler();

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(replaceJoinAndWhere(sql, params, themeDisplay.getLanguageId(), themeDisplay.getCompanyId()));
			sb.append(StringPool.CLOSE_PARENTHESIS);
			
			sql = sb.toString();
			
			sql = replaceLanguage(sql, themeDisplay.getLanguageId());
					
			SimpleDateFormat parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = parseDate.format(new Date());
			
			sql = sql.replace("[$DATENOW$]", date);
			
			if(Validator.isNull(orderByColumn)){
				orderByColumn = "Lms_Course.courseId";
			}
			
			if(Validator.isNotNull(orderByColumn)){
				if(orderByColumn.startsWith("c.")){
					orderByColumn = StringUtil.replaceFirst(orderByColumn, "c.", "Lms_Course.");
				}
				if(orderByColumn.contains(" c.")){
					orderByColumn = StringUtil.replaceFirst(orderByColumn, " c.", " Lms_Course.");
				}
				if(orderByColumn.contains("(c.")){
					orderByColumn = StringUtil.replaceFirst(orderByColumn, "(c.", "(Lms_Course.");
				}
				if(orderByColumn.contains(",c.")){
					orderByColumn = StringUtil.replaceFirst(orderByColumn, ",c.", ",Lms_Course.");
				}
				sql += " ORDER BY " + orderByColumn + " " + orderByType;
			}
			
			if(start >= 0 && end >= 0){
				sql += " LIMIT " + start + "," + (end-start);
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
			String url = null;
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
				
				url = themeDisplay.getPortalURL()+"/"+themeDisplay.getLocale().getLanguage()+"/web" + (String)myCourse[7];
			     
			    if(themeDisplay.isImpersonated()){
			    	String doAsUserId = "?doAsUserId=".concat(URLEncoder.encode(themeDisplay.getDoAsUserId(),"UTF-8"));
			    	url+=doAsUserId; 
			    }
			    courseView.setUrl(url);
			    courseView.setExecutionStartDate((Date)myCourse[8]);
			    courseView.setExecutionEndDate((Date)myCourse[9]);
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
	
	
	public int countExistingUserCourses(long userId){
		Session session = null;
		int countValue = 0;
		try{
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(COUNT_EXISTING_USER_COURSES);
			
			
			if(log.isDebugEnabled()){
				log.debug("sql: " + sql);
				log.debug("userId: " + userId);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(userId);
			
			Iterator<Long> itr = q.iterate();

			
			if (itr.hasNext()) {
				Object count = itr.next();
				
				if (count != null) {
					if(count instanceof Long){
						countValue = ((Long)count).intValue();
					}else if(count instanceof BigInteger){
						countValue = ((BigInteger)count).intValue();
					}else if(count instanceof Integer){
						countValue = (Integer)count;
					}
					
				}
			}
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
		return countValue;
	}
	
	public int countMyCourses(long groupId, long userId, LinkedHashMap<String, Object> params, ThemeDisplay themeDisplay){
		Session session = null;
		int countValue = 0;
		try{
			
			session = openSession();
			
			String sql = CustomSQLUtil.get(COUNT_MY_COURSES);
			
			StringBundler sb = new StringBundler();

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(replaceJoinAndWhere(sql, params, themeDisplay.getLanguageId(), themeDisplay.getCompanyId()));
			sb.append(StringPool.CLOSE_PARENTHESIS);
			
			sql = sb.toString();
			
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
				Object count = itr.next();
				
				if (count != null) {
					if(count instanceof Long){
						countValue = ((Long)count).intValue();
					}else if(count instanceof BigInteger){
						countValue = ((BigInteger)count).intValue();
					}else if(count instanceof Integer){
						countValue = (Integer)count;
					}
					
				}
			}
			
		} catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	        closeSession(session);
	    }
	
		return countValue;
	}
	
	protected void setJoinCustomAttribute(
			QueryPos qPos, long columnId, String expandoValue){
		if(columnId > 0 && Validator.isNotNull(expandoValue)){
			try {
				ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.getColumn(columnId);
				qPos.add(expandoColumn.getTableId());
				qPos.add(columnId);
				expandoValue = "%" + expandoValue + "%";
				qPos.add(expandoValue);
			} catch (PortalException | SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				qPos.add(0);
				qPos.add(0);
				qPos.add("");
			}

		}
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
	
	private static final String PARAM_TITLE_DESCRIPTION = "title";
	private static final String PARAM_GROUP_ID = "groupId";
	private static final String PARAM_STATUS = "status";
	private static final String PARAM_PARENT_COURSE_ID = "parentCourseId";

	private LinkedHashMap<String, Object> _emptyLinkedHashMap =
		new LinkedHashMap<String, Object>(0);
	
}
