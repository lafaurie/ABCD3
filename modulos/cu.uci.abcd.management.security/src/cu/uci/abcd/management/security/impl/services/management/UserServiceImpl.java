package cu.uci.abcd.management.security.impl.services.management;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dao.common.UserDAO;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.dao.specification.SecuritySpecification;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.management.security.IUserService;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class UserServiceImpl implements IUserService {

	private UserDAO userDAO;

	public void bindUserDao(UserDAO userDAO, Map<?, ?> properties) {
		this.userDAO = userDAO;
	}

	@Override
	public User viewAccount(Long idUser) {
		return userDAO.findOne(idUser);
	}

	@Override
	public User findOneUser(Long idUser) {
		return userDAO.findOne(idUser);
	}

	@Override
	public User addUser(User user) {
		return userDAO.save(user);
	}

	@Override
	public void deleteUser(User user) {
		this.userDAO.delete(user);
	}

	public Collection<User> findAccountByQuery(String userName) {
		List<User> all = (List<User>) this.userDAO.findAll();
		ArrayList<User> tmp = new ArrayList<User>();
		for (User acc : all) {
			if (userName.compareTo("") == 0
					|| acc.getUsername().compareTo(userName) == 0)
				tmp.add(acc);
		}
		return tmp;
	}

	@Override
	public Page<User> findAll(Library library, int withPerson,
			boolean autenticated, String firstNameConsult,
			String secondNameConsult, String firstSurnameConsult,
			String secondSurnameConsult, String identificationConsult,
			String userConsult, boolean opacConsult, boolean systemConsult,
			Date fromDate, Date toDate, int page, int size, int direction,
			String order) {
		return userDAO.findAll(SecuritySpecification.searchUsers(library,
				withPerson, autenticated, firstNameConsult, secondNameConsult,
				firstSurnameConsult, secondSurnameConsult,
				identificationConsult, userConsult, opacConsult, systemConsult,
				fromDate, toDate), PageSpecification.getPage(page, size,
				direction, order));
	}

	@Override
	public User readUser(Long idUser) {
		return userDAO.findOne(idUser);
	}

	@Override
	public User readUserByPerson(Long idPerson) {
		return userDAO.findUserByPerson_PersonID(idPerson);
	}

	@Override
	public User findLocalUser(String userName, String password, Library library) {
		return (password == null) ? userDAO.findLocalUser(userName, library)
				: userDAO.findLocalUser(userName, password, library);
	}

	@Override
	public User findDomainUser(String userName, Library library, Ldap ldap) {
		return userDAO.findDomainUser(userName, library, ldap);
	}

	@Override
	public User findLocalSystemUser(String userName, String password,
			Library library) {
		return userDAO.findLocalSystemUser(userName, password, library);
	}

	@Override
	public User findDomainSystemUser(String userName, Library library, Ldap ldap) {
		return userDAO.findDomainSystemUser(userName, library, ldap);
	}

}
