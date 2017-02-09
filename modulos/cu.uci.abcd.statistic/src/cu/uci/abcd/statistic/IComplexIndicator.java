package cu.uci.abcd.statistic;

import cu.uci.abcd.domain.statistic.Report;

public interface IComplexIndicator {
	
	Object execute(Report report, Object ... params);

}
