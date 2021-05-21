package com.liferay.lms.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import com.liferay.lms.NoSuchCourseException;
import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class ImportCsvUnassignUsersThread extends ImportCsvThread {

	private final int HEADER_LENGTH = 2;
	
	protected long roleId;
	protected String authType;
	private long teacherRoleId;
	private long editorRoleId;
	private long siteMemberRoleId;
	private long siteOwnerRoleId;
	
	public ImportCsvUnassignUsersThread(long roleId, String authType,
			InputStream csvFile, String idThread, ThemeDisplay themeDisplay) {
		
		super(csvFile, idThread, themeDisplay);
		
		this.roleId = roleId;
		this.authType = authType;
		
		try {
			
			LmsPrefs prefs = LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
			this.teacherRoleId = RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getRoleId();
			this.editorRoleId = RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getRoleId();
			
			Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
			Role siteOwnerRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_OWNER);
			
			this.siteMemberRoleId = Validator.isNotNull(siteMemberRole) ? siteMemberRole.getRoleId() : -1;
			this.siteOwnerRoleId = Validator.isNotNull(siteOwnerRole) ? siteOwnerRole.getRoleId() : -1;
		
		} catch (PortalException | SystemException e) {
			this.teacherRoleId = -1;
			this.editorRoleId = -1;
			this.siteMemberRoleId = -1;
			this.siteOwnerRoleId = -1;
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	protected void readAndProcessCsv(){
		
		if(teacherRoleId>-1 && editorRoleId>-1 && siteMemberRoleId>-1 && siteOwnerRoleId>-1){
			
			CSVReader reader = null;
			
			try{
				InputStreamReader fstream = new InputStreamReader(csvFile, StringPool.UTF8);
				reader = new CSVReader(fstream,CharPool.SEMICOLON);
				totalLines = getTotalLines(reader);
				_log.debug(":::totalLines::: " + totalLines);
				
				if(totalLines < 1){
					errors.add(LanguageUtil.get(themeDisplay.getLocale(), "courseadmin.import.csv.error.no-lines"));
					_log.error(":: IMPORT DATA :: EMPTY FILE ::");
				} else {
					csvFile.reset();
					reader = new CSVReader(fstream,CharPool.SEMICOLON);
					
					String[] strLine = null;
					line = 0;
					boolean isFirstLine = Boolean.TRUE;
					boolean isCorrect = Boolean.TRUE;
					boolean isCorrectLine = Boolean.TRUE;
					
					while (Validator.isNotNull(reader) && (strLine = reader.readNext()) != null && isCorrect) {
						_log.debug("::::: line ::: " + line);
						
						if (!isFirstLine) {
							isCorrectLine = processLine(strLine);
							if(isCorrectLine)
								numCorrectLines++;
						}else{
							isFirstLine = Boolean.FALSE;
							//Compruebo la cabecera
							headerLength = getHeaderLength(strLine);
							_log.debug("::headerLength::: " + headerLength);
							isCorrect = checkHead(headerLength, HEADER_LENGTH);
						}
						
						line++;
						progress = ((line - 1) * 100) / totalLines;
					}
				}
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				if(Validator.isNotNull(reader)){
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		} else {
			_log.error(":: Teacher or editor role not found in companyId = " + themeDisplay.getCompanyId());
			errors.add(LanguageUtil.get(themeDisplay.getLocale(), "courseadmin.import.csv.error.no-roles-editor-tutor"));
		}
		
	}
	
	private boolean processLine(String[] data){
		
		boolean result = checkLineLength(data, HEADER_LENGTH);
		
		if(result){
			
			String userData = data[0];
			_log.debug("::userData:: " + userData);
			
			result = Validator.isNotNull(userData) && !StringPool.BLANK.equals(userData);
			
			if(result){
				
				User user = null;
				
				try{
					switch (authType) {
					
						case CompanyConstants.AUTH_TYPE_SN:
							user = UserLocalServiceUtil.getUserByScreenName(themeDisplay.getCompanyId(), userData);
							break;
			
						case CompanyConstants.AUTH_TYPE_EA:
							user = UserLocalServiceUtil.getUserByEmailAddress(themeDisplay.getCompanyId(), userData);
							break;
						
						default:
							user = UserLocalServiceUtil.getUser(Long.parseLong(userData.trim()));
							break;
					}
			
				} catch(NoSuchUserException e){
					
					_log.error("::No such user exception:: " + e.getMessage());
					errors.add(LanguageUtil.format(themeDisplay.getLocale(),
							"courseadmin.importuserrole.csvError.user-id-not-found", 
							new Object[] { line, userData }) + StringPool.NEW_LINE);
					result = Boolean.FALSE;
					
				} catch(NumberFormatException e){
					
					_log.error(":: USER :: NUMBER FORMAT EXCEPTION :: LINE - " + line);
					errors.add(LanguageUtil.format(themeDisplay.getLocale(),
							"courseadmin.importuserrole.csvError.user-id-bad-format", line) + StringPool.NEW_LINE);
					result = Boolean.FALSE;
				
				} catch (PortalException | SystemException e) {
					
					e.printStackTrace();
					errors.add(LanguageUtil.format(themeDisplay.getLocale(),
							"courseadmin.importuserrole.csvError.user-id-not-found",
							new Object[] { line, userData }) + StringPool.NEW_LINE);
					result = Boolean.FALSE;
			
				} catch(Exception e){
					
					e.printStackTrace();
					errors.add(LanguageUtil.format(themeDisplay.getLocale(),
							"courseadmin.importuserrole.csvError.user-id-not-found",
							new Object[] { line, userData }) + StringPool.NEW_LINE);
					result = Boolean.FALSE;
				}
				
				if(result){
					
					long userId = user.getUserId();
					
					long courseId = -1;
					
					try{
						courseId = Long.parseLong(data[1]);
					
					} catch(NumberFormatException e){
						
						_log.error(":: COURSE :: NUMBER FORMAT EXCEPTION :: LINE - " + line);
						errors.add(LanguageUtil.format(themeDisplay.getLocale(),
								"courseadmin.import.csv.error.course-id-bad-format",
								new Object[] { line, courseId }) + StringPool.NEW_LINE);
						result = Boolean.FALSE;
					}
					
					if(result){
						
						Course course = null;
						
						try {
							course = CourseLocalServiceUtil.getCourse(courseId);
						
						} catch (NoSuchCourseException e){
							
							_log.error("::No such course exception :: " + e.getMessage());
							errors.add(LanguageUtil.format(themeDisplay.getLocale(),
									"courseadmin.import.csv.error.course-id-not-found",
									new Object[] { line, courseId }) + StringPool.NEW_LINE);
							result = Boolean.FALSE;
							
						} catch (PortalException | SystemException e) {
						
							e.printStackTrace();
							errors.add(LanguageUtil.format(themeDisplay.getLocale(),
									"courseadmin.import.csv.error.course-id-not-found",
									new Object[] { line, courseId }) + StringPool.NEW_LINE);
							result = Boolean.FALSE;
					
						} catch (Exception e){
							
							e.printStackTrace();
							errors.add(LanguageUtil.format(themeDisplay.getLocale(),
									"courseadmin.import.csv.error.course-id-not-found",
									new Object[] { line, courseId }) + StringPool.NEW_LINE);
							result = Boolean.FALSE;
						}
						
						if(result)
							result = unassignUser(userId, course);
							
					}
				}
				
			} else {
				
				_log.error(":: USER IDENTIFICATOR BAD FORMAT :: LINE - " + line);
				
				switch (authType) {
				
					case CompanyConstants.AUTH_TYPE_SN:
						errors.add(LanguageUtil.format(themeDisplay.getLocale(),
								"courseadmin.importuserrole.csvError.user-name-bad-format", line) + StringPool.NEW_LINE);
						break;
		
					case CompanyConstants.AUTH_TYPE_EA:
						errors.add(LanguageUtil.format(themeDisplay.getLocale(),
								"courseadmin.importuserrole.csvError.email-address-bad-format", line) + StringPool.NEW_LINE);
						break;
					
					default:
						errors.add(LanguageUtil.format(themeDisplay.getLocale(),
								"courseadmin.importuserrole.csvError.user-id-bad-format", line) + StringPool.NEW_LINE);
						break;
				}
			}
		}
		
		return result;
	}
	
	private boolean unassignUser(long userId, Course course){
		
		boolean result = Boolean.TRUE;
		
		long courseId = course.getCourseId();
		long courseGroupId = course.getGroupCreatedId();
		
		try {
			
			if(UserLocalServiceUtil.hasGroupUser(courseGroupId, userId)){
			
				if(roleId != siteMemberRoleId) {
					
					List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(userId, courseGroupId);
					
					if(userGroupRoles.isEmpty() || userGroupRoles.size() == 1 || 
							(userGroupRoles.size()== 2 && (siteMemberRoleId==userGroupRoles.get(0).getRoleId() ||
							siteMemberRoleId == userGroupRoles.get(1).getRoleId()))){
						
						GroupLocalServiceUtil.unsetUserGroups(userId,
								new long[] { courseGroupId });
					}
					
					UserGroupRoleLocalServiceUtil.deleteUserGroupRoles(
							userId, courseGroupId, new long[]{roleId, siteOwnerRoleId});
				
				} else {
					
					GroupLocalServiceUtil.unsetUserGroups(userId,
							new long[] { courseGroupId });
				}
				
				//Se audita
				if(roleId == teacherRoleId){
					AuditingLogFactory.audit(course.getCompanyId(), courseGroupId, Course.class.getName(), 
							courseId, userId, AuditConstants.UNREGISTER, "COURSE_TUTOR_REMOVE");
				}
				if(roleId == editorRoleId){
					AuditingLogFactory.audit(course.getCompanyId(), courseGroupId, Course.class.getName(), 
							courseId, userId, AuditConstants.UNREGISTER, "COURSE_EDITOR_REMOVE");
				}
			} else {
				_log.error("::User " + userId + " is not in course " + courseId);
				errors.add(LanguageUtil.format(themeDisplay.getLocale(),
						"courseadmin.import.csv.unassign-users.error.user-is-not-in-this-course",
						new Object[] { line, userId, courseId }) + StringPool.NEW_LINE);
				result = Boolean.FALSE;
			}
		
		} catch (SystemException e) {
			e.printStackTrace();
			result = Boolean.FALSE;
		}
		
		return result;
	}
	
	private static Log _log = LogFactoryUtil.getLog(ImportCsvUnassignUsersThread.class);

}
