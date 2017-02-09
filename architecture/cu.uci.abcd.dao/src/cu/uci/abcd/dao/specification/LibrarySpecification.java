package cu.uci.abcd.dao.specification;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.Calendar;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abcd.domain.management.library.Coin;
import cu.uci.abcd.domain.management.library.FormationCourse;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Provider;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.library.Schedule;
import cu.uci.abcd.domain.management.library.Worker;

public class LibrarySpecification {

	public static Specification<Library> searchLibrary(final String libraryName) {
		return new Specification<Library>() {
			@Override
			public Predicate toPredicate(Root<Library> root,
					CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				if (libraryName != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root
									.<String> get("libraryName")), "%"
									+ libraryName.replaceAll(" +", " ").trim().toUpperCase() + "%")));
				}
				return criteriaBuilder.and(predicateList
						.toArray(new Predicate[predicateList.size()]));
			}
		};

	}

	public static Specification<FormationCourse> searchFormationCourse(final Library library, final String courseName, final int clasification, final Room room, final int addressedTo, final Person proffesor, final Date fromDate, final Date toDate) {
		return new Specification<FormationCourse>() {
			@Override
			public Predicate toPredicate(Root<FormationCourse> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Date> date = root.get("startDate");
				Path<Library> myLibrary = root.get("library");
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myLibrary, library)));
				}
				
				if (room != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<Room> get("room"), room)));
				}
				
				if (proffesor != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<Person> get("professor"), proffesor)));
				}
				
				if (addressedTo >0) {
					switch (addressedTo) {
					case 1:
						predicateList
								.add(criteriaBuilder.and(criteriaBuilder.like(
										criteriaBuilder
												.substring(root
														.<String> get("code"),
														1, 1),
										"1")));
						break;
					case 2:
						predicateList
						.add(criteriaBuilder.and(criteriaBuilder.like(
								criteriaBuilder
										.substring(root
												.<String> get("code"),
												2, 1),
								"1")));
						break;
					case 3:
						predicateList
						.add(criteriaBuilder.and(criteriaBuilder.like(
								criteriaBuilder
										.substring(root
												.<String> get("code"),
												3, 1),
								"1")));
						break;
					case 4:
						predicateList
						.add(criteriaBuilder.and(criteriaBuilder.like(
								criteriaBuilder
										.substring(root
												.<String> get("code"),
												4, 1),
								"1")));
						break;
					case 5:
						predicateList
						.add(criteriaBuilder.and(criteriaBuilder.like(
								criteriaBuilder
										.substring(root
												.<String> get("code"),
												5, 1),
								"1")));
						break;
					case 6:
						predicateList
						.add(criteriaBuilder.and(criteriaBuilder.like(
								criteriaBuilder
										.substring(root
												.<String> get("code"),
												6, 1),
								"1")));
						break;
					}
					
				}
				
				if (courseName != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root.<String> get("courseName")), "%"
									+ courseName.replaceAll(" +", " ").trim().toUpperCase() + "%")));
				}
				if (clasification > 0) {
					switch (clasification) {
					case 1:
						//predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<boolean> get("professor"), null)));
						predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<Boolean> get("received"), false)));
						break;

					case 2:
						predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<Boolean> get("received"), true)));
						break;
					}
					
				}
				if (fromDate != null && toDate != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.between(date, fromDate, toDate)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};

	}

	public static Specification<Room> searchRoom(final Library library, final String roomName) {
		return new Specification<Room>() {
			@Override
			public Predicate toPredicate(Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Library> myLibrary = root.get("library");

				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myLibrary, library)));
				}
				if (roomName != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root
									.<String> get("roomName")), "%"
									+ roomName.replaceAll(" +", " ").trim().toUpperCase() + "%")));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Worker> searchWorker(final Library library, final String firstName, final String secondName, final String firstSurname, final String secondSurname, final String dni,
			final Nomenclator workerType, final Nomenclator gender, final Date fromDate, final Date toDate) {
		return new Specification<Worker>() {
			@Override
			public Predicate toPredicate(Root<Worker> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Person> persona = root.get("person");
				Path<Nomenclator> workerTypeObject = root.get("workerType");
				Path<Nomenclator> genderPerson = persona.get("sex");
				Path<Date> date = root.get("registerDate");
				Path<Library> myLibrary = persona.get("library");
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myLibrary, library)));
				}
				if (firstName != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(persona
									.<String> get("firstName")), "%"
									+ firstName.replaceAll(" +", " ").trim().toUpperCase() + "%")));
				}

				if (secondName != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.isNotNull(persona.<String> get("secondName"))));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(persona
									.<String> get("secondName")), "%"
									+ secondName.replaceAll(" +", " ").trim().toUpperCase() + "%")));
				}
				if (firstSurname != null) {
					/*
					predicateList.add(criteriaBuilder.or(criteriaBuilder.like(
							persona.<String> get("firstSurname"), "%"
									+ firstSurname + "%")));
					*/
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(persona
									.<String> get("firstSurname")), "%"
									+ firstSurname.replaceAll(" +", " ").trim().toUpperCase() + "%")));
				}
				if (secondSurname != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.isNotNull(persona.<String> get("secondSurname"))));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(persona
									.<String> get("secondSurname")), "%"
									+ secondSurname.replaceAll(" +", " ").trim().toUpperCase() + "%")));
				}

				if (workerType != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(workerTypeObject, workerType)));
				}
				if (dni != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(persona
									.<String> get("dni")), "%"
									+ dni.toUpperCase() + "%")));
				}
				if (gender != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(genderPerson, gender)));
				}
				if (fromDate != null && toDate != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.between(date, fromDate, toDate)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};

	}

	public static Specification<Calendar> searchCalendar(final Library library, final Nomenclator dayType, final Integer year) {
		return new Specification<Calendar>() {
			@Override
			public Predicate toPredicate(Root<Calendar> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Library> myLibrary = root.get("library");
				Path<Nomenclator> myDayType = root.get("daytype");
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myLibrary, library)));
				}
				if (dayType != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myDayType, dayType)));
				}
				if (year !=null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<Integer> get("year"), year)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<CirculationRule> searchCirculationRule(final Library library, final Nomenclator recordType, final Nomenclator loanUserType) {
		return new Specification<CirculationRule>() {
			@Override
			public Predicate toPredicate(Root<CirculationRule> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Nomenclator> myRecordType = root.get("recordType");
				Path<Nomenclator> myLoanUserType = root.get("loanUserType");
				Path<Library> myLibrary = root.get("library");
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myLibrary, library)));
				}
				if (recordType != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myRecordType, recordType)));
				}
				if (loanUserType != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myLoanUserType, loanUserType)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};

	}

	public static Specification<Coin> searchCoin(final Library library, final String coinName, final Nomenclator identification) {
		return new Specification<Coin>() {
			@Override
			public Predicate toPredicate(Root<Coin> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Nomenclator> coinType = root.get("coinType");
				Path<Library> myLibrary = root.get("library");
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myLibrary, library)));
				}
				
				if (coinName != null) {
					/*
					predicateList
							.add(criteriaBuilder.and(criteriaBuilder.like(
									root.<String> get("coinName"), "%"
											+ coinName + "%")));
					*/
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root.<String> get("coinName")), "%"
									+ coinName.replaceAll(" +", " ").trim().toUpperCase() + "%")));
					
				}
				if (identification != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(coinType, identification)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Provider> searchProvider(final Library library, final String nameProvider, final String providerNit, final String providerRif, final Nomenclator providerState,
			final Nomenclator cangeConsult, final Nomenclator commercialConsult, final Nomenclator donationConsult) {
		return new Specification<Provider>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Predicate toPredicate(Root<Provider> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();

				Path<Nomenclator> myEntityState = root.get("providerState");
				Path<Library> myLibrary = root.get("library");
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myLibrary, library)));
				}
				if (cangeConsult != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder
							.isMember(cangeConsult,
									root.<Collection> get("types"))));
				}
				if (commercialConsult != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder
							.isMember(commercialConsult,
									root.<Collection> get("types"))));
				}
				if (donationConsult != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder
							.isMember(donationConsult,
									root.<Collection> get("types"))));
				}

				// Join<Provider, Industry> industryRoot =
				// provider.join(Provider_.industries)
				// industryRoot.in(industries);

				if (nameProvider != null) {
					/*
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							root.<String> get("providerName"), "%"
									+ nameProvider + "%")));
					*/
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root.<String> get("providerName")), "%"
									+ nameProvider.replaceAll(" +", " ").trim().toUpperCase() + "%")));
					
				}
				if (providerNit != null) {
					/*
					predicateList
							.add(criteriaBuilder.and(criteriaBuilder.like(
									root.<String> get("nit"), "%" + providerNit
											+ "%")));
											*/
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root.<String> get("nit")), "%"
									+ providerNit.replaceAll(" +", " ").trim().toUpperCase() + "%")));
				}
				if (providerRif != null) {
					/*
					predicateList
							.add(criteriaBuilder.and(criteriaBuilder.like(
									root.<String> get("rif"), "%" + providerRif
											+ "%")));
											*/
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root.<String> get("rif")), "%"
									+ providerRif.replaceAll(" +", " ").trim().toUpperCase() + "%")));
				}
				if (providerState != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myEntityState, providerState)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Schedule> searchSchedule(final Library library) {
		return new Specification<Schedule>() {
			@Override
			public Predicate toPredicate(Root<Schedule> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Library> myLibrary = root.get("library");
				
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myLibrary, library)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}
}
