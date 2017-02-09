package cu.uci.abcd.opac.ui.controller;

import cu.uci.abcd.opac.IOpacCommentService;
import cu.uci.abcd.opac.IOpacDataBaseManager;
import cu.uci.abcd.opac.IOpacLibraryService;
import cu.uci.abcd.opac.IOpacLoanObjectService;
import cu.uci.abcd.opac.IOpacLoanUserService;
import cu.uci.abcd.opac.IOpacNomenclatorService;
import cu.uci.abcd.opac.IOpacPenaltyService;
import cu.uci.abcd.opac.IOpacRecommendationService;
import cu.uci.abcd.opac.IOpacRecordRatingService;
import cu.uci.abcd.opac.IOpacRegisterUserService;
import cu.uci.abcd.opac.IOpacReservationService;
import cu.uci.abcd.opac.IOpacSelectionListService;
import cu.uci.abcd.opac.IOpacSuggestionService;
import cu.uci.abcd.opac.IOpacTagService;
import cu.uci.abcd.opac.IOpacTransactionService;
import cu.uci.abcd.opac.IOpacUserService;
import cu.uci.abcd.opac.IOpacWeigUpService;
import cu.uci.abcd.opac.ui.tracker.CommentTracker;
import cu.uci.abcd.opac.ui.tracker.ConsultTracker;
import cu.uci.abcd.opac.ui.tracker.LibraryTracker;
import cu.uci.abcd.opac.ui.tracker.LoanObjectTracker;
import cu.uci.abcd.opac.ui.tracker.LoanUserTracker;
import cu.uci.abcd.opac.ui.tracker.NomenclatorTracker;
import cu.uci.abcd.opac.ui.tracker.PDFGeneratorTracker;
import cu.uci.abcd.opac.ui.tracker.PenaltyTracker;
import cu.uci.abcd.opac.ui.tracker.RatingTracker;
import cu.uci.abcd.opac.ui.tracker.RecommendationTracker;
import cu.uci.abcd.opac.ui.tracker.RecordRatingTracker;
import cu.uci.abcd.opac.ui.tracker.RegisterUserTracker;
import cu.uci.abcd.opac.ui.tracker.ReservationTracker;
import cu.uci.abcd.opac.ui.tracker.SelectionListTracker;
import cu.uci.abcd.opac.ui.tracker.SuggestionTracker;
import cu.uci.abcd.opac.ui.tracker.TagTracker;
import cu.uci.abcd.opac.ui.tracker.TransactionTracker;
import cu.uci.abcd.opac.ui.tracker.UserTracker;
import cu.uci.abos.api.util.ServiceListener;
import cu.uci.abos.reports.PDFReportGenerator;

public class ProxyController implements ServiceListener<Object> {

	private IOpacSuggestionService suggestionService;
	private IOpacDataBaseManager dataBaseManager;
	private IOpacPenaltyService penaltyService;
	private IOpacReservationService reservationService;
	private IOpacSelectionListService selectionListService;
	private IOpacRegisterUserService registerUserService;
	private IOpacLoanUserService loanUserService;
	private IOpacTransactionService transactionService;
	private IOpacLoanObjectService loanObjectService;
	private PDFReportGenerator pdfGeneratorService;
	private IOpacTagService tagService;
	private IOpacNomenclatorService nomenclatorService;
	private IOpacCommentService commentService;
	private IOpacRecommendationService recommendationService;
	private IOpacLibraryService libraryService;
	private IOpacUserService userService;
	private IOpacWeigUpService ratingService;
	private IOpacRecordRatingService recordRatingService;

	// private IOpacTopicStatisticsService topicStatisticsService;

	public ProxyController() {

		SuggestionTracker suggestionTracker = new SuggestionTracker();
		suggestionTracker.setSuggestionServiceListener(this);
		suggestionTracker.open();

		ConsultTracker consultTracker = new ConsultTracker();
		consultTracker.setConsultServiceListener(this);
		consultTracker.open();

		PenaltyTracker penaltyTracker = new PenaltyTracker();
		penaltyTracker.setPenaltyServiceListener(this);
		penaltyTracker.open();

		ReservationTracker reservationTracker = new ReservationTracker();
		reservationTracker.setReservationServiceListener(this);
		reservationTracker.open();

		SelectionListTracker selectionListTracker = new SelectionListTracker();
		selectionListTracker.setSelectionListServiceListener(this);
		selectionListTracker.open();

		RegisterUserTracker registerUserTracker = new RegisterUserTracker();
		registerUserTracker.setRegisterUserServiceListener(this);
		registerUserTracker.open();

		LoanUserTracker loanUserTracker = new LoanUserTracker();
		loanUserTracker.setLoanUserServiceListener(this);
		loanUserTracker.open();

		TransactionTracker transactionTracker = new TransactionTracker();
		transactionTracker.setTransactionServiceListener(this);
		transactionTracker.open();

		LoanObjectTracker loanObjectTracker = new LoanObjectTracker();
		loanObjectTracker.setLoanObjectServiceListener(this);
		loanObjectTracker.open();

		PDFGeneratorTracker pdfGeneratorTracker = new PDFGeneratorTracker();
		pdfGeneratorTracker.setPDFGeneratorServiceListener(this);
		pdfGeneratorTracker.open();

		TagTracker tagTracker = new TagTracker();
		tagTracker.setTagServiceListener(this);
		tagTracker.open();

		NomenclatorTracker nomenclatorTracker = new NomenclatorTracker();
		nomenclatorTracker.setNomenclatorServiceListener(this);
		nomenclatorTracker.open();

		RecommendationTracker recommendationTracker = new RecommendationTracker();
		recommendationTracker.setRecommendationServiceListener(this);
		recommendationTracker.open();

		CommentTracker commentTracker = new CommentTracker();
		commentTracker.setCommentServiceListener(this);
		commentTracker.open();

		LibraryTracker libraryTracker = new LibraryTracker();
		libraryTracker.setLibraryService(this);
		libraryTracker.open();

		UserTracker userTracker = new UserTracker();
		userTracker.setUserService(this);
		userTracker.open();

		RatingTracker ratingTracker = new RatingTracker();
		ratingTracker.setRatingServiceListener(this);
		ratingTracker.open();

		RecordRatingTracker recordRatingTracker = new RecordRatingTracker();
		recordRatingTracker.setRecordRatingServiceListener(this);
		recordRatingTracker.open();
		/*
		 * TopicsStatisticTracker topicsStatisticTracker = new
		 * TopicsStatisticTracker();
		 * topicsStatisticTracker.setTopicStatisticServiceListener(this);
		 * topicsStatisticTracker.open();
		 */

	}        
       
