package cu.uci.abcd.dao.test.management.security;

import java.util.LinkedList;

import cu.uci.abcd.dao.management.security.ProfileDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.management.security.Profile;

public class ProfileDAOImpl extends DaoUtil<Profile> implements ProfileDAO {

	public ProfileDAOImpl() {
		super();
		data= new LinkedList<Profile>(DataGenerator.getInstance().getProfiles());
	}

}
