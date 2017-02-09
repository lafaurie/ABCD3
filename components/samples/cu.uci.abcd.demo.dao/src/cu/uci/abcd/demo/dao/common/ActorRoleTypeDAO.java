package cu.uci.abcd.demo.dao.common;

import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.demo.domain.common.ActorRoleType;

public interface ActorRoleTypeDAO extends
		PagingAndSortingRepository<ActorRoleType, Long> {

}
