package cu.uci.abcd.administracion.bdisis.fachada.internals;

import cu.uci.abcd.administracion.bdisis.fachada.publics.IBdIsisFachada;
import cu.uci.abcd.domain.administracion.bdisis.BasesDatosIsis;
import cu.uci.abcd.domain.administracion.bdisis.Biblioteca;
import cu.uci.abos.dataprovider.hibernateogm.publics.IHibernateOgm;
import cu.uci.abos.dataprovider.jisis.publics.IJisis;


public class BdIsisFachada implements IBdIsisFachada {

	private IHibernateOgm hibernateOgm;
	
	private IJisis jisis; 
	 
	
	public IHibernateOgm getHibernateOgm() {
		return hibernateOgm;
	}



	public void setHibernateOgm(IHibernateOgm hibernateOgm) {
		this.hibernateOgm = hibernateOgm;
	}

	
	@Override
	public String salvarBiblioteca() {	
			
		
		try {
			this.hibernateOgm.getCurrentSession().beginTransaction();
			
			/*Biblioteca biblioteca = new Biblioteca();
			
			biblioteca.setNombre("UCI-1");
			biblioteca.setIdentificador("0002-1");
			
			this.hibernateOgm.salvar(biblioteca);	*/	
			
			this.hibernateOgm.obtenerTodos(Biblioteca.class);
			 
			this.hibernateOgm.getCurrentSession().getTransaction().commit();
			
		} catch (Exception e) {
			return "Incorrecto";
		}
		return "Correcto";
	}
	
	public String salvarBasesDatosIsis(BasesDatosIsis basesDatosIsis){
		
		try {
			this.hibernateOgm.getCurrentSession().beginTransaction();
			this.hibernateOgm.salvar(basesDatosIsis);
			this.jisis.crearBDISIS();
			this.hibernateOgm.getCurrentSession().getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
		
	}

}
