package cu.uci.abcd.dao.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.domain.cataloguing.CataloguingNomenclator;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;

public class CataloguingSpecification {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	public static Specification<LoanObject> searchLoanObjectByControlNumber(final String controlNumber){

		return new Specification<LoanObject>() {

			@Override
			public Predicate toPredicate(Root<LoanObject> root,
					CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicateList = new ArrayList<Predicate>();

				Path<String> ctrNumber = root.get("controlNumber");

				if (controlNumber != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(ctrNumber, controlNumber)));
				}

				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<LoanObject> searchLoanObjectByPrecataloguedSituation(){

		return new Specification<LoanObject>() {

			@Override
			public Predicate toPredicate(Root<LoanObject> root,
					CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicateList = new ArrayList<Predicate>();

				Path<Nomenclator> situation = root.get("situation");
				
				long precataloguingSituation = CataloguingNomenclator.SITUATION_PRECATALOGUING;

				predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(situation.<Long> get("nomenclatorID"), precataloguingSituation)));

				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}
}
