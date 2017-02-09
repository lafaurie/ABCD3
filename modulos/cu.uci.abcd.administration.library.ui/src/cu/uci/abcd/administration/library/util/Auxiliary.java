package cu.uci.abcd.administration.library.util;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;

public class Auxiliary {

	public static String doubleValue(Text text, Double value) {
		String out;
		if (value != null) {
			String[] nums = value.toString().split("\\.");
			if (nums[1].equals("0")) {
				out = nums[0];
			} else {
				out = value.toString();
			}
		} else {
			out = "";
		}
		return out;
	}

	public static void showLabelAndTable(Label label, CRUDTreeTable table,
			boolean visible) {
		label.setVisible(visible);
		table.setVisible(visible);
	}

	public static void ajustRezise(Composite parent, int height) {
		parent.setSize(parent.getSize().x, height);
		FormData tempo = (FormData) parent.getLayoutData();
		tempo.height = height;
		parent.setLayoutData(tempo);

		parent.setLayoutData(tempo);
		parent.layout(true, true);
		parent.redraw();
		parent.update();
	}

	public static void refreshPage(Composite parent) {
		int height = 0;
		for (int i = 0; i < parent.getChildren().length; i++) {
			if (parent.getChildren()[i] instanceof Composite) {
				height = height + parent.getChildren()[i].getSize().y;
			}
		}
		parent.setSize(parent.getSize().x, height);
		FormData tempo = (FormData) parent.getLayoutData();
		tempo.height = parent.getSize().y;
		parent.setLayoutData(tempo);
		parent.layout(true, true);
		parent.redraw();
		parent.update();
	}

	@SuppressWarnings("deprecation")
	public static Date getDate(DateTime dateTime) {
		int fromYear = dateTime.getYear() - 1900;
		int fromMonth = dateTime.getMonth();
		int fromDay = dateTime.getDay();
		return new Date(fromYear, fromMonth, fromDay);
	}

	public static Date getDateToday() {
		java.util.Date utilDate = new java.util.Date();
		long lnMilisegundos = utilDate.getTime();
		return new java.sql.Date(lnMilisegundos);
	}

	@SuppressWarnings("deprecation")
	public static String FormatHour(Timestamp timestamp) {
		int hour = Integer.parseInt(timestamp.toString().substring(11, 13));
		String minutes = timestamp.toString().substring(14, 16);
		int hours = (hour > 12) ? hour - 12 : hour;
		return hours + ":" + minutes + " "
				+ getAMPM(timestamp.getHours(), minutes);
	}

	public static String getAMPM(int hour, String minutes) {
		return (hour < 12) ? "AM" : "PM";
	}

	public static void refresh(Composite parent) {
		parent.layout(true, true);
		parent.redraw();
		parent.update();
	}

	public static void goDateTimeToBeforeOneMonth(DateTime dataTime) {
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
		java.util.Date d = new java.util.Date();
		Date a = new Date(d.getTime());
		Date b = new Date(a.getTime());
		Date c = new Date(b.getTime());

		int fromYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(c));
		int fromMonth = Integer.parseInt(new SimpleDateFormat("MM").format(c));
		int fromDay = Integer.parseInt(new SimpleDateFormat("dd").format(c));

		dataTime.setDate(fromYear, fromMonth - 1, fromDay);
	}

	public static boolean finishDate_MoreThanInitDate_And_LessOrEqualThanToday(
			DateTime fromDateTime, DateTime toDateTime) {
		Date fromDate = Auxiliary.getDate(fromDateTime);
		Date toDate = Auxiliary.getDate(toDateTime);
		Date today = Auxiliary.getDateToday();

		if (toDate.before(today)) {
			toDateTime.setBackground(null);
			fromDateTime.setBackground(null);
		} else {
			RetroalimentationUtils.showErrorShellMessage(MessageUtil
					.unescape(AbosMessages.get().START_DATE_LESS_CURRENT_DATE));
			toDateTime.setBackground(new Color(toDateTime.getDisplay(), 255,
					204, 153));
			return false;
		}
		if (fromDate.before(toDate) || fromDate.equals(toDate)) {
			toDateTime.setBackground(null);
			fromDateTime.setBackground(null);
		} else {
			RetroalimentationUtils.showErrorShellMessage(MessageUtil
					.unescape(AbosMessages.get().END_DATE_MORE_START_DATE));
			fromDateTime.setBackground(new Color(toDateTime.getDisplay(), 255,
					204, 153));
			toDateTime.setBackground(new Color(toDateTime.getDisplay(), 255,
					204, 153));
			return false;
		}

		return true;
	}

	public static boolean dateLessOrEqualToday(DateTime toDateTime, String msg) {
		Date toDate = Auxiliary.getDate(toDateTime);
		Date today = Auxiliary.getDateToday();

		if (toDate.before(today)) {
			toDateTime.setBackground(null);
		} else {
			RetroalimentationUtils.showErrorShellMessage(msg);
			toDateTime.setBackground(new Color(toDateTime.getDisplay(), 255,
					204, 153));
			return false;
		}
		return true;
	}

	public static boolean initDateLessOrEqualThanFinishDate(
			DateTime fromDateTime, DateTime toDateTime) {
		Date fromDate = Auxiliary.getDate(fromDateTime);
		Date toDate = Auxiliary.getDate(toDateTime);

		if (fromDate.before(toDate) || fromDate.equals(toDate)) {
			toDateTime.setBackground(null);
			fromDateTime.setBackground(null);
		} else {
			RetroalimentationUtils
					.showErrorShellMessage(MessageUtil.unescape(AbosMessages
							.get().END_DATE_MORE_EQUAL_START_DATE));
			toDateTime.setBackground(new Color(toDateTime.getDisplay(), 255,
					204, 153));
			return false;
		}

		return true;
	}

	public static String getValue(String text) {
		int c = 0;
		int length = text.length();
		while (text.indexOf(" ") > -1) {
			text = text.substring(text.indexOf(" ") + " ".length(),
					text.length());
			c++;
		}
		return (c == length) ? null : text;
	}

	public static boolean isNumber(Text control) {
		String text = control.getText();
		boolean msg = true;
		if (!text.isEmpty()) {
			boolean init = Character.isDigit(text.charAt(0))
					&& Integer.parseInt(String.valueOf(text.charAt(0))) != 0;
			if (init) {
				for (int i = 1; i < text.length(); i++) {
					char c = text.charAt(i);
					if (!Character.isDigit(c)) {
						msg = false;
						break;
					}
				}
			} else {
				msg = false;
			}
		}
		return msg;

	}

	public static String ConcatLenght(String msg, String text, int maxLenght) {
		if (text.length() > maxLenght) {
			String concat = (msg != "") ? "\n" : "";
			msg = msg + concat + "El máximo de caracteres permitidos es "
					+ maxLenght + ".";
		}
		return msg;
	}
}
