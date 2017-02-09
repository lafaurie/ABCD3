package cu.uci.abcd.opac;

import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.opac.Recommendation;
//FIXME FALTAN COMENTARIOS DE INTERFACE
public interface IOpacRecommendationService {
	
	public Recommendation addRecommendation(Recommendation recommendation);
    
	public Recommendation updateRecommendation(Long idRecommendation);	
	
	public void deleteRecommendation(Long idSRecommendation);
	     
	public List<Recommendation> findAllRecommendationsByUser();	

	public Recommendation findRecommendation(Long idRecommendation);
	
	public Page<Recommendation> findAllRecommendationByUserAndLibrary(Long userId, Long libraryId, int page, int size, int direction, String orderByString);

}
