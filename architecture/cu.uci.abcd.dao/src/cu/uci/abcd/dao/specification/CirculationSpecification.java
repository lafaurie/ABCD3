package cu.uci.abcd.dao.specification;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;

public class CirculationSpecification {

	public static Specification<LoanUser> searchLoanUserTypeInterLibrarianFragment(final String params, final Library library) {
		return new Specification<LoanUser>() {
			@Override
			public Predicate toPredicate(Root<LoanUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				Path<Nomenclator> loanUserState = root.get("loanUserState");
				Path<Nomenclator> loanUserType = root.get("loanUserType");
				Path<Person> person = root.get("person");
				Path<Library> libraryPath = person.get("library");

				Predicate predicateAND = null;
				Predicate predicateOR = null;

				predicateAND = criteriaBuilder.and(criteriaBuilder.notEqual(loanUserState.<Long> get("nomenclatorID"), Nomenclator.LOANUSER_STATE_INACTIVE), criteriaBuilder.and(criteriaBuilder.equal(loanUserType.<Long> get("nomenclatorID"), Nomenclator.LOANUSER_TYPE_LOAN_INTERLIBRARY)),
						criteriaBuilder.and(criteriaBuilder.equal(libraryPath, library)));

				if (params != null) {
					predicateOR = criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("firstName")), "%" + params.toUpperCase() + "%"), criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("dni")), "%" + params.toUpperCase() + "%"),
							criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("loanUserCode")), "%" + params.toUpperCase() + "%"));
				}
				if (predicateOR == null) {
					return predicateAND;
				} else {
					return criteriaBuilder.and(predicateAND, predicateOR);
				}
			}
		};
	}

	public static Specification<LoanUser> searchLoanUserOtherTypeFragment(final String params, final Library library) {
		return new Specification<LoanUser>() {
			@Override
			public Predicate toPredicate(Root<LoanUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				Path<Nomenclator> loanUserState = root.get("loanUserState");
				Path<Nomenclator> loanUserType = root.get("loanUserType");
				Path<Person> person = root.get("person");
				Path<Library> libraryPath = person.get("library");

				Predicate predicateAND = null;
				Predicate predicateOR = null;

				predicateAND = criteriaBuilder.and(criteriaBuilder.notEqual(loanUserState.<Long> get("nomenclatorID"), Nomenclator.LOANUSER_STATE_INACTIVE), criteriaBuilder.and(criteriaBuilder.notEqual(loanUserType.<Long> get("nomenclatorID"), Nomenclator.LOANUSER_TYPE_LOAN_INTERLIBRARY)),
						criteriaBuilder.and(criteriaBuilder.equal(libraryPath, library)));

				if (params != null) {
					predicateOR = criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("firstName")), "%" + params.toUpperCase() + "%"), criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("dni")), "%" + params.toUpperCase() + "%"),
							criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("loanUserCode")), "%" + params.toUpperCase() + "%"));
				}
				if (predicateOR == null) {
					return predicateAND;
				} else {
					return criteriaBuilder.and(predicateAND, predicateOR);
				}
			}
		};
	}

	public static Specification<LoanUser> searchLoanUserFragment(final String params, final Library library) {
		return new Specification<LoanUser>() {
			@Override
			public Predicate toPredicate(Root<LoanUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				Path<Nomenclator> loanUserState = root.get("loanUserState");
				Path<Person> person = root.get("person");
				Path<Library> libraryPath = person.get("library");

				Predicate predicateAND = null;
				Predicate predicateOR = null;

				predicateAND = criteriaBuilder.and(criteriaBuilder.notEqual(loanUserState.<Long> get("nomenclatorID"), Nomenclator.LOANUSER_STATE_INACTIVE), criteriaBuilder.and(criteriaBuilder.equal(libraryPath, library)));

				if (params != null) {
					predicateOR = criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("firstName")), "%" + params.toUpperCase() + "%"), criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("dni")), "%" + params.toUpperCase() + "%"),
							criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("loanUserCode")), "%" + params.toUpperCase() + "%"));
				}
				if (predicateOR == null) {
					return predicateAND;
				} else {
					return criteriaBuilder.and(predicateAND, predicateOR);
				}
			}
		};
	}

	public static Specification<LoanUser> searchLoanUserConsult(final String loan_user_code, final Room room_user, final Nomenclator loan_user_type_id, final Nomenclator loan_user_state, final Nomenclator facultyID, final Nomenclator specialityID, final String firstName, final String secondName,
			final String firstLast, final String secondLast, final Date fromDate, final Date toDate, final String DNI, final Library library) {
		return new Specification<LoanUser>() {
			@Override
			public Predicate toPredicate(Root<LoanUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();

				Path<Person> person = root.get("person");
				Path<Room> room = root.get("registeredatroom");
				Path<Nomenclator> loanUserType = root.get("loanUserType");
				Path<Nomenclator> loanUserState = root.get("loanUserState");
				Path<Date> date = root.get("registrationDate");
				Path<Nomenclator> faculty = root.get("faculty");
				Path<Nomenclator> speciality = root.get("speciality");
				Path<Library> libraryPath = person.get("library");

				if (firstName != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("firstName")), "%" + firstName.toUpperCase() + "%")));
				}
				if (secondName != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("secondName")), "%" + secondName.toUpperCase() + "%")));
				}
				if (firstLast != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("firstSurname")), "%" + firstLast.toUpperCase() + "%")));
				}
				if (secondLast != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("secondSurname")), "%" + secondLast.toUpperCase() + "%")));
				}
				if (DNI != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("dni")), "%" + DNI.toUpperCase() + "%")));
				}
				if (room_user != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(room, room_user)));
				}
				if (loan_user_code != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("loanUserCode")), "%" + loan_user_code.toUpperCase() + "%")));
				}
				if (loan_user_type_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanUserType, loan_user_type_id)));
				}
				if (facultyID != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(faculty, facultyID)));
				}
				if (specialityID != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(speciality, specialityID)));
				}
				if (loan_user_state != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanUserState, loan_user_state)));
				}
				if (fromDate != null && toDate != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.between(date, fromDate, toDate)));
				}

				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(libraryPath, library)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<LoanUser> searchCodeLoanUser(final String loan_user_code) {
		return new Specification<LoanUser>() {
			@Override
			public Predicate toPredicate(Root<LoanUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();

				if (loan_user_code != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("loanUserCode")), "%" + loan_user_code.toUpperCase() + "%")));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<LoanObject> searchLoanObject(final String title, final String author, final Nomenclator record_type_id, final Nomenclator loan_object_state, final String inventory_number, final String control_number, final Date fromDate, final Date toDate, final Room room1,
			final Library library) {
		return new Specification<LoanObject>() {
			@Override
			public Predicate toPredicate(Root<LoanObject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();

				Path<Nomenclator> recordtype = root.get("recordType");
				Path<Nomenclator> loanObjectState = root.get("loanObjectState");
				Path<Date> date = root.get("registrationDate");
				Path<Room> roomPath = root.get("room");
				Path<Library> libraryPath = root.get("libraryOwner");

				if (title != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("title")), "%" + title.toUpperCase() + "%")));
				}
				if (author != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("author")), "%" + author.toUpperCase() + "%")));
				}
				if (inventory_number != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("inventorynumber")), "%" + inventory_number.toUpperCase() + "%")));
				}
				if (control_number != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("controlNumber")), "%" + control_number.toUpperCase() + "%")));
				}
				if (record_type_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(recordtype, record_type_id)));
				}
				if (loan_object_state != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanObjectState, loan_object_state)));
				}
				if (fromDate != null && toDate != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.between(date, fromDate, toDate)));
				}
				if (room1 != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(roomPath, room1)));
				}
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(libraryPath, library)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<LoanObject> searchLoanObjectInventoryNumber(final String inventory_number, final Library library, final List<Room> listWorkerRoom) {
		return new Specification<LoanObject>() {
			@Override
			public Predicate toPredicate(Root<LoanObject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Nomenclator> loanObjectState = root.get("loanObjectState");
				Path<Library> libraryPath = root.get("libraryOwner");
				Path<Room> roomPath = root.get("room");

				Predicate predicateAND = null;
				Predicate predicateOR = null;

				// for (int i = 0; i < listWorkerRoom.size(); i++) {

				predicateList.add(roomPath.in(listWorkerRoom));

				// predicateList.add(criteriaBuilder.isMember(roomPath,
				// listWorkerRoom));
				// predicateList.add(criteriaBuilder.or(criteriaBuilder.equal(roomPath,
				// listWorkerRoom.get(1))));
				// }

				if (inventory_number.length() != 0) {
					predicateAND = criteriaBuilder.and(criteriaBuilder.and(criteriaBuilder.equal(loanObjectState.<Long> get("nomenclatorID"), Nomenclator.LOANOBJECT_STATE_AVAILABLE)),
							criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("inventorynumber")), "%" + inventory_number.toUpperCase() + "%")), criteriaBuilder.and(criteriaBuilder.equal(libraryPath, library)));

					predicateList.add(predicateAND);
				} else {
					predicateAND = criteriaBuilder.and(criteriaBuilder.and(criteriaBuilder.equal(loanObjectState.<Long> get("nomenclatorID"), Nomenclator.LOANOBJECT_STATE_AVAILABLE)), criteriaBuilder.and(criteriaBuilder.equal(libraryPath, library)));

					predicateList.add(predicateAND);
				}

				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};
	}

	public static Specification<Penalty> searchPenaltyConsult(final Nomenclator penalty_type, final Nomenclator penalty_state, final Nomenclator loan_user_type_id, final String loan_user_code, final String firstName, final String secondName, final String firstLast, final String secondLast,
			final Date fromDate, final Date toDate, final String title, final String author, final String control_number, final Library library) {
		return new Specification<Penalty>() {
			@Override
			public Predicate toPredicate(Root<Penalty> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Nomenclator> penaltyType = root.get("penaltyType");
				Path<Nomenclator> penaltyState = root.get("penaltyState");
				Path<LoanUser> buscarUser = root.get("loanUser");
				Path<Person> person = buscarUser.get("person");
				Path<Nomenclator> loanUserType = buscarUser.get("loanUserType");
				Path<LoanObject> buscarObject = root.get("loanObject");
				Path<Date> date = root.get("effectiveDate");
				Path<Library> libraryPath = root.get("library");

				if (penalty_type != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(penaltyType, penalty_type)));
				}
				if (penalty_state != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(penaltyState, penalty_state)));
				}
				if (loan_user_type_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanUserType, loan_user_type_id)));
				}
				if (loan_user_code != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(buscarUser.<String> get("loanUserCode")), "%" + loan_user_code.toUpperCase() + "%")));
				}
				if (firstName != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("firstName")), "%" + firstName.toUpperCase() + "%")));
				}
				if (secondName != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("secondName")), "%" + secondName.toUpperCase() + "%")));
				}
				if (firstLast != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("firstSurname")), "%" + firstLast.toUpperCase() + "%")));
				}
				if (secondLast != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("secondSurname")), "%" + secondLast.toUpperCase() + "%")));
				}
				if (fromDate != null && toDate != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.between(date, fromDate, toDate)));
				}

				if (title != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(buscarObject.<String> get("title")), "%" + title.toUpperCase() + "%")));
				}
				if (author != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(buscarObject.<String> get("author")), "%" + author.toUpperCase() + "%")));
				}
				if (control_number != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(buscarObject.<String> get("controlNumber")), "%" + control_number.toUpperCase() + "%")));
				}

				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(libraryPath, library)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Penalty> searchPenaltyByLoanUserCurrent(final Long loan_user_id) {
		return new Specification<Penalty>() {
			@Override
			public Predicate toPredicate(Root<Penalty> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanUser> buscarUser = root.get("loanUser");
				Path<Penalty> statePenalty = root.get("penaltyState");

				if (loan_user_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), loan_user_id)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(statePenalty.<Long> get("nomenclatorID"), Nomenclator.PENALTY_STATE_PAID)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(statePenalty.<Long> get("nomenclatorID"), Nomenclator.PENALTY_STATE_INACTIVE)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Penalty> searchPenaltyByLoanUserHistory(final Long loan_user_id) {
		return new Specification<Penalty>() {
			@Override
			public Predicate toPredicate(Root<Penalty> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanUser> buscarUser = root.get("loanUser");
				Path<Penalty> statePenalty = root.get("penaltyState");

				if (loan_user_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), loan_user_id)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(statePenalty.<Long> get("nomenclatorID"), Nomenclator.PENALTY_STATE_PENDING_PAYMENT)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(statePenalty.<Long> get("nomenclatorID"), Nomenclator.PENALTY_STATE_ACTIVE)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Penalty> searchPenaltyByLoanObject(final Long loan_object_id) {
		return new Specification<Penalty>() {
			@Override
			public Predicate toPredicate(Root<Penalty> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanObject> buscarObject = root.get("loanObject");
				Path<Penalty> statePenalty = root.get("penaltyState");
				if (loan_object_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarObject.<Long> get("loanObjectID"), loan_object_id)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(statePenalty.<Long> get("nomenclatorID"), Nomenclator.PENALTY_STATE_PENDING_PAYMENT)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(statePenalty.<Long> get("nomenclatorID"), Nomenclator.PENALTY_STATE_ACTIVE)));

				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}
	
	public static Specification<Penalty> searchPenaltyByLoanObject2(final Long loan_object_id) {
		return new Specification<Penalty>() {
			@Override
			public Predicate toPredicate(Root<Penalty> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanObject> buscarObject = root.get("loanObject");
				Path<Penalty> statePenalty = root.get("penaltyState");
				if (loan_object_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarObject.<Long> get("loanObjectID"), loan_object_id)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(statePenalty.<Long> get("nomenclatorID"), Nomenclator.PENALTY_STATE_PENDING_PAYMENT)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(statePenalty.<Long> get("nomenclatorID"), Nomenclator.PENALTY_STATE_ACTIVE)));

				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Reservation> searchReservationConsult(final Nomenclator loan_user_type_id, final String loan_user_code, final String firstName, final String secondName, final String firstSurname, final String secondSurname, final String title, final Nomenclator record_type_id,
			final Nomenclator reservation_state, final Nomenclator reservation_type, final Date dateRegister, final Date endDateRegister, final Library library) {
		return new Specification<Reservation>() {
			@Override
			public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanUser> buscarUser = root.get("loanUser");
				Path<Person> person = buscarUser.get("person");
				Path<Nomenclator> reservationState = root.get("state");
				Path<Nomenclator> reservationType = root.get("reservationType");
				Path<Nomenclator> loanUserType = buscarUser.get("loanUserType");
				Path<Nomenclator> loanObjectType = root.get("objecttype");
				Path<Date> date = root.get("reservationDate");
				Path<Library> libraryPath = person.get("library");

				if (firstName != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("firstName")), "%" + firstName.toUpperCase() + "%")));
				}
				if (secondName != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("secondName")), "%" + secondName.toUpperCase() + "%")));
				}
				if (firstSurname != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("firstSurname")), "%" + firstSurname.toUpperCase() + "%")));
				}
				if (secondSurname != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("secondSurname")), "%" + secondSurname.toUpperCase() + "%")));
				}

				if (loan_user_code != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(buscarUser.<String> get("loanUserCode")), "%" + loan_user_code.toUpperCase() + "%")));
				}
				if (loan_user_type_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanUserType, loan_user_type_id)));
				}
				if (title != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("title")), "%" + title.toUpperCase() + "%")));
				}
				if (record_type_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanObjectType, record_type_id)));
				}
				if (reservation_state != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(reservationState, reservation_state)));
				}
				if (reservation_type != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(reservationType, reservation_type)));
				}
				if (dateRegister != null && endDateRegister != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.between(date, dateRegister, endDateRegister)));
				}

				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(libraryPath, library)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Reservation> searchReservationLoanUserCurrent(final Long loan_user_id) {
		return new Specification<Reservation>() {
			@Override
			public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanUser> buscarUser = root.get("loanUser");
				Path<Reservation> state = root.get("state");
				if (loan_user_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), loan_user_id)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(state.<Long> get("nomenclatorID"), Nomenclator.RESERVATION_STATE_CANCELLED)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(state.<Long> get("nomenclatorID"), Nomenclator.RESERVATION_STATE_EXECUTED)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Reservation> searchReservationLoanUserHistory(final Long loan_user_id) {
		return new Specification<Reservation>() {
			@Override
			public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanUser> buscarUser = root.get("loanUser");
				Path<Reservation> state = root.get("state");

				if (loan_user_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), loan_user_id)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(state.<Long> get("nomenclatorID"), Nomenclator.RESERVATION_STATE_PENDING)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Reservation> searchReservationLoanObject(final Long loan_object_id) {
		return new Specification<Reservation>() {
			@Override
			public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanObject> buscarObject = root.get("loanObject");
				Path<Reservation> state = root.get("state");
				if (loan_object_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarObject.<Long> get("loanObjectID"), loan_object_id)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(state.<Long> get("nomenclatorID"), Nomenclator.RESERVATION_STATE_PENDING)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Transaction> searchTransaction(final String inventory_number, final String title, final Nomenclator record_type_id, final Nomenclator loan_user_type_id, final String loan_user_code, final String first_Name, final String second_Name, final String first_Surname,
			final String second_Surname, final Nomenclator loan_type, final Nomenclator transaction_state, final Date dateRegister, final Date endDateRegister, final Room loanObjectRoom, final Library library) {
		return new Specification<Transaction>() {
			@Override
			public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Nomenclator> loanType = root.get("loanType");
				Path<Nomenclator> state = root.get("state");
				Path<LoanUser> buscarUser = root.get("loanUser");
				Path<Person> person = buscarUser.get("person");
				Path<LoanObject> buscarObject = root.get("loanObject");
				Path<Nomenclator> loanUserType = buscarUser.get("loanUserType");
				Path<Nomenclator> recordType = buscarObject.get("recordType");
				Path<Date> date = root.get("transactionDateTime");
				Path<Room> roomPath = buscarObject.get("room");
				Path<Library> libraryPath = person.get("library");

				predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<Boolean> get("isparent"), false)));

				if (inventory_number != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(buscarObject.<String> get("inventorynumber")), "%" + inventory_number.toUpperCase() + "%")));
				}
				if (title != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(buscarObject.<String> get("title")), "%" + title.toUpperCase() + "%")));
				}
				if (loan_user_code != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(buscarUser.<String> get("loanUserCode")), "%" + loan_user_code.toUpperCase() + "%")));
				}
				if (loan_user_type_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanUserType, loan_user_type_id)));
				}
				if (first_Name != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("firstName")), "%" + first_Name.toUpperCase() + "%")));
				}
				if (second_Name != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("secondName")), "%" + second_Name.toUpperCase() + "%")));
				}
				if (first_Surname != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("firstSurname")), "%" + first_Surname.toUpperCase() + "%")));
				}
				if (second_Surname != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(person.<String> get("secondSurname")), "%" + second_Surname.toUpperCase() + "%")));
				}
				if (record_type_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(recordType, record_type_id)));
				}
				if (transaction_state != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(state, transaction_state)));
				}
				if (loan_type != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanType, loan_type)));
				}
				if (loanObjectRoom != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(roomPath, loanObjectRoom)));
				}
				if (dateRegister != null && endDateRegister != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.between(date, dateRegister, endDateRegister)));
				}
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(libraryPath, library)));
				}

				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Transaction> searchTransactionByLoanUserReturn(final Long loan_user_id) {
		return new Specification<Transaction>() {
			@Override
			public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanUser> buscarUser = root.get("loanUser");
				Path<Nomenclator> buscarTransactionState = root.get("state");

				if (loan_user_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), loan_user_id)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_RETURN)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_NOT_DELIVERED)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Transaction> searchTransactionByLoanUserCurrent(final Long loan_user_id) {
		return new Specification<Transaction>() {
			@Override
			public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanUser> buscarUser = root.get("loanUser");
				Path<Nomenclator> buscarTransactionState = root.get("state");

				if (loan_user_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), loan_user_id)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_RETURN)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_NOT_DELIVERED)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Transaction> searchTransactionByLoanUserHistory(final Long loan_user_id) {
		return new Specification<Transaction>() {
			@Override
			public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanUser> buscarUser = root.get("loanUser");
				Path<Nomenclator> buscarTransactionState = root.get("state");

				if (loan_user_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), loan_user_id)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_BORROWED)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_RENEW)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Transaction> searchTransactionByLoanObject(final Long loan_object_id) {
		return new Specification<Transaction>() {
			@Override
			public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanObject> buscarObject = root.get("loanObject");
				Path<Nomenclator> buscarTransactionState = root.get("state");

				if (loan_object_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarObject.<Long> get("loanObjectID"), loan_object_id)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_BORROWED)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_RENEW)));

				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}
	
	public static Specification<Transaction> searchTransactionByLoanObject2(final Long loan_object_id) {
		return new Specification<Transaction>() {
			@Override
			public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanObject> buscarObject = root.get("loanObject");

				if (loan_object_id != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarObject.<Long> get("loanObjectID"), loan_object_id)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Transaction> searchTransactionByRegisterReturn(final String params, final Library library, final List<Room> listWorkerRoom) {
		return new Specification<Transaction>() {
			@Override
			public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				Path<LoanObject> buscarObject = root.get("loanObject");
				Path<LoanUser> buscarUser = root.get("loanUser");
				Path<Person> buscarPerson = buscarUser.get("person");
				Path<Nomenclator> buscarTransactionState = root.get("state");
				Path<Library> libraryPath = buscarPerson.get("library");
				Path<Room> roomLoanObject = buscarObject.get("room");

				Predicate predicateAND = null;
				Predicate predicateOR = null;

				// predicateList.add(roomLoanObject.in(listWorkerRoom));

				predicateAND = criteriaBuilder.and(criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_RETURN)),
						criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_NOT_DELIVERED)), criteriaBuilder.and(criteriaBuilder.equal(root.<Boolean> get("isparent"), false)),
						criteriaBuilder.and(criteriaBuilder.equal(libraryPath, library)), criteriaBuilder.and(roomLoanObject.in(listWorkerRoom)));

				if (params != null) {
					predicateOR = criteriaBuilder.or(criteriaBuilder.equal(buscarObject.<String> get("inventorynumber"), params), criteriaBuilder.equal(buscarPerson.<String> get("dni"), params),
							criteriaBuilder.like(criteriaBuilder.upper(buscarUser.<String> get("loanUserCode")), "%" + params.toUpperCase() + "%"));
				}
				if (predicateOR == null) {
					return predicateAND;
				} else {
					return criteriaBuilder.and(predicateAND, predicateOR);
				}

			}
		};

	}
    
	public static Specification<Reservation> searchReservationByLoanObject(final Long idLoanObject) {
		return new Specification<Reservation>() {
			@Override
			public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanObject> findLoanObject = root.get("reservationList");

				if (findLoanObject != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findLoanObject.<Long> get("id"), idLoanObject)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

}
