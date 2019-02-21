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

package com.liferay.lms.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Team;
import com.liferay.portal.service.TeamLocalService;
import com.liferay.portal.service.TeamLocalServiceUtil;

/**
 * The extended model implementation for the Schedule service. Represents a row in the &quot;Lms_Schedule&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.model.Schedule} interface.
 * </p>
 *
 * @author TLS
 */
public class ScheduleImpl extends ScheduleBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a schedule model instance should use the {@link com.liferay.lms.model.Schedule} interface instead.
	 */
	public ScheduleImpl() {
	}
	
	public Team getTeam(){
		try {
			return TeamLocalServiceUtil.getTeam(getTeamId());
		} catch (PortalException | SystemException e) {
			e.printStackTrace();
			return null;
		}
	}
}