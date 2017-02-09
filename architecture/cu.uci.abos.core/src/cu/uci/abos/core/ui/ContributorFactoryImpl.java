/*
 * @(#)ContributorFactoryImpl.java 1.1.1 12/09/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.core.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.osgi.framework.ServiceReference;

import cu.uci.abos.api.exception.AbosInstantiationException;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorFactory;
import cu.uci.abos.api.ui.ViewController;

/**
 * 
 * @author Jose Rolando Lafaurie Olivares
 * @see ContributorFactory
 */
public class ContributorFactoryImpl implements ContributorFactory {
	private static final String CLASS = "class";
	private ViewController viewController;
	private Class<Contributor> moduleUContributorClass;

	@Override
	public Contributor create() throws AbosInstantiationException {
		Contributor resultContributor = null;
		try {
			resultContributor = moduleUContributorClass.newInstance();
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
	public void injectViewController(Contributor contributor) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = getMethod( contributor.getClass());
		if (method != null) {
			method.setAccessible(true);
			method.invoke(contributor, viewController);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void injectServiceReference(ServiceReference<ContributorFactory> contributorServiceReference) throws ClassNotFoundException {
		String className = (String) contributorServiceReference.getProperty(CLASS);
		moduleUContributorClass = (Class<Contributor>) contributorServiceReference.getBundle().loadClass(className);
		setViewController((ViewController) contributorServiceReference.getProperty("viewController"));
	}

	private void setViewController(ViewController viewController) {
		this.viewController = viewController;
	}

	private Method getMethod(Class<? extends Contributor> clazz) {
		Method result = null;
		try {
			result = clazz.getMethod("setViewController", ViewController.class);
		} catch (SecurityException ignore) {
			ignore.printStackTrace();
		} catch (NoSuchMethodException ignore) {
			ignore.printStackTrace();
		}
		return result;
	}
}
