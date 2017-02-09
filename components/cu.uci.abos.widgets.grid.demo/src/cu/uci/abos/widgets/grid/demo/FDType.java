package cu.uci.abos.widgets.grid.demo;

import cu.uci.abos.widgets.ncombo.INomenclator;

public enum FDType implements INomenclator {

	ALPHANUMERIC(1), ALPHABETIC(2), NUMERIC(3), PATTERN(4), DATE(5), TIME(6), BLOB(7), URL(8), DOC(9);

	private int id;
	
	private FDType(int id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return new Long(this.id);
	}

	@Override
	public String getName() {
		return this.name();
	}
}