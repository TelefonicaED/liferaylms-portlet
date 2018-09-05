package com.liferay.lms.auditing;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;

public class AuditingLogFactory 
{
	static Log log = LogFactoryUtil.getLog(AuditingLogFactory.class);
	protected static final String LMS_ACTIVITIES_LIST_PORTLET_ID =  PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());

	static List<AuditingLog> auditLogs = new ArrayList<AuditingLog>();
	public static List<AuditingLog> getAuditLogs() throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		if(auditLogs.size()<=0){
			Class<?> clase= null;
			try{
				Properties properties = PrefsPropsUtil.getProperties("audit.implementation", true);// PropsUtil.getProperties("audit.implementation", true);
				log.debug("pref props properties size: " + properties.size());
				log.debug("pref props properties size: " + properties.size());
				if(properties.size()<=0){
					properties = PropsUtil.getProperties("audit.implementation", true);
					log.debug("props properties size: " + properties.size());
					log.debug("props properties size: " + properties.size());
				}
				for (Object key:properties.keySet()) {
					log.debug("key: " + key.toString());
					String className=properties.getProperty(key.toString());
					log.debug("type: " + className);
					String [] context = ((String) key).split("\\.");
					String classContent = LMS_ACTIVITIES_LIST_PORTLET_ID;
					log.debug("Context length "+context.length);
					if (Validator.isNotNull(context) && context.length > 1) {
						classContent = context[1];
					}
					log.debug("classContent: "+classContent);
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(className, true, 
							PortletClassLoaderUtil.getClassLoader(classContent)).newInstance(), className, 
							PortletClassLoaderUtil.getClassLoader(classContent));
					AuditingLog auditLog=new AuditingLogClp(classLoaderProxy);
					log.debug("auditing with defined:"+className);
					auditLogs.add(auditLog);
				
				}
				
			}catch(SystemException e){
				e.printStackTrace();
			}
			
			
			if(auditLogs.size()<=0){
				clase=Class.forName("com.liferay.lms.auditing.AuditingLogDB");
				auditLogs.add((AuditingLog)clase.newInstance());
			}
			
		}
		return auditLogs;
	}
	
	public static void audit(long companyId, long groupId, String className,long classPK,
			long userId, String action, String extraData)
			throws SystemException {
		audit(companyId, groupId, className, classPK, GetterUtil.DEFAULT_LONG, 
				userId, action, extraData);
	}
	
	
	public static void resetAuditLogs() throws SystemException {
		log.debug("Reseting auditLogs "+auditLogs.size());
		auditLogs = new ArrayList<AuditingLog>();
		log.debug("Audit Logs RESETED "+auditLogs.size());
	}
	
	
	public static void audit(long companyId, long groupId, String className,long classPK,long associationClassPK, 
			long userId, String action, String extraData)
			throws SystemException {
		try{
			if(log.isDebugEnabled())log.debug("audit:"+className+"::"+classPK);
			List<AuditingLog> auditLogs = getAuditLogs();
			for(AuditingLog auditLog : auditLogs){
				log.debug("AUDIT LOG "+auditLog.toString());
				auditLog.audit(companyId, groupId, className, classPK, associationClassPK, userId, action, extraData);
			}
			
		}catch(ClassNotFoundException cnfe){
			if(log.isDebugEnabled())cnfe.printStackTrace();
		}catch(IllegalAccessException iae){
			if(log.isDebugEnabled())iae.printStackTrace();
		}catch(InstantiationException ie){
			if(log.isDebugEnabled())ie.printStackTrace();
		}
	}
}
