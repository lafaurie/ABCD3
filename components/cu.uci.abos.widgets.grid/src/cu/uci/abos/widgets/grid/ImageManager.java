package cu.uci.abos.widgets.grid;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class ImageManager {

	private Display display;

	public ImageManager(Display display) {
		this.display = display;
	}

	public Image loadImage(String name) {
		InputStream stream = getClass().getClassLoader().getResourceAsStream("resources/" + name);
		Image result = new Image(display, stream);
		try {
			stream.close();
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		return result;
	}
}
