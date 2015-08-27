/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.lms.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.liferay.lms.model.Competence;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.base.CompetenceLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.util.LmsLocaleUtil;

/**
 * The implementation of the competence local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CompetenceLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.CompetenceLocalServiceBaseImpl
 * @see com.liferay.lms.service.CompetenceLocalServiceUtil
 */
public class CompetenceLocalServiceImpl extends CompetenceLocalServiceBaseImpl {
	private static Log log = LogFactoryUtil.getLog(CompetenceLocalServiceImpl.class);
	
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CompetenceLocalServiceUtil} to access the competence local service.
	 */
	@Indexable(type=IndexableType.REINDEX)
	public Competence addCompetence (String title, String description,ServiceContext serviceContext)
			throws SystemException, PortalException 
			{
		       return addCompetence ( title,description,false, serviceContext);
		    			
			}
	
	@Indexable(type=IndexableType.REINDEX)
	public Competence addCompetence (String title, String description,boolean generateCertificate,ServiceContext serviceContext)
			throws SystemException, PortalException 
			{
		long userId=serviceContext.getUserId();
		Competence competence = competencePersistence.create(counterLocalService.increment(Competence.class.getName()));	
			competence.setCompanyId(serviceContext.getCompanyId());
			competence.setGroupId(serviceContext.getScopeGroupId());
			competence.setDiplomaTemplate(ParamUtil.getString(serviceContext.getRequest(),"template",StringPool.BLANK ),serviceContext.getLocale());
			competence.setGenerateCertificate(generateCertificate);
			competence.setUserId(userId);
			competence.setDescription(description,serviceContext.getLocale());
			competence.setTitle(title,serviceContext.getLocale());
			competence.setStatus(WorkflowConstants.STATUS_APPROVED);
			competence.setExpandoBridgeAttributes(serviceContext);
			competence.setPage((String)serviceContext.getAttribute("page"));
			competencePersistence.update(competence, true);
			

			competence = LmsLocaleUtil.checkDefaultLocale(Competence.class, competence, "title");
			competence = LmsLocaleUtil.checkDefaultLocale(Competence.class, competence, "description");
			
			try
			{
			resourceLocalService.addResources(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
					userId,Competence.class.getName(), competence.getPrimaryKey(), false,true, true);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			AssetEntryLocalServiceUtil.updateEntry(userId, competence.getGroupId(), Competence.class.getName(),
					competence.getCompetenceId(), competence.getUuid(),0, serviceContext.getAssetCategoryIds(),
					serviceContext.getAssetTagNames(), true, null, null,new java.util.Date(System.currentTimeMillis()), null,
					ContentTypes.TEXT_HTML, competence.getTitle(),
					competence.getDescription(serviceContext.getLocale()), competence.getDescription(serviceContext.getLocale()),
					null, null, 0, 0,null, false);
			//creating group
		return competence;
		
		
	}

	@Indexable(type=IndexableType.REINDEX)
	public Competence updateCompetence(Competence competence, ServiceContext serviceContext) throws SystemException, PortalException {

		competence = LmsLocaleUtil.checkDefaultLocale(Competence.class, competence, "title");
		competence = LmsLocaleUtil.checkDefaultLocale(Competence.class, competence, "description");
		
		Competence competenceReturn = competencePersistence.update(competence, true);
		Locale locale=new Locale(serviceContext.getLanguageId());
		
		long userId=serviceContext.getUserId();
		AssetEntryLocalServiceUtil.updateEntry(
				userId, competence.getGroupId(), Competence.class.getName(),
				competence.getCompetenceId(), competence.getUuid(),0, serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(), true, null, null,
				new java.util.Date(System.currentTimeMillis()), null,
				ContentTypes.TEXT_HTML, competence.getTitle(), competence.getDescription(locale), competence.getDescription(locale), null, null, 0, 0,
				null, false);
		return competenceReturn;
	}

	@Indexable(type=IndexableType.REINDEX)
	public Competence modCompetence (Competence competence, ServiceContext serviceContext) throws SystemException, PortalException {
			competence.setExpandoBridgeAttributes(serviceContext);

			competence = LmsLocaleUtil.checkDefaultLocale(Competence.class, competence, "title");
			competence = LmsLocaleUtil.checkDefaultLocale(Competence.class, competence, "description");
			
			Locale locale=new Locale(serviceContext.getLanguageId());
			competencePersistence.update(competence, true);
			long userId=serviceContext.getUserId();
			AssetEntryLocalServiceUtil.updateEntry(
					userId, competence.getGroupId(), Competence.class.getName(),
					competence.getCompetenceId(), competence.getUuid(),0, serviceContext.getAssetCategoryIds(),
					serviceContext.getAssetTagNames(), true, null, null,
					new java.util.Date(System.currentTimeMillis()), null,
					ContentTypes.TEXT_HTML, competence.getTitle(), competence.getDescription(locale), competence.getDescription(locale), null, null, 0, 0,
					null, false);
			return competence;
		
	}
	
	@Override
	@Indexable(type=IndexableType.DELETE)
	public Competence deleteCompetence (long competenceId) throws SystemException, PortalException {
		return this.deleteCompetence(this.getCompetence(competenceId));
	}
	
	@Override
	@Indexable(type=IndexableType.DELETE)
	public Competence deleteCompetence (Competence competence) throws SystemException 
	{
		try {
			AssetEntryLocalServiceUtil.deleteEntry(Competence.class.getName(), competence.getPrimaryKey());
			competencePersistence.remove(competence);
		} catch (PortalException e) {
			if(log.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		return competence;
	}
	public String getBGImageURL(long groupId,HttpServletRequest request)
	{
	
		String imageurl = request.getScheme()
			      + "://"
			      + request.getServerName()
			      + ":"
			      + request.getServerPort()+request.getContextPath()+"/default-cert.jpg";
		String baseDir=PropsUtil.get("liferay.home")+"/data/compimages";
		File baseDirFile=new File(baseDir);
		if(baseDirFile.exists())
		{
		File imageFile=new File(baseDirFile.getAbsolutePath()+"/"+Long.toString(groupId)+".png");
		if(imageFile.exists())
		{
			try {
				return imageFile.toURL().toString();
			} catch (MalformedURLException e) {
				if(log.isDebugEnabled())e.printStackTrace();
			}
		}
		imageFile=new File(baseDirFile.getAbsolutePath()+"/"+Long.toString(groupId)+".jpeg");
		if(imageFile.exists())
		{
			try {
				return imageFile.toURL().toString();
			} catch (MalformedURLException e) {
				if(log.isDebugEnabled())e.printStackTrace();
			}
		}
		imageFile=new File(baseDirFile.getAbsolutePath()+"/"+Long.toString(groupId)+".jpg");
		if(imageFile.exists())
		{
			try {
				return imageFile.toURL().toString();
			} catch (MalformedURLException e) {
				if(log.isDebugEnabled())e.printStackTrace();
			}
		}
		imageFile=new File(baseDirFile.getAbsolutePath()+"/"+Long.toString(groupId)+".gif");
		if(imageFile.exists())
		{
			try {
				return imageFile.toURL().toString();
			} catch (MalformedURLException e) {
				if(log.isDebugEnabled())e.printStackTrace();
			}
		}
		}
	    return imageurl;
	}
	
	public String getBGImageURL(Competence competence,HttpServletRequest request)
	{
		return getBGImageURL(competence.getGroupId(), request);
	}
	
	public void setBGImage(byte[] data,long groupId, String name) throws IOException
	{
		String baseDir=PropsUtil.get("liferay.home")+"/data/compimages";
		File baseDirFile=new File(baseDir);
		if(!baseDirFile.exists())
		{
			baseDirFile.mkdir();
		}
		String fileExtension=name.substring(name.lastIndexOf('.'));
		File imageFile=new File(baseDirFile.getAbsolutePath()+"/"+Long.toString(groupId)+fileExtension.toLowerCase());
		/* Cambios de Miguel que borra imagenes */
		 String [] nameOfFiles = baseDirFile.list();
		 String [] aux = null;
		 File fileToDelete = null;
		 for (int i=0; i<nameOfFiles.length; i++){
			 aux = nameOfFiles[i].split("\\.");
			 if(aux[0].equals(Long.toString(groupId))){
				 fileToDelete = new File(baseDirFile.getAbsolutePath()+"/"+Long.toString(groupId)+"."+aux[1]);
				 fileToDelete.delete();
				 fileExtension = null;
			 }
		 }
		/* Fin de cambios de Miguel */
		
		imageFile.createNewFile();
		FileOutputStream fos=new FileOutputStream(imageFile);
		fos.write(data);
		fos.close();
	}
	public long countAll() throws SystemException{
		return competencePersistence.countAll();
	}
	
	public List findByCompanyId(long companyId) throws SystemException{
		return competencePersistence.findByCompanyId(companyId);
	}
}