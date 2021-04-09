package com.liferay.lms.threads;

import java.util.Enumeration;
import java.util.Hashtable;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

public class ImportCsvThreadMapper {
	
	private static Hashtable<String, ImportCsvThread> imporCsvThreads = new Hashtable<String, ImportCsvThread>();
	
	public static void addThread(String uuid,ImportCsvThread thread){
		unlinkFinishedThreads();
		imporCsvThreads.put(uuid, thread);
		thread.start();
	}
	
	public static boolean threadFinished(String uuid){
		if(Validator.isNotNull(imporCsvThreads.get(uuid)))
			return imporCsvThreads.get(uuid).isFinished();
		else
			return true;
	}
	
	public static boolean getThread(String uuid){
		return imporCsvThreads.contains(uuid);
	}
		
	public static String getFileUrl(String uuid){
		return imporCsvThreads.get(uuid).getFilePath();
	}
	
	public static void unlinkThread(String uuid){
		_log.info("::UnlinkThread:: uuid = " + uuid);
		imporCsvThreads.remove(uuid);
	}
	
	public static int getProgress(String uuid){
		if(Validator.isNotNull(imporCsvThreads.get(uuid)))
			return imporCsvThreads.get(uuid).getProgress();
		else
			return 100;
	}	
	
	public static void setThreadProgress(String uuid, int progreso){
		imporCsvThreads.get(uuid).setProgress(progreso);
	}
	
	public static void unlinkFinishedThreads(){
		Enumeration<String> keys = imporCsvThreads.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			ImportCsvThread thread  = imporCsvThreads.get(key);
			if(thread.isFinished()){
				imporCsvThreads.remove(key);
			}
		}
	}
	
	private static Log _log = LogFactoryUtil.getLog(ImportCsvThreadMapper.class);
}