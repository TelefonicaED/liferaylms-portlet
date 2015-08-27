package com.liferay.lms.servlet;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;

import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.SCORMLearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.SCORMContent;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.SCORMContentLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.util.Encryptor;

/**
 * Servlet implementation class SCORMFileServerServlet
 *
 * Part of this code was copied from:
 * net/balusc/webapp/FileServlet.java
 * Hopefully has exactly the same License that Wemooc.
 * 
 *
 *
 * Copyright (C) 2009 BalusC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library.
 * If not, see <http://www.gnu.org/licenses/>.
 * net/balusc/webapp/FileServlet.java
*
* Copyright (C) 2009 BalusC
*
* This program is free software: you can redistribute it and/or modify it under the terms of the
* GNU Lesser General Public License as published by the Free Software Foundation, either version 3
* of the License, or (at your option) any later version.
* 
* This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
* even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* Lesser General Public License for more details.
* 
* You should have received a copy of the GNU Lesser General Public License along with this library.
* If not, see <http://www.gnu.org/licenses/>.
*/

public class SCORMFileServerServlet extends HttpServlet {
	private static final int DEFAULT_BUFFER_SIZE = 10240; // ..bytes = 10KB.
    private static final long DEFAULT_EXPIRE_TIME = 604800000L; // ..ms = 1 week.
    private static final String MULTIPART_BOUNDARY = "MULTIPART_BYTERANGES";
	private static final long serialVersionUID = 1L;

