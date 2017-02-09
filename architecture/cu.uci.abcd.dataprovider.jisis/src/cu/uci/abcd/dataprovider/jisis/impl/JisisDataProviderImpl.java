package cu.uci.abcd.dataprovider.jisis.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.unesco.jisis.corelib.client.ClientDbProxy;
import org.unesco.jisis.corelib.common.CreateDbParams;
import org.unesco.jisis.corelib.common.FieldDefinitionTable;
import org.unesco.jisis.corelib.common.FieldSelectionTable;
import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.common.Global;
import org.unesco.jisis.corelib.common.PrintFormat;
import org.unesco.jisis.corelib.common.WorksheetDef;
import org.unesco.jisis.corelib.exceptions.DbException;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionAND;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionNOT;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionOR;
import cu.uci.abos.jisis.conection.JISISConection;

public class JisisDataProviderImpl implements IJisisDataProvider {

	private ClientDbProxy proxy;
	private JISISConection connectionManager;

	public void bind(JISISConection connectionManager, Map<?, ?> properties) {
		this.connectionManager = connectionManager;
		proxy = connectionManager.getProxy();
	}

	@Override
	public void createDatabase(String name, FieldDefinitionTable fdt, FieldSelectionTable fst, WorksheetDef defaultWorksheet, PrintFormat defaultPft, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		try {
			CreateDbParams params = new CreateDbParams(libraryIsisDatabasesHomeFolder, name);
			params.setFieldDefinitionTable(fdt);
			params.setFieldSelectionTable(fst);
			params.setDefaultWorkSheet(defaultWorksheet);
			params.setDefaultPft(defaultPft.getName(), defaultPft.getFormat());

			proxy.createDatabase(params, Global.DATABASE_DURABILITY_WRITE);
		} catch (DbException e) {
			System.out.println("========================================ERROR=====================================");
			e.printStackTrace();
			System.out.println("Database connection failed");
			System.out.println("==================================================================================");
			throw new JisisDatabaseException("Error creating database");
		}
	}

