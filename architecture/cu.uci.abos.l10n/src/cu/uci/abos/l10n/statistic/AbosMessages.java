package cu.uci.abos.l10n.statistic;

import org.eclipse.rap.rwt.RWT;

import cu.uci.abos.l10n.AbosMessageConstants;

public class AbosMessages {

	// MODULES

	public String STATISTIC_MODULE;

	// MENU
	public String MENU_ADMIN_TABLE_ROPORT_STATISTIC;
	public String MENU_CREATE_TABLE_OR_REPORT;
	public String MENU_CONFIGURATION;
	public String MENU_ADMIN_VARIABLE_STATIST;
	public String MENU_GENERAR_REPORT;
	public String MENU_ADMIN_REPORT;
	public String MENU_ADMIN_INDICATOR;

	// System Messages
	public String MESSAGE_SELECTED;
	public String MESSAGE_COINCIDENT;
	public String MESSAGE_ADD;
	public String MESSAGE_UPDATE;
	public String MESSAGE_DELETE;
	public String MESSAGE_NO_ADD;
	public String MESSAGE_REPORT;
	public String MESSAGE_INDICATOR_SELEC;
	public String MESSAGE_ADD_REPORT;
	public String MESSAGE_MISSING_FIELDS;
	public String MESSAGE_NAME_OF_REPORT;
	public String MESSAGE_VALIDATE_FIELD_REQUIRED;

	// BUTTONS
	public String BUTTON_ACEPT;
	public String BUTTON_ADD_INDICATOR;
	public String BUTTON_CANCEL;
	public String BUTTON_VISUALIZE;
	public String BUTTON_TEST;
	public String BUTTON_CONSULT;
	public String BUTTON_NEW_SEARCH;
	public String BUTTON_NEW;
	public String BUTTON_CLOSE;
	public String BUTTON_GENERATE_OUT;
	public String BUTTON_ASSOCIATE_SELECTION;
	public String BUTTON_ADITIONAL_INDICATOR;

	public String BUTTON_EXPORT_TO_EXCEL;
	public String BUTTON_EXPORT_TO_PDF;
	public String BUTTON_LOG_IN;
	public String BUTTON_UPDATE;
	public String BUTTON_MODIFY;
	public String BUTTON_ADD;
	public String BUTTON_INSERT;
	public String BUTTON_RETURN;
	public String BUTTON_VALIDATE;
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
	public String BUTTON_SEE_STRUCTOR_OF_REPORT;

	// RADIOS

	public String RADIO_DATABASE_ISIS;

	public String RADIO_DATABASE_POSTGRES;

	// COMBOS
	public String COMBO_DATABASE_ISIS;
	public String COMBO_DATABASE_POSTGRES;
	public String COMBO_VALUE_COL;
	public String COMBO_VALUE_ROW;
	public String COMBO_TYPE_OF_REPORT;

	// LABELS Admin Table
	public String LABEL_TYPE;
	public String LABEL_SEARCH_CRITERIA;
	public String LABEL_EDIT_VARIABLE;
	public String LABEL_LIBRARY_NAME;

	public String LABEL_NAME_DATABASE;
	public String LABLE_ADMIN_REPORT;
	public String LABEL_ADD_INDICATOR;

	// LABELS Create One Table

	public String LABEL_DATABASE_ISIS;
	public String LABEL_COLUMN_HEADING;
	public String LABEL_COLUMN_VALUE;
	public String LABEL_ROW_HEADING;
	public String LABEL_ROW_VALUE;
	public String LABEL_SEPARATOR;
	public String lABEL_SALVE_TABLE_HOW;
	public String LABEL_NAME;
	public String LABEL_DESCRIPTION;
	public String LABEL_CREATE_NEW_VARIABLE;

	// LABELS Generate Report
	public String LABEL_TYPE_OF_REPORT;
	public String LABEL_RANGE_OF_DATE_REPORT;
	public String LABEL_START_DATE;
	public String LABEL_END_DATE;

	// LABELS Generate Exit
	public String LABEL_LIST_OF_TABLES_EXISTEN;

	public String LABEL_ADD_NEW_REPORT;
	public String LABEL_NAME_REPORT;
	public String LABEL_NAME_INDICATOR;
	public String LABEL_NUM_INDICATOR;
	public String LABEL_CONSULT_QUERY;
	public String LABEL_ADMIN_INDICATOR;

	// LABELS USED TABLE EXITEN

	public String LABEL_TYPE_OF_DATABASE;

	// LABELS Admin Table

	public String LABEL_TABLE_NAME;

	// LABELS Create One Table
	public String LABEL_ESTROCTURE_OF_DATABASE;

	public String LABEL_HEAD_COL;
	public String LABEL_VALUE_COL;
	public String LABEL_HEAD_ROW;
	public String LABEL_VALUE_ROW;

	public String lABEL_NAME;

	// LABELS Generate Exit
	public String lABEL_LIST_OF_TABLES_EXISTEN;
	public String LABEL_RANGE_MFN;
	public String LABEL_UP;
	public String LABEL_FROM;

	// LABELS USED TABLE EXITEN

	public String LABEL_CRITERION_SEARCH;

	public String LABEL_DATE_OF_TABLE;
	public String LABEL_CREATE_NEW_TABLE;
	public String LABEL_FIELD;
	public String LABEL_HEADER;

	public String LABEL_SELEC_DATABASE;
	public String lABEL_DATA_OF_COLUMNA;
	public String LABEL_TABLE_A;

	public String LABEL_TO;
	public String LABEL_SALVE_HOW;
	public String LABEL_TABLE_OR_REPORT_STATISTIC_COINCIDET;
	public String LABEL_TABLE_B;
	public String LABEL_VALUE_TABLE_B;
	public String LABEL_SALVE_REPORT_AS;
	public String LABEL_DATA_OF_TEST;
	public String LABEL_CODE_OUT;

	public String LABEL_DATABASE_POSTGRES;

	public String LABEL_DATA_BASE_POSTGRES;

	public String LABEL_USE_TABLE_EXISTENT;
	public String LABEL_LIST_OF_REPORT_EXISTENT;
	public String LABEL_RAGE_OF_DATE_REPORT;
	public String LABEL_ADD_VARIABLE_STATISTIC;
	public String LABEL_FORMAT_OF_EXTENTION;
	public String LABEL_PREFIX;
	public String LABEL_HEAD_OF_REPORT;
	public String LABEL_GENERATE_DINAMIC_REPORT;
	public String LABEL_SELECT_RAGE_OF_DATE_OR_REPORT_TO_GENERAT;
	public String LABEL_ACTION;

	// LINKS
	public String LINK_CLEAR;
	public String LINK_DELETE_TABLE;
	public String LINK_NEW_TABLE;
	public String LINK_VIEW_STRUTURE_REPORT;

	// TABLES
	public String TABLE_NAME;
	public String TABLE_TYPE;
	public String TABLE_DESCRIPTION;

	// GROUPS

	// CONTRIBUTOR_NAME

	// RADIOS

	// COMBOS

	private AbosMessages() {
		// Prevent instantiation
	}

	public static AbosMessages get() {
		return RWT.NLS.getISO8859_1Encoded(AbosMessageConstants.ABOS_MESSAGES_STATISTIC_CLASS_NAME, AbosMessages.class);
	}
}
