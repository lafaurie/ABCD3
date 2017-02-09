package cu.uci.abos.core.validation;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;

import cu.uci.abos.core.l10n.AbosMessages;
import cu.uci.abos.core.util.MessageUtil;

public class CustomValidation {

	public static void ValidateFieldRequired(ControlDecoration controlDecoration) {
		controlDecoration.hide();
		int length = 0;
		Control control = controlDecoration.getControl();
		int contSpace = 0;
		if (control.getClass().equals(Text.class)) {
			length = ((Text) control).getText().length();

			for (int i = 0; i < length; i++) {
				if (((Text) control).getText().charAt(i) == ' ') {
					contSpace = contSpace + 1;
					;
				}
			}
		} else {
			if (control.getClass().equals(Combo.class)) {
				length = ((Combo) control).getText().length();
				if (((Combo) control).getText().equalsIgnoreCase(AbosMessages.get().COMBO_SELECT)) {
					length = 0;
				}
			}
		}
		if (length == 0 || contSpace == length) {
			controlDecoration.show();
		} else {
			controlDecoration.hide();
		}
	}

	public static void ValidateFieldAlphaNumeric(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";
		if (!text.isEmpty()) {
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				if (!Character.isLetterOrDigit(c)) {
					msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_ONLY_LETTERS_OR_NUMBERS);
					break;
				}
			}
			msg = ConcatLenght(msg, text, maxLenght);
		}
		ShowControlDecoration(controlDecoration, msg);
	}

	public static void ValidateFieldAlpha(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";
		if (!text.isEmpty()) {
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				if (!Character.isLetter(c)) {
					msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_ALPHA);
					break;
				}
			}
			msg = ConcatLenght(msg, text, maxLenght);
		}
		ShowControlDecoration(controlDecoration, msg);
	}

	public static void ValidateLengthField(ControlDecoration controlDecoration, int maxLength) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";
		if (text.length() > maxLength) {
			msg = ConcatLenght(msg, text, maxLength);
		}
		ShowControlDecoration(controlDecoration, msg);
	}

	public static void ValidateFieldDateRange(ControlDecoration controlDecoration, int beforeYears, int beforeMonths, int beforeDays, int afterYears, int afterMonths, int afterDays) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		DateTime dateTime = ((DateTime) control);
		Date selectedDate = new Date(new GregorianCalendar(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay()).getTime().getTime());

		Date referenceFromDay = new Date(DateUtil.DateAnyYearsBefore(beforeYears, beforeMonths, beforeDays).getTime().getTime());
		Date referenceToDay = new Date(DateUtil.DateAnyYearsBefore(afterYears, afterMonths, afterDays).getTime().getTime());

		if ((selectedDate.compareTo(referenceToDay) < 0 || selectedDate.toString().equals(referenceToDay.toString())) && (selectedDate.compareTo(referenceFromDay) > 0 || selectedDate.toString().equals(referenceFromDay.toString()))) {
			controlDecoration.hide();
		} else {
			controlDecoration.show();
		}
	}

	public static void ValidateFieldDateAnyYearsBackward(ControlDecoration controlDecoration, int years, int months, int days) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		DateTime dateTime = ((DateTime) control);
		Date selectedDate = new Date(new GregorianCalendar(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay()).getTime().getTime());
		Date referenceDay = new Date(DateUtil.DateAnyYearsBefore(years, months, days).getTime().getTime());
		if (selectedDate.compareTo(referenceDay) < 0 || selectedDate.toString().equals(referenceDay.toString())) {
			controlDecoration.hide();
		} else {
			controlDecoration.show();
		}
	}

	public static void ValidateFieldDateAnyYearsForward(ControlDecoration controlDecoration, int years, int months, int days) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		DateTime dateTime = ((DateTime) control);
		Date selectedDate = new Date(new GregorianCalendar(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay()).getTime().getTime());
		Date referenceDay = new Date(DateUtil.DateAnyYearsBefore(years, months, days).getTime().getTime());
		if (selectedDate.compareTo(referenceDay) > 0 || selectedDate.toString().equals(referenceDay.toString())) {
			controlDecoration.hide();
		} else {
			controlDecoration.show();
		}
	}

	public static void ValidateFieldPort(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";
		if (!text.isEmpty() && !isEmpty(((Text) control))) {
			boolean init = Character.isDigit(text.charAt(0)) && Integer.parseInt(String.valueOf(text.charAt(0))) != 0;
			if (init) {
				for (int i = 1; i < text.length(); i++) {
					char c = text.charAt(i);
					if (!Character.isDigit(c)) {
						msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_PORT);
						break;
					}
				}
			} else {
				msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_PORT);
			}
			msg = ConcatLenght(msg, text, maxLenght);
		}
		ShowControlDecoration(controlDecoration, msg);
	}

	public static void ValidateFieldOnlyNumbersNotStartWithZero(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";
		if (!text.isEmpty()) {
			boolean init = Character.isDigit(text.charAt(0)) && Integer.parseInt(String.valueOf(text.charAt(0))) != 0;
			if (init) {
				for (int i = 1; i < text.length(); i++) {
					char c = text.charAt(i);
					if (!Character.isDigit(c)) {
						msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_NUMBER_ONLY_NOT_START_WITH_CERO);
						break;
					}
				}
			} else {
				msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_NUMBER_ONLY_NOT_START_WITH_CERO);
			}
			msg = ConcatLenght(msg, text, maxLenght);
		}
		ShowControlDecoration(controlDecoration, msg);
	}

	public static void ValidateFieldOnlyNumbers(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";
		if (!text.isEmpty()) {
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				if (!Character.isDigit(c)) {
					msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_ONLY_NUMBERS);
					break;
				}
			}
			msg = ConcatLenght(msg, text, maxLenght);
		}
		ShowControlDecoration(controlDecoration, msg);
	}

	public static void ValidateFieldOnlyNumbersAndPoints(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";
		if (!text.isEmpty()) {
			final String PATTERN_EMAIL = "[1-9]*|([1-9][0-9]*\\.[0-9]*)*";
			Pattern pattern = Pattern.compile(PATTERN_EMAIL);
			Matcher matcher = pattern.matcher(text);
			if (!matcher.matches() || (matcher.matches() && !Character.isDigit(text.charAt(text.length() - 1)))) {
				msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_ONLY_NUMBERS_POINT);
			}
			msg = ConcatLenght(msg, text, maxLenght);
		}
		ShowControlDecoration(controlDecoration, msg);
	}

	public static void ValidateFieldDomain(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";

		if (!text.isEmpty() && !isEmpty(((Text) control))) {
			final String PATTERN_EMAIL = "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$";
			Pattern pattern = Pattern.compile(PATTERN_EMAIL);
			Matcher matcher = pattern.matcher(text);

			boolean mal = false;
			// boolean mal1= false;

			// if(!Character.isLetterOrDigit(text.charAt(0))){
			// mal1 = true;
			// }

			for (int i = 0; i < text.length(); i++) {
				Character c = text.charAt(i);
				if (!Character.isLetterOrDigit(c) && !Character.isSpaceChar(c) && i > 0 && !(Character.isLetterOrDigit(text.charAt(i - 1)) || Character.isSpaceChar(text.charAt(i - 1))) // &&
																																															// c.equals(text.charAt(i
																																															// -
																																															// 1))
				) {
					mal = true;
					break;
				}
			}

			if (!matcher.matches() || mal) {
				msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_DOMAIN);
			}
			msg = ConcatLenght(msg, text, maxLenght);
		}

		/*
		 * if (!text.isEmpty() && !isEmpty(((Text) control))) { final String
		 * DOMAIN_PATTERN =
		 * "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$"; Pattern pattern
		 * = Pattern.compile(DOMAIN_PATTERN); Matcher matcher =
		 * pattern.matcher(text); if (!matcher.matches()) { msg = msg +
		 * MessageUtil .unescape(AbosMessages.get().MESSAGE_VALIDATE_DOMAIN); }
		 * msg = ConcatLenght(msg, text, maxLenght); }
		 */
		ShowControlDecoration(controlDecoration, msg);
	}

	public static void ValidateFieldAlphaNumericsSpaces(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String[] textArray = text.split(" ");
		String msg = "";
		boolean value = true;

		if (!text.isEmpty()) {

			final String PATTERN_ALPHA_NUMERICS_SPACES = "([a-zA-Zá-źÁ-Źü0-9]*)";
			Pattern pattern = Pattern.compile(PATTERN_ALPHA_NUMERICS_SPACES);

			for (int i = 0; i < textArray.length; i++) {
				Matcher matcher = pattern.matcher(textArray[i]);
				if (!matcher.matches()) {
					msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_ALPHA_NUMERICS_SPACES);
					value = false;
					break;
				}
			}

			if (value) {
				for (int i = 0; i < text.length(); i++) {
					char c = text.charAt(i);
					if (!(Character.isDigit(c) || Character.isLetter(c) || Character.isSpaceChar(c))) {
						msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_ALPHA_NUMERICS_SPACES);
						break;
					}
				}
			}

			msg = ConcatLenght(msg, text, maxLenght);

		}
		ShowControlDecoration(controlDecoration, msg);
	}

	public static void ValidateFieldAlphaSpaces(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";
		if (!text.isEmpty()) {
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				if (!(Character.isLetter(c) || Character.isWhitespace(c))) {
					msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_ALPHA_SPACES);
					break;
				}
			}
			msg = ConcatLenght(msg, text, maxLenght);
		}
		ShowControlDecoration(controlDecoration, msg);
	}

	public static void ValidateFieldAlphaSpacesGuion(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";
		if (!text.isEmpty()) {
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				if (!(Character.isLetter(c) || Character.isWhitespace(c) || c == '-')) {
					msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_ALPHA_SPACES_GUION);
					break;
				}
			}
			if (msg == "") {
				if (text.charAt(0) == '-' || text.charAt(text.length() - 1) == '-') {
					msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_ALPHA_SPACES_GUION);
				} else {
					boolean flag = false;
					String pepe = text.replaceAll(" +", "").trim();
					for (int i = 0; i < pepe.length(); i++) {
						if (pepe.charAt(i) == '-' && (i + 1 < pepe.length() && pepe.charAt(i + 1) == '-')) {
							flag = true;
							break;
						}
					}

					if (flag) {
						msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_ALPHA_SPACES_GUION);
					}
					// if(Quantity(text, '-')+1 != QuantityWord(text)){
					// msg = msg
					// + MessageUtil
					// .unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_ALPHA_SPACES_GUION);
					// }
				}
			}

			msg = ConcatLenght(msg, text, maxLenght);
		}
		ShowControlDecoration(controlDecoration, msg);
	}

	public static void ValidateFieldDouble(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";
		if (!text.isEmpty()) {
			final String PATTERN_DOUBLE = "([1-9][0-9]*)|(0\\.([1-9][0-9]*))|(0\\.[1-9])|(0\\.([0-9][1-9]+))|(([1-9][0-9]*)\\.([0-9]|[0-9][0-9]))";
			Pattern pattern = Pattern.compile(PATTERN_DOUBLE);
			Matcher matcher = pattern.matcher(text);
			if (!matcher.matches()) {
				msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_DOUBLE);
			}
			msg = ConcatLenght(msg, text, maxLenght);
		}
		ShowControlDecoration(controlDecoration, msg);
	}

	public static void ValidateFieldEMail(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";
		if (!text.isEmpty()) {
			final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			Pattern pattern = Pattern.compile(PATTERN_EMAIL);
			Matcher matcher = pattern.matcher(text);

			boolean mal = false;
			boolean mal1 = false;
			if (!Character.isLetterOrDigit(text.charAt(0))) {
				mal1 = true;
			}

			for (int i = 0; i < text.length(); i++) {
				Character c = text.charAt(i);
				if (!Character.isLetterOrDigit(c) && !Character.isSpaceChar(c) && i > 0 && !(Character.isLetterOrDigit(text.charAt(i - 1)) || Character.isSpaceChar(text.charAt(i - 1))) // &&
																																															// c.equals(text.charAt(i
																																															// -
																																															// 1))
				) {
					mal = true;
					break;
				}
			}

			if (!matcher.matches() || mal || mal1) {
				msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_EMAIL);
			}
			msg = ConcatLenght(msg, text, maxLenght);
		}
		ShowControlDecoration(controlDecoration, msg);
	}

	public static void ValidateFieldUrl(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";
		if (!text.isEmpty()) {
			final String PATTERN_URL = "^(http://|https://)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
			Pattern pattern = Pattern.compile(PATTERN_URL);
			Matcher matcher = pattern.matcher(text);
			if (!matcher.matches()) {
				msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_URL);
			}
			msg = ConcatLenght(msg, text, maxLenght);
		}
		ShowControlDecoration(controlDecoration, msg);
	}

	public static void ValidateFieldGrupal(ControlDecoration controlDecoration1, ControlDecoration controlDecoration2) {
		controlDecoration1.hide();
		controlDecoration2.hide();
		Control control1 = controlDecoration1.getControl();
		Control control2 = controlDecoration2.getControl();

		String text1 = ((Text) control1).getText();
		String text2 = ((Text) control2).getText();

		if (text1.length() == 0 && text2.length() == 0) {
			controlDecoration1.show();
			controlDecoration2.show();
		} else {
			controlDecoration1.hide();
			controlDecoration2.hide();
		}
	}

	public static void ValidateFieldIp(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";
		if (!text.isEmpty() && !isEmpty(((Text) control))) {
			final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-3])$";
			Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
			Matcher matcher = pattern.matcher(text);
			String[] numbers = text.split("\\.");
			int errors = 0;
			for (int i = 0; i < numbers.length; i++) {
				char init = 'a';
				if (numbers[i].length() > 0) {
					init = numbers[i].charAt(0);
				}
				if (Character.isDigit(init) && Integer.parseInt(String.valueOf(numbers[i].charAt(0))) == 0 && numbers[i].length() > 1) {
					errors++;
				}
			}
			try {
				// int pp = Integer.parseInt(numbers[3]);
				if (numbers.length == 4 && Integer.parseInt(numbers[3]) == 0) {
					errors++;
				}
			} catch (Exception e) {
				errors++;
			}

			if (matcher.matches() && errors == 0 && Integer.parseInt(String.valueOf(text.charAt(0))) != 0) {
			} else {
				msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_IP);
			}
			msg = ConcatLenght(msg, text, maxLenght);
		}
		ShowControlDecoration(controlDecoration, msg);
	}

	public static void ValidateFieldNoOtherRepeatConsecutive(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";
		if (!text.isEmpty()) {
			for (int i = 0; i < text.length(); i++) {
				Character c = text.charAt(i);
				if (!Character.isLetterOrDigit(c) && !Character.isSpaceChar(c) && i > 0 && !(Character.isLetterOrDigit(text.charAt(i - 1)) || Character.isSpaceChar(text.charAt(i - 1))) // &&
																																															// c.equals(text.charAt(i
																																															// -
																																															// 1))
				) {
					msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_NO_ALPHANUMERIC_REPEAT_CONSECUTIVE);
					break;
				}
			}
			msg = ConcatLenght(msg, text, maxLenght);
		}
		ShowControlDecoration(controlDecoration, msg);
	}

	public static void ValidateFieldAlphaNumericAndUnderscore(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";
		if (!text.isEmpty()) {
			boolean init = Character.isLetterOrDigit(text.charAt(0));
			boolean end = Character.isLetterOrDigit(text.charAt(text.length() - 1));
			if (init && end) {
				controlDecoration.hide();
				for (int i = 1; i < text.length(); i++) {
					Character c = text.charAt(i);
					Character a = text.charAt(i - 1);
					if (init && (Character.isLetterOrDigit(c) || c.equals('_'))) {
						if (c.equals('_') && a.equals('_')) {
							msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_ALPHANUMERIC_AND_UNDERSCORE);
							break;
						}
					} else {
						msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_ALPHANUMERIC_AND_UNDERSCORE);
						break;
					}
				}
			} else {
				msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_ALPHANUMERIC_AND_UNDERSCORE);
			}
			msg = ConcatLenght(msg, text, maxLenght);
		}
		ShowControlDecoration(controlDecoration, msg);
	}

	public static String ConcatLenght(String msg, String text, int maxLenght) {
		if (text.length() > maxLenght) {
			String concat = (msg != "") ? "\n" : "";
			msg = msg + concat + "El máximo de caracteres permitidos es " + maxLenght + ".";
		}
		return msg;
	}

	public static boolean isEmpty(Text text) {
		int contSpace = 0;
		int length = text.getText().length();

		for (int i = 0; i < length; i++) {
			if (text.getText().charAt(i) == ' ') {
				contSpace = contSpace + 1;
				;
			}
		}
		return length == contSpace;
	}

	public static void ShowControlDecoration(ControlDecoration controlDecoration, String msg) {
		if (msg.length() > 0) {
			controlDecoration.setDescriptionText(msg);
			controlDecoration.show();
		} else {
			controlDecoration.hide();
		}
	}

	public static int Quantity(String text, char a) {
		int cont = 0;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == a) {
				cont = cont + 1;
				;
			}
		}
		return cont;
	}

	public static int QuantityWord(String text) {
		StringTokenizer st = new StringTokenizer(text);
		return st.countTokens();
	}

	public static void ValidateFieldUserNames(ControlDecoration controlDecoration, int maxLenght) {
		controlDecoration.hide();
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		String msg = "";
		String temTxt = "";

		if (!text.isEmpty()) {

			for (int i = 0; i < text.length(); i++) {

				if (!(text.substring(i, i + 1).equals(".")) & !(text.substring(i, i + 1).equals("+")) & !(text.substring(i, i + 1).equals("-")) & !(text.substring(i, i + 1).equals("@") & !(text.substring(i, i + 1).equals("_"))))
					temTxt += text.substring(i, i + 1);
			}

			text = temTxt;

			if (!text.isEmpty()) {

				final String PATTERN_ALPHA_NUMERICS_SPACES = "([a-zA-Z0-9]*)";
				Pattern pattern = Pattern.compile(PATTERN_ALPHA_NUMERICS_SPACES);

				Matcher matcher = pattern.matcher(text);
				if (!matcher.matches())
					msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_USER_NAME);
			} else
				msg = msg + MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_USER_NAME);

		}
		msg = ConcatLenght(msg, text, maxLenght);

		ShowControlDecoration(controlDecoration, msg);
	}
}
