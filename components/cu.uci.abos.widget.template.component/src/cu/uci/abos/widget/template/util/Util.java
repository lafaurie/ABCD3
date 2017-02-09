package cu.uci.abos.widget.template.util;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.core.util.SecurityUtils;

public class Util {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	public static String getDefHome(){
		Library library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		return library.getIsisDefHome();
	}
	
	public static Library getLibrary(){
		Library library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		return library;
	}

}
