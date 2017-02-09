package cu.uci.abcd.dao.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.common.Nomenclator;

public interface NomenclatorDAO extends PagingAndSortingRepository<Nomenclator, Long>, JpaSpecificationExecutor<Nomenclator> {

	@Query("select n from Nomenclator n where n.ownerLibrary.libraryID = ?1 order by n.nomenclatorName asc")
	public List<Nomenclator> findByOwnerLibrary(Long libraryID);

	@Query("select n from Nomenclator n where n.ownerLibrary.libraryID = ?1 and n.nomenclator.nomenclatorID = ?2 order by n.nomenclatorName asc")
	public List<Nomenclator> findByOwnerLibraryAndNomenclator(Long library, Long nomenclatorType);
	
	@Query("select n from Nomenclator n where n.nomenclator.nomenclatorID = ?1")
	public List<Nomenclator> findByNomenclator( Long nomenclatorType);

	@Query("select n from Nomenclator n where n.ownerLibrary.libraryID = ?1 and n.nomenclatorName = ?2")
	public List<Nomenclator> findByOwnerLibraryAndNomenclatorName(Long library, String nomenclatorName);

	@Query("select n from Nomenclator n where n.ownerLibrary.libraryID = ?1 and n.nomenclatorID = ?2")
	public Nomenclator findByOwnerLibraryAndNomenclatorID(Long library, Long id);
	
	@Query("select n from Nomenclator n where n.ownerLibrary.libraryID = ?1 and n.nomenclator.nomenclatorID = ?2 and(n.nomenclatorName = ?3 or n.nomenclatorDescription = ?4)")
	public List<Nomenclator> findDistinctByOwnerLibraryAndNomenclatorNameOrNomenclatorDescription( Long library, Long nomenclator,String name, String description);

	@Query("select n from Nomenclator n where n.nomenclatorID = ?1")
	public Nomenclator findByNomenclatorID(Long id);
	
	@Query("select n from Nomenclator n where ( n.ownerLibrary.libraryID = ?1 or n.ownerLibrary.libraryID = null ) and n.nomenclator.nomenclatorID = ?2")
	public List<Nomenclator> findNomenclatorsByLibraryOrLibraryNullAndParent(Long library, Long idParent);

	@Query("select n from Nomenclator n where ( n.ownerLibrary.libraryID = ?1 or n.ownerLibrary.libraryID = null ) and n.nomenclator.nomenclatorID = ?2 order by n.nomenclatorID ASC")
	public List<Nomenclator> findNomenclatorsByLibraryOrLibraryNullAndParentOrderById(Long library, Long idParent);

	
	@Query("select n from Nomenclator n where ( n.ownerLibrary.libraryID = ?1 or n.ownerLibrary.libraryID = null ) and n.nomenclator.nomenclatorID = ?2 or n.nomenclator.nomenclatorID = ?3")
	public List<Nomenclator> findNomenclatorsByLibraryOrLibraryNullAndParents(Long library, Long idParent1, Long idParent2);

	@Query("select n from Nomenclator n where ( n.ownerLibrary.libraryID = ?1 or n.ownerLibrary.libraryID = null ) and n.nomenclator.nomenclatorID = ?2 and UPPER(n.nomenclatorName)=UPPER(?3)")
	public Nomenclator findNomenclatorsByLibraryAndParentAndNomenclatorName(Long library, Long idParent, String nomenclatorName);


	
}
