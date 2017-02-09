package cu.uci.abos.core.widget.grid;

import java.util.LinkedList;
import java.util.List;

import cu.uci.abos.core.domain.Row;

public class BaseGridViewEntity<T extends Row> implements IGridViewEntity {

	private T row;
	private IGridViewEntity parent;
	private List<IGridViewEntity> children;

	public BaseGridViewEntity(T row) {
		this.children = new LinkedList<IGridViewEntity>();
		this.row = row;
	}

	@Override
	public IGridViewEntity getParent() {
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
		if (!this.children.contains(child)) {
			this.children.add(child);
			if (child.getParent() == null || !child.getParent().equals(this)) {
				child.setParent(this);
			}
		}
	}

	public <TE extends IGridViewEntity> void addChildren(List<TE> children) {
		for (IGridViewEntity child : children) {
			this.addChild(child);
		}
	}

	public <TE extends IGridViewEntity> void setChildren(List<TE> children) {
		this.children.clear();
		this.addChildren(children);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((row == null) ? 0 : row.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseGridViewEntity other = (BaseGridViewEntity) obj;
		if (row == null) {
			if (other.row != null)
				return false;
		} else if (this.row.getRowID().hashCode()!=other.row.getRowID().hashCode())
			return false;
		return true;
	}

	public void setRow(T row) {
		this.row = row;
	}

	@Override
	public boolean equalsRow(Row otherEntity) {
		if (this.getRow()==otherEntity) {
			return true;
		}
		if (otherEntity==null) {
			return false;
		}
		if (this.row.getClass()!= otherEntity.getClass()) {
			return false;
		}
		if (this.row.getRowID().hashCode()!=otherEntity.getRowID().hashCode()) {
			return false;
		}
		
		return true;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <T extends Row> T getRow() {
		return (T) row;
	}
}
