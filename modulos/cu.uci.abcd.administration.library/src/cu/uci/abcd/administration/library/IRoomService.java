package cu.uci.abcd.administration.library;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface IRoomService {

	public Room addRoom(Room room);

	public Page<Room> findAll(Library library, String roomName, int page,
			int size, int direction, String order);

	public Room readRoom(Long idRoom);

	public void deleteRoom(Long idRoom);

	public Room findRoomByName(Long idLibrary, String roomName);

	public List<Room> findAll(Long idLibrary);

}
