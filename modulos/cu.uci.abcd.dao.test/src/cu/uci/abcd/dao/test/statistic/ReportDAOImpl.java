package cu.uci.abcd.dao.test.statistic;

import java.util.LinkedList;
import java.util.List;

import cu.uci.abcd.dao.statistic.ReportDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.domain.statistic.Report;

public class ReportDAOImpl extends DaoUtil<Report> implements ReportDAO{

	@Override
	public List<Report> findByDatabaseName(String databaseName) {
		List<Report> result = new LinkedList<Report>();
		for (Report report : data) {
			if (databaseName.equals(report.getDatabaseName())) {
				result.add(report);
			}
		}
		return result;
	}

	@Override
	public List<Report> findById(Long id) {
		List<Report> result = new LinkedList<Report>();
		for (Report report : data) {
			if (id.equals(report.getId())) {
				result.add(report);
			}
		}
		return result;
	}

	@Override
	public List<Report> findByNameReport(String name) {
		List<Report> result = new LinkedList<Report>();
		for (Report report : data) {
			if (name.equals(report.getNameReport())) {
				result.add(report);
			}
		}
		return result;
	}

}
