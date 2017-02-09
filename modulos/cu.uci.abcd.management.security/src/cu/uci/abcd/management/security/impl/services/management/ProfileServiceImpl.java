package cu.uci.abcd.management.security.impl.services.management;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dao.management.security.ProfileDAO;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.dao.specification.SecuritySpecification;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Permission;
import cu.uci.abcd.domain.management.security.Profile;
import cu.uci.abcd.management.security.IProfileService;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ProfileServiceImpl implements IProfileService {

	private ProfileDAO profileDAO;

	@Override
	public Profile viewProfile(Long idProfile) {
		return profileDAO.findOne(idProfile);
	}

	@Override
	public Profile findOneProfile(Long idProfile) {
		return profileDAO.findOne(idProfile);
	}

	@Override
	public Profile addProfile(Profile profile) {
		return profileDAO.save(profile);
	}

	public void bindProfileDao(ProfileDAO profileDAO, Map<?, ?> properties) {
		this.profileDAO = profileDAO;
	}

	@Override
	public void deleteProfile(Profile profile) {
		this.profileDAO.delete(profile);
	}

	@Override
	public Collection<Profile> searchProfile(String name) {
		//FIXME OIGRES ARREGLAR ESTO QUE HAGA LA CONSULTA EN BD.
		List<Profile> all = (List<Profile>) this.profileDAO.findAll();
		ArrayList<Profile> tmp = new ArrayList<Profile>();
		for (Profile profile : all) {
			if (profile.getProfileName() == name || name.compareTo("") == 0) {
				tmp.add(profile);
			}
		}
		return tmp;
	}

	@Override
	public Collection<Profile> findProfileByQuery(String name, Long idBiblioteca) {
		//FIXME OIGRES ARREGLAR ESTO QUE HAGA LA CONSULTA EN BD.
		List<Profile> all = (List<Profile>) this.profileDAO.findAll();
		ArrayList<Profile> tmp = new ArrayList<Profile>();
		for (Profile profile : all) {
			if ((name.compareTo("") == 0 || profile.getProfileName().compareTo(name) == 0) && (idBiblioteca == -1 || profile.getLibrary().getLibraryID() == idBiblioteca)) {
				tmp.add(profile);
			}
		}
		return tmp;
	}

	@Override
	public Page<Profile> findAll(Library library, String profileName,
			Date fromDate, Date toDate, int page,
			int size, int direction, String order) {
		return profileDAO.findAll(
				SecuritySpecification.searchProfiles(library, profileName, fromDate, toDate),
				PageSpecification.getPage(page,
						size, direction, order));
	}

	@Override
	public Collection<Profile> findProfileByLibarry(Library library) {
		return profileDAO.findAllByLibrary(library);
	}

	@Override
	public Profile findProfileByName(Long idLibrary, String profileName) {
		return profileDAO.findProfileByLibrary_LibraryIDAndProfileNameIgnoreCase(idLibrary, profileName);
	}
	
	@Override
	public Profile findProfileByPermissions(Long idLibrary, List<Permission> permissions) {
		List<Profile> all = (List<Profile>) this.profileDAO.findAll();
		
		for (Profile profile : all) {
			if (profile.getAsignedPermissions().size()== permissions.size()&& (idLibrary == -1 || profile.getLibrary().getLibraryID() == idLibrary)) {
				boolean retain = profile.getAsignedPermissions().retainAll(permissions);
				if (retain) {
					return profile;
					
				}
			}
		}
		return null;
		
	}
}
