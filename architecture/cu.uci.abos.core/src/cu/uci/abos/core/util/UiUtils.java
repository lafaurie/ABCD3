package cu.uci.abos.core.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;

import cu.uci.abos.core.domain.Row;
import cu.uci.abos.core.l10n.AbosMessages;

public class UiUtils {

	private UiUtils() {
		super();
	}

	/**
	 * Set combo values for collection. Combo items, get values for toString()
	 * method in every items.
	 * 
	 * @param control
	 *            to set values.
	 * @param items
	 *            values to set.
	 */
	static public void initialize(Control control, Collection<?> items) {
		initialize(control, items, AbosMessages.get().COMBO_SELECT);
	}


	static public void initialize(Control control, Collection<?> items, String intValue) {
		
		if (control.getClass().isAssignableFrom(Combo.class)) {
			String[] values = new String[items.size() + 1];
			int i = 0;
			values[i++] = intValue;
			for (Object object : items) {
				values[i++] = object.toString();
			}
			((Combo) control).setItems(values);
			 control.setData(items);
			((Combo) control).select(0);

		}
	}

	/**
	 * Select the value in the combo items equals to correspond to parameter
	 * value.
	 * 
	 * @param combo
	 *            to select index.
	 * @param value
	 *            to compare.
	 */
	@SuppressWarnings("rawtypes")
	static public void selectValue(Combo combo, Row value) {
		for (int i = 1; i < combo.getItemCount(); i++) {
			if (((Row) ((Vector) combo.getData()).get(i-1)).getRowID().equals(value.getRowID())) {
				combo.select(i);
				return;
			}
		}
	}

	/**
	 * Return the selected value on combo or <code>null</code> is no one
	 * selected.
	 * 
	 * @param combo
	 *            to use for search.
	 * @return <code>null</code> if no valid value selected in combo. Otherwise
	 *         return the object correspond to selected value.
	 */
	static public Object getSelected(Combo combo) {
		if (combo.getSelectionIndex() > 0) {
			return ((List<?>) combo.getData()).get(combo.getSelectionIndex()-1);
		}
		return null;
	}

	static public UiUtils get() {
		return new UiUtils();
	}

	public UiUtils addSearchCriteria(final List<String> collection, String denomination, String value) {
		try {
			if (!denomination.isEmpty() && !value.isEmpty()) {
				collection.add(denomination);
				collection.add(value);
			}
		} catch (NullPointerException e) {
			// nothing to do
		}
		return this;
	}

	public UiUtils addSearchCriteria(final List<String> collection, String denomination, Combo value) {
		try {
			if (!denomination.isEmpty() && value.getSelectionIndex() > 0) {
				collection.add(denomination);
				collection.add(value.getText());
			}
		} catch (NullPointerException e) {
			// nothing to do
		}
		return this;
	}

	@SuppressWarnings("deprecation")
	public UiUtils addSearchCriteria(final List<String> collection, String denomination, DateTime dateTime) {
		try {
			if (!denomination.isEmpty()) {
				collection.add(denomination);
				collection.add(new SimpleDateFormat("dd-MM-yyyy").format(new Date(dateTime.getYear() - 1900, dateTime.getMonth(), dateTime.getDay())));
			}
		} catch (NullPointerException e) {
			// nothing to do
		}
		return this;
	}

}
