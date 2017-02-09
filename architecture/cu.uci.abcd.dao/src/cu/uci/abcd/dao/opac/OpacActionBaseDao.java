package cu.uci.abcd.dao.opac;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.opac.OPACAction;

public interface OpacActionBaseDao<T extends OPACAction> extends PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T> {

}
