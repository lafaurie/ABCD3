package cu.uci.abcd.management.security.ui.model;

import org.eclipse.swt.widgets.Button;

import java.util.List;

import cu.uci.abcd.domain.management.security.Permission;
import cu.uci.abcd.domain.management.security.Profile;

public class Item {
	private Profile profile;
	private Button checked;

	public Button getChecked() {
		return checked;
	}

	public void setChecked(Button checked) {
		this.checked = checked;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	private int saltos;

	// private String permissions;

	public int getSaltos() {
		return saltos;
	}

	public void setSaltos(int saltos) {
		this.saltos = saltos;
	}

	public Item(Profile profile, Button checked) {
		this.profile = profile;
		saltos = 1;
		this.checked = checked;
	}

	public Item(Profile profile) {
		this.profile = profile;
		saltos = 1;
	}

	public String Order(int limite) {
		String assignedPermissions = "";
		List<Permission> permissionList = profile.getAsignedPermissions();
		
			
		
		// int limite = 90;
		String subparte = "";
		int p = 0;
		boolean aaa1;
		for (int i = 0; i < permissionList.size(); i++) {
			
			
			if (p + permissionList.get(i).getValue().length() > limite) {
				p = 0;
				if( subparte !=""){
				subparte = subparte + "\n" + permissionList.get(i).getValue();
				}else{
					subparte = subparte + permissionList.get(i).getValue();
				}
				aaa1 = true;
			} else {
				subparte = subparte + permissionList.get(i).getValue() + ", ";
				p = subparte.length();
				aaa1 = false;
			}
			if (aaa1 == true || ((aaa1 == false) && (i + 1 == permissionList.size()))) {
				assignedPermissions = assignedPermissions + subparte;
				subparte = ", ";
				saltos++;
			}

		}
		if( assignedPermissions.charAt(assignedPermissions.length()-1)==',' || assignedPermissions.charAt(assignedPermissions.length()-2)==','  ){
			return assignedPermissions.substring(0, assignedPermissions.length()-2) + ".";
		}else{
			return assignedPermissions + ".";
		}
		
	}

}
