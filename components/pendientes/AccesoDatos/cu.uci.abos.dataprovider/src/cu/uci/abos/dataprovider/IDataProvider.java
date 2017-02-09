package cu.uci.abos.dataprovider;

import java.util.List;

import cu.uci.abos.dataprovider.exception.DataProviderException;


/**
 * Servicio gen�rico que sirve como base para los distintos proveedores de datos.
 * 
 * @author Leandro Tabares Martin
 * @version 0.0.1
 * @since 0.0.1
 * */
public interface IDataProvider {

	/**
	 * Para resolver la cadena de conexi�n a una BD espec�fica.
	 * 
	 * @throws DataProviderException
	 * @since 0.0.1
	 * */
	public Object connect() throws DataProviderException;

	/**
	 * Para cargar una colecci�n de entidades del proveedor de datos.
	 * 
	 * @param params
	 * 			Par�metros necesarios para la ejecuci�n de la consulta.
	 * @since 0.0.1
	 * */
	public <T> List<T> get(Object... params) throws DataProviderException;
	
}
