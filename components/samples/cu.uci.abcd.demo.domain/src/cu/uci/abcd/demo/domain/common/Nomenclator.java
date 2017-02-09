package cu.uci.abcd.demo.domain.common;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Inheritance
@DiscriminatorColumn(name = "nomenclator_name")
@Table(name = "dnomenclator", schema = "abcd")
public abstract class Nomenclator {
	@Id
	@GeneratedValue(generator = "GEN_NOMENCLATOR")
	@SequenceGenerator(name = "GEN_NOMENCLATOR", sequenceName = "seq_nomenclator")
	@Column(name = "nomenclator_id")
	protected Long nomenclatorID;
	@Column(name = "nomenclator_name")
	protected String nomeclatorName;
	@Column(name = "nomenclator_code")
	protected String nomenclatorCode;
	@Column(name = "nomenclator_description")
	protected String nomenclatorDescription;

	public Nomenclator() {
		super();
	}

	public String getNomeclatorName() {
		return nomeclatorName;
	}

	public void setNomeclatorName(String nomeclatorName) {
		this.nomeclatorName = nomeclatorName;
	}

	public String getNomenclatorDescription() {
		return nomenclatorDescription;
	}

	public void setNomenclatorDescription(String nomenclatorDescription) {
		this.nomenclatorDescription = nomenclatorDescription;
	}

	public String getNomenclatorCode() {
		return nomenclatorCode;
	}

	public void setNomenclatorCode(String nomenclatorCode) {
		this.nomenclatorCode = nomenclatorCode;
	}

}
