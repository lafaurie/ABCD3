package cu.uci.abcd.opac.ui.controller;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.circulation.Penalty;
import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Calendar;
import cu.uci.abcd.domain.management.library.CirculationRule;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Schedule;
import cu.uci.abcd.domain.opac.Comment;
import cu.uci.abcd.domain.opac.OpacDataSources;
import cu.uci.abcd.domain.opac.Rating;
import cu.uci.abcd.domain.opac.Recommendation;
import cu.uci.abcd.domain.opac.RecordRating;
import cu.uci.abcd.domain.opac.Tag;
import cu.uci.abcd.opac.EmailConfigService;
import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.reports.PDFReportGenerator;

public class AllManagementOpacViewController implements ViewController {

	private ProxyController proxyController;

	// //.....Transaction.....\\\\

	public Page<Transaction> findAllTransactionByUser(Long loanUserId, int page, int size, int direction, String orderByString) {

		Page<Transaction> temp = proxyController.getIOpacTransactionService().findAll(loanUserId, page, size, direction, orderByString);

		return temp;
	}

	// //.....Reservation.....\\\\

	public Reservation addReservation(Reservation reservation) {
		return proxyController.getIOpacReservationService().addReservation(reservation);
	}

	public void deleteReservation(Long idReservation) {
		proxyController.getIOpacReservationService().deleteReservation(idReservation);
	}

	public Page<Reservation> findAllCurrentReservationByUser(Long idLoanUser, int page, int size, int direction, String orderByString) {
		return proxyController.getIOpacReservationService().findCurrentReservation(idLoanUser, page, size, direction, orderByString);
	}

	public Page<Reservation> findHistoricalReservationByUser(Long idLoanUser, int page, int size, int direction, String orderByString) {
		return proxyController.getIOpacReservationService().findHistoricalReservationByUser(idLoanUser, page, size, direction, orderByString);
	}

	public Page<Reservation> findHistoricalReservationByUser(Long idLoanUser, int page, int size, int direction, String orderByString, String titleBook, Long state) {
		return proxyController.getIOpacReservationService().findHistoricalReservationByParameters(idLoanUser, titleBook, null, state, page, size, direction, orderByString);
	}

	public int CountReservationPendingByUser(Long idLoanUser) {
		return proxyController.getIOpacReservationService().CountReservationPendingByUser(idLoanUser);
	}

	public CirculationRule findCirculationRule(Nomenclator circulationRuleState, Nomenclator loanUserType, Nomenclator recordType, Long actorID) {
		return proxyController.getIOpacReservationService().findCirculationRule(circulationRuleState, loanUserType, recordType, actorID);
	}

	public List<Calendar> findCalendar(Long libraryID) {
		return proxyController.getIOpacReservationService().findCalendar(libraryID);
	}

	public List<Schedule> findHorarybyLibrary(Long libraryID) {
		return proxyController.getIOpacReservationService().findHorarybyLibrary(libraryID);
	}

	public boolean isAvailableByDate(Date startDate, Date endDate, Long loanObjectId) {
		return proxyController.getIOpacReservationService().isAvailableByDate(startDate, endDate, loanObjectId);
	}

	public boolean isAvailableByDate(Date startDate, Date endDate, Long loanObjectId, Long loanUserId) {
		return proxyController.getIOpacReservationService().isAvailableByDate(startDate, endDate, loanObjectId, loanUserId);
	}

	public List<LoanObject> findLoanObjectsByControlNumberAndLibrary(String controlNumber, Long librariID) {
		return proxyController.getIOpacLoanObjectService().findLoanObjectsByControlNumberAndLibrary(controlNumber, librariID);
	}

	// //.....Penalty.....\\\\

	public Page<Penalty> findAllPenaltyByUser(Long loanUserId, int page, int size, int direction, String orderByString) {
		return proxyController.getIOpacPenaltyService().findAllPenaltyByUser(loanUserId, page, size, direction, orderByString);
	}

	public Page<Penalty> findHistoricalPenaltyByUser(Long loanUserId, int page, int size, int direction, String orderByString) {
		return proxyController.getIOpacPenaltyService().findAllHistoricalPenaltyByUser(loanUserId, page, size, direction, orderByString);
	}

	public void deletePenalty(Long idPenalty) {
		proxyController.getIOpacPenaltyService().deletePenalty(idPenalty);
	}

	// //.....LoanUser.....\\\\

