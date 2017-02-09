package cu.uci.abcd.dao.test.management.security;

import java.util.LinkedList;

import cu.uci.abcd.dao.management.security.PermissionDAO;
import cu.uci.abcd.dao.test.DaoUtil;
import cu.uci.abcd.dao.test.DataGenerator;
import cu.uci.abcd.domain.management.security.Permission;

public class PermissionDAOImpl extends DaoUtil<Permission> implements PermissionDAO {

	public PermissionDAOImpl() {
		super();
		data= new LinkedList<Permission>(DataGenerator.getInstance().getPermissions());
	}
	
}
