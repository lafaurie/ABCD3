package cu.uci.abcd.administration.library.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.IProviderService;
import cu.uci.abcd.dao.management.library.ProviderDAO;
import cu.uci.abcd.dao.specification.LibrarySpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Provider;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ProviderServiceImpl implements IProviderService {

	private ProviderDAO providerDAO;

	public void bind(ProviderDAO providerDAO, Map<?, ?> properties) {
		this.providerDAO = providerDAO;
	}

	@Override
	public Provider addProvider(Provider provider) {
		return providerDAO.save(provider);
	}

	@Override
	public Provider readProvider(Long idProvider) {
		return providerDAO.findOne(idProvider);
	}

	@Override
	public void deleteProvider(Long idProvider) {
		providerDAO.delete(idProvider);

	}

	@Override
	public Page<Provider> findAll(Library library, String nameProvider,
			String providerNit, String providerRif, Nomenclator providerState,
			Nomenclator cangeConsult, Nomenclator commercialConsult,
			Nomenclator donationConsult, int page, int size, int direction,
			String order) {
		return providerDAO.findAll(LibrarySpecification.searchProvider(library,
				nameProvider, providerNit, providerRif, providerState,
				cangeConsult, commercialConsult, donationConsult),
				PageSpecification.getPage(page, size, direction, order));
	}

	@Override
	public List<Provider> findAll(Long idLibrary) {
		return providerDAO.findProviderByLibrary_LibraryID(idLibrary);
	}

	@Override
	public Provider findProviderByLibraryAndTypeAndName(Long idLibrary,
			boolean intitutional, String providerName) {
		return providerDAO
				.findProviderByLibrary_LibraryIDAndIsIntitutionalAndProviderNameIgnoreCase(
						idLibrary, intitutional, providerName);
	}

	@Override
	public Provider findProviderByLibraryAndTypeAndPerson(Long idLibrary,
			boolean intitutional, Long idPerson) {
		return providerDAO
				.findProviderByLibrary_LibraryIDAndIsIntitutionalAndPerson_PersonID(
						idLibrary, intitutional, idPerson);
	}

}
