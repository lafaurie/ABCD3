package cu.uci.abcd.administration.nomenclators;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;


/**
 * 
 * @author Dayana Rivera
 *
 */

public interface IManageNomenclator {
	
	/**
	 *RF_AD2_Register Record Type
	 *RF_AD113_ Register Loan User Type
	 * @param nomenclator
	 * @return
	 */
    public Nomenclator registerNomenclator(Nomenclator nomenclator);
    
    /**
	  *RF_AD2_Register Record Type
	 *RF_AD113_ Register Loan User Type
	 * @param id
	 * @return
	 */
	public Nomenclator searchNomenclator(Long library, Long id); 
	
	/**
	 *RF_AD1_List Record Type defined
	 *RF_AD112_List Loan User Type Type defined
	 * @param library
	 * @param nomenclatorCode
	 * @return
	 */
	public List<Nomenclator> listByCode(Long library, Long nomenclatorCode);
	
	/**
	 *RF_AD1_List Record Type defined
	 *RF_AD112_List Loan User Type Type defined
	 * @param actorID
	 * @param nomenclatorCode
	 * @return
	 */
	public List<Nomenclator> listByCode(Long nomenclatorCode);
	//FIXME EXCESO DE PARAMETROS
	/**
	 *RF_AD1_List Record Type defined
	 *RF_AD1.1_Order List Record Type 
	 * RF_AD112.1_Order List Loan User Type
	 * @param library
	 * @param nomenclatorCode
	 * @param nomenclatorName
	 * @param norderByString
	 * @return
	 */
	public Page<Nomenclator> listAll(Library library, Long nomenclatorCode, String nomenclatorName, int page,
			int size, int direction, String orderByString);
	
	/**
	 *RF_AD1.3_Filter List Record Type
	 * RF_AD112.3_Filter List Loan User Type
	 * @param library
	 * @param nomenclatorName
	 * @return
	 */
	public List<Nomenclator> listByName(Long library, String nomenclatorName);
	
	/**
	 *RF_AD3_Delete Record Type
	 *RF_AD116_Delete Loan User Type
	 * @param ID
	 * @return
	 */
	public void deleteNomenclator(Long ID); 
	
	/**
	 * List managed nomemclator types.
	 * @return
	 */
	public List<Nomenclator> listManagedTypes(Library library);
	
	public Nomenclator findNomenclator(Long library, Long idParent, String nomenclatorName);
	
}

