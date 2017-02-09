package cu.uci.abcd.opac.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dao.common.UserDAO;
import cu.uci.abcd.dao.specification.OpacSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.opac.IOpacUserService;

public class OpacUserServiceImpl implements IOpacUserService {

	UserDAO userDAO;

	@Override
	public User addUser(User user) {
		return userDAO.save(user);
	}

	@Override
	public void deleteUser(Long arg0) {
	}

	@Override
	public User findUser(Long userId) {
		return userDAO.findOne(userId);
	}

	@Override
	public User updateUser(User arg0) {

		return null;
	}

	@Override
	public Page<User> findAllUsersByLibrary(Long userId, Long libraryId, int page, int size, int direction, String orderByString) {

		return userDAO.findAll(OpacSpecification.searchUser(userId, libraryId), PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public Page<User> findAllUsersByLibraryAndUserName(Long userId, Long libraryId, String userName, int page, int size, int direction, String orderByString) {

		return userDAO.findAll(OpacSpecification.searchUser(userId, libraryId, userName), PageSpecification.getPage(page, size, direction, orderByString));
	}

	public void bind(UserDAO userDAO, Map<String, Object> properties) {
		this.userDAO = userDAO;
		System.out.println("servicio registrado");
	}    

	@Override
	public boolean checkExistingUser(String userName, Long libraryId) {

		List<User> users = (List<User>) userDAO.findAll();

		for (User user : users)
			if (user.getUsername().equals(userName) && user.getLibrary().getLibraryID() == libraryId)
				return true;

		return false;
	}
}
