package cu.uci.abcd.opac.ui.controller;

import java.util.ArrayList;
import java.util.List;

import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abos.api.ui.ViewController;

public class PenaltyViewController implements ViewController {

	private ProxyController proxyController;	

	public Penalty readPenalty(Long idPenalty) {
		return proxyController.getIOpacPenaltyService().findPenalty(idPenalty);
	}

	public List<Penalty> findAllPenalties() {
		if (proxyController.getIOpacPenaltyService() != null) {
			return proxyController.getIOpacPenaltyService().findAllPenalty();
		}
		return new ArrayList<Penalty>();
	}	

	public void setProxyController(ProxyController proxyController) {
		this.proxyController = proxyController;
	}
}

