package cu.uci.abcd.dao.statistic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.statistic.Variable;

public interface VariableDAO extends PagingAndSortingRepository<Variable, Long> , JpaSpecificationExecutor<Variable>{

	public List<Variable> findById(Long id);

	public List<Variable> findByField(String field);

	public List<Variable> findByHeader(String header);
	
	public List<Variable> findByOutputFormat(String outputFormat);
}
