package cu.uci.abos.validation.ui;

import java.util.GregorianCalendar;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;

public class ValidationUtil {
	/**
	 * Recive un arreglo de checksboxes y calcula la cantidad de checkboxes que
	 * hay seleccionados.
	 * 
	 * @param checkboxes
	 *            - arreglo de checkboxes.
	 */
	public static int CountSelectedComboboxes(Button[] checkboxes) {
		int cont = 0;
		for (int i = 0; i < checkboxes.length; i++) {
			if (checkboxes[i].getSelection()) {
				cont++;
			}
		}
		return cont;
	}

	/**
	 * Recive dos DateTime y retorna 1 si to es mayor que from, -1 si to es
	 * menos que from y 0 si ambas fechas son iguales.
	 * 
	 * @param from
	 *            - fecha inicio.
	 * @param to
	 *            - fecha fin.
	 */
	public static int ValidateTwoDateTime(DateTime from, DateTime to) {
		GregorianCalendar gcFrom = ValidationUtil.ConvertoDataTimeToGregorianCalendar(from);
		GregorianCalendar gcTo = ValidationUtil.ConvertoDataTimeToGregorianCalendar(to);

		if (gcTo.after(gcFrom)) {
			return 1;
		} else {
			if (gcTo.equals(gcFrom)) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	/**
	 * Recive un DateTime y lo convierte en formato GregorianCalendar.
	 * 
	 * @param dateTime
	 *            - fecha en formato dateTime.
	 */
	private static GregorianCalendar ConvertoDataTimeToGregorianCalendar(DateTime dateTime) {
		int year = dateTime.getYear();
		int month = dateTime.getMonth();
		int day = dateTime.getDay();
		return new GregorianCalendar(year, month, day);
	}
}
