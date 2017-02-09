package cu.uci.abos.jisis.conection;

import org.unesco.jisis.corelib.client.ClientDbProxy;
import org.unesco.jisis.corelib.common.IConnection;

public interface JISISConection {
	
	public IConnection getConnection();

	public ClientDbProxy getProxy();

	public void closeConnection();

}
