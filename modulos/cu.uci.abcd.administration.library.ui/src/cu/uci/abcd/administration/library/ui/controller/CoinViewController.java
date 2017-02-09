package cu.uci.abcd.administration.library.ui.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.unesco.jisis.corelib.common.WorksheetDef;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Coin;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class CoinViewController implements ViewController {

	private AllManagementLibraryViewController allManagementLibraryViewController;

	public AllManagementLibraryViewController getAllManagementLibraryViewController() {
		return allManagementLibraryViewController;
	}

	public void setAllManagementLibraryViewController(
			AllManagementLibraryViewController allManagementLibraryViewController) {
		this.allManagementLibraryViewController = allManagementLibraryViewController;
	}

	public Coin getCoinById(Long idCoin) {
		return allManagementLibraryViewController.getCoinService().readCoin(
				idCoin);
	}

	public Coin saveCoinAAA(Coin coin) {
		return allManagementLibraryViewController.getCoinService()
				.addCoin(coin);
	}

	public Coin saveCoin(Coin coin) {
		Coin coinSaved = null;
		Library library = coin.getLibrary();
		try {
			// jisis sirve
			getAllManagementLibraryViewController().getLibraryService()
					.getDatabaseNames(library.getIsisDefHome());
		} catch (Exception e1) {
			return null;
		}
		try {
			// postgres sirve
			coinSaved = allManagementLibraryViewController.getCoinService()
					.addCoin(coin);
		} catch (Exception e2) {
			return null;
		}
		// hacer todo
		List<Coin> allCoins = findCoinByLibrary(library.getLibraryID());
		saveCoinJisis(allCoins, library);

		return coinSaved;
	}

	public void saveCoinJisis(List<Coin> allCoins, Library library) {
		String pickList = allCoins.toString();
		pickList = pickList.replace("[", "");
		pickList = pickList.replace("]", "");
		pickList = pickList.replace(",", ";");
		String databaseName = "Registro_De_Adquisicion";
		String isisDefHome = library.getIsisDefHome();
		String workSheetNameDonation = "Donación";
		String workSheetNameCompra = "Compra";
		String workSheetNameDefault = "Hoja de trabajo por defecto";
		String workSheetNameCanje = "Canje";
		try {

			WorksheetDef workSheetDonation = allManagementLibraryViewController
					.getLibraryService().getWorksheet(workSheetNameDonation,
							databaseName, isisDefHome);
			workSheetDonation.getFieldByTag(22).setPickList(pickList);
			allManagementLibraryViewController.getLibraryService()
					.updateWorksheet(workSheetDonation);

			WorksheetDef workSheetCompra = allManagementLibraryViewController
					.getLibraryService().getWorksheet(workSheetNameCompra,
							databaseName, isisDefHome);
			workSheetCompra.getFieldByTag(22).setPickList(pickList);
			allManagementLibraryViewController.getLibraryService()
					.updateWorksheet(workSheetCompra);

			WorksheetDef workSheetDefault = allManagementLibraryViewController
					.getLibraryService().getWorksheet(workSheetNameDefault,
							databaseName, isisDefHome);
			workSheetDefault.getFieldByTag(22).setPickList(pickList);
			allManagementLibraryViewController.getLibraryService()
					.updateWorksheet(workSheetDefault);

			WorksheetDef workSheetCanje = allManagementLibraryViewController
					.getLibraryService().getWorksheet(workSheetNameCanje,
							databaseName, isisDefHome);
			workSheetCanje.getFieldByTag(22).setPickList(pickList);
			allManagementLibraryViewController.getLibraryService()
					.updateWorksheet(workSheetCanje);

		} catch (JisisDatabaseException e1) {
			// RetroalimentationUtils
			// .showErrorMessage("JISIS is down");
		}

	}

	public int deleteCoin(Coin coin) {
		Library library = coin.getLibrary();
		try {
			// jisis sirve
			getAllManagementLibraryViewController().getLibraryService()
					.getDatabaseNames(library.getIsisDefHome());
		} catch (Exception e1) {
			return 2;
		}
		try {
			allManagementLibraryViewController.getCoinService().deleteCoin(
					coin.getCoinID());
		} catch (Exception x) {
			return 1;
		}

		List<Coin> allCoins = findCoinByLibrary(library.getLibraryID());
		saveCoinJisis(allCoins, library);
		return 3;
	}

	// public void deleteCoinById(Long idCoin) {
	// allManagementLibraryViewController.getCoinService().deleteCoin(idCoin);
	// }

	public Page<Coin> findByCoinByParams(Library library,
			String coinNameConsult, Nomenclator identificationConsult,
			int page, int size, int direction, String orderByString) {
		return allManagementLibraryViewController.getCoinService().findAll(
				library, coinNameConsult, identificationConsult, page, size,
				direction, orderByString);
	}

	public List<Coin> findAll() {
		return allManagementLibraryViewController.getCoinService().findAll();
	}

	public Coin findCoinByName(String coinName, Long idLibrary) {
		return allManagementLibraryViewController.getCoinService()
				.findCoinByByName(coinName, idLibrary);
	}

	public List<Coin> findCoinByLibrary(Long idLibrary) {
		return allManagementLibraryViewController.getCoinService().findAll(
				idLibrary);
	}

}
