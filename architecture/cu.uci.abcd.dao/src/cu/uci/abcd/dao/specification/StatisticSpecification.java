package cu.uci.abcd.dao.specification;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Statistic;
import cu.uci.abcd.domain.statistic.Indicator;
import cu.uci.abcd.domain.statistic.Report;
import cu.uci.abcd.domain.statistic.Variable;

public class StatisticSpecification {
	public static Specification<Statistic> searchStatistic(final String databasetype, final String tablename) {
		return new Specification<Statistic>() {

			@Override
			public Predicate toPredicate(Root<Statistic> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				
				if (databasetype != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(root.<String> get("databaseType"), "%" + databasetype + "%")));
				}
				if (tablename != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(root.<String> get("tableName"), "%" + tablename + "%")));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Variable> searchVariable(final String databaseName, final String variableheader, final String field) {
		return new Specification<Variable>() {
			@Override
			public Predicate toPredicate(Root<Variable> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();

				if (databaseName != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(root.<String> get("databaseName"), "&" + databaseName + "%")));
				}
				if (variableheader != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(root.<String> get("header"), "%" + variableheader + "%")));
				}
				if (field != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(root.<String> get("field"), "%" + field + "%")));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Indicator> searchIndicator(final String nombIndicator, final String numIndicator) {
		return new Specification<Indicator>() {
			@Override
			public Predicate toPredicate(Root<Indicator> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				
				if (numIndicator != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(root.<String> get("indicatorId"), "%" + numIndicator + "%")));
				}
				if (nombIndicator != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root
									.<String> get("nameIndicator")), "%" + nombIndicator.replaceAll(" +", " ").trim().toUpperCase() + "%")) );
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Report> searchReport(final String nameReport) {
		return new Specification<Report>() {
			@Override
			public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				
				if (nameReport != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.upper(root
									.<String> get("nameReport")), "%" + nameReport.replaceAll(" +", " ").trim().toUpperCase() + "%")));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Indicator> searchIndicatorId(final long id) {
		return new Specification<Indicator>() {
			@Override
			public Predicate toPredicate(Root<Indicator> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				
				if (id != 0) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<Long> get("id"), id)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}
	
	public static Specification<LoanObject> searchVolumesInPeriod(final Date date) {
		return new Specification<LoanObject>() {

			@Override
			public Predicate toPredicate(Root<LoanObject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				
				predicateList.add(criteriaBuilder.equal(root.get("registrationDate"), date));
				
				predicateList.add(criteriaBuilder.isNotNull(root.get("volume")));
				
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}

		};
	}
	
	public static Specification<LoanObject> searchVolumesAtEndOfPeriod() {
		return new Specification<LoanObject>() {

			@Override
			public Predicate toPredicate(Root<LoanObject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				
				predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.<Date>get("registrationDate"), criteriaBuilder.currentDate()));
				
				predicateList.add(criteriaBuilder.isNotNull(root.get("volume")));
				
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}

		};
	}
}
