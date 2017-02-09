package cu.uci.abcd.acquisition.impl;

import java.util.List;
import java.util.Map;

import cu.uci.abcd.acquisition.IManageRoomService;
import cu.uci.abcd.dao.management.library.RoomDAO;
import cu.uci.abcd.domain.management.library.Room;

public class ManageRoomService implements IManageRoomService {

	
	private RoomDAO roomServiceDao;

	@Override
	public List<Room> findAll(Long idLibrary) {		
		return (List<Room>) roomServiceDao.findAll();
	}
	
	public void bind(RoomDAO roomDAO, Map<?, ?> properties) {
		this.roomServiceDao = roomDAO;
	}

}
