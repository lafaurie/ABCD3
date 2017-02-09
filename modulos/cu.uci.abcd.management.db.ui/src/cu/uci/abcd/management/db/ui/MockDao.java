package cu.uci.abcd.management.db.ui;
//package cu.uci.abcd.management.db.ui.contributors;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.LinkedList;
//import java.util.List;
//
//import org.eclipse.swt.SWT;
//
//import cu.uci.abos.widgets.grid.IGridViewEntity;
//import cu.uci.abos.widgets.grid.SortData;
//
//public class MockDao {
//
//    public static List<FDTViewEntity> mockFDTData = createMockFDTData();
//    public static List<DEWViewEntity> mockDEWData = createMockDEWData();
//
//   public static Page<FDTViewEntity> findAllFDTs(int start, int size, final SortData sortData) {
//    	 
//	if (sortData != null) {
//	    System.out.println("Ordenando por: " + sortData.columnIndex + " " + sortData.columnIndex + " " + sortData.columnHeader);
//	    Collections.sort(mockFDTData, new Comparator<FDTViewEntity>() {
//		@Override
//		public int compare(FDTViewEntity e1, FDTViewEntity e2) {
//		    switch (sortData.columnIndex) {
//		    case 0:
//			if (sortData.sortDirection == SWT.UP) {
//			    return Integer.compare(e1.getTag(), e2.getTag());
//			} else if (sortData.sortDirection == SWT.DOWN) {
//			    return -Integer.compare(e1.getTag(), e2.getTag());
//			}
//			break;
//
//		    case 1:
//			if (sortData.sortDirection == SWT.UP) {
//			    return e1.getName().compareToIgnoreCase(e2.getName());
//			} else {
//			    return -e1.getName().compareToIgnoreCase(e2.getName());
//			}
//		    case 2:
//			if (sortData.sortDirection == SWT.UP) {
//			    return Integer.compare(e1.getType(), e2.getType());
//			} else {
//			    return -Integer.compare(e1.getType(), e2.getType());
//			}
//		    case 3:
//			if (sortData.sortDirection == SWT.UP) {
//			    return Boolean.compare(e1.hasIndicators(), e2.hasIndicators());
//			} else {
//			    return -Boolean.compare(e1.hasIndicators(), e2.hasIndicators());
//			}
//		    case 4:
//			if (sortData.sortDirection == SWT.UP) {
//			    return Boolean.compare(e1.isRepeatable(), e2.isRepeatable());
//			} else {
//			    return -Boolean.compare(e1.isRepeatable(), e2.isRepeatable());
//			}
//		    case 5:
//			if (sortData.sortDirection == SWT.UP) {
//			    return Boolean.compare(e1.hasFirstSubfield(), e2.hasFirstSubfield());
//			} else {
//			    return -Boolean.compare(e1.hasFirstSubfield(), e2.hasFirstSubfield());
//			}
//		    case 6:
//			if (sortData.sortDirection == SWT.UP) {
//			    return e1.getSubfields().compareToIgnoreCase(e2.getSubfields());
//			} else {
//			    return -e1.getSubfields().compareToIgnoreCase(e2.getSubfields());
//			}
//		    }
//		    return 0;
//		}
//	    });
//	
//	int end = start + size;
//	if (end > mockFDTData.size()) {
//	    end = mockFDTData.size();
//	}
//			
//	return new Page<FDTViewEntity>(mockFDTData.subList(start, end), mockFDTData.size());
//	//return new Page<FDTViewEntity>(mockFDTData.subList(start, end), mockFDTData.size());
//    }
//	return null;
//   }
//
//	
//	
//    public static Page<DEWViewEntity> findAllWDs(int start, int size, final SortData sortData) {
//     int end = start + size;
//	if (end > mockDEWData.size()) {
//	}
//	return new Page<DEWViewEntity>(mockDEWData.subList(start, end), mockDEWData.size());
//    }
//
//    public static int getCount() {
//	return mockFDTData.size();
//    }
//
//    public static void insert(FDTViewEntity entity) {
//	mockFDTData.add(entity);
//    }
//
//    public static void update(FDTViewEntity entity) {
//
//    }
//
//    public static void delete(IGridViewEntity entity) {
//	mockFDTData.remove(entity);
//    }
//
//    private static List<FDTViewEntity> createMockFDTData() {
//	List<FDTViewEntity> mockFDTData = new LinkedList<FDTViewEntity>(Arrays.asList(new FDTViewEntity(12, "Conference main entry", FDType.ALPHANUMERIC, false, false, true, "npdz"),
//		new FDTViewEntity(24, "Title", FDType.ALPHANUMERIC, false, false, true, "z"), new FDTViewEntity(25, "Edition", FDType.ALPHANUMERIC, false, false, false, ""), new FDTViewEntity(26,
//			"Imprint", FDType.ALPHANUMERIC, false, false, false, "abc"), new FDTViewEntity(30, "Collation", FDType.ALPHANUMERIC, false, false, false, "abc"), new FDTViewEntity(44,
//			"Series", FDType.ALPHANUMERIC, false, true, false, "vz"), new FDTViewEntity(50, "Notes", FDType.ALPHANUMERIC, false, false, false, ""), new FDTViewEntity(69, "Keywords",
//			FDType.ALPHANUMERIC, false, false, false, ""), new FDTViewEntity(70, "Personal Authors", FDType.ALPHANUMERIC, false, true, false, ""), new FDTViewEntity(71,
//			"Corporate Bodies", FDType.ALPHANUMERIC, false, true, false, ""), new FDTViewEntity(72, "Meetings", FDType.ALPHANUMERIC, false, true, false, "npdz"), new FDTViewEntity(74,
//			"Added Title", FDType.ALPHANUMERIC, false, true, false, "z"), new FDTViewEntity(76, "Other languages titles", FDType.ALPHANUMERIC, false, true, false, "z")));
//	return mockFDTData;
//    }
//
//  private static List<DEWViewEntity> createMockDEWData() {
//	DEWViewEntity tag12 = new DEWViewEntity(mockFDTData.get(0), "", "", "", "", "Enter Conference title^nnumber^pplace^ddate with no punctuation before or after subfields", "", "");
//	DEWViewEntity tag24 = new DEWViewEntity(mockFDTData.get(1), "", "", "", "", "Enter title in english^aPlace of  publication^bPublisher^cDate", "", "");
//	DEWViewEntity tag25 = new DEWViewEntity(mockFDTData.get(2), "", "", "", "", "", "", "");
//	DEWViewEntity tag26 = new DEWViewEntity(mockFDTData.get(3), "", "", "", "", "", "", "");
//	DEWViewEntity tag30 = new DEWViewEntity(mockFDTData.get(4), "", "", "", "", "", "", "");
//	DEWViewEntity tag70 = new DEWViewEntity(mockFDTData.get(8), "", "", "", "", "", "", "");
//
//	DEWViewEntity tag12_1 = new DEWViewEntity(new FDTViewEntity(71, "Name of meeting", FDType.ALPHANUMERIC, false, false, false, ""), "$*", "", "", "", "", "", "");
//	DEWViewEntity tag12_2 = new DEWViewEntity(new FDTViewEntity(72, "Number of meeting", FDType.ALPHANUMERIC, false, false, false, ""), "$n", "", "", "", "", "", "");
//	DEWViewEntity tag12_3 = new DEWViewEntity(new FDTViewEntity(73, "Place of meeting", FDType.ALPHANUMERIC, false, false, false, ""), "$p", "", "", "", "", "", "");
//	DEWViewEntity tag12_4 = new DEWViewEntity(new FDTViewEntity(74, "Date of meeting", FDType.ALPHANUMERIC, false, false, false, ""), "$d", "", "", "", "", "", "");
//	DEWViewEntity tag12_5 = new DEWViewEntity(new FDTViewEntity(75, "Geographic subdivision", FDType.ALPHANUMERIC, false, false, false, ""), "$z", "", "", "", "", "", "");
//
//	DEWViewEntity tag24_1 = new DEWViewEntity(new FDTViewEntity(76, "Title", FDType.ALPHANUMERIC, false, false, false, ""), "$*", "", "", "", "", "", "");
//	DEWViewEntity tag24_2 = new DEWViewEntity(new FDTViewEntity(75, "Geographic subdivision", FDType.ALPHANUMERIC, false, false, false, ""), "$z", "", "", "", "", "", "");
//
//	DEWViewEntity tag26_1 = new DEWViewEntity(new FDTViewEntity(77, "Place of publication", FDType.ALPHANUMERIC, false, false, false, ""), "$a", "", "", "", "", "", "");
//	DEWViewEntity tag26_2 = new DEWViewEntity(new FDTViewEntity(78, "Publisher", FDType.ALPHANUMERIC, false, false, false, ""), "$b", "", "", "", "", "", "");
//	DEWViewEntity tag26_3 = new DEWViewEntity(new FDTViewEntity(79, "Date of publication", FDType.ALPHANUMERIC, false, false, false, ""), "$c", "", "", "", "", "", "");
//
//	DEWViewEntity tag30_1 = new DEWViewEntity(new FDTViewEntity(75, "Extent of item", FDType.ALPHANUMERIC, false, false, false, ""), "$a", "", "", "", "", "", "");
//	DEWViewEntity tag30_2 = new DEWViewEntity(new FDTViewEntity(75, "Other phisical details", FDType.ALPHANUMERIC, false, false, false, ""), "$b", "", "", "", "", "", "");
//	DEWViewEntity tag30_3 = new DEWViewEntity(new FDTViewEntity(75, "Dimensions", FDType.ALPHANUMERIC, false, false, false, ""), "$c", "", "", "", "", "", "");
//
//
////	tag12.addChildren(Arrays.asList(tag12_1, tag12_2, tag12_3, tag12_4, tag12_5));
////	tag24.addChildren(Arrays.asList(tag24_1, tag24_2));
////	tag26.addChildren(Arrays.asList(tag26_1, tag26_2, tag26_3));
////	tag30.addChildren(Arrays.asList(tag30_1, tag30_2, tag30_3));
//
//	return Arrays.asList(tag12, tag24, tag25, tag26, tag30, tag70, tag12, tag24, tag25, tag26, tag30, tag70, tag12, tag24, tag25, tag26, tag30, tag70, tag12, tag24, tag25, tag26, tag30, tag70,
//		tag12, tag24, tag25, tag26, tag30, tag70, tag12, tag24, tag25, tag26, tag30, tag70);
//	
//  } 
//}
//
//
