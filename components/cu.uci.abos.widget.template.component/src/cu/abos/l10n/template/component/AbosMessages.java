package cu.abos.l10n.template.component;

import org.eclipse.rap.rwt.RWT;

import cu.abos.l10n.template.component.AbosMessageConstants;

public class AbosMessages {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	// BUTTONS
	public String BUTTON_NEW_AUTHORITY;
	public String BUTTON_SEARCH_MATERIALS;
	public String BUTTON_EXIT;
	public String BUTTON_TEST_PATTERN;
	public String BUTTON_REBOOT_PATTERN;
	public String BUTTON_SAVE_SUBSCRIPTION;
	public String BUTTON_SHOW_HIDE_SCHEMES;
	public String BUTTON_NEW_SUBSCRIPTION;
	public String BUTTON_SEARCH_TERM;
	public String BUTTON_ASSOCIATE_TERM;
	public String BUTTON_SEARCH;

	// RADIOS
	public String RADIO_TEMPORARY_REPOSITORY;
	public String RADIO_LOCAL;
	public String RADIO_AUTHORITIES;
	public String RADIO_HEADER;
	public String RADIO_ALL_HEADINGS;
	public String RADIO_SEEK_SUBSCRIPTION;
	public String RADIO_LEND;
	public String RADIO_SEARCH_IN_CATALOG;

	// COMBOS
	public String COMBO_PERSONAL_NAME;
	public String COMBO_CONTAINS;
	public String COMBO_HEADER;
	public String COMBO_PLEASE_CHOOSE;
	public String COMBO_NUMBER_OF_COPIES;
	public String COMBO_NONE;

	// VALUES OF THE COMBO
	public String VALUE_COMBO_SERIAL;
	public String VALUE_COMBO_NO_SERIAL;
	public String VALUE_COMBO_WITHOUT_PROCESSING;
	public String VALUE_COMBO_PROCESSED;

	// LABELS
	public String LABEL_FILE_NAME;
	public String LABEL_SEARCH_RESULTS_AUTHORITY;
	public String LABEL_RESULTS;
	public String LABEL_CATALOGER;
	public String LABEL_RECORD_TYPE;
	public String LABEL_STARTING_DATE;
	public String LABEL_FINAL_DATE;
	public String LABEL_PUBLICATION_OF;
	public String LABEL_FIRST_ISSUE;
	public String LABEL_FRECUENCY;
	public String LABEL_PATTERN_OF;
	public String LABEL_NUMERATION;
	public String LABEL_MANUAL_HISTORY;
	public String LABEL_START_OF_THE;
	public String LABEL_SUBSCRIPTION;
	public String LABEL_DURATION_OF_THE;
	public String LABEL_FORMULA_OF;
	public String LABEL_ENTER_QUANTITY_AND_FIGURES;
	public String LABEL_SPP;
	public String LABEL_MATTER;
	public String LABEL_TERM_OBTAINED;
	public String LABEL_PROVIDER;
	public String LABEL_SIGNATURE;
	public String LABEL_LIBRARY;
	public String LABEL_NOTIFICATION_TO;
	public String LABEL_PUBLIC_NOTE;
	public String LABEL_NONPUBLIC_NOTE;
	public String LABEL_NOTE;
	public String LABEL_ENABLE_FALSE;
	public String LABEL_TITLE_PTS;
	public String LABEL_AUTHOR_PTS;
	public String LABEL_SUBSCRIPTION_N;
	public String LABEL_RECORD_PTS;
	public String LABEL_USER_PTS;
	public String LABEL_SEARCH_RECORDS_FILTERED_BY;
	public String LABEL_TITLE;
	public String LABEL_AUTHOR;
	public String LABEL_EDITORIAL;
	public String LABEL_PUBLICATION_YEAR;
	public String LABEL_TYPE_OF_RECORD;
	public String LABEL_RECORD_STATE;

	// LINKS
	public String LINK_CHECK_ALL;
	public String LINK_UNCHECK_ALL;
	public String LINK_SEARCH_PROVIDER;
	public String LINK_SEARCH_BY_RECORD;

	// TABLES
	public String TABLE_TITLE;
	public String TABLE_AUTHOR;
	public String TABLE_FILE;
	public String TABLE_USER;
	public String TABLE_SELECT;
	public String TABLE_SUMMARY;
	public String TABLE_USED_IN;
	public String TABLE_NUMBER;
	public String TABLE_STARTING_WITH;
	public String TABLE_EXPECTED_COPIES;
	public String TABLE_X;
	public String TABLE_Y;
	public String TABLE_Z;
	public String TABLE_INSERT;
	public String TABLE_ONCE_EVERY;
	public String TABLE_WHEN_MORE_THAN;
	public String TABLE_INTERNAL_COUNTER;
	public String TABLE_BACK_TO;
	public String TABLE_BEGINS_WITH;
	public String TABLE_ISSN;
	public String TABLE_NOTES;
	public String TABLE_LIBRARY_NUMBER;

	// GROUPS
	public String GROUP_FILE_DATA;
	public String GROUP_PROPOSED_FILE;
	public String GROUP_PLANNING_PERIODICAL_PUBLICATIONS;
	public String GROUP_CONSULT_SUBSCRIPTION_PERIODICAL_PUBLICATIONS;
	public String GROUP_SUBSCRIPTION_DETAILS;
	public String GROUP_DATA_MATERIAL;

	// MENU
	public String MENU_CATALOGATION;

	//TOOLIEMS
	public String TOOLITEM_NEW_RECORD;
	public String TOOLITEM_SEARCH_RECORD_FOR_PROCESSING;
	public String TOOLITEM_EDIT_RECORD;
	public String TOOLITEM_MANEGE_COPIES;
	public String TOOLITEM_DELETE_RECORD;
	public String TOOLITEM_SEARCH_RECORDS;

	// CONTRIBUTOR_NAME
	public String CONTRIBUTOR_ATTACH_DIGITAL_FILE;
	public String CONTRIBUTOR_ASSOCIATE_THESAURUS_TERMS;
	public String CONTRIBUTOR_CONSULT_AUTHORITIES;
	public String CONTRIBUTOR_CONSULT_MATERIALS_CATALOGER;
	public String CONTRIBUTOR_CONSULT_SUBSCRIPTION;
	public String CONTRIBUTOR_NEW_SUBSCRIPTION;
	public String CONTRIBUTOR_NEW_SUBSCRIPTION1;
	public String CONTRIBUTOR_NEW_SUBSCRIPTION2;
	public String CONTRIBUTOR_PREDICTION_PATTERN;
	public String CONTRIBUTOR_REGISTER_BIBLIOGRAPHIC_RECORD;

	private AbosMessages() {
		// Prevent instantiation
	}

	public static AbosMessages get() {
		return RWT.NLS.getISO8859_1Encoded(AbosMessageConstants.ABOS_MESSAGES_TEMPLATE_COMPONENT_CLASS_NAME, AbosMessages.class);
	}
}
