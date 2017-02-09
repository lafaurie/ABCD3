/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.ldap.ejemploLDAPNovell;

import java.util.logging.Logger;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPResponse;
import com.novell.ldap.LDAPResponseQueue;

/**
 * 
 * @author Ing. Ray Williams Robinson Valiente
 */
public final class LDAPUtil {

	public static final Logger logger = Logger.getLogger("LDAPUtil.class");
	//Este era el logger que utilizaron del servidor Apache.
	//public static final Logger logger = Logger.getLogger(LDAPUtil.class);

	public boolean checkDomainCredentials(String host, String domainName, String domainUser, String domainPassword)
			throws Exception {
		LDAPConnection connection = new LDAPConnection();
		LDAPResponseQueue responseQueue = null;
		byte[] password = domainPassword.getBytes("UTF8");
		String loginDN = String.format("%s@%s", domainUser, domainName);
		// connect to the server
		connection.connect(host, LDAPConnection.DEFAULT_PORT);
		// bind to the server. Asynchronous bind is used to
		// get the response and the message from the response
		responseQueue = connection.bind(LDAPConnection.LDAP_V3, loginDN, password, (LDAPResponseQueue) null);
		// get the bind response
		LDAPResponse response = (LDAPResponse) responseQueue.getResponse();
		// get the return code from the response
		int bindResult = response.getResultCode();
		boolean result;
		if (bindResult != LDAPException.SUCCESS) {
			// get the error message from the response
			String message = response.getErrorMessage();
			logger.info(String.format("Binding error ocurred: %s\n", message));
			result = false;
		} else {
			result = true;
		}
		connection.disconnect();
		return result;
	}
}
