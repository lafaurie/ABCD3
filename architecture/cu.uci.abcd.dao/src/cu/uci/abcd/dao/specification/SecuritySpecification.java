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

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Room;
import cu.uci.abcd.domain.management.security.AccessRecord;
import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.domain.management.security.Permission;
import cu.uci.abcd.domain.management.security.Profile;

public class SecuritySpecification {

	public static Specification<Person> searchPerson(final Library library, final String firstNameConsult, final String secondNameConsult, final String firstSurnameConsult, final String secondSurnameConsult, final String identificationConsult) {
		return new Specification<Person>() {
			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Path<Library> myLibrary = root.get("library");
				List<Predicate> predicateList = new ArrayList<Predicate>();
				
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myLibrary, library)));
				}
				if (firstNameConsult != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root
									.<String> get("firstName")), "%"
									+ firstNameConsult.replaceAll(" +", " ").trim().toUpperCase() + "%")));		
				}
				if (secondNameConsult != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.<String> get("secondName"))));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root
									.<String> get("secondName")), "%"
									+ secondNameConsult.replaceAll(" +", " ").trim().toUpperCase() + "%")));
				}
				if (firstSurnameConsult != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root
									.<String> get("firstSurname")), "%"
									+ firstSurnameConsult.replaceAll(" +", " ").trim().toUpperCase() + "%")));

				}
				if (secondSurnameConsult != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.<String> get("secondSurname"))));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root
									.<String> get("secondSurname")), "%"
									+ secondSurnameConsult.replaceAll(" +", " ").trim().toUpperCase() + "%")));
				}
				if (identificationConsult != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root
									.<String> get("dni")), "%"
									+ identificationConsult.toUpperCase() + "%")));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<User> searchUsers(final Library library, final int withPerson, final boolean autenticated, final String firstNameConsult, final String secondNameConsult, final String firstSurnameConsult, final String secondSurnameConsult, final String identificationConsult, final String userConsult,
			final boolean opacConsult, final boolean systemConsult, final Date fromDate, final Date toDate) {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();

				Path<Library> myLibrary = root.get("library");
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myLibrary, library)));
				}
				
				if (withPerson == 1) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.<Person> get("person"))));
					if (firstNameConsult != null) {
						
						//predicateList.add(criteriaBuilder.and(criteriaBuilder
						//		.like(root.<Person> get("person").<String> get(
							//			"firstName"), "%" + firstNameConsult
							//			+ "%")));
						
						predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
								criteriaBuilder.upper(root.<Person> get("person").
										<String> get("firstName")), "%"
										+ firstNameConsult.replaceAll(" +", " ").trim().toUpperCase() + "%")));
						

					}
					if (secondNameConsult != null) {
						
						predicateList.add(criteriaBuilder.and(criteriaBuilder
								.isNotNull(root.<Person> get("person")
										.<String> get("secondName"))));
						
						//predicateList.add(criteriaBuilder.and(criteriaBuilder
						//		.like(root.<Person> get("person").<String> get(
							//			"secondName"), "%" + secondNameConsult
							//			+ "%")));

						
						predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
								criteriaBuilder.upper(root.<Person> get("person").
										<String> get("secondName")), "%"
										+ secondNameConsult.replaceAll(" +", " ").trim().toUpperCase() + "%")));
						
						
						
					}
					if (firstSurnameConsult != null) {
						
						//predicateList.add(criteriaBuilder.and(criteriaBuilder
							//	.like(root.<Person> get("person").<String> get(
						//				"firstSurname"), "%"
						//				+ firstSurnameConsult + "%")));
						
						predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
								criteriaBuilder.upper(root.<Person> get("person").
										<String> get("firstSurname")), "%"
										+ firstSurnameConsult.replaceAll(" +", " ").trim().toUpperCase() + "%")));
						
						

					}
					if (secondSurnameConsult != null) {
						predicateList.add(criteriaBuilder.and(criteriaBuilder.isNotNull(root.<Person> get("person").<String> get("secondSurname"))));
						
						//predicateList.add(criteriaBuilder.and(criteriaBuilder
						//		.like(root.<Person> get("person").<String> get(
						//				"secondSurname"), "%"
							//			+ secondSurnameConsult + "%")));
						
						predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
								criteriaBuilder.upper(root.<Person> get("person").
										<String> get("secondSurname")), "%"
										+ secondSurnameConsult.replaceAll(" +", " ").trim().toUpperCase() + "%")));
						

					}
					if (identificationConsult != null) {
						
						//predicateList.add(criteriaBuilder.and(criteriaBuilder
						//		.like(root.<Person> get("person").<String> get(
						//				"DNI"), "%" + identificationConsult
						//				+ "%")));
						
						predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
								criteriaBuilder.upper(root.<Person> get("person").
										<String> get("dni")), "%"
										+ identificationConsult.toUpperCase() + "%")));
						

					}
				}
				if (userConsult != null) {
					
					//predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
					//		root.<String> get("username"), "%" + userConsult
					//				+ "%")));
					
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root
									.<String> get("username")), "%"
									+ userConsult.replaceAll(" +", " ").trim().toUpperCase() + "%")));
					

				}
				if (opacConsult == true && systemConsult == false) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<Boolean> get("opacuser"), opacConsult)));
				}
				if (systemConsult == true && opacConsult == false) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<Boolean> get("systemuser"), systemConsult)));
				}
				
				if (systemConsult == true && opacConsult == true) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<Boolean> get("systemuser"), systemConsult)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<Boolean> get("opacuser"), opacConsult)));
				}
				
				if (fromDate != null && toDate != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.between(root.<Date> get("creationDate"), fromDate, toDate)));
				}
				if (autenticated) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<Boolean> get("authenticated"), true)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<AccessRecord> searchAccessRecords(final Library library, final String firstName, final String secondName, final String firstSurname, final String seconSurname, final String identification, final Room room, final Date fromDate, final Date toDate) {
		return new Specification<AccessRecord>() {
			@Override
			public Predicate toPredicate(Root<AccessRecord> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Person> myPerson = root.get("person");
				//Path<Library> myLibrary = root.get("library");
				
				//Path<Library> myLibrary = root.<Room> get("room").<Library> get("library");
				Path<Library> myLibrary = root.<Library> get("library");
				
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myLibrary, library)));
				}
				
				if (firstName != null) {
					
					//predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
					//		myPerson.<String> get("firstName"), "%" + firstName
						//			+ "%")));
					

					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(myPerson
									.<String> get("firstName")), "%"
									+ firstName.replaceAll(" +", " ").trim().toUpperCase() + "%")));
					
					

				}
				if (secondName != null) {
					
					//predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
					//		myPerson.<String> get("secondName"), "%"
					//				+ secondName + "%")));
					
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(myPerson
									.<String> get("secondName")), "%"
									+ secondName.replaceAll(" +", " ").trim().toUpperCase() + "%")));

				}
				if (firstSurname != null) {
					
					//predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
						//	myPerson.<String> get("firstSurname"), "%"
						//			+ firstSurname + "%")));
					
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(myPerson
									.<String> get("firstSurname")), "%"
									+ firstSurname.replaceAll(" +", " ").trim().toUpperCase() + "%")));

				}
				if (seconSurname != null) {
					
					//predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
						//	myPerson.<String> get("secondSurname"), "%"
						//			+ seconSurname + "%")));
					
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(myPerson
									.<String> get("secondSurname")), "%"
									+ seconSurname.replaceAll(" +", " ").trim().toUpperCase() + "%")));

				}
				if (identification != null) {
					
					//predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
					//		myPerson.<String> get("DNI"), "%" + identification
						//			+ "%")));
					
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(myPerson
									.<String> get("dni")), "%"
									+ identification.toUpperCase() + "%")));

				}

				Path<Room> myRoom = root.get("room");
				if (room != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myRoom, room)));
				}
				if (fromDate != null && toDate != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.between(root.<Date> get("accessDate"), fromDate, new Date(toDate.getTime() + 24 * 60 * 60 * 1000))));
				}
				// Path<Timestamp> date = root.get("accessDate");
				// Date nextDayToDay= new Date( toDate.getTime() +
				// 24*60*60*1000);

				// if (fromDate != null && toDate != null) {
				// predicateList.add(criteriaBuilder.and(criteriaBuilder
				// .between(date, fromDate, nextDayToDay)));
				// }

				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Permission> searchPermissions(final String nameConsult, final Nomenclator moduleConsult) {
		return new Specification<Permission>() {
			@Override
			public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();

				if (nameConsult != null) {
					
					//predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
					//		root.<String> get("permissionName"), "%"
						//			+ nameConsult + "%")));
					
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root
									.<String> get("permissionName")), "%"
									+ nameConsult.toUpperCase() + "%")));

				}

				Path<Nomenclator> module = root.get("module");

				if (moduleConsult != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(module, moduleConsult)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Profile> searchProfiles(final Library library, final String name, final Date fromDate, final Date toDate) {
		return new Specification<Profile>() {
			@Override
			public Predicate toPredicate(Root<Profile> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Library> myLibrary = root.get("library");
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myLibrary, library)));
				}

				if (name != null) {
					
					//predicateList
					//		.add(criteriaBuilder.and(criteriaBuilder.like(
						//			root.<String> get("profileName"), "%"
										//	+ name + "%")));
					
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root
									.<String> get("profileName")), "%"
									+ name.replaceAll(" +", " ").trim().toUpperCase() + "%")));

				}
				if (fromDate != null && toDate != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.between(root.<Date> get("creationDate"), fromDate, toDate)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<User> searchUser(final String userName, final String password) {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();

				// if (userName != null) {
				predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<String> get("username"), userName)));
				
				predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<String> get("userPassword"), password)));
				// }

				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Person> searchPerson1(final Library library, final String firstName, final String dni) {
		return new Specification<Person>() {
			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Library> myLibrary = root.get("library");
				// List<Predicate> predicateList = new ArrayList<Predicate>();

				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myLibrary, library)));
				}
				if (firstName != null) {
					predicateList.add(criteriaBuilder.or(criteriaBuilder.like(root.<String> get("firstName"), "%" + firstName + "%")));
				}
				if (dni != null) {
					predicateList.add(criteriaBuilder.or(criteriaBuilder.like(root.<String> get("dni"), "%" + dni + "%")));
				}
				return (predicateList.size() == 0) ? criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])) : criteriaBuilder.or(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}
	
	
	public static Specification<Ldap> searchLdaps(final Library library) {
		return new Specification<Ldap>() {
			@Override
			public Predicate toPredicate(Root<Ldap> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
