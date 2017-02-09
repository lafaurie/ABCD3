package cu.uci.abcd.management.security;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.security.AccessRecord;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface IAccessRecordService {

	public Page<AccessRecord> findAll(Library library, String firstName,
			String secondName, String firstSurname, String secondSurname,
			String identification, Room room, Date fromDate, Date toDate,
			int page, int size, int direction, String orde);

	public AccessRecord addAccessrecord(AccessRecord accessrecord);

	public AccessRecord readAccessRecord(Long idAccessRecord);

	public void deleteAccessRecord(Long idAccessRecord);

	public List<Room> findRoomByLibrary(Long idLibrary);

}
