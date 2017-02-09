package cu.uci.abcd.dataprovider.jisis.impl.search.option;


public class Option {

	private String field;
	private String term;
	private Integer group;

	public Option(String field, String term) {
		super();
		this.field = field;
		this.term = term;
	}
	
	public Option(String field, String term, Integer group) {
		super();
		this.field = field;
		this.term = term;
		this.group = group;
	}

	public Integer getGroup() {
		return group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

}
