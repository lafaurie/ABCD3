package cu.uci.abcd.demo.dao.common;

import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.demo.domain.common.PhoneNumber;

public interface PhoneNumberDAO extends
		PagingAndSortingRepository<PhoneNumber, Long> {

}
