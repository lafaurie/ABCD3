package cu.uci.abcd.demo.dao.common;

import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.demo.domain.common.Url;

public interface UrlDAO extends PagingAndSortingRepository<Url, Long> {

}
