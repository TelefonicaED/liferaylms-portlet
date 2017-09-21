package com.liferay.lms.upgrade;


import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.util.UpgradeUtil;

public class UpgradeVersion extends UpgradeProcess {
	public int getThreshold() {
		return 233;
	}

	protected void doUpgrade() throws Exception {
		// your upgrade code here.
		
		UpgradeUtil.upgrade();
		
	
	}
	
	
}