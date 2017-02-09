package cu.uci.abcd.management.db.ui.util;
//package cu.uci.abcd.management.db.ui.utils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.unesco.jisis.corelib.common.FieldDefinitionTable;
//import org.unesco.jisis.corelib.common.FieldDefinitionTable.FieldDefinition;
//import org.unesco.jisis.corelib.common.FieldSelectionTable;
//import org.unesco.jisis.corelib.common.FieldSelectionTable.FstEntry;
//import org.unesco.jisis.corelib.common.Global;
//import org.unesco.jisis.corelib.common.WorksheetDef;
//import org.unesco.jisis.corelib.common.WorksheetDef.WorksheetField;
//
//import cu.uci.abcd.management.db.ui.contributors.DEWViewEntity;
//import cu.uci.abcd.management.db.ui.contributors.FDTViewEntity;
//import cu.uci.abcd.management.db.ui.contributors.FDType;
//import cu.uci.abcd.management.db.ui.contributors.FSTViewEntity;
//
//public class ViewEntityConverter {
//    public static List<FSTViewEntity> convertFST(FieldSelectionTable fst) {
//	List<FSTViewEntity> entries = new ArrayList<>();
//
//	for (int i = 0; i < fst.getEntriesCount(); i++) {
//	    FstEntry fstEntry = fst.getEntryByIndex(0);
//
//	    FSTViewEntity entry = new FSTViewEntity(fstEntry.getTag(), fstEntry.getName(), fstEntry.getTechnique(), fstEntry.getFormat());
//	    entries.add(entry);
//	}
//
//	return entries;
//    }
//
//    public static List<FDTViewEntity> convertFDT(FieldDefinitionTable fdt) {
//	List<FDTViewEntity> entries = new ArrayList<>();
//
//	for (int i = 0; i < fdt.getFieldsCount(); i++) {
//	    FieldDefinition fdtEntry = fdt.getFieldByIndex(i);
//
//	    FDTViewEntity entry = new FDTViewEntity(fdtEntry.getTag(), fdtEntry.getName(), getFdtType(fdtEntry.getType()), fdtEntry.hasIndicators(), fdtEntry.isRepeatable(),
//		    fdtEntry.hasFirstSubfield(), fdtEntry.getSubfields());
//
//	    entries.add(entry);
//	}
//
//	return entries;
//    }
//
//    public List<DEWViewEntity> convertDEW(FDTViewEntity fdtViewEntity, WorksheetDef wksDef) {
//	List<DEWViewEntity> entries = new ArrayList<>();
//
//	for (int i = 0; i < wksDef.getFieldsCount(); i++) {
//	    WorksheetField wksEntry = wksDef.getFieldByIndex(i);
//
//	    DEWViewEntity entry = new DEWViewEntity(fdtViewEntity, wksEntry.getSubfieldCode(), wksEntry.getDescription(), wksEntry.getDisplayControl(), wksEntry.getDefaultValue(),
//		    wksEntry.getHelpMessage(), wksEntry.getValidationFormat(), wksEntry.getPickList());
//
//	    entries.add(entry);
//	}
//
//	return entries;
//    }
//
//    private static FDType getFdtType(int type) {
//	switch (type) {
//	case Global.FIELD_TYPE_ALPHANUMERIC:
//	    return FDType.ALPHANUMERIC;
//	case Global.FIELD_TYPE_ALPHABETIC:
//	    return FDType.ALPHABETIC;
//	case Global.FIELD_TYPE_BLOB:
//	    return FDType.BLOB;
//	case Global.FIELD_TYPE_DATE:
//	    return FDType.DATE;
//	case Global.FIELD_TYPE_DOC:
//	    return FDType.DOC;
//	case Global.FIELD_TYPE_NUMERIC:
//	    return FDType.NUMERIC;
//	case Global.FIELD_TYPE_PATTERN:
//	    return FDType.PATTERN;
//	case Global.FIELD_TYPE_TIME:
//	    return FDType.TIME;
//	case Global.FIELD_TYPE_URL:
//	    return FDType.URL;
//
//	default:
//	    return null;
//	}
//    }
//}
