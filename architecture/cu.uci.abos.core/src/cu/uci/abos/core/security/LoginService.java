package cu.uci.abos.core.security;

import java.util.List;

/**
 * 
 * @author Eberto
 * 
 */
public interface LoginService {
	/**
	 * 
	 * @param user
	 * @param password
	 * @throws LoginException
	 */
	public void login(String user, String password, Object... params) throws LoginException;

	/**
	 * 
	 * @param listener
	 */
	public void onLoginChanged(LoginChangedListener listener);

	/**
	 * 
	 * @return
	 */
	public boolean isLoggedIn();
	
	public void fireLoginChangedEvent();

	/**
	 * 
	 * @throws LoginException
	 */
	public void logout() throws LoginException;

	/**
	 * 
	 */
	public void clearEvents();

	/**
	 * 
	 * @return
	 */
	public boolean isEnabled();

	/**
	 * 
	 * @return
	 */
	public int getSessionTimeout();

	/**
	 * Get the
	 * 
	 * @return
	 */
	public AccountPrincipal getPrincipal();
	
	
	public List<?> loadParams();

}