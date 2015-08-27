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

package com.liferay.lms.service.base;

import com.liferay.lms.service.CompetenceLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class CompetenceLocalServiceClpInvoker {
	public CompetenceLocalServiceClpInvoker() {
		_methodName0 = "addCompetence";

		_methodParameterTypes0 = new String[] { "com.liferay.lms.model.Competence" };

		_methodName1 = "createCompetence";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteCompetence";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteCompetence";

		_methodParameterTypes3 = new String[] { "com.liferay.lms.model.Competence" };

		_methodName4 = "dynamicQuery";

		_methodParameterTypes4 = new String[] {  };

		_methodName5 = "dynamicQuery";

		_methodParameterTypes5 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery"
			};

		_methodName6 = "dynamicQuery";

		_methodParameterTypes6 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery", "int", "int"
			};

		_methodName7 = "dynamicQuery";

		_methodParameterTypes7 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery", "int", "int",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName8 = "dynamicQueryCount";

		_methodParameterTypes8 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery"
			};

		_methodName9 = "fetchCompetence";

		_methodParameterTypes9 = new String[] { "long" };

		_methodName10 = "getCompetence";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getPersistedModel";

		_methodParameterTypes11 = new String[] { "java.io.Serializable" };

		_methodName12 = "getCompetenceByUuidAndGroupId";

		_methodParameterTypes12 = new String[] { "java.lang.String", "long" };

		_methodName13 = "getCompetences";

		_methodParameterTypes13 = new String[] { "int", "int" };

		_methodName14 = "getCompetencesCount";

		_methodParameterTypes14 = new String[] {  };

		_methodName15 = "updateCompetence";

		_methodParameterTypes15 = new String[] {
				"com.liferay.lms.model.Competence"
			};

		_methodName16 = "updateCompetence";

		_methodParameterTypes16 = new String[] {
				"com.liferay.lms.model.Competence", "boolean"
			};

		_methodName135 = "getBeanIdentifier";

		_methodParameterTypes135 = new String[] {  };

		_methodName136 = "setBeanIdentifier";

		_methodParameterTypes136 = new String[] { "java.lang.String" };

		_methodName141 = "addCompetence";

		_methodParameterTypes141 = new String[] {
				"java.lang.String", "java.lang.String",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName142 = "addCompetence";

		_methodParameterTypes142 = new String[] {
				"java.lang.String", "java.lang.String", "boolean",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName143 = "updateCompetence";

		_methodParameterTypes143 = new String[] {
				"com.liferay.lms.model.Competence",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName144 = "modCompetence";

		_methodParameterTypes144 = new String[] {
				"com.liferay.lms.model.Competence",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName145 = "deleteCompetence";

		_methodParameterTypes145 = new String[] { "long" };

		_methodName146 = "deleteCompetence";

		_methodParameterTypes146 = new String[] {
				"com.liferay.lms.model.Competence"
			};

		_methodName147 = "getBGImageURL";

		_methodParameterTypes147 = new String[] {
				"long", "javax.servlet.http.HttpServletRequest"
			};

		_methodName148 = "getBGImageURL";

		_methodParameterTypes148 = new String[] {
				"com.liferay.lms.model.Competence",
				"javax.servlet.http.HttpServletRequest"
			};

		_methodName149 = "setBGImage";

		_methodParameterTypes149 = new String[] {
				"byte[][]", "long", "java.lang.String"
			};

		_methodName150 = "countAll";

		_methodParameterTypes150 = new String[] {  };

		_methodName151 = "findByCompanyId";

		_methodParameterTypes151 = new String[] { "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return CompetenceLocalServiceUtil.addCompetence((com.liferay.lms.model.Competence)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return CompetenceLocalServiceUtil.createCompetence(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return CompetenceLocalServiceUtil.deleteCompetence(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return CompetenceLocalServiceUtil.deleteCompetence((com.liferay.lms.model.Competence)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return CompetenceLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return CompetenceLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return CompetenceLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return CompetenceLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return CompetenceLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return CompetenceLocalServiceUtil.fetchCompetence(((Long)arguments[0]).longValue());
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return CompetenceLocalServiceUtil.getCompetence(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return CompetenceLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return CompetenceLocalServiceUtil.getCompetenceByUuidAndGroupId((java.lang.String)arguments[0],
				((Long)arguments[1]).longValue());
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return CompetenceLocalServiceUtil.getCompetences(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return CompetenceLocalServiceUtil.getCompetencesCount();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return CompetenceLocalServiceUtil.updateCompetence((com.liferay.lms.model.Competence)arguments[0]);
		}

		if (_methodName16.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes16, parameterTypes)) {
			return CompetenceLocalServiceUtil.updateCompetence((com.liferay.lms.model.Competence)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName135.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes135, parameterTypes)) {
			return CompetenceLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName136.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes136, parameterTypes)) {
			CompetenceLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName141.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes141, parameterTypes)) {
			return CompetenceLocalServiceUtil.addCompetence((java.lang.String)arguments[0],
				(java.lang.String)arguments[1],
				(com.liferay.portal.service.ServiceContext)arguments[2]);
		}

		if (_methodName142.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes142, parameterTypes)) {
			return CompetenceLocalServiceUtil.addCompetence((java.lang.String)arguments[0],
				(java.lang.String)arguments[1],
				((Boolean)arguments[2]).booleanValue(),
				(com.liferay.portal.service.ServiceContext)arguments[3]);
		}

		if (_methodName143.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes143, parameterTypes)) {
			return CompetenceLocalServiceUtil.updateCompetence((com.liferay.lms.model.Competence)arguments[0],
				(com.liferay.portal.service.ServiceContext)arguments[1]);
		}

		if (_methodName144.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes144, parameterTypes)) {
			return CompetenceLocalServiceUtil.modCompetence((com.liferay.lms.model.Competence)arguments[0],
				(com.liferay.portal.service.ServiceContext)arguments[1]);
		}

		if (_methodName145.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes145, parameterTypes)) {
			return CompetenceLocalServiceUtil.deleteCompetence(((Long)arguments[0]).longValue());
		}

		if (_methodName146.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes146, parameterTypes)) {
			return CompetenceLocalServiceUtil.deleteCompetence((com.liferay.lms.model.Competence)arguments[0]);
		}

		if (_methodName147.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes147, parameterTypes)) {
			return CompetenceLocalServiceUtil.getBGImageURL(((Long)arguments[0]).longValue(),
				(javax.servlet.http.HttpServletRequest)arguments[1]);
		}

		if (_methodName148.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes148, parameterTypes)) {
			return CompetenceLocalServiceUtil.getBGImageURL((com.liferay.lms.model.Competence)arguments[0],
				(javax.servlet.http.HttpServletRequest)arguments[1]);
		}

		if (_methodName149.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes149, parameterTypes)) {
			CompetenceLocalServiceUtil.setBGImage((byte[])arguments[0],
				((Long)arguments[1]).longValue(), (java.lang.String)arguments[2]);

			return null;
		}

		if (_methodName150.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes150, parameterTypes)) {
			return CompetenceLocalServiceUtil.countAll();
		}

		if (_methodName151.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes151, parameterTypes)) {
			return CompetenceLocalServiceUtil.findByCompanyId(((Long)arguments[0]).longValue());
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName0;
	private String[] _methodParameterTypes0;
	private String _methodName1;
	private String[] _methodParameterTypes1;
	private String _methodName2;
	private String[] _methodParameterTypes2;
	private String _methodName3;
	private String[] _methodParameterTypes3;
	private String _methodName4;
	private String[] _methodParameterTypes4;
	private String _methodName5;
	private String[] _methodParameterTypes5;
	private String _methodName6;
	private String[] _methodParameterTypes6;
	private String _methodName7;
	private String[] _methodParameterTypes7;
	private String _methodName8;
	private String[] _methodParameterTypes8;
	private String _methodName9;
	private String[] _methodParameterTypes9;
	private String _methodName10;
	private String[] _methodParameterTypes10;
	private String _methodName11;
	private String[] _methodParameterTypes11;
	private String _methodName12;
	private String[] _methodParameterTypes12;
	private String _methodName13;
	private String[] _methodParameterTypes13;
	private String _methodName14;
	private String[] _methodParameterTypes14;
	private String _methodName15;
	private String[] _methodParameterTypes15;
	private String _methodName16;
	private String[] _methodParameterTypes16;
	private String _methodName135;
	private String[] _methodParameterTypes135;
	private String _methodName136;
	private String[] _methodParameterTypes136;
	private String _methodName141;
	private String[] _methodParameterTypes141;
	private String _methodName142;
	private String[] _methodParameterTypes142;
	private String _methodName143;
	private String[] _methodParameterTypes143;
	private String _methodName144;
	private String[] _methodParameterTypes144;
	private String _methodName145;
	private String[] _methodParameterTypes145;
	private String _methodName146;
	private String[] _methodParameterTypes146;
	private String _methodName147;
	private String[] _methodParameterTypes147;
	private String _methodName148;
	private String[] _methodParameterTypes148;
	private String _methodName149;
	private String[] _methodParameterTypes149;
	private String _methodName150;
	private String[] _methodParameterTypes150;
	private String _methodName151;
	private String[] _methodParameterTypes151;
}