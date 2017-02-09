package cu.uci.abcd.administration.library.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.administration.library.IRoomService;
import cu.uci.abcd.dao.management.library.RoomDAO;
import cu.uci.abcd.dao.specification.LibrarySpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class RoomServiceImpl implements IRoomService {
	private RoomDAO roomDAO;

	public void bind(RoomDAO roomDAO, Map<?, ?> properties) {
		this.roomDAO = roomDAO;
	}

	@Override
	public Room addRoom(Room room) {
		return roomDAO.save(room);
	}

	@Override
	public Page<Room> findAll(Library library, String roomName, int page,
			int size, int direction, String order) {
		return roomDAO.findAll(
				LibrarySpecification.searchRoom(library, roomName),
				PageSpecification.getPage(page, size, direction, order));
	}

	@Override
	public Room readRoom(Long idRoom) {
		return roomDAO.findOne(idRoom);
	}

	@Override
	public void deleteRoom(Long idRoom) {
		roomDAO.delete(idRoom);
	}

	@Override
	public Room findRoomByName(Long idLibrary, String roomName) {
		return roomDAO.findRoomByLibrary_LibraryIDAndRoomNameIgnoreCase(
				idLibrary, roomName);
	}

	@Override
	public List<Room> findAll(Long idLibrary) {
		return roomDAO.findDistinctRoomByLibrary_LibraryID(idLibrary);
	}

}
