package cu.uci.abcd.dao.management.security;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.management.security.Ldap;

public interface LdapDAO extends PagingAndSortingRepository<Ldap, Long> ,JpaSpecificationExecutor<Ldap>{

	public List<Ldap> findDistinctLdapByLibrary_LibraryID(Long libraryID);
	
	public List<Ldap> findDistinctLdapByEnabledAndLibrary_LibraryID(boolean enabled, Long libraryID);
	
	public Ldap findLdapByLibrary_LibraryIDAndDomainIgnoreCase(Long idLibrary, String domain);
	
	public Ldap findLdapByLibrary_LibraryIDAndHostIgnoreCaseAndPort(Long idLibrary, String host, int port);
	
}
