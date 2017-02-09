package cu.uci.abcd.administration.library.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.ICoinService;
import cu.uci.abcd.dao.management.library.CoinDAO;
import cu.uci.abcd.dao.specification.LibrarySpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Coin;
import cu.uci.abcd.domain.management.library.Library;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class CoinServiceImpl implements ICoinService {

	private CoinDAO coinDAO;

	public void bind(CoinDAO coinDAO, Map<?, ?> properties) {
		this.coinDAO = coinDAO;
	}

	@Override
	public Coin addCoin(Coin coin) {
		return coinDAO.save(coin);
	}

	@Override
	public Coin readCoin(Long idCoin) {
		return coinDAO.findOne(idCoin);
	}

	@Override
	public void deleteCoin(Long idCoin) {
		try {
			coinDAO.delete(idCoin);
		} catch (Exception e) {
			// Throwable a = e.getCause();
			// hrowable x = a;
		}

	}

	@Override
	public Page<Coin> findAll(Library library, String coinName,
			Nomenclator identification, int page, int size, int direction,
			String order) {
		return coinDAO.findAll(LibrarySpecification.searchCoin(library,
				coinName, identification), PageSpecification.getPage(page,
				size, direction, order));
	}

	@Override
	public List<Coin> findAll() {
		return (List<Coin>) coinDAO.findAll();
	}

	@Override
	public Coin findCoinByByName(String coinName, Long idLibrary) {
		return coinDAO.findCoinByCoinNameIgnoreCaseAndLibrary_LibraryID(
				coinName, idLibrary);
	}

	@Override
	public List<Coin> findAll(Long idLibrary) {
		return coinDAO.findDistinctCoinByLibrary_LibraryID(idLibrary);
	}

}
