package cu.uci.abcd.dao.test.opac;

import java.util.LinkedList;

import cu.uci.abcd.dao.opac.RecommendationDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.opac.Recommendation;

public class RecommendationDAOImpl extends DaoUtil<Recommendation> implements RecommendationDAO {

	public RecommendationDAOImpl() {
		super();
		data= new LinkedList<Recommendation>(DataGenerator.getInstance().getRecommendations());
	}

}
