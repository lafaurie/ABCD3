package cu.uci.abcd.opac;


import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.circulation.Penalty;
//FIXME FALTAN COMENTARIOS DE INTERFACE
public interface IOpacPenaltyService {

	
	public Penalty addPenalty(Penalty penalty);
	
	public Penalty updatePenalty(Long idPenalty);
	
	public void deletePenalty(Long idPenalty);
	
	public List<Penalty> findAllPenalty();
	
	public Penalty findPenalty(Long idPenalty);
	      
	public Page<Penalty> findAllPenaltyByUser(Long loanUserId, int page, int size, int direction, String orderByString);

	public Page<Penalty> findAllHistoricalPenaltyByUser(Long loanUserId, int page, int size, int direction, String orderByString);

}
