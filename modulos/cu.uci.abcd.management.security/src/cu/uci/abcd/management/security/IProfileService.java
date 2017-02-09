package cu.uci.abcd.management.security;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Permission;
import cu.uci.abcd.domain.management.security.Profile;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface IProfileService {

	public Profile viewProfile(Long idProfile);

	public Page<Profile> findAll(Library library, String profileNameConsult,
			Date fromDateTimeConsult, Date toDateTimeConsult, int page,
			int size, int direction, String orderByString);

	public Profile findOneProfile(Long idProfile);

	public Profile addProfile(Profile profile);

	public void deleteProfile(Profile profile);

	public Collection<Profile> searchProfile(String name);

	public Collection<Profile> findProfileByQuery(String name, Long idBiblioteca);

	public Collection<Profile> findProfileByLibarry(Library library);

	public Profile findProfileByName(Long idLibrary, String profileName);
	
	public Profile findProfileByPermissions(Long idLibrary, List<Permission> permissions);
}
