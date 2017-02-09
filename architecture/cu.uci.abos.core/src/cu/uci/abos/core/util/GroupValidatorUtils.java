package cu.uci.abos.core.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abos.core.validation.AllRule;
import cu.uci.abos.core.validation.IsOneAllRule;
import cu.uci.abos.core.validation.LessOneRule;
import cu.uci.abos.core.validation.ValidationRule;

public class GroupValidatorUtils {
	static Map<GroupValidatorType, ValidationRule> rules;
	static {
		rules = new HashMap<GroupValidatorType, ValidationRule>();
		rules.put(GroupValidatorType.AT_LESS_ONE, new LessOneRule());
		rules.put(GroupValidatorType.ALL, new AllRule());
		rules.put(GroupValidatorType.IS_ONE_ALL, new IsOneAllRule());
	}

	public static boolean validate(Control[] controls, Composite parent, String[] groups, GroupValidatorType[] types) {
		boolean result = true;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < types.length; i++) {
			synchronized (sb) {
				for (int j = 0; j < groups.length; j++) {
					if (!rules.get(types[i]).setControls(controls).validate(groups[j])) {
						result = false;
						sb.append(rules.get(types[i]).getErrorMessage());
					}
				}
			}
			rules.get(types[i]).setControls(null);
		}
		if (!result) {
			if(parent!=null){
				RetroalimentationUtils.showErrorMessage(parent, sb.toString());
			}else{
				RetroalimentationUtils.showErrorShellMessage(sb.toString());
			}
			
		}
		return result;
	}
	
	public static boolean validate(Control[] controls, String[] groups, GroupValidatorType[] types) {
		return validate(controls, null, groups, types);
	}
}
