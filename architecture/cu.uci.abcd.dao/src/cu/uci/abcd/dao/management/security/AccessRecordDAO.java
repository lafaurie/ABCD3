package cu.uci.abcd.dao.management.security;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.management.security.AccessRecord;

public interface AccessRecordDAO extends PagingAndSortingRepository<AccessRecord, Long> ,JpaSpecificationExecutor<AccessRecord> {

}
