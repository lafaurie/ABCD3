package cu.uci.abcd.dao.test.adquisition;

import java.util.LinkedList;

import cu.uci.abcd.dao.acquisition.SuggestionDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.acquisition.Suggestion;

public class SuggestionDAOImpl extends DaoUtil<Suggestion> implements SuggestionDAO {

	public SuggestionDAOImpl() {
		super();
		data= new LinkedList<Suggestion>(DataGenerator.getInstance().getSugesstions());
	}

}
