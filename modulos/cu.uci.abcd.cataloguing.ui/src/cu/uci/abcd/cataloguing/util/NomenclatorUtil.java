package cu.uci.abcd.cataloguing.util;

import cu.uci.abcd.cataloguing.ILoanObjectCreation;
import cu.uci.abcd.domain.common.Nomenclator;

public class NomenclatorUtil {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	public static Nomenclator getNomenclator(ILoanObjectCreation service,
			long id){
		return service.getNomenclator(id);
	}
}
