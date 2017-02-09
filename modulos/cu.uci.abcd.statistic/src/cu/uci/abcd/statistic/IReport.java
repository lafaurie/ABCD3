package cu.uci.abcd.statistic;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.domain.statistic.Report;

public interface IReport {

	public Report addReport(Report report);

	public Report editReport(Long id);
	
	public Report viewReport(Long id);

	public Report findReportByName(String nameReport);

	public void deleteReport(Long id);

	public Collection<Report> findAllReport();

	public Report generateReportValues(Report report, Object... parameters);

	public Page<Report> findAllReportByIndicator(Specification<Report> specification, Pageable pageable);

	public Page<Report> listAllReport(String nameReport, int page, int size, int direction, String orderByString);

}
