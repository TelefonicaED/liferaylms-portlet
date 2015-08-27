package com.tls.util.liferay.patch;

import com.liferay.portal.kernel.util.MethodKey;

@SuppressWarnings("serial")
public class MethodKeyPatched extends MethodKey 
{
	public MethodKeyPatched(String className, String methodName,
			String[] parameterTypeNames,ClassLoader loader) throws ClassNotFoundException {
		super(className,methodName,toClassArray(parameterTypeNames,loader));
	}

	public static Class<?>[] toClassArray(String[] parameterTypeNames,ClassLoader loader) throws ClassNotFoundException
	{
		Class<?>[] _parameterTypes = new Class[parameterTypeNames.length];
	
		for (int i = 0; i < parameterTypeNames.length; i++) 
		{
            String parameterTypeName = parameterTypeNames[i];
            _parameterTypes[i] = Class.forName(parameterTypeName,true,loader);
        }
		return _parameterTypes;
	}
	
}
