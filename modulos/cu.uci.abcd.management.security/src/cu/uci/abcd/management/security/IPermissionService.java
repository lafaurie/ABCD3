package cu.uci.abcd.management.security;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.security.Permission;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public interface IPermissionService {

	public Collection<Permission> findAll();

	public Collection<Permission> findAllByQuery(String name);

	public Page<Permission> findAll(String name, Nomenclator module, int page,
			int size, int direction, String order);

	public List<Permission> findAllIfModuleIsNotNull();

	public Permission readPermission(Long idPermission);

	public Permission readPermissionByName(String permissionName);
}
