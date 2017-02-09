package cu.uci.abcd.opac;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.LoanObject;

public interface IOpacLoanObjectService {
	//FIXME FALTAN COMENTARIOS DE INTERFACE
	     
	public List<LoanObject> findRecentLoanObject(Long libraryId);

	public List<LoanObject> findAllAvailableLoanObjectByControlNumberAndLibrary(String controlNumber, String databaseName, Long libraryId);	
	
	public List<LoanObject> findAllLoanObjectByDate();   
	
	public List<LoanObject> findLoanObjectsByControlNumberAndLibrary(String controlNumber, Long idLibrary);
	
	public int findLoanObjectByControlNumberAndLibrary(String controlNumber, Long libraryId);  
	   
	public Page<LoanObject> findAllCoppysByLoanObjectAndLibrary(String controlNumber, Long idLibrary, int page, int size, int direction, String orderByString);
	
}
    