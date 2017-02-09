package cu.uci.abcd.dao.common;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.common.Person;

public interface PersonBaseDao<T extends Person> extends PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T> {

}
