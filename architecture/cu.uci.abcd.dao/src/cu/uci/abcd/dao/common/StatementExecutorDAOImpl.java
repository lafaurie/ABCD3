package cu.uci.abcd.dao.common;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class StatementExecutorDAOImpl implements StatementExecutorDAO {

	private EntityManager entityManager;

	@Override
	public List<?> findALL(String statement, Object... params) {
		return createQuery(statement, params).getResultList();
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public Object findOne(String statement, Object... params) {
		return createQuery(statement, params).getSingleResult();
	}

	@Override
	public List<?> findALL(String statement) {
		return createQuery(statement).getResultList();
	}

	@Override
	public Object findOne(String statement) {
		return createQuery(statement).getSingleResult();
	}

	private Query createQuery(String statement, Object... params) {
		Query query = createQuery(statement);

		if (params != null) {
			int i = 0;
			while (i < params.length) {
				query.setParameter(i + 1, params[i]);
				i++;
			}
		}
		return query;
	}

	private Query createQuery(String statement) {
		return entityManager.createNativeQuery(statement);
	}

	@Override
	public int executeUpdate(String statement) {
		int result =0;
		entityManager.getTransaction().begin();
		result = createQuery(statement).executeUpdate();
		if (entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().commit();
		}
		 return result;
	}

	@Override
	public int executeUpdate(String statement, Object... params) {
		int result =0;
		entityManager.getTransaction().begin();
		result = createQuery(statement,params).executeUpdate();
		if (entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().commit();
		}
		 return result;
	}
}