	public LoanUser findLoanUserByPersonIdAndIdLibrary(Long personID, Long idLibrary) {
		return proxyController.getIOpacLoanUserService().findLoanUserByIdPersonAndIdLibrary(personID, idLibrary);
	}

	public void setProxyController(ProxyController proxyController) {
		this.proxyController = proxyController;
	}

	// //.....LoanObject.....\\\\

	public int cantAcquisitionRecents() {
		return proxyController.getIOpacLoanObjectService().findAllLoanObjectByDate().size();
	}   
	

	public List<LoanObject> findAllRecentAcquisitions(Long libraryId) {
		return proxyController.getIOpacLoanObjectService().findRecentLoanObject(libraryId);
	}

	public List<LoanObject> findAvailableLoanObjectByControlNumberAndLibrary(String controlNumber, String databaseName, Long libraryId) {
		return proxyController.getIOpacLoanObjectService().findAllAvailableLoanObjectByControlNumberAndLibrary(controlNumber, databaseName, libraryId);
	}

	public Page<LoanObject> findAllCoppysByLoanObjectAndLibrary(String controlNumber, Long librarId, int page, int size, int direction, String orderByString) {
		return proxyController.getIOpacLoanObjectService().findAllCoppysByLoanObjectAndLibrary(controlNumber, librarId, page, size, direction, orderByString);
	}

	public int findLoanObjectByControlNumberAndLibrary(String controlNumber, Long libraryId) {
		return proxyController.getIOpacLoanObjectService().findLoanObjectByControlNumberAndLibrary(controlNumber, libraryId);
	}

	// //.....PDFGenerator.....\\\\

	public PDFReportGenerator getPdfGenerator() {
		return proxyController.getPDFGeneratorService();
	}

	// //.....Tags....\\\\\

	public Tag addtag(Tag tag) {
		return proxyController.getIOpacTagService().addTag(tag);
	}

	public void deleteTag(Long idTag) {
		proxyController.getIOpacTagService().deleteTag(idTag);
	}
	
	public boolean checkUniqueTag(Long userID, String tagName, String controlNum, String dataBaseName, Long libraryId){
		return proxyController.getIOpacTagService().checkUniqueTag(userID, tagName, controlNum, dataBaseName, libraryId);	
	}

	public List<Tag> findTagsByRegister(String controlNum, String dataBaseName, Long libraryId) {
		return proxyController.getIOpacTagService().findTagsByRegister(controlNum, dataBaseName, libraryId);
	}

	public Page<Tag> findAllTagsByUserAndLibrary(Long userId, Long libraryId, int page, int size, int direction, String orderByString) {
		return proxyController.getIOpacTagService().findAllTagByUserAndLibrary(userId, libraryId, page, size, direction, orderByString);
	}

	public Page<Tag> searchTagsByLibrary(Long userId, Long libraryId, int page, int size, int direction, String orderByString) {
		return proxyController.getIOpacTagService().searchTagsByLibrary(userId, libraryId, page, size, direction, orderByString);
	}

	// //.....Nomenclator....\\ \\

	public Nomenclator findNomenclator(Long idNomenclator) {
		return proxyController.getIOpacNomenclatorService().findNomenclator(idNomenclator);
	}

	public List<Nomenclator> findAllNomenclatorsByCode(String nomencaltorCode) {
		return proxyController.getIOpacNomenclatorService().findAllNomenclatorsByCode(nomencaltorCode);
	}

	public List<Nomenclator> findAllNomencaltors(Long nomenclatorID) {
		return proxyController.getIOpacNomenclatorService().findAllNomencaltors(nomenclatorID);
	}

	// //.....Recommend....\\ \\

	public Recommendation addRecommendation(Recommendation recommendation) {
		return proxyController.getIOpacRecommendationService().addRecommendation(recommendation);
	}

	public Page<Recommendation> findAllRecommendationByUserAndLibrary(Long userId, Long libraryId, int page, int size, int direction, String orderByString) {
		return proxyController.getIOpacRecommendationService().findAllRecommendationByUserAndLibrary(userId, libraryId, page, size, direction, orderByString);
	}

	public void deleteRecommendation(Long idRecomendation) {
		proxyController.getIOpacRecommendationService().deleteRecommendation(idRecomendation);
	}

	// //.....User....\\ \\

	public User addUser(User user) {
		return proxyController.getIOpacUserService().addUser(user);
	}

	public User findUser(Long userId) {
		return proxyController.getIOpacUserService().findUser(userId);
	}

