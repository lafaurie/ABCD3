package cu.uci.abos.l10n.management.db;

import org.eclipse.rap.rwt.RWT;

import cu.uci.abos.l10n.AbosMessageConstants;

public class AbosMessages {

	// Manage databases

	public String BUTTON_CANCEL;
	public String BUTTON_CONSULT;
	public String BUTTON_NEW;
	public String BUTTON_NEW_SEARCH;

	public String LABEL_DATABASE_HITS;
	public String MANAGE_DATABASES_DATABASE_NAME_LABEL;

	// Isis editors

	public String GROUP_TABLE_FDT;
	public String GROUP_TABLE_WD;
	public String GROUP_EDITABLE_AREA_FDT;

	public String BUTTON_ADD_FDT;
	public String BUTTON_SAVE_FDT;
	public String BUTTON_SAVE_ALL_FDT;
	public String BUTTON_RESTORE_FDT;
	public String BUTTON_CANCEL_FDT;

	public String MESSAGE_SEARCH_HINT_FDT;

	public String HEADER_TAG_FDT;
	public String HEADER_NAME_FDT;
	public String HEADER_TYPE;
	public String HEADER_INDICATORS;
	public String HEADER_REP;
	public String HEADER_FIRST_SUBFIELD;
	public String HEADER_SUBFIELDS_PATTERN_FDT;
	public String DATABASE_MANAGEMENT_TOTAL;
	public String DATABASE_MANAGEMENT_HEADER;
	public String HEADER_NODES_WD;
	public String HEADER_NODE_TYPE_WD;
	public String HEADER_PROMPT_WD;
	public String HEADER_DEFAULT_VALUE_WD;
	public String HEADER_DISPLAY_CONTROL_WD;
	public String HEADER_HELP_MSG_WD;
	public String HEADER_PICK_LIST_WD;
	public String HEADER_VALUE_FORMAT_WD;

	public String WIZARD_BUTTON_CANCEL;
	public String WIZARD_BUTTON_BEFORE;
	public String WIZARD_BUTTON_NEXT;
	public String WIZARD_BUTTON_FINISH;

	private AbosMessages() {
		// Prevent instantiation
	}

	public static AbosMessages get() {
		return RWT.NLS.getISO8859_1Encoded(AbosMessageConstants.ABOS_MESSAGES_MANAGEMENT_DB_CLASS_NAME, AbosMessages.class);
	}
}
