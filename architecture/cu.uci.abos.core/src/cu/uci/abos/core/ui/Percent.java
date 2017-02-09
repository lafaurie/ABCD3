package cu.uci.abos.core.ui;

public enum Percent {
	W5(5), W10(10), W15(15), W20(20), W25(25), W30(30), W33(33), W40(40), W45(45), W50(50), W75(75),W80(80),W90(90), W100(100);
 
 private Integer value;

	private Percent(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
