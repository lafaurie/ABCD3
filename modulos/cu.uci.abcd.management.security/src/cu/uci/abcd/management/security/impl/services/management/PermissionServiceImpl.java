package cu.uci.abcd.management.security.impl.services.management;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import cu.uci.abcd.dao.management.security.PermissionDAO;
import cu.uci.abcd.dao.specification.PageSpecification;
import cu.uci.abcd.dao.specification.SecuritySpecification;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.security.Permission;
import cu.uci.abcd.management.security.IPermissionService;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class PermissionServiceImpl implements IPermissionService {

	private PermissionDAO permissionDAO;

	public void bindPermissionDao(PermissionDAO permissionDAO,
			Map<?, ?> properties) {
		this.permissionDAO = permissionDAO;
	}

	@Override
	public Collection<Permission> findAll() {
		return ((Collection<Permission>) permissionDAO.findAll());
	}

	@Override
	public Collection<Permission> findAllByQuery(String name) {
		return (List<Permission>) permissionDAO.findAll();
	}

	@Override
	public Page<Permission> findAll(String name, Nomenclator module, int page,
			int size, int direction, String order) {
		return permissionDAO.findAll(
				SecuritySpecification.searchPermissions(name, module),
				PageSpecification.getPage(page, size, direction, order));
	}

	@Override
	public List<Permission> findAllIfModuleIsNotNull() {
		return permissionDAO.findAllIfModuleIsNotNull();
	}

	@Override
	public Permission readPermission(Long idPermission) {
		return permissionDAO.findOne(idPermission);
	}

	@Override
	public Permission readPermissionByName(String permissionName) {
		return permissionDAO.findPermissionByPermissionName(permissionName);
	}

}
