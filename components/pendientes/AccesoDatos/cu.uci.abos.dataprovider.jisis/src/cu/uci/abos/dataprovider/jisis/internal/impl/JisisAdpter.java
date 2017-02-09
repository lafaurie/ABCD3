package cu.uci.abos.dataprovider.jisis.internal.impl;

import java.io.Serializable;
import java.util.List;

import cu.uci.abos.dataprovider.exception.DataProviderException;
import cu.uci.abos.dataprovider.jisis.domain.Esquema;
import cu.uci.abos.dataprovider.jisis.domain.Esquema.Coleccion.Campo;
import cu.uci.abos.dataprovider.jisis.domain.Esquema.Coleccion.HojaTrabajo;
import cu.uci.abos.dataprovider.jisis.domain.Esquema.Coleccion.Indice;
import cu.uci.abos.dataprovider.jisis.domain.Esquema.Coleccion.PresentacionRegistro;
import cu.uci.abos.dataprovider.jisis.domain.Esquema.UserInfo;
import cu.uci.abos.dataprovider.jisis.domain.Registro;
import cu.uci.abos.dataprovider.jisis.services.IJisisAdapter;

public class JisisAdpter implements IJisisAdapter {

	@Override
	public String getDbHome() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int lockDatabase(UserInfo userinfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UserInfo databaseLockOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int lockRecord(long l, UserInfo userinfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int unlockRecord(long l, UserInfo userinfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRecordLockStatus(long l, UserInfo userinfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UserInfo recordLockOwner(long l) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Registro> getRecordLocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Registro addNewRecord() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Registro addRecord(Registro record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Registro getRecord(long l) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Registro getRecordCursor(long l) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Registro updateRecord(Registro record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Registro getFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Registro getLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Registro getNext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Registro getPrev() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Registro getCurrent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Registro> getRecordChunck(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Registro> getRecordChunck(long l, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Registro> getRecordChunck(long[] al) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Campo> getFieldDefinitionTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveFieldDefinitionTable(List<Campo> fielddefinitiontable) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Indice> getFieldSelectionTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveFieldSelectionTable(List<Indice> fieldselectiontable) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveFst(String s, List<Indice> fieldselectiontable) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Indice> getFst(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HojaTrabajo getWorksheetDef(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveWorksheetDef(HojaTrabajo worksheetdef) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Indice getIndexInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Esquema getDatabaseInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PresentacionRegistro getRecordFmt(long l, String s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> boolean create(Object... params) throws DataProviderException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getID() throws DataProviderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDBName() throws DataProviderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Serializable> void getDB(Object... params)
			throws DataProviderException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int unlockDB() throws DataProviderException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isDBLocked() throws DataProviderException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDBConfig() throws DataProviderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveDBConfig(String s) throws DataProviderException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetInfo() throws DataProviderException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int unlockAllRecords() throws DataProviderException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLastMfn() throws DataProviderException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] getFstNames() throws DataProviderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeFst(String s) throws DataProviderException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDefaultFstName() throws DataProviderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultPrintFormat() throws DataProviderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultPrintFormatName() throws DataProviderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrintFormat(String s) throws DataProviderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrintFormatAnsi(String s) throws DataProviderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getPrintFormatNames() throws DataProviderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removePrintFormat(String s) throws DataProviderException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void saveDefaultPrintFormat(String s) throws DataProviderException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean savePrintFormat(String s, String s1)
			throws DataProviderException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] getWorksheetNames() throws DataProviderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeWorksheetDef(String s) throws DataProviderException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean buildIndex() throws DataProviderException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean clearIndex() throws DataProviderException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reIndex() throws DataProviderException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long[] search(String s) throws DataProviderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long[] searchLucene(String s) throws DataProviderException {
		// TODO Auto-generated method stub
		return null;
	}

}
