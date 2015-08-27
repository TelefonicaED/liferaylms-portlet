package com.liferay.lms;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class AnnouncementsDeliver
 */
public class AnnouncementsDeliver extends MVCPortlet {
 
public void changeDelivery(ActionRequest arq, ActionResponse arp){
	String pks = ParamUtil.get(arq, "announcementsDeliveriesSearchContainerPrimaryKeys","");
	if(!pks.equalsIgnoreCase("")){
		String[] deliveries= pks.split(",");
		for(String del:deliveries){
			
			try {
				long deliveryId = Long.parseLong(del);
				AnnouncementsDelivery ad = AnnouncementsDeliveryLocalServiceUtil.fetchAnnouncementsDelivery(deliveryId);
				boolean value = ParamUtil.get(arq, "checkbox_"+deliveryId,ad.getEmail());
				ad.setEmail(value);
				AnnouncementsDeliveryLocalServiceUtil.updateAnnouncementsDelivery(ad);
			} catch (SystemException e) {
				e.printStackTrace();
			}catch(NumberFormatException e){}
		}
	}
}

}
