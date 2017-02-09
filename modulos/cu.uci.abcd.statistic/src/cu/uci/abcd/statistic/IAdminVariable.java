package cu.uci.abcd.statistic;

import java.util.Collection;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.statistic.Variable;

/**
 * @author Erdin Espinosa González
 *
 */
public interface IAdminVariable {
	
	public Collection<Variable> findAllVariable();
	
	public Collection<Variable> findVariableByDatabase(String databaseName, String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException ;
	
	public Variable addVariable (Variable variable );
		
	public Variable  viewVariable (Long idVariable );
		
	public Variable  editVariable (Long idVariable );
		
	public void deleteVariable (Long idVariable );
	
	public Page<Variable > FindAllVariable(String databaseName, String variableheader, String field, int page, int size, int direction, String orderByString);
	
}
