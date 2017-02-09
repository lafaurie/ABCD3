package cu.uci.abcd.opac.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dao.opac.RecommendationDAO;
import cu.uci.abcd.dao.specification.OpacSpecification;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.domain.opac.Recommendation;
import cu.uci.abcd.opac.IOpacRecommendationService;

public class OpacRecommendationServiceImpl implements IOpacRecommendationService {

	RecommendationDAO recommendationDAO;
		
	@Override
	public Recommendation addRecommendation(Recommendation recommendation) {		
		return recommendationDAO.save(recommendation);
	}

	@Override
	public void deleteRecommendation(Long idRecomendation) {
		recommendationDAO.delete(idRecomendation);		
	}

	@Override
	public Page<Recommendation> findAllRecommendationByUserAndLibrary(Long userId, Long libraryId, int page, int size, int direction, String orderByString) {
		
		return recommendationDAO.findAll(OpacSpecification.searchRecommendationByUserAndLibrary(userId, libraryId), PageSpecification.getPage(page, size, direction, orderByString));
	}
      
	@Override
	public List<Recommendation> findAllRecommendationsByUser() {
		return null;
	}

	@Override
	public Recommendation findRecommendation(Long arg0) {
		return null;
	}

	@Override
	public Recommendation updateRecommendation(Long arg0) {
		
		return null;
	}
	
	public void bind(RecommendationDAO recommendationDAO, Map<String, Object> properties) {
		this.recommendationDAO = recommendationDAO;
		System.out.println("servicio registrado");
	}
}
