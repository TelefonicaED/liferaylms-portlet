/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.lms.service;

import com.liferay.lms.model.AuditEntryClp;
import com.liferay.lms.model.CheckP2pMailingClp;
import com.liferay.lms.model.CompetenceClp;
import com.liferay.lms.model.CourseClp;
import com.liferay.lms.model.CourseCompetenceClp;
import com.liferay.lms.model.CourseResultClp;
import com.liferay.lms.model.LearningActivityClp;
import com.liferay.lms.model.LearningActivityResultClp;
import com.liferay.lms.model.LearningActivityTryClp;
import com.liferay.lms.model.LmsPrefsClp;
import com.liferay.lms.model.ModuleClp;
import com.liferay.lms.model.ModuleResultClp;
import com.liferay.lms.model.P2pActivityClp;
import com.liferay.lms.model.P2pActivityCorrectionsClp;
import com.liferay.lms.model.SCORMContentClp;
import com.liferay.lms.model.SurveyResultClp;
import com.liferay.lms.model.TestAnswerClp;
import com.liferay.lms.model.TestQuestionClp;
import com.liferay.lms.model.UserCompetenceClp;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassLoaderObjectInputStream;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModel;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ClpSerializer {
	public static String getServletContextName() {
		if (Validator.isNotNull(_servletContextName)) {
			return _servletContextName;
		}

		synchronized (ClpSerializer.class) {
			if (Validator.isNotNull(_servletContextName)) {
				return _servletContextName;
			}

			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Class<?> portletPropsClass = classLoader.loadClass(
						"com.liferay.util.portlet.PortletProps");

				Method getMethod = portletPropsClass.getMethod("get",
						new Class<?>[] { String.class });

				String portletPropsServletContextName = (String)getMethod.invoke(null,
						"liferaylms-portlet-deployment-context");

				if (Validator.isNotNull(portletPropsServletContextName)) {
					_servletContextName = portletPropsServletContextName;
				}
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Unable to locate deployment context from portlet properties");
				}
			}

			if (Validator.isNull(_servletContextName)) {
				try {
					String propsUtilServletContextName = PropsUtil.get(
							"liferaylms-portlet-deployment-context");

					if (Validator.isNotNull(propsUtilServletContextName)) {
						_servletContextName = propsUtilServletContextName;
					}
				}
				catch (Throwable t) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Unable to locate deployment context from portal properties");
					}
				}
			}

			if (Validator.isNull(_servletContextName)) {
				_servletContextName = "liferaylms-portlet";
			}

			return _servletContextName;
		}
	}

	public static Object translateInput(BaseModel<?> oldModel) {
		Class<?> oldModelClass = oldModel.getClass();

		String oldModelClassName = oldModelClass.getName();

		if (oldModelClassName.equals(AuditEntryClp.class.getName())) {
			return translateInputAuditEntry(oldModel);
		}

		if (oldModelClassName.equals(CheckP2pMailingClp.class.getName())) {
			return translateInputCheckP2pMailing(oldModel);
		}

		if (oldModelClassName.equals(CompetenceClp.class.getName())) {
			return translateInputCompetence(oldModel);
		}

		if (oldModelClassName.equals(CourseClp.class.getName())) {
			return translateInputCourse(oldModel);
		}

		if (oldModelClassName.equals(CourseCompetenceClp.class.getName())) {
			return translateInputCourseCompetence(oldModel);
		}

		if (oldModelClassName.equals(CourseResultClp.class.getName())) {
			return translateInputCourseResult(oldModel);
		}

		if (oldModelClassName.equals(LearningActivityClp.class.getName())) {
			return translateInputLearningActivity(oldModel);
		}

		if (oldModelClassName.equals(LearningActivityResultClp.class.getName())) {
			return translateInputLearningActivityResult(oldModel);
		}

		if (oldModelClassName.equals(LearningActivityTryClp.class.getName())) {
			return translateInputLearningActivityTry(oldModel);
		}

		if (oldModelClassName.equals(LmsPrefsClp.class.getName())) {
			return translateInputLmsPrefs(oldModel);
		}

		if (oldModelClassName.equals(ModuleClp.class.getName())) {
			return translateInputModule(oldModel);
		}

		if (oldModelClassName.equals(ModuleResultClp.class.getName())) {
			return translateInputModuleResult(oldModel);
		}

		if (oldModelClassName.equals(P2pActivityClp.class.getName())) {
			return translateInputP2pActivity(oldModel);
		}

		if (oldModelClassName.equals(P2pActivityCorrectionsClp.class.getName())) {
			return translateInputP2pActivityCorrections(oldModel);
		}

		if (oldModelClassName.equals(SCORMContentClp.class.getName())) {
			return translateInputSCORMContent(oldModel);
		}

		if (oldModelClassName.equals(SurveyResultClp.class.getName())) {
			return translateInputSurveyResult(oldModel);
		}

		if (oldModelClassName.equals(TestAnswerClp.class.getName())) {
			return translateInputTestAnswer(oldModel);
		}

		if (oldModelClassName.equals(TestQuestionClp.class.getName())) {
			return translateInputTestQuestion(oldModel);
		}

		if (oldModelClassName.equals(UserCompetenceClp.class.getName())) {
			return translateInputUserCompetence(oldModel);
		}

		return oldModel;
	}

	public static Object translateInput(List<Object> oldList) {
		List<Object> newList = new ArrayList<Object>(oldList.size());

		for (int i = 0; i < oldList.size(); i++) {
			Object curObj = oldList.get(i);

			newList.add(translateInput(curObj));
		}

		return newList;
	}

	public static Object translateInputAuditEntry(BaseModel<?> oldModel) {
		AuditEntryClp oldClpModel = (AuditEntryClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getAuditEntryRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputCheckP2pMailing(BaseModel<?> oldModel) {
		CheckP2pMailingClp oldClpModel = (CheckP2pMailingClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getCheckP2pMailingRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputCompetence(BaseModel<?> oldModel) {
		CompetenceClp oldClpModel = (CompetenceClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getCompetenceRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputCourse(BaseModel<?> oldModel) {
		CourseClp oldClpModel = (CourseClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getCourseRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputCourseCompetence(BaseModel<?> oldModel) {
		CourseCompetenceClp oldClpModel = (CourseCompetenceClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getCourseCompetenceRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputCourseResult(BaseModel<?> oldModel) {
		CourseResultClp oldClpModel = (CourseResultClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getCourseResultRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputLearningActivity(BaseModel<?> oldModel) {
		LearningActivityClp oldClpModel = (LearningActivityClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getLearningActivityRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputLearningActivityResult(
		BaseModel<?> oldModel) {
		LearningActivityResultClp oldClpModel = (LearningActivityResultClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getLearningActivityResultRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputLearningActivityTry(
		BaseModel<?> oldModel) {
		LearningActivityTryClp oldClpModel = (LearningActivityTryClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getLearningActivityTryRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputLmsPrefs(BaseModel<?> oldModel) {
		LmsPrefsClp oldClpModel = (LmsPrefsClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getLmsPrefsRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputModule(BaseModel<?> oldModel) {
		ModuleClp oldClpModel = (ModuleClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getModuleRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputModuleResult(BaseModel<?> oldModel) {
		ModuleResultClp oldClpModel = (ModuleResultClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getModuleResultRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputP2pActivity(BaseModel<?> oldModel) {
		P2pActivityClp oldClpModel = (P2pActivityClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getP2pActivityRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputP2pActivityCorrections(
		BaseModel<?> oldModel) {
		P2pActivityCorrectionsClp oldClpModel = (P2pActivityCorrectionsClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getP2pActivityCorrectionsRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputSCORMContent(BaseModel<?> oldModel) {
		SCORMContentClp oldClpModel = (SCORMContentClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getSCORMContentRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputSurveyResult(BaseModel<?> oldModel) {
		SurveyResultClp oldClpModel = (SurveyResultClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getSurveyResultRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputTestAnswer(BaseModel<?> oldModel) {
		TestAnswerClp oldClpModel = (TestAnswerClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getTestAnswerRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputTestQuestion(BaseModel<?> oldModel) {
		TestQuestionClp oldClpModel = (TestQuestionClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getTestQuestionRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputUserCompetence(BaseModel<?> oldModel) {
		UserCompetenceClp oldClpModel = (UserCompetenceClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getUserCompetenceRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInput(Object obj) {
		if (obj instanceof BaseModel<?>) {
			return translateInput((BaseModel<?>)obj);
		}
		else if (obj instanceof List<?>) {
			return translateInput((List<Object>)obj);
		}
		else {
			return obj;
		}
	}

	public static Object translateOutput(BaseModel<?> oldModel) {
		Class<?> oldModelClass = oldModel.getClass();

		String oldModelClassName = oldModelClass.getName();

		if (oldModelClassName.equals(
					"com.liferay.lms.model.impl.AuditEntryImpl")) {
			return translateOutputAuditEntry(oldModel);
		}

		if (oldModelClassName.equals(
					"com.liferay.lms.model.impl.CheckP2pMailingImpl")) {
			return translateOutputCheckP2pMailing(oldModel);
		}

		if (oldModelClassName.equals(
					"com.liferay.lms.model.impl.CompetenceImpl")) {
			return translateOutputCompetence(oldModel);
		}

		if (oldModelClassName.equals("com.liferay.lms.model.impl.CourseImpl")) {
			return translateOutputCourse(oldModel);
		}

		if (oldModelClassName.equals(
					"com.liferay.lms.model.impl.CourseCompetenceImpl")) {
			return translateOutputCourseCompetence(oldModel);
		}

		if (oldModelClassName.equals(
					"com.liferay.lms.model.impl.CourseResultImpl")) {
			return translateOutputCourseResult(oldModel);
		}

		if (oldModelClassName.equals(
					"com.liferay.lms.model.impl.LearningActivityImpl")) {
			return translateOutputLearningActivity(oldModel);
		}

		if (oldModelClassName.equals(
					"com.liferay.lms.model.impl.LearningActivityResultImpl")) {
			return translateOutputLearningActivityResult(oldModel);
		}

		if (oldModelClassName.equals(
					"com.liferay.lms.model.impl.LearningActivityTryImpl")) {
			return translateOutputLearningActivityTry(oldModel);
		}

		if (oldModelClassName.equals("com.liferay.lms.model.impl.LmsPrefsImpl")) {
			return translateOutputLmsPrefs(oldModel);
		}

		if (oldModelClassName.equals("com.liferay.lms.model.impl.ModuleImpl")) {
			return translateOutputModule(oldModel);
		}

		if (oldModelClassName.equals(
					"com.liferay.lms.model.impl.ModuleResultImpl")) {
			return translateOutputModuleResult(oldModel);
		}

		if (oldModelClassName.equals(
					"com.liferay.lms.model.impl.P2pActivityImpl")) {
			return translateOutputP2pActivity(oldModel);
		}

		if (oldModelClassName.equals(
					"com.liferay.lms.model.impl.P2pActivityCorrectionsImpl")) {
			return translateOutputP2pActivityCorrections(oldModel);
		}

		if (oldModelClassName.equals(
					"com.liferay.lms.model.impl.SCORMContentImpl")) {
			return translateOutputSCORMContent(oldModel);
		}

		if (oldModelClassName.equals(
					"com.liferay.lms.model.impl.SurveyResultImpl")) {
			return translateOutputSurveyResult(oldModel);
		}

		if (oldModelClassName.equals(
					"com.liferay.lms.model.impl.TestAnswerImpl")) {
			return translateOutputTestAnswer(oldModel);
		}

		if (oldModelClassName.equals(
					"com.liferay.lms.model.impl.TestQuestionImpl")) {
			return translateOutputTestQuestion(oldModel);
		}

		if (oldModelClassName.equals(
					"com.liferay.lms.model.impl.UserCompetenceImpl")) {
			return translateOutputUserCompetence(oldModel);
		}

		return oldModel;
	}

	public static Object translateOutput(List<Object> oldList) {
		List<Object> newList = new ArrayList<Object>(oldList.size());

		for (int i = 0; i < oldList.size(); i++) {
			Object curObj = oldList.get(i);

			newList.add(translateOutput(curObj));
		}

		return newList;
	}

	public static Object translateOutput(Object obj) {
		if (obj instanceof BaseModel<?>) {
			return translateOutput((BaseModel<?>)obj);
		}
		else if (obj instanceof List<?>) {
			return translateOutput((List<Object>)obj);
		}
		else {
			return obj;
		}
	}

	public static Throwable translateThrowable(Throwable throwable) {
		if (_useReflectionToTranslateThrowable) {
			try {
				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(unsyncByteArrayOutputStream);

				objectOutputStream.writeObject(throwable);

				objectOutputStream.flush();
				objectOutputStream.close();

				UnsyncByteArrayInputStream unsyncByteArrayInputStream = new UnsyncByteArrayInputStream(unsyncByteArrayOutputStream.unsafeGetByteArray(),
						0, unsyncByteArrayOutputStream.size());

				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader = currentThread.getContextClassLoader();

				ObjectInputStream objectInputStream = new ClassLoaderObjectInputStream(unsyncByteArrayInputStream,
						contextClassLoader);

				throwable = (Throwable)objectInputStream.readObject();

				objectInputStream.close();

				return throwable;
			}
			catch (SecurityException se) {
				if (_log.isInfoEnabled()) {
					_log.info("Do not use reflection to translate throwable");
				}

				_useReflectionToTranslateThrowable = false;
			}
			catch (Throwable throwable2) {
				_log.error(throwable2, throwable2);

				return throwable2;
			}
		}

		Class<?> clazz = throwable.getClass();

		String className = clazz.getName();

		if (className.equals(PortalException.class.getName())) {
			return new PortalException();
		}

		if (className.equals(SystemException.class.getName())) {
			return new SystemException();
		}

		if (className.equals("com.liferay.lms.NoSuchModuleException")) {
			return new com.liferay.lms.NoSuchModuleException();
		}

		if (className.equals("com.liferay.lms.NoSuchAuditEntryException")) {
			return new com.liferay.lms.NoSuchAuditEntryException();
		}

		if (className.equals("com.liferay.lms.NoSuchCheckP2pMailingException")) {
			return new com.liferay.lms.NoSuchCheckP2pMailingException();
		}

		if (className.equals("com.liferay.lms.NoSuchCompetenceException")) {
			return new com.liferay.lms.NoSuchCompetenceException();
		}

		if (className.equals("com.liferay.lms.NoSuchCourseException")) {
			return new com.liferay.lms.NoSuchCourseException();
		}

		if (className.equals("com.liferay.lms.NoSuchCourseCompetenceException")) {
			return new com.liferay.lms.NoSuchCourseCompetenceException();
		}

		if (className.equals("com.liferay.lms.NoSuchCourseResultException")) {
			return new com.liferay.lms.NoSuchCourseResultException();
		}

		if (className.equals("com.liferay.lms.NoSuchLearningActivityException")) {
			return new com.liferay.lms.NoSuchLearningActivityException();
		}

		if (className.equals(
					"com.liferay.lms.NoSuchLearningActivityResultException")) {
			return new com.liferay.lms.NoSuchLearningActivityResultException();
		}

		if (className.equals(
					"com.liferay.lms.NoSuchLearningActivityTryException")) {
			return new com.liferay.lms.NoSuchLearningActivityTryException();
		}

		if (className.equals("com.liferay.lms.NoSuchPrefsException")) {
			return new com.liferay.lms.NoSuchPrefsException();
		}

		if (className.equals("com.liferay.lms.NoSuchModuleException")) {
			return new com.liferay.lms.NoSuchModuleException();
		}

		if (className.equals("com.liferay.lms.NoSuchModuleResultException")) {
			return new com.liferay.lms.NoSuchModuleResultException();
		}

		if (className.equals("com.liferay.lms.NoSuchP2pActivityException")) {
			return new com.liferay.lms.NoSuchP2pActivityException();
		}

		if (className.equals(
					"com.liferay.lms.NoSuchP2pActivityCorrectionsException")) {
			return new com.liferay.lms.NoSuchP2pActivityCorrectionsException();
		}

		if (className.equals("com.liferay.lms.NoSuchSCORMContentException")) {
			return new com.liferay.lms.NoSuchSCORMContentException();
		}

		if (className.equals("com.liferay.lms.NoSuchSurveyResultException")) {
			return new com.liferay.lms.NoSuchSurveyResultException();
		}

		if (className.equals("com.liferay.lms.NoSuchTestAnswerException")) {
			return new com.liferay.lms.NoSuchTestAnswerException();
		}

		if (className.equals("com.liferay.lms.NoSuchTestQuestionException")) {
			return new com.liferay.lms.NoSuchTestQuestionException();
		}

		if (className.equals("com.liferay.lms.NoSuchUserCompetenceException")) {
			return new com.liferay.lms.NoSuchUserCompetenceException();
		}

		return throwable;
	}

	public static Object translateOutputAuditEntry(BaseModel<?> oldModel) {
		AuditEntryClp newModel = new AuditEntryClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setAuditEntryRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputCheckP2pMailing(BaseModel<?> oldModel) {
		CheckP2pMailingClp newModel = new CheckP2pMailingClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setCheckP2pMailingRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputCompetence(BaseModel<?> oldModel) {
		CompetenceClp newModel = new CompetenceClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setCompetenceRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputCourse(BaseModel<?> oldModel) {
		CourseClp newModel = new CourseClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setCourseRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputCourseCompetence(BaseModel<?> oldModel) {
		CourseCompetenceClp newModel = new CourseCompetenceClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setCourseCompetenceRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputCourseResult(BaseModel<?> oldModel) {
		CourseResultClp newModel = new CourseResultClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setCourseResultRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputLearningActivity(BaseModel<?> oldModel) {
		LearningActivityClp newModel = new LearningActivityClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setLearningActivityRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputLearningActivityResult(
		BaseModel<?> oldModel) {
		LearningActivityResultClp newModel = new LearningActivityResultClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setLearningActivityResultRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputLearningActivityTry(
		BaseModel<?> oldModel) {
		LearningActivityTryClp newModel = new LearningActivityTryClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setLearningActivityTryRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputLmsPrefs(BaseModel<?> oldModel) {
		LmsPrefsClp newModel = new LmsPrefsClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setLmsPrefsRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputModule(BaseModel<?> oldModel) {
		ModuleClp newModel = new ModuleClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setModuleRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputModuleResult(BaseModel<?> oldModel) {
		ModuleResultClp newModel = new ModuleResultClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setModuleResultRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputP2pActivity(BaseModel<?> oldModel) {
		P2pActivityClp newModel = new P2pActivityClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setP2pActivityRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputP2pActivityCorrections(
		BaseModel<?> oldModel) {
		P2pActivityCorrectionsClp newModel = new P2pActivityCorrectionsClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setP2pActivityCorrectionsRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputSCORMContent(BaseModel<?> oldModel) {
		SCORMContentClp newModel = new SCORMContentClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setSCORMContentRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputSurveyResult(BaseModel<?> oldModel) {
		SurveyResultClp newModel = new SurveyResultClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setSurveyResultRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputTestAnswer(BaseModel<?> oldModel) {
		TestAnswerClp newModel = new TestAnswerClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setTestAnswerRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputTestQuestion(BaseModel<?> oldModel) {
		TestQuestionClp newModel = new TestQuestionClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setTestQuestionRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputUserCompetence(BaseModel<?> oldModel) {
		UserCompetenceClp newModel = new UserCompetenceClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setUserCompetenceRemoteModel(oldModel);

		return newModel;
	}

	private static Log _log = LogFactoryUtil.getLog(ClpSerializer.class);
	private static String _servletContextName;
	private static boolean _useReflectionToTranslateThrowable = true;
}