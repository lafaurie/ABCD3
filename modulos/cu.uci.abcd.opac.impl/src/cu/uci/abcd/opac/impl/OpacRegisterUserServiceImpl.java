package cu.uci.abcd.opac.impl;

import java.util.List;
import java.util.Map;

import cu.uci.abcd.dao.common.UserDAO;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.opac.IOpacRegisterUserService;
   
public class OpacRegisterUserServiceImpl implements IOpacRegisterUserService {
	
	UserDAO userDAO;	

	@Override
	public User addUser(User account) {
		return userDAO.save(account);
	}

	@Override
	public User findUser(Long idAccount) {
		return userDAO.findOne(idAccount);	
	}

	@Override
	public List<User> findAllUser() {
		return (List<User>) userDAO.findAll();
	}

	@Override
	public User updateUser(Long id) {		
		return null;
	}	
	
	public void bind(UserDAO userDao, Map<String, Object> properties) {
		this.userDAO = userDao;
		System.out.println("servicio registrado");
	}

	
	
}