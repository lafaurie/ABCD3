package cu.uci.abcd.demo.dao.common;

import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.demo.domain.common.EmailAddress;

public interface EmailAddressDAO extends
		PagingAndSortingRepository<EmailAddress, Long> {

}
