package cu.uci.abos.dataprovider.jisis.services;

import java.util.List;

import cu.uci.abos.dataprovider.advanced.IDataBase;
import cu.uci.abos.dataprovider.jisis.domain.Esquema;
import cu.uci.abos.dataprovider.jisis.domain.Esquema.Coleccion.Campo;
import cu.uci.abos.dataprovider.jisis.domain.Esquema.Coleccion.HojaTrabajo;
import cu.uci.abos.dataprovider.jisis.domain.Esquema.Coleccion.Indice;
import cu.uci.abos.dataprovider.jisis.domain.Esquema.Coleccion.PresentacionRegistro;
import cu.uci.abos.dataprovider.jisis.domain.Esquema.UserInfo;
import cu.uci.abos.dataprovider.jisis.domain.Registro;

public interface IJisisAdapter extends IDataBase {

	/**
	 * 
	 * @return
	 */
	public String getDbHome();

	/**
	 * 
	 * @param userinfo
	 * @return
	 */
	public int lockDatabase(UserInfo userinfo);

	/**
	 * 
	 * @return
	 */
	public UserInfo databaseLockOwner();

	/**
	 * 
	 * @param l
	 * @param userinfo
	 * @return
	 */
	public int lockRecord(long l, UserInfo userinfo);

	/**
	 * 
	 * @param l
	 * @param userinfo
	 * @return
	 */
	public int unlockRecord(long l, UserInfo userinfo);

	/**
	 * 
	 * @param l
	 * @param userinfo
	 * @return
	 */
	public int getRecordLockStatus(long l, UserInfo userinfo);

	/**
	 * 
	 * @param l
	 * @return
	 */
	public UserInfo recordLockOwner(long l);

	/**
	 * 
	 * @return
	 */
	public List<Registro> getRecordLocks();

	/**
	 * 
	 * @return
	 */
	public Registro addNewRecord();

	/**
	 * 
	 * @param record
	 * @return
	 */
	public Registro addRecord(Registro record);

	/**
	 * 
	 * @param l
	 * @return
	 */
	public Registro getRecord(long l);

	/**
	 * 
	 * @param l
	 * @return
	 */
	public Registro getRecordCursor(long l);

	/**
	 * 
	 * @param record
	 * @return
	 */
	public Registro updateRecord(Registro record);

	/**
	 * 
	 * @return
	 */
	public Registro getFirst();

	/**
	 * 
	 * @return
	 */
	public Registro getLast();

	/**
	 * 
	 * @return
	 */
	public Registro getNext();

	/**
	 * 
	 * @return
	 */
	public Registro getPrev();

	/**
	 * 
	 * @return
	 */
	public Registro getCurrent();

	/**
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public List<Registro> getRecordChunck(int i, int j);

	/**
	 * 
	 * @param l
	 * @param i
	 * @return
	 */
	public List<Registro> getRecordChunck(long l, int i);

	/**
	 * 
	 * @param al
	 * @return
	 */
	public List<Registro> getRecordChunck(long al[]);

	/**
	 * 
	 * @return
	 */
	public List<Campo> getFieldDefinitionTable();

	/**
	 * 
	 * @param fielddefinitiontable
	 * @return
	 */
	public boolean saveFieldDefinitionTable(List<Campo> fielddefinitiontable);

	/**
	 * 
	 * @return
	 */
	public List<Indice> getFieldSelectionTable();

	/**
	 * 
	 * @param fieldselectiontable
	 * @return
	 */
	public boolean saveFieldSelectionTable(List<Indice> fieldselectiontable);

	/**
	 * 
	 * @param s
	 * @param fieldselectiontable
	 * @return
	 */
	public boolean saveFst(String s, List<Indice> fieldselectiontable);

	/**
	 * 
	 * @param s
	 * @return
	 */
	public List<Indice> getFst(String s);

	/**
	 * 
	 * @param s
	 * @return
	 */
	public HojaTrabajo getWorksheetDef(String s);

	/**
	 * 
	 * @param worksheetdef
	 * @return
	 */
	public boolean saveWorksheetDef(HojaTrabajo worksheetdef);

	/**
	 * 
	 * @return
	 */
	public Indice getIndexInfo();

	/**
	 * 
	 * @return
	 */
	public Esquema getDatabaseInfo();

	/**
	 * 
	 * @param l
	 * @param s
	 * @return
	 */
	public PresentacionRegistro getRecordFmt(long l, String s);
}
