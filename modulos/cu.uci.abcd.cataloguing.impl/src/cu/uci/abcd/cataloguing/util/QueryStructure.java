package cu.uci.abcd.cataloguing.util;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

public class QueryStructure {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private Text titleText;
	private Text authorText;
	private Text editorialText;
	private Text publicationYearText;
	private Combo recordTypeCombo;
	private Combo recordStatusCombo;

	public Text getTitleText() {
		return titleText;
	}
	public void setTitleText(Text titleText) {
		this.titleText = titleText;
	}
	public Text getAuthorText() {
		return authorText;
	}
	public void setAuthorText(Text authorText) {
		this.authorText = authorText;
	}
	public Text getEditorialText() {
		return editorialText;
	}
	public void setEditorialText(Text editorialText) {
		this.editorialText = editorialText;
	}
	public Text getPublicationYearText() {
		return publicationYearText;
	}
	public void setPublicationYearText(Text publicationYearText) {
		this.publicationYearText = publicationYearText;
	}
	public Combo getRecordTypeCombo() {
		return recordTypeCombo;
	}
	public void setRecordTypeCombo(Combo recordTypeCombo) {
		this.recordTypeCombo = recordTypeCombo;
	}
	public Combo getRecordStatusCombo() {
		return recordStatusCombo;
	}
	public void setRecordStatusCombo(Combo recordStatusCombo) {
		this.recordStatusCombo = recordStatusCombo;
	}
}
