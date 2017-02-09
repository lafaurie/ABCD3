package cu.uci.abos.core.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SecurityDataStoreImpl implements SecurityDataStore, Serializable {

	private static final long serialVersionUID = -3754295890256273284L;
	
	private Map<String, Object> securityDataStore;

	public SecurityDataStoreImpl() {
		super();
		securityDataStore = new HashMap<>();
	}

	@Override
	public Object putSecurityDataObject(String dataObjectKey, Object dataObject) {
		return securityDataStore.put(dataObjectKey, dataObject);
	}

	@Override
	public Object getSecurityDataObject(String dataObjectKey) {
		return securityDataStore.get(dataObjectKey);
	}

	@Override
	public Object removeSecurityDataObject(String dataObjectKey) {
		return securityDataStore.remove(dataObjectKey);
	}

	@Override
	public void clearSecurityDataStore() {
		securityDataStore.clear();
	}
}
