package cu.uci.abos.widget.template.util;

import java.util.ArrayList;

import cu.uci.abos.widget.repeatable.field.domain.FieldDomain;

public class QuickSort {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	public static void sort(Integer[] matrix, int a, int b, ArrayList<ArrayList<FieldDomain>> components){

		if (matrix == null || matrix.length == 0)
			return;

		if (Compare.isGreaterThanOrEqual(a, b))
			return;

		// pick the pivot
		int middle = a + (b - a) / 2;
		int pivot = matrix[middle];

		// make left < pivot and right > pivot
		int i = a, j = b;
		while (Compare.isLessThanOrEqual(i, j)) {
			while (Compare.isLower(matrix[i], pivot)) {
				i++;
			}

			while (Compare.isGreater(matrix[j], pivot)) {
				j--;
			}

			if (Compare.isLessThanOrEqual(i, j)) {
				int temp = matrix[i];
				ArrayList<FieldDomain> tempDomain = components.get(i);
				matrix[i] = matrix[j];
				components.set(i, components.get(j));
				matrix[j] = temp;
				components.set(j, tempDomain);
				i++;
				j--;
			}
		}

		// recursively sort two sub parts
		if (Compare.isLower(a, j))
			sort(matrix, a, j, components);

		if (Compare.isGreater(b, i))
			sort(matrix, i, b, components);
	}
}
