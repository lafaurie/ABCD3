package cu.uci.abcd.dao.management.security;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cu.uci.abcd.domain.management.security.Permission;

public interface PermissionDAO extends PagingAndSortingRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

	@Query("select p from Permission p where p.module != null ")
	public List<Permission> findAllIfModuleIsNotNull();
	
	public Permission findPermissionByPermissionName(String permissionName);

	
}
