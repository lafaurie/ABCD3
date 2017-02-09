package cu.uci.abcd.acquisition.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.widgets.DateTime;

public class Auxiliary {

	public static boolean dateLessOrEqualToday(DateTime toDateTime) {
		Date toDate = Auxiliary.getDate(toDateTime);
		Date  today = Auxiliary.getDateToday();

		if (toDate.before(today)) {
			toDateTime.setBackground(null);
		} else {
			return false;
		}
		return true;
	}




	@SuppressWarnings("deprecation")
	public static Date getDate(DateTime dateTime){
		int fromYear = dateTime.getYear() - 1900;
		int fromMonth = dateTime.getMonth();
		int fromDay = dateTime.getDay();
		return new Date(fromYear, fromMonth, fromDay);
	}


	public static Date getDateToday(){
		java.util.Date utilDate = new java.util.Date(); //fecha actual
		long lnMilisegundos = utilDate.getTime();
		return new java.sql.Date(lnMilisegundos);
		//java.sql.Time sqlTime = new java.sql.Time(lnMilisegundos);
	}
	
	public static void goDateTimeToBeforeOneMonth(DateTime dataTime) {
		// arreglar, invento
		java.util.Date d = new java.util.Date();
		Date a = new Date(d.getTime() - 899999999);
		Date b = new Date(a.getTime() - 899999999);
		Date c = new Date(b.getTime() - 899999999);

		int fromYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(c));
		int fromMonth = Integer.parseInt(new SimpleDateFormat("MM").format(c));
		int fromDay = Integer.parseInt(new SimpleDateFormat("dd").format(c));

		dataTime.setDate(fromYear, fromMonth - 1, fromDay);
	}
	
	public static void goDateTimeToToday(DateTime dataTime) {
		// arreglar, invento
		java.util.Date d = new java.util.Date();
		Date a = new Date(d.getTime());
		Date b = new Date(a.getTime());
		Date c = new Date(b.getTime());

		int fromYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(c));
		int fromMonth = Integer.parseInt(new SimpleDateFormat("MM").format(c));
		int fromDay = Integer.parseInt(new SimpleDateFormat("dd").format(c));

		dataTime.setDate(fromYear, fromMonth - 1, fromDay);
	}


}
