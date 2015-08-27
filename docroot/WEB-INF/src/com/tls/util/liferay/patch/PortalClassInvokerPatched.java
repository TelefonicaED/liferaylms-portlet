package com.tls.util.liferay.patch;

import java.lang.reflect.InvocationTargetException;

import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

public class PortalClassInvokerPatched 
{
    public static Object invoke(
                    boolean newInstance, String className, String methodName,
                    String[] parameterTypeNames, Object... arguments)
            throws Exception {

            Thread currentThread = Thread.currentThread();

            ClassLoader contextClassLoader = currentThread.getContextClassLoader();

            try {
                    currentThread.setContextClassLoader(
                            PortalClassLoaderUtil.getClassLoader());

                    MethodKey methodKey = new MethodKeyPatched(
                            className, methodName, parameterTypeNames,
                            PortalClassLoaderUtil.getClassLoader());

                    MethodHandler methodHandler = new MethodHandler(
                            methodKey, arguments);

                    return methodHandler.invoke(newInstance);
            }
            catch (InvocationTargetException ite) {
                    throw (Exception)ite.getCause();
            }
            finally {
                    currentThread.setContextClassLoader(contextClassLoader);
            }
    }
}
