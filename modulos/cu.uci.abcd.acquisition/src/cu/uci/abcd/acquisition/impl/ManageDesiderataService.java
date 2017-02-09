package cu.uci.abcd.acquisition.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.acquisition.IManageDesiderataService;
import cu.uci.abcd.dao.acquisition.DesiderataDAO;
import cu.uci.abcd.domain.acquisition.Desiderata;

public class ManageDesiderataService implements IManageDesiderataService {
	
	private DesiderataDAO desiderataDAO;

	// Actualizar desiderata
	@Override
	public Desiderata setDesiderata(Desiderata desiderata) {
		return desiderataDAO.save(desiderata);
	}

	// Borrar una desiderata
	@Override
	public void deleteDesiderata(Long idDesideraata) {
		desiderataDAO.delete(idDesideraata);

	}

	// all desiderata

	public List<Desiderata> findAll() {
		return (List<Desiderata>) desiderataDAO.findAll();
	}

	// read desiderata
	@Override
	public Desiderata readDesiderata(Long id) {
		return desiderataDAO.findOne(id);
	}

	public void bind(DesiderataDAO desiderataDAO, Map<?, ?> properties) {
		this.desiderataDAO = desiderataDAO;

	}

	@Override
	public Page<Desiderata> findAllDesideratas(Specification<Desiderata> specification, Pageable pageable) {
		return desiderataDAO.findAll(specification, pageable);
	}

	@Override
	public Long countDesiderata() {
		return desiderataDAO.count();
	}

	@Override
	public List<Desiderata> findDesideratasByPurchaseRequestId(
			Long purchaseRequestID) {
		return desiderataDAO.findDistinctDesiderataByPurchaseRequest_purchaseRequestID(purchaseRequestID);
	}

}
