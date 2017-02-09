package cu.uci.abos.validation.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class CustomControlDecoration {

	private Map<String, ControlDecoration> listMapControlDecorations;

	public CustomControlDecoration() {
		setListMapControlDecorations(new HashMap<String, ControlDecoration>());
	}

	public Map<String, ControlDecoration> getListMapControlDecorations() {
		return listMapControlDecorations;
	}

	private void setListMapControlDecorations(Map<String, ControlDecoration> listMapControlDecorations) {
		this.listMapControlDecorations = listMapControlDecorations;
	}

	public void createDecorator(DecoratedControl[] decoratedControls, DecoratorType decoratorType, int option, int position) {
		switch (decoratorType) {
		case REQUIRED_FIELD: {
			CreateDecorateToRequeredField(decoratedControls[0], true, position);
			break;
		}
		case ERROR: {
			CreateDefaultErrorDecorateToField(decoratedControls[0], position);
			break;
		}
		case WARNING: {
			CreateDefaultWarningDecorateToField(decoratedControls[0], position);
			break;
		}
		case INFORMATION: {
			CreateDefaultInformationDecorateToField(decoratedControls[0], position);
			break;
		}
		case CONTENT_PROPOSAL: {
			CreateDefaultContentProposalDecorateToField(decoratedControls[0], position);
			break;
		}
		case LETTERS_ONLY: {
			CreateDecorateToOnlyLettersField(decoratedControls[0], position);
			break;
		}
		case NUMBER_ONLY: {
			CreateDecorateToOnlyNumberField(decoratedControls[0], position);
			break;
		}
		case ALPHA_NUMERIC: {
			CreateDecorateToAlphaNumericField(decoratedControls[0], position);
			break;
		}
		case EMAIL: {
			CreateDecorateToEMailField(decoratedControls[0], position);
			break;
		}
		case DOUBLE: {
			CreateDecorateToDoubleField(decoratedControls[0], position);
			break;
		}
		case DATE_RANGE: {
			DecoratedControl auxDecoratedControl = decoratedControls[0];
			DecoratedControl auxDecoratedControl2 = decoratedControls[1];
			CreateDecorateToRangeDate((DateTime) auxDecoratedControl.getDecorateControl(), (DateTime) auxDecoratedControl2.getDecorateControl(), option, auxDecoratedControl.getDecorateControlKey(),
					auxDecoratedControl2.getDecorateControlKey(), position);
			break;
		}
		default:
			break;
		}
	}

	/**
	 * Retorna un Control Decoration visible o oculto en dependencia de la
	 * variable visible para un Control requerido pasado por parametro.
	 * 
	 * @param control
	 *            - control a crearle el Control Decoration.
	 * @param visible
	 *            - visibilidad inicial del ControlDecoration.
	 */
	private void CreateDecorateToRequeredField(DecoratedControl decoratedControl, boolean visible, int position) {
		String image = "DEC_REQUIRED";
		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(),
				visible, position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1772240475592411618L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldRequired(controlDecoration);
			}
		});
	}

	/**
	 * Retorna un Control Decoration para un Control.
	 * 
	 * @param control
	 *            - control a crearle el Control Decoration.
	 * @param image
	 *            - icono del Control Decoration.
	 * @param description
	 *            - descripcion del Control Decoration.
	 * @param visible
	 *            - visibilidad inicial del ControlDecoration.
	 */
	private ControlDecoration CreateDecorateToFieldAs(Control control, String image, String controlDecorationName, String description, boolean visible, int position) {
		final ControlDecoration decoration = new ControlDecoration(control, position);
		Image warningImage = FieldDecorationRegistry.getDefault().getFieldDecoration(image).getImage();
		decoration.setImage(warningImage);
		decoration.setMarginWidth(5);
		decoration.setDescriptionText(description);

		if (visible) {
			decoration.show();
		} else {
			decoration.hide();
		}
		getListMapControlDecorations().put(controlDecorationName, decoration);
		return decoration;
	}

	/**
	 * Retorna un Control Decoration oculto, con el icono de error, y sin
	 * descripcion para un Control.
	 * 
	 * @param control
	 *            - control a crearle el ControlDecoration.
	 */
	private ControlDecoration CreateDefaultErrorDecorateToField(DecoratedControl decoratedControl, int position) {
		String image = "DEC_ERROR";
		return CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false, position);
	}

	/**
	 * Retorna un Control Decoration oculto, con el icono de Warning, y sin
	 * descripcion para un Control.
	 * 
	 * @param control
	 *            - control a crearle el ControlDecoration.
	 */
	private ControlDecoration CreateDefaultWarningDecorateToField(DecoratedControl decoratedControl, int position) {
		String image = "DEC_WARNING";
		return CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false, position);
	}

	/**
	 * Retorna un Control Decoration oculto, con el icono de Warning, y sin
	 * descripcion para un Control.
	 * 
	 * @param control
	 *            - control a crearle el ControlDecoration
	 */
	private ControlDecoration CreateDefaultInformationDecorateToField(DecoratedControl decoratedControl, int position) {
		String image = "DEC_INFORMATION";
		return CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false, position);
	}

	/**
	 * Retorna un Control Decoration oculto, con el icono de
	 * ContentProposal(propuesta de contenido), y sin descripcion para un
	 * Control.
	 * 
	 * @param control
	 *            - control a crearle el ControlDecoration.
	 * 
	 */
	private ControlDecoration CreateDefaultContentProposalDecorateToField(DecoratedControl decoratedControl, int position) {
		String image = "DEC_CONTENT_PROPOSAL";
		return CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false, position);
	}

	/**
	 * Retorna un Control Decoration oculto para un Control que admite solo
	 * letras.
	 * 
	 * @param control
	 *            - control a crearle el Control Decoration.
	 */
	private void CreateDecorateToOnlyLettersField(DecoratedControl decoratedControl, int position) {
		String image = "DEC_ERROR";
		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = -465852997372118643L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldOnlyLetters(controlDecoration);
			}
		});
	}

	/**
	 * Retorna un Control Decoration oculto para un Control que admite solo
	 * numeros.
	 * 
	 * @param control
	 *            - control a crearle el Control Decoration.
	 */
	private void CreateDecorateToOnlyNumberField(DecoratedControl decoratedControl, int position) {
		String image = "DEC_ERROR";
		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 5863700271394570610L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldOnlyNumbers(controlDecoration);
			}
		});
	}

	/**
	 * Retorna un Control Decoration oculto para un Control que admite solo
	 * letras o numeros.
	 * 
	 * @param control
	 *            - control a crearle el Control Decoration.
	 */
	private void CreateDecorateToAlphaNumericField(DecoratedControl decoratedControl, int position) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldAlphaNumeric(controlDecoration);
			}
		});
	}

	/**
	 * Retorna un Control Decoration oculto para un Control con formato de
	 * email.
	 * 
	 * @param control
	 *            - control a crearle el Control Decoration.
	 */
	private void CreateDecorateToEMailField(DecoratedControl decoratedControl, int position) {
		String image = "DEC_WARNING";
		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = -8725139884954084931L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldEMail(controlDecoration);
			}
		});
	}

	/**
	 * Retorna un Control Decoration oculto para un Control con formato de
	 * double.
	 * 
	 * @param control
	 *            - control a crearle el Control Decoration.
	 */

	private void CreateDecorateToDoubleField(DecoratedControl decoratedControl, int position) {
		String image = "DEC_WARNING";
		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			/**
					 * 
					 */
			private static final long serialVersionUID = 7320708214963684394L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldDouble(controlDecoration);
			}
		});
	}

	/**
	 * Retorna una lista de 2 Control Decoration oculto para 2 Controles
	 * DataTime donde la fecha HASTA debe ser igual y(o) posterior a la fecha
	 * DESDE (Validar rango de fechas).
	 * 
	 * @param init
	 *            - DataTime DESDE.
	 * @param end
	 *            - DataTime HASTA.
	 */

	// option 1: menor
	// option 2: menor o igual
	private void CreateDecorateToRangeDate(DateTime init, DateTime end, final int option, String controlDecorationName1, String controlDecorationName2, int position) {
		String image = "DEC_ERROR";
		ControlDecoration decorationInit = new ControlDecoration(init, SWT.LEFT);
		Image warningImage = FieldDecorationRegistry.getDefault().getFieldDecoration(image).getImage();
		decorationInit.setImage(warningImage);
		decorationInit.setMarginWidth(5);
		decorationInit.hide();

		ControlDecoration decorationEnd = new ControlDecoration(end, SWT.LEFT);
		decorationEnd.setImage(warningImage);
		decorationEnd.setMarginWidth(5);
		decorationEnd.hide();

		final List<ControlDecoration> lista = new ArrayList<ControlDecoration>();
		lista.add(decorationInit);
		lista.add(decorationEnd);

		init.addListener(SWT.Modify, new Listener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7768447506334944639L;

			@Override
			public void handleEvent(Event arg0) {
				switch (option) {
				case 1:
					CustomValidation.ValidateRangeDateFromLessThanTo(lista);
					break;
				case 2:
					CustomValidation.ValidateRangeDateFromLessOrEqualThanTo(lista);
					break;
				}
			}
		});

		end.addListener(SWT.Modify, new Listener() {

			private static final long serialVersionUID = -3892922400543524654L;

			@Override
			public void handleEvent(Event arg0) {
				switch (option) {
				case 1:
					CustomValidation.ValidateRangeDateFromLessThanTo(lista);
					break;
				case 2:
					CustomValidation.ValidateRangeDateFromLessOrEqualThanTo(lista);
					break;
				}
			}
		});

		getListMapControlDecorations().put(controlDecorationName1, decorationInit);
		getListMapControlDecorations().put(controlDecorationName2, decorationEnd);

	}

	public boolean AllControlDecorationsHide() {
		Collection<ControlDecoration> collection = listMapControlDecorations.values();
		for (Iterator<ControlDecoration> iterator = collection.iterator(); iterator.hasNext();) {
			ControlDecoration controlDecoration = (ControlDecoration) iterator.next();
			if (controlDecoration.isVisible()) {
				return false;
			}

		}
		return true;
	}

	public void setControlDecorationText(String controlKey, String text) {
		listMapControlDecorations.get(controlKey).setDescriptionText(text);
	}

	public ControlDecoration getControlByKey(String controlKey) {
		return listMapControlDecorations.get(controlKey);
	}

	public void removeControlDecoration(String contrlKey) {
		listMapControlDecorations.remove(contrlKey);
	}
}
