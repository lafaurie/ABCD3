package cu.uci.abos.core.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import cu.uci.abos.core.l10n.AbosMessages;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DateUtil;
import cu.uci.abos.core.validation.DecoratedControl;
import cu.uci.abos.core.validation.DecoratorType;

public class ValidatorUtils {
	
	public CustomControlDecoration decorationFactory;

	public ValidatorUtils(CustomControlDecoration decorationFactory) {
		this.decorationFactory = decorationFactory;
	}
	
	public ValidatorUtils() {
		
	}
	
	public void applyValidator(Control control, int maxLength){
		if (control.getClass().equals( Text.class)) {
			DecoratedControl[] decorated = new DecoratedControl[] { new DecoratedControl(control,UUID.randomUUID().toString() , "El máximo de caracteres permitidos es " + maxLength +". ") };
			decorationFactory.createDecorator(decorationFactory, decorated, DecoratorType.LENGTH_FIELD, 0, SWT.RIGHT, maxLength);
		}
	}
	
	public void applyValidator(Control control, int maxLength, String key){
		if (control.getClass().equals( Text.class)) {
			DecoratedControl[] decorated = new DecoratedControl[] { new DecoratedControl(control, key, "El máximo de caracteres permitidos es " + maxLength +". ") };
			decorationFactory.createDecorator(decorationFactory, decorated, DecoratorType.LENGTH_FIELD, 0, SWT.RIGHT, maxLength);
		}
	}
	
	public void applyValidator(Control control, String key, DecoratorType type, boolean visible){
		applyValidator(control, key, type, visible, 500);
	}

	public void applyRangeDateValidator(Control control, String key, DecoratorType type, int BeforeYears, int BeforeMonths, int BeforeDays, int AfterYears, int AfterMonths, int AfterDays, boolean visible) {
		String message = "Error";
		DecoratedControl[] decorated;
		switch (type) {
		case DATE_RANGE:
			Date referenceOne = new Date(DateUtil.DateAnyYearsBefore(BeforeYears, BeforeMonths, BeforeDays).getTime().getTime() );
			Date referenceTwo = new Date(DateUtil.DateAnyYearsBefore(AfterYears, AfterMonths, AfterDays).getTime().getTime() );
			message = MessageUtil.unescape("Este campo permite solo fechas entre el "+ new SimpleDateFormat("dd/MM/yyyy").format(referenceOne)  + " y el " + new SimpleDateFormat("dd/MM/yyyy").format(referenceTwo)   );
			break;
		default:
			break;
		}
		decorated = new DecoratedControl[] { new DecoratedControl(control, key, message) };
		decorationFactory.createDecorator(decorationFactory, decorated, type, BeforeYears, BeforeMonths, BeforeDays,  AfterYears, AfterMonths, AfterDays, 0, SWT.RIGHT);
	}
	
	public void applyDateValidator(Control control, String key, DecoratorType type, int BeforeYears, int BeforeMonths, int BeforeDays, boolean visible) {
		String message = "Error";
		DecoratedControl[] decorated;
		Date reference;
		switch (type) {
		case ANY_TIME_BACKWARD:
			reference = new Date(DateUtil.DateAnyYearsBefore(BeforeYears, BeforeMonths, BeforeDays+1).getTime().getTime() );
			message = MessageUtil.unescape("Este campo permite solo fechas anteriores al "+ new SimpleDateFormat("dd-MM-yyyy").format(reference));
			break;
			
		case ANY_TIME_FORWARD:
			reference = new Date(DateUtil.DateAnyYearsBefore(BeforeYears, BeforeMonths, BeforeDays-1).getTime().getTime() );
			message = MessageUtil.unescape("Este campo permite solo fechas posteriores al " + new SimpleDateFormat("dd-MM-yyyy").format(reference));
			break;
			
		default:
			break;
		}
		decorated = new DecoratedControl[] { new DecoratedControl(control, key, message) };
		decorationFactory.createDecorator(decorationFactory, decorated, type, BeforeYears, BeforeMonths, BeforeDays, 0, SWT.RIGHT);
	}
	
	public void applyValidator(Control control, String key, DecoratorType type, boolean visible, int maxLenght) {
		String message = "";
		DecoratedControl[] decorated;
		if(type==DecoratorType.REQUIRED_FIELD){
			message = MessageUtil.unescape(AbosMessages.get().MESSAGE_VALIDATE_FIELD_REQUIRED);
		}
		decorated = new DecoratedControl[] { new DecoratedControl(control, key, message) };
		decorationFactory.createDecorator(decorationFactory, decorated, type, 0, SWT.RIGHT, maxLenght);
	}
}