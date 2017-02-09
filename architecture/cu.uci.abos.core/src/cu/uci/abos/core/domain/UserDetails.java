package cu.uci.abos.core.domain;

import java.util.List;

import org.eclipse.swt.graphics.Image;

public interface UserDetails {
	
	public Image getPhoto();
	
	public String getUserName();
	
    public List<?>getPermission();
    
    public Object getByKey(String key);
}
