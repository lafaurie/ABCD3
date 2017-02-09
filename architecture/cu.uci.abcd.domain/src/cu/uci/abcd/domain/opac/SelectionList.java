package cu.uci.abcd.domain.opac;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import cu.uci.abcd.domain.common.Nomenclator;

@Entity
@Table(name = "dselectionlist", schema = "abcdn")
@PrimaryKeyJoinColumn(name = "opacaction")
@DiscriminatorValue("SelectionList")
public class SelectionList extends OPACAction {

	private static final long serialVersionUID = 2314868555931953829L;
	    
	@Column(name = "listname", length = 150)
	private String selectionListName;
	
	@ManyToOne
	@JoinColumn(name = "category")
	private Nomenclator category;

	@ManyToOne
	@JoinColumn(name = "orderedby")
	private Nomenclator orderBy;
       
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "dsellistsellistisis", schema = "abcdn", joinColumns = { @JoinColumn(name = "selectionlist", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "selectionlistisis", referencedColumnName = "id", nullable = false) })
	private List<SelectionListData> selectionListData;

	public SelectionList() {
		super();
		selectionListData = new ArrayList<>();
	}

	public Nomenclator getCategory() {
		return category;
	}

	public void setCategory(Nomenclator category) {
		this.category = category;
	}

	public Nomenclator getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Nomenclator orderBy) {
		this.orderBy = orderBy;
	}

	public String getSelectionListName() {
		return selectionListName;
	}

	public void setSelectionListName(String selectionListName) {
		this.selectionListName = selectionListName;
	}

	public List<SelectionListData> getSelectionListData() {
		return selectionListData;
	}

	public void setSelectionListData(List<SelectionListData> selectionListData) {
		this.selectionListData = selectionListData;
	}

	public void addListData(SelectionListData listData) {
		selectionListData.add(listData);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectionList other = (SelectionList) obj;
		if (category != other.category)
			return false;
		if (orderBy != other.orderBy)
			return false;
		if (selectionListData == null) {
			if (other.selectionListData != null)
				return false;
		} else if (!selectionListData.equals(other.selectionListData))
			return false;
		if (selectionListName == null) {
			if (other.selectionListName != null)
				return false;
		} else if (!selectionListName.equals(other.selectionListName))
			return false;
		return true;
	}

}
