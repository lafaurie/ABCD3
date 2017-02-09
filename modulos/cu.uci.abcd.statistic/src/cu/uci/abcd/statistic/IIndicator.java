package cu.uci.abcd.statistic;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.statistic.Indicator;
import cu.uci.abcd.domain.statistic.ReportGroup;

public interface IIndicator {

	public Page<Indicator> listAllIndicator(String nameIndicator, String numIndicator, int page, int zise, int direction, String orderByString);
	
	public List<Indicator> listIndicator(String nameIndicator, String numIndicator);

	public Indicator getIndicatorValues(Indicator indicator, Object... params);

	public Indicator searchIndicator(Long idIndicator);

	public Indicator getIndicatorValue(Indicator indicator, Object... params);

	public Indicator addIndicator(Indicator indicator);
	
	public Indicator findIndicatorByNumberAndName(String number, String name);
	
	public Boolean validate(Indicator indicator);

	public boolean deleteIndicator(Long idIndicator);

	public List<ReportGroup> searchParents();

}
