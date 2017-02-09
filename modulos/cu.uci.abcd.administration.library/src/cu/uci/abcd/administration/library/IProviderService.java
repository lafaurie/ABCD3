package cu.uci.abcd.administration.library;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Provider;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface IProviderService {

	public Provider addProvider(Provider provider);

	public Provider readProvider(Long idProvider);

	public void deleteProvider(Long idProvider);

	public Page<Provider> findAll(Library library, String providerName,
			String providerNit, String providerRif, Nomenclator providerState,
			Nomenclator cangeConsult, Nomenclator commercialConsult,
			Nomenclator donationConsult, int page, int size, int direction,
			String order);

	public List<Provider> findAll(Long idLibrary);

	public Provider findProviderByLibraryAndTypeAndName(Long idLibrary,
			boolean intitutional, String providerName);

	public Provider findProviderByLibraryAndTypeAndPerson(Long idLibrary,
			boolean intitutional, Long idPerson);
}
