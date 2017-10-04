package com.liferay.lms.learningactivity.testquestion;

import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

public class GenerateQuestionClp implements GenerateQuestion{

	private ClassLoaderProxy clp;
	
	public GenerateQuestionClp(ClassLoaderProxy clp) {
		
		this.clp = clp;
	}

	@Override
	public String generateAleatoryQuestions(long actId, long typeId) {
		
		Object returnObj = null;
		
		try {
			MethodKey translateMethod = new MethodKey(clp.getClassName(), "generateAleatoryQuestions", long.class, long.class);
			returnObj = clp.invoke(new MethodHandler(translateMethod, actId, typeId));
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
		return (String)returnObj;
	}
	
}
