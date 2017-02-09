package cu.uci.abcd.opac.impl;

import java.util.List;
import java.util.Map;

import cu.uci.abcd.dao.opac.RecordRatingDAO;
import cu.uci.abcd.domain.opac.RecordRating;
import cu.uci.abcd.opac.IOpacRecordRatingService;

public class OpacRecordRatingImpl implements IOpacRecordRatingService {

    RecordRatingDAO recordRatingDAO;      
    

    public void bind(RecordRatingDAO recordRatingDAO, Map<String, Object> properties) {
	this.recordRatingDAO = recordRatingDAO;
	System.out.println("servicio registrado");
    }

    @Override
    public RecordRating addRecordRating(RecordRating recordRating) {
   
	List<RecordRating> temp = (List<RecordRating>) recordRatingDAO.findAll();
      
	for (RecordRating tempRecordRating : temp)
	    if (recordRating.getMfn().equals(tempRecordRating.getMfn()) && recordRating.getIsisdatabasename().equals(tempRecordRating.getIsisdatabasename())
		    && recordRating.getLibraryOwner().getLibraryID() == tempRecordRating.getLibraryOwner().getLibraryID()) {
      
		tempRecordRating.setRating(recordRating.getRating());

		return recordRatingDAO.save(tempRecordRating);
	    }
    
	return recordRatingDAO.save(recordRating);
    }

    @Override
    public float findRatingByRecord(Long libraryId, String mfn, String dataBaseName) {

	List<RecordRating> recordRatings = (List<RecordRating>) recordRatingDAO.findAll();
    
	for (RecordRating recordRating : recordRatings)
	    if (recordRating.getLibraryOwner().getLibraryID().equals(libraryId) && recordRating.getMfn().equals(mfn) && recordRating.getIsisdatabasename().equals(dataBaseName))
		return recordRating.getRating();

	return 0;
    }

    @Override
    public RecordRating updateRecordRating(RecordRating arg0) {
	// FIXME: Eliminar este metodo si no se usa
	return null;
    }       

}