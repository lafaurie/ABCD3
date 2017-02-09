package cu.uci.abcd.management.db.impl;

import java.util.List;
import java.util.Map;

import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.management.db.IDatabaseManager;
import cu.uci.abos.core.util.SecurityUtils;

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
		Library library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
	    return jIsisDataProvider.getDatabaseNames(library.getIsisDefHome());
	} catch (JisisDatabaseException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public void bind(IJisisDataProvider provider, Map<?, ?> properties) {
	this.jIsisDataProvider = provider;
    }
}
