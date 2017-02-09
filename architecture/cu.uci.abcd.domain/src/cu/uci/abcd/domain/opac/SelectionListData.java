package cu.uci.abcd.domain.opac;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dselectionlistisis", schema = "abcdn")
@SequenceGenerator(name = "seq_selectionlistisis", sequenceName = "sq_dselectionlistisis", allocationSize = 1, schema = "abcdn")
public class SelectionListData implements Serializable, Row {
	private static final long serialVersionUID = -2797696448909240786L;
   
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_selectionlistisis")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "controlnumber")
	private String controlNumber;
	
	@Column(name = "isisdatabasename", length = 500)
	private String isisdatabasename;
	
	@Column(name = "isishome", length = 50)
	private String isisHome;	

	public String getIsisRecordID() {
		return controlNumber;
	}

	public void setIsisRecordID(String controlNumber) {
		this.controlNumber = controlNumber;
	}

	public String getIsisdatabasename() {
		return isisdatabasename;
	}

	public void setIsisdatabasename(String isisdatabasename) {
		this.isisdatabasename = isisdatabasename;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getIsisHome() {
		return isisHome;
	}

	public void setIsisHome(String isisHome) {
		this.isisHome = isisHome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((controlNumber == null) ? 0 : controlNumber.hashCode());
		result = prime * result + ((isisdatabasename == null) ? 0 : isisdatabasename.hashCode());
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
		SelectionListData other = (SelectionListData) obj;
		if (controlNumber == null) {
			if (other.controlNumber != null)
				return false;
		} else if (!controlNumber.equals(other.controlNumber))
			return false;
		if (isisdatabasename == null) {
			if (other.isisdatabasename != null)
				return false;
		} else if (!isisdatabasename.equals(other.isisdatabasename))
			return false;
		return true;
	}

	@Override
	public Object getRowID() {
		return getId();
	}

}
