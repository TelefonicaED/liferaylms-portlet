package com.liferay.util; 

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import javax.mail.internet.InternetAddress;

import com.liferay.lms.model.CheckP2pMailing;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.impl.CheckP2pMailingImpl;
import com.liferay.lms.service.CheckP2pMailingLocalServiceUtil;
import com.liferay.lms.service.CourseLocalService;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.PortletQName;
import com.liferay.util.mail.MailEngine;
import com.liferay.util.mail.MailEngineException;
import com.tls.lms.util.LiferaylmsUtil;


public class P2pCheckActivity implements MessageListener
{
	@Override
	public void receive(Message message) {
		
		if(log.isDebugEnabled()){log.debug("P2pCheckActivity::receive:: Entra en la funcion");}
		
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivity.class);
		Criterion criterion=PropertyFactoryUtil.forName("typeId").eq(3);
		dq.add(criterion);
		
		try{

			List<LearningActivity> listActivities=
					(java.util.List<LearningActivity>)LearningActivityLocalServiceUtil.dynamicQuery(dq);
			
			if(listActivities!=null && listActivities.size()>0)
			{
				for(LearningActivity lAct:listActivities){
					if(CheckP2pMailingLocalServiceUtil.findByActId(lAct.getActId())==null){
						//String sNumFilesToPass = lAct.getExtracontent();
						String sNumFilesToPass = LearningActivityLocalServiceUtil.getExtraContentValue(lAct.getActId(), "validaciones");
						if(sNumFilesToPass.equals(""))
						{
							sNumFilesToPass="3";
						}
						long NumFilesToPass = Long.valueOf(sNumFilesToPass)+1;
						//Se le suma 1 puesto que tiene que debe haber el numero de actividades 
						//mas la del usuario puesto que el no se corrige a si mismo.
						List<P2pActivity> ListP2PinAct = P2pActivityLocalServiceUtil.findByActIdOrderByP2pId(lAct.getActId());
						boolean deregisterMail;
						if(ListP2PinAct.size() > NumFilesToPass){
							int cont = 0;
							for(P2pActivity myp2p:ListP2PinAct){
								long userId = myp2p.getUserId();
								User user = UserLocalServiceUtil.getUser(userId);
								//Comprobamos que la actividad no esta bloqueada para el usuario.
								if(!lAct.isLocked(user)){
									cont++;
									deregisterMail = false;
									if(user.getExpandoBridge().getAttribute(LiferaylmsUtil.DEREGISTER_USER_EXPANDO,false)!=null){
										deregisterMail = (Boolean)user.getExpandoBridge().getAttribute(LiferaylmsUtil.DEREGISTER_USER_EXPANDO,false);
									}
									
									if(!deregisterMail){
										sendMail(user, lAct.getActId());
									}
									if(cont>NumFilesToPass){
										CheckP2pMailing cP2pM = new CheckP2pMailingImpl();
										cP2pM.setActId(lAct.getActId());
										CheckP2pMailingLocalServiceUtil.addCheckP2pMailing(cP2pM);
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			if(log.isErrorEnabled()){
				log.error(e);
			}
		}
	}
	private static void sendMail(User user, long actId){
		try
		{
			//Falta Internacionalizar.
			LearningActivity act =LearningActivityLocalServiceUtil.getLearningActivity(actId);
			Module myModule = ModuleLocalServiceUtil.getModule(act.getModuleId());
			Group group = GroupLocalServiceUtil.getGroup(myModule.getGroupId());
			Company company = CompanyLocalServiceUtil.getCompany(group.getCompanyId());
			
			QName qName = SAXReaderUtil.createQName("moduleId");
			
			StringBundler sb = new StringBundler();
			
			sb.append("http://");
			sb.append(company.getVirtualHostname());
			sb.append("/web");
			sb.append(group.getFriendlyURL());
			sb.append("/reto");
			sb.append(StringPool.QUESTION);
			sb.append("p_p_id");
			sb.append(StringPool.EQUAL);
			sb.append("lmsactivitieslist_WAR_liferaylmsportlet");
			sb.append(StringPool.AMPERSAND);
			sb.append("p_p_lifecycle");
			sb.append(StringPool.EQUAL);
			sb.append("0");
			sb.append(StringPool.AMPERSAND);
			sb.append(PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE);
			sb.append(qName.getNamespaceURI().hashCode());
			sb.append(StringPool.UNDERLINE);
			sb.append(qName.getLocalPart());
			sb.append("moduleId");
			sb.append(StringPool.EQUAL);
			sb.append(myModule.getModuleId());
			
			
			String url = sb.toString();
					
			//"http://" + company.getVirtualHost() + "/web" +group.getFriendlyURL()+"/reto?p_p_id=lmsactivitieslist_WAR_liferaylmsportlet" +"&p_p_lifecycle=0";
			//String sharedParam = "&p_r_p_"+qName.getNamespaceURI().hashCode()+"_moduleId="+myModule.getModuleId();
			//url += sharedParam;
			
			String firmaPortal  = PrefsPropsUtil.getString(company.getCompanyId(),"firma.email.admin");
			// JOD
			firmaPortal = (firmaPortal!=null?firmaPortal:"");
			
			if(log.isDebugEnabled()){log.debug("P2pCheckActivity::sendMail::URL:"+url);}
			
			String subject = new String(LanguageUtil.format(user.getLocale(), "you-can-pass-activity-p2p-subject", 
					new Object[]{act.getTitle(user.getLocale())}).getBytes(), Charset.forName("UTF-8"));
			
			if(log.isDebugEnabled()){log.debug("P2pCheckActivity::sendMail::subject:"+subject);}
			
			// JOD: duplicar la url.
			String body=new String(LanguageUtil.format(user.getLocale(), 
					"you-can-pass-activity-p2p-body", 
					new Object[]{user.getFullName(),url,url,firmaPortal},
					false).getBytes(), Charset.forName("UTF-8"));
			
			if(log.isDebugEnabled()){log.debug("P2pCheckActivity::sendMail::body:"+body);}
			
		
			//String body=new String(LanguageUtil.format(user.getLocale(), "you-can-pass-activity-p2p-body", new Object[]{user.getFullName(),url}).getBytes(), Charset.forName("UTF-8"));
			
			//String fromUser=PrefsPropsUtil.getString(user.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
			String fromName=PrefsPropsUtil.getString(user.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_NAME,"");
			String fromAddress=PrefsPropsUtil.getString(user.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_ADDRESS,"");
			InternetAddress from = new InternetAddress(fromAddress, fromName);
			InternetAddress to = new InternetAddress(user.getEmailAddress(), user.getFullName());
			
			MailMessage mailMessage = new MailMessage(from, to, subject, body, true);
			
			MailServiceUtil.sendEmail(mailMessage);		
			
			
			//MailEngine.send(from, new InternetAddress[]{to}, new InternetAddress[]{}, subject, body, true);
			/*String from=PrefsPropsUtil.getString(user.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
			//MailEngine.send( from,user.getEmailAddress(),subject,body);*/
			if(log.isDebugEnabled()){log.debug("P2pCheckActivity::sendMail::Enviado email a :"+user.getEmailAddress());}
	
		}
		/*catch(MailEngineException ex) {
			if(log.isDebugEnabled()){log.debug("P2pCheckActivity::sendMail::MailEngineException:"+ex);}
		}*/ catch (Exception e) {
			if(log.isDebugEnabled()){log.debug("P2pCheckActivity::sendMail::Exception:"+e);}
		}
	}
	private static Log log = LogFactoryUtil.getLog(P2pCheckActivity.class);
}
