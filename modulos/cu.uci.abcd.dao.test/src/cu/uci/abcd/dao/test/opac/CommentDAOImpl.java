package cu.uci.abcd.dao.test.opac;

import java.util.LinkedList;

import cu.uci.abcd.dao.opac.CommentDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.opac.Comment;

public class CommentDAOImpl extends DaoUtil<Comment> implements CommentDAO {

	public CommentDAOImpl() {
		super();
		data = new LinkedList<Comment>(DataGenerator.getInstance().getComments());
	}

}
