package cu.uci.abcd.management.db.ui;

import java.util.ArrayList;
import java.util.List;



public enum FDType {

	ALPHANUMERIC(1), ALPHABETIC(2), NUMERIC(3), PATTERN(4), DATE(5), TIME(6), BLOB(7), URL(8), DOC(9);

	private int id;
	private static List<FDType> aux;
	
	private FDType(int id) {
		this.id = id;
	}

	public Long getId() {
		return new Long(this.id);
	}

	public String getName() {
		return this.name();
	}
	
	public static List<FDType> FDTValues(){
		aux = new ArrayList<>();
		for (int i = 0; i < FDType.values().length; i++) {
			aux.add(FDType.values()[i]);
		}
		return aux;
	}
}