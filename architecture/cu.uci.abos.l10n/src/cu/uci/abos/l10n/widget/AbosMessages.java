package cu.uci.abos.l10n.widget;

import org.eclipse.rap.rwt.RWT;

import cu.uci.abos.l10n.AbosMessageConstants;

public class AbosMessages {

	public String BUTTON_DETAILS;
	public String BUTTON_EDIT;
	public String BUTTON_REMOVED;

	private AbosMessages() {
		// Prevent instantiation
	}

	public static AbosMessages get() {
		return RWT.NLS.getISO8859_1Encoded(AbosMessageConstants.ABOS_MESSAGES_WIDGET_CLASS_NAME, AbosMessages.class);
	}
}
