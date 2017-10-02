package com.liferay.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;

public class LmsLocaleUtil {
	static Log log = LogFactoryUtil.getLog(LmsLocaleUtil.class);

	public static <T> T checkDefaultLocale(Class<T> type,Object oinstance, String propertie){		
		if(propertie.length()>0){
			String methodName = new StringBuffer("get").append(propertie.substring(0, 1).toUpperCase()).append(propertie.substring(1, propertie.length())).toString();
			String setMethodName = new StringBuffer("set").append(propertie.substring(0, 1).toUpperCase()).append(propertie.substring(1, propertie.length())).toString();
			try{	
				Method getLocaleMethod = type.getMethod(methodName, new Class[] {Locale.class});
				if(getLocaleMethod!=null){
					String text = (String)getLocaleMethod.invoke(oinstance, new Object[] {LocaleUtil.getDefault()});
					if(text==null||text.equals(StringPool.BLANK)){
						Method getBruteMethod = type.getMethod(methodName, new Class[] {});
						if(getBruteMethod!=null){
							text = (String)getBruteMethod.invoke(oinstance, new Class[] {});
							String[] locales = LocalizationUtil.getAvailableLocales(text);
							if(locales.length>0){
								Method getStringMethod = type.getMethod(methodName, new Class[] {String.class});
								if(getStringMethod!=null){
									String defaultText = null;
									for(String locale : locales){ 
										String tLocale = (String)getStringMethod.invoke(oinstance, new Object[] {locale});
										if(tLocale!=null&&!tLocale.equals(StringPool.BLANK)){
											defaultText = tLocale;
											break;
										}
									}
									if(defaultText!=null&&!defaultText.equals(StringPool.BLANK)){ 
										Method setMethod = type.getMethod(setMethodName, new Class[] {String.class,Locale.class});
										setMethod.invoke(oinstance, new Object[] {defaultText,LocaleUtil.getDefault()});
									}
								}
							}
						}
						
					}
				}
			}catch(Exception e){
				if(log.isDebugEnabled())e.printStackTrace();
				if(log.isInfoEnabled())log.info(e.getMessage());
			}
		}
		
		return type.cast(oinstance);
	}
	
	public static Map<Locale, String> getLocalizationMap(
			UploadRequest request, String parameter) {

			Locale[] locales = LanguageUtil.getAvailableLocales();

			Map<Locale, String> map = new HashMap<Locale, String>();

			for (Locale locale : locales) {
				String languageId = LocaleUtil.toLanguageId(locale);

				String localeParameter = parameter.concat(
					StringPool.UNDERLINE).concat(languageId);
				map.put(
					locale,
					ParamUtil.getString(request,localeParameter));
			}

			return map;
	}
}
