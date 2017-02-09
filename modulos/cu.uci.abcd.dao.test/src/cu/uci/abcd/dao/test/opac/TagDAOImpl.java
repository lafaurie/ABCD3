package cu.uci.abcd.dao.test.opac;

import java.util.LinkedList;

import cu.uci.abcd.dao.opac.TagDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.opac.Tag;

public class TagDAOImpl extends DaoUtil<Tag> implements TagDAO {

	public TagDAOImpl() {
		super();
		data = new LinkedList<Tag>(DataGenerator.getInstance().getTags());
	}

}
