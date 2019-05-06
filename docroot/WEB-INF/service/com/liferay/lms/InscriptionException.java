package com.liferay.lms;

import com.liferay.portal.kernel.exception.PortalException;

public class InscriptionException extends PortalException {
	
	private String key;
	
	public InscriptionException(String key, String message) {
		super(message);
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
