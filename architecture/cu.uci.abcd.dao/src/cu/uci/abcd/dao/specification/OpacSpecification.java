package cu.uci.abcd.dao.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.opac.OpacDataSources;
import cu.uci.abcd.domain.opac.Recommendation;
import cu.uci.abcd.domain.opac.SelectionList;
import cu.uci.abcd.domain.opac.Tag;

public class OpacSpecification {
	public static Specification<User> searchUser(final Long userId, final Long libraryId) {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Library> findLibrary = root.get("library");

				if (libraryId != null)
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findLibrary.<Long> get("libraryID"), libraryId)));
				if (userId != null)
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(root.<Long> get("userID"), userId)));
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<User> searchUser(final Long userId, final Long libraryId, final String userName) {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Library> findLibrary = root.get("library");

				if (userName != null)
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("username")), "%" + userName.replaceAll(" +", " ").trim().toUpperCase() + "%")));
				if (libraryId != null)
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findLibrary.<Long> get("libraryID"), libraryId)));
				if (userId != null)
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(root.<Long> get("userID"), userId)));
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Transaction> searchTransaction(final Long loanUserId) {
		return new Specification<Transaction>() {
			@Override
			public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanUser> loanUser = root.get("loanUser");
				Path<Nomenclator> findState = root.get("state");

				if (loanUserId != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanUser.<Long> get("id"), loanUserId)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_BORROWED)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Transaction> searchHistoricalTransaction(final Long loanUserId) {
		return new Specification<Transaction>() {
			@Override
			public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanUser> loanUser = root.get("loanUser");

				if (loanUserId != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanUser.<Long> get("id"), loanUserId)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Reservation> searchReservation(final Long idLoanUser) {
		return new Specification<Reservation>() {
			@Override
			public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanUser> buscarUser = root.get("loanUser");
				Path<Nomenclator> buscarReservationState = root.get("state");

				if (idLoanUser != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), idLoanUser)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarReservationState.<Long> get("nomenclatorID"), Nomenclator.RESERVATION_STATE_PENDING)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Reservation> searchHistoricalReservation(final Long idLoanUser) {
		return new Specification<Reservation>() {
			@Override
			public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanUser> buscarUser = root.get("loanUser");

				if (idLoanUser != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), idLoanUser)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Reservation> searchHistoricalReservationByParameters(final Long idLoanUser, final String tittleBook, final String autorBook, final Long state) {
		return new Specification<Reservation>() {
			@Override
			public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanUser> buscarUser = root.get("loanUser");
				Path<Nomenclator> buscarReservationState = root.get("state");

				if (idLoanUser != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), idLoanUser)));
				}
				if (tittleBook != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("title")), "%" + tittleBook.replaceAll(" +", " ").trim().toUpperCase() + "%")));
				}
				if (state != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarReservationState.<Long> get("nomenclatorID"), state)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	// //.....Penalty.....\\\\

	public static Specification<Penalty> searchPenalty(final Long loanUserId) {
		return new Specification<Penalty>() {
			@Override
			public Predicate toPredicate(Root<Penalty> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanUser> findLoanUser = root.get("loanUser");
				Path<Nomenclator> findState = root.get("penaltyState");

				if (loanUserId != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findLoanUser.<Long> get("id"), loanUserId)));
					predicateList.add(criteriaBuilder.or(criteriaBuilder.notEqual(findState.<Long> get("nomenclatorID"), Nomenclator.PENALTY_STATE_INACTIVE)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Penalty> searchHistoricalPenalty(final Long loanUserId) {
		return new Specification<Penalty>() {
			@Override
			public Predicate toPredicate(Root<Penalty> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<LoanUser> findLoanUser = root.get("loanUser");

				if (loanUserId != null)
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findLoanUser.<Long> get("id"), loanUserId)));
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Suggestion> searchSuggestion(final Long userId, final Long libraryId) {
		return new Specification<Suggestion>() {
			@Override
			public Predicate toPredicate(Root<Suggestion> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<User> buscarUser = root.get("user");

				if (userId != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("userID"), userId)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Suggestion> searchSuggestionPending(final Long userId, final Long libraryId) {
		return new Specification<Suggestion>() {
			@Override
			public Predicate toPredicate(Root<Suggestion> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<User> findUser = root.get("user");
				Path<Nomenclator> findState = root.get("state");

				if (userId != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findUser.<Long> get("userID"), userId)));
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findState.<Long> get("nomenclatorID"), Nomenclator.SUGGESTION_STATE_PENDING)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<SelectionList> searchSelectionListByUser(final Integer userId, final Long librayId) {

		return new Specification<SelectionList>() {
			@Override
			public Predicate toPredicate(Root<SelectionList> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Long> findUser = root.get("user");
				Path<Library> findLibrary = root.get("library");

				if (userId != null)
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findUser.<Long> get("userID"), userId)));
				if (librayId != null)
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findLibrary.<Long> get("libraryID"), librayId)));
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<SelectionList> searchPublicSelectionList() {
		return new Specification<SelectionList>() {
			@Override
			public Predicate toPredicate(Root<SelectionList> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Nomenclator> findCategory = root.get("category");

				predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findCategory.<Long> get("nomenclatorID"), Nomenclator.CATEGORY_SELECTION_PUBLIC)));
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<LoanObject> searchRecentLoanObject() {
		return new Specification<LoanObject>() {
			@Override
			public Predicate toPredicate(Root<LoanObject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Tag> searchTagsByUserAndLibrary(final Long userId, final Long libraryId) {
		return new Specification<Tag>() {
			@Override
			public Predicate toPredicate(Root<Tag> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Library> findLibrary = root.get("library");
				Path<User> findUser = root.get("user");

				if (libraryId != null)
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findLibrary.<Long> get("libraryID"), libraryId)));
				if (userId != null)
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findUser.<Long> get("userID"), userId)));

				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Tag> searchTagsByLibrary(final Long userId, final Long libraryId) {
		return new Specification<Tag>() {
			@Override
			public Predicate toPredicate(Root<Tag> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Library> findLibrary = root.get("library");
				Path<User> findUser = root.get("user");

				if (libraryId != null)
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findLibrary.<Long> get("libraryID"), libraryId)));
				if (userId != null)
					predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(findUser.<Long> get("userID"), userId)));

				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<Recommendation> searchRecommendationByUserAndLibrary(final Long userId, final Long libraryId) {
		return new Specification<Recommendation>() {
			@Override
			public Predicate toPredicate(Root<Recommendation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Long> findUser = root.get("destinationUser");
				Path<Library> findLibrary = root.get("library");

				if (libraryId != null)
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findLibrary.<Long> get("libraryID"), libraryId)));
				if (userId != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findUser.<Long> get("userID"), userId)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<LoanObject> searchCoppysByLoanObjectAndLibrary(final String controlNumber, final Long libraryId) {
		return new Specification<LoanObject>() {
			@Override
			public Predicate toPredicate(Root<LoanObject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Long> findControlNumber = root.get("controlNumber");
				Path<Library> findLibrary = root.get("libraryOwner");

				if (libraryId != null)
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findLibrary.<Long> get("libraryID"), libraryId)));
				if (controlNumber != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findControlNumber, controlNumber)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	public static Specification<OpacDataSources> searchfindAllDataSourcesByLibrary(final Long libraryID) {
		return new Specification<OpacDataSources>() {
			@Override
			public Predicate toPredicate(Root<OpacDataSources> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Library> findLibrary = root.get("library");

				if (libraryID != null)
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findLibrary.<Long> get("libraryID"), libraryID)));

				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

}
