package cu.uci.abos.core.widget.grid;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/*
 * @author Leandro Tabares Martin
 * @date 12/02/2015
 * 
 * */

public class DynamicGridField implements Comparable<DynamicGridField> {
	private Field field;
	private Object parentEntity;
	private boolean visibleField;

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public boolean isVisibleField() {
		return visibleField;
	}

	public void setVisibleField(boolean visibleField) {
		this.visibleField = visibleField;
	}

	public Object getParentEntity() {
		return parentEntity;
	}

	public void setParentEntity(Object parentEntity) {
		this.parentEntity = parentEntity;
	}

	public Object getFieldValue() {
		try {
			field.setAccessible(true);

			final String BOOLEAN_ACCESSOR_METHOD_PATTERN = "is";
			final String GENERIC_ACCESSOR_METHOD_PATTERN = "get";

			final List<Method> accesibleMethods = Arrays.asList(parentEntity.getClass().getMethods());

			String fieldName = field.getName();
			fieldName = fieldName.replaceFirst(String.valueOf(fieldName.charAt(0)), String.valueOf(fieldName.charAt(0)).toUpperCase());

			for (Method method : accesibleMethods) {
				if ((!field.getType().isAssignableFrom(Boolean.class)) && (!field.getType().isAssignableFrom(boolean.class))) {
					if (method.getName().equals(GENERIC_ACCESSOR_METHOD_PATTERN.concat(fieldName))) {
						return method.invoke(parentEntity);
					}

				} else {
					if (method.getName().equals(BOOLEAN_ACCESSOR_METHOD_PATTERN.concat(fieldName))) {
						return method.invoke(parentEntity);
					}
				}
			}

		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {
		return field.getName();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int compareTo(DynamicGridField other) {
		try {
			field.setAccessible(true);
			other.getField().setAccessible(true);
			final Object fieldValue = field.get(parentEntity);
			final Object otherValue = other.getField().get(other.getParentEntity());

			return ((Comparable) fieldValue).compareTo(otherValue);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((parentEntity == null) ? 0 : parentEntity.hashCode());
		result = prime * result + (visibleField ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DynamicGridField other = (DynamicGridField) obj;
		if (!this.toString().equals(other.toString()))
			return false;
		return true;
	}

}
