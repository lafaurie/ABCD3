package cu.uci.abcd.domain.l10n;

import org.eclipse.rap.rwt.RWT;

public class AbosMessages {

	public String ACTIVE;
	public String INACTIVE;
	public String NOT_EXIST_ASSOCIATE_PERSON;
	
	public String MONTH_JANUARY;
	public String MONTH_FEBRUARY;
	public String MONTH_MARCH;
	public String MONTH_APRIL;
	public String MONTH_MAY;
	public String MONTH_JUNE;
	public String MONTH_JULY;
	public String MONTH_AUGUST;
	public String MONTH_SEPTEMBER;
	public String MONTH_OCTOBER;
	public String MONTH_NOVEMBER;
	public String MONTH_DICEMBER;

	private AbosMessages() {
		
	}

	public static AbosMessages get() {
		return RWT.NLS.getISO8859_1Encoded("cu.uci.abcd.domain.l10n.AbosMessages", 
				AbosMessages.class);
	}
}
