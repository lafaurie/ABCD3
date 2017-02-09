package cu.uci.abcd.dao.statistic;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.statistic.ReportGroup;

public interface ReportGroupDAO extends PagingAndSortingRepository<ReportGroup, Long>, JpaSpecificationExecutor<ReportGroup> {

}
