package cu.uci.abcd.dao.test.adquisition;

import java.util.LinkedList;

import cu.uci.abcd.dao.acquisition.OrderDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.acquisition.Desiderata;

public  class DesiderataDAOImpl extends DaoUtil<Desiderata> implements OrderDAO{

	public DesiderataDAOImpl() {
		super();
		data= new LinkedList<Desiderata>(DataGenerator.getInstance().getDesidaratas());
	}

	@Override
	public <S extends Desiderata> S save(S value) {
		if (value.getDesidertaID()==null) {
			value.setDesidertaID(new Long(data.size()));
		}
		return super.save(value);
	}
	

}
