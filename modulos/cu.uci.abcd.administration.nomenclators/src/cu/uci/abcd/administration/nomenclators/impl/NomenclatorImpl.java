package cu.uci.abcd.administration.nomenclators.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import cu.uci.abcd.administration.nomenclators.IManageNomenclator;
import cu.uci.abcd.dao.common.NomenclatorDAO;
import cu.uci.abcd.dao.specification.NomenclatorSpecification;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;

/**
 * @author Dayana Rivera
 * 
 */

public class NomenclatorImpl implements IManageNomenclator {
	
	private NomenclatorDAO nomenclatorDAO;

	@Override
	public Nomenclator registerNomenclator(Nomenclator tNomenclator) {
		Nomenclator nomenclator = nomenclatorDAO.save(tNomenclator);
		return nomenclator;
	}

	@Override
	public Nomenclator searchNomenclator(Long library, Long ID) {
		return nomenclatorDAO.findByOwnerLibraryAndNomenclatorID(library, ID);
	}

	@Override
	public List<Nomenclator> listByCode(Long library, Long nomenclatorCode) {
		return (List<Nomenclator>) nomenclatorDAO.findByOwnerLibraryAndNomenclator(library, nomenclatorCode);
	}

	@Override
	public List<Nomenclator> listByName(Long library, String nomenclatorName) {
		return nomenclatorDAO.findByOwnerLibraryAndNomenclatorName(library, nomenclatorName);
	}

	@Override
	public void deleteNomenclator(Long ID) {
		Nomenclator deleteNomenclator = nomenclatorDAO.findOne(ID);
		nomenclatorDAO.delete(deleteNomenclator);

	}

	public void bind(NomenclatorDAO nomenclatorDAO, Map<?, ?> properties) {
		this.nomenclatorDAO = nomenclatorDAO;
	}
	
	@Override
	public Page<Nomenclator> listAll(Library library, Long nomenclatorCode, String nomenclatorName, int page, int size, int direction, String orderByString) {
		return nomenclatorDAO.findAll(NomenclatorSpecification.searchNomenclator(library, nomenclatorCode, nomenclatorName), getPage(page, size, direction, orderByString));
	}

	@Override
	public List<Nomenclator> listByCode(Long nomenclatorCode) {
		return nomenclatorDAO.findByNomenclator(nomenclatorCode);
	}

	@Override
	public List<Nomenclator> listManagedTypes(Library library) {
		return nomenclatorDAO.findAll(NomenclatorSpecification.searchTypes(library),sort(1024, "nomenclatorName"));
	}
	
	public static Pageable getPage(int page, int size, int direction, String orderByString) {
		return new PageRequest(page, size, sort(direction, orderByString));

	}

	public static Sort sort(int direction, String orderByString) {
		return new Sort(new Sort.Order((direction == 1024) ? Sort.Direction.ASC : Sort.Direction.DESC, orderByString));
	}

	@Override
	public Nomenclator findNomenclator(Long library, Long idParent,
			String nomenclatorName) {
		return nomenclatorDAO.findNomenclatorsByLibraryAndParentAndNomenclatorName(library, idParent, nomenclatorName);
	}
	
}
