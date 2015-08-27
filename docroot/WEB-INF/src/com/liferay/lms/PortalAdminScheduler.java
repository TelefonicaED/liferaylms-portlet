package com.liferay.lms;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

public class PortalAdminScheduler implements MessageListener {
	
	@Override
	public void receive(Message arg0) {
		
		try {

			
			//PortalAdmin.deleteRepeatedModuleResult(true);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
