package cu.uci.abcd.management.security.util;

import java.util.ArrayList;
import java.util.List;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.security.Permission;
import cu.uci.abcd.management.security.ui.controller.ProfileViewController;
import cu.uci.abos.api.ui.ViewController;

public class TreeModulePermissions {

	private List<Permission> listPermission;
	List<Permission> listPermissionsLibrary = new ArrayList<Permission>();
	List<Permission> listPermissionsSecurity = new ArrayList<Permission>();
	List<Permission> listPermissionsAdquitition = new ArrayList<Permission>();
	List<Permission> listPermissionsCataloguing = new ArrayList<Permission>();
	List<Permission> listPermissionsCirculation = new ArrayList<Permission>();
	List<Permission> listPermissionsNomenclatorAdministration = new ArrayList<Permission>();
	List<Permission> listPermissionsStadistic = new ArrayList<Permission>();

	Nomenclator libraryModule;
	Nomenclator securityModule;
	Nomenclator adquititionModule;
	Nomenclator cataloguingModule;
	Nomenclator circulationModule;
	Nomenclator nomenclatorAdministrationModule;
	Nomenclator stadisticModule;

	public ModulePermission getModulePermissionLibrary() {
		return modulePermissionLibrary;
	}

	public ModulePermission getModulePermissionSecurity() {
		return modulePermissionSecurity;
	}

	public ModulePermission getModulePermissionCirculation() {
		return modulePermissionCirculation;
	}

	public ModulePermission getModulePermissionAdquitition() {
		return modulePermissionAdquitition;
	}

	public ModulePermission getModulePermissionCataloguing() {
		return modulePermissionCataloguing;
	}

	public ModulePermission getModulePermissionNomenclatorAdministration() {
		return modulePermissionNomenclatorAdministration;
	}

	public ModulePermission getModulePermissionStadistic() {
		return modulePermissionStadistic;
	}

	ModuleCategory moduleCategoryRegister;
	ModuleCategory moduleCategoryManage;
	ModuleCategory moduleCategoryUpdate;
	ModuleCategory moduleCategoryView;
	ModuleCategory moduleCategoryDelete;
	ModuleCategory moduleCategoryConfigure;
	ModuleCategory moduleCategoryConsult;
	ModuleCategory moduleCategoryPerfil;
	ModuleCategory moduleCategoryGenerate;
	ModuleCategory moduleCategoryApprovedReject;
	ModuleCategory moduleCategoryOther;

	ModulePermission modulePermissionAdquitition;
	ModulePermission modulePermissionCataloguing;
	ModulePermission modulePermissionCirculation;
	ModulePermission modulePermissionStadistic;
	ModulePermission modulePermissionNomenclatorAdministration;
	ModulePermission modulePermissionLibrary;
	ModulePermission modulePermissionSecurity;

	public TreeModulePermissions(List<Permission> listPermission,
			ViewController controller) {
		this.setListPermission(listPermission);

		libraryModule = ((ProfileViewController) controller)
				.getAllManagementSecurityViewController().getPersonService()
				.findNomenclatorById(Nomenclator.LIBRARY_MODULE);

		securityModule = ((ProfileViewController) controller)
				.getAllManagementSecurityViewController().getPersonService()
				.findNomenclatorById(Nomenclator.SECURITY_MODULE);

		adquititionModule = ((ProfileViewController) controller)
				.getAllManagementSecurityViewController().getPersonService()
				.findNomenclatorById(Nomenclator.ADQUITITION_MODULE);

		cataloguingModule = ((ProfileViewController) controller)
				.getAllManagementSecurityViewController().getPersonService()
				.findNomenclatorById(Nomenclator.CATALOGUING_MODULE);

		circulationModule = ((ProfileViewController) controller)
				.getAllManagementSecurityViewController().getPersonService()
				.findNomenclatorById(Nomenclator.CIRCULATION_MODULE);

		nomenclatorAdministrationModule = ((ProfileViewController) controller)
				.getAllManagementSecurityViewController()
				.getPersonService()
				.findNomenclatorById(
						Nomenclator.NOMENCLATOR_ADMINISTRATION_MODULE);

		stadisticModule = ((ProfileViewController) controller)
				.getAllManagementSecurityViewController().getPersonService()
				.findNomenclatorById(Nomenclator.STATISTIC_MODULE);

		modulePermissionAdquitition = new ModulePermission(
				adquititionModule.getNomenclatorName(), 1000);
		modulePermissionCataloguing = new ModulePermission(
				cataloguingModule.getNomenclatorName(), 1000);
		modulePermissionCirculation = new ModulePermission(
				circulationModule.getNomenclatorName(), 1000);
		modulePermissionStadistic = new ModulePermission(
				stadisticModule.getNomenclatorName(), 1000);
		modulePermissionNomenclatorAdministration = new ModulePermission(
				nomenclatorAdministrationModule.getNomenclatorName(), 1000);
		modulePermissionLibrary = new ModulePermission(
				libraryModule.getNomenclatorName(), 1154);
		modulePermissionSecurity = new ModulePermission(
				securityModule.getNomenclatorName(), 842);

		for (int i = 0; i < listPermission.size(); i++) {
			if (listPermission.get(i).getModule().equals(libraryModule)) {
				listPermissionsLibrary.add(listPermission.get(i));

			} else {
				if (listPermission.get(i).getModule().equals(securityModule)) {
					listPermissionsSecurity.add(listPermission.get(i));
				} else {
					if (listPermission.get(i).getModule()
							.equals(adquititionModule)) {
						listPermissionsAdquitition.add(listPermission.get(i));
					} else {
						if (listPermission.get(i).getModule()
								.equals(cataloguingModule)) {
							listPermissionsCataloguing.add(listPermission
									.get(i));
						} else {
							if (listPermission.get(i).getModule()
									.equals(circulationModule)) {
								listPermissionsCirculation.add(listPermission
										.get(i));
							} else {
								if (listPermission
										.get(i)
										.getModule()
										.equals(nomenclatorAdministrationModule)) {
									listPermissionsNomenclatorAdministration
											.add(listPermission.get(i));
								} else {
									listPermissionsStadistic.add(listPermission
											.get(i));
								}
							}
						}
					}
				}
			}
		}

		load(modulePermissionLibrary, listPermissionsLibrary);
		load(modulePermissionSecurity, listPermissionsSecurity);
		load(modulePermissionCirculation, listPermissionsCirculation);
		load(modulePermissionAdquitition, listPermissionsAdquitition);
		load(modulePermissionCataloguing, listPermissionsCataloguing);
		load(modulePermissionNomenclatorAdministration,
				listPermissionsNomenclatorAdministration);
		load(modulePermissionStadistic, listPermissionsStadistic);
	}

