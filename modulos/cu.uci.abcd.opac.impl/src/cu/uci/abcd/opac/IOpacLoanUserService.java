package cu.uci.abcd.opac;
 
import cu.uci.abcd.domain.circulation.LoanUser;

public interface IOpacLoanUserService {	
	//FIXME FALTAN COMENTARIOS DE INTERFACE
	
	public LoanUser findLoanUserByIdPersonAndIdLibrary(Long idPerson, Long idLibray);

}
