package cu.uci.abcd.statistic.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.unesco.jisis.corelib.common.FieldDefinitionTable;

import cu.uci.abcd.dao.specification.StatisticSpecification;
import cu.uci.abcd.dao.statistic.VariableDAO;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.statistic.Variable;
import cu.uci.abcd.statistic.IAdminVariable;

/**
 * @author Dayana Rivera Muñoz
 * 
 */
public class VariableImpl implements IAdminVariable {
	private VariableDAO variableDAO;
    private IJisisDataProvider jisisDataProvider;
	

	public void bind(IJisisDataProvider dataProvider, Map<?, ?> properties) {
		this.jisisDataProvider = dataProvider;
	}

	public void bind(VariableDAO variableDAO, Map<?, ?> properties) {
		this.variableDAO = variableDAO;
	}

	@Override
	public Page<Variable> FindAllVariable(String databaseName, String variableheader, String field, int page, int size, int direction, String orderByString) {
		return variableDAO.findAll(StatisticSpecification.searchVariable(databaseName, variableheader, field), getPage(page, size, direction, orderByString));
	}

	@Override
	public Variable addVariable(Variable variable) {
		return variableDAO.save(variable);
	}

	@Override
	public void deleteVariable(Long idVariable) {
		variableDAO.delete(variableDAO.findOne(idVariable));
	}

	
	@Override
	public Variable editVariable(Long idVariable) {
		//FIXME OIGRES REPETIDO
		return variableDAO.findOne(idVariable);
	}

	@Override
	public Variable viewVariable(Long idVariable) {
		//FIXME OIGRES REPETIDO
		return variableDAO.findOne(idVariable);
	}

	@Override
	public Collection<Variable> findAllVariable() {
		return (Collection<Variable>) variableDAO.findAll();
	}

	@Override
	public Collection<Variable> findVariableByDatabase(String databaseName,
			String libraryIsisDatabasesHomeFolder) throws JisisDatabaseException {
		FieldDefinitionTable definitionTable= jisisDataProvider.getFieldDefinitionTable(databaseName, libraryIsisDatabasesHomeFolder);
		Collection<Variable> collection= new ArrayList<>();
		
		for (int i = 0; i < definitionTable.getFieldsCount(); i++) {
			Variable variable = new Variable();
			variable.setDatabaseName(databaseName);
			variable.setField(definitionTable.getFieldByIndex(i).getName());
			variable.setOutputFormat("v"+definitionTable.getFieldByIndex(i).getTag());
			collection.add(variable);
		}
		return collection;
	}
	
	static Pageable getPage(int page, int size, int direction, String orderByString) {
		return new PageRequest(page, size, sort(direction, orderByString));
	}

	static Sort sort(int direction, String orderByString) {
		return new Sort(new Sort.Order((direction == 1024) ? Sort.Direction.ASC : Sort.Direction.DESC, orderByString));
	}

	

}
