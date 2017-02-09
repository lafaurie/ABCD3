package cu.uci.abcd.opac.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dao.opac.SelectionListDAO;
import cu.uci.abcd.dao.specification.OpacSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.opac.SelectionList;
import cu.uci.abcd.opac.IOpacSelectionListService;

public class OpacSelectionListServiceImpl implements IOpacSelectionListService {

	SelectionListDAO selectionListDAO;

	@Override
	public SelectionList addSelectionList(SelectionList selectionList) {
		return selectionListDAO.save(selectionList);
	}

	@Override
	public SelectionList updateSelectionList(Long idSelectionList) {
		return null;
	}

	@Override
	public void deleteSelectionList(Long idSelectionList) {
		selectionListDAO.delete(idSelectionList);
	}

	@Override
	public SelectionList findSelectionList(Long idSelectionList) {
		return selectionListDAO.findOne(idSelectionList);
	}    

	@Override
	public int findAllPublicSelectionList() {
		List<SelectionList> tempSelectionLists = (List<SelectionList>) selectionListDAO.findAll();
   
		for (int i = 0; i < tempSelectionLists.size(); i++) {
			if (tempSelectionLists.get(i).getCategory().getNomenclatorID() != Nomenclator.CATEGORY_SELECTION_PUBLIC) {
				tempSelectionLists.remove(i);
				i--;
			}
		}

		return tempSelectionLists.size();
	}

	@Override
	public Page<SelectionList> findAllPublicSelectionListPage(int page, int size, int direction, String orderByString) {
		
		return selectionListDAO.findAll(OpacSpecification.searchPublicSelectionList(), PageSpecification.getPage(page, size, direction, orderByString));
	}

	@Override
	public Page<SelectionList> findAllSelectionListPageByUser(Integer userId, Long libraryId, int page, int size, int direction, String orderByString) {
		
		return selectionListDAO.findAll(OpacSpecification.searchSelectionListByUser(userId, libraryId), PageSpecification.getPage(page, size, direction, orderByString));
	}     
     
	@Override
	public List<SelectionList> findAllSelectionListByUser(Long userId, Long libraryId) {
      
		List<SelectionList> temp = (List<SelectionList>) selectionListDAO.findAll();

		for (int i = 0; i < temp.size(); i++)
			if (temp.get(i).getDuser().getUserID() != userId.intValue() 
			|| temp.get(i).getLibrary().getLibraryID() != libraryId) {
				temp.remove(temp.get(i));
				i--;
			}

		return temp;
	}
	
	@Override
	public boolean findAllSelectionListByName(String nameSelectionList,Long userId) {
      
		List<SelectionList> temp = (List<SelectionList>) selectionListDAO.findAll();
		boolean booleanSelection = false;

		for (int i = 0; i < temp.size(); i++)
			if (temp.get(i).getSelectionListName().equals(nameSelectionList) && temp.get(i).getDuser().getUserID() == userId.intValue()) {
				booleanSelection = true;
				break;
			}
		return booleanSelection;
	}
	
	@Override
	public boolean findAllSelectionListByName(String nameSelectionList, Long userId, Long selectionListId) {
	
		List<SelectionList> temp = (List<SelectionList>) selectionListDAO.findAll();
		boolean booleanSelection = false;

		for (int i = 0; i < temp.size(); i++)
			if (temp.get(i).getSelectionListName().equals(nameSelectionList) && temp.get(i).getDuser().getUserID() == userId.intValue()) {
				if(temp.get(i).getId() != selectionListId)
					booleanSelection = true;
				break;
			}
		return booleanSelection;
	}	
	
	public void bind(SelectionListDAO selectionListDAO, Map<String, Object> properties) {
		this.selectionListDAO = selectionListDAO;
		System.out.println("servicio registrado");
	}

	@Override
	public List<SelectionList> findAllSelectionList() {
		
		return (List<SelectionList>) selectionListDAO.findAll();
	}

}
