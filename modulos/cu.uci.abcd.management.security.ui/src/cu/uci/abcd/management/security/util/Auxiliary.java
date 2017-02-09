package cu.uci.abcd.management.security.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;

public class Auxiliary {

	public static void showLabelAndTable(Label label, CRUDTreeTable table,
			boolean visible) {
		label.setVisible(visible);
		table.setVisible(visible);
	}

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
	
	public static boolean finishDate_MoreThanInitDate_And_LessOrEqualThanToday(DateTime fromDateTime, DateTime toDateTime) {
		Date fromDate = Auxiliary.getDate(fromDateTime);
		Date toDate = Auxiliary.getDate(toDateTime);
		Date today = Auxiliary.getDateToday();

		if (toDate.before(today)) { 
			toDateTime.setBackground(null);
			fromDateTime.setBackground(null);
		} else {
			RetroalimentationUtils
					.showErrorShellMessage(
							//shell,
							MessageUtil
									.unescape(AbosMessages.get().START_DATE_LESS_CURRENT_DATE));
			toDateTime.setBackground(new Color(toDateTime.getDisplay(), 255,
					204, 153));
			return false;
		}

		
		if (fromDate.before(toDate) || fromDate.equals(toDate)) {											
			toDateTime.setBackground(null);
			fromDateTime.setBackground(null);
		} else {
			RetroalimentationUtils
					.showErrorShellMessage(
							//shell,
							MessageUtil
									.unescape(AbosMessages.get().END_DATE_MORE_START_DATE));
			fromDateTime.setBackground(new Color(toDateTime.getDisplay(), 255,
					204, 153));
			toDateTime.setBackground(new Color(toDateTime.getDisplay(), 255,
					204, 153));
			return false;
		}
		
		
		return true;
	}
	
	public static int getAge(Date dateOfBirth) {

		Calendar today = Calendar.getInstance();
		Calendar birthDate = Calendar.getInstance();

		int age = 0;

		birthDate.setTime(dateOfBirth);
		if (birthDate.after(today)) {
			throw new IllegalArgumentException("Can't be born in the future");
		}

		age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

		// If birth date is greater than todays date (after 2 days adjustment of
		// leap year) then decrement age one year
		if ((birthDate.get(Calendar.DAY_OF_YEAR)
				- today.get(Calendar.DAY_OF_YEAR) > 3)
				|| (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH))) {
			age--;

			// If birth date and todays date are of same month and birth day of
			// month is greater than todays day of month then decrement age
		} else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH))
				&& (birthDate.get(Calendar.DAY_OF_MONTH) > today
						.get(Calendar.DAY_OF_MONTH))) {
			age--;
		}

		return age;
	}

	public static String FormatDate(Date date) {
		return new SimpleDateFormat("dd-MM-yyyy").format(date);

	}

	/*
	 * public static void refreshPage(Composite parent) { int height = 0; for
	 * (int i = 0; i < parent.getChildren().length; i++) { if(
	 * parent.getChildren()[i] instanceof Composite ){ height = height +
	 * parent.getChildren()[i].getSize().y; } }
	 * parent.setSize(parent.getSize().x, height); FormData tempo = (FormData)
	 * parent.getLayoutData(); tempo.height = parent.getSize().y;
	 * parent.setLayoutData(tempo); parent.layout(true, true); parent.redraw();
	 * parent.update(); }
	 */

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
	
	public static void refresh(Composite parent) {
		parent.layout(true, true);
		parent.redraw();
		parent.update();
	}
	
	
	public static String getValue(String text){
		int c = 0;
		int length = text.length();
	    while (text.indexOf(" ") > -1) {
	    	text = text.substring(text.indexOf(
	    			" ")+" ".length(),text.length());
	          c++; 
	    }
	    return (c==length)?null:text;
	}
	
}
