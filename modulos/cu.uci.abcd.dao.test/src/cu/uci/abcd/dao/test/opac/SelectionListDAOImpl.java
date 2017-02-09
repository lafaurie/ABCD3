package cu.uci.abcd.dao.test.opac;

import java.util.LinkedList;

import cu.uci.abcd.dao.opac.SelectionListDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.opac.SelectionList;

public class SelectionListDAOImpl extends DaoUtil<SelectionList> implements SelectionListDAO {

	public SelectionListDAOImpl() {
		super();
		data= new LinkedList<SelectionList>(DataGenerator.getInstance().getSelectionsLists());
	}

}
