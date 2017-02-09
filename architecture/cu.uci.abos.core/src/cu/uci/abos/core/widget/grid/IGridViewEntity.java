package cu.uci.abos.core.widget.grid;

import java.util.List;

import cu.uci.abos.core.domain.Row;

public interface IGridViewEntity {
	
	<TE extends IGridViewEntity> void addChild(TE child);
	
	<TE extends IGridViewEntity> void addChildren(List<TE> children);
	
	<TE extends IGridViewEntity> void setChildren(List<TE> children);
	
	List<IGridViewEntity> getChildren();
	
	IGridViewEntity getParent();
	
	void setParent(IGridViewEntity entity);

	boolean equalsRow(Row otherEntity);
	
	public<T extends Row> T getRow();
	
	
}
