package org.com.webproject;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpContext {
	 /** Returns the mime type of the specified resource */
	  String getMimeType(java.lang.String name);

	  /** Returns the URL to retrieve the specified resource */
	  URL getResource(java.lang.String name);

	  /** Manages security for the specified request */
	  boolean handleSecurity(HttpServletRequest request, HttpServletResponse response); 
}
