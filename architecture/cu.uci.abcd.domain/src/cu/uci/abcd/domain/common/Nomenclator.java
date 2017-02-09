package cu.uci.abcd.domain.common;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.eclipse.rap.rwt.RWT;

import cu.uci.abcd.domain.acquisition.AdquisitionNomenclator;
import cu.uci.abcd.domain.circulation.CirculationNomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.LibraryNomenclator;
import cu.uci.abcd.domain.management.security.SecurityNomenclator;
import cu.uci.abcd.domain.opac.OPACNomenclator;
import cu.uci.abos.core.domain.Row;


@Entity
@SequenceGenerator(name = "seq_dnomenclator", sequenceName = "sq_dnomenclator", schema = "abcdn", allocationSize = 1)
@Table(name = "dnomenclator", schema = "abcdn")
//@Customizer(HistoryCustomizer.class)
//@EntityListeners(AuditingEntityListener.class)
public class Nomenclator /*extends AbstractAuditable<String, Long>*/ implements Serializable, CommonNomenclator, CirculationNomenclator, AdquisitionNomenclator, OPACNomenclator, LibraryNomenclator, SecurityNomenclator, Row {

	private static final long serialVersionUID = 546040584160047713L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dnomenclator")
	@Column(name = "id")
	private Long nomenclatorID;

	@Column(name = "nomenclatorvalue")
	private String nomenclatorName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "discriminator", nullable = false)
	private Nomenclator nomenclator;

	@Column(name = "description")
	private String nomenclatorDescription;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "library", nullable = false)
	private Library ownerLibrary;

	@Column(name = "manageable")
	private Integer manageable;

	@OneToOne
	@JoinColumn(name = "internationalization", nullable = true)
	private Internationalization internationalization;

	// FIXME OIGRES, Esto está acá porque explota. dice que le hace falta.
	// Quitar cuando se descubra.
	@Column(name = "code")
	private String code;

	public Nomenclator() {
		super();
		setManageable(8);
	}

	public String getNomenclatorName() {
		if (8 != manageable  && internationalization != null) {
			return internationalization.getValue();
		}
		return nomenclatorName;
	}

	public void setNomenclatorName(String nomeclatorName) {
		this.nomenclatorName = nomeclatorName;
		if (internationalization != null) {
			if (RWT.getLocale().equals(Locale.ENGLISH)) {
				internationalization.setEn(nomeclatorName);
			}else {
				internationalization.setEs(nomeclatorName);
			}
		}
		
	}

	public String getNomenclatorDescription() {
		return nomenclatorDescription;
	}

	public void setNomenclatorDescription(String nomenclatorDescription) {
		this.nomenclatorDescription = nomenclatorDescription;
	}

	public Long getNomenclatorID() {
		return nomenclatorID;
	}

	public void setNomenclatorID(Long nomenclatorID) {
		this.nomenclatorID = nomenclatorID;
	}

	public Library getOwnerLibrary() {
		return ownerLibrary;
	}

	public void setOwnerLibrary(Library ownerLibrary) {
		this.ownerLibrary = ownerLibrary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomenclatorID == null) ? 0 : nomenclatorName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nomenclator other = (Nomenclator) obj;
		if (nomenclatorID == null) {
			if (other.getNomenclatorID() != null)
				return false;
		} else if (!nomenclatorID.equals(other.getNomenclatorID()))
			return false;
		return true;
	}

	public void setNomenclator(Nomenclator nomenclator) {
		this.nomenclator = nomenclator;
	}

	public Nomenclator getNomenclator() {
		return nomenclator;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public Integer getManageable() {
		return manageable;
	}

	public void setManageable(Integer manageable) {
		this.manageable = manageable;
	}

	public Internationalization getInternationalization() {
		return internationalization;
	}

	public void setInternationalization(Internationalization internationalization) {
		this.internationalization = internationalization;
	}

	@Override
	public Object getRowID() {
		return getNomenclatorID();
	}
	
	public String getNomenclatorTypeName(){
		return getNomenclator().getNomenclatorName();
	}

	@Override
	public String toString() {
		return getNomenclatorName();
	}

}
