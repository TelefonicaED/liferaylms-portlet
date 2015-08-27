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

import com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class P2pActivityCorrectionsLocalServiceClpInvoker {
	public P2pActivityCorrectionsLocalServiceClpInvoker() {
		_methodName0 = "addP2pActivityCorrections";

		_methodParameterTypes0 = new String[] {
				"com.liferay.lms.model.P2pActivityCorrections"
			};

		_methodName1 = "createP2pActivityCorrections";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteP2pActivityCorrections";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteP2pActivityCorrections";

		_methodParameterTypes3 = new String[] {
				"com.liferay.lms.model.P2pActivityCorrections"
			};

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

		_methodName9 = "fetchP2pActivityCorrections";

		_methodParameterTypes9 = new String[] { "long" };

		_methodName10 = "getP2pActivityCorrections";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getPersistedModel";

		_methodParameterTypes11 = new String[] { "java.io.Serializable" };

		_methodName12 = "getP2pActivityCorrectionses";

		_methodParameterTypes12 = new String[] { "int", "int" };

		_methodName13 = "getP2pActivityCorrectionsesCount";

		_methodParameterTypes13 = new String[] {  };

		_methodName14 = "updateP2pActivityCorrections";

		_methodParameterTypes14 = new String[] {
				"com.liferay.lms.model.P2pActivityCorrections"
			};

		_methodName15 = "updateP2pActivityCorrections";

		_methodParameterTypes15 = new String[] {
				"com.liferay.lms.model.P2pActivityCorrections", "boolean"
			};

		_methodName134 = "getBeanIdentifier";

		_methodParameterTypes134 = new String[] {  };

		_methodName135 = "setBeanIdentifier";

		_methodParameterTypes135 = new String[] { "java.lang.String" };

		_methodName140 = "findByP2pActivityIdAndUserId";

		_methodParameterTypes140 = new String[] {
				"java.lang.Long", "java.lang.Long"
			};

		_methodName141 = "exitsCorP2p";

		_methodParameterTypes141 = new String[] {
				"java.lang.Long", "java.lang.Long"
			};

		_methodName142 = "findByP2pActivityId";

		_methodParameterTypes142 = new String[] { "java.lang.Long" };

		_methodName143 = "findByActIdIdAndUserId";

		_methodParameterTypes143 = new String[] {
				"java.lang.Long", "java.lang.Long"
			};

		_methodName144 = "findByActIdAndUserIdOrderByDate";

		_methodParameterTypes144 = new String[] {
				"java.lang.Long", "java.lang.Long"
			};

		_methodName145 = "findByActIdAndUserIdOrderById";

		_methodParameterTypes145 = new String[] {
				"java.lang.Long", "java.lang.Long"
			};

		_methodName146 = "addorUpdateP2pActivityCorrections";

		_methodParameterTypes146 = new String[] {
				"com.liferay.lms.model.P2pActivityCorrections"
			};

		_methodName147 = "asignP2PCorrectionsToUsers";

		_methodParameterTypes147 = new String[] { "long", "long", "java.util.List" };

		_methodName148 = "asignCorrectionsToP2PActivities";

		_methodParameterTypes148 = new String[] {
				"long", "long", "int", "java.util.List", "long"
			};

		_methodName149 = "getNumCorrectionsAsignToP2P";

		_methodParameterTypes149 = new String[] { "long" };

		_methodName150 = "getNumCorrectionsAsignToUser";

		_methodParameterTypes150 = new String[] { "long", "long" };

		_methodName151 = "getCorrectionsDoneByUserInP2PActivity";

		_methodParameterTypes151 = new String[] { "long", "long" };

		_methodName152 = "areAllCorrectionsDoneByUserInP2PActivity";

		_methodParameterTypes152 = new String[] { "long", "long" };

		_methodName153 = "getCorrectionByP2PActivity";

		_methodParameterTypes153 = new String[] { "long" };

		_methodName154 = "getAVGCorrectionsResults";

		_methodParameterTypes154 = new String[] { "long" };

		_methodName155 = "isP2PAsignationDone";

		_methodParameterTypes155 = new String[] { "long", "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.addP2pActivityCorrections((com.liferay.lms.model.P2pActivityCorrections)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.createP2pActivityCorrections(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.deleteP2pActivityCorrections(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.deleteP2pActivityCorrections((com.liferay.lms.model.P2pActivityCorrections)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.fetchP2pActivityCorrections(((Long)arguments[0]).longValue());
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getP2pActivityCorrections(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getP2pActivityCorrectionses(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getP2pActivityCorrectionsesCount();
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.updateP2pActivityCorrections((com.liferay.lms.model.P2pActivityCorrections)arguments[0]);
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.updateP2pActivityCorrections((com.liferay.lms.model.P2pActivityCorrections)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName134.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes134, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName135.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes135, parameterTypes)) {
			P2pActivityCorrectionsLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName140.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes140, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.findByP2pActivityIdAndUserId((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1]);
		}

		if (_methodName141.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes141, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.exitsCorP2p((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1]);
		}

		if (_methodName142.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes142, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.findByP2pActivityId((java.lang.Long)arguments[0]);
		}

		if (_methodName143.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes143, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.findByActIdIdAndUserId((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1]);
		}

		if (_methodName144.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes144, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.findByActIdAndUserIdOrderByDate((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1]);
		}

		if (_methodName145.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes145, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.findByActIdAndUserIdOrderById((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1]);
		}

		if (_methodName146.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes146, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.addorUpdateP2pActivityCorrections((com.liferay.lms.model.P2pActivityCorrections)arguments[0]);
		}

		if (_methodName147.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes147, parameterTypes)) {
			P2pActivityCorrectionsLocalServiceUtil.asignP2PCorrectionsToUsers(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(java.util.List<com.liferay.portal.model.User>)arguments[2]);

			return null;
		}

		if (_methodName148.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes148, parameterTypes)) {
			P2pActivityCorrectionsLocalServiceUtil.asignCorrectionsToP2PActivities(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Integer)arguments[2]).intValue(),
				(java.util.List<com.liferay.lms.model.P2pActivity>)arguments[3],
				((Long)arguments[4]).longValue());

			return null;
		}

		if (_methodName149.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes149, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getNumCorrectionsAsignToP2P(((Long)arguments[0]).longValue());
		}

		if (_methodName150.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes150, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getNumCorrectionsAsignToUser(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName151.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes151, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getCorrectionsDoneByUserInP2PActivity(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName152.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes152, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.areAllCorrectionsDoneByUserInP2PActivity(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName153.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes153, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getCorrectionByP2PActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName154.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes154, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getAVGCorrectionsResults(((Long)arguments[0]).longValue());
		}

		if (_methodName155.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes155, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.isP2PAsignationDone(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
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
	private String _methodName134;
	private String[] _methodParameterTypes134;
	private String _methodName135;
	private String[] _methodParameterTypes135;
	private String _methodName140;
	private String[] _methodParameterTypes140;
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
	private String _methodName152;
	private String[] _methodParameterTypes152;
	private String _methodName153;
	private String[] _methodParameterTypes153;
	private String _methodName154;
	private String[] _methodParameterTypes154;
	private String _methodName155;
	private String[] _methodParameterTypes155;
}