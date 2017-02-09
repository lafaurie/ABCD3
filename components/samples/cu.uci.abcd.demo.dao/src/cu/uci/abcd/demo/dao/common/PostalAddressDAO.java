package cu.uci.abcd.demo.dao.common;

import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.demo.domain.common.PostalAddress;

public interface PostalAddressDAO extends
		PagingAndSortingRepository<PostalAddress, Long> {

}
