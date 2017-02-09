package cu.uci.abcd.opac.impl;

import java.util.List;
import java.util.Map;

import cu.uci.abcd.dao.opac.CommentDAO;
import cu.uci.abcd.domain.opac.Comment;
import cu.uci.abcd.opac.IOpacCommentService;

public class OpacCommentServiceImpl implements IOpacCommentService {

	CommentDAO commentDAO;

	@Override
	public Comment addComment(Comment comment) {

		return commentDAO.save(comment);
	}

	@Override
	public void deleteComment(Long idComment) {

		commentDAO.delete(idComment);
	}

	@Override
	public List<Comment> findAllCommentsByMaterial(String idMaterial, String dataBaseName, String def_home) {
		
		return commentDAO.findByMaterial(idMaterial);
	}

	@Override
	public Comment updateComment(Comment comment) {

		return commentDAO.save(comment);
	}

	@Override
	public List<Comment> findAllCommentsPendingByUser() {
		return null;
	}

	@Override
	public Comment findComment(Long arg0) {
		return null;
	}

	public void bind(CommentDAO commentDAO, Map<String, Object> properties) {
		this.commentDAO = commentDAO;
		System.out.println("servicio registrado");
	}
}
