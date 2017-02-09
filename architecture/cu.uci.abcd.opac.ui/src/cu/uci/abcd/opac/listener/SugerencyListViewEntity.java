package cu.uci.abcd.opac.listener;

import java.util.List;

import cu.uci.abos.widgets.grid.IGridViewEntity;
import cu.uci.abos.widgets.grid.TreeTableColumn;

public class SugerencyListViewEntity implements IGridViewEntity {

	
	private int tag;
	private String title;
	private String firstName;
	private String lastName;
	private String date;
	
	
	
	
	public SugerencyListViewEntity(int tag, String title, String firstName, String lastName, String date) {
		this.tag = tag;
		this.title = title;		
		this.firstName = firstName;
		this.lastName = lastName;
		this.date = date;
		
	}
	
	public SugerencyListViewEntity() {
	}

	@TreeTableColumn(percentWidth=5, index=0)
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	@TreeTableColumn(percentWidth = 30, index=1)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@TreeTableColumn(percentWidth = 25, index=2)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}	
	@TreeTableColumn(percentWidth = 25, index=3)
	public String getLastName() {
		return lastName;
	}
	public void setCopyrightDate(String lastName) {
		this.lastName = lastName;
	}	
	@TreeTableColumn(percentWidth = 15, index=3)
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public void addChild(IGridViewEntity arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<IGridViewEntity> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

}
