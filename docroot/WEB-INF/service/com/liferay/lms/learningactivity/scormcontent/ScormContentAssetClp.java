package com.liferay.lms.learningactivity.scormcontent;

import java.io.IOException;
import java.util.Locale;

import javax.portlet.RenderRequest;

import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portlet.asset.model.AssetEntry;

public class ScormContentAssetClp implements ScormContentAsset {
   
	private ClassLoaderProxy clp;
	
	public ScormContentAssetClp(ClassLoaderProxy clp) {
		this.clp = clp;
	}
		
	public java.lang.Object invokeMethod(java.lang.String name,
			java.lang.String[] parameterTypes, java.lang.Object[] arguments)
			throws java.lang.Throwable {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean acceptsSelectSCO() throws DocumentException, IOException,
			PortalException, SystemException {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("acceptsSelectSCO", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Boolean)returnObj).booleanValue();
	}

	@Override
	public String getManifestURL(RenderRequest renderRequest,AssetEntry entry)
			throws DocumentException, IOException, PortalException,
			SystemException {
		Object returnObj = null;

		try {
			MethodKey translateMethod = new MethodKey(clp.getClassName(), "getManifestURL", RenderRequest.class, AssetEntry.class); 
			returnObj = clp.invoke(new MethodHandler(translateMethod, renderRequest, entry));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}

	@Override
	public String getManifestURL(String scoshow, RenderRequest renderRequest,AssetEntry entry)
			throws DocumentException, IOException, PortalException,
			SystemException {
		Object returnObj = null;

		try {
			MethodKey translateMethod = new MethodKey(clp.getClassName(), "getManifestURL",String.class, RenderRequest.class, AssetEntry.class); 
			returnObj = clp.invoke(new MethodHandler(translateMethod,scoshow, renderRequest, entry));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}

	@Override
	public Document getManifest(RenderRequest renderRequest,AssetEntry entry)
			throws DocumentException, IOException, PortalException,
			SystemException {
		Object returnObj = null;

		try {
			MethodKey translateMethod = new MethodKey(clp.getClassName(), "getManifest", RenderRequest.class, AssetEntry.class); 
			returnObj = clp.invoke(new MethodHandler(translateMethod, renderRequest, entry));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Document)returnObj);
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				Object returnObj = null;

				try {
					returnObj = clp.invoke("getClassName", new Object[] {});
				}
				catch (Throwable t) {
					t = ClpSerializer.translateThrowable(t);

					if (t instanceof RuntimeException) {
						throw (RuntimeException)t;
					}
					else {
						throw new RuntimeException(t.getClass().getName() +
							" is not a valid exception");
					}
				}

				return ((String)returnObj);
	}

}
