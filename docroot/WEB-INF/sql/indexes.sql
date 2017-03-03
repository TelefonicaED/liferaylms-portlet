create index IX_BD5C2361 on Lms_ActivityTriesDeleted (actId, status);
create index IX_9BB02FE8 on Lms_ActivityTriesDeleted (groupId);

create index IX_6E7EC4FA on Lms_AuditEntry (classname, classPK);
create index IX_A9ACD23E on Lms_AuditEntry (companyId);
create index IX_6820D040 on Lms_AuditEntry (groupId);
create index IX_301D31E4 on Lms_AuditEntry (userId);
create index IX_E3182346 on Lms_AuditEntry (userId, groupId);
create index IX_1E118FED on Lms_AuditEntry (userId, groupId, classname);

create index IX_A9A7E97F on Lms_CheckP2pMailing (actId);

create index IX_DEF4F10C on Lms_Competence (companyId);
create index IX_56DE4A8E on Lms_Competence (groupId);
create index IX_EA812898 on Lms_Competence (uuid_);
create unique index IX_7E6C7712 on Lms_Competence (uuid_, groupId);

create index IX_AABFFF7A on Lms_Course (companyId);
create index IX_C9B5BF5A on Lms_Course (companyId, closed);
create unique index IX_243A72BA on Lms_Course (companyId, friendlyURL);
create index IX_F944A64E on Lms_Course (companyId, parentCourseId);
create index IX_B852F6C6 on Lms_Course (groupCreatedId);
create index IX_E1561C7C on Lms_Course (groupId);
create index IX_F367BA5C on Lms_Course (groupId, closed);
create index IX_23547F50 on Lms_Course (groupId, parentCourseId);
create index IX_999B22C2 on Lms_Course (parentCourseId);
create index IX_D92F6E28 on Lms_Course (userId);
create index IX_684B7382 on Lms_Course (userId, groupId);
create index IX_97009E06 on Lms_Course (uuid_);
create unique index IX_F90F3D64 on Lms_Course (uuid_, groupId);

create unique index IX_4AF8B71 on Lms_CourseCompetence (courseId, competenceId, condition_);
create index IX_17104179 on Lms_CourseCompetence (courseId, condition_);
create index IX_F3F3D23D on Lms_CourseCompetence (uuid_);

create index IX_8C107135 on Lms_CourseResult (courseId);
create index IX_E5606419 on Lms_CourseResult (courseId, passed);
create index IX_BF5FDCB on Lms_CourseResult (courseId, passed, passedDate);
create index IX_A4AA5B05 on Lms_CourseResult (courseId, passed, passedDate, userId);
create index IX_E5289353 on Lms_CourseResult (courseId, passed, userId);
create index IX_E84F40E7 on Lms_CourseResult (courseId, passedDate);
create index IX_388CC221 on Lms_CourseResult (courseId, passedDate, userId);
create index IX_B4BB8BC5 on Lms_CourseResult (courseId, startDate, userId);
create index IX_8CF7C46F on Lms_CourseResult (courseId, userId);
create index IX_583EFC25 on Lms_CourseResult (userId);
create unique index IX_3F29EDEF on Lms_CourseResult (userId, courseId);

create index IX_A674914A on Lms_LearningActivity (groupId);
create index IX_A26B5373 on Lms_LearningActivity (groupId, typeId);
create index IX_D6755EC7 on Lms_LearningActivity (groupId, weightinmodule);
create index IX_A907C93B on Lms_LearningActivity (moduleId);
create index IX_CA9C7713 on Lms_LearningActivity (moduleId, priority);
create index IX_C1A494B8 on Lms_LearningActivity (moduleId, weightinmodule);
create index IX_7D8395E on Lms_LearningActivity (precedence);
create index IX_A331EE54 on Lms_LearningActivity (uuid_);
create unique index IX_75B864D6 on Lms_LearningActivity (uuid_, groupId);

