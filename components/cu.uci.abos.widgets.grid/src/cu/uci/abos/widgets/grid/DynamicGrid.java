package cu.uci.abos.widgets.grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * @author Leandro Tabares Martin
 * @date 12/02/2015
 * 
 * */

public class DynamicGrid<T> {
	public static final Integer ORDER_ASCENDANT = 1;
	public static final Integer ORDER_DESCENDANT = 2;

	// THE ENTITY TO REPRESENT
	private List<T> entities;

	private List<DynamicGridEntity<T>> gridEntities;

	private String fieldNameToCompare = null;

	public DynamicGrid(List<T> entities) {
		super();
		this.entities = entities;
		populateGridEntities();
	}

	private void populateGridEntities() {
		gridEntities = new ArrayList<>();
		for (T entity : entities) {
			gridEntities.add(new DynamicGridEntity<T>(entity));
		}
	}

	public List<DynamicGridField> getFields() {
		return gridEntities.get(0).getFields();
	}

	public List<DynamicGridEntity<T>> sort(int order) {
		if (ORDER_ASCENDANT == order)
			Collections.sort(gridEntities);
		else
			Collections.sort(gridEntities, Collections.reverseOrder());

		return gridEntities;
	}

	public void configureGridFieldsVisibility(List<DynamicGridField> fields) {
		for (DynamicGridEntity<T> entity : gridEntities) {
			List<DynamicGridField> entityFields = entity.getFields();

			for (int i = 0; i < entityFields.size(); i++) {
				configureField(entityFields.get(i), fields);
			}

			entity.setFields(entityFields);
		}
	}

	private void configureField(DynamicGridField fieldToConfigure, List<DynamicGridField> fieldValues) {
		for (DynamicGridField field : fieldValues) {
			if (field.equals(fieldToConfigure)) {
				fieldToConfigure.setVisibleField(field.isVisibleField());
				break;
			}
		}
	}

	public List<T> getEntities() {
		return entities;
	}

	public void setEntities(List<T> entities) {
		this.entities = entities;
	}

	public List<DynamicGridEntity<T>> getGridEntities() {
		return gridEntities;
	}

	public void setGridEntities(List<DynamicGridEntity<T>> gridEntities) {
		this.gridEntities = gridEntities;
	}

	public String getFieldNameToCompare() {
		return fieldNameToCompare;
	}

	public void setFieldNameToCompare(String fieldNameToCompare) {
		this.fieldNameToCompare = fieldNameToCompare;
		for (DynamicGridEntity<T> entity : gridEntities)
			entity.setFieldNameToCompare(fieldNameToCompare);
	}
}
