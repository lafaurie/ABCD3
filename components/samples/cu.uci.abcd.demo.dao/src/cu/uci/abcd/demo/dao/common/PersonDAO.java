package cu.uci.abcd.demo.dao.common;

import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.demo.domain.common.Person;

public interface PersonDAO extends PagingAndSortingRepository<Person, Long> {

}
