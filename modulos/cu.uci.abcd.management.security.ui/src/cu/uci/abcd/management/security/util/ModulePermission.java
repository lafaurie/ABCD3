package cu.uci.abcd.management.security.util;

import java.util.ArrayList;
import java.util.List;

import cu.uci.abcd.domain.management.security.Permission;

public class ModulePermission {
	
	private String title;
	private List<ModuleCategory> listModuleCategory;
	private int height;

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ModulePermission(String title, int height) {
		this.title = title;
		listModuleCategory = new ArrayList<ModuleCategory>();
		this.height = height;
	}

	public ModulePermission(String title, List<ModuleCategory> listModuleCategory) {
		this.title = title;
		this.listModuleCategory = listModuleCategory;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ModuleCategory> getListModuleCategory() {
		return listModuleCategory;
	}

	public void setListModuleCategory(List<ModuleCategory> listModuleCategory) {
		this.listModuleCategory = listModuleCategory;
	}
	
	public void addModuleCategory(ModuleCategory moduleCategory) {
		if(moduleCategory.getListPermissions().size()>0){
			listModuleCategory.add(moduleCategory);
		}
		
	}
	
	public List<Permission> allPermission(){
		List<Permission> list = new ArrayList<Permission>();
		for (int i = 0; i < listModuleCategory.size(); i++) {
			for (int j = 0; j < listModuleCategory.get(i).getListPermissions().size(); j++) {
				list.add(listModuleCategory.get(i).getListPermissions().get(j));
			}
		}
		return list;
	}

}
