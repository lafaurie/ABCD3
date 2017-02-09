package cu.uci.abcd.dao.statistic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.statistic.Report;

public interface ReportDAO extends PagingAndSortingRepository<Report, Long>, JpaSpecificationExecutor<Report> {

	public List<Report> findByNameReport(String name);

	// public List<Report> findByDatabaseName(String databaseName);

	public List<Report> findById(Long id);
	
	@Query("select n from Report n where UPPER(n.nameReport)=UPPER(?1)")
	public Report findReportsByReportName(String indicatorName);

}
