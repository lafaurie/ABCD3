package publico.interfaces;

import interno.interfaces.DataProvider;

import java.io.Serializable;
import java.util.Collection;
import publico.excepciones.DataBaseException;

/**
 * Interfaz que debe ser implementada para los proveedores de datos administrados por el sistema.
 * @author Leandro Tabares Martin
 * @version 1.0.0
 * @since 1.0.0
 * */

public interface ManagedDataProvider extends DataProvider{
	
	/**
	 * Permite salvar una entidad persistente
	 * @param entidad Entidad que se desea persistir
	 * @throws DataBaseException
	 * @since 1.0.0
	 * 
	 * */
	public <T extends Serializable> void salvar(T entidad) throws DataBaseException;
	
	/**
	 * Permite actualizar una entidad persistente
	 * @param entidad Entidad que se desea actualizar
	 * @throws DataBaseException
	 * @since 1.0.0
	 * 
	 * */
	public <T extends Serializable> void actualizar(T entidad) throws DataBaseException;
	
	/**
	 * Permite eliminar una entidad previamente almacenada
	 * @param entidad Entidad que se desea eliminar
	 * @throws DataBaseException
	 * @since 1.0.0
	 * 
	 * */
	public <T extends Serializable> void eliminar(T entidad) throws DataBaseException;
	
	/**
	 * Permite eliminar una coleccion de entidades previamente almacenadas
	 * @param coleccion Coleccion de entidades que se desean eliminar
	 * @throws DataBaseException
	 * @since 1.0.0
	 * 
	 * */
	public <T extends Serializable> void eliminar(Collection<T> coleccion) throws DataBaseException;
	
	/**
	 * Permite contar la cantidad de objetos de un tipo previamente almacenados
	 * @param clase Tipo de los objetos que se desean contar
	 * @param parametros Parametros necesarios para la ejecucion de la consulta
	 * @since 1.0.0
	 * 
	 * */
	public <T> int contarObjetos(Class<T> clase, Object... parametros);
}
