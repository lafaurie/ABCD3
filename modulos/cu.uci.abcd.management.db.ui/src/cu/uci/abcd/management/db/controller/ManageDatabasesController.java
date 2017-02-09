package cu.uci.abcd.management.db.controller;

import java.util.List;

import cu.uci.abcd.management.db.IDatabaseManager;
import cu.uci.abos.api.ui.ViewController;


public class ManageDatabasesController implements ViewController {
    private IDatabaseManager service;

    public List<String> getDatabaseNames() {
	return service.getDatabasesList();
    }

    public void bind(IDatabaseManager service) {
	this.service = service;
    }
}
