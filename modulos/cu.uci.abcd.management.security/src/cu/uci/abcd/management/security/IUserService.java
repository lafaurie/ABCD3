package cu.uci.abcd.management.security;

import java.sql.Date;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Ldap;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface IUserService {

	public User viewAccount(Long idUser);

	public Page<User> findAll(Library library, int withPerson,
			boolean autenticated, String firstNameConsult,
			String secondNameConsult, String firstSurnameConsult,
			String secondSurnameConsult, String identificationConsult,
			String userConsult, boolean opacConsult, boolean systemConsult,
			Date fromDateTimeConsult, Date toDateTimeConsult, int page,
			int size, int direction, String order);

	public User findOneUser(Long idUser);

	public User addUser(User user);

	public void deleteUser(User user);

	public User readUser(Long idUser);

	public User readUserByPerson(Long idPerson);

	public User findLocalUser(String userName, String password, Library library);

	public User findDomainUser(String userName, Library library, Ldap ldap);

	public User findLocalSystemUser(String userName, String password,
			Library library);

	public User findDomainSystemUser(String userName, Library library, Ldap ldap);

}