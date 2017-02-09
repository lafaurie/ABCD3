package cu.uci.abos.dataprovider.hibernateogm.internals;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jisis.driver.connection.ConectionJisis;
import jisis.driver.operation.db.DatabaseJisis;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.ogm.datastore.jisis.impl.configuration.JisisConfiguration;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.service.spi.Configurable;
import org.unesco.jisis.corelib.common.IConnection;
import org.unesco.jisis.corelib.common.IDatabase;
import org.unesco.jisis.corelib.exceptions.DbException;

import cu.uci.abos.dataprovider.hibernateogm.publics.IHibernateOgm;

public class HibernateOgmImpl implements IHibernateOgm, Configurable {

	private static String ENTIDAD_NULA = "La entidad no puede ser nula.";
	private static String CLASE_NULA = "La clase de la entidad no puede ser nula.";
	private static String IDENTIFICADOR_NULO = "El identificador no puede ser nulo.";

	private SessionFactory sessionFactory;
	
	private final JisisConfiguration jisisConfig = new JisisConfiguration();
	
	
	@Override
	public void salvar(Object entidad) throws Exception {
		if (entidad == null)
			throw new Exception(ENTIDAD_NULA);
		else
			getCurrentSession().save(entidad);
	}

	@Override
	public void eliminar(Object entidad) throws Exception {
		if (entidad == null)
			throw new Exception(ENTIDAD_NULA);
		getCurrentSession().delete(entidad);
	}

	@Override
	public void actualizar(Object entidad) throws Exception {
		if (entidad == null)
			throw new Exception(ENTIDAD_NULA);
		getCurrentSession().update(entidad);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Serializable> T obtenerPorId(Class<T> claseEntidad, Serializable valor) throws Exception {
		if (claseEntidad == null)
			throw new Exception(CLASE_NULA);
		if (valor == null)
			throw new Exception(IDENTIFICADOR_NULO);
		return (T) getCurrentSession().get(claseEntidad, valor);
	}

	@Override
	public Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		if(sessionFactory == null){
			sessionFactory = HibernateUtil.getSessionFactory();
		}
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}	

	@Override
	public <T extends Serializable> List<T> obtenerTodos(Class<T> claseEntidad) throws Exception {
		List<T> listResult = new ArrayList<T>();	
		
		try {
			IConnection jisisConnection = null;
			jisisConnection = ConectionJisis.conectJisis(this.jisisConfig.getJisisHost(), this.jisisConfig.getJisisPort(), this.jisisConfig.getJisisUsername(), this.jisisConfig.getJisisPassword());
			DatabaseJisis.iniDatabase(jisisConnection, this.jisisConfig.getJisisHome());
			
			 			
			ClassMetadata cm = sessionFactory.getClassMetadata(claseEntidad);
			
			AbstractEntityPersister aep = (AbstractEntityPersister) cm;
			String tableName = aep.getTableName();
			
			IDatabase database = DatabaseJisis.getTableDB(tableName, true);	
			long[] mfns = database.searchLucene("_1:[* TO *]");
			
			
			for (int i = 0; i < mfns.length; i++) {
				listResult.add(this.obtenerPorId(claseEntidad, mfns[i]));
			}
		} catch (DbException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listResult;
	}

	@Override
	public <T extends Serializable> List<T> obtener(Class<T> claseEntidad, String queryLucene) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void configure(@SuppressWarnings("rawtypes") Map configurationMap) {
		this.jisisConfig.initialize(configurationMap);		
	}

	

	

}
