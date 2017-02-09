/*
 * @(#)IEntryPointService.java 1.0.0 28/01/2015 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.api.ui;

import java.util.Map;
import org.eclipse.rap.rwt.application.EntryPoint;

/**
 * This interface represents an <code>EntryPoint</code> service that will be
 * injected in the platform to avoid incorporated via code
 * 
 * @author Jose Rolando Lafaurie Olivaress
 * @version 1.0.0 28/01/2015
 * 
 * 
 */
public interface EntryPointService {
	/**
	 * 
	 * @return
	 */
	public String getPath();

	/**
	 * 
	 * @return
	 */
	public Class<? extends EntryPoint> getEntryPointClass();

	/**
	 * 
	 * @return
	 */
	public Map<String, String> getProperties();
}
