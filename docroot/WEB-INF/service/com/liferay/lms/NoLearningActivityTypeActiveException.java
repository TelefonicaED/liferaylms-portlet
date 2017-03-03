package com.liferay.lms;

import com.liferay.portal.kernel.exception.PortalException;

public class NoLearningActivityTypeActiveException  extends PortalException {

	public NoLearningActivityTypeActiveException() {
		super();
	}

	public NoLearningActivityTypeActiveException(String msg) {
		super(msg);
	}

	public NoLearningActivityTypeActiveException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoLearningActivityTypeActiveException(Throwable cause) {
		super(cause);
	}

}
