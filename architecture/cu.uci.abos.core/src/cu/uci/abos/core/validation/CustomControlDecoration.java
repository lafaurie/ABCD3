package cu.uci.abos.core.validation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class CustomControlDecoration {

	private Map<String, ControlDecoration> listMapControlDecorations;
	private CustomControlDecoration decorationFactory;

	public CustomControlDecoration() {
		setListMapControlDecorations(new HashMap<String, ControlDecoration>());
	}

	public Map<String, ControlDecoration> getListMapControlDecorations() {
		return listMapControlDecorations;
	}

	private void setListMapControlDecorations(Map<String, ControlDecoration> listMapControlDecorations) {
		this.listMapControlDecorations = listMapControlDecorations;
	}

	public void createDecorator(CustomControlDecoration decorationFactory, DecoratedControl[] decoratedControls, DecoratorType decoratorType, int beforeYears, int beforeMonths, int beforeDays, int afterYears, int afterMonths, int afterDays, int option, int position) {
		this.decorationFactory = decorationFactory;
		switch (decoratorType) {
		case DATE_RANGE:{
			CreateDecorateToRangeDateField(decoratedControls[0], position, beforeYears, beforeMonths, beforeDays, afterYears, afterMonths, afterDays);
			break;
		}
		default:
			break;
		}
		
	}
	
	public void createDecorator(CustomControlDecoration decorationFactory, DecoratedControl[] decoratedControls, DecoratorType decoratorType, int years, int months, int days, int option, int position) {
		this.decorationFactory = decorationFactory;
		switch (decoratorType) {
		case ANY_TIME_BACKWARD:{
			CreateDecorateToDateAnyYearsBackwardField(decoratedControls[0], position, years, months, days);
			break;
		}
		case ANY_TIME_FORWARD:{
			CreateDecorateToDateAnyYearsForwardField(decoratedControls[0], position, years, months, days);
			break;
		}
		default:
			break;
		}
	}
	
	public void createDecorator(CustomControlDecoration decorationFactory, DecoratedControl[] decoratedControls, DecoratorType decoratorType, int option, int position) {
		createDecorator(decorationFactory, decoratedControls, decoratorType, option, position, 500);
	}
	public void createDecorator(CustomControlDecoration decorationFactory, DecoratedControl[] decoratedControls, DecoratorType decoratorType, int option, int position, int maxLenght) {
		this.decorationFactory = decorationFactory;
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
		//case LETTERS_ONLY: {
			//CreateDecorateToOnlyLettersField(decoratedControls[0], position, maxLenght);
			//break;
		//}
		case NUMBER_ONLY: {
			CreateDecorateToOnlyNumberField(decoratedControls[0], position, maxLenght);
			break;
		}
		case NUMBER_POINT_ONLY: {
			CreateDecorateToOnlyNumberAndPointField(decoratedControls[0], position, maxLenght);
			break;
		}
		case ALPHA_NUMERIC: {
			CreateDecorateToAlphaNumericField(decoratedControls[0], position, maxLenght);
			break;
		}
		case ALPHA_SPACES: {
			CreateDecorateToAlphaSpaceField(decoratedControls[0], position, maxLenght);
			break;
		}
		
		case ALPHA_SPACES_GUION: {
			CreateDecorateToAlphaSpaceFieldGuion(decoratedControls[0], position, maxLenght);
			break;
		}
		
		case EMAIL: {
			CreateDecorateToEMailField(decoratedControls[0], position, maxLenght);
			break;
		}
		case URL: {
			CreateDecorateToUrlField(decoratedControls[0], position, maxLenght);
			break;
		}
		case ALPHA_NUMERICS_SPACES: {
			CreateDecorateToAlphaNumericSpaceField(decoratedControls[0], position, maxLenght);
			break;
		}
		case DOUBLE: {
			CreateDecorateToDoubleField(decoratedControls[0], position, maxLenght);
			break;
		}
		//case DATE_RANGE: {
		//	DecoratedControl auxDecoratedControl = decoratedControls[0];
		//	DecoratedControl auxDecoratedControl2 = decoratedControls[1];
		//	CreateDecorateToRangeDate((DateTime) auxDecoratedControl.getDecorateControl(), (DateTime) auxDecoratedControl2.getDecorateControl(), option, auxDecoratedControl.getDecorateControlKey(),
			//		auxDecoratedControl2.getDecorateControlKey(), position);
		//	break;
		//}
		/*
		case DOUBLE_SPACES: {
			CreateDecorateToDoubleSpaceField(decoratedControls[0], position, maxLenght);
			break;
		}
		*/
		case ALPHA: {
			CreateDecorateToAlphaField(decoratedControls[0], position, maxLenght);
			break;
		}
		
		//case PROVIDER_NAME: {
			//CreateDecorateToProviderNameField(decoratedControls[0], position, maxLenght);
			//break;
		//}
		
		case ALPHANUMERIC_AND_UNDERSCORE:{
			CreateDecorateToAlphaNumericAndUnderscoreField(decoratedControls[0], position, maxLenght);
			break;
		}
		
		case ALL_BUT_NO_OTHER_REPEAT_CONSECUTIVE:{
			CreateDecorateToNoOtherRepeatConsecutiveField(decoratedControls[0], position, maxLenght);
			break;
		}
		
		case NUMBER_ONLY_NOT_START_WITH_CERO:{
			CreateDecorateToOnlyNumberNotStartWithZeroField(decoratedControls[0], position, maxLenght);
			break;
		}
		
		
		case PORT:{
			CreateDecorateToPortField(decoratedControls[0], position, maxLenght);
			break;
		}
		
		case DOMAIN:{
			CreateDecorateToDomainField(decoratedControls[0], position, maxLenght);
			break;
		}
		
		case IP:{
			CreateDecorateToIpField(decoratedControls[0], position, maxLenght);
			break;
		}
		
		//case GRUPAL_VALIDATION:{
			//CreateDecorateToGrupalValidationField(decoratedControls, position);
			//CreateDecorateToGrupalValidationField(decoratedControls[1], position);
			//break;
		//}
		
		case LENGTH_FIELD: {
			CreateDecorateToLenghtField(decoratedControls[0], position, maxLenght);
			break;
		}	    	
		case USER_NAME: {
			CreateDecorateToUserNameField(decoratedControls[0], position, maxLenght);
			break;
		}	
		default:
			break;
		}
	}
	
	private void CreateDecorateToAlphaNumericAndUnderscoreField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldAlphaNumericAndUnderscore(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}

	
	private void CreateDecorateToNoOtherRepeatConsecutiveField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldNoOtherRepeatConsecutive(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}
	
	/**
	 * Retorna un Control Decoration visible o oculto en dependencia de la
	 * variable visible para un Control requerido pasado por parametro.
	 * 
	 * @param control
	 *            - control a crearle el Control Decoration.
	 * @param visible
	 *            - visibilidad inicial del ControlDecoration.
	 * @param maxLenght 
	 */
	private void CreateDecorateToRequeredField(DecoratedControl decoratedControl, boolean visible, int position) {
		String image = "DEC_REQUIRED";
		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(),
				visible, position);
		
		controlDecoration.getControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1772240475592411618L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldRequired(controlDecoration);
				decorationFactory.paint(controlDecoration.getControl());
				
			}
		});
		
		/*
		Control control = controlDecoration.getControl();
		if (control instanceof Text) {
			control.addListener(SWT.Modify, new Listener() {
				private static final long serialVersionUID = 1772240475592411618L;

				@Override
				public void handleEvent(Event arg0) {
					CustomValidation.ValidateFieldRequired(controlDecoration);
				}
			});
		} else {
			if (control instanceof Combo) {
				((Combo)control).addSelectionListener(new SelectionListener() {
					private static final long serialVersionUID = -4186307840238390263L;

					public void widgetSelected(SelectionEvent arg0) {
						CustomValidation.ValidateFieldRequired(controlDecoration);
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});
				
			}
		}
		*/
		
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
	 * @param maxLenght 
	 * 
	 * @param control
	 *            - control a crearle el Control Decoration.
	 */
	/*
	private void CreateDecorateToOnlyLettersField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";
		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = -465852997372118643L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldOnlyLetters(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}
*/
	/**
	 * Retorna un Control Decoration oculto para un Control que admite solo
	 * numeros.
	 * @param maxLenght 
	 * 
	 * @param control
	 *            - control a crearle el Control Decoration.
	 */
	private void CreateDecorateToOnlyNumberField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";
		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 5863700271394570610L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldOnlyNumbers(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}
	
	private void CreateDecorateToPortField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";
		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 5863700271394570610L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldPort(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}
	
	private void CreateDecorateToOnlyNumberNotStartWithZeroField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";
		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 5863700271394570610L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldOnlyNumbersNotStartWithZero(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}

	/**
	 * Retorna un Control Decoration oculto para un Control que admite solo
	 * letras o numeros.
	 * @param maxLenght 
	 * 
	 * @param control
	 *            - control a crearle el Control Decoration.
	 */
	private void CreateDecorateToAlphaNumericField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldAlphaNumeric(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}    
		});
	}
	
	private void CreateDecorateToLenghtField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateLengthField(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}
	
	private void CreateDecorateToDomainField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";
		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = -8725139884954084931L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldDomain(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}
	
	private void CreateDecorateToAlphaNumericSpaceField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldAlphaNumericsSpaces(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}
	
	
	/*
	private void CreateDecorateToDoubleSpaceField(DecoratedControl decoratedControl, int position, int maxLenght) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldDoubleSpaces(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}
	*/
	private void CreateDecorateToAlphaSpaceField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldAlphaSpaces(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}
	
	private void CreateDecorateToAlphaSpaceFieldGuion(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldAlphaSpacesGuion(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}
	
	private void CreateDecorateToOnlyNumberAndPointField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation. ValidateFieldOnlyNumbersAndPoints(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}

	/**
	 * Retorna un Control Decoration oculto para un Control con formato de
	 * email.
	 * @param maxLenght 
	 * 
	 * @param control
	 *            - control a crearle el Control Decoration.
	 */
	private void CreateDecorateToEMailField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";
		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = -8725139884954084931L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldEMail(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}
	
	private void CreateDecorateToUrlField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";
		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = -8725139884954084931L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldUrl(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}
	
	
	/*
	private void CreateDecorateToGrupalValidationField(DecoratedControl[] decoratedControl, int position) {
		String image = "DEC_ERROR";
		final ControlDecoration controlDecoration1 = CreateDecorateToFieldAs(decoratedControl[0].getDecorateControl(), image, decoratedControl[0].getDecorateControlKey(), decoratedControl[0].getText(), false,
				position);
		final ControlDecoration controlDecoration2 = CreateDecorateToFieldAs(decoratedControl[0].getDecorateControl(), image, decoratedControl[0].getDecorateControlKey(), decoratedControl[0].getText(), false,
				position);
		
		CustomValidation.ValidateFieldGrupal(controlDecoration1, controlDecoration2);
		CustomValidation.ValidateFieldGrupal(controlDecoration2, controlDecoration1);
		
		decoratedControl[0].getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = -8725139884954084931L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldGrupal(controlDecoration1, controlDecoration2);
				decorationFactory.paint(controlDecoration1.getControl());
				decorationFactory.paint(controlDecoration2.getControl());
			}
		});
		
		decoratedControl[1].getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = -8725139884954084931L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldGrupal(controlDecoration2, controlDecoration1);
				decorationFactory.paint(controlDecoration1.getControl());
				decorationFactory.paint(controlDecoration2.getControl());
			}
		});
		
	}
	*/
	private void CreateDecorateToIpField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";
		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = -8725139884954084931L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldIp(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}

	/**
	 * Retorna un Control Decoration oculto para un Control con formato de
	 * double.
	 * @param maxLenght 
	 * 
	 * @param control
	 *            - control a crearle el Control Decoration.
	 */

	private void CreateDecorateToDoubleField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";
		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			/**
					 * 
					 */
			private static final long serialVersionUID = 7320708214963684394L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldDouble(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}
	
	

	private void CreateDecorateToAlphaField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldAlpha(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}
	/*
	private void CreateDecorateToProviderNameField(DecoratedControl decoratedControl, int position) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldProviderName(controlDecoration);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}
	*/
	
	private void CreateDecorateToRangeDateField(DecoratedControl decoratedControl, int position, final int beforeYears, final int beforeMonths, final int beforeDays, final int afterYears, final int afterMonths, final int afterDays) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		
		CustomValidation.ValidateFieldDateRange(controlDecoration, beforeYears, beforeMonths, beforeDays, afterYears, afterMonths, afterDays);
		
		((DateTime)decoratedControl.getDecorateControl()).addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				CustomValidation.ValidateFieldDateRange(controlDecoration, beforeYears, beforeMonths, beforeDays, afterYears, afterMonths, afterDays);
				decorationFactory.paint(controlDecoration.getControl());
			}

		});
		
	}
	
	private void CreateDecorateToDateAnyYearsBackwardField(DecoratedControl decoratedControl, int position, final int years, final int months, final int days) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		
		CustomValidation.ValidateFieldDateAnyYearsBackward(controlDecoration, years, months, days);
		
		((DateTime)decoratedControl.getDecorateControl()).addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				CustomValidation.ValidateFieldDateAnyYearsBackward(controlDecoration, years, months, days);
				decorationFactory.paint(controlDecoration.getControl());
			}

		});
		
	}
	
	private void CreateDecorateToDateAnyYearsForwardField(DecoratedControl decoratedControl, int position, final int years, final int months, final int days) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		
		CustomValidation.ValidateFieldDateAnyYearsForward(controlDecoration, years, months, days);
		
		((DateTime)decoratedControl.getDecorateControl()).addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				CustomValidation.ValidateFieldDateAnyYearsForward(controlDecoration, years, months, days);
				decorationFactory.paint(controlDecoration.getControl());
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

	/*
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
*/
	/*
	public void CreateDecorateToLengthField(final CustomControlDecoration decorationFactory, DecoratedControl decoratedControl, int position, final int maxLength) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateLengthField(controlDecoration, maxLength);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
	}
		*/
	
	public void paint(Control control) {
		//int cont = 0;
		Collection<ControlDecoration> collection = getListMapControlDecorations().values();
		for (Iterator<ControlDecoration> iterator = collection.iterator(); iterator.hasNext();) {
			ControlDecoration controlDecoration = (ControlDecoration) iterator.next();
			
			if (controlDecoration.getControl() == control &&  controlDecoration.isVisible()) {
				//return false;
				//Control control = controlDecoration.getControl();
				control.setBackground(new Color(control.getDisplay(), 255, 204, 153));
				
				return;
				//controlDecoration.show();
				//cont++;
			}
			

		}
		control.setBackground(null);
		//return cont==0;
	}
	
	public boolean AllControlDecorationsHide() {
		int cont = 0;
		Collection<ControlDecoration> collection = listMapControlDecorations.values();
		for (Iterator<ControlDecoration> iterator = collection.iterator(); iterator.hasNext();) {
			ControlDecoration controlDecoration = (ControlDecoration) iterator.next();
			if (controlDecoration.isVisible()) {
				Control control = controlDecoration.getControl();
				control.setBackground(new Color(control.getDisplay(), 255, 204, 153));
				controlDecoration.show();
				cont++;
			}
		}
		return cont==0;
	}
	
	public void unPaintAll(){
		Collection<ControlDecoration> collection = listMapControlDecorations.values();
		for (Iterator<ControlDecoration> iterator = collection.iterator(); iterator.hasNext();) {
			ControlDecoration controlDecoration = (ControlDecoration) iterator.next();
			Control control = controlDecoration.getControl();
			control.setBackground(null);
		}
	}
	
	public boolean isRequiredControlDecorationIsVisible() {
		unPaintAll();
		int cont = 0;
		Collection<ControlDecoration> collection = listMapControlDecorations.values();
		Image warningImage = FieldDecorationRegistry.getDefault().getFieldDecoration("DEC_REQUIRED").getImage();
		for (Iterator<ControlDecoration> iterator = collection.iterator(); iterator.hasNext();) {
			ControlDecoration controlDecoration = (ControlDecoration) iterator.next();
			Control control = controlDecoration.getControl();
			if ( ( controlDecoration.getImage()==warningImage )  && (  controlDecoration.isVisible() ) ) {
				control.setBackground(new Color(control.getDisplay(), 255, 204, 153));
				controlDecoration.show();
				cont++;
			}
		}
		return cont>0;
	}
	        
	private void CreateDecorateToUserNameField(DecoratedControl decoratedControl, int position, final int maxLenght) {
		String image = "DEC_ERROR";

		final ControlDecoration controlDecoration = CreateDecorateToFieldAs(decoratedControl.getDecorateControl(), image, decoratedControl.getDecorateControlKey(), decoratedControl.getText(), false,
				position);
		decoratedControl.getDecorateControl().addListener(SWT.Modify, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				CustomValidation.ValidateFieldUserNames(controlDecoration, maxLenght);
				decorationFactory.paint(controlDecoration.getControl());
			}
		});
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
	
	public void hideControlDecoration(String contrlKey) {
		listMapControlDecorations.get(contrlKey).hide();
	}
	
	public void removeAllControlDecoration() {
		listMapControlDecorations.clear();
	}

	
	
}
