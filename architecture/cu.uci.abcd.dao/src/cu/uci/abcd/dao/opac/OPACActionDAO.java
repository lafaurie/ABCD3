package cu.uci.abcd.dao.opac;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.opac.OPACAction;

public interface OPACActionDAO extends PagingAndSortingRepository<OPACAction, Long>, JpaSpecificationExecutor<OPACAction>  {


}
