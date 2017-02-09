package cu.uci.abcd.administration.nomenclators.l10n;

import org.eclipse.rap.rwt.RWT;

public class AbosMessages {

	// Management Nomenclators
	// LABEL
	public String LABEL_DESCRIPTION;
	public String LABEL_SEARCH_CRITERIA;
	public String LABEL_NOMENCLATOR_DATA;
	public String LABEL_NOMENCLATOR_TYPE;
	public String LABEL_NOMENCLATOR_VALUE;
	public String LABEL_NOMENCLATOR_LIST;
	public String LABEL_VALUE;
	public String LABEL_MANAGE_NOMENCLATOR;

	// Name of Interfaces
	public String NAME_UI_MANAGE_NOMENCLATOR;
	public String NAME_UI_REGISTER_NOMENCLATOR;
	public String NAME_UI_UPDATE_NOMENCLATOR;
	public String NAME_UI_WATCH_NOMENCLATOR;

	// BUTTONS
	public String BUTTON_LOG_IN;
	public String BUTTON_NEW;
	public String BUTTON_ACEPT;
	public String BUTTON_UPDATE;
	public String BUTTON_CLOSE;
	public String BUTTON_CANCEL;
	public String BUTTON_MODIFY;
	public String BUTTON_ADD;
	public String BUTTON_INSERT;
	public String BUTTON_EXPORT_TO_EXCEL;
	public String BUTTON_EXPORT_TO_PDF;
	public String BUTTON_RETURN;
	public String BUTTON_VALIDATE;
	public String BUTTON_CONSULT;
	public String BUTTON_NEW_SEARCH;
	public String BUTTON_ASSOCIATE;
	public String BUTTON_DISSOCIATE;
	public String BUTTON_RENEW;
	public String BUTTON_CURRENT_STATUS;
	public String BUTTON_HISTORY;
	public String BUTTON_LOSS_REGISTER;
	public String BUTTON_LOAN;
	public String BUTTON_APPROVE_ALL;
	public String BUTTON_REJECT_ALL;
	public String BUTTON_DELETE;
	public String BUTTON_SEARCH;
	public String BUTTON_MORE_OPTIONS;
	public String BUTTON_START_SEARCH;
	public String BUTTON_ATTACH;
	public String BUTTON_SAVE;
	public String BUTTON_EDIT;
	public String BUTTON_ERASE;
	public String BUTTON_ACEPT_ALL;

	// System Messages
	public String MESSAGE_SELECTED;
	public String MESSAGE_COINCIDENT;
	public String MESSAGE_ADD;
	public String MESSAGE_UPDATE;
	public String MESSAGE_DELETE;
	public String MESSAGE_NO_ADD;

	private AbosMessages() {
		// Prevent instantiation
	}

	public static AbosMessages get() {
		return RWT.NLS.getISO8859_1Encoded("cu.uci.abcd.administration.nomenclators.l10n.AbosMessages", AbosMessages.class);
	}
}
