package cu.uci.abcd.dao.common;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface StatementExecutorDAO {

	public List<?> findALL(String statement, Object ... params);
	
	public Object findOne(String statement, Object ... params);
	
	public List<?> findALL(String statement);
	
	public Object findOne(String statement);
	
	public int executeUpdate(String statement);
	
	public int executeUpdate(String statement, Object ... params);
	
	
}
