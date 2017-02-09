package cu.uci.abcd.opac;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.opac.OpacDataSources;

public interface IOpacLibraryService {
	
	/**
	 * 
	 * @author Alberto Alejandro Arias Benítez
	 * @version 1.0
	 * 
	 */
	public Library findLibrary(Long userID);
	
	/**
	 * 
	 * @author Alberto Alejandro Arias Benítez
	 * @version 1.0
	 * 
	 */
	public List<Library> findAllLibrary();
	
	/**
	 * 
	 * @author Alberto Alejandro Arias Benítez
	 * @return 
	 */
	public List<Library> findAllAvailableLibrary();
	
	/**
	 * 
	 * @author Alberto Alejandro Arias Benítez
	 * @version 1.0
	 * 
	 */
	public EmailConfigService getEmailConfig();
	
	
	/**
	 * 
	 * @author Alberto Alejandro Arias Benítez
	 * @version 1.0
	 * 
	 */
	public OpacDataSources addOpacDataSources(OpacDataSources opacDataSources);	
	
	
	/**
	 * 
	 * @author Alberto Alejandro Arias Benítez
	 * @version 1.0
	 * 
	 */
	public List<OpacDataSources> getAllDataSourcesByLibrary(Long libraryID);	
	
	   
	/**
	 * 
	 * @author Alberto Alejandro Arias Benítez
	 * @version 1.0
	 * 
	 */
	public Page<OpacDataSources> findAllDataSourcesByLibrary(Long libraryID, int page, int size, int direction, String orderByString);
	
	
	public void deleteDataSources(OpacDataSources opacDataSources);
	
}
