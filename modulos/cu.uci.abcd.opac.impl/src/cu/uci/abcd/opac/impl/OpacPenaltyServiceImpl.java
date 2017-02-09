package cu.uci.abcd.opac.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dao.circulation.PenaltyDAO;
import cu.uci.abcd.dao.specification.OpacSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.opac.IOpacPenaltyService;

public class OpacPenaltyServiceImpl implements IOpacPenaltyService {

	PenaltyDAO penaltyDAO;

	@Override
	public Penalty addPenalty(Penalty penalty) {
		return penaltyDAO.save(penalty);
	}

	@Override
	public void deletePenalty(Long idPenalty) {
		penaltyDAO.delete(idPenalty);
	}

	@Override
	public Penalty findPenalty(Long idPenalty) {
		return penaltyDAO.findOne(idPenalty);
	}

	@Override
	public List<Penalty> findAllPenalty() {
		return (List<Penalty>) penaltyDAO.findAll();
	}
	  
	@Override
	public Page<Penalty> findAllPenaltyByUser(Long loanUserId, int page, int size, int direction, String orderByString) {
	
		return penaltyDAO.findAll(OpacSpecification.searchPenalty(loanUserId), PageSpecification.getPage(page, size, direction, orderByString));
	}
	
	@Override
	public Page<Penalty> findAllHistoricalPenaltyByUser(Long loanUserId, int page, int size, int direction, String orderByString) {
	
		return penaltyDAO.findAll(OpacSpecification.searchHistoricalPenalty(loanUserId), PageSpecification.getPage(page, size, direction, orderByString));
	}	
	

	@Override
	public Penalty updatePenalty(Long arg0) {
		return null;
	}

	public void bind(PenaltyDAO penaltyDAO, Map<String, Object> properties) {
		this.penaltyDAO = penaltyDAO;
		System.out.println("servicio registrado");
	}

	

}
