package cu.uci.abos.dataprovider.hibernateogm.publics;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

public interface IHibernateOgm {

	public Session getCurrentSession();

	public void salvar(Object entidad) throws Exception;

	public void eliminar(Object entidad) throws Exception;

	public void actualizar(Object entidad) throws Exception;

	public <T extends Serializable> T obtenerPorId(Class<T> claseEntidad,
			Serializable id) throws Exception;
	
	public <T extends Serializable> List<T> obtenerTodos(Class<T> claseEntidad) throws Exception;
	
	public <T extends Serializable> List<T> obtener(Class<T> claseEntidad, String queryLucene) throws Exception;

	

}
