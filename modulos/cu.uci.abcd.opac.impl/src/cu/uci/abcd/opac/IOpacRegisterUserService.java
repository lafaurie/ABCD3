package cu.uci.abcd.opac;

import java.util.List;

import cu.uci.abcd.domain.common.User;
//FIXME FALTAN COMENTARIOS DE INTERFACE
public interface IOpacRegisterUserService {

	public User addUser(User account);
    
	public User updateUser(Long idUser);
	
	public User findUser(Long idUser);
	     
	public List<User> findAllUser();	    

	
	
}
