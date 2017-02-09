package cu.uci.abos.dataprovider.jisis.domain;

import java.util.List;

public class Esquema {
	
	private String nombre;
	
	private List<Coleccion> colecciones;
	
	private List<UserInfo> usersInfo;
	
	public class Coleccion{
		
		private String nombre;
		
		private List<Campo> campos;
		
		private List<Restriccion> restricciones;
		
		private Secuencia secuencia;
		
		private List<HojaTrabajo> hojasTrabajo;
		
		private List<Indice> indices;
		
		private List<PresentacionRegistro> presentacionRegistros;
		
		public class Campo{
			
		}
		
		public class Restriccion{
			
		}
		
		public class Secuencia{
			
		}
		
		public class HojaTrabajo{
			
			private List<CampoHojaTrabajo> camposHojaTrabajo;
			
			public class CampoHojaTrabajo extends Campo{
				
			}

			public List<CampoHojaTrabajo> getCamposHojaTrabajo() {
				return camposHojaTrabajo;
			}

			public void setCamposHojaTrabajo(List<CampoHojaTrabajo> camposHojaTrabajo) {
				this.camposHojaTrabajo = camposHojaTrabajo;
			}		
			
		}
		
		public class Indice{
			
		}
		
		public class PresentacionRegistro{
			
		}

		public List<Campo> getCampos() {
			return campos;
		}

		public void setCampos(List<Campo> campos) {
			this.campos = campos;
		}

		public List<HojaTrabajo> getHojasTrabajo() {
			return hojasTrabajo;
		}

		public void setHojasTrabajo(List<HojaTrabajo> hojasTrabajo) {
			this.hojasTrabajo = hojasTrabajo;
		}

		public List<Indice> getIndices() {
			return indices;
		}

		public void setIndices(List<Indice> indices) {
			this.indices = indices;
		}

		public List<PresentacionRegistro> getPresentacionRegistros() {
			return presentacionRegistros;
		}

		public void setPresentacionRegistros(
				List<PresentacionRegistro> presentacionRegistros) {
			this.presentacionRegistros = presentacionRegistros;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public List<Restriccion> getRestricciones() {
			return restricciones;
		}

		public void setRestricciones(List<Restriccion> restricciones) {
			this.restricciones = restricciones;
		}

		public Secuencia getSecuencia() {
			return secuencia;
		}

		public void setSecuencia(Secuencia secuencia) {
			this.secuencia = secuencia;
		}	
		
	}	
	
	public class UserInfo{
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<Coleccion> getColecciones() {
		return colecciones;
	}

	public void setColecciones(List<Coleccion> colecciones) {
		this.colecciones = colecciones;
	}

	
	public List<UserInfo> getUsersInfo() {
		return usersInfo;
	}

	
	public void setUsersInfo(List<UserInfo> usersInfo) {
		this.usersInfo = usersInfo;
	}
	
	


}
