package com.liferay.lms.threads;

import java.util.Enumeration;
import java.util.Hashtable;

public class ExportSurveyStatisticsThreadMapper {
	private static Hashtable<String, ExportSurveyStatisticsContentThread> hilos = new Hashtable<String, ExportSurveyStatisticsContentThread>();
		
	public static void addHilo(String uuid,ExportSurveyStatisticsContentThread hilo){
		unlinkFinishedHilosExcel();
		hilos.put(uuid, hilo);
		hilo.start();
	}
	
	
	public static boolean hiloFinished(String uuid){
		if(hilos.get(uuid)!=null){
			return hilos.get(uuid).isFinished();
		}else{
			return true;
		}
	}
	
	
	public static boolean getHiloExcel(String uuid){
		return hilos.contains(uuid);
	}
	
	public static String getFileUrl(String uuid){
		return hilos.get(uuid).getFilePath();
	}	

	public static String getFileName(String uuid){
		return hilos.get(uuid).getFileName();
	}	
	
	public static void unlinkHiloExcel(String uuid){
		hilos.remove(uuid);
	}
	
	
	public static void unlinkFinishedHilosExcel(){
		Enumeration<String> keys = hilos.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			ExportSurveyStatisticsContentThread hilo  = hilos.get(key);
			if(hilo.isFinished()){
				hilos.remove(key);
			}
		}
			
	}
	
	
}
