package cu.uci.abcd.dao.common;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.common.Statistic;

public interface StatisticDAO extends PagingAndSortingRepository<Statistic, Long>, JpaSpecificationExecutor<Statistic> {

}
