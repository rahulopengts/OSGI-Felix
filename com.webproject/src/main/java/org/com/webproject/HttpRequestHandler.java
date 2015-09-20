package org.com.webproject;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class HttpRequestHandler extends HttpServlet {

	@Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    //resp.getWriter().write("Hello World");
		System.out.println("\n YESDDD");
		String temlate	=	getSnippet("createprofile");
		printDebugMessage("doGet",temlate);
	    req.getRequestDispatcher("web/index.html").forward(req, resp);
	    //resp.sendRedirect("/web/index.html");
	  }
	

	public static final String	TEMPLATE_LOCATION	=	"templates/";
	public static final String	TEMPLATE_EXT	=	".html";
	
	protected synchronized String getSnippet(String elementType){
		elementType = elementType.toLowerCase();
		String template	=	null;

			String snippetLocation = TEMPLATE_LOCATION + elementType + TEMPLATE_EXT;
			URL entry = Activator.getContext().getBundle().getEntry(snippetLocation);
			printDebugMessage(this.toString(), "URL Entry "+entry);
			
			if(entry!=null) {
				try {
					template = IOUtils.toString(entry.openStream());

				} catch (IOException e) {
					e.printStackTrace();
					//logger.warn("Cannot load snippet for element type '{}'", elementType, e);
				}
			} 
		return template;
	}

	public void printDebugMessage(String tag,String message){
		System.out.println("\n "+tag+" :: "+message);
	}
}
