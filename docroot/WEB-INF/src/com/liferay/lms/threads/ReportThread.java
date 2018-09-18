package com.liferay.lms.threads;

import java.io.File;
import java.io.IOException;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.theme.ThemeDisplay;

public abstract class ReportThread extends Thread {
	private static Log log = LogFactoryUtil.getLog(ReportThread.class);

	private boolean isFinished;	
	protected ThemeDisplay themeDisplay;
	private String idThread;
	protected String fileName;
	protected File importFile;
	protected int progress;
	protected int totalLines;
	protected int line;	
	protected int countRegistered;

	public ReportThread(ThemeDisplay themeDisplay, String idThread, File importFile) throws PortalException, SystemException{
		this.setThemeDisplay(themeDisplay);
		this.idThread = idThread;
		this.importFile = importFile;
		progress = 0;
		totalLines = 0;
		countRegistered = 0;
		setLine(0);
	}

	/**
	 * Ejecuta el hilo
	 */
	public void run() {
		log.debug("ReportThread::run::init");
		try {
			progress = 1;
			generateCSV();
			isFinished = true;
			progress = 100;
			
		}catch (Exception e) {
			e.printStackTrace();
			ReportThreadMapper.unlinkThread(idThread);
		} 
		log.debug("ReportThread::run::end");
	}

	/**
	 * Metodo abstracto donde crearemos el fichero
	 * @throws IOException 
	 */
	protected abstract void generateCSV() throws IOException;

	public boolean isFinished(){
		return this.isFinished;
	}

	public int getProgress(){
		if(totalLines > 0){
			return 100 * line / totalLines;
		}else{
			return this.progress;
		}
	}

	public String getIdThread(){
		return this.idThread;
	}
	
	public String getFileName(){
		return this.fileName;
	}

	public void setProgress(int progress){
		this.progress = progress;
	}

	public int getTotalLines() {
		return totalLines;
	}

	public ThemeDisplay getThemeDisplay() {
		return themeDisplay;
	}

	public void setThemeDisplay(ThemeDisplay themeDisplay) {
		this.themeDisplay = themeDisplay;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}
	
	public File getImportFile() {
		return importFile;
	}

	public void setImportFile(File importFile) {
		this.importFile = importFile;
	}
	
	public int getCountRegistered() {
		return countRegistered;
	}

	public void setCountRegistered(int countRegistered) {
		this.countRegistered = countRegistered;
	}

}
