package cu.uci.abcd.dao.common;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Ldap;

public interface UserDAO extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {
	
	@Query("select u from User u where u.username = ?1 and u.userPassword = ?2 and u.library = null and u.domain = null and u.enabled=true ")
	public User findUser(String userName, String password);
	
	@Query("select u from User u where u.username = ?1 and u.userPassword = ?2 and u.library = ?3 and u.domain = null and u.enabled=true ")
	public User findLocalUser(String userName, String password, Library library);
	
	@Query("select u from User u where u.username = ?1 and u.userPassword = ?2 and u.library = ?3 and u.domain = null and u.enabled=true and u.systemuser=true")
	public User findLocalSystemUser(String userName, String password, Library library);
	
	@Query("select u from User u where u.username = ?1 and u.userPassword = ?2 and u.library = ?3 and u.domain = null and u.enabled=true and u.opacuser=true")
	public User findLocalOpacUser(String userName, String password, Library library);

	@Query("select u from User u where u.username = ?1 and u.library = ?2  and u.domain = ?3 and u.domain != null and u.enabled=true ")
	public User findDomainUser(String userName, Library library, Ldap ldap);
	
	@Query("select u from User u where u.username = ?1 and u.library = ?2  and u.domain = ?3 and u.domain != null and u.enabled=true and u.systemuser=true ")
	public User findDomainSystemUser(String userName, Library library, Ldap ldap);
	
	@Query("select u from User u where u.username = ?1 and u.library = ?2  and u.domain = ?3 and u.domain != null and u.enabled=true and u.opacuser=true ")
	public User findDomainOpacUser(String userName, Library library, Ldap ldap);

	//buscar locales(domain) por user, library 
	@Query("select u from User u where u.username = ?1 and u.library = ?2 and u.domain = null and u.enabled=true ")
	public User findLocalUser(String userName, Library library);

	////buscar en todos por user, library, ldap
	//@Query("select u from User u where u.username = ?1 and u.library = ?2  and u.domain = ?3 and and u.domain != null")
	//public User findDomainUser(String userName, Library library, Ldap ldap);

	
	public User findUserByPerson_PersonID(Long personID);
}
