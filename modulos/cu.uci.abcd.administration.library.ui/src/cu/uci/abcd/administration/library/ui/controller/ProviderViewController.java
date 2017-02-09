package cu.uci.abcd.administration.library.ui.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.unesco.jisis.corelib.common.WorksheetDef;
import org.unesco.jisis.corelib.common.WorksheetDef.WorksheetField;

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Provider;
import cu.uci.abos.api.ui.ViewController;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ProviderViewController implements ViewController {

	private AllManagementLibraryViewController allManagementLibraryViewController;

	public AllManagementLibraryViewController getAllManagementLibraryViewController() {
		return allManagementLibraryViewController;
	}

	public void setAllManagementLibraryViewController(
			AllManagementLibraryViewController allManagementLibraryViewController) {
		this.allManagementLibraryViewController = allManagementLibraryViewController;
	}

	public Provider saveProvider(Provider provider) {
		Provider providerSaved = null;
		Library library = provider.getLibrary();
		try {
			// jisis sirve
			getAllManagementLibraryViewController().getLibraryService()
					.getDatabaseNames(library.getIsisDefHome());
		} catch (Exception e1) {
			return null;
		}
		try {
			// postgres sirve
			providerSaved = allManagementLibraryViewController
					.getProviderService().addProvider(provider);
		} catch (Exception e2) {
			return null;
		}
		// hacer todo
		List<Provider> allProviders = findProviderByLibrary(library
				.getLibraryID());
		saveProviderJisis(allProviders, library);

		return providerSaved;
	}

	public Page<Provider> findByProviderByParams(Library library,
			String providerName, String providerNit, String providerRif,
			Nomenclator providerState, Nomenclator cangeConsult,
			Nomenclator commercialConsult, Nomenclator donationConsult,
			int page, int size, int direction, String order) {
		return allManagementLibraryViewController.getProviderService().findAll(
				library, providerName, providerNit, providerRif, providerState,
				cangeConsult, commercialConsult, donationConsult, page, size,
				direction, order);
	}

	public Provider getProviderById(Long idProvider) {
		return allManagementLibraryViewController.getProviderService()
				.readProvider(idProvider);
	}

	public int deleteProviderByProvider(Provider provider) {
		Library library = provider.getLibrary();
		try {
			// jisis sirve
			getAllManagementLibraryViewController().getLibraryService()
					.getDatabaseNames(library.getIsisDefHome());
		} catch (Exception e1) {
			return 2;
		}
		try {
			allManagementLibraryViewController.getProviderService()
					.deleteProvider(provider.getProviderID());
		} catch (Exception x) {
			return 1;
		}
		
		List<Provider> allProviders = findProviderByLibrary(library
				.getLibraryID());
		saveProviderJisis(allProviders, library);
		return 3;
	}

	public List<Provider> findProviderByLibrary(Long idLibrary) {
		return allManagementLibraryViewController.getProviderService().findAll(
				idLibrary);
	}

	public Provider findProviderByLibraryAndTypeAndName(Long idLibrary,
			boolean intitutional, String providerName) {
		return allManagementLibraryViewController.getProviderService()
				.findProviderByLibraryAndTypeAndName(idLibrary, intitutional,
						providerName);
	}

	public Provider findProviderByLibraryAndTypeAndPerson(Long idLibrary,
			boolean intitutional, Long idPerson) {
		return allManagementLibraryViewController.getProviderService()
				.findProviderByLibraryAndTypeAndPerson(idLibrary, intitutional,
						idPerson);
	}

	public void saveProviderJisis(List<Provider> allProviders, Library library) {
		try {
			// Library library = provider.getLibrary();
			// List<Provider> allProviders =
			// findProviderByLibrary(library.getLibraryID());

			String pickList = allProviders.toString();
			pickList = pickList.replace("[", "");
			pickList = pickList.replace("]", "");
			pickList = pickList.replace(",", ";");
			String databaseName = "Registro_De_Adquisicion";
			String isisDefHome = library.getIsisDefHome();
			String workSheetNameDonation = "Donación";
			String workSheetNameCompra = "Compra";
			String workSheetNameDefault = "Hoja de trabajo por defecto";
			String workSheetNameCanje = "Canje";

			WorksheetDef workSheetDonation = getAllManagementLibraryViewController()
					.getLibraryService().getWorksheet(workSheetNameDonation,
							databaseName, isisDefHome);
			WorksheetField fieldProviderDonation = workSheetDonation
					.getFieldByTag(25);
			fieldProviderDonation.setPickList(pickList);
			getAllManagementLibraryViewController().getLibraryService()
					.updateWorksheet(workSheetDonation);

			WorksheetDef workSheetCompra = getAllManagementLibraryViewController()
					.getLibraryService().getWorksheet(workSheetNameCompra,
							databaseName, isisDefHome);
			WorksheetField fieldProviderCompra = workSheetCompra
					.getFieldByTag(24);
			fieldProviderCompra.setPickList(pickList);
			getAllManagementLibraryViewController().getLibraryService()
					.updateWorksheet(workSheetCompra);

			WorksheetDef workSheetDefault = getAllManagementLibraryViewController()
					.getLibraryService().getWorksheet(workSheetNameDefault,
							databaseName, isisDefHome);
			WorksheetField fieldProviderDefault = workSheetDefault
					.getFieldByTag(24);
			fieldProviderDefault.setPickList(pickList);
			getAllManagementLibraryViewController().getLibraryService()
					.updateWorksheet(workSheetDefault);

			WorksheetDef workSheetCanje = getAllManagementLibraryViewController()
					.getLibraryService().getWorksheet(workSheetNameCanje,
							databaseName, isisDefHome);
			WorksheetField fieldProviderCanje = workSheetCanje
					.getFieldByTag(31);
			fieldProviderCanje.setPickList(pickList);
			getAllManagementLibraryViewController().getLibraryService()
					.updateWorksheet(workSheetCanje);
		} catch (JisisDatabaseException e) {

		}
	}

}
