package cu.uci.abcd.demo.dao.common;

import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.demo.domain.common.Application;

public interface ApplicationDAO extends
		PagingAndSortingRepository<Application, Long> {

}
