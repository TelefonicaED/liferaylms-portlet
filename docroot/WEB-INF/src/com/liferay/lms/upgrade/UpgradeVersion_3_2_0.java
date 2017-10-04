package com.liferay.lms.upgrade;


import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.util.UpgradeUtil;

public class UpgradeVersion_3_2_0 extends UpgradeProcess {
	public int getThreshold() {
		return 320;
	}

	protected void doUpgrade() throws Exception {
		// your upgrade code here.
		
		UpgradeUtil.upgrade();
		
	
	}
	
	
}