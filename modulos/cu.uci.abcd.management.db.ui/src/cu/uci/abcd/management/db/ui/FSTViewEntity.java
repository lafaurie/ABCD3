package cu.uci.abcd.management.db.ui;

import java.util.List;

import org.unesco.jisis.corelib.common.FieldSelectionTable.FstEntry;

import cu.uci.abos.core.domain.Row;
import cu.uci.abos.core.widget.grid.IGridViewEntity;


public class FSTViewEntity extends FstEntry implements IGridViewEntity {

    private static final long serialVersionUID = 1L;

    public FSTViewEntity(int tag, String name, int technique, String format) {
	super(tag, name, technique, format);
    }

    //@TreeTableColumn(percentWidth = 25, index = 0)
    @Override
    public int getTag() {
	return super.getTag();
    };

    //@TreeTableColumn(percentWidth = 25, index = 1)
    @Override
    public String getName() {
	return super.getName();
    }

    //@TreeTableColumn(percentWidth = 25, index = 2)
    @Override
    public int getTechnique() {
	return super.getTechnique();
    }

    //@TreeTableColumn(percentWidth = 25, index = 3)
    @Override
    public String getFormat() {
	return super.getFormat();
    }

    @Override
    public <TE extends IGridViewEntity> void addChild(TE arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public <TE extends IGridViewEntity> void addChildren(List<TE> arg0) {
	// TODO Auto-generated method stub

    }

	@Override
	public boolean equals(Row arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IGridViewEntity> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGridViewEntity getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Row> T getRow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <TE extends IGridViewEntity> void setChildren(List<TE> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParent(IGridViewEntity arg0) {
		// TODO Auto-generated method stub
		
	}   

}
