package cu.uci.abos.widgets.grid;

import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;

public class Column {

	private Image image;
	private String header;
	private int percentWidth, initialWidth;
	private boolean moveable;
	private int alignment;
	private int index;
	private List<TreeColumnListener> listeners;
	private String dataExtractor;
	private IEditableArea editableArea;
	private String toolTipText;
	private SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat dateTimeformatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public Column(Image image, IEditableArea editableArea, TreeColumnListener mouseDown) { // Action
																							// columns
																							// with
																							// editable
																							// area.
		this.initialize(image, "", -1, -1, editableArea, mouseDown);
	}

	public Column(Image image, TreeColumnListener mouseDown) { // Action columns
																// without
																// editable
																// area.
		this.initialize(image, "", -1, -1, null, mouseDown);
	}

	public Column(String resourceKey, Device device, IEditableArea editableArea, TreeColumnListener mouseDown) { // Action
																													// columns
																													// with
																													// editable
																													// area.
		Image image = new Image(device, RWT.getResourceManager().getRegisteredContent(resourceKey));
		this.initialize(image, "", -1, -1, editableArea, mouseDown);
	}

	public Column(String resourceKey, Device device, TreeColumnListener mouseDown) { // Action
																						// columns
																						// without
																						// editable
																						// area.
		Image image = new Image(device, RWT.getResourceManager().getRegisteredContent(resourceKey));
		this.initialize(image, "", -1, -1, null, mouseDown);
	}

	public Column(int percentWidth, int index) { // Data column.
		this.initialize(null, "", percentWidth, index, null, null);
	}

	private void initialize(Image image, String header, int percentWidth, int index, IEditableArea editableArea, TreeColumnListener mouseDown) {
		this.image = image;
		this.header = header;
		this.percentWidth = percentWidth;
		this.index = index;
		this.listeners = new LinkedList<TreeColumnListener>();
		if (mouseDown != null) {
			this.listeners.add(mouseDown);
		}
		this.setEditableArea(editableArea);
		this.alignment = SWT.CENTER;
		this.moveable = true;
	}

	public String getHeader() {
		return this.header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public int getPercentWidth() {
		return this.percentWidth;
	}

	public int getInitialWidth() {
		return this.initialWidth;
	}

	public Image getImage() {
		return this.image;
	}

	public boolean getMoveable() {
		return this.moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	public int getAlignment() {
		return this.alignment;
	}

	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void addListener(TreeColumnListener listener) {
		this.listeners.add(listener);
	}

	public void fireListeners(TreeColumnEvent eventData) {
		eventData.editableArea = this.editableArea;
		for (int i = this.listeners.size() - 1; i >= 0; i--) {
			TreeColumnListener listener = this.listeners.get(i);
			listener.handleEvent(eventData);
		}
	}

	public void setDataExtractor(String dataExtractor) {
		this.dataExtractor = dataExtractor;
	}

	public Object getCellData(Object entity) {
		Object result = null;
		try {
			String[] methods = dataExtractor.split("\\.");

			Method method = ((BaseGridViewEntity<?>) entity).getRow().getClass().getMethod(methods[0]);
			method.setAccessible(true);
			result = method.invoke(((BaseGridViewEntity<?>) entity).getRow());

			for (int i = 1; i < methods.length; i++) {
				method = result.getClass().getMethod(methods[i]);
				method.setAccessible(true);
				result = method.invoke(result);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != null) {
			if (result.getClass().isAssignableFrom(Date.class)) {
				return dateformatter.format(result);
			}
			if (result.getClass().isAssignableFrom(Timestamp.class)) {
				return dateTimeformatter.format(result);
			}
			return result;

		}
		return "";
	}

	public IEditableArea getEditableArea() {
		return editableArea;
	}

	public void setEditableArea(IEditableArea editableArea) {
		this.editableArea = editableArea;
	}

	public String getToolTipText() {
		return toolTipText;
	}

	public void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}
}
