package cu.uci.abcd.dao.circulation;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.common.Nomenclator;

public interface ReservationDAO extends PagingAndSortingRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {

	public List<Reservation> findDistinctReservationByLoanUser_Person_PersonID(Long personID);

	public List<Reservation> findDistinctReservationByState(Nomenclator stateReservation);
	
	/*@Query("select loanobject from abcdn.dreservationloanobject where loanobject = ?1")
	public List<Long> searchReservationsByLoanObjectID(Long loanObjectID);*/
	
}