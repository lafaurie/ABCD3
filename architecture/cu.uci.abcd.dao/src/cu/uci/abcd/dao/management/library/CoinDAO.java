package cu.uci.abcd.dao.management.library;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.management.library.Coin;

public interface CoinDAO extends PagingAndSortingRepository<Coin, Long>, JpaSpecificationExecutor<Coin> {

	public Coin findCoinByCoinNameIgnoreCaseAndLibrary_LibraryID(String coinName, Long idLibrary);
	
	public List<Coin>  findDistinctCoinByLibrary_LibraryID(Long idLibrary);
}
