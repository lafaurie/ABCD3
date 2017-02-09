package cu.uci.abcd.acquisition;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;

public interface IManageSuggestionService {

	//Search all Suggestion 
	public List<Suggestion> findAll();

	// RF_AQ2.1_Adicionar Sugerencias
	public Suggestion addSuggestion(Suggestion suggestion);//FIXME Metodo que no se usa(Hansel)

	// Eliminar Suggestion
	public void deleteSuggestion(Long idSuggestion);
	
	
	// RF_AQ2_Listar Sugerencias Pendientes
	// RF_AQ1_Listar todas las Sugerencias realizadas
	public Page<Suggestion> findAllSuggestions(Specification<Suggestion> specification, Pageable pageable);

	// Find One Suggestion
	public Suggestion findOne(Long idSuggestion);

	//Get nomenclator
	public Nomenclator getNomenclator(Long id);

	//Search Suggestion by Desiderata Id
	public List<Suggestion> findSuggestionByIdDesiderata(Long idDesiderata);
	
	//Search all Nomenclator
	public List<Nomenclator> findAllNomenclators(Long idLibrary, Long code);
	
	//Search all Users
	public List<User> findAllUsers();
	
}
