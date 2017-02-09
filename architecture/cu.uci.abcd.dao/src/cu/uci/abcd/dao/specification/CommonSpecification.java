package cu.uci.abcd.dao.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.Library;

public class CommonSpecification {
/*
	public static Specification<Person> searchPerson(final Library library, final String param) {
		return new Specification<Person>() {
			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Library> myLibrary = root.get("library");
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(myLibrary, library)));
				}
				if (param != null) {
					predicateList.add(criteriaBuilder.or(criteriaBuilder.like(root.<String> get("firstName"), "%" + param + "%")));
					predicateList.add(criteriaBuilder.or(criteriaBuilder.like(root.<String> get("firstSurname"), "%" + param + "%")));
					predicateList.add(criteriaBuilder.or(criteriaBuilder.like(root.<String> get("DNI"), "%" + param + "%")));
				}
				return (predicateList.size() == 0) ? criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])) : criteriaBuilder.or(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};

	}
*/
	public static Specification<Person> searchPerson(final Library library,
			final String param) {
		return new Specification<Person>() {
			@Override
			public Predicate toPredicate(Root<Person> root,
					CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Path<Library> myLibrary = root.get("library");
				Predicate predicateLibrary = null;
				Predicate predicateParams = null;
				if (library != null) {
					predicateLibrary = criteriaBuilder.and(criteriaBuilder
							.equal(myLibrary, library));
				}
				if (param != null) {
					predicateParams = criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.upper(
							root.<String> get("firstName")), "%" + param.toUpperCase()
									+ "%"), criteriaBuilder.like(criteriaBuilder.upper(
							root.<String> get("firstSurname")), "%" + param.toUpperCase()
									+ "%"), criteriaBuilder.like(criteriaBuilder.upper(
							root.<String> get("dni")), "%" + param.toUpperCase() + "%"));
					}
				if (predicateParams == null) {
					return predicateLibrary;
				} else {
					return criteriaBuilder.and(predicateLibrary,
							predicateParams);
				}
			}
		};
	}
}
