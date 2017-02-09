package cu.uci.abcd.management.security.impl.services.management;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dao.management.library.RoomDAO;
import cu.uci.abcd.dao.management.security.AccessRecordDAO;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.dao.specification.SecuritySpecification;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.security.AccessRecord;
import cu.uci.abcd.management.security.IAccessRecordService;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class AccessRecordServiceImpl implements IAccessRecordService {
	private AccessRecordDAO accessRecordDAO;
	private RoomDAO roomDAO;

	public void bindAccessRecordDao(AccessRecordDAO accessRecordDAO,
			Map<?, ?> properties) {
		this.accessRecordDAO = accessRecordDAO;
	}

	public void bindRoomDao(RoomDAO roomDAO, Map<?, ?> properties) {
		this.roomDAO = roomDAO;
	}

	@Override
	public Page<AccessRecord> findAll(Library library, String firstName,
			String secondName, String firstSurname, String secondSurname,
			String identification, Room room, Date fromDate, Date toDate,
			int page, int size, int direction, String order) {
		return accessRecordDAO.findAll(SecuritySpecification
				.searchAccessRecords(library, firstName, secondName,
						firstSurname, secondSurname, identification, room,
						fromDate, toDate), PageSpecification.getPage(page,
				size, direction, order));
	}

	@Override
	public AccessRecord addAccessrecord(AccessRecord accessrecord) {
		return accessRecordDAO.save(accessrecord);
	}

	public AccessRecord readAccessRecord(Long idAccessRecord) {
		return accessRecordDAO.findOne(idAccessRecord);
	}

	public void deleteAccessRecord(Long idAccessRecord) {
		accessRecordDAO.delete(idAccessRecord);

	}

	@Override
	public List<Room> findRoomByLibrary(Long idLibrary) {
		return roomDAO.findDistinctRoomByLibrary_LibraryID(idLibrary);
	}

}
