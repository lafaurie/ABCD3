package cu.uci.abcd.statistic.impl;

import java.util.Collection;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.dao.common.StatisticDAO;
import cu.uci.abcd.domain.common.Statistic;
import cu.uci.abcd.domain.statistic.Variable;
import cu.uci.abcd.statistic.IAdminTable;
import cu.uci.abcd.statistic.jisis.CrossStatistics;
import cu.uci.abcd.statistic.jisis.domain.StatisticParam;
import cu.uci.abcd.statistic.jisis.domain.TabularStatistic;
import cu.uci.abcd.statistic.jisis.utils.OptionUtils;

public class TableImpl implements IAdminTable {

	private StatisticDAO statisticDAO;
	private CrossStatistics crossStatistics;

	public void bind(StatisticDAO statisticDAO, Map<?, ?> properties) {
		this.statisticDAO = statisticDAO;
	}

	public void bind(CrossStatistics crossStatistics, Map<?, ?> properties) {
		this.crossStatistics = crossStatistics;
	}

	@Override
	public Statistic addStatistic(Statistic statistic) {
		return statisticDAO.save(statistic);
	}

	@Override
	public void deleteStatistic(Long idStatistic) {
		statisticDAO.delete(idStatistic);
	}

	@Override
	public Statistic editStatistic(Long idStatistic) {
		return statisticDAO.findOne(idStatistic);
	}

	@Override
	public Statistic viewStatistic(Long idStatistic) {
		return statisticDAO.findOne(idStatistic);
	}

	@Override
	public Page<Statistic> FinAllStatistic(Specification<Statistic> specification, Pageable pageable) {
		return statisticDAO.findAll(specification, pageable);
	}

	@Override
	public Collection<Statistic> findAllStatistic() {
		return (Collection<Statistic>) statisticDAO.findAll();
	}

	@Override
	public TabularStatistic getValuesFromLucene(Variable row, Variable colum, String options) {
		return crossStatistics.buildStatistics( new StatisticParam(OptionUtils.buildOptions(options), row.getField(), colum.getField(), row.getDatabaseName(), "DEF_HOME"));
	}

	@Override
	public TabularStatistic getValuesFromMFN(Variable row, Variable colum, Integer begin, Integer end) {
		return crossStatistics.buildStatistics(new StatisticParam(row.getField(), colum.getField(), begin, end, row.getDatabaseName(), "DEF_HOME"));
	}

	@Override
	public TabularStatistic getValuesFromLuceneAndMFN(Variable row, Variable colum, String options, Integer begin, Integer end) {
		StatisticParam param = new StatisticParam(row.getField(), colum.getField(), begin, end, row.getDatabaseName(), "DEF_HOME");
		param.setOptions(OptionUtils.buildOptions(options));
		return crossStatistics.buildStatistics(param);
	}

}
