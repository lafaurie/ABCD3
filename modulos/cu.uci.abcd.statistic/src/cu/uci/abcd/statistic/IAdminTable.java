package cu.uci.abcd.statistic;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.domain.common.Statistic;
import cu.uci.abcd.domain.statistic.Variable;
import cu.uci.abcd.statistic.jisis.domain.TabularStatistic;

/**
 * @author Erdin Espinosa González
 * 
 */
public interface IAdminTable {

	public Statistic addStatistic(Statistic statistic);

	public Statistic viewStatistic(Long idStatistic);

	public Statistic editStatistic(Long idStatistic);

	public void deleteStatistic(Long idStatistic);

	public Collection<Statistic> findAllStatistic();

	public Page<Statistic> FinAllStatistic(Specification<Statistic> specification, Pageable pageable);
	
	public TabularStatistic getValuesFromLucene(Variable row, Variable colum, String options);
	
	public TabularStatistic getValuesFromMFN(Variable row, Variable colum, Integer begin, Integer end);
	
	public TabularStatistic getValuesFromLuceneAndMFN(Variable row, Variable colum, String options, Integer begin, Integer end);

}
