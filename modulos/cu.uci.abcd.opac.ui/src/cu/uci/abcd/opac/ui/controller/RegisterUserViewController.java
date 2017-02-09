package cu.uci.abcd.opac.ui.controller;

import java.util.ArrayList;
import java.util.List;
   
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;

public class RegisterUserViewController implements ViewController {
	

	private ProxyController proxyController;

	public User addUser(User user) {
		return proxyController.getIOpacRegisterUserService().addUser(user);
	}

	public User readUser(Long idUser) {
		return proxyController.getIOpacRegisterUserService().findUser(idUser);
	}

	public List<User> findAllUsers() {
		if (proxyController.getIOpacRegisterUserService() != null) {
			return proxyController.getIOpacRegisterUserService().findAllUser();
		}
		return new ArrayList<User>();
	}
	
	public Library getLibraryByUser(Long userID){
		
		return proxyController.getIOpacLibraryService().findLibrary(userID);
		
	}
	
	public void setProxyController(ProxyController proxyController) {
		this.proxyController = proxyController;
	}
}


