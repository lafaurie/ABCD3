package cu.uci.abos.validation.ui;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;

public class CustomValidation {

	/**
	 * Recibe un controlDecoration y valida que el Control asociado a dicho
	 * controlDecoration es obligatorio(No puede ser vacio). Solo implementado
	 * para componente Text y Combobox.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al Control a validar.
	 */
	public static void ValidateFieldRequired(ControlDecoration controlDecoration) {
		int length = 0;
		Control control = controlDecoration.getControl();
		if (control instanceof Text) {
			length = ((Text) control).getText().length();

		} else {
			if (control instanceof Combo) {
				length = ((Combo) control).getText().length();
			}
		}
		if (length == 0) {
			control.setBackground(new Color(control.getDisplay(), 255, 204, 153));
			controlDecoration.show();
		} else {
			control.setBackground(null);
			controlDecoration.hide();
		}
	}

	/**
	 * Recibe un controlDecoration y valida que el Control asociado a dicho
	 * controlDecoration contiene solo letras y(o) numeros.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al Control a validar.
	 */
	public static void ValidateFieldAlphaNumeric(ControlDecoration controlDecoration) {
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		if (!text.isEmpty()) {
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				if (!Character.isLetterOrDigit(c)) {
					control.setBackground(new Color(control.getDisplay(), 255, 204, 153));
					controlDecoration.show();
					return;
				}
			}
			control.setBackground(null);
			controlDecoration.hide();
		} else {
			control.setBackground(null);
			controlDecoration.hide();
		}
	}

	/**
	 * Recibe un controlDecoration y valida que el Control asociado a dicho
	 * controlDecoration contiene solo numeros.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al Control a validar
	 */
	public static void ValidateFieldOnlyNumbers(ControlDecoration controlDecoration) {
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		if (!text.isEmpty()) {
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				if (!Character.isDigit(c)) {
					control.setBackground(new Color(control.getDisplay(), 255, 204, 153));
					controlDecoration.show();
					return;
				}
			}
			control.setBackground(null);
			controlDecoration.hide();
		} else {
			control.setBackground(null);
			controlDecoration.hide();
		}
	}

	/**
	 * Recibe un controlDecoration y valida que el Control asociado a dicho
	 * controlDecoration contiene solo letras.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al Control a validar.
	 */
	public static void ValidateFieldOnlyLetters(ControlDecoration controlDecoration) {
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		if (!text.isEmpty()) {
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				if (!Character.isLetter(c)) {
					control.setBackground(new Color(control.getDisplay(), 255, 204, 153));
					controlDecoration.show();
					return;
				}
			}
			control.setBackground(null);
			controlDecoration.hide();
		} else {
			control.setBackground(null);
			controlDecoration.hide();
		}
	}

	/**
	 * Recibe un controlDecoration y valida que el Control asociado a dicho
	 * controlDecoration contiene texto en formato numero double. Solo
	 * implementado para componente Text.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al Control a validar.
	 */
	public static void ValidateFieldDouble(ControlDecoration controlDecoration) {
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		if (!text.isEmpty()) {
			final String PATTERN_DOUBLE = "(0|([1-9][0-9]*)).[0-9][0-9]";
			Pattern pattern = Pattern.compile(PATTERN_DOUBLE);
			Matcher matcher = pattern.matcher(text);
			if (matcher.matches()) {
				control.setBackground(null);
				controlDecoration.hide();
			} else {
				if (!text.isEmpty()) {
					control.setBackground(new Color(control.getDisplay(), 255, 204, 153));
					controlDecoration.show();
				}
			}
		} else {
			control.setBackground(null);
			controlDecoration.hide();
		}
	}

	/**
	 * Recibe un controlDecoration y valida que el Control asociado a dicho
	 * controlDecoration contiene texto en formato de email. Solo implementado
	 * para componente Text.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al Control a validar.
	 */
	public static void ValidateFieldEMail(ControlDecoration controlDecoration) {
		Control control = controlDecoration.getControl();
		String text = ((Text) control).getText();
		if (!text.isEmpty()) {
			final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			Pattern pattern = Pattern.compile(PATTERN_EMAIL);
			Matcher matcher = pattern.matcher(text);
			if (matcher.matches()) {
				control.setBackground(null);
				controlDecoration.hide();
			} else {
				if (!text.isEmpty()) {
					control.setBackground(new Color(control.getDisplay(), 255, 204, 153));
					controlDecoration.show();
				}
			}
		} else {
			control.setBackground(null);
			controlDecoration.hide();
		}
	}

	/**
	 * Recibe un controlDecoration y valida que el Control asociado a dicho
	 * controlDecoration contiene una cantidad de caracteres comparados con el
	 * parametro cantidad, en dependencia de el numero de la opcion
	 * 
	 * opcion 1: campo con menos cantidad de caracteres que parametro cantidad
	 * 
	 * opcion 2: campo con menos o igual cantidad de caracteres que parametro
	 * cantidad
	 * 
	 * opcion 3: campo con igual cantidad de caracteres que parametro cantidad
	 * 
	 * opcion 4: campo con igual o mas cantidad de caracteres que parametro
	 * cantidad
	 * 
	 * opcion 5: campo con mas cantidad de caracteres que parametro cantidad
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al Control a validar
	 * @param cantidad
	 *            - cantidad a comparar con la cantidad de caracteres
	 * @param option
	 *            - opcion para comparar
	 */
	private static void ValidateFieldLenghtCharacteres(ControlDecoration controlDecoration, int cantidad, int option) {
		// opcion 1: campo con menos cantidad de caracteres que parametro
		// cantidad
		// opcion 2: campo con menos o igual cantidad de caracteres que
		// parametro cantidad
		// opcion 3: campo con igual cantidad de caracteres que parametro
		// cantidad
		// opcion 4: campo con igual o mas cantidad de caracteres que parametro
		// cantidad
		// opcion 5: campo con mas cantidad de caracteres que parametro cantidad
		Control control = controlDecoration.getControl();
		String texto = ((Text) control).getText();
		if (!texto.isEmpty()) {
			String caract;
			if (cantidad == 1) {
				caract = " caracter";
			} else {
				caract = " caracteres";
			}
			switch (option) {
			case 1:
				if (texto.length() < cantidad) {
					controlDecoration.hide();
				} else {
					controlDecoration.setDescriptionText("Este campo debe tener menos de " + cantidad + caract);
					controlDecoration.show();
				}
				break;

			case 2:
				if (texto.length() <= cantidad) {
					controlDecoration.hide();
				} else {
					controlDecoration.setDescriptionText("Este campo debe tener menos o igual que " + cantidad + caract);
					controlDecoration.show();
				}
				break;

			case 3:
				if (texto.length() == cantidad) {
					controlDecoration.hide();
				} else {
					controlDecoration.setDescriptionText("Este campo debe tener obligatoriamente " + cantidad + caract);
					controlDecoration.show();
				}
				break;

			case 4:
				if (texto.length() >= cantidad) {
					controlDecoration.hide();
				} else {
					controlDecoration.setDescriptionText("Este campo debe tener igual o mas de " + cantidad + caract);
					controlDecoration.show();
				}
				break;

			case 5:
				if (texto.length() > cantidad) {
					controlDecoration.hide();
				} else {
					controlDecoration.setDescriptionText("Este campo debe tener mas de " + cantidad + caract);
					controlDecoration.show();
				}
				break;
			}
			if (controlDecoration.isVisible()) {
				control.setBackground(new Color(control.getDisplay(), 255, 204, 153));
			} else {
				control.setBackground(null);
			}
		} else {
			control.setBackground(null);
			controlDecoration.hide();
		}
	}

	/**
	 * Recibe un controlDecoration y valida que el Control asociado a dicho
	 * controlDecoration contiene MENOS caracteres que el parametro cantidad.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al Control a validar.
	 * @param cantidad
	 *            - cantidad a comparar.
	 */
	public static void ValidateFieldLessCharacteresThan(ControlDecoration controlDecoration, int cantidad) {
		ValidateFieldLenghtCharacteres(controlDecoration, cantidad, 1);
	}

	/**
	 * Recibe un controlDecoration y valida que el Control asociado a dicho
	 * controlDecoration contiene IGUAL o MENOS caracteres que el parametro
	 * cantidad.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al Control a validar.
	 * @param cantidad
	 *            - cantidad a comparar.
	 */
	public static void ValidateFieldLessOrEqualCharacteresThan(ControlDecoration controlDecoration, int cantidad) {
		ValidateFieldLenghtCharacteres(controlDecoration, cantidad, 2);
	}

	/**
	 * Recive un controlDecoration y valida que el Control asociado a dicho
	 * controlDecoration contiene obligatoriamente IGUAL cantidad de caracteres
	 * que el parametro cantidad.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al Control a validar.
	 * @param cantidad
	 *            - cantidad a comparar.
	 */
	public static void ValidateFieldEqualCharacteresThan(ControlDecoration controlDecoration, int cantidad) {
		ValidateFieldLenghtCharacteres(controlDecoration, cantidad, 3);
	}

	/**
	 * Recibe un controlDecoration y valida que el Control asociado a dicho
	 * controlDecoration contiene IGUAL o MAS caracteres que el parametro
	 * cantidad.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al Control a validar.
	 * @param cantidad
	 *            - cantidad a comparar.
	 */
	public static void ValidateFieldMoreOrEqualCharacteresThan(ControlDecoration controlDecoration, int cantidad) {
		ValidateFieldLenghtCharacteres(controlDecoration, cantidad, 4);
	}

	/**
	 * Recibe un controlDecoration y valida que el Control asociado a dicho
	 * controlDecoration contiene MAS caracteres que el parametro cantidad.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al Control a validar.
	 * @param cantidad
	 *            - cantidad a comparar.
	 */
	public static void ValidateFieldMoreCharacteresThan(ControlDecoration controlDecoration, int cantidad) {
		ValidateFieldLenghtCharacteres(controlDecoration, cantidad, 5);
	}

	/**
	 * Recibe una lista de dos controlDecoration y valida que el Control
	 * asociado al controlDecoration de fecha HASTA sea posterior o igual a la
	 * fecha DESDE, en dependencia del parametro endMoreOrEqualToInit.
	 * 
	 * @param dateRange
	 *            - rango de fechas: Lista de controlDecoration de los controles
	 *            fecha DESDE y fecha HASTA
	 * @param endMoreOrEqualToInit
	 *            - boolean, si es true la fecha HASTA puede ser la misma que la
	 *            fecha DESDE
	 */
	private static void ValidateRangeDateFromTo(List<ControlDecoration> dateRange, boolean endMoreOrEqualToInit) {
		ControlDecoration decorationDesde = dateRange.get(0);
		Control controlFrom = decorationDesde.getControl();
		DateTime from = (DateTime) controlFrom;
		ControlDecoration decorationHasta = dateRange.get(1);
		Control controlTo = decorationHasta.getControl();
		DateTime to = (DateTime) controlTo;

		int responde = ValidationUtil.ValidateTwoDateTime(from, to);
		if (endMoreOrEqualToInit) {
			if (responde == -1) {
				decorationHasta.setDescriptionText("La fecha Fin debe ser la igual o posterior a la fecha Inicio");
				decorationHasta.show();
			} else {
				decorationHasta.hide();
			}
		} else {
			if (responde == 1) {
				decorationHasta.hide();
			} else {
				decorationHasta.setDescriptionText("La fecha Fin debe ser posterior a la fecha Inicio");
				decorationHasta.show();
			}
		}
		if (decorationHasta.isVisible()) {
			controlTo.setBackground(new Color(controlTo.getDisplay(), 255, 204, 153));
		} else {
			controlTo.setBackground(null);
		}

	}

	/**
	 * Recibe una lista de dos controlDecoration y valida que en el Control
	 * asociado a la fecha HASTA sea solo posterior a la fecha DESDE.
	 * 
	 * @param dateRange
	 *            - Lista de controlDecoration de los controles fecha DESDE y
	 *            fecha HASTA
	 */
	public static void ValidateRangeDateFromLessThanTo(List<ControlDecoration> dateRange) {
		ValidateRangeDateFromTo(dateRange, false);
	}

	/**
	 * Recibe una lista de dos controlDecoration y valida que el Control
	 * asociado a la fecha HASTA sea igual o posterior a la fecha DESDE.
	 * 
	 * @param dateRange
	 *            - Lista de controlDecoration de los controles fecha DESDE y
	 *            fecha HASTA
	 */
	public static void ValidateRangeDateFromLessOrEqualThanTo(List<ControlDecoration> dateRange) {
		ValidateRangeDateFromTo(dateRange, true);
	}

	/**
	 * Recibe un controlDecoration y un arreglo de checksboxes y valida que al
	 * menos haya un checkbox seleccionado.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al Control a validar.
	 * @param checks
	 *            - arreglo de checkboxes.
	 */
	public static void ValidateRequiredChecks(ControlDecoration controlDecoration, Button[] checks) {
		int checked = 0;
		for (int i = 0; i < checks.length; i++) {
			if (checks[i].getSelection()) {
				checked++;
			}
		}
		if (checked == 0) {
			controlDecoration.show();
		} else {
			controlDecoration.hide();
		}
	}

	/**
	 * Recibe un controlDecoration y un entero desde y otro hasta para validar
	 * que en control asociado tiene cantidad una caracteres entre esos dos
	 * valores
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al Control a validar
	 * @param from
	 *            - entero minima longitud
	 * @param to
	 *            - entero maxima longitud
	 */
	public static void ValidateFieldLenghtBetween(ControlDecoration controlDecoration, int from, int to) {
		Control control = controlDecoration.getControl();
		String texto = ((Text) control).getText();
		if (!texto.isEmpty()) {
			if (texto.length() < from || texto.length() > to) {
				controlDecoration.setDescriptionText("Este campo solo admite entre " + from + " y " + to + " caracteres");
				control.setBackground(new Color(control.getDisplay(), 255, 204, 153));
				controlDecoration.show();
			} else {
				control.setBackground(null);
				controlDecoration.hide();
			}
		} else {
			control.setBackground(null);
			controlDecoration.hide();
		}
	}

	/**
	 * Recibe un ControlDecoration de un label, un arreglo de checksboxes y una
	 * cantidad a comparar y valida con esa cantidad de checkbox seleccionados
	 * en dependencia de la opcion
	 * 
	 * opcion 1: deben seleccionarse menos comboboxes que el parametro cantidad
	 * 
	 * opcion 2: deben seleccionarse menos o igual cantidad de comboboxes que el
	 * parametro cantidad
	 * 
	 * opcion 3: cdeben seleccionarse exactamente tantos comboboxes como el
	 * parametro cantidad
	 * 
	 * opcion 4: deben seleccionarse igual o mas comboboxes que el parametro
	 * cantidad
	 * 
	 * opcion 5: deben seleccionarse mas comboboxes que el parametro cantidad
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al Control a validar.
	 * @param checkboxes
	 *            - arreglo de checkboxes.
	 * @param cantidad
	 *            - cantidad a comparar.
	 * @param option
	 *            - opcion a comparar.
	 */
	private static void ValidateCheckboxesSelected(ControlDecoration labelChecks, Button[] checkboxes, int cantidad, int option) {
		int checkeds = ValidationUtil.CountSelectedComboboxes(checkboxes);
		String elements;
		if (cantidad == 1) {
			elements = " elemento";
		} else {
			elements = " elementos";
		}
		switch (option) {
		case 1:
			if (checkeds < cantidad) {
				labelChecks.hide();
			} else {
				labelChecks.show();
				labelChecks.setDescriptionText("Debe seleccionarse menos de " + cantidad + elements);
			}
			break;
		case 2:
			if (checkeds <= cantidad) {
				labelChecks.hide();
			} else {
				labelChecks.show();
				labelChecks.setDescriptionText("Deben seleccionarse menos o igual que " + cantidad + elements);
			}
			break;
		case 3:
			if (checkeds == cantidad) {
				labelChecks.hide();
			} else {
				labelChecks.show();
				labelChecks.setDescriptionText("Deben seleccionarse exactamente " + cantidad + elements);
			}
			break;
		case 4:
			if (checkeds >= cantidad) {
				labelChecks.hide();
			} else {
				labelChecks.show();
				labelChecks.setDescriptionText("Deben seleccionarse igual o mas de " + cantidad + elements);
			}
			break;
		case 5:
			if (checkeds > cantidad) {
				labelChecks.hide();
			} else {
				labelChecks.show();
				labelChecks.setDescriptionText("Deben seleccionarse mas de " + cantidad + elements);
			}
			break;
		}
	}

	/**
	 * Recibe un ControlDecoration de un label, un arreglo de checksboxes y una
	 * cantidad a comparar y valida que haya menos de esa cantidad de checkbox
	 * seleccionados.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al control a validar.
	 * @param checkboxes
	 *            - arreglo de checkboxes.
	 * @param cantidad
	 *            - cantidad a comparar.
	 */
	public static void ValidateCheckboxesSelectedLessThan(ControlDecoration labelChecks, Button[] checkboxes, int cantidad) {
		ValidateCheckboxesSelected(labelChecks, checkboxes, cantidad, 1);
	}

	/**
	 * Recibe un ControlDecoration de un label, un arreglo de checksboxes y una
	 * cantidad a comparar y valida que haya menos o igual que esa cantidad de
	 * checkbox seleccionados.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al control a validar.
	 * @param checkboxes
	 *            - arreglo de checkboxes.
	 * @param cantidad
	 *            - cantidad a comparar.
	 */
	public static void ValidateCheckboxesSelectedLessOrEqualThan(ControlDecoration labelChecks, Button[] checkboxes, int cantidad) {
		ValidateCheckboxesSelected(labelChecks, checkboxes, cantidad, 2);
	}

	/**
	 * Recibe un label, un arreglo de checksboxes y una cantidad a comparar y
	 * valida que obligatoriamente esa cantidad de checkbox seleccionados.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al control a validar.
	 * @param checkboxes
	 *            - arreglo de checkboxes.
	 * @param cantidad
	 *            - cantidad a comparar.
	 */
	public static void ValidateCheckboxesSelectedEqualThan(ControlDecoration labelChecks, Button[] checkboxes, int cantidad) {
		ValidateCheckboxesSelected(labelChecks, checkboxes, cantidad, 3);
	}

	/**
	 * Recibe un ControlDecoration de un label, un arreglo de checksboxes y una
	 * cantidad a comparar y valida que haya igual o mas que esa esa cantidad de
	 * checkbox seleccionados.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al control a validar.
	 * @param checkboxes
	 *            - arreglo de checkboxes.
	 * @param cantidad
	 *            - cantidad a comparar.
	 */
	public static void ValidateCheckboxesSelectedMoreOrEqualThan(ControlDecoration labelChecks, Button[] checkboxes, int cantidad) {
		ValidateCheckboxesSelected(labelChecks, checkboxes, cantidad, 4);
	}

	/**
	 * Recibe un ControlDecoration de un label, un arreglo de checksboxes y una
	 * cantidad a comparar y valida que haya mas que esa esa cantidad de
	 * checkbox seleccionados.
	 * 
	 * @param controlDecoration
	 *            - controlDecoration asociado al control a validar.
	 * @param checkboxes
	 *            - arreglo de checkboxes.
	 * @param cantidad
	 *            - cantidad a comparar.
	 */
	public static void ValidateCheckboxesSelectedMoreThan(ControlDecoration labelChecks, Button[] checkboxes, int cantidad) {
		ValidateCheckboxesSelected(labelChecks, checkboxes, cantidad, 1);
	}

}