create index IX_4AEF2D40 on Lms_LearningActivityResult (actId);
create index IX_86B3E524 on Lms_LearningActivityResult (actId, passed);
create index IX_9F67B375 on Lms_LearningActivityResult (actId, passed, endDate);
create index IX_5E5BD95E on Lms_LearningActivityResult (actId, passed, userId);
create index IX_2E4B457A on Lms_LearningActivityResult (actId, userId);
create index IX_678F5817 on Lms_LearningActivityResult (userId);
create unique index IX_8F7CCC6 on Lms_LearningActivityResult (userId, actId);
create index IX_8B143A37 on Lms_LearningActivityResult (uuid_);

create index IX_6C40B616 on Lms_LearningActivityTry (actId);
create index IX_BAC99850 on Lms_LearningActivityTry (actId, userId);
create index IX_706EEA01 on Lms_LearningActivityTry (userId);
create index IX_AC65C30D on Lms_LearningActivityTry (uuid_);

create index IX_306AE98B on Lms_Module (groupId);
create index IX_3ED54339 on Lms_Module (userId);
create index IX_E045C191 on Lms_Module (userId, groupId);
create index IX_47B362D5 on Lms_Module (uuid_);
create unique index IX_B29B7175 on Lms_Module (uuid_, groupId);

create index IX_7D47DB17 on Lms_ModuleResult (moduleId);
create index IX_A588CBFB on Lms_ModuleResult (moduleId, passed);
create index IX_1767A2AD on Lms_ModuleResult (moduleId, passed, passedDate);
create index IX_6AA1FDE7 on Lms_ModuleResult (moduleId, passed, passedDate, userId);
create index IX_6A63F935 on Lms_ModuleResult (moduleId, passed, userId);
create index IX_C5CE7C9 on Lms_ModuleResult (moduleId, passedDate);
create index IX_43FE6703 on Lms_ModuleResult (moduleId, passedDate, userId);
create index IX_4D202C51 on Lms_ModuleResult (moduleId, userId);
create index IX_40BB0076 on Lms_ModuleResult (userId);
create unique index IX_A378B6D1 on Lms_ModuleResult (userId, moduleId);

create index IX_A70D5A07 on Lms_P2pActivity (actId);
create index IX_90AFBB41 on Lms_P2pActivity (actId, userId);
create index IX_8F36C430 on Lms_P2pActivity (userId);
create index IX_E73266FE on Lms_P2pActivity (uuid_);

create index IX_70AAED8C on Lms_P2pActivityCorrections (actId, userId);
create index IX_599E425D on Lms_P2pActivityCorrections (p2pActivityId);
create index IX_85BFE7C0 on Lms_P2pActivityCorrections (p2pActivityId, actId);
create index IX_86D5ED97 on Lms_P2pActivityCorrections (p2pActivityId, userId);
create index IX_C0C0C045 on Lms_P2pActivityCorrections (userId);
create index IX_331E1449 on Lms_P2pActivityCorrections (uuid_);

create index IX_7B067356 on Lms_SCORMContent (companyId);
create index IX_7D1FD758 on Lms_SCORMContent (groupId);
create index IX_5A14E7CC on Lms_SCORMContent (userId);
create index IX_4E8A925E on Lms_SCORMContent (userId, groupId);
create index IX_C4735FE2 on Lms_SCORMContent (uuid_);
create unique index IX_1942D308 on Lms_SCORMContent (uuid_, groupId);

create index IX_3AA8B3F6 on Lms_Schedule (teamId);

create index IX_ED91C253 on Lms_SurveyResult (actId);
create index IX_2F35042C on Lms_SurveyResult (answerId, questionId);
create index IX_8B8399FF on Lms_SurveyResult (questionId);
create index IX_9AF915DE on Lms_SurveyResult (questionId, actId);
create index IX_193F6564 on Lms_SurveyResult (userId);
create index IX_2DB6CF4A on Lms_SurveyResult (uuid_);

create index IX_31C145A on Lms_TestAnswer (actId);
create index IX_558E7458 on Lms_TestAnswer (questionId);
create index IX_43412151 on Lms_TestAnswer (uuid_);

create index IX_8D95C0F2 on Lms_TestQuestion (actId);
create index IX_CDBACDE9 on Lms_TestQuestion (uuid_);

create index IX_EB2113C1 on Lms_UserCompetence (userId);
create index IX_B2E889B9 on Lms_UserCompetence (userId, competenceId);
create index IX_E1E7614D on Lms_UserCompetence (uuid_);