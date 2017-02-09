package cu.uci.abcd.demo.dao.common;

import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.demo.domain.common.Account;

public interface AccountDAO extends PagingAndSortingRepository<Account, Long> {

}
