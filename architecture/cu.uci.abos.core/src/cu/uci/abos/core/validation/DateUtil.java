package cu.uci.abos.core.validation;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtil {

	public static Calendar DateAnyYearsBefore(int years, int months, int days) {
		Calendar c1 = GregorianCalendar.getInstance();
		c1.add(Calendar.DATE, days);
		c1.add(Calendar.MONTH, months);
        c1.add(Calendar.YEAR, years);
		return c1;
	}
	
}
