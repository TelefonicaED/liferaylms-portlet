package com.liferay.lms.threads;

import java.util.Enumeration;
import java.util.Hashtable;

public class ReportThreadMapper {
	private static Hashtable<String, ReportThread> importThreads = new Hashtable<String, ReportThread>();
	
	/**
	 * Añade un hilo al mapa de hilos
	 * @param uuid
	 * @param hilo
	 */
	public static void addThread(String uuid,ReportThread hilo){
		unlinkFinishedThreads();
		importThreads.put(uuid, hilo);
		hilo.start();
	}
	
	/**
	 * Devuelve si un hilo ha terminado
	 * @param uuid identificador del hilo
	 * @return true si el hijo ha terminado
	 * false en caso contrario
	 */
	public static boolean threadFinished(String uuid){
		if(importThreads.get(uuid)!=null){
			return importThreads.get(uuid).isFinished();
		}else{
			return true;
		}
	}
	
	/**
	 * Devuelve si un hilo est� todav�a en el mapa
	 * @param uuid identificador del hilo
	 * @return true si est� en el mapa
	 * false en caso contrario
	 */
	public static boolean getThread(String uuid){
		return importThreads.contains(uuid);
	}
	
	/**
	 * Devuelve el nombre del fichero a descargar cuando el hilo ha finalizado
	 * @param uuid identificador del hilo
	 * @return ruta del fichero a descargar si el hilo ha finalizado
	 */
	public static String getThreadFileName(String uuid){
		return importThreads.get(uuid).getFileName();
	}
	
	/**
	 * Elimina el hilo del mapa
	 * @param uuid identificador del hilo
	 */
	public static void unlinkThread(String uuid){
		importThreads.remove(uuid);
	}
	
	/**
	 * Devuelve el progreso del hilo en funci�n de las filas pintadas en el csv
	 * @param uuid identificador del hilo
	 * @return progreso del 0-100
	 */
	public static int getProgress(String uuid){
		return importThreads.get(uuid).getProgress();
	}	
	
	/**
	 * Elimina los hilos que ya han finalizado del mapa
	 */
	public static void unlinkFinishedThreads(){
		Enumeration<String> keys = importThreads.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			ReportThread hilo  = importThreads.get(key);
			if(hilo.isFinished()){
				importThreads.remove(key);
			}
		}
			
	}
	
	public static void setThreadProgress(String uuid, int progreso){
		importThreads.get(uuid).setProgress(progreso);
	}
	public static long getThreadLines(String uuid){
		return importThreads.get(uuid).getTotalLines();
	}
	public static long getThreadRegistered(String uuid){
		return importThreads.get(uuid).getCountRegistered();
	}
	public static boolean getResult(String uuid){
		int countLinesWithData = importThreads.get(uuid).getTotalLines()-1;
		return countLinesWithData==importThreads.get(uuid).getCountRegistered();
	}
}
