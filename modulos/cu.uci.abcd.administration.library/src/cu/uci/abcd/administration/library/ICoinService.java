package cu.uci.abcd.administration.library;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Coin;
import cu.uci.abcd.domain.management.library.Library;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface ICoinService {

	public Coin addCoin(Coin coin);

	public Coin readCoin(Long idCoin);

	public void deleteCoin(Long idCoin);

	public Page<Coin> findAll(Library library, String coinName,
			Nomenclator identification, int page, int size, int direction,
			String order);

	public List<Coin> findAll();

	public Coin findCoinByByName(String coinName, Long idLibrary);

	public List<Coin> findAll(Long idLibrary);

}
