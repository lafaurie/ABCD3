package cu.uci.abcd.opac.impl;

import java.util.List;

import cu.uci.abcd.domain.opac.MostBorrowed;
import cu.uci.abcd.opac.IOpacStatisticService;

public class OpacStatisticServiceImpl implements IOpacStatisticService {

	// MostBorrowedDAO mostBorrowedDAO;

	@Override
	public List<MostBorrowed> mostBorrowedTitles(Long libraryId) {

		@SuppressWarnings("unused")
		int a = 4;
		a += 5;

		// return mostBorrowedDAO.mostBorrowed(libraryId);

		return null;
	}

	/*
	 * public void bind(MostBorrowedDAO mostBorrowedDAO, Map<String, Object>
	 * properties) { this.mostBorrowedDAO = mostBorrowedDAO;
	 * System.out.println("servicio registrado"); }
	 */
}
