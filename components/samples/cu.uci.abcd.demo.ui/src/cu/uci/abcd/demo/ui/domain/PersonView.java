package cu.uci.abcd.demo.ui.domain;

import java.sql.Date;
import java.util.List;

import cu.uci.abcd.demo.domain.common.Person;
import cu.uci.abos.widgets.grid.IGridViewEntity;
import cu.uci.abos.widgets.grid.TreeTableColumn;

public class PersonView extends Person implements IGridViewEntity {

	public PersonView(String firstName, String lastName, Date birthDate) {
		super();
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setBirthDate(birthDate);
	}

	public PersonView() {
		super();
	}

	@Override
	public void addChild(IGridViewEntity child) {

	}

	@Override
	public List<IGridViewEntity> getChildren() {
		return null;
	}

	@Override
	@TreeTableColumn(index = 3, percentWidth = 15)
	public Date getBirthDate() {
		return super.getBirthDate();
	}

	@Override
	@TreeTableColumn(index = 0, percentWidth = 10)
	public Long getResourceID() {
		return super.getResourceID();
	}

	@Override
	@TreeTableColumn(index = 1, percentWidth = 35)
	public String getFirstName() {
		return super.getFirstName();
	}

	@Override
	@TreeTableColumn(index = 2, percentWidth = 40)
	public String getLastName() {
		return super.getLastName();
	}

}
