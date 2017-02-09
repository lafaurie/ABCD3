package cu.uci.abos.l10n.opac;

import org.eclipse.rap.rwt.RWT;

import cu.uci.abos.l10n.AbosMessageConstants;

public class AbosMessages {

	// HEADER
	public String HEADER_LOGIN_TEXT;
	public String HEADER_OPAC_TEXT;

	// LOGIN

	// CONTRIBUTORS_NAMES
	public String UI_ACQUISITION;
	public String UI_CIRCULATION;
	public String UI_CONFIGURATION_OPTIONS;
	public String UI_MAIN_CONTENT;
	public String UI_MY_ALERTS;
	public String UI_MY_CURRENT_STATE;
	public String UI_MY_HISTORICAL_STATE;
	public String UI_MY_SELECTION_LIST_CONTENT;
	public String UI_MY_SELECTION_LIST;
	public String UI_MY_TAGS;
	public String UI_RECENT_ACQUISITIONS;
	public String UI_REGISTER_USER;
	public String UI_REGISTER_SCORE;
	public String UI_SAVE_IN_SELECTION_LIST;
	public String UI_SELECTION;
	public String UI_MY_SUGGESTIONS;
	public String UI_VIEW_LOG;

	// PERFIL
	public String P_PROFILE;
	public String P_RESUME;
	public String P_HISTORICAL_RESUME;
	public String P_SUGGESTION;
	public String P_MY_SUGGESTIONS;
	public String P_RECENT_ACQUISITIONS;
	public String P_POPULAR_TOPICS;
	public String P_MORE_LOANED;

	// ACQUISITION
	public String LABEL_BOOK_TITLE;
	public String LABEL_BOOK_AUTHOR;
	public String LABEL_BOOK_PUBLICATION_DATE;
	public String LABEL_BOOK_EDITOR;
	public String LABEL_BOOK_NOTE;
	public String INFORMATION_SUGGEST;

	public String BUTTON_SUGGESTION;

	// RESUME
	public String LABEL_USER_PROFILE;
	public String LABEL_USER_NAME;
	public String LABEL_USER_DNI;
	public String LABEL_USER_SEX;
	public String LABEL_USER;
	public String LABEL_USER_TYPE;

	public String TAB_LOANS;
	public String TAB_PENALTIES;
	public String TAB_RESERVATIONS;

	public String TABLE_LOANS_LOAN_TYPE;
	public String TABLE_LOANS_TITLE;
	public String TABLE_LOANS_OBJECT_TYPE;
	public String TABLE_LOANS_LOAN_DATE;
	public String TABLE_LOANS_DELIVERY_DATE;

	public String TABLE_PENALTY_START_DATE;
	public String TABLE_PENALTY_END_DATE;
	public String TABLE_PENALTY_TYPE;
	public String TABLE_PENALTY_AMOUNT_TO_PAY;

	public String TABLE_RESERVATION_TITLE;
	public String TABLE_RESERVATION_AUTHOR;
	public String TABLE_RESERVATION_DATE;
	public String TABLE_RESERVATION_EXPIRATION_DATE;
	public String TABLE_RESERVATION_STATE;

	// CONFIGURATION_OPTIONS
	public String LABEL_CONFIGURATION_RESULTS;
	public String LABEL_RESULTS_BY_PAGE;

	// MESSAGES OF FIELDS VALIDATIONS
	public String EMPTY_FIELDS;

	private AbosMessages() {
		// Prevent instantiation
	}

	public static AbosMessages get() {
		return RWT.NLS.getISO8859_1Encoded(AbosMessageConstants.ABOS_MESSAGES_OPAC_CLASS_NAME, AbosMessages.class);
	}
}
