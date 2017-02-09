package cu.uci.abcd.opac.impl;

import java.util.List;
import java.util.Map;

import cu.uci.abcd.dao.common.NomenclatorDAO;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.opac.IOpacNomenclatorService;

public class OpacNomenclatorServiceImpl implements IOpacNomenclatorService{

	NomenclatorDAO nomenclatorDAO;
	
	
	@Override
	public Nomenclator findNomenclator(Long idNomenclator) {
		
		Nomenclator temp = nomenclatorDAO.findOne(idNomenclator);
		
		return temp;
	}            

	@Override
	public List<Nomenclator> findAllNomenclatorsByCode(String nomencaltorCode) {
		
		return (List<Nomenclator>) nomenclatorDAO.findAll();		
	}

	@Override
	public List<Nomenclator> findAllNomenclatorsByParent(Integer arg0) {
		// TODO Apéndice de método generado automáticamente
		return null;
	}
	
	public void bind(NomenclatorDAO nomenclatorDAO, Map<String, Object> properties) {
		this.nomenclatorDAO = nomenclatorDAO;
		System.out.println("servicio registrado");
	}

	@Override
	public List<Nomenclator> findAllNomencaltors(Long nomenclatorID) {		
		return  nomenclatorDAO.findByNomenclator(nomenclatorID);
	}

}
