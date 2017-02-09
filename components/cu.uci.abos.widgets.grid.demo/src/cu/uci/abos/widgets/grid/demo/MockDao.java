package cu.uci.abos.widgets.grid.demo;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;

import cu.uci.abos.widgets.grid.IGridViewEntity;
import cu.uci.abos.widgets.grid.SortData;

public class MockDao {

	public static List<FDTViewEntity> mockFDTData = createMockData();
	
	public static Page<FDTViewEntity> findAll(int start, int size, final SortData sortData) {
		
		if(sortData != null) {
			System.out.println("Ordenando por: " + sortData.columnIndex + " " + sortData.columnIndex + " " + sortData.columnHeader);
			Collections.sort(mockFDTData, new Comparator<FDTViewEntity>() {
				@Override
				public int compare(FDTViewEntity e1, FDTViewEntity e2) {						
					switch(sortData.columnIndex) {
						case 0: 
							if(sortData.sortDirection == SWT.UP) {
								return Integer.compare(e1.getTag(), e2.getTag());
							}
							else if(sortData.sortDirection == SWT.DOWN) {
								return -Integer.compare(e1.getTag(), e2.getTag());
							}
							break;
							
						case 1:
							if(sortData.sortDirection == SWT.UP) {
								return e1.getName().compareToIgnoreCase(e2.getName());
							}
							else {
								return -e1.getName().compareToIgnoreCase(e2.getName());
							}
						case 2:
							if(sortData.sortDirection == SWT.UP) {
								return Integer.compare(e1.getType(), e2.getType());
							}
							else {
								return -Integer.compare(e1.getType(), e2.getType());
							}
						case 3:
							if(sortData.sortDirection == SWT.UP) {
								return Boolean.compare(e1.hasIndicators(), e2.hasIndicators());
							}
							else {
								return -Boolean.compare(e1.hasIndicators(), e2.hasIndicators());
							}							
						case 4:
							if(sortData.sortDirection == SWT.UP) {
								return Boolean.compare(e1.isRepeatable(), e2.isRepeatable());
							}
							else {
								return -Boolean.compare(e1.isRepeatable(), e2.isRepeatable());
							}							
						case 5:
							if(sortData.sortDirection == SWT.UP) {
								return Boolean.compare(e1.hasFirstSubfield(), e2.hasFirstSubfield());
							}
							else {
								return -Boolean.compare(e1.hasFirstSubfield(), e2.hasFirstSubfield());
							}
						case 6:
							if(sortData.sortDirection == SWT.UP) {
								return e1.getSubfields().compareToIgnoreCase(e2.getSubfields());
							}
							else {
								return -e1.getSubfields().compareToIgnoreCase(e2.getSubfields());
							}						
					}						
					return 0;
				}					
			});
		}		
		int end = start + size;
		if(end > mockFDTData.size()) {
			end = mockFDTData.size();
		}
		return new Page<FDTViewEntity>(mockFDTData.subList(start, end), mockFDTData.size());
	}
	
	public static int getCount() {
		return mockFDTData.size();
	}
	
	public static void insert(FDTViewEntity entity) {
		mockFDTData.add(entity);
	}
	
	public static void update(FDTViewEntity entity) {
		
	}
	
	public static void delete(IGridViewEntity entity) {
		mockFDTData.remove(entity);
	}

	private static List<FDTViewEntity> createMockData() {
    	List<FDTViewEntity> mockData = new LinkedList<>(Arrays.asList(
	    	new FDTViewEntity(0, "nombre", FDType.ALPHANUMERIC, true, false, true, "ab"),
			new FDTViewEntity(1, "apellidos", FDType.ALPHANUMERIC, false, true, false, "ab"),
			new FDTViewEntity(2, "identificación", FDType.ALPHABETIC, true, false, true, "ab"),
			new FDTViewEntity(3, "sexo", FDType.NUMERIC, true, false, true, "ab"),
			new FDTViewEntity(0, "a", FDType.ALPHANUMERIC, true, false, true, "ab"),
			new FDTViewEntity(1, "b", FDType.ALPHANUMERIC, false, true, false, "ab"),
			new FDTViewEntity(2, "c", FDType.ALPHABETIC, true, false, true, "ab"),
			new FDTViewEntity(3, "d", FDType.NUMERIC, true, false, true, "ab"),
			new FDTViewEntity(0, "e", FDType.ALPHANUMERIC, true, false, true, "ab"),
			new FDTViewEntity(1, "f", FDType.ALPHANUMERIC, false, true, false, "ab"),
			new FDTViewEntity(2, "g", FDType.ALPHABETIC, true, false, true, "ab"),
			new FDTViewEntity(3, "h", FDType.NUMERIC, true, false, true, "ab"),
			new FDTViewEntity(0, "i", FDType.ALPHANUMERIC, true, false, true, "ab"),
			new FDTViewEntity(1, "j", FDType.ALPHANUMERIC, false, true, false, "ab"),
			new FDTViewEntity(2, "k", FDType.ALPHABETIC, true, false, true, "ab"),
			new FDTViewEntity(3, "l", FDType.NUMERIC, true, false, true, "ab"),
			new FDTViewEntity(0, "m", FDType.ALPHANUMERIC, true, false, true, "ab"),
			new FDTViewEntity(1, "n", FDType.ALPHANUMERIC, false, true, false, "ab"),
			new FDTViewEntity(2, "o", FDType.ALPHABETIC, true, false, true, "ab"),
			new FDTViewEntity(3, "p", FDType.NUMERIC, true, false, true, "ab"),
			new FDTViewEntity(0, "q", FDType.ALPHANUMERIC, true, false, true, "ab"),
			new FDTViewEntity(1, "r", FDType.ALPHANUMERIC, false, true, false, "ab"),
			new FDTViewEntity(2, "s", FDType.ALPHABETIC, true, false, true, "ab"),
			new FDTViewEntity(3, "t", FDType.NUMERIC, true, false, true, "ab"),
			new FDTViewEntity(0, "u", FDType.ALPHANUMERIC, true, false, true, "ab"),
			new FDTViewEntity(1, "v", FDType.ALPHANUMERIC, false, true, false, "ab"),
			new FDTViewEntity(2, "w", FDType.ALPHABETIC, true, false, true, "ab"),
			new FDTViewEntity(3, "x", FDType.NUMERIC, true, false, true, "ab"),
			new FDTViewEntity(0, "y", FDType.ALPHANUMERIC, true, false, true, "ab"),
			new FDTViewEntity(1, "z", FDType.ALPHANUMERIC, false, true, false, "ab"),
			new FDTViewEntity(2, "za", FDType.ALPHABETIC, true, false, true, "ab"),
			new FDTViewEntity(3, "zb", FDType.NUMERIC, true, false, true, "ab"),
			new FDTViewEntity(0, "zc", FDType.ALPHANUMERIC, true, false, true, "ab"),
			new FDTViewEntity(1, "ze", FDType.ALPHANUMERIC, false, true, false, "ab"),
			new FDTViewEntity(2, "zf", FDType.ALPHABETIC, true, false, true, "ab"),
			new FDTViewEntity(3, "zg", FDType.NUMERIC, true, false, true, "ab")
	    ));
    	
    	return mockData;
	}
}
