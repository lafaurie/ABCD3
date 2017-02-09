package cu.uci.abcd.opac;
//FIXME FALTAN COMENTARIOS DE INTERFACE
import cu.uci.abcd.domain.opac.RecordRating;

public interface IOpacRecordRatingService {
	
	public RecordRating addRecordRating(RecordRating recordRating);
    
	public RecordRating updateRecordRating(RecordRating recordRating);	
	
	public float findRatingByRecord(Long libraryId, String mfn, String dataBaseName);
	

}
