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

package com.liferay.lms.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.lms.service.http.CompetenceServiceSoap}.
 *
 * @author    TLS
 * @see       com.liferay.lms.service.http.CompetenceServiceSoap
 * @generated
 */
public class CompetenceSoap implements Serializable {
	public static CompetenceSoap toSoapModel(Competence model) {
		CompetenceSoap soapModel = new CompetenceSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCompetenceId(model.getCompetenceId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setUserId(model.getUserId());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setPage(model.getPage());
		soapModel.setGenerateCertificate(model.getGenerateCertificate());
		soapModel.setDiplomaTemplate(model.getDiplomaTemplate());

		return soapModel;
	}

	public static CompetenceSoap[] toSoapModels(Competence[] models) {
		CompetenceSoap[] soapModels = new CompetenceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CompetenceSoap[][] toSoapModels(Competence[][] models) {
		CompetenceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CompetenceSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CompetenceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CompetenceSoap[] toSoapModels(List<Competence> models) {
		List<CompetenceSoap> soapModels = new ArrayList<CompetenceSoap>(models.size());

		for (Competence model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CompetenceSoap[soapModels.size()]);
	}

	public CompetenceSoap() {
	}

	public long getPrimaryKey() {
		return _competenceId;
	}

	public void setPrimaryKey(long pk) {
		setCompetenceId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCompetenceId() {
		return _competenceId;
	}

	public void setCompetenceId(long competenceId) {
		_competenceId = competenceId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public String getStatusByUserName() {
		return _statusByUserName;
	}

	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getPage() {
		return _page;
	}

	public void setPage(String page) {
		_page = page;
	}

	public boolean getGenerateCertificate() {
		return _generateCertificate;
	}

	public boolean isGenerateCertificate() {
		return _generateCertificate;
	}

	public void setGenerateCertificate(boolean generateCertificate) {
		_generateCertificate = generateCertificate;
	}

	public String getDiplomaTemplate() {
		return _diplomaTemplate;
	}

	public void setDiplomaTemplate(String diplomaTemplate) {
		_diplomaTemplate = diplomaTemplate;
	}

	private String _uuid;
	private long _competenceId;
	private long _companyId;
	private long _groupId;
	private long _userId;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
	private String _title;
	private String _description;
	private String _page;
	private boolean _generateCertificate;
	private String _diplomaTemplate;
}