package cu.uci.abcd.cataloguing.util;

import java.util.ArrayList;

import cu.uci.abos.widget.repeatable.field.util.FieldStructure;


public class QuickSort {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	public void quickSort(int a, int b, ArrayList<FieldStructure> components){

		if(components == null || components.size() == 0)
			return;

		if(Compare.isGreaterThanOrEqual(a, b))
			return;	

		//pick the pivot
		int middle = a + (b - a)/2;
		FieldStructure pivot = components.get(middle);

		//make left < pivot and right > pivot
		int i = a, j = b;

		while (Compare.isLessThanOrEqual(i, j)){

			while (Compare.isLower(components.get(i).getTag(), pivot.getTag()) || 
					(Compare.isEqual(components.get(i).getTag(), pivot.getTag()) && 
							Compare.isLower(components.get(i).getSubtag(), pivot.getSubtag())))
				i++;

			while (Compare.isGreater(components.get(j).getTag(), pivot.getTag()) || 
					(Compare.isEqual(components.get(j).getTag(), pivot.getTag()) && 
							Compare.isGreater(components.get(j).getSubtag(), pivot.getSubtag())))
				j--;

			if (Compare.isLessThanOrEqual(i, j)){
				FieldStructure temp = components.get(i);
				components.set(i, components.get(j));
				components.set(j, temp);
				i++;
				j--;
			}
		}	
		//recursively sort two sub parts
		if (Compare.isLower(a, j))
			quickSort(a, j,components);

		if (Compare.isGreater(b, i))
			quickSort(i, b,components);
	}
}
