/*
 * @(#)ContributorFactoryImpl.java 1.1.1 12/09/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.core.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.osgi.framework.ServiceReference;

import cu.uci.abos.api.exception.AbosInstantiationException;
import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IContributorFactory;
import cu.uci.abos.ui.api.IViewController;

/**
 * 
 * @author Jose Rolando Lafaurie Olivares
 * @see IContributorFactory
 */
public class ContributorFactoryImpl implements IContributorFactory {
	private static final String CLASS = "class";
	private IViewController viewController;
	private Class<IContributor> moduleUIContributorClass;

	@Override
	public IContributor create() throws AbosInstantiationException {
		IContributor resultContributor = null;
		try {
			resultContributor = moduleUIContributorClass.newInstance();
			injectViewController(resultContributor);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			throw new AbosInstantiationException(e);
		}
		return resultContributor;
	}

	@Override
	public void injectViewController(IContributor contributor) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = getMethod(contributor.getClass());
		if (method != null) {
			method.setAccessible(true);
			method.invoke(contributor, viewController);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void injectServiceReference(ServiceReference<IContributorFactory> contributorServiceReference) throws ClassNotFoundException {
		String className = (String) contributorServiceReference.getProperty(CLASS);
		moduleUIContributorClass = (Class<IContributor>) contributorServiceReference.getBundle().loadClass(className);
		setViewController((IViewController) contributorServiceReference.getProperty("viewController"));
	}

	private void setViewController(IViewController viewController) {
		this.viewController = viewController;
	}

	private Method getMethod(Class<? extends IContributor> clazz) {
		Method result = null;
		try {
			result = clazz.getDeclaredMethod("setViewController", IViewController.class);
		} catch (SecurityException ignore) {
			// nothing to inject
		} catch (NoSuchMethodException ignore) {
			// nothing to inject
		}
		return result;
	}
}
