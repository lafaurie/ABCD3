package cu.uci.abos.dataprovider.jisis.internals.utils;

import java.io.IOException;

import org.unesco.jisis.corelib.client.ClientDbProxy;
import org.unesco.jisis.corelib.client.ConnectionNIO;
import org.unesco.jisis.corelib.common.IConnection;
import org.unesco.jisis.corelib.exceptions.DbException;

public class ConnectionManager {
	private IConnection connection = null;
	
	private String serverAddress;
	private int serverPort;
	private String dbUser;
	private String dbPassword;
	
	private IConnection openConnection(){
		try {
			connection = ConnectionNIO.connect(serverAddress, serverPort, dbUser, dbPassword);
		} catch (DbException e) {
			System.out.println("========================================ERROR=====================================");
			e.printStackTrace();
			System.out.println("Database connection failed");
			System.out.println("==================================================================================");
		} catch (IOException e) {
			System.out.println("========================================ERROR=====================================");
			e.printStackTrace();
			System.out.println("Database connection failed");
			System.out.println("==================================================================================");
		}
	
		return connection;
	}
	
	public IConnection getConnection(){
		return (null == connection)? openConnection() : connection;
	}
	
	public ClientDbProxy getProxy(){
		return new ClientDbProxy(getConnection());
	}
	
	public void closeConnection(){
		try {
			getConnection().close();
		} catch (DbException e) {
			System.out.println("========================================ERROR=====================================");
			e.printStackTrace();
			System.out.println("Database connection failed");
			System.out.println("==================================================================================");
		}
	}
	
	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public void setConnection(IConnection connection) {
		this.connection = connection;
	}
}
