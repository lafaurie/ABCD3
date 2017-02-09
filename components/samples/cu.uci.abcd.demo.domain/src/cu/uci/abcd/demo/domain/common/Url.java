package cu.uci.abcd.demo.domain.common;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "durl", schema = "abcd")
@PrimaryKeyJoinColumn(name = "url_id")
@DiscriminatorValue(value = "URL")
public class Url extends Address {
	@Column
	private String url;

	public Url() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
