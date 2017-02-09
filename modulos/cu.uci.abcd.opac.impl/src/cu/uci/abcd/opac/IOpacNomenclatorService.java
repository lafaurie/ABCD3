package cu.uci.abcd.opac;

import java.util.List;

import cu.uci.abcd.domain.common.Nomenclator;

public interface IOpacNomenclatorService {
	//FIXME FALTAN COMENTARIOS DE INTERFACE
	     
	public List<Nomenclator> findAllNomenclatorsByCode(String nomenclatorCode);	

	public Nomenclator findNomenclator(Long idNomenclator);
	
	public List<Nomenclator> findAllNomenclatorsByParent(Integer idNomenclator);
	
	public List<Nomenclator> findAllNomencaltors(Long nomenclatorID);
	
}