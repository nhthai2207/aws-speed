package controllers.exception;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import services.ServletUtilities;

/**
 * Simple servlet for testing. Generates HTML instead of plain text as with the
 * HelloWorld servlet.
 */

@WebServlet("/exception/trade")
public class ExpStackTrace extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServletUtilities.testException();
		} catch (Exception e) {		
			e.printStackTrace();
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>\n" + "<html>\n" + "<head><title>A Test Servlet</title></head>\n" + "<body bgcolor=\"#fdf5e6\">\n"
				+ "<h1>Test</h1>\n" + "<p>Simple servlet for testing.</p>\n" + "</body></html>");
	}
}
