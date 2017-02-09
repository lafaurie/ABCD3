package cu.uci.abos.core.widget.grid;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * @author Leandro Tabares Martin
 * @date 12/02/2015
 * 
 * */

public class DynamicGridEntity<T extends Object> implements Comparable<DynamicGridEntity<T>> {
	private T baseEntity;
	private List<DynamicGridField> fields;
	private String fieldNameToCompare = null;

	public DynamicGridEntity(T baseEntity) {
		super();
		this.baseEntity = baseEntity;
		this.fields = getBaseEntityFieldsAsDynamicGridFields();
	}

	private List<DynamicGridField> getBaseEntityFieldsAsDynamicGridFields() {
		List<DynamicGridField> baseEntityFields = new ArrayList<>();

		List<Field> declaredFields = Arrays.asList(baseEntity.getClass().getDeclaredFields());
		List<Field> accesibleFields = getFieldsWithAccessorMethod(declaredFields, baseEntity);
		for (Field field : accesibleFields) {
			DynamicGridField dgf = new DynamicGridField();
			dgf.setField(field);
			dgf.setVisibleField(true);
			dgf.setParentEntity(baseEntity);
			baseEntityFields.add(dgf);
		}
		return baseEntityFields;
	}

	private List<Field> getFieldsWithAccessorMethod(List<Field> fields, Object entity) {
		final String BOOLEAN_ACCESSOR_METHOD_PATTERN = "is";
		final String GENERIC_ACCESSOR_METHOD_PATTERN = "get";

		List<Field> filteredFields = new ArrayList<>();

		final List<Method> accesibleMethods = Arrays.asList(entity.getClass().getMethods());

		for (Field field : fields) {
			String fieldName = field.getName();
			fieldName = fieldName.replaceFirst(String.valueOf(fieldName.charAt(0)), String.valueOf(fieldName.charAt(0)).toUpperCase());
			if ((!field.getType().isAssignableFrom(Boolean.class)) && (!field.getType().isAssignableFrom(boolean.class))) {
				for (Method method : accesibleMethods) {
					if (method.getName().equals(GENERIC_ACCESSOR_METHOD_PATTERN.concat(fieldName))) {
						filteredFields.add(field);
						break;
					}
				}
			} else {
				for (Method method : accesibleMethods) {
					if (method.getName().equals(BOOLEAN_ACCESSOR_METHOD_PATTERN.concat(fieldName))) {
						filteredFields.add(field);
						break;
					}
				}
			}
		}
		return filteredFields;
	}

	public T getBaseEntity() {
		return baseEntity;
	}

	public void setBaseEntity(T baseEntity) {
		this.baseEntity = baseEntity;
	}

	public List<DynamicGridField> getFields() {
		return fields;
	}

	public void setFields(List<DynamicGridField> fields) {
		this.fields = fields;
	}

	public String getFieldNameToCompare() {
		return fieldNameToCompare;
	}

	public void setFieldNameToCompare(String fieldNameToCompare) {
		this.fieldNameToCompare = fieldNameToCompare;
	}

	public DynamicGridField getDynamicGridFieldByName(String fieldName) {
		for (DynamicGridField field : fields)
			if (field.toString().equals(fieldName))
				return field;

		return null;
	}

	@Override
	public int compareTo(DynamicGridEntity<T> other) {
		if (null != fieldNameToCompare) {
			DynamicGridField thisField = this.getDynamicGridFieldByName(fieldNameToCompare);
			DynamicGridField otherField = other.getDynamicGridFieldByName(fieldNameToCompare);

			return thisField.compareTo(otherField);
		}

		return 0;
	}

}
