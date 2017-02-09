package cu.uci.abcd.management.security;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Ldap;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface ILdapService {

	public Ldap addLdap(Ldap ldap);

	public Ldap findOneLdap(Long idLdap);

	public void deleteLdap(Long idLdap);

	public Library findOneLibrary(Long idLibrary);

	public Library addLibrary(Library library);

	public List<Ldap> findAllByLibrary(Long libraryID);

	public List<Ldap> findAllEnabledByLibrary(Long libraryID);

	public Page<Ldap> findAll(Library library, int page, int size,
			int direction, String order);

	public Ldap getLdapByDomain(Long idLibrary, String domain);

	public Ldap getLdapByHostAndPort(Long idLibrary, String host, int port);

}
