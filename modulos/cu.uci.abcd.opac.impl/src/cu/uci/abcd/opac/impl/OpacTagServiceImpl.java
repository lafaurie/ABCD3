package cu.uci.abcd.opac.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dao.opac.TagDAO;
import cu.uci.abcd.dao.specification.OpacSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.opac.Tag;
import cu.uci.abcd.opac.IOpacTagService;

public class OpacTagServiceImpl implements IOpacTagService {

	TagDAO tagDAO;

	@Override
	public Tag addTag(Tag tag) {
		return tagDAO.save(tag);
	}

	@Override
	public void deleteTag(Long idTag) {
		tagDAO.delete(idTag);
	}

	@Override
	public Page<Tag> findAllTagByUserAndLibrary(Long userId, Long libraryId, int page, int size, int direction, String orderByString) {
		
		return tagDAO.findAll(OpacSpecification.searchTagsByUserAndLibrary(userId, libraryId), PageSpecification.getPage(page, size, direction, orderByString));
	}  
	               
	@Override
	public Page<Tag> searchTagsByLibrary(Long userId,Long libraryId, int page, int size, int direction, String orderByString) {
		       
		return tagDAO.findAll(OpacSpecification.searchTagsByLibrary(userId,libraryId), PageSpecification.getPage(page, size, direction, orderByString));
	}  
          
	@Override
	public List<Tag> findTagsByRegister(String controlNum, String dataBaseName, Long libraryId) {
	
		List<Tag> tempTags =  (List<Tag>) tagDAO.findAll();
		                
		for (int i = 0; i < tempTags.size(); i++) 
			if (!(tempTags.get(i).getMaterial().equals(controlNum) && tempTags.get(i).getLibrary().getLibraryID() == 
			libraryId && tempTags.get(i).getDatabasename().equals(dataBaseName))) {
                
				tempTags.remove(i);
				i--;
			}    
				
		
		return tempTags;
	}

	@Override
	public Tag findTag(Long idTag) {
		return tagDAO.findOne(idTag);
	}

	@Override
	public Tag updateTag(Tag tag) {

		tagDAO.delete(tag.getId());
		return tagDAO.save(tag);
	}

	public void bind(TagDAO tagDAO, Map<String, Object> properties) {
		this.tagDAO = tagDAO;
		System.out.println("servicio registrado");
	}
        
	@Override
	public boolean checkUniqueTag(Long userID, String tagName, String controlNum, String dataBaseName, Long libraryId) {
		   
		List<Tag> temp = (List<Tag>) tagDAO.findAll();
		
		for (int i = 0; i < temp.size(); i++) 
			if(temp.get(i).getDuser().getUserID() == userID && temp.get(i).getTagName().equals(tagName)
				&&	temp.get(i).getMaterial().equals(controlNum) && temp.get(i).getDatabasename().equals(dataBaseName) 
				&& temp.get(i).getLibrary().getLibraryID() == libraryId	)
				return true;		
		
		return false;
	}
}
