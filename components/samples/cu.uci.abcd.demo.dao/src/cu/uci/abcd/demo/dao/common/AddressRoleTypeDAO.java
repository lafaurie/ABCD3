package cu.uci.abcd.demo.dao.common;

import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.demo.domain.common.AddressRoleType;

public interface AddressRoleTypeDAO extends
		PagingAndSortingRepository<AddressRoleType, Long> {

}
