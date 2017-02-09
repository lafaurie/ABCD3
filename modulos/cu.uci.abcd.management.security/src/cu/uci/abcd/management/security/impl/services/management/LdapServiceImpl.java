package cu.uci.abcd.management.security.impl.services.management;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dao.management.library.LibraryDAO;
import cu.uci.abcd.dao.management.security.LdapDAO;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.dao.specification.SecuritySpecification;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.management.security.ILdapService;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class LdapServiceImpl implements ILdapService {

	private LdapDAO ldapDAO;
	private LibraryDAO libraryDAO;

	public void bindLdapDao(LdapDAO ldapDAO, Map<?, ?> properties) {
		this.ldapDAO = ldapDAO;
	}
	
	public void bindLibraryDao(LibraryDAO libraryDAO, Map<?, ?> properties) {
		this.libraryDAO = libraryDAO;
	}

	@Override
	public Ldap addLdap(Ldap ldap) {
		return ldapDAO.save(ldap);
	}

	@Override
	public Ldap findOneLdap(Long idLdap) {
		return ldapDAO.findOne(idLdap);
	}

	@Override
	public void deleteLdap(Long idLdap) {
		ldapDAO.delete(idLdap);
		
	}

	@Override
	public Library findOneLibrary(Long idLibrary) {
		return libraryDAO.findOne(idLibrary);
	}

	@Override
	public Library addLibrary(Library library) {
		return libraryDAO.save(library);
	}

	@Override
	public List<Ldap> findAllByLibrary(Long libraryID) {
		return ldapDAO.findDistinctLdapByLibrary_LibraryID(libraryID);
	}

	@Override
	public Page<Ldap> findAll(Library library, int page, int size,
			int direction, String order) {
		return ldapDAO.findAll(SecuritySpecification.searchLdaps(library),
				PageSpecification.getPage(page, size, direction, order));
	}

	@Override
	public List<Ldap> findAllEnabledByLibrary(Long libraryID) {
		return ldapDAO.findDistinctLdapByEnabledAndLibrary_LibraryID(true, libraryID);
	}

	@Override
	public Ldap getLdapByDomain(Long idLibrary, String domain) {
		return ldapDAO.findLdapByLibrary_LibraryIDAndDomainIgnoreCase(idLibrary, domain);
	}

	@Override
	public Ldap getLdapByHostAndPort(Long idLibrary, String host, int port) {
		return ldapDAO.findLdapByLibrary_LibraryIDAndHostIgnoreCaseAndPort(idLibrary, host, port);
	}
}
