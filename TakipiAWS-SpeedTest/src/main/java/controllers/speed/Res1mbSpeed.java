package controllers.speed; // Always use packages. Never use default package.

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

@WebServlet("/speed/res1mb")
public class Res1mbSpeed extends HttpServlet {
	private static byte[] get5MBData() throws Exception
	{
		InputStream in = new BufferedInputStream(new URL("http",
				"download.thinkbroadband.com", "/5MB.zip").openStream());
		return IOUtils.toByteArray(in);
	}
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		out.println("OK");
	}
}
