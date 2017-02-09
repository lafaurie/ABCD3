package interno.interfaces;

import java.util.List;

import publico.excepciones.DataBaseException;

/**
 * Interfaz de la que heredan las interfaces que definen las funcionalidades de los DataProviders.
 * @author Leandro Tabares Martin
 * @version 1.0.0
 * @since 1.0.0
 * */
public interface DataProvider {
	
	/**
	 * Permite obtener la conexion a la base de datos especifica
	 * @throws DataBaseException
	 * @since 1.0.0
	 * 
	 * */
	public Object getConexion() throws DataBaseException;
	
	/**
	 * Permite cargar una coleccion de entidades previamente almacenadas
	 * @param parametros Parametros necesarios para la ejecucion de la consulta
	 * @since 1.0.0
	 * 
	 * */
	public <T> List<T> obtener(Object... parametros);
}