	@Override
	public void saveFieldSelectionTable(FieldSelectionTable fst, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			proxy.saveFieldSelectionTable(fst);
		} catch (DbException e) {
			System.out.println("========================================ERROR=====================================");
			e.printStackTrace();
			System.out.println("Database connection failed");
			System.out.println("==================================================================================");
			throw new JisisDatabaseException("Error saving fst");
		}
	}

	@Override
	public List<String> getDatabaseNames(String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		List<String> databaseNames = new ArrayList<>();
		try {
			databaseNames = connectionManager.getConnection().getDbNames(libraryIsisDatabasesHomeFolder);
		} catch (DbException e) {
			System.out.println("========================================ERROR=====================================");
			e.printStackTrace();
			System.out.println("Database connection failed");
			System.out.println("==================================================================================");
			throw new JisisDatabaseException("Error getting database names");
		}

		return databaseNames;
	}

	@Override
	public void saveRecord(IRecord record, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			proxy.updateRecord((Record) record);
		} catch (Exception e) {
			System.out.println("========================================ERROR=====================================");
			e.printStackTrace();
			System.out.println("Error creating record");
			System.out.println("==================================================================================");
			throw new JisisDatabaseException("Error saving record");
		}
	}

	@Override
	public void deleteRecord(IRecord record, String databaseName, String libraryIsisDatabasesHomeFolder) {
		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			proxy.deleteRecord(record.getMfn());
		} catch (Exception e) {
			System.out.println("========================================ERROR=====================================");
			e.printStackTrace();
			System.out.println("Error removing record");
			System.out.println("==================================================================================");
		}
	}

	@Override
	public FieldSelectionTable getFieldSelectionTable(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			return proxy.getFieldSelectionTable();
		} catch (DbException e) {
			System.out.println("========================================ERROR=====================================");
			e.printStackTrace();
			System.out.println("Database connection failed");
			System.out.println("==================================================================================");
			throw new JisisDatabaseException("Error obtaining FST");
		}
	}

	@Override
	public List<Record> findByOptions(List<Option> options, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		List<Record> records = new ArrayList<>();
		long[] mfn = null;
		String field = "";
		String term = "";
		String consulta = "";

		for (int i = 0; i < options.size(); i++) {
			if ((null != options.get(i).getGroup()) && ((0 == i) || (options.get(i).getGroup() != options.get(i - 1).getGroup())))
				consulta += "(";

			if (options.get(i) instanceof OptionAND) {

				field = options.get(i).getField();
				term = options.get(i).getTerm().toUpperCase();

				consulta += " AND _" + field + ":" + term;

			} else if (options.get(i) instanceof OptionOR) {

				field = options.get(i).getField();
				term = options.get(i).getTerm().toUpperCase();

				consulta += " OR _" + field + ":" + term;

			} else if (options.get(i) instanceof OptionNOT) {

				field = options.get(i).getField();
				term = options.get(i).getTerm().toUpperCase();

				consulta += " AND NOT _" + field + ":" + term;

			} else {

				field = options.get(i).getField();
				term = options.get(i).getTerm().toUpperCase();

				consulta += "_" + field + ":" + term;
			}

			if (null != options.get(i).getGroup()) {
				if (i == options.size() - 1)
					consulta += ")";
				else if (options.get(i).getGroup() != options.get(i + 1).getGroup())
					consulta += ") AND ";
			}
		}
		System.out.println("========================================");
		System.out.println(consulta);
		System.out.println("========================================");

		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			mfn = proxy.searchLucene(consulta);
			if (null != mfn) {
				for (int j = 0; j < mfn.length; j++) {
					records.add((Record) proxy.getRecord(mfn[j]));
				}
			}
		} catch (DbException e) {
			e.printStackTrace();
		}

		return records;
	}

	@Override
	public List<Record> findByOptions(List<Option> options, String databaseName, String libraryIsisDatabasesHomeFolder, boolean opac) throws JisisDatabaseException {
		List<Record> records = new ArrayList<>();
		long[] mfn = null;
		String field = "";
		String term = "";
		String consulta = "";
              
		for (int i = 0; i < options.size(); i++) {
			if ((null != options.get(i).getGroup()) && ((0 == i) || (options.get(i).getGroup() != options.get(i - 1).getGroup())))
				consulta += "(";

			if (options.get(i) instanceof OptionAND) {

				field = options.get(i).getField();
				term = options.get(i).getTerm().toUpperCase();

				consulta += " AND _" + field + ":" + term;

			} else if (options.get(i) instanceof OptionOR) {

				field = options.get(i).getField();
				term = options.get(i).getTerm().toUpperCase();

				consulta += " OR _" + field + ":" + term;

			} else if (options.get(i) instanceof OptionNOT) {

				field = options.get(i).getField();
				term = options.get(i).getTerm().toUpperCase();

				consulta += " ANDNOT _" + field + ":" + term;

			} else {

				field = options.get(i).getField();
				term = options.get(i).getTerm().toUpperCase();

				consulta += "_" + field + ":" + term;
			}

			if (null != options.get(i).getGroup()) {
				if ((i == options.size() - 1))
					consulta += ")";
				else if (options.get(i).getGroup() != options.get(i + 1).getGroup() && options.get(i + 1).getTerm() == "") {
					if (options.get(i + 1) instanceof OptionAND) {
						consulta += ") AND ";
						i++;     
					}                  
					else if (options.get(i + 1) instanceof OptionOR) {
						consulta += ") OR ";
						i++;
					} else {
						consulta += ") ANDNOT ";
						i++;
					}
				} else if (options.get(i).getGroup() != options.get(i + 1).getGroup()) {
					consulta += ") AND ";
				}
			}
		}
		System.out.println("========================================");
		System.out.println(consulta);
		System.out.println("========================================");

		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			mfn = proxy.searchLucene(consulta);
			if (null != mfn) {
				for (int j = 0; j < mfn.length; j++) {
					records.add((Record) proxy.getRecord(mfn[j]));
				}
			}
		} catch (DbException e) {
			e.printStackTrace();
		}

		return records;
	}

	@Override
	public List<Record> find(String term, String databaseName, String libraryIsisDatabasesHomeFolder) {
		return find(term, databaseName, libraryIsisDatabasesHomeFolder, null);
	}

	@Override
	public List<Record> find(String term, String databaseName, String libraryIsisDatabasesHomeFolder, List<Option> options) {
		term = term.toUpperCase();
		String field = "";
		FieldSelectionTable fst = new FieldSelectionTable();
		List<Record> records = new ArrayList<>();
		long[] mfn = null;
		int[] tags = null;
		String parte1 = "";
		String consulta = "";
		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			fst = proxy.getFieldSelectionTable();
			tags = fst.getEntriesTag();

			if (!options.isEmpty() && options != null)
				consulta = "(";

			String[] terms = term.split(" ");
			for (int i = 0; i < terms.length; i++) {
				if (terms.length == 1) {
					for (int j = 0; j < tags.length; j++) {
						parte1 += " _" + tags[j] + ":" + terms[0] + " OR ";
					}
					consulta += parte1.substring(0, parte1.length() - 4);
				} else if (terms.length > 1) {
					for (int j = 0; j < tags.length; j++) {
						parte1 += " _" + tags[j] + ":" + terms[i] + " OR ";
					}
					consulta += parte1.substring(0, parte1.length() - 4);
				}
			}

			if (!options.isEmpty() && options != null) {
				consulta += ") AND (";

				field = options.get(0).getField();
				term = options.get(0).getTerm().toUpperCase();
				consulta += " _" + field + ":" + term;

				for (int i = 1; i < options.size(); i++) {
					field = options.get(i).getField();
					term = options.get(i).getTerm().toUpperCase();

					consulta += " OR _" + field + ":" + term;
				}

				consulta += ")";
			}

			mfn = proxy.searchLucene(consulta);

			if (null != mfn) {
				for (int h = 0; h < mfn.length; h++) {
					records.add((Record) proxy.getRecord(mfn[h]));
				}
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return records;
	}

	@Override
	public void updateRecord(IRecord record, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			proxy.updateRecord((Record) record);
		} catch (Exception e) {
			System.out.println("========================================ERROR=====================================");
			e.printStackTrace();
			System.out.println("Error updating record");
			System.out.println("==================================================================================");
			throw new JisisDatabaseException("Error saving record");
		}

	}

	@Override
	public List<String> getWorksheetNames(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			return Arrays.asList(proxy.getWorksheetNames());
		} catch (DbException e) {
			e.printStackTrace();
			throw new JisisDatabaseException(e.getMessage());
		}

	}

	@Override
	public WorksheetDef getWorksheet(String worksheetName, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			return proxy.getWorksheetDef(worksheetName);
		} catch (DbException e) {
			e.printStackTrace();
			throw new JisisDatabaseException(e.getMessage());
		}

	}

	@Override
	public List<String> getDatabaseFormats(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		List<String> databaseFormats = new ArrayList<>();

		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			databaseFormats = Arrays.asList(proxy.getPrintFormatNames());
		} catch (DbException e) {
			e.printStackTrace();
			throw new JisisDatabaseException(e.getMessage());
		}

		return databaseFormats;
	}

	@Override
	public FormattedRecord getFormattedRecord(String databaseName, Record record, String formatName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			return proxy.getRecordFmt(record.getMfn(), formatName);
		} catch (DbException e) {
			e.printStackTrace();
			throw new JisisDatabaseException(e.getMessage());
		}
	}

	@Override
	public Record getLastRecord(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			return (Record) proxy.getLast();
		} catch (DbException e) {
			e.printStackTrace();
			throw new JisisDatabaseException(e.getMessage());
		}
	}

	@Override
	public Record getFirstRecord(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			return (Record) proxy.getFirst();
		} catch (DbException e) {
			e.printStackTrace();
			throw new JisisDatabaseException(e.getMessage());
		}
	}

	@Override
	public FieldDefinitionTable getFieldDefinitionTable(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			return proxy.getFieldDefinitionTable();
		} catch (DbException e) {
			e.printStackTrace();
			throw new JisisDatabaseException(e.getMessage());
		}
	}

	@Override
	public List<Record> findByMfns(long[] mfn, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		List<Record> records = new ArrayList<>();

		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			if (null != mfn) {
				for (int j = 0; j < mfn.length; j++) {
					records.add((Record) proxy.getRecord(mfn[j]));
				}
			}
		} catch (DbException e) {
			e.printStackTrace();
		}

		return records;

	}

	@Override
	public Record findByMfn(long mfn, String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		Record record = null;

		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			record = (Record) proxy.getRecord(mfn);

		} catch (DbException e) {
			e.printStackTrace();
		}

		return record;
	}

	@Override
	public List<Record> findByRecordNumber(List<String> recordNumber, String databaseName, String libraryIsisDatabasesHomeFolder) {
		List<Record> records = new ArrayList<>();
		long[] mfn = null;
		String consulta = "";

		for (int j = 0; j < recordNumber.size(); j++) {

			if (consulta == "")
				consulta += "_1:" + recordNumber.get(j);
			else
				consulta += " OR _1:" + recordNumber.get(j);

		}

		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			mfn = proxy.searchLucene(consulta);
			if (null != mfn) {
				for (int j = 0; j < mfn.length; j++) {
					records.add((Record) proxy.getRecord(mfn[j]));
				}
			}
		} catch (DbException e) {
			e.printStackTrace();
		}

		System.out.println("========================================");
		System.out.println(consulta);
		System.out.println("========================================");

		return records;
	}

	@Override
	public Long totalRecords(String databaseName, String libraryIsisDatabasesHomeFolder) {
		Long recordsCount = (long) 0;

		try {
			proxy.getDatabase(libraryIsisDatabasesHomeFolder, databaseName, Global.DATABASE_DURABILITY_WRITE);
			recordsCount = proxy.getRecordsCount();

		} catch (DbException e) {
			e.printStackTrace();
		}
		return recordsCount;
	}

	@Override
	public boolean updateWorksheet(WorksheetDef worksheet) throws JisisDatabaseException {
		boolean response = false;
		try {
			response = proxy.saveWorksheetDef(worksheet);
		} catch (Exception e) {
			System.out.println("========================================ERROR=====================================");
			e.printStackTrace();
			System.out.println("Error updating worksheet");
			System.out.println("==================================================================================");
			throw new JisisDatabaseException("Error saving worksheet");
		}
		return response;
	}

}
