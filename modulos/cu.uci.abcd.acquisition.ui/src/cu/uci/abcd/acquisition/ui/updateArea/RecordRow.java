package cu.uci.abcd.acquisition.ui.updateArea;

import java.io.Serializable;
import java.util.List;

import org.unesco.jisis.corelib.exceptions.DbException;
import org.unesco.jisis.corelib.record.IField;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abos.core.domain.Row;

public class RecordRow implements  Row ,Serializable{

	private static final long serialVersionUID = -1855369995469609454L;
    Record record;
    
    public RecordRow(Record record) {
		super();
		this.record = record;
	}
	public void addField(IField field) {
        record.addField(field);
    }
    public void clear() {
        record.clear();
    }
    public boolean equals(Object arg0) {
        return record.equals(arg0);
    }
    
    public IField getField(Integer tag) throws DbException {
        return record.getField(tag);
    }
    public int getFieldCount() throws DbException {
        return record.getFieldCount();
    }
    public List<IField> getFields() throws DbException {
        return record.getFields();
    }
    public long getMfn() {
        return record.getMfn();
    }
    
    
	@Override
	public Object getRowID() {
		return record.getMfn();
	}
}
