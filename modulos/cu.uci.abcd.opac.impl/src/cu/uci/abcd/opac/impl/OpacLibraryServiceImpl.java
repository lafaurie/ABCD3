package cu.uci.abcd.opac.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dao.management.library.LibraryDAO;
import cu.uci.abcd.dao.opac.OpacDataSourcesDAO;
import cu.uci.abcd.dao.specification.OpacSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.opac.OpacDataSources;
import cu.uci.abcd.opac.EmailConfigService;
import cu.uci.abcd.opac.IOpacLibraryService;

public class OpacLibraryServiceImpl implements IOpacLibraryService {

	private LibraryDAO libraryDAO;

	private OpacEmailConfigServiceImpl opacEmailConfigServiceImpl;

	private OpacDataSourcesDAO opacDataSourcesDAO;

	public OpacEmailConfigServiceImpl getOpacEmailConfigServiceImpl() {
		return opacEmailConfigServiceImpl;
	}

	public void setOpacEmailConfigServiceImpl(OpacEmailConfigServiceImpl opacEmailConfigServiceImpl) {
		this.opacEmailConfigServiceImpl = opacEmailConfigServiceImpl;
	}

	@Override
	public Library findLibrary(Long userID) {

		return libraryDAO.findOne(1L);
	}

	@Override
	public List<Library> findAllLibrary() {
		
		List<Library> temp = (List<Library>) libraryDAO.findAll();
		
		for (int i = 0; i < temp.size(); i++) 
			if(!temp.get(i).isEnabled())
				temp.remove(i--);		
		
		return temp;
	}

	public EmailConfigService getEmailConfig() {
		return new EmailConfigService(opacEmailConfigServiceImpl);
	}
	
	@Override
	public OpacDataSources addOpacDataSources(OpacDataSources opacDataSources) {		
		return opacDataSourcesDAO.save(opacDataSources);
	}
    
	@Override
	public List<OpacDataSources> getAllDataSourcesByLibrary(Long libraryID) {
		return (List<OpacDataSources>) opacDataSourcesDAO.findAll();		
	}
	   
	@Override
	public Page<OpacDataSources> findAllDataSourcesByLibrary(Long libraryID, int page, int size, int direction, String orderByString) {
		return opacDataSourcesDAO.findAll(OpacSpecification.searchfindAllDataSourcesByLibrary(libraryID), PageSpecification.getPage(page, size, direction, orderByString));

	}
	    

	public void bind(LibraryDAO libraryDAO, Map<String, Object> properties) {
		this.libraryDAO = libraryDAO;
		System.out.println("servicio registrado");
	}     
	
	public void bindOpacDataSource(OpacDataSourcesDAO opacDataSourcesDAO, Map<String, Object> properties) {
		this.opacDataSourcesDAO = opacDataSourcesDAO;
		System.out.println("servicio registrado");
	}    

	@Override
	public List<Library> findAllAvailableLibrary() {
		
		return libraryDAO.findDistinctLibraryByEnabled();
	}

	@Override
	public void deleteDataSources(OpacDataSources opacDataSources) {
		
		opacDataSourcesDAO.delete(opacDataSources);
	}	
}
