package cu.uci.abos.dataprovider.jisis.services;

import java.util.List;

import cu.uci.abos.dataprovider.advanced.ISchemaManager;
import cu.uci.abos.dataprovider.jisis.domain.Esquema;

public interface IJisisSchemaManager extends ISchemaManager {

	public boolean salvar(Esquema esquema);

	public boolean eliminar(Long idEsquema);

	public Esquema obtener(Long idEsquema);

	public List<Esquema> obtener();
}
