package com.liferay.lms.threads;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.ThemeDisplay;

public abstract class ImportCsvThread extends Thread {
	
	protected boolean isFinished;	
	protected int line;
	protected int totalLines;
	protected int numCorrectLines;
	protected int headerLength;
	protected String idThread;
	protected InputStream csvFile;
	protected String filePath;
	protected int progress;
	protected List<String> errors = new ArrayList<String>();
	protected ThemeDisplay themeDisplay;
		
	public ImportCsvThread(InputStream csvFile, String idThread, ThemeDisplay themeDisplay){
		this.themeDisplay = themeDisplay;
		this.idThread = idThread;
		this.csvFile = csvFile;
	}
	
	public void run(){
		_log.info("::Running thread:: uuid = " + idThread);
		isFinished = false;
		progress = 1;
		filePath = null;
		_log.info("::Start reading import file :: uuid " + idThread);
		readAndProcessCsv();
		_log.info("::Finish reading import file :: uuid " + idThread);
		filePath = createCSVImportReport();
		_log.info("::Report File Path :: [uuid =" + idThread + "] " + filePath);
		isFinished = true;
		progress = 100;
		_log.info("::Finish thread:: uuid = " + idThread);
	}
	
	protected abstract void readAndProcessCsv();
	
	private String createCSVImportReport(){
		
		_log.info("::Create report import :: uuid " + idThread);
		
		File file = FileUtil.createTempFile("csv");
		
		try(BufferedWriter bw  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));) {
			
			bw.append('\ufeff');
			
			bw.append(LanguageUtil.get(themeDisplay.getLocale(), 
					"courseadmin.import.csv.total-lines") 
					+ StringPool.COLON + StringPool.SPACE + totalLines 
					+ StringPool.NEW_LINE);
			
			bw.append(LanguageUtil.get(themeDisplay.getLocale(),
					"courseadmin.import.csv.correct-lines")
					+ StringPool.COLON + StringPool.SPACE + numCorrectLines 
					+ StringPool.NEW_LINE);
			
			bw.append(StringPool.NEW_LINE);
			
			for(String error:errors)
				bw.append("ERROR: " + error);
			
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file.getAbsolutePath();
	}

	public void setIsFinished(boolean isFinished){
		this.isFinished = isFinished;
	}
	public boolean isFinished(){
		return isFinished;
	}
	
	public void setIdThread(String idThread){
		this.idThread = idThread;
	}
	public String getIdThread(){
		return idThread;
	}
	
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}
	public String getFilePath(){
		return filePath;
	}
	
	public void setProgress(int progress){
		this.progress = progress;
	}
	public int getProgress(){
		return progress;
	}
	
	public void setErrors(List<String> errors){
		this.errors = errors;
	}
	public List<String> getErrors(){
		return errors;
	}
	
	public void setHeaderLength(int headerLength){
		this.headerLength = headerLength;
	}
	public int getHeaderLength(){
		return headerLength;
	}
	
	public void setHeaderLength(String[] data){
		this.headerLength = data.length;
	}
	public int getHeaderLength(String[] data){
		return data.length;
	}
	
	public boolean checkHead(long headerLength, int headerLengthFixed) throws IOException {
		boolean isCorrect = headerLength == headerLengthFixed;
		if(!isCorrect)
			errors.add(LanguageUtil.get(themeDisplay.getLocale(),
					"courseadmin.import.csv.error.header-incorrect") 
					+ StringPool.NEW_LINE);
		return isCorrect;
	}
	
	public boolean checkLineLength(String[] data, int headerLengthFixed) {
		boolean isCorrect = data.length == headerLengthFixed;
		if(!isCorrect)
			errors.add(LanguageUtil.format(themeDisplay.getLocale(),
					"courseadmin.import.csv.error.line-length", line) 
					+ StringPool.NEW_LINE);
		return isCorrect;
	}
	
	public int getTotalLines(CSVReader reader){
		int lines = -1;
		try {
			while (reader.readNext() != null) {
				lines++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	public static Log _log = LogFactoryUtil.getLog(ImportCsvThread.class);
}
