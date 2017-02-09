package cu.uci.abos.dataprovider.exception;

/**
 * Excepción base para todas las generadas por los data providers
 * 
 * @author Leandro Tabares Martin
 * @version 0.0.1
 * @since 0.0.1
 */
@SuppressWarnings("serial")
public class DataProviderException extends Exception {

	public DataProviderException() { super(); }

	public DataProviderException(String mensaje) { super(mensaje); }

	public DataProviderException(Throwable excepcion, String mensaje) { super(mensaje, excepcion); }
	
}
