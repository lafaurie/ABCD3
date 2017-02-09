package cu.uci.abos.platform;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.application.ExceptionHandler;
import org.eclipse.rap.rwt.client.WebClient;
import org.eclipse.rap.rwt.service.ResourceLoader;
import org.eclipse.swt.widgets.Display;

import cu.uci.abos.core.service.ReportServiceHandler;
import cu.uci.abos.core.ui.ConfiguratorTracker;
import cu.uci.abos.core.util.MessageDialogUtil;

public class ApplicationPlatform implements ApplicationConfiguration {
	static final String EXAMPLE_UI = "abcd";

	@Override
	public void configure(Application application) {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(WebClient.PAGE_TITLE, "ABCD");
		// load menu
		application.addResource("menu", new ResourceLoader() {
			// @Override
			public InputStream getResourceAsStream(String resourceName) throws IOException {
				InputStream is = new FileInputStream("abcdconfig/menu/menu.xml");
				return is;
			}
		});
		// load css
		application.addStyleSheet(RWT.DEFAULT_THEME_ID, "abcdconfig/resources/theme.css", new ResourceLoader() {
			@Override
			public InputStream getResourceAsStream(String arg0) throws IOException {
				InputStream is = null;
				if (arg0.equalsIgnoreCase("abcdconfig/resources/theme.css")) {
					is = new FileInputStream(arg0);
				} else {
					is = new FileInputStream("abcdconfig/resources/" + arg0);
				}
				return is;
			}
		});
		// load images
		loadImages(application);
		// add entry point
		application.addEntryPoint("/", MainEntryPoint.class, properties);
		// add service handler

		application.addServiceHandler("reportService", new ReportServiceHandler());

		application.setExceptionHandler(new ExceptionHandler() {
			@Override
			public void handleException(Throwable exception) {
				MessageDialogUtil.openError(Display.getCurrent().getActiveShell(), "Error", exception.getLocalizedMessage(), null);
			}
		});
		new ConfiguratorTracker(application, this);

	}

	private void loadImages(Application application) {
		Properties abcdProperties = new Properties();
		try {
			abcdProperties.load(new FileInputStream("abcdconfig/abcd-resources.properties"));
			String imageString = (String) abcdProperties.get("abcd.image");
			String[] images = imageString.split(",");
			for (int i = 0; i < images.length; i++) {
				// load image configuration
				String[] auxConfig = images[i].split(";");
				final String imageName = auxConfig[0].trim();
				String appIdentifier = auxConfig[1].split("=")[1].trim();
				application.addResource(appIdentifier, new ResourceLoader() {
					@Override
					public InputStream getResourceAsStream(String arg0) throws IOException {
						return loadImage(imageName);
					}
				});
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private InputStream loadImage(String imageName) {
		try {
			return new FileInputStream("abcdconfig/resources/" + imageName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
