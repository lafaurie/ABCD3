package cu.uci.abcd.dao.opac;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.opac.OpacDataSources;

public interface OpacDataSourcesDAO extends PagingAndSortingRepository<OpacDataSources, Long>, JpaSpecificationExecutor<OpacDataSources> {

}
