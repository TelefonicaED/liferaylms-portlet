package com.liferay.lms.upgrade;

import java.io.IOException;
import java.sql.SQLException;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

public class UpgradeVersion_4_10_0 extends UpgradeProcess {
	private static Log log = LogFactoryUtil.getLog(UpgradeVersion_4_10_0.class);
	
	public int getThreshold() {
		return 4100;
	}
	
	protected void doUpgrade() throws Exception {
		log.info("Actualizando version a 4.5.0");		
		
		DB db = DBFactoryUtil.getDB();			
		
		String updateTestQuestion = "UPDATE "
				+ "lms_testquestion qe "
				+ "INNER JOIN lms_learningactivity ae ON qe.actId = ae.actId "
				+ "INNER JOIN lms_course ce ON ae.groupId = ce.groupCreatedId "
				+ "INNER JOIN lms_course c ON c.courseId = ce.parentCourseId "
				+ "INNER JOIN lms_learningactivity a ON a.uuid_ = ae.uuid_ AND a.groupId = c.groupCreatedId "
				+ "INNER JOIN lms_testquestion q ON a.actId = q.actId AND qe.text_ = q.text_ AND qe.questionType = q.questionType "
				+ "SET qe.uuid_ = q.uuid_;";
		
		log.info("Update table lms_testquestion -->> SET uuid");
		try {
			db.runSQL(updateTestQuestion);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		
		String updateTestAnswer = "UPDATE "
				+ "lms_testanswer ane "
				+ "INNER JOIN lms_testquestion qe ON ane.questionId = qe.questionId "
				+ "INNER JOIN lms_learningactivity ae ON qe.actId = ae.actId "
				+ "INNER JOIN lms_course ce ON ae.groupId = ce.groupCreatedId "
				+ "INNER JOIN lms_course c ON c.courseId = ce.parentCourseId "
				+ "INNER JOIN lms_learningactivity a ON a.uuid_ = ae.uuid_ AND a.groupId = c.groupCreatedId "
				+ "INNER JOIN lms_testquestion q ON q.uuid_ = qe.uuid_ AND q.actId = a.actId "
				+ "INNER JOIN lms_testanswer an ON an.questionId = q.questionId AND an.answer = ane.answer "
				+ "SET ane.uuid_ = an.uuid_;";
		
		log.info("Update table lms_testanswer -->> SET uuid");
		try {
			db.runSQL(updateTestAnswer);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
	}
	
	
}