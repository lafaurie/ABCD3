package cu.uci.abcd.statistic.impl;


import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.unesco.jisis.corelib.common.FieldDefinitionTable;
import org.unesco.jisis.corelib.common.WorksheetDef;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dao.management.library.LibraryDAO;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.statistic.IManageDatabase;

public class ManageDatabaseImpl implements IManageDatabase {
	
	private LibraryDAO libraryDAO;
	private IJisisDataProvider jisisDataProvider;
	
	

	public void bind(IJisisDataProvider dataProvider, Map<?, ?> properties) {
		this.jisisDataProvider = dataProvider;
	}

	public void bind(LibraryDAO libraryDAO, Map<?, ?> properties) {
		this.libraryDAO = libraryDAO;
	}

	@Override
	public List<String> getDataBaseNames(String libraryIsisDatabasesHomeFolder) {
		try {
			return jisisDataProvider.getDatabaseNames(libraryIsisDatabasesHomeFolder);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public List<String> getWorksheetNames(String databaseName, String library) {
		try {
			return jisisDataProvider.getWorksheetNames(databaseName, library);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public WorksheetDef getWorksheet(String worksheetName, String databaseName, String library) {
		try {
			return jisisDataProvider.getWorksheet(worksheetName, databaseName, library);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public FieldDefinitionTable getFieldDefinitionTable(String databaseName, String library) {
		try {
			return jisisDataProvider.getFieldDefinitionTable(databaseName, library);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Record getLastRecord(String databaseName, String library) {
		try {
			return jisisDataProvider.getLastRecord(databaseName, library);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<Library> findAllLibrary() {
		return (Collection<Library>) libraryDAO.findAll();
	}

	@Override
	public List<Record> findByOptions(List<Option> options, String databaseName, String library) {
		try {
			return jisisDataProvider.findByOptions(options, databaseName, library);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Record> findByMfns(long[] mfn, String databaseName, String library) {
		try {
			return jisisDataProvider.findByMfns(mfn, databaseName, library);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public boolean updateWorksheet(WorksheetDef worksheet){
		try {
			return jisisDataProvider.updateWorksheet(worksheet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
