package cu.uci.abcd.dao.test.common;

import java.util.LinkedList;

import cu.uci.abcd.dao.common.PersonDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.common.Person;

public class PersonDAOImpl extends DaoUtil<Person> implements PersonDAO {

	public PersonDAOImpl() {
		super();
		data = new LinkedList<Person>(DataGenerator.getInstance().getPersons());
	}

	@Override
	public <S extends Person> S save(S value) {
		if (value.getPersonID()==null) {
			value.setPersonID(new Long(data.size()));
		}
		return super.save(value);
	}
	
	

}
