package cu.uci.abcd.statistic.impl;

import java.util.ArrayList;
import java.util.List;

import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionAND;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionNOT;
import cu.uci.abcd.dataprovider.jisis.impl.search.option.OptionOR;

public class JISISQueryParser {

	private List<String> databases = new ArrayList<>(2);
	private String projection;
	private String condition;
	private Integer groups = 0;
	private boolean togroup = false;
	//private String operator;
	private String value;
	private String logicOperator;

	List<Option> options = new ArrayList<>();

	public boolean check(String query) {
		return query.toUpperCase().contains("JISIS");
	}

	public void parse(String query) {
		Integer i = 0;
		String[] tokens;
		if (query.toUpperCase().contains("JISIS")) {
			tokens = query.split("JISIS")[1].split("\\s");
		} else {
			tokens = query.split("\\s");
		}
		select(tokens, i);
	}

	private void select(String[] tokens, Integer i) {
		if (!tokens[i].toUpperCase().contains("SELECT") && i < tokens.length) {
			i++;
			select(tokens, i);
		} else {
			i++;
			getProjection(tokens, i);
		}

	}

	private void getProjection(String[] tokens, Integer i) {
		if (tokens[i].equalsIgnoreCase("TAG")) {
			i++;
			projection = tokens[i];
			i++;
			if (tokens[i].equalsIgnoreCase("FROM")) {
				i++;
				getDatabase(tokens, i);
			}
		}
	}

	private void getDatabase(String[] tokens, Integer i) {
		databases.add(tokens[i]);
		i++;
		if (tokens[i].equalsIgnoreCase("WHERE")) {
			i++;
			getConditions(tokens, i);
		} else {
			i++;
			getDatabase(tokens, i);
		}
	}

	private void getConditions(String[] tokens, Integer i) {
		
		if (tokens[i].equals("(")) {
			groups++;
			togroup = true;
			i++;
		}
		
		if (tokens[i].equalsIgnoreCase("TAG")) {
			i++;
			condition = tokens[i];
			i++;
			//operator = tokens[i];
			i++;
			value = tokens[i];
			i++;
			createOption();
			
			if (i<tokens.length && tokens[i].equals(")")) {
				togroup = false;
				i++;
			}
			if (i < tokens.length && (tokens[i].equalsIgnoreCase("AND") || tokens[i].equalsIgnoreCase("OR") || tokens[i].equalsIgnoreCase("NOR"))) {
				logicOperator = tokens[i];
				i++;
				getConditions(tokens, i);
			}
		}

	}

	public List<String> getDatabases() {
		return databases;
	}

	public String getProjection() {
		return projection;
	}

	public List<Option> getOptions() {
		return options;
	}

	private void createOption() {
		Option option;
		if (togroup) {
			if (logicOperator != null && !logicOperator.isEmpty()) {
				if (logicOperator.equalsIgnoreCase("AND")) {
					option = new OptionAND(condition, value,groups);
				} else {
					if (logicOperator.equalsIgnoreCase("OR")) {
						option = new OptionOR(condition, value,groups);
					} else {
						option = new OptionNOT(condition, value,groups);
					}
				}
			} else {
				option = new Option(condition, value, groups);
			}
		} else {
			if (logicOperator != null && !logicOperator.isEmpty()) {
				if (logicOperator.equalsIgnoreCase("AND")) {
					option = new OptionAND(condition, value);
				} else {
					if (logicOperator.equalsIgnoreCase("OR")) {
						option = new OptionOR(condition, value);
					} else {
						option = new OptionNOT(condition, value);
					}
				}
			} else {
				option = new Option(condition, value);
			}
		}
		options.add(option);

	}


}
