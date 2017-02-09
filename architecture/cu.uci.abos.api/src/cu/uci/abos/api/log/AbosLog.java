/*
 * @(#)IAbosLog.java 1.0.0 27/012015 
 * Copyright (c) 2015 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.api.log;

/**
 * This interface manage logs in the platform
 * 
 * @author Jose Rolando Lafaurie Olivares
 * @version 1.0.0 27/01/2015
 */
public interface AbosLog {
	/**
	 * Generate a log in the platform
	 * 
	 * @param level
	 *            Represents the level of the log {@link AbosLogConstant}
	 * @param message
	 *            Message of the log
	 * @param throwable
	 *            Type error generated if exist
	 */
	public void log(int level, String message, Throwable throwable);
}
