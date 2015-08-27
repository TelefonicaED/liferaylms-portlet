package com.liferay.lms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class LearningTypesProperties {
	private static Properties prop = null; 

	public static String get(String key){
		if(prop==null)
			init();
		return prop.getProperty(key);
	}
	
	public static Set<String> getNames(){
		if(prop==null)
			init();
		return prop.stringPropertyNames();
	}
	
	public static String getName(String property){
		if(prop==null)
			init();
		
		Set<String> names = prop.stringPropertyNames();
		for(String name: names){
			if(name.equals(property)){
				String []prop = name.split(".");
				String res = prop[prop.length-1];
				return res.substring(0, 1).toUpperCase();
			}
		}
		return "";
	}
	
	private static void init(){
		prop = new Properties();
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			prop.load(classLoader.getResourceAsStream("learningtype.properties"));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
}