	@Override
	public void addServiceListener(Object service) {
		if (service instanceof IOpacSuggestionService) {
			this.suggestionService = (IOpacSuggestionService) service;
		} else if (service instanceof IOpacDataBaseManager) {
			this.dataBaseManager = (IOpacDataBaseManager) service;
		} else if (service instanceof IOpacPenaltyService) {
			this.penaltyService = (IOpacPenaltyService) service;
		} else if (service instanceof IOpacReservationService) {
			this.reservationService = (IOpacReservationService) service;
		} else if (service instanceof IOpacSelectionListService) {
			this.selectionListService = (IOpacSelectionListService) service;
		} else if (service instanceof IOpacUserService) {     
			this.userService = (IOpacUserService) service;
		} else if (service instanceof IOpacRegisterUserService) {
			this.registerUserService = (IOpacRegisterUserService) service;
		} else if (service instanceof IOpacLoanUserService) {
			this.loanUserService = (IOpacLoanUserService) service;
		} else if (service instanceof IOpacTransactionService) {
			this.transactionService = (IOpacTransactionService) service;
		} else if (service instanceof IOpacLoanObjectService) {
			this.loanObjectService = (IOpacLoanObjectService) service;
		} else if (service instanceof PDFReportGenerator) {
			this.pdfGeneratorService = (PDFReportGenerator) service;
		} else if (service instanceof IOpacTagService) {
			this.tagService = (IOpacTagService) service;
		} else if (service instanceof IOpacNomenclatorService) {
			this.nomenclatorService = (IOpacNomenclatorService) service;
		} else if (service instanceof IOpacRecommendationService) {
			this.recommendationService = (IOpacRecommendationService) service;
		} else if (service instanceof IOpacCommentService) {
			this.commentService = (IOpacCommentService) service;
		} else if (service instanceof IOpacLibraryService) {
			this.libraryService = (IOpacLibraryService) service;
		} else if (service instanceof IOpacWeigUpService) {
			this.ratingService = (IOpacWeigUpService) service;
		} else if (service instanceof IOpacRecordRatingService) {
			this.recordRatingService = (IOpacRecordRatingService) service;
		}     
		   
		
		
	}

	public IOpacSuggestionService getSuggestionService() {
		return suggestionService;
	}

	public IOpacDataBaseManager getIOpacDataBaseManager() {
		return dataBaseManager;
	}

	public IOpacPenaltyService getIOpacPenaltyService() {
		return penaltyService;
	}

	public IOpacReservationService getIOpacReservationService() {
		return reservationService;
	}

	public IOpacSelectionListService getIOpacSelectionListService() {
		return selectionListService;
	}

	public IOpacRegisterUserService getIOpacRegisterUserService() {
		return registerUserService;
	}

	public IOpacLoanUserService getIOpacLoanUserService() {
		return loanUserService;
	}

	public IOpacTransactionService getIOpacTransactionService() {
		return transactionService;
	}

	public IOpacLoanObjectService getIOpacLoanObjectService() {
		return loanObjectService;
	}

	public PDFReportGenerator getPDFGeneratorService() {
		return pdfGeneratorService;
	}

	public IOpacTagService getIOpacTagService() {
		return tagService;
	}

	public IOpacNomenclatorService getIOpacNomenclatorService() {
		return nomenclatorService;
	}

	public IOpacRecommendationService getIOpacRecommendationService() {
		return recommendationService;
	}

	public IOpacCommentService getIOpacCommentService() {
		return commentService;
	}

	public IOpacLibraryService getIOpacLibraryService() {
		return libraryService;
	}

	public IOpacUserService getIOpacUserService() {
		return userService;
	}

	public IOpacWeigUpService getIOpacWeigUpService() {
		return ratingService;
	}

	public IOpacRecordRatingService getIOpacRecordRatingService() {
		return recordRatingService;
	}

}
