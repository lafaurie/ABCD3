package cu.uci.abos.dataprovider.hibernateogm.internals;

import org.hibernate.SessionFactory;
import org.hibernate.ogm.cfg.OgmConfiguration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
			
			OgmConfiguration cfgogm = new OgmConfiguration();		
			
			cfgogm.configure();
			sessionFactory = cfgogm.buildSessionFactory();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}