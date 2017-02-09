package cu.uci.abcd.acquisition.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.unesco.jisis.corelib.exceptions.DbException;
import org.unesco.jisis.corelib.record.IRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.acquisition.IRegistrationManageSampleAcquisitionService;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionAND;


public class RegistrationManageSampleAcquisition implements IRegistrationManageSampleAcquisitionService {
	private IJisisDataProvider registro;
	private Option option1;
	private Option option2;

	@Override
	public void registerSampleAcquisition(IRecord newRecord) {
		try {
			try {
				if (registro.find(newRecord.getField(9).getStringFieldValue(), "Registro_Adquisicion_Ejemplar","DEF_HOME").isEmpty())
					registro.saveRecord(newRecord, "Registro_De_Adquisicion_Ejemplar","DEF_HOME");
			} catch (JisisDatabaseException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		} catch (DbException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void editRecordSampleAcquisition(Record record, Record nuevo) {
		record = nuevo;
		try {
			registro.updateRecord(record, "Registro_Adquisicion_Ejemplar","DEF_HOME");
		} catch (JisisDatabaseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public void deleteRecordSampleAcquisition(Record record) {
		registro.deleteRecord(record, "Registro_Adquisicion_Ejemplar","DEF_HOME");
	}

	public List<Record> findRecords(String title, String author) {
		option1 = new OptionAND("title", title);
		option2 = new OptionAND("author", author);

		List<Option> aux = new ArrayList<>();
		aux.add(0, option1);
		aux.add(1, option2);

		try {
			return (List<Record>) registro.findByOptions(aux, "Registro_De_Adquisicion","DEF_HOME");
		} catch (JisisDatabaseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public IJisisDataProvider getService() {
		return registro;
	}

	@Override
	public IRecord readRecordSampleAcquisition(IRecord r) {
		try {
			if (!getService().find(r.getField(9).getStringFieldValue(), "Registro_Adquisicion_Ejemplar","DEF_HOME").isEmpty())
				return r;
			else {
				throw new RuntimeException("Data not found, invalid value for 9 field.");
			}
		} catch (JisisDatabaseException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (DbException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public void bind(IJisisDataProvider registro, Map<?, ?> properties) {
		this.registro = registro;
	}
}
