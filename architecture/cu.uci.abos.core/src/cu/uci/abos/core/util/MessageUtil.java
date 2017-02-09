package cu.uci.abos.core.util;

import org.eclipse.rap.rwt.internal.util.Entities;

public class MessageUtil {
	public static String unescape(String str) {
		return Entities.HTML40.unescape(str);
	}

	public static String escape(String str) {
		return Entities.HTML40.escape(str);
	}
}
