package com.liferay.util;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.Validator;

import java.util.Iterator;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;


public class VelocityUtil {

	public static String evaluate(String input) throws Exception {
		return evaluate(input, null);
	}

	public static String evaluate(String input, Map<String, Object> variables)
		throws Exception {

		VelocityEngine velocityEngine = new VelocityEngine();

		velocityEngine.init();

		VelocityContext velocityContext = new VelocityContext();

		if (variables != null) {
			Iterator<Map.Entry<String, Object>> itr =
				variables.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = itr.next();

				String key = entry.getKey();
				Object value = entry.getValue();

				if (Validator.isNotNull(key)) {
					velocityContext.put(key, value);
				}
			}
		}

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		velocityEngine.evaluate(
			velocityContext, unsyncStringWriter, VelocityUtil.class.getName(),
			input);

		return unsyncStringWriter.toString();
	}

}
