package cu.uci.abcd.cataloguing.util;

public class Compare {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	public static boolean isLower(int x, int y){

		boolean result;

		if(x >= 3000)
			x = -x;

		if(y >= 3000){
			y = -y;
		}

		if(x < 0 && y < 0){
			if(x > y)
				result = true;
			else
				result = false;
		}
		else{
			if(x < y)
				result = true;
			else
				result = false;
		}

		return result;

	}

	public static boolean isLessThanOrEqual(int x, int y){

		boolean result;

		if(x >= 3000)
			x = -x;

		if(y >= 3000){
			y = -y;
		}

		if(x < 0 && y < 0){
			if(x >= y)
				result = true;
			else
				result = false;
		}
		else{
			if(x <= y)
				result = true;
			else
				result = false;
		}

		return result;

	}

	public static boolean isGreater(int x, int y){

		boolean result;

		if(x >= 3000)
			x = -x;

		if(y >= 3000){
			y = -y;
		}

		if(x < 0 && y < 0){
			if(x < y)
				result = true;
			else
				result = false;
		}
		else{
			if(x > y)
				result = true;
			else
				result = false;
		}

		return result;

	}

	public static boolean isGreaterThanOrEqual(int x, int y){

		boolean result;

		if(x >= 3000)
			x = -x;

		if(y >= 3000){
			y = -y;
		}

		if(x < 0 && y < 0){
			if(x <= y)
				result = true;
			else
				result = false;
		}
		else{
			if(x >= y)
				result = true;
			else
				result = false;
		}

		return result;

	}

	public static boolean isEqual(int x, int y){
		boolean result;

		if(x == y)
			result = true;
		else
			result = false;

		return result;
	}
}