	private String hexStringToStringByAscii(String hexString) {
		byte[] bytes = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length() / 2; i++) {
			String oneHexa = hexString.substring(i * 2, i * 2 + 2);
			bytes[i] = Byte.parseByte(oneHexa, 16);
		}
		try {
			return new String(bytes, "ASCII");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	protected void doHead(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException
	    {
	        // Process request without content.
	        processRequest(request, response, false);
	    }
	/**
	 * Procesa los metodos HTTP GET y POST.<br>
	 * Busca en la ruta que se le ha pedido el comienzo del directorio
	 * "contenidos" y sirve el fichero.
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response, boolean content) throws ServletException,
			java.io.IOException {
		String mime_type;
		String charset;
		String patharchivo;
		String uri;
		
		
		try {
			User user = PortalUtil.getUser(request);
			
			if (user == null) {
				String userId = null;
				String companyId = null;
				Cookie[] cookies = ((HttpServletRequest) request).getCookies();
				if (Validator.isNotNull(cookies)) {
					for (Cookie c : cookies) {
						if ("COMPANY_ID".equals(c.getName())) {
							companyId = c.getValue();
						} else if ("ID".equals(c.getName())) {
							userId = hexStringToStringByAscii(c.getValue());
						}
					}
				}

				if (userId != null && companyId != null) {
					try {
						Company company = CompanyLocalServiceUtil
								.getCompany(Long.parseLong(companyId));
						Key key = company.getKeyObj();

						String userIdPlain = Encryptor.decrypt(key, userId);

						user = UserLocalServiceUtil.getUser(Long
								.valueOf(userIdPlain));

						// Now you can set the liferayUser into a thread local
						// for later use or
						// something like that.

					} catch (Exception pException) {
						throw new RuntimeException(pException);
					}
				}
			}

			String rutaDatos = SCORMContentLocalServiceUtil.getBaseDir();

			// Se comprueba que el usuario tiene permisos para acceder.
			// Damos acceso a todo el mundo al directorio "personalizacion",
			// para permitir mostrar a todos la pantalla de identificacion.
			uri = URLDecoder.decode(request.getRequestURI(), "UTF-8");
			uri = uri.substring(uri.indexOf("scorm/") + "scorm/".length());
			patharchivo = rutaDatos + "/" + uri;

			String[] params = uri.split("/");
			long groupId = GetterUtil.getLong(params[1]);
			String uuid = params[2];
			SCORMContent scormContent = SCORMContentLocalServiceUtil
					.getSCORMContentByUuidAndGroupId(uuid, groupId);
			
			boolean allowed = false;
			if (user == null) {
				user = UserLocalServiceUtil.getDefaultUser(PortalUtil.getDefaultCompanyId());
			}
			PermissionChecker pc = PermissionCheckerFactoryUtil.create(user);
			allowed = pc.hasPermission(groupId,
					SCORMContent.class.getName(), 
					scormContent.getScormId(),
					ActionKeys.VIEW);
			if (!allowed) 
			{
				AssetEntry scormAsset = AssetEntryLocalServiceUtil.getEntry(SCORMContent.class.getName(), scormContent.getPrimaryKey());
				long scormAssetId = scormAsset.getEntryId();
				int typeId = new Long((new SCORMLearningActivityType()).getTypeId()).intValue();
				long[] groupIds = user.getGroupIds();
				for (long gId : groupIds) {
					List<LearningActivity> acts = LearningActivityLocalServiceUtil.getLearningActivitiesOfGroupAndType(gId, typeId);
					for (LearningActivity act : acts) {
						String entryId = LearningActivityLocalServiceUtil.getExtraContentValue(act.getActId(), "assetEntry");
						if (Validator.isNotNull(entryId) && Long.valueOf(entryId) == scormAssetId) 
						{
							allowed = pc.hasPermission(gId, LearningActivity.class.getName(), act.getActId(), ActionKeys.VIEW);
							if (allowed)
							{
								break;
							}
						}
					}
					if (allowed) 
					{
						break;
					}
				}
				
			}
			if (allowed) 
			{

				File archivo = new File(patharchivo);
				

				// Si el archivo existe y no es un directorio se sirve. Si no,
				// no se hace nada.
				if (archivo.exists() && archivo.isFile()) 
				{
					
					// El content type siempre antes del printwriter
					mime_type = MimeTypesUtil.getContentType(archivo);
					charset = "";
					if (archivo.getName().toLowerCase().endsWith(".html")
							|| archivo.getName().toLowerCase().endsWith(".htm")) {
						mime_type = "text/html";
						if (isISO(FileUtils.readFileToString(archivo))) {
							charset = "ISO-8859-1";
						}
					}
					if (archivo.getName().toLowerCase().endsWith(".swf")) {
						mime_type = "application/x-shockwave-flash";
					}
					if (archivo.getName().toLowerCase().endsWith(".mp4")) {
						mime_type = "video/mp4";
					}
					if (archivo.getName().toLowerCase().endsWith(".flv")) {
						mime_type = "video/x-flv";
					}
					response.setContentType(mime_type);
					if (Validator.isNotNull(charset)) {
						response.setCharacterEncoding(charset);
						
					}
					response.addHeader("Content-Type", mime_type + (Validator.isNotNull(charset) ? "; "+ charset : ""));
					/*if (archivo.getName().toLowerCase().endsWith(".swf")
							|| archivo.getName().toLowerCase().endsWith(".flv")) {
						response.addHeader("Content-Length",
								String.valueOf(archivo.length()));
					}
					*/
					if(archivo.getName().toLowerCase().endsWith("imsmanifest.xml"))
					{
						FileInputStream fis = new FileInputStream(patharchivo);
						
						String sco=ParamUtil.get(request, "scoshow", "");
						Document manifest=SAXReaderUtil.read(fis);
						if(sco.length()>0)
						{
							
							
							Element organizatEl=manifest.getRootElement().element("organizations").element("organization");
							Element selectedItem=selectItem(organizatEl,sco);
							if(selectedItem!=null)
							{
								selectedItem.detach();
							    java.util.List<Element> items=organizatEl.elements("item");
								for(Element item:items)
								{
									
									organizatEl.remove(item);
								}
								organizatEl.add(selectedItem);
							}				
						}
						//clean unused resources.
						Element resources=manifest.getRootElement().element("resources");
						java.util.List<Element> theResources=resources.elements("resource");
						Element organizatEl=manifest.getRootElement().element("organizations").element("organization");
						java.util.List<String> identifiers=getIdentifierRefs(organizatEl);
						for(Element resource:theResources)
						{
							String identifier=resource.attributeValue("identifier");
							if(!identifiers.contains(identifier))
							{	
								resources.remove(resource);
							}
						}
						response.getWriter().print(manifest.asXML());
						fis.close();
						return;
							
					}
				
					if(mime_type.startsWith("text")||mime_type.endsWith("javascript")||mime_type.equals("application/xml"))
					{
						
						java.io.OutputStream out = response.getOutputStream();
						FileInputStream fis = new FileInputStream(patharchivo);
						
	
						byte[] buffer = new byte[512];
						int i = 0;
	
						while (fis.available() > 0) 
						{
							i = fis.read(buffer);
							if (i == 512)
								out.write(buffer);
							else
								out.write(buffer, 0, i);
						
		
						}

						fis.close();
						out.flush();
						out.close();
						return;
					}
					//If not manifest
					String fileName = archivo.getName();
			        long length = archivo.length();
			        long lastModified = archivo.lastModified();
			        String eTag = fileName + "_" + length + "_" + lastModified;
			        long expires = System.currentTimeMillis() + DEFAULT_EXPIRE_TIME;
			        String ifNoneMatch = request.getHeader("If-None-Match");
			        if (ifNoneMatch != null && matches(ifNoneMatch, eTag)) {
			            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			            response.setHeader("ETag", eTag); // Required in 304.
			            response.setDateHeader("Expires", expires); // Postpone cache with 1 week.
			            return;
			        }
			        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
			        if (ifNoneMatch == null && ifModifiedSince != -1 && ifModifiedSince + 1000 > lastModified) {
			            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			            response.setHeader("ETag", eTag); // Required in 304.
			            response.setDateHeader("Expires", expires); // Postpone cache with 1 week.
			            return;
			        }
			        
			        // If-Match header should contain "*" or ETag. If not, then return 412.
			        String ifMatch = request.getHeader("If-Match");
			        if (ifMatch != null && !matches(ifMatch, eTag)) {
			            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
			            return;
			        }

			        // If-Unmodified-Since header should be greater than LastModified. If not, then return 412.
			        long ifUnmodifiedSince = request.getDateHeader("If-Unmodified-Since");
			        if (ifUnmodifiedSince != -1 && ifUnmodifiedSince + 1000 <= lastModified) {
			            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
			            return;
			        }


			        // Validate and process range -------------------------------------------------------------

			        // Prepare some variables. The full Range represents the complete file.
			        Range full = new Range(0, length - 1, length);
			        List<Range> ranges = new ArrayList<Range>();

			        // Validate and process Range and If-Range headers.
			        String range = request.getHeader("Range");
			        if (range != null) {

			            // Range header should match format "bytes=n-n,n-n,n-n...". If not, then return 416.
			            if (!range.matches("^bytes=\\d*-\\d*(,\\d*-\\d*)*$")) {
			                response.setHeader("Content-Range", "bytes */" + length); // Required in 416.
			                response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
			                return;
			            }

			            // If-Range header should either match ETag or be greater then LastModified. If not,
			            // then return full file.
			            String ifRange = request.getHeader("If-Range");
			            if (ifRange != null && !ifRange.equals(eTag)) {
			                try {
			                    long ifRangeTime = request.getDateHeader("If-Range"); // Throws IAE if invalid.
			                    if (ifRangeTime != -1 && ifRangeTime + 1000 < lastModified) {
			                        ranges.add(full);
			                    }
			                } catch (IllegalArgumentException ignore) {
			                    ranges.add(full);
			                }
			            }

			            // If any valid If-Range header, then process each part of byte range.
			            if (ranges.isEmpty()) {
			                for (String part : range.substring(6).split(",")) {
			                    // Assuming a file with length of 100, the following examples returns bytes at:
			                    // 50-80 (50 to 80), 40- (40 to length=100), -20 (length-20=80 to length=100).
			                    long start = sublong(part, 0, part.indexOf("-"));
			                    long end = sublong(part, part.indexOf("-") + 1, part.length());

			                    if (start == -1) {
			                        start = length - end;
			                        end = length - 1;
			                    } else if (end == -1 || end > length - 1) {
			                        end = length - 1;
			                    }

			                    // Check if Range is syntactically valid. If not, then return 416.
			                    if (start > end) {
			                        response.setHeader("Content-Range", "bytes */" + length); // Required in 416.
			                        response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
			                        return;
			                    }

			                    // Add range.
			                    ranges.add(new Range(start, end, length));
			                }
			            }
			        }
			        boolean acceptsGzip = false;
			        String disposition = "inline";
			        
			        if (mime_type.startsWith("text")) {
			            //String acceptEncoding = request.getHeader("Accept-Encoding");
			           // acceptsGzip = acceptEncoding != null && accepts(acceptEncoding, "gzip");
			           // mime_type += ";charset=UTF-8";
			        } 
				
			        // Else, expect for images, determine content disposition. If content type is supported by
			        // the browser, then set to inline, else attachment which will pop a 'save as' dialogue.
			        else if (!mime_type.startsWith("image")) {
			            String accept = request.getHeader("Accept");
			            disposition = accept != null && accepts(accept, mime_type) ? "inline" : "attachment";
			        }

			        // Initialize response.
			        response.reset();
			        response.setBufferSize(DEFAULT_BUFFER_SIZE);
			        response.setHeader("Content-Disposition", disposition + ";filename=\"" + fileName + "\"");
			        response.setHeader("Accept-Ranges", "bytes");
			        response.setHeader("ETag", eTag);
			        response.setDateHeader("Last-Modified", lastModified);
			        response.setDateHeader("Expires", expires);

			     // Send requested file (part(s)) to client ------------------------------------------------

			        // Prepare streams.
			        RandomAccessFile input = null;
			        OutputStream output = null;

			        try {
			            // Open streams.
			            input = new RandomAccessFile(archivo, "r");
			            output = response.getOutputStream();

			            if (ranges.isEmpty() || ranges.get(0) == full) {

			                // Return full file.
			                Range r = full;
			                response.setContentType(mime_type);
			                response.setHeader("Content-Range", "bytes " + r.start + "-" + r.end + "/" + r.total);

			                if (content) {
			                  
			                        // Content length is not directly predictable in case of GZIP.
			                        // So only add it if there is no means of GZIP, else browser will hang.
			                   response.setHeader("Content-Length", String.valueOf(r.length));
			                    

			                    // Copy full range.
			                    copy(input, output, r.start, r.length);
			                }

			            } else if (ranges.size() == 1) {

			                // Return single part of file.
			                Range r = ranges.get(0);
			                response.setContentType(mime_type);
			                response.setHeader("Content-Range", "bytes " + r.start + "-" + r.end + "/" + r.total);
			                response.setHeader("Content-Length", String.valueOf(r.length));
			                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206.

			                if (content) {
			                    // Copy single part range.
			                    copy(input, output, r.start, r.length);
			                }

			            } else {

			                // Return multiple parts of file.
			                response.setContentType("multipart/byteranges; boundary=" + MULTIPART_BOUNDARY);
			                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206.

			                if (content) {
			                    // Cast back to ServletOutputStream to get the easy println methods.
			                    ServletOutputStream sos = (ServletOutputStream) output;

			                    // Copy multi part range.
			                    for (Range r : ranges) {
			                        // Add multipart boundary and header fields for every range.
			                        sos.println();
			                        sos.println("--" + MULTIPART_BOUNDARY);
			                        sos.println("Content-Type: " + mime_type);
			                        sos.println("Content-Range: bytes " + r.start + "-" + r.end + "/" + r.total);

			                        // Copy single part range of multi part range.
			                        copy(input, output, r.start, r.length);
			                    }

			                    // End with multipart boundary.
			                    sos.println();
			                    sos.println("--" + MULTIPART_BOUNDARY + "--");
			                }
			            }
			        } 
			        finally 
			        {
			            // Gently close streams.
			            close(output);
			            close(input);
			        }
				} 
				else {
					//java.io.OutputStream out = response.getOutputStream();
					response.sendError(404);
					//out.write(uri.getBytes());
				}
			} 
			else 
			{
				response.sendError(401);
			}
		}
		catch (Exception e) 
		{
			System.out
					.println("Error en el processRequest() de ServidorArchivos: "
							+ e.getMessage());
		}
	}
	/**
     * Returns true if the given accept header accepts the given value.
     * @param acceptHeader The accept header.
     * @param toAccept The value to be accepted.
     * @return True if the given accept header accepts the given value.
     */
    private static boolean accepts(String acceptHeader, String toAccept) {
        String[] acceptValues = acceptHeader.split("\\s*(,|;)\\s*");
        Arrays.sort(acceptValues);
        return Arrays.binarySearch(acceptValues, toAccept) > -1
            || Arrays.binarySearch(acceptValues, toAccept.replaceAll("/.*$", "/*")) > -1
            || Arrays.binarySearch(acceptValues, "*/*") > -1;
    }

    /**
     * Returns true if the given match header matches the given value.
     * @param matchHeader The match header.
     * @param toMatch The value to be matched.
     * @return True if the given match header matches the given value.
     */
    private static boolean matches(String matchHeader, String toMatch) {
        String[] matchValues = matchHeader.split("\\s*,\\s*");
        Arrays.sort(matchValues);
        return Arrays.binarySearch(matchValues, toMatch) > -1
            || Arrays.binarySearch(matchValues, "*") > -1;
    }

    /**
     * Returns a substring of the given string value from the given begin index to the given end
     * index as a long. If the substring is empty, then -1 will be returned
     * @param value The string value to return a substring as long for.
     * @param beginIndex The begin index of the substring to be returned as long.
     * @param endIndex The end index of the substring to be returned as long.
     * @return A substring of the given string value as long or -1 if substring is empty.
     */
    private static long sublong(String value, int beginIndex, int endIndex) {
        String substring = value.substring(beginIndex, endIndex);
        return (substring.length() > 0) ? Long.parseLong(substring) : -1;
    }

    /**
     * Copy the given byte range of the given input to the given output.
     * @param input The input to copy the given range to the given output for.
     * @param output The output to copy the given range from the given input for.
     * @param start Start of the byte range.
     * @param length Length of the byte range.
     * @throws IOException If something fails at I/O level.
     */
    private static void copy(RandomAccessFile input, OutputStream output, long start, long length)
        throws IOException
    {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int read;

        if (input.length() == length) {
            // Write full range.
            while ((read = input.read(buffer)) > 0) {
                output.write(buffer, 0, read);
            }
        } else {
            // Write partial range.
            input.seek(start);
            long toRead = length;

            while ((read = input.read(buffer)) > 0) {
                if ((toRead -= read) > 0) {
                    output.write(buffer, 0, read);
                } else {
                    output.write(buffer, 0, (int) toRead + read);
                    break;
                }
            }
        }
    }

    /**
     * Close the given resource.
     * @param resource The resource to be closed.
     */
    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException ignore) {
                // Ignore IOException. If you want to handle this anyway, it might be useful to know
                // that this will generally only be thrown when the client aborted the request.
            }
        }
    }

    // Inner classes ------------------------------------------------------------------------------

    /**
     * This class represents a byte range.
     */
    protected class Range {
        long start;
        long end;
        long length;
        long total;

        /**
         * Construct a byte range.
         * @param start Start of the byte range.
         * @param end End of the byte range.
         * @param total Total length of the byte source.
         */
        public Range(long start, long end, long total) {
            this.start = start;
            this.end = end;
            this.length = end - start + 1;
            this.total = total;
        }

    }
	
	private List<String> getIdentifierRefs(Element organizatEl) {
		java.util.List<String> ident=new java.util.ArrayList<String>();
		// TODO Auto-generated method stub
		java.util.List<Element> items=organizatEl.elements("item");
		for(Element item:items)
		{
			String identi=item.attributeValue("identifierref");
			ident.add(identi);
			java.util.List<String> subident=getIdentifierRefs(item);
			if(subident!=null && subident.size()>0)
			{
				ident.addAll(subident);
			}
			
		}
		return ident;
	}

	private Element selectItem(Element organizatEl, String sco) 
	{
		java.util.List<Element> items=organizatEl.elements("item");
		for(Element item:items)
		{
			if(item.attributeValue("identifier").equals(sco))
			{
				return item;
			}
			Element retorno=selectItem(item,sco);
			if(retorno!=null)
			{
				return retorno;
			}
		}
		return null;
	}

	private boolean isISO(String testString) {
		if (Validator.isNotNull(testString)) {
			return testString.substring(0, (testString.length() >= 1024 ? 1024 : testString.length())).contains("ISO-8859-1");
		}
		return false;
	}

	/**
	 * Procesa el metodo HTTP GET.
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			java.io.IOException {
		processRequest(request, response,true);
	}

	/**
	 * Procesa el metodo HTTP POST.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			java.io.IOException {
		processRequest(request, response,true);
	}
}
