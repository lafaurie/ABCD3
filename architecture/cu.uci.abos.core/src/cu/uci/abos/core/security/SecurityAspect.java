package cu.uci.abos.core.security;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public class SecurityAspect {

	private LoginService service;

	public void verify(Object instance) {

		if (instance.getClass().isAnnotationPresent(SecuredUI.class)) {
			verifyPermission(instance);
			verifySecurityInMembers(instance);
		}
	}

	public LoginService getService() {
		return service;
	}

	public void setService(LoginService service) {
		this.service = service;
	}

	public boolean verifyPermission(Object instance) {
		if (instance.getClass().isAnnotationPresent(SecuredUI.class)) {
			if (!verifyType(instance))
				return false;
			verifySecurityInMembers(instance);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean verifyType(Object instance) {
		List<String> permissions =  (List<String>) service.getPrincipal().getPermission();
		if (instance.getClass().isAnnotationPresent(SecuredUI.class)) {
			String permission = instance.getClass().getAnnotation(SecuredUI.class).permission();
			for (Iterator<String> iterator = permissions.iterator(); iterator.hasNext();) {
				String temp = (String) iterator.next();
				if (permission.equalsIgnoreCase(temp)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	private void verifySecurityInMembers(Object instance) {
		Field[] fields = instance.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(SecurityPermision.class)) {
				verifySecurity(field.getAnnotation(SecurityPermision.class), field, instance);
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void verifySecurity(SecurityPermision sp, Field field, Object instance) {
		List<String> permissions = (List<String>) service.getPrincipal().getPermission();
		for (Iterator<String> iterator = permissions.iterator(); iterator.hasNext();) {
			String permission = (String) iterator.next();
			if (sp.permission().equalsIgnoreCase(permission)) {

				try {
					Method setVisible = field.getType().getMethod("setVisible", boolean.class);
					if (setVisible != null) {
						try {
							field.setAccessible(true);
							setVisible.setAccessible(true);
							setVisible.invoke(field.get(instance), true);
							field.setAccessible(false);
							return;
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
			}
			try {
				Method setVisible = field.getType().getMethod("setVisible", boolean.class);
				if (setVisible != null) {
					try {
						field.setAccessible(true);
						setVisible.setAccessible(true);
						setVisible.invoke(field.get(instance), false);
						field.setAccessible(false);
						return;
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}

}