	public void load(ModulePermission modulePermission,
			List<Permission> permissionList) {

		moduleCategoryRegister = new ModuleCategory("Registar");
		moduleCategoryManage = new ModuleCategory("Administrar");
		moduleCategoryUpdate = new ModuleCategory("Actualizar");
		moduleCategoryView = new ModuleCategory("Ver");
		moduleCategoryDelete = new ModuleCategory("Eliminar");
		moduleCategoryConfigure = new ModuleCategory("Configurar");
		moduleCategoryConsult = new ModuleCategory("Consultar");
		moduleCategoryPerfil = new ModuleCategory("Perfil");
		moduleCategoryGenerate = new ModuleCategory("Generar");
		moduleCategoryApprovedReject = new ModuleCategory("Aprobar/Rechazar");
		moduleCategoryOther = new ModuleCategory("Otra");

		for (int i = 0; i < permissionList.size(); i++) {
			if (permissionList.get(i).getPermissionName().contains("add")) {
				moduleCategoryRegister.addValue(permissionList.get(i)
						.getValue());
				moduleCategoryRegister.addPermission(permissionList.get(i));
			} else {
				if (permissionList.get(i).getPermissionName()
						.contains("manage")) {
					moduleCategoryManage.addValue(permissionList.get(i)
							.getValue());
					moduleCategoryManage.addPermission(permissionList.get(i));
				} else {
					if (permissionList.get(i).getPermissionName()
							.contains("update")) {
						moduleCategoryUpdate.addValue(permissionList.get(i)
								.getValue());
						moduleCategoryUpdate.addPermission(permissionList
								.get(i));
					} else {
						if (permissionList.get(i).getPermissionName()
								.contains("view")) {
							moduleCategoryView.addValue(permissionList.get(i)
									.getValue());
							moduleCategoryView.addPermission(permissionList
									.get(i));
						} else {
							if (permissionList.get(i).getPermissionName()
									.contains("delete")) {
								moduleCategoryDelete.addValue(permissionList
										.get(i).getValue());
								moduleCategoryDelete
										.addPermission(permissionList.get(i));
							} else {
								if (permissionList.get(i).getPermissionName()
										.contains("configure")) {
									moduleCategoryConfigure
											.addValue(permissionList.get(i)
													.getValue());
									moduleCategoryConfigure
											.addPermission(permissionList
													.get(i));
								} else {
									if (permissionList.get(i)
											.getPermissionName()
											.contains("consult")) {
										moduleCategoryConsult
												.addValue(permissionList.get(i)
														.getValue());
										moduleCategoryConsult
												.addPermission(permissionList
														.get(i));
									} else {
										if (permissionList.get(i)
												.getPermissionName()
												.contains("perfil")) {
											moduleCategoryPerfil
													.addValue(permissionList
															.get(i)
															.getValue());
											moduleCategoryPerfil
													.addPermission(permissionList
															.get(i));
										} else {
											if (permissionList.get(i)
													.getPermissionName()
													.contains("generate")) {
												moduleCategoryGenerate
														.addValue(permissionList
																.get(i)
																.getValue());
												moduleCategoryGenerate
														.addPermission(permissionList
																.get(i));
											} else {
												if (permissionList
														.get(i)
														.getPermissionName()
														.contains(
																"approvedReject")) {
													moduleCategoryApprovedReject
															.addValue(permissionList
																	.get(i)
																	.getValue());
													moduleCategoryApprovedReject
															.addPermission(permissionList
																	.get(i));
												} else {
													moduleCategoryOther
															.addValue(permissionList
																	.get(i)
																	.getValue());
													moduleCategoryOther
															.addPermission(permissionList
																	.get(i));
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		modulePermission.addModuleCategory(moduleCategoryManage);
		modulePermission.addModuleCategory(moduleCategoryConsult);
		modulePermission.addModuleCategory(moduleCategoryConfigure);
		modulePermission.addModuleCategory(moduleCategoryRegister);
		modulePermission.addModuleCategory(moduleCategoryUpdate);
		modulePermission.addModuleCategory(moduleCategoryView);
		modulePermission.addModuleCategory(moduleCategoryDelete);
		modulePermission.addModuleCategory(moduleCategoryPerfil);
		modulePermission.addModuleCategory(moduleCategoryGenerate);
		modulePermission.addModuleCategory(moduleCategoryApprovedReject);
		modulePermission.addModuleCategory(moduleCategoryOther);

	}

	public List<Permission> getListPermission() {
		return listPermission;
	}

	public void setListPermission(List<Permission> listPermission) {
		this.listPermission = listPermission;
	}

}
