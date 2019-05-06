package com.liferay.lms.events;

import java.io.Serializable;

public final class ThemeIdEvent implements Serializable {	
	private static final long serialVersionUID = -8549599250647470213L;
	
	public static final long EVALUATION_THEME_ID=-1;
	
	private long moduleId;
	long themeId;
	private long actId;
	/**
	 * @return the moduleId
	 */
	public final long getModuleId() {
		return moduleId;
	}
	public long getActId() {
		return actId;
	}
	public void setActId(long actId) {
		this.actId = actId;
	}
	/**
	 * @param moduleId the moduleId to set
	 */
	public final void setModuleId(final long moduleId) {
		this.moduleId = moduleId;
	}
	/**
	 * @return the themeId
	 */
	public final long getThemeId() {
		return themeId;
	}
	/**
	 * @param themeId the themeId to set
	 */
	public final void setThemeId(final long themeId) {
		this.themeId = themeId;
	}
	
	
}
