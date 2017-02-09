package cu.uci.abcd.dao.test.common;

import java.util.LinkedList;
import java.util.List;

import cu.uci.abcd.dao.common.NomenclatorDAO;
import cu.uci.abcd.dao.test.DaoTestException;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.common.Nomenclator;

public class NomenclatorDAOImpl extends DaoUtil<Nomenclator> implements NomenclatorDAO {
	
	public NomenclatorDAOImpl() {
		super();
		data= new LinkedList<Nomenclator>(DataGenerator.getInstance().getNomenclators());
	}
	

	@Override
	public <S extends Nomenclator> S save(S value) {
		if (value.getNomenclatorID()==null) {
			value.setNomenclatorID(new Long(data.size()));
		}
		return super.save(value);
	}


	@Override
	public List<Nomenclator> findByNomenclatorCode(String code) {
		List<Nomenclator> result = new LinkedList<Nomenclator>();
		for (Nomenclator nomenclator : data) {
			if (code.equals(nomenclator.getNomenclatorCode())) {
				result.add(nomenclator);
			}
		}
		return result;
		
	}

	@Override
	public List<Nomenclator> findByOwnerLibrary(Long id) {
		List<Nomenclator> result = new LinkedList<Nomenclator>();
		for (Nomenclator nomenclator : data) {
			if (id.equals(nomenclator.getOwnerLibrary().getLibraryID())) {
				result.add(nomenclator);
			}
		}
		return result;
	}

	@Override
	public Nomenclator findByOwnerLibraryAndID(Long library, Long id) {
	
		for (Nomenclator nomenclator : data) {
			if (id.equals(nomenclator.getNomenclatorID())&& library.equals(nomenclator.getOwnerLibrary().getLibraryID())) {
				return nomenclator;
			}
		}
		throw new DaoTestException("Data Not Found.");
	}

	@Override
	public List<Nomenclator> findByOwnerLibraryAndName(Long library, String name) {
		List<Nomenclator> result = new LinkedList<Nomenclator>();
		for (Nomenclator nomenclator : data) {
			if (name.equals(nomenclator.getNomenclatorName())&& library.equals(nomenclator.getOwnerLibrary().getLibraryID())) {
				result.add(nomenclator);
			}
		}
		return result;
	}

	@Override
	public List<Nomenclator> findByOwnerLibraryAndNomenclatorCode(Long library, String code) {
		List<Nomenclator> result = new LinkedList<Nomenclator>();
		for (Nomenclator nomenclator : data) {
			if (code.equals(nomenclator.getNomenclatorCode())&& library.equals(nomenclator.getOwnerLibrary().getLibraryID())) {
				result.add(nomenclator);
			}
		}
		return result;
	}

	@Override
	public List<Nomenclator> findDistinctNomenclatorByOwnerLibraryAndNomenclatorNameOrNomenclatorDescription(Long library, String code, String name, String description) {
		List<Nomenclator> result = new LinkedList<Nomenclator>();
		for (Nomenclator nomenclator : data) {
			if (library.equals(nomenclator.getOwnerLibrary().getLibraryID())&&(code.equals(nomenclator.getNomenclatorCode())||name.equals(nomenclator.getNomenclatorName())||description.equals(nomenclator.getNomenclatorDescription()))) {
				result.add(nomenclator);
			}
		}
		return result;
	}

	
}
