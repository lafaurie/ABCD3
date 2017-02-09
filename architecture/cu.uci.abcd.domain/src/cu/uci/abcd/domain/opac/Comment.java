package cu.uci.abcd.domain.opac;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "dcomment", schema = "abcdn")
@PrimaryKeyJoinColumn(name = "opacaction")
@DiscriminatorValue("Comment")
public class Comment extends OPACAction {
	private static final long serialVersionUID = -5556470464465529126L;
	
	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "state")
	private Integer state;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
