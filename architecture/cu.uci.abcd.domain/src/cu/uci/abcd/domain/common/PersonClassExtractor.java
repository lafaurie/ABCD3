package cu.uci.abcd.domain.common;

import org.eclipse.persistence.descriptors.ClassExtractor;
import org.eclipse.persistence.sessions.Record;
import org.eclipse.persistence.sessions.Session;

import cu.uci.abcd.domain.management.library.Worker;

public class PersonClassExtractor extends ClassExtractor {

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Class extractClassFromRow(Record record, Session session) {
		if (record.containsKey("workertype") && record.get("workertype") != null) {
			return Worker.class;
		}
		return Person.class;
	}

}
