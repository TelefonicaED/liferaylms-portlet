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
		getLogoUrl: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "LearningActivity",
	{
		getLearningActivitiesOfGroup: true,
		getLearningActivitiesOfModule: true,
		deleteLearningactivity: true,
		getLearningActivity: true,
		addLearningActivity: true,
		modLearningActivity: true,
		isLocked: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "LearningActivityResult",
	{
		getByActId: true,
		getByActIdAndUser: true,
		userPassed: true,
		userLoginPassed: true,
		update: true,
		updateFinishTry: true
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
		isUserPassed: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "SCORMContent",
	{
		getSCORMContentOfGroup: true
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