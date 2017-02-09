package cu.uci.abcd.management.security.util;

import java.util.ArrayList;
import java.util.List;

import cu.uci.abcd.domain.management.security.Permission;

public class ModuleCategory {

	private String title;
	private List<String> listValues;
	private List<Permission> listPermissions;
	
	public List<Permission> getListPermissions() {
		return listPermissions;
	}

	public void setListPermissions(List<Permission> listPermissions) {
		this.listPermissions = listPermissions;
	}

	public ModuleCategory(String title) {
		this.title = title;
		listValues = new ArrayList<String>();
		listPermissions =  new ArrayList<Permission>();
	}

	public ModuleCategory(String title, List<String> listValues) {
		this.title = title;
		this.listValues = listValues;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getListValues() {
		return listValues;
	}

	public void setListValues(List<String> listValues) {
		this.listValues = listValues;
	}
	
	public void addValue(String value){
		listValues.add(value);
	}
	
	public void addPermission(Permission permision){
		listPermissions.add(permision);
	}
	
	public void clean(){
		setListPermissions(null);
		setListValues(null);
	}

	
}
