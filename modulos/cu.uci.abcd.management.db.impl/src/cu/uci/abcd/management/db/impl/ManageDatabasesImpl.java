package cu.uci.abcd.management.db.impl;

import java.util.List;
import java.util.Map;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.management.db.IDatabaseManager;

public class ManageDatabasesImpl implements IDatabaseManager {
    private IJisisDataProvider jIsisDataProvider;

    public IJisisDataProvider getjIsisDataProvider() {
	return jIsisDataProvider;
    }

    public void setjIsisDataProvider(IJisisDataProvider jIsisDataProvider) {
	this.jIsisDataProvider = jIsisDataProvider;
    }

    public List<String> getDatabasesList() {
	try {
	    return jIsisDataProvider.getDatabaseNames();
	} catch (JisisDatabaseException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public void bind(IJisisDataProvider provider, Map<?, ?> properties) {
	this.jIsisDataProvider = provider;
    }
}
