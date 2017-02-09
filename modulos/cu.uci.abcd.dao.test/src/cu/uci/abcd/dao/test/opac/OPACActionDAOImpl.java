package cu.uci.abcd.dao.test.opac;

import java.util.LinkedList;

import cu.uci.abcd.dao.opac.OPACActionDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.opac.OPACAction;

public class OPACActionDAOImpl extends DaoUtil<OPACAction> implements OPACActionDAO {

	public OPACActionDAOImpl() {
		super();
		data = new LinkedList<OPACAction>(DataGenerator.getInstance().getOpacActions());
	}

}
