package cu.uci.abos.dataprovider.jisis.internal.utils;

import java.io.IOException;

import org.unesco.jisis.corelib.client.ConnectionNIO;
import org.unesco.jisis.corelib.client.ConnectionPool;
import org.unesco.jisis.corelib.common.IConnection;
import org.unesco.jisis.corelib.exceptions.DbException;

public class JisisUtil {
	public static IConnection getConnection(String dbServer, int dbPort,
			String username, String password) {
		IConnection connection = null;
		int i = ConnectionPool.findConnection(dbServer, dbPort);
		if (i == -1) {
			try {
				connection = ConnectionNIO.connect(dbServer, dbPort, username,
						password);
			} catch (DbException | IOException e) {
				e.printStackTrace();
			}
			ConnectionPool.addConnection(connection);
		} else {
			connection = ConnectionPool.getConnections().get(i).getConnection();

		}
		return connection;
	}

}
