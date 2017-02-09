package cu.uci.abcd.demo.dao.circulation;

import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.demo.domain.circulation.LoanUser;

public interface LoanUserDAO extends PagingAndSortingRepository<LoanUser, Long> {

}
