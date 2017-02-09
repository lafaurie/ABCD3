package cu.uci.abos.widget.template.util;

public class BinarySearch {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	public static boolean contain(int[] obligatoryFields, int number){

		boolean result = false;
		int loIndex = 0;
		int hiIndex = obligatoryFields.length-1;

		while(loIndex <= hiIndex){
			int midIdenx = (loIndex + hiIndex)/2;
			if(number > obligatoryFields[midIdenx])
				loIndex = midIdenx + 1;
			else if(number < obligatoryFields[midIdenx])
				hiIndex = midIdenx - 1;
			else{
				break;
			}
		}

		if (loIndex > hiIndex)
			result = false;
		else
			result = true;

		return result; 	
	}
}
