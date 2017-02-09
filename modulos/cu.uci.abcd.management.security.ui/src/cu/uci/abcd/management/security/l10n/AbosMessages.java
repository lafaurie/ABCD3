package cu.uci.abcd.management.security.l10n;

import org.eclipse.rap.rwt.RWT;

public class AbosMessages {
	
	/*
	 * Buttons
	 */
	public String BUTTON_NEW;
	public String BUTTON_ACEPT;
	public String BUTTON_CLOSE;
	public String BUTTON_CANCEL;
	public String BUTTON_ADD;
	public String BUTTON_REGISTER;
	public String BUTTON_SAVE;
	public String BUTTON_EXPORT_TO_EXCEL;
	public String BUTTON_EXPORT_TO_PDF;
	public String BUTTON_CONSULT;
	public String BUTTON_NEW_SEARCH;
	public String BUTTON_SEARCH;
	public String BUTTON_ASSOCIATE;
	public String BUTTON_UNASSOCIATE;
	public String BUTTON_CHECK;
	public String RESET_PASSWORD;

	/*
	 * Labels
	 */
	public String SEARCH_CRITERIA;
	public String LIST_OF_MATCHES;
	public String CONSULT_USER;
	public String FIRST_NAME;
	public String SECOND_NAME;
	public String FIRST_SURNAME;
	public String SECOND_SURNAME;
	public String IDENTIFICATION;
	public String DESTIN;
	public String FROM;
	public String TO;
	public String RESET;
	public String REGISTER_DATE;
	public String ACCESS_LIST;
	public String NAME_AND_SURNAME;
	public String HOUR;
	public String ACTIVE_SESSIONS_LIST;
	public String ACTIVE_SESSIONS_CONSULT;
	public String PERSON_CONSULT;
	public String PERSON_LIST;
	public String AGE;
	public String SEX;
	public String PROFILE_USER_CONSULT;
	public String PROFILE_NAME;
	public String PROFILE_LIST;
	public String CONSULT_PROFILES;
	public String GESTION_SYSTEM;
	public String OPAC;
	public String USER_LIST;
	public String ACCESS;
	public String UPDATE_ACCESS;
	public String PERSON;
	public String PROFILE;
	public String USER;
	public String AUTHENTICATE;
	public String NAME;
	public String BIRTHDAY;
	public String ASSOCIATE_PERSON;
	public String ACCESS_DATA;
	public String ROOM;
	public String REASON;
	public String FILTER_PERMISSIONS;
	public String MODULE;
	public String FILTER;
	public String PERMISSIONS_LIST;
	public String PERMISSIONS_NAME;
	public String GENERAL_DATA;
	public String EMAIL;
	public String ADDRESS;
	public String USER_DATA;
	public String PASSWORD;
	public String CONFIRM_PASSWORD;
	public String PERMISSIONS;
	public String ASIGNED_PERMISSIONS;
	public String SHOULD_SELECT_PERSON;
	public String SHOULD_SELECT_PERMISSION;
	public String SHOULD_SELECT_PROFILE;
	public String NOT_EQUAL_PASSWORD;
	public String OPAC_USER_CAN_NOT_MODIFICATED;
	public String INCORRECT_PASSWORD;
	public String CHANGE_PASSWORD;
	public String LAST_PASSWORD;
	public String MY_PROFILE;
	public String ID_CARNE;
	public String DATE;
	public String FULL_NAME;
	public String PERFIL_NAME;
	public String CREATION_DATE;
	public String ONLY_LETTERS_NUMBERS;
	public String LAST_NAME;

	public String VIEW_PERSON;
	public String REGISTER_PERSON;
	public String REGISTER_USER;
	public String REGISTER_PROFILE;
	public String UPDATE_PROFILE;
	public String REGISTER_ACCESS;
	public String LABEL_PERSON_DATA;
	public String LABEL_ALL;
	public String CONSULT_PROFILE;
	public String VIEW_USER;
	public String LABEL_USER_DATA;
	public String PROFILE_SAME_PERMISSION;
	
	public String LABEL_REGISTER_LDAP;
	public String LABEL_UPDATE_LDAP;
	public String LABEL_IP_DIRECTION;
	public String LABEL_PORT;
	public String LABEL_USER_TYPE;
	public String MESSAGE_LDAP;
	public String LABEL_ALL_PERMISSIONS;
	public String LABEL_EXAMINE;
	public String MESSAGE_NO_PICTURE;
	public String MESSAGE_FILE_PICTURE;
	public String LABEL_SYSTEM;
	
	public String VIEW_PROFILE;
	public String LABEL_PROFILE_DATA;
	
	public String VIEW_ACCESS_RECORD;
	public String LABEL_ACCESS_RECORD_DATA;
	
	public String LABEL_NAME_AND_LASTNAME;
	public String LABEL_IDENTIFICATION;
	public String LABEL_SEX;
	public String LABEL_NAME;
	public String LABEL_LAST_NAME;
	public String LABEL_OPEN_FILE;
	public String REMEMBER;
	public String MESSAGE_SELECT;
	
	public String DOMAIN;
	public String LOCAL;
	public String SHOULD_INDICATE_A_PERSON_AS_USER;
	/*
	 * contributorName
	 */
	public String ACCESS_CONSULT;
	
	public String USER_OR_PASSWORD_INCORRECT;
	public String USER_CORRECT;
	public String USER_INCORRECT;
	public String USER_NOT_FOUND;
	public String USER_NOT_CHECKED;
	public String SHOULD_ESPECIFICAD_USER_NAME;
	
	public String USER_AVAILABLE;
	public String USER_NOT_EXIST_IN_DOMAIN;
	public String USER_NOT_AVAILABLE;
	
	public String ENTERED_USER_IS_NOT_CORRECT;
	public String PASSWORD_NOT_MATCH;
	
	public String USER_ALREADY_EXIST;
	public String LABEL_STATE;
	public String LABEL_STATE_ACTIVE;
	public String LABEL_STATE_INACTIVE;
	
	public String ELEMENT_EXIST;
	
	public String ALERT_PERSON_IS_USER;
	
	public String START_DATE_LESS_CURRENT_DATE;
	public String END_DATE_MORE_START_DATE;
	public String END_DATE_MORE_EQUAL_START_DATE;
	
	public String UPDATE_USER;
	public String THERE_IS_NO_DATA;
	public String RESET_PASSWORD_OK;
	
	public String ABOUT_US;
	public String MANUALS;
	
	public String ABCD_VERSION;
	public String PROJECT_COPERATION;
	
	public String ADQUISITION_MODULE;
	public String CATALOGUING_MODULE;
	public String CIRCULATION_MODULE;
	public String LIBRARY_ADMINISTRATION_MODULE;
	public String SECURITY_ADMINISTRATION_MODULE;
	public String NOMENCLATOR_ADMINISTRATION_MODULE;
	public String STADISTIC_MODULE;
	public String OPAC_MODULE;
	
	public String UCI;
	public String UCLV;
	public String UPR;
	public String UO;
	public String HOL;
	public String CMG;
	
	public String LANGUAGE;
	public String NOT_EXIST_FILE;
	
	public String CONSULT_USERS;
	
	private AbosMessages() {
		// Prevent instantiation
	}

	public static AbosMessages get() {
		return RWT.NLS
				.getISO8859_1Encoded(
						"cu.uci.abcd.management.security.l10n.AbosMessages",
						AbosMessages.class);
	}
}
