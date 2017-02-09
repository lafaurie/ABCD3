package cu.uci.abcd.dao.common;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cu.uci.abcd.domain.common.Person;

public interface PersonDAO extends PersonBaseDao<Person>, JpaSpecificationExecutor<Person> {

	public Person findPersonByDniIgnoreCaseAndLibrary_LibraryID(String identification, long idLibrary);
	
}
