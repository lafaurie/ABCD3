package cu.uci.abos.dataprovider.advanced;

import java.io.Serializable;

import cu.uci.abos.dataprovider.exception.DataProviderException;


/**
 * Expone una exaustiva gama de operaciones sobre un almacen de datos
 * 
 * @author Team
 * @version 0.0.1
 * @since 0.0.1
 */
public interface IDataBase {

	/**
	 * ?
	 * @param params
	 * @return
	 * @throws DataProviderException
	 */
	public <T> boolean create(Object... params) throws DataProviderException;

	/**
	 * ?
	 * @return
	 * @throws DataProviderException
	 */
	public Object getID() throws DataProviderException;

	/**
	 * 
	 * @return
	 * @throws DataProviderException
	 */
	public String getDBName() throws DataProviderException;

	/**
	 * ?
	 * @param params
	 * @throws DataProviderException
	 */
	public <T extends Serializable> void getDB(Object... params)
			throws DataProviderException;

	/**
	 * ?
	 * @return
	 * @throws DataProviderException
	 */
	public int unlockDB() throws DataProviderException;

	/**
	 * ?
	 * @return
	 * @throws DataProviderException
	 */
	public boolean isDBLocked() throws DataProviderException;

	/**
	 * ?
	 * @return
	 * @throws DataProviderException
	 */
	public String getDBConfig() throws DataProviderException;

	/**
	 * ?
	 * @param s
	 * @return
	 * @throws DataProviderException
	 */
	public boolean saveDBConfig(String s) throws DataProviderException;

	/**
	 * ?
	 * @throws DataProviderException
	 */
	public void resetInfo() throws DataProviderException;

	/**
	 * ?
	 * @return
	 * @throws DataProviderException
	 */
	public int unlockAllRecords() throws DataProviderException;

	/**
	 * ?
	 * @return
	 * @throws DataProviderException
	 */
	public long getLastMfn() throws DataProviderException;

	/**
	 * ?
	 * @return
	 * @throws DataProviderException
	 */
	public String[] getFstNames() throws DataProviderException;

	/**
	 * ?
	 * @param s
	 * @return
	 * @throws DataProviderException
	 */
	public boolean removeFst(String s) throws DataProviderException;

	/**
	 * ?
	 * @return
	 * @throws DataProviderException
	 */
	public String getDefaultFstName() throws DataProviderException;

	/**
	 * ?
	 * @return
	 * @throws DataProviderException
	 */
	public String getDefaultPrintFormat() throws DataProviderException;

	/**
	 * ?
	 * @return
	 * @throws DataProviderException
	 */
	public String getDefaultPrintFormatName() throws DataProviderException;

	/**
	 * ?
	 * @param s
	 * @return
	 * @throws DataProviderException
	 */
	public String getPrintFormat(String s) throws DataProviderException;

	/**
	 * ?
	 * @param s
	 * @return
	 * @throws DataProviderException
	 */
	public String getPrintFormatAnsi(String s) throws DataProviderException;

	/**
	 * ?
	 * @return
	 * @throws DataProviderException
	 */
	public String[] getPrintFormatNames() throws DataProviderException;

	/**
	 * ?
	 * @param s
	 * @return
	 * @throws DataProviderException
	 */
	public boolean removePrintFormat(String s) throws DataProviderException;

	/**
	 * ?
	 * @param s
	 * @throws DataProviderException
	 */
	public void saveDefaultPrintFormat(String s) throws DataProviderException;

	/**
	 * ?
	 * @param s
	 * @param s1
	 * @return
	 * @throws DataProviderException
	 */
	public boolean savePrintFormat(String s, String s1)
			throws DataProviderException;

	/**
	 * ?
	 * @return
	 * @throws DataProviderException
	 */
	public String[] getWorksheetNames() throws DataProviderException;

	/**
	 * ?
	 * @param s
	 * @return
	 * @throws DataProviderException
	 */
	public boolean removeWorksheetDef(String s) throws DataProviderException;

	/**
	 * ?
	 * @return
	 * @throws DataProviderException
	 */
	public boolean buildIndex() throws DataProviderException;

	/**
	 * ?
	 * @return
	 * @throws DataProviderException
	 */
	public boolean clearIndex() throws DataProviderException;

	/**
	 * ?
	 * @return
	 * @throws DataProviderException
	 */
	public boolean reIndex() throws DataProviderException;

	/**
	 * ?
	 * @param s
	 * @return
	 * @throws DataProviderException
	 */
	public long[] search(String s) throws DataProviderException;

	/**
	 * ?
	 * @param s
	 * @return
	 * @throws DataProviderException
	 */
	public long[] searchLucene(String s) throws DataProviderException;

}
