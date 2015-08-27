/**
 * Copyright (c)2013 Telefonica Learning Services. All rights reserved.
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

import java.util.Date;

import com.liferay.lms.model.Module;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;

/**
 * The extended model implementation for the LearningActivity service. Represents a row in the &quot;Lms_LearningActivity&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.model.LearningActivity} interface.
 * </p>
 *
 * @author TLS
 */
public class LearningActivityImpl extends LearningActivityBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a learning activity model instance should use the {@link com.liferay.lms.model.LearningActivity} interface instead.
	 */

	@Override
	public Date getStartdate() {
		Date startDate = super.getStartdate();
		if(Validator.isNull(startDate)) {
			try {
				Module module = ModuleLocalServiceUtil.fetchModule(getModuleId());
				if(Validator.isNotNull(module)) {
					return module.getStartDate();
				}
			}
			catch(SystemException systemException) {}

			return null;
		}
		else {
			return startDate;
		}
	}

	@Override
	public boolean isNullStartDate() {
		try {
			if(super.getStartdate()==null){
				return true;
			}
			
			Module module = ModuleLocalServiceUtil.fetchModule(getModuleId());
			if(Validator.isNotNull(module)) {
				return module.getStartDate().equals(super.getStartdate());
			}else{
				return true;
			}
		}
		catch(SystemException systemException) {
			return true;
		}
	}

	@Override
	public Date getEnddate() {
		Date endDate = super.getEnddate();
		if(Validator.isNull(endDate)) {
			try {			
				Module module = ModuleLocalServiceUtil.fetchModule(getModuleId());
				if(Validator.isNotNull(module)) {
					return module.getEndDate();
				}
			}
			catch(SystemException systemException) {}

			return null;
		}
		else {
			return endDate;
		}
	}

	@Override
	public boolean isNullEndDate() {
		try {
			if(super.getEnddate()==null){
				return true;
			}
			
			Module module = ModuleLocalServiceUtil.fetchModule(getModuleId());
			if(Validator.isNotNull(module)) {
				return module.getEndDate().equals(super.getEnddate());
			}else{
				return true;
			}
		}
		catch(SystemException systemException) {
			return true;
		}
	}

}