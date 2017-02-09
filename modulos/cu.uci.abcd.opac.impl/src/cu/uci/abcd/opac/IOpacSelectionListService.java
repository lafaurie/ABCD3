package cu.uci.abcd.opac;
//FIXME FALTAN COMENTARIOS DE INTERFACE
import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.opac.SelectionList;

public interface IOpacSelectionListService {

	public SelectionList addSelectionList(SelectionList selectionList);

	public SelectionList updateSelectionList(Long idSelectionList);

	public void deleteSelectionList(Long idSelectionList);

	public int findAllPublicSelectionList();    
	
	public List<SelectionList> findAllSelectionListByUser(Long userId, Long libraryId);
	
	public boolean findAllSelectionListByName(String nameSelectionList, Long userId);
	
	public boolean findAllSelectionListByName(String nameSelectionList, Long userId, Long selectionListId);

	public SelectionList findSelectionList(Long idSelectionList);
	
	public Page<SelectionList> findAllPublicSelectionListPage(int page, int size, int direction, String orderByString);
	
	public Page<SelectionList> findAllSelectionListPageByUser(Integer userId, Long libraryId, int page, int size, int direction, String orderByString);
	
	public List<SelectionList> findAllSelectionList();

}
