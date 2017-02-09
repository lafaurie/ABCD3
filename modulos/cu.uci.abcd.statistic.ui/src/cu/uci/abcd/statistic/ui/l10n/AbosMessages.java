package cu.uci.abcd.statistic.ui.l10n;

import org.eclipse.rap.rwt.RWT;


public class AbosMessages {


	// MENU
	public String MENU_ADMIN_TABLE_ROPORT_STATISTIC;
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
	public String MESSAGE_MISSING_FIELDS;
	public String MESSAGE_EMPTY_REPORT;
	public String MESSAGE_VALIDATE_FIELD_REQUIRED;
	public String MESSAGE_INCORRECT_SQL;

	// BUTTONS
	public String BUTTON_ACEPT;
	public String BUTTON_CANCEL;
	public String BUTTON_GENERATE;
	public String BUTTON_TEST;
	public String BUTTON_CONSULT;
	public String BUTTON_NEW_SEARCH;
	public String BUTTON_NEW;
	public String BUTTON_CLOSE;
	public String BUTTON_GENERATE_OUT;
	public String BUTTON_ASSOCIATE_SELECTION;

	public String BUTTON_EXPORT_TO_EXCEL;
	public String BUTTON_EXPORT_TO_PDF;
	public String BUTTON_UPDATE;
	public String BUTTON_ADD;
	public String BUTTON_INSERT;
	public String BUTTON_RETURN;
	public String BUTTON_VALIDATE;
	public String BUTTON_DELETE;
	public String BUTTON_SEARCH;
	public String BUTTON_ATTACH;
	public String BUTTON_SAVE;
	public String BUTTON_EDIT;
	public String BUTTON_ERASE;
	public String BUTTON_ACEPT_ALL;


	// COMBOS
	public String COMBO_DATABASE_ISIS;
	public String COMBO_DATABASE_POSTGRES;
	public String COMBO_VALUE_COL;
	public String COMBO_VALUE_ROW;

	// LABELS Admin Table
	public String LABEL_TYPE;
	public String LABEL_SEARCH_CRITERIA;
	public String LABEL_EDIT_VARIABLE;
	public String LABEL_LIBRARY_NAME;

	public String LABEL_NAME_DATABASE;
	public String LABEL_GENERATE_REPORT;
	public String LABEL_MANAGE_INDICATOR;
	public String LABEL_MANAGE_REPORT;
	public String LABEL_ADD_INDICATOR;
	public String LABEL_EDIT_INDICATOR;
	public String LABEL_VIEW_INDICATOR;
	public String LABEL_ADD_NEW_REPORT;
	public String LABEL_EDIT_REPORT;
	public String LABEL_VIEW_REPORT;
	public String LABEL_REPORT_DATA;
	public String LABEL_INDICATORS;
	public String LABEL_INDICATORS_ASSOCIATED;
	public String LABEL_RANGE_OF_DATE;
	

	// LABELS Create One Table

	public String LABEL_DATABASE_ISIS;
	public String LABEL_COLUMN_HEADING;
	public String LABEL_COLUMN_VALUE;
	public String LABEL_ROW_HEADING;
	public String LABEL_ROW_VALUE;
	public String LABEL_NAME;
	public String LABEL_DESCRIPTION;
	public String LABEL_CREATE_NEW_VARIABLE;

	// LABELS Generate Report
	public String LABEL_TYPE_OF_REPORT;
	public String LABEL_ACADEMIC_COURSE;
	public String LABEL_CODE;
	public String LABEL_SIXMONTHS;
	public String LABEL_ORGANISM;
	
	
	

	// LABELS Generate Exit
	public String LABEL_LIST_OF_COINCIDENCES;

	public String LABEL_NAME_REPORT;
	public String LABEL_INDICATOR_DATA;
	public String LABEL_NUM_INDICATOR;
	public String LABEL_CONSULT_QUERY;

	// LABELS USED TABLE EXITEN

	public String LABEL_TYPE_OF_DATABASE;
	

	// LABELS Create One Table
	public String LABEL_ESTROCTURE_OF_DATABASE;


	// LABELS Generate Exit
	public String LABEL_RANGE_MFN;
	public String LABEL_FROM;

	// LABELS USED TABLE EXITE

	public String LABEL_DATE_OF_TABLE;
	public String LABEL_CREATE_NEW_TABLE;
	public String LABEL_FIELD;
	public String LABEL_HEADER;
	public String LABEL_SELEC_DATABASE;
	
	public String LABEL_TABLE_A;

	public String LABEL_TO;
	public String LABEL_SALVE_HOW;
	public String LABEL_TABLE_B;
	public String LABEL_VALUE_TABLE_B;
	public String LABEL_DATA_OF_TEST;
	public String LABEL_CODE_OUT;



	public String LABEL_USE_TABLE_EXISTENT;
	public String LABEL_RAGE_OF_DATE_REPORT;
	public String LABEL_ADD_VARIABLE_STATISTIC;
	public String LABEL_FORMAT_OF_EXTENTION;
	public String LABEL_PREFIX;
	public String LABEL_GENERATE_DINAMIC_REPORT;

	// LINKS
	public String LINK_CLEAR;
	public String LINK_DELETE_TABLE;
	public String LINK_NEW_TABLE;
	public String LINK_VIEW_STRUTURE_REPORT;

	// TABLES
	public String TABLE_NAME;
	public String TABLE_TYPE;
	public String TABLE_DESCRIPTION;
	public String TABLE_CONCEPT;                             
	public String TABLE_COUNT;


	private AbosMessages() {
		// Prevent instantiation
	}

	public static AbosMessages get() {
		return RWT.NLS.getISO8859_1Encoded( "cu.uci.abcd.statistic.ui.l10n.AbosMessages" , AbosMessages.class);
	}
}
