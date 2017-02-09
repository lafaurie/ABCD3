/*
 * @(#)AbosImageUtil.java 1.0.0 15/10/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author Jose Rolando Lafaurie Olivares
 * @version 1.0.0 15/10/2014
 * 
 */
public class AbosImageUtil {

	private AbosImageUtil() {
		// Prevent instantiation
	}

	public static Image loadImage(Class<?> classType, Device device, String path, boolean local) {
		InputStream inputStream = null;
		if (local) {
			ClassLoader classLoader = (classType == null) ? AbosImageUtil.class.getClassLoader() : classType.getClassLoader();
			inputStream = classLoader.getResourceAsStream(path);
		} else {
			try {
				inputStream = new FileInputStream(path);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		Image resultImage = null;
		if (inputStream != null) {
			try {
				resultImage = new Image(device, inputStream);

			} finally {
				try {
					inputStream.close();
				} catch (Exception e) {
				}
			}
		}
		return resultImage;
	}
}
