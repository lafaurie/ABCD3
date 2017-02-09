package cu.uci.abcd.demo.dao.management.library;

import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.demo.domain.management.library.Library;

public interface LibraryDAO extends PagingAndSortingRepository<Library, Long> {

}
