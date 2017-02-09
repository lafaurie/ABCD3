package cu.uci.abcd.domain.common;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.eclipse.rap.rwt.RWT;

@Entity
@Table(name = "internationalization", schema = "abcdn")
@SequenceGenerator(name = "seq_internationalization", sequenceName = "sq_internationalization", allocationSize = 1, schema = "abcdn")
public class Internationalization implements Serializable {

	private static final long serialVersionUID = 2272308004342412918L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_internationalization")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "es", nullable = false)
	private String es;

	@Column(name = "en", nullable = false)
	private String en;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEs() {
		return es;
	}

	public void setEs(String es) {
		this.es = es;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getValue() {
		if (RWT.getLocale().equals(Locale.ENGLISH))
			return en;
		else
			return es;
	}

}
