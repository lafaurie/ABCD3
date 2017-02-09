package cu.uci.abcd.management.db.l10n;

import org.eclipse.rap.rwt.RWT;     



public class AbosMessages {

	// Manage Buttons

	public String BUTTON_CANCEL;
	public String BUTTON_FINISH;
	public String BUTTON_NEXT;
	public String BUTTON_BACK;
	public String BUTTON_ADD;
	public String BUTTON_DELETE;
	
	public String BUTTON_CONSULT;
	public String BUTTON_NEW_NAME;
	public String BUTTON_NEW_SEARCH;
	
	// Database Name 
	public String ENTER_DATABASE_NAME_LABEL;
	public String NEW_DATABASE_NAME_LABEL;
	
	// Database Structure
	public String DATABASE_STRUCTURE_LABEL;
	public String LABEL_TAG;
	public String LABEL_FIELD;
	public String LABEL_TYPE;
	public String LABEL_INDICATORS;
	public String LABEL_REPEATABLE;
	public String LABEL_FIRST_SUBFIELD;
	public String LABEL_SUBFIELDS_PATTERN;
	
	
	public String LABEL_SELECT_DATABASE_HOME;

	public String LABEL_DATABASE_HITS;
	public String MANAGE_DATABASES_DATABASE_NAME_LABEL;

	public String ID_FST;
	public String PREFIJO;
	public String NOMBRE_CAMPO;
	public String NUMERO_CONTROL;
	public String ORDENABLE;
	public String SEE_FDT;
	public String FIELDS_TO_SELECT;
	public String ADVANCED_SEARCH_CRITERIA;
	public String SELECTED_FIELD;
	
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
	public String ISIS_MANAGEMENT_HEADER;
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
	
	// HOJA DE TRABAJO
	public String WORKSHEET_MANAGEMENT_HEADER;
	public String WORKSHEET_HEADER;
	
	
	private AbosMessages() {
		// Prevent instantiation
	}

	public static AbosMessages get() {
		return RWT.NLS.getISO8859_1Encoded("cu.uci.abcd.management.db.l10n.AbosMessages", AbosMessages.class);
	}
}
