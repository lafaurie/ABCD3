package cu.uci.abcd.dao.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
   
public class NomenclatorSpecification {

	public static Specification<Nomenclator> searchTypes(final Library library) {
		return new Specification<Nomenclator>() {
			@Override
			public Predicate toPredicate(Root<Nomenclator> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Library> myLibrary = root.get("ownerLibrary");
				predicateList.add(criteriaBuilder.and(criteriaBuilder.or(criteriaBuilder.equal(myLibrary, library), criteriaBuilder.isNull(myLibrary))));
				predicateList.add(criteriaBuilder.and(criteriaBuilder.or(criteriaBuilder.equal(root.<Integer> get("manageable"), 2), criteriaBuilder.equal(root.<Integer> get("manageable"), 6))));
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};

	}

	public static Specification<Nomenclator> searchNomenclator(final Library library, final Long nomenclatorCode, final String nomenclatorName) {
		return new Specification<Nomenclator>() {
			@Override
			public Predicate toPredicate(Root<Nomenclator> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Library> myLibrary = root.get("ownerLibrary");
				predicateList.add(criteriaBuilder.and(criteriaBuilder.or(criteriaBuilder.equal(myLibrary, library), criteriaBuilder.isNull(myLibrary))));
				predicateList.add(criteriaBuilder.and(criteriaBuilder.or(criteriaBuilder.equal(root.<Integer> get("manageable"), 4), criteriaBuilder.equal(root.<Integer> get("manageable"), 6), criteriaBuilder.equal(root.<Integer> get("manageable"), 8))));

				if (nomenclatorCode != null) {
					Path<Nomenclator> type = root.get("nomenclator");
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(type.<Long> get("nomenclatorID"), nomenclatorCode)));
				}
				if (nomenclatorName != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root
									.<String> get("nomenclatorName")), "%" + nomenclatorName.replaceAll(" +", " ").trim().toUpperCase() + "%")) );
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

}
