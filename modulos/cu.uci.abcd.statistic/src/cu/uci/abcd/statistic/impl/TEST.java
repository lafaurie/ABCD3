package cu.uci.abcd.statistic.impl;

import java.util.List;

import cu.uci.abcd.dataprovider.jisis.impl.search.option.Option;

public class TEST {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JISISQueryParser parser = new JISISQueryParser();
		String consulta = "SELECT * FROM XX WHERE X IN JISIS SELECT TAG 100 FROM MARC21 WHERE TAG 80 = 2 AND ( TAG 3000 = ? OR TAG 1212 = 20 )";
		parser.parse(consulta);
		
		System.out.println("Projection  tag --> "+parser.getProjection() );
		System.out.println("Databases --> "+parser.getDatabases().toString());
		System.out.println("LUCENE QUERY");
		List<Option> options = parser.getOptions();
		for (Option option : options) {
			System.out.println("--"+option.getField()+"--"+option.getTerm()+"--");
		}

	}

}
