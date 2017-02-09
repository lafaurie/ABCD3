package cu.uci.abos.widgets.grid;

import java.util.LinkedList;
import java.util.List;

import cu.uci.abcd.domain.Row;

public class BaseGridViewEntity<T extends Row> implements IGridViewEntity {

	private T row;
	private IGridViewEntity parent;
	private List<IGridViewEntity> children;
	
	public BaseGridViewEntity(T row) {
		this.children = new LinkedList<IGridViewEntity>();
		this.row= row;
	}

	@Override
	public  IGridViewEntity getParent() {
		return this.parent;
	}

	@Override
	public void setParent(IGridViewEntity parent) {
		this.parent = parent;
		parent.addChild(this);
	}

	public List<IGridViewEntity> getChildren() {
		return this.children;
	}

	@Override
	public <TE extends IGridViewEntity> void addChild(TE child) {
		if(!this.children.contains(child)) {
			this.children.add(child);
			if(child.getParent() == null || !child.getParent().equals(this)) {
				child.setParent(this);
			}
		}
	}

	public <TE extends IGridViewEntity> void addChildren(List<TE> children) {
		for(IGridViewEntity child : children) {
			this.addChild(child);
		}
	}

	public <TE extends IGridViewEntity> void setChildren(List<TE> children) {
		this.children.clear();
		this.addChildren(children);
	}

	@Override
	public boolean equals(Row otherEntity) {
		if(this.row.getRowID().equals(otherEntity.getRowID())) {
			return true;
		}
		return false;
	}

	

	public void setRow(T row) {
		this.row = row;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <T extends Row> T getRow() {
		return (T) row;
	}
}