	// /FIXEDDDDD
	public Page<User> findAllUsersByLibrary(Long userId, Long libraryId, int page, int size, int direction, String orderByString) {
		return proxyController.getIOpacUserService().findAllUsersByLibrary(userId, libraryId, page, size, direction, orderByString);
	}

	public Page<User> findAllUsersByLibraryAndUserName(Long userId, Long libraryId, String userName, int page, int size, int direction, String orderByString) {
		return proxyController.getIOpacUserService().findAllUsersByLibraryAndUserName(userId, libraryId, userName, page, size, direction, orderByString);
	}

	// ** ... Jisis ...** \\
	/*public List<RecordIsis> findByMfns(List<String> controlNumber, String databaseName, String libraryIsisDatabasesHomeFolder, Library library) throws JisisDatabaseException {
		return proxyController.getIOpacDataBaseManager().findRecordByControlNumber(controlNumber, databaseName, libraryIsisDatabasesHomeFolder);
	}*/

	public List<String> getDatabaseFormats(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		return proxyController.getIOpacDataBaseManager().getDatabaseFormats(databaseName, libraryIsisDatabasesHomeFolder);
	}

	public FormattedRecord getFormattedRecord(String databaseName, Record record, String formatName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		return proxyController.getIOpacDataBaseManager().getFormattedRecord(databaseName, record, formatName, libraryIsisDatabasesHomeFolder);
	}
	
	public List<String> getDataBaseNames(String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		return proxyController.getIOpacDataBaseManager().getDatabaseNames(libraryIsisDatabasesHomeFolder);
	}

	// ** ... Rating ...** \\
	public Rating addRating(Rating rating, Library library) {

		Rating tempRating = proxyController.getIOpacWeigUpService().addRating(rating);

		float aveRating = proxyController.getIOpacWeigUpService().ratingByRegister(rating.getMaterial(), rating.getDatabasename(), library.getLibraryID());

		RecordRating recordRating = new RecordRating();
		recordRating.setMfn(rating.getMaterial());
		recordRating.setIsisdatabasename(rating.getDatabasename());
		recordRating.setLibraryOwner(library);
		recordRating.setRating(aveRating);

		proxyController.getIOpacRecordRatingService().addRecordRating(recordRating);

		return tempRating;

	}
	
	public Rating findRatingByControlNumAndUser(String controlNum, String dataBaseName, Long libraryId, long userID) {
		
		return proxyController.getIOpacWeigUpService().findRatingByControlNumAndUser(controlNum, dataBaseName, libraryId, userID);

	}

	// ** ... Comment ...** \\

	public Comment addCommentIntoDB(Comment comment) {
		return proxyController.getIOpacCommentService().addComment(comment);
	}

	public Comment upComment(Comment comment) {
		return proxyController.getIOpacCommentService().updateComment(comment);
	}

	public void deleteCommentIntoDB(Long idcomment) {
		proxyController.getIOpacCommentService().deleteComment(idcomment);
	}

	public List<Comment> findAllCommentsByMaterial(String idMaterial, String dataBaseName, String def_home) {
		return proxyController.getIOpacCommentService().findAllCommentsByMaterial(idMaterial, dataBaseName, def_home);
	}
      
	// ** ... Email ... ** \\

	public EmailConfigService getEmailConfig() {
		return proxyController.getIOpacLibraryService().getEmailConfig();
	}

	// ** ... DataSources ... ** \\
	
	public OpacDataSources addOpacDataSources(OpacDataSources opacDataSources) {		
		return proxyController.getIOpacLibraryService().addOpacDataSources(opacDataSources);
	}

	public List<OpacDataSources> getAllDataSourcesByLibrary(Long libraryID) {
		return proxyController.getIOpacLibraryService().getAllDataSourcesByLibrary(libraryID);
	}

	public Page<OpacDataSources> findAllDataSourcesByLibrary(Long libraryID, int page, int size, int direction, String orderByString) {
		return proxyController.getIOpacLibraryService().findAllDataSourcesByLibrary(libraryID, page, size, direction, orderByString);
	}
	     
	public void deleteOpacDataSouce(OpacDataSources opacDataSources){
		proxyController.getIOpacLibraryService().deleteDataSources(opacDataSources);
	}
	
	/**
	 * Test quitar de aki luego
	 */
	
	public List<RecordIsis> find(String term, String databaseName, String libraryIsisDatabasesHomeFolder, Library library, List<Option> options) throws JisisDatabaseException {
		return proxyController.getIOpacDataBaseManager().find(term, databaseName, libraryIsisDatabasesHomeFolder, library, options);
	}	

}
