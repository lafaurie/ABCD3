package cu.uci.abos.core.navigation.impl;

import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.core.navigation.ViewNavigation;
import cu.uci.abos.core.util.ServiceProviderUtil;

public class ViewNavigationImpl implements ViewNavigation{

	private  ContributorService service;
	
	private final static ViewNavigation navigator= new ViewNavigationImpl();

	private ViewNavigationImpl() {
		super();
		service = ServiceProviderUtil.getService(ContributorService.class);
	}

	@Override
	public void navigateTo(String contributor) {
		service.selectContributor(contributor);
	}

	public static synchronized ViewNavigation get() {
		return navigator;
	}
	

	

}
