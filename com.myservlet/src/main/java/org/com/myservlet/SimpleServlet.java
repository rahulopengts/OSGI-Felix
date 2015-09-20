package org.com.myservlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public SimpleServlet() {
		System.out.println("Servlet instantiated.");
	}
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.getWriter().write("Hello world!");
		System.out.println("Hello world!");
	}
	
		
	public void init() throws ServletException {
		System.out.println("Servlet initialized.");
	}

}