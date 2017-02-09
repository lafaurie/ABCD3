package cu.uci.abcd.common.l10n;

import org.eclipse.rap.rwt.RWT;

public class AbosMessages {

	public String NAME;
	public String SURNAME;
	public String IDENTIFICATION;
	public String LABEL_NAME_AND_LASTNAME;
	public String LABEL_SEX;
	public String BUTTON_DISSOCIATE;
	public String BUTTON_SEARCH;
	public String LABEL_PERSON_DATA;
	public String LABEL_USER;
	public String LABEL_BIRTHDATE;

	private AbosMessages() {

	}

	public static AbosMessages get() {
		return RWT.NLS.getISO8859_1Encoded("cu.uci.abcd.common.l10n.AbosMessages", AbosMessages.class);
	}
}
