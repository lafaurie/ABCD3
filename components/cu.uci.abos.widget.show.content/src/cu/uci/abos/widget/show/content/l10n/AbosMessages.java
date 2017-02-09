package cu.uci.abos.widget.show.content.l10n;

import org.eclipse.rap.rwt.RWT;

public class AbosMessages {

	public String LABEL_PUBLICATION_PLACE;
	public String LABEL_PUBLICATION_DATE;
	public String LABEL_RESULT_COMPONENT_LOCATION;
	public String LABEL_ACTIONS;
	public String BUTTON_SAVE_TO_LIST;
	public String BUTTON_ADD_TO_SELECTION;
	public String BUTTON_RATE;
	
	public String COMBO_FIRST_FILTER_TITLE;
	public String COMBO_FIRST_FILTER_AUTHOR;
		
			
	private AbosMessages() {
		// Prevent instantiation
	}

	public static AbosMessages get() {
		return RWT.NLS.getISO8859_1Encoded("cu.uci.abos.widget.show.content.l10n.AbosMessages", AbosMessages.class);
	}
}