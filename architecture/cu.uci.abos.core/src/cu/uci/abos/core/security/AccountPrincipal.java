package cu.uci.abos.core.security;


import java.io.Serializable;
import java.security.Principal;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import cu.uci.abos.core.domain.UserDetails;

public class AccountPrincipal implements Principal, Serializable, UserDetails {
	private static final long serialVersionUID = 1L;
	private String accountName;
	private SecurityDataStore dataStore;

	public AccountPrincipal(String accountName) {
		this.accountName = accountName;
		this.dataStore = new SecurityDataStoreImpl();
	}

	public String getName() {
		return this.accountName;
	}

	public String toString() {
		return ("AccountPrincipal: " + this.accountName);
	}

	public SecurityDataStore getDataStore() {
		return dataStore;
	}

	public void setDataStore(SecurityDataStore dataStore) {
		this.dataStore = dataStore;
	}

	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (this == o)
			return true;

		if (!(o.getClass().isAssignableFrom(AccountPrincipal.class)))
			return false;
		AccountPrincipal that = (AccountPrincipal) o;

		if (this.getName().equals(that.getName()))
			return true;
		return false;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		return result;
	}

	public void clear() {
		dataStore.clearSecurityDataStore();
		dataStore = null;
	}

	@Override
	public Image getPhoto() {
		return (Image) this.getByKey("image");
	}

	@Override
	public Object getByKey(String key) {
		return this.getDataStore().getSecurityDataObject(key);
	}

	@Override
	public String getUserName() {
		return getName();
	}

	@Override
	public List<?> getPermission() {
		return (List<?>) this.getByKey("permissions");
	}

	public void setPermission(List<?> permissions) {
		getDataStore().putSecurityDataObject("permissions", permissions);
	}

	public void setPhoto(Image photo) {
		getDataStore().putSecurityDataObject("image", photo);
	}
	
	public void setObject(String key, Object object) {
		getDataStore().putSecurityDataObject(key, object);
	}

}