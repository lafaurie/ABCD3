package cu.uci.abcd.management.db.ui;

public enum Type {	
	ALPHANUMERIC("ALPHANUMERIC"),ALPHEBETIC("ALPHEBETIC"),NUMERIC("NUMERIC"),
	PATTERN("PATTERN"),DATE("DATE"),TIME("TIME"),BLOB("BLOB"),URL("URL"),DOC("DOC");
	private String value;

	private Type(String s) {
		value = s;
	}

	public String toString() {
		return value;
	}
};
