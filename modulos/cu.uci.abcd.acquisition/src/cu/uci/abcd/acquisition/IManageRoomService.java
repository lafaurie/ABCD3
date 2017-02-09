package cu.uci.abcd.acquisition;

import java.util.List;

import cu.uci.abcd.domain.management.library.Room;

/**
 * 
 * @author alberto
 * @version 1.0.0 31/01/2017
 *
 */
public interface IManageRoomService {
	
	public List<Room> findAll(Long idLibrary);


}
