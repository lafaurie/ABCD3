package cu.uci.abcd.dao.test.management.library;

import java.util.LinkedList;

import cu.uci.abcd.dao.management.library.LibraryDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.management.library.Library;

public class LibraryDAOImpl extends DaoUtil<Library> implements LibraryDAO {

	
	public LibraryDAOImpl() {
		super();
		data= new LinkedList<Library>(DataGenerator.getInstance().getLibrarys());
	}

	@Override
	public Library findLibraryByAcountIDMember(Long userID) {
		// FIXME OIGRES ESTO NO VA AQUI, PONERLO EN USUARIO
	    return DataGenerator.getInstance().getLibrarys().get(0);
	}

}
