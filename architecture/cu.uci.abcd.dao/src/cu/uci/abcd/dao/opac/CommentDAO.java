package cu.uci.abcd.dao.opac;

import java.util.List;

import cu.uci.abcd.domain.opac.Comment;

public interface CommentDAO extends OpacActionBaseDao<Comment> {
	   
	public List<Comment> findByMaterial(String idMaterial);

}
