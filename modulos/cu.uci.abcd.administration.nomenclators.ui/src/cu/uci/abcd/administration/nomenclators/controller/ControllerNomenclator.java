package cu.uci.abcd.administration.nomenclators.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.nomenclators.IManageNomenclator;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;

public class ControllerNomenclator implements ViewController {
	private IManageNomenclator manageNomenclator;

	public Nomenclator registerNomenclator(Nomenclator tNomenclator) {
		return manageNomenclator.registerNomenclator(tNomenclator);
	}

	public Nomenclator searchNomenclator(Long actorID, Long tNomenclatorID) {
		return manageNomenclator.searchNomenclator(actorID, tNomenclatorID);
	}

	public List<Nomenclator> listByCode(Long library, Long nomenclatorCode) {
		return manageNomenclator.listByCode(library, nomenclatorCode);

	}

	public List<Nomenclator> listByName(Long library, String nomenclatorName) {
		return manageNomenclator.listByName(library, nomenclatorName);

	}

	public Page<Nomenclator> listAll(Library library, Long nomenclatorCode, String nomenclatorName, int page, int size, int direction, String orderByString) {
		return manageNomenclator.listAll(library, nomenclatorCode, nomenclatorName, page, size, direction, orderByString);
	}

	public void deleteNomenclator(Long nomenclatorID) {
		manageNomenclator.deleteNomenclator(nomenclatorID);
	}

	public void bind(IManageNomenclator manageNomenclator, Map<?, ?> properties) {
		this.manageNomenclator = manageNomenclator;
	}
	
	public List<Nomenclator> listByCode(Long nomenclatorCode){
		return manageNomenclator.listByCode(nomenclatorCode);
	}
	
	public List<Nomenclator> listManagedTypes(Library library){
		return manageNomenclator.listManagedTypes(library);
	}
	
	public Nomenclator findNomenclatorsByLibraryAndParentAndNomenclatorName(Long library, Long idParent, String nomenclatorName){
		return manageNomenclator.findNomenclator(library, idParent, nomenclatorName);
	}

}
