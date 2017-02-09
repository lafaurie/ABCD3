package cu.uci.abos.widget.advanced.query.domain;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

public class QueryStructure {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private Combo clave;
	private Text term;
	private Combo andOr;

	public QueryStructure(Combo clave, Text term) {

		this.clave = clave;
		this.term = term;
		this.andOr = null;

	}

	public Combo getClave() {
		return clave;
	}

	public Text getTerm() {
		return term;
	}

	public Combo getAndOr() {
		return andOr;
	}

	public void setAndOr(Combo andOrd) {
		this.andOr = andOrd;
	}

}
