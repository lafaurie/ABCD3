package cu.uci.abos.core.util;

import java.util.Locale;

import org.eclipse.swt.graphics.Image;

public final class Language {
	public final String name;
	public final Locale locale;
	public final Image flag;
	public final int index;

	public Language(Locale locale, Image flag, int index) {
		if (locale == null) {
			this.name = "Default";
		} else {
			this.name = locale.getDisplayLanguage();
		}
		this.locale = locale;
		this.flag = flag;
		this.index = index;
	}

}
