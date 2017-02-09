package cu.uci.abos.widget.template.util;

public class Compare {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	public static boolean isLower(int x, int y){
		boolean result;

		if (x == 30)
			x = -1;

		if (y == 30) 
			y = -1;

		if (x < y)
			result = true;
		else
			result = false;

		return result;
	}

	public static boolean isLessThanOrEqual(int x, int y){
		boolean result;

		if (x == 30)
			x = -1;

		if (y == 30)
			y = -1;

		if (x <= y)
			result = true;
		else
			result = false;

		return result;
	}

	public static boolean isGreater(int x, int y){
		boolean result;

		if (x == 30)
			x = -1;

		if (y == 30)
			y = -1;

		if (x > y)
			result = true;
		else
			result = false;

		return result;
	}

	public static boolean isGreaterThanOrEqual(int x, int y){
		boolean result;

		if (x == 30)
			x = -1;

		if (y == 30)
			y = -1;

		if (x >= y)
			result = true;
		else
			result = false;

		return result;
	}
}
