package cu.uci.abos.core.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.ServiceHandler;

public class ReportServiceHandler implements ServiceHandler {

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// Which file to download?
		String fileName = request.getParameter("filename");
		String extension = request.getParameter("contenttype");

		// Get the file content
		byte[] download = ((ByteArrayOutputStream) RWT.getUISession().getHttpSession().getAttribute("report-data")).toByteArray();
		// Send the file in the response
		response.setContentType(extension);
		response.setContentLength(download.length);
		String contentDisposition = "attachment; filename=\"" + fileName + "\"";
		response.setHeader("Content-Disposition", contentDisposition);
		response.getOutputStream().write(download);
		request.setAttribute("report-data", null);
	}

}
