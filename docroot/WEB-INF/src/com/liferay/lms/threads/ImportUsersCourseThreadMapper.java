package com.liferay.lms.threads;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class ImportUsersCourseThreadMapper {
	private static Hashtable<String, ImportUsersCourseThread> importThreads = new Hashtable<String, ImportUsersCourseThread>();
	
	public static void addThread(String uuid,ImportUsersCourseThread hilo){
		unlinkFinishedThreads();
		importThreads.put(uuid, hilo);
		hilo.start();
	}
	
	public static boolean threadFinished(String uuid){
		if(importThreads.get(uuid)!=null){
			return importThreads.get(uuid).isFinished();
		}else{
			return true;
		}
	}
	
	
	public static boolean getThread(String uuid){
		return importThreads.contains(uuid);
	}
		
	public static String getThreadFileUrl(String uuid){
		return importThreads.get(uuid).getFileName();
	}
	
	public static void unlinkThread(String uuid){
		importThreads.remove(uuid);
	}
	
	public static int getProgress(String uuid){
		return importThreads.get(uuid).getProgress();
	}	
	
	public static void unlinkFinishedThreads(){
		Enumeration<String> keys = importThreads.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			ImportUsersCourseThread hilo  = importThreads.get(key);
			if(hilo.isFinished()){
				importThreads.remove(key);
			}
		}
			
	}
	
	public static void setThreadProgress(String uuid, int progreso){
		importThreads.get(uuid).setProgress(progreso);
	}
	
	
	public static List<String> getThreadErrors(String uuid){
		return importThreads.get(uuid).getErrors();
	}
	
	public static  List<String>  getThreadWarnings(String uuid){
		return importThreads.get(uuid).getWarnings();
	}
	
	
	public static long getThreadLines(String uuid){
		return importThreads.get(uuid).getTotalLines();
	}
	
	public static String getThreadFileReport(String uuid){
		return importThreads.get(uuid).getFileReport();
	}
	
	public static int getUsersInscripted(String uuid){
		return importThreads.get(uuid).getUsersInscripted();
	}
	

}
