package cu.uci.abcd.statistic.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import cu.uci.abcd.dao.common.StatementExecutorDAO;
import cu.uci.abcd.dao.specification.StatisticSpecification;
import cu.uci.abcd.dao.statistic.IndicatorDAO;
import cu.uci.abcd.dao.statistic.ReportGroupDAO;
import cu.uci.abcd.domain.statistic.Indicator;
import cu.uci.abcd.domain.statistic.Report;
import cu.uci.abcd.domain.statistic.ReportGroup;
import cu.uci.abcd.statistic.IIndicator;

public class IndicatorImpl implements IIndicator {

	private IndicatorDAO indicatorDAO;
	private ReportGroupDAO reportGroupDAO;
	private StatementExecutorDAO statementExecutorDAO;

	public void bind(IndicatorDAO indicatorDAO, Map<?, ?> properties) {
		this.indicatorDAO = indicatorDAO;
	}
	
	public void bindReportGroupDAO(ReportGroupDAO reportGroupDAO, Map<?, ?> properties) {
		this.reportGroupDAO = reportGroupDAO;
	}

	public void bindStatementExecutorDAO(StatementExecutorDAO statementExecutorDAO, Map<?, ?> properties) {
		this.statementExecutorDAO = statementExecutorDAO;
	}

	@Override
	public Page<Indicator> listAllIndicator(String nameIndicator, String numIndicator, int page, int size, int direction, String orderByString) {
		return indicatorDAO.findAll(StatisticSpecification.searchIndicator(nameIndicator, numIndicator), getPage(page, size, direction, orderByString));
	}

	@Override
	public Indicator getIndicatorValue(Indicator indicator, Object... params) {
		if (indicator.getQueryText() != null && !indicator.getQueryText().isEmpty() && !(indicator.getQueryText() == "")) {
			indicator.setValue(statementExecutorDAO.findOne(indicator.getQueryText(), params));
		}
		return indicator;
	}

	@Override
	public Indicator addIndicator(Indicator indicator) {
		return indicatorDAO.save(indicator);
	}
	
	@Override
	public List<Indicator> listIndicator(String nameIndicator, String numIndicator) {
		return indicatorDAO.findAll(StatisticSpecification.searchIndicator(nameIndicator, numIndicator));
	}

	@Override
	public boolean deleteIndicator(Long idIndicator) {
		
		Indicator deleteIndicator = indicatorDAO.findOne(idIndicator);
		List<Report> reports = deleteIndicator.getReports();
			if (reports.isEmpty()) {
				indicatorDAO.delete(deleteIndicator);
				return true;
			}
			return false;
		
	}

	@Override
	public Indicator getIndicatorValues(Indicator indicator, Object... params) {
		if (indicator.getQueryText() != null && !indicator.getQueryText().isEmpty()) {
			indicator.setValue(statementExecutorDAO.findOne(indicator.getQueryText(), params));
		}
		return indicator;
	}

	static Pageable getPage(int page, int size, int direction, String orderByString) {
		return new PageRequest(page, size, sort(direction, orderByString));
	}

	static Sort sort(int direction, String orderByString) {
		return new Sort(new Sort.Order((direction == 1024) ? Sort.Direction.ASC : Sort.Direction.DESC, orderByString));
	}

	@Override
	public Indicator searchIndicator(Long idIndicator) {
		return indicatorDAO.findOne(idIndicator);
	}

	 @Override
		public Boolean validate(Indicator indicator) {
			if (indicator.getQueryText() != null&& !indicator.getQueryText().isEmpty()) {
				String query = indicator.getQueryText().toUpperCase();
				if (query.contains(" INSERT ") || query.contains(" UPDATE ") || query.contains(" DELETE ") || query.contains(" DROP ") || query.contains(" TRUNCATE ")) {
					return false;
				}
				/*if (statementExecutorDAO.findALL(query.replace("?", "null")).size()!=1){
					return false;
				}*/
				try {
					query= new String(indicator.getQueryText());
					statementExecutorDAO.findOne(query.replace("?", "null"));
					return true;
				} catch (Exception e) {
					return false;
				}
			}
			return true;
		}


	@Override
	public List<ReportGroup> searchParents() {
		
		return (List<ReportGroup>) reportGroupDAO.findAll();
	}

	@Override
	public Indicator findIndicatorByNumberAndName(String number, String name) {
		return  indicatorDAO.findIndicatorsByIndicatorIdAndIndicatorName(number, name);
	}
}
