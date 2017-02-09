package cu.uci.abcd.opac.impl;

import java.util.List;
import java.util.Map;

import cu.uci.abcd.dao.opac.RatingDAO;
import cu.uci.abcd.domain.opac.Rating;
import cu.uci.abcd.opac.IOpacWeigUpService;

public class OpacWeigUpServiceImpl implements IOpacWeigUpService {

	RatingDAO ratingDAO;

	public void bind(RatingDAO ratingDAO, Map<String, Object> properties) {
		this.ratingDAO = ratingDAO;
		System.out.println("servicio registrado");
	}

	@Override
	public Rating addRating(Rating rating) {

		List<Rating> temp = (List<Rating>) ratingDAO.findAll();

		for (Rating tempRating : temp)
			if (rating.getMaterial().equals(tempRating.getMaterial()) && rating.getDuser().equals(tempRating.getDuser())) {
				tempRating.setRating(rating.getRating());
				return ratingDAO.save(tempRating);
			}

		return ratingDAO.save(rating);
	}

	@Override
	public List<Rating> findAllRatingByRegister() {

		return null;
	}

	@Override
	public float ratingByRegister(String controlNum, String dataBaseName, Long libraryId) {

		float total = 0;
		float count = 0;

		List<Rating> tempRatings = (List<Rating>) ratingDAO.findAll();

		for (Rating rating : tempRatings)
			if (rating.getMaterial().equals(controlNum) && rating.getDatabasename().equals(dataBaseName)) {

				total += rating.getRating();
				count++;
			}

		return total / count;
	}

	@Override
	public Rating updateRating(Rating arg0) {

		return null;
	}     

	@Override
	public Rating findRatingByControlNumAndUser(String controlNum, String dataBaseName, Long libraryId, long userID) {
    
		List<Rating> ratings = (List<Rating>) ratingDAO.findAll();

		for (Rating rating : ratings)
			if (rating.getDuser().getUserID() == userID)
				if (rating.getMaterial().equals(controlNum) && rating.getDatabasename().equals(dataBaseName) && rating.getLibrary().getLibraryID() == libraryId)
					return rating;

		return null;
	}
}
