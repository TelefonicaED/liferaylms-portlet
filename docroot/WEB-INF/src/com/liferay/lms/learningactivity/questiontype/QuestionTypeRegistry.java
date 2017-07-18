package com.liferay.lms.learningactivity.questiontype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.PropsUtil;

public class QuestionTypeRegistry {
	static Map<Long, QuestionType> types;
	static List<QuestionType> typesList;

	public QuestionType getQuestionType(long typeId) {
		return types.get(typeId);
	}

	public java.util.List<QuestionType> getQuestionTypes() {
		return typesList;
	}

	public QuestionTypeRegistry() throws SystemException {
		if( types==null ){
			Properties props = PropsUtil.getProperties("lms.question.type", true);
			types = new HashMap<Long, QuestionType>();
			typesList = new java.util.ArrayList<QuestionType>();
			for (Object key:props.keySet()) {
				String type=props.getProperty(key.toString());
				try {
					Class<?> c = Class.forName(type);
				
					QuestionType qt = (QuestionType)c.newInstance();
					long typeId=qt.getTypeId();
					types.put(typeId,qt);
					typesList.add(qt);
				} catch (ClassNotFoundException e) {
					try {
						String [] context = ((String) key).split("\\.");
						Class c = Class.forName(type, true, PortletClassLoaderUtil.getClassLoader(context[1]));
						ClassLoaderProxy clp = new ClassLoaderProxy(c.newInstance(), type, PortletClassLoaderUtil.getClassLoader(context[1]));
						QuestionTypeClp qtclp = new QuestionTypeClp(clp);
						long typeId=qtclp.getTypeId();
						types.put(typeId,qtclp);
						typesList.add(qtclp);
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					} catch (Throwable e1) {
						e1.printStackTrace();
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
