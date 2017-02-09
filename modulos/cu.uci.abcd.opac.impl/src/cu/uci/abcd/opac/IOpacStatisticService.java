package cu.uci.abcd.opac;

//FIXME FALTAN COMENTARIOS DE INTERFACE
import java.util.List;
import cu.uci.abcd.domain.opac.MostBorrowed;

public interface IOpacStatisticService {    
	
	public List<MostBorrowed> mostBorrowedTitles(Long libraryId);

}
