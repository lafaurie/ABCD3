package cu.uci.abos.dataprovider.jisis.services;

import cu.uci.abos.dataprovider.advanced.IRecordManager;
import cu.uci.abos.dataprovider.jisis.domain.Registro;

public interface IJisisRecordManager extends IRecordManager {
	
	public Registro obtener(Long idRegitro);

	public void salvar(Registro registro);

	public void eliminar(Long idRegistro);
}
