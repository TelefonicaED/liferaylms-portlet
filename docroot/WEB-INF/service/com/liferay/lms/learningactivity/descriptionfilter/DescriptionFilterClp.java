package com.liferay.lms.learningactivity.descriptionfilter;

import javax.portlet.RenderRequest;

import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;


public class DescriptionFilterClp implements DescriptionFilter {
	private ClassLoaderProxy clp;
	public DescriptionFilterClp(ClassLoaderProxy clp) {
		this.clp = clp;
	}
	@Override
	public String filter(String description) {
		Object returnObj = null;

		try {
			MethodKey translateMethod = new MethodKey(clp.getClassName(), "filter", String.class); 
			returnObj = clp.invoke(new MethodHandler(translateMethod, description));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}

}
