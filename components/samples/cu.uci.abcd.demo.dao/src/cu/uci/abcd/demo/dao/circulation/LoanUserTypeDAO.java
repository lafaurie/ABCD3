package cu.uci.abcd.demo.dao.circulation;

import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.demo.domain.circulation.LoanUserType;

public interface LoanUserTypeDAO extends
		PagingAndSortingRepository<LoanUserType, Long> {

}
