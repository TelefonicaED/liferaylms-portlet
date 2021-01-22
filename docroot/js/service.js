Liferay.Service.register("Liferay.Service.Lms", "com.liferay.lms.service", "liferaylms-portlet");

Liferay.Service.registerClass(
	Liferay.Service.Lms, "Competence",
	{
		getCompetencesOfGroup: true,
		getCountCompetencesOfGroup: true,
		getCompetencesOfGroups: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "Course",
	{
		getCoursesOfGroup: true,
		createCourse: true,
		getCourses: true,
		getCourseStudents: true,
		getCourseTeachers: true,
		getCourseEditors: true,
		addStudentToCourse: true,
		addStudentToCourseWithDates: true,
		editUserInscriptionDates: true,
		addTeacherToCourse: true,
		addEditorToCourse: true,
		removeStudentFromCourse: true,
		removeTeacherFromCourse: true,
		removeEditorFromCourse: true,
		getUserResult: true,
		myCourses: true,
		addUser: true,
		updateUser: true,
		existsCourseName: true,
		getLogoUrl: true,
		getCoursesParents: true,
		getChildCourses: true,
		getStudentsFromCourseCount: true,
		getPublicCoursesByCompanyId: true,
		getPlidActivityViewer: true,
		getFriendlyURLTeachers: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "LearningActivity",
	{
		countLearningActivityMandatory: true,
		countLearningActivity: true,
		countLearningActivityMandatoryPassed: true,
		countLearningActivityPassed: true,
		getLearningActivitiesOfGroup: true,
		getLearningActivitiesOfModule: true,
		deleteLearningactivity: true,
		getLearningActivity: true,
		addLearningActivity: true,
		modLearningActivity: true,
		isLocked: true,
		getURLActivity: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "LearningActivityResult",
	{
		getByActId: true,
		getByActIdAndUser: true,
		userPassed: true,
		userLoginPassed: true,
		finishResult: true,
		update: true,
		forceFinishTry: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "LearningActivityTry",
	{
		createLearningActivityTry: true,
		getLearningActivityTries: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "Module",
	{
		findAllInGroup: true,
		findAllInCourse: true,
		isLocked: true,
		PassedByMe: true,
		isUserPassed: true,
		getURLModule: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "TestAnswer",
	{
		getTestAnswersByQuestionId: true,
		getTestAnswer: true,
		modTestAnswer: true,
		addTestAnswer: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "TestQuestion",
	{
		addQuestion: true,
		getQuestions: true,
		getQuestion: true
	}
);