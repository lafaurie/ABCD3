package cu.uci.abcd.dao.test.common;

import java.util.LinkedList;

import cu.uci.abcd.dao.common.UserDAO;
import cu.uci.abcd.dao.test.DaoTestException;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.common.User;

public class UserDAOImpl extends DaoUtil<User> implements UserDAO{

	public UserDAOImpl() {
		super();
		data= new LinkedList<User>(DataGenerator.getInstance().getUsers());
	}
	
	
	
	@Override
	public <S extends User> S save(S value) {
		if (value.getUserID()==null) {
			value.setUserID(new Long(data.size()));
		}
		return super.save(value);
	}



	@Override
	public User findByUsernameAndUserPassword(String username, String pasword) {
		for (User user : data) {
			if (username.equals(user.getUsername())&& pasword.equals(user.getUserPassword())) {
				return user;
			}
		}
		throw new DaoTestException("Data Not Found.");
	}

	@Override
	public User findUserByPerson_PersonID(Long id) {
		for (User user : data) {
			if (id.equals(user.getPerson().getPersonID())) {
				return user;
			}
		}
		throw new DaoTestException("Data Not Found.");
	}

	
	

}
