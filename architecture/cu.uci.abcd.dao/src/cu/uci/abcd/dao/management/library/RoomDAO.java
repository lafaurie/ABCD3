package cu.uci.abcd.dao.management.library;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.management.library.Room;

public interface RoomDAO extends PagingAndSortingRepository<Room, Long>, JpaSpecificationExecutor<Room> {

	public List<Room> findDistinctRoomByLibrary_LibraryID(Long libraryID);
	
	public Room findRoomByLibrary_LibraryIDAndRoomNameIgnoreCase(Long idLibrary, String roomName);
}
