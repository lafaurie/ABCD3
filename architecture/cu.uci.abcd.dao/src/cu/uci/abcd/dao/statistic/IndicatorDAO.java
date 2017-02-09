package cu.uci.abcd.dao.statistic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.statistic.Indicator;

public interface IndicatorDAO extends PagingAndSortingRepository<Indicator, Long>, JpaSpecificationExecutor<Indicator> {

	public List<Indicator> findById(Long id);

	public List<Indicator> findByNameIndicator(String name);

	public List<Indicator> findByIndicatorId(String indicatorId);

	public List<Indicator> findByQueryText(String queryText);
	
	@Query("select n from Indicator n where n.indicatorId = ?1 and UPPER(n.nameIndicator)=UPPER(?2)")
	public Indicator findIndicatorsByIndicatorIdAndIndicatorName(String indicatorId, String indicatorName);

}