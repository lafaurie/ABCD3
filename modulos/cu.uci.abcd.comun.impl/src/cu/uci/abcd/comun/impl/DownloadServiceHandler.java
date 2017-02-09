package cu.uci.abcd.comun.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.rap.rwt.service.ServiceHandler;

public class DownloadServiceHandler implements ServiceHandler {

	@Override
	public void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		// Which file to download?
		String fileName = arg0.getParameter("filename");
		// Get the file content
		byte[] download = MyDataStore.getData();
		// Send the file in the response
		arg1.setContentType("application/pdf");
		arg1.setContentLength(download.length);
		String contentDisposition = "attachment; filename=\"" + fileName + "\"";
		arg1.setHeader("Content-Disposition", contentDisposition);
		arg1.getOutputStream().write(download);
	}

}
