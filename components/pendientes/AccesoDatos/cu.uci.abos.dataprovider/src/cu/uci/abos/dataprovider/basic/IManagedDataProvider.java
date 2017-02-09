package cu.uci.abos.dataprovider.basic;

import java.io.Serializable;
import java.util.Collection;

import cu.uci.abos.dataprovider.IDataProvider;
import cu.uci.abos.dataprovider.exception.DataProviderException;


/**
 * Servicio a implementar por los proveedores de datos manejados por el sistema.
 * 
 * @author Leandro Tabares Martin
 * @version 0.0.1
 * @since 0.0.1
 * */
public interface IManagedDataProvider extends IDataProvider {

	/**
	 * Para hacer persistente una entidad dada.
	 * 
	 * @param entity
	 *            Objeto entidad que se desea persistir.
	 * @throws DataBaseException
	 * @since 0.0.1
	 * */
	public <T extends Serializable> boolean save(T entity)
			throws DataProviderException;

	/**
	 * Para actualizar una entidad persistente.
	 * 
	 * @param entity
	 *            Entidad o Identificador de entidad que se desea actualizar.
	 * @throws DataBaseException
	 * @since 0.0.1
	 * */
	public <T extends Serializable> boolean update(T entity)
			throws DataProviderException;

	/**
	 * Permite eliminar una entidad persistente.
	 * 
	 * @param entity
	 *            Entidad o Identificador de entidad que se desea eliminar.
	 * @throws DataBaseException
	 * @since 0.0.1
	 * */
	public <T extends Serializable> boolean erase(T entity)
			throws DataProviderException;

	/**
	 * Permite eliminar una colecci�n de entidades persistentes.
	 * 
	 * @param list
	 *            Colecci�n de entidades o identificadores de las entidades que
	 *            se desean eliminar.
	 * @throws DataBaseException
	 * @since 0.0.1
	 * */
	public <T extends Serializable> boolean erase(Collection<T> list)
			throws DataProviderException;

	/**
	 * Permite contar la cantidad de objetos de un tipo previamente almacenados.
	 * 
	 * @param type
	 *            Tipo (ClassName) de los objetos que se desean contar.
	 * @param params
	 *            Par�metros necesarios para la ejecuci�n del conteo.
	 * @since 0.0.1
	 * */
	public <T> int count(Class<T> type, Object... params)
			throws DataProviderException;

}
