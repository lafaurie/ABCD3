package cu.uci.abos.core.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.UrlLauncher;

public class URLUtil {
	/**
	 * 
	 * @param path
	 * @return
	 */
	public static String createUrl(String path) {
		StringBuffer url = new StringBuffer();
		url.append(RWT.getRequest().getContextPath());
		url.append(path);
		return RWT.getResponse().encodeURL(url.toString());
	}

	private static String createDownloadUrl(ReportType reportType, String fileName) {
		String contentType = "";
		switch (reportType) {
		case PDF:
			contentType = "application/pdf";
			break;
		case TXT:
			contentType = "text/plain";
			break;

		case XML:
			contentType = "application/xml";
			break;

		case MODS:
			contentType = "application/mods+xml";
			break;

		case HTML:
			contentType = "text/html";
			break;

		case MARC:
			contentType = "application/marc";
			break;

		default:
			contentType = "application/xls";
			break;
		}
		StringBuilder url = new StringBuilder();
		url.append(RWT.getServiceManager().getServiceHandlerUrl("reportService"));
		url.append('&').append("filename").append('=').append(fileName + "." + reportType.getExtension());
		url.append('&').append("contenttype").append('=').append(contentType);
		return url.toString();
	}

	public static void generateDownloadReport(ReportType reportType, OutputStream dataOutputStream) {
		RWT.getUISession().getHttpSession().setAttribute("report-data", dataOutputStream);
		UrlLauncher launcher = RWT.getClient().getService(UrlLauncher.class);
		launcher.openURL(createDownloadUrl(reportType, "file"));
	}

	public static void download(String path, String fileName,  ReportType type) {
		if (fileName != null && !fileName.equals("")) {
			try {
				FileInputStream in = new FileInputStream(path + fileName);

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				try {
					try {
						byte[] buf = new byte[512];
						int read;

						while ((read = in.read(buf)) > 0)
							outputStream.write(buf, 0, read);
					} finally {
						outputStream.close();
					}
				} finally {
					in.close();
				}

				RWT.getUISession().getHttpSession().setAttribute("report-data", outputStream);
				UrlLauncher launcher = RWT.getClient().getService(UrlLauncher.class);
				
				launcher.openURL(createDownloadUrl(type, fileName));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		
	}
	
	public static void download(String path, String fileName, String archiveName, ReportType type) {
		if (fileName != null && !fileName.equals("")) {
			try {
				FileInputStream in = new FileInputStream(path + fileName);

				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				try {
					try {
						byte[] buf = new byte[512];
						int read;

						while ((read = in.read(buf)) > 0)
							outputStream.write(buf, 0, read);
					} finally {
						outputStream.close();
					}
				} finally {
					in.close();
				}

				RWT.getUISession().getHttpSession().setAttribute("report-data", outputStream);
				UrlLauncher launcher = RWT.getClient().getService(UrlLauncher.class);
				
				launcher.openURL(createDownloadUrl(type, archiveName));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		
	}

}
