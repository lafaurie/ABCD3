package cu.uci.abcd.opac.ui.contribution;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;

import cu.uci.abcd.domain.circulation.LoanUser;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.PlatformContributor;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.security.LoginService;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;

public class OpacPerfilMenu implements PlatformContributor {
	public static final String ADQUISITION_MENU_CONTROL = OpacPerfilMenu.class.getName() + "#ADQUISITIONMENU";
	public static final int ADQUISITION_MENU_WIDTH = 160;

	private final ServiceProvider serviceProvider;
	private ViewController controller;
	private Circulation circulation;
	private User user;
	private LoanUser loanUser;

	public Composite result;
	public Composite rightLogo;
	public Composite menu;

	private LoginService login;

	private Label profileLbl;

	private Image image;

	private Button configurationDataSourcesBtn;
	private Button myCurrentState;
	private Button myHistoryState;
	private Button adquisition;
	private Button mySuggestions;
	private Button myTags;
	private Button myRecommend;

	public OpacPerfilMenu(ServiceProvider serviceProvider) {
		super();
		this.serviceProvider = serviceProvider;
	}

	public void update() {
		result.layout(true, true);
		result.redraw();
		result.update();
	}

	@Override
	public Control createUIControl(Composite parent) {

		try {

			if (SecurityUtils.getService() != null)
				login = SecurityUtils.getService();

		} catch (Exception e) {
			e.printStackTrace();
		}

		result = new Composite(parent, SWT.NORMAL);
		result.setLayout(new FormLayout());
		result.setData(RWT.CUSTOM_VARIANT, "workspace_content");

		rightLogo = new Composite(result, SWT.NORMAL);
		rightLogo.setLayout(new FormLayout());
		rightLogo.setData(RWT.CUSTOM_VARIANT, "workspace_content");
   
		if (login.isLoggedIn())
			rightLogo.setVisible(false);
		else
			rightLogo.setVisible(true);

		result.setBackground(rightLogo.getBackground());

		FormDatas.attach(rightLogo).atLeft(-25).atTop().atBottom().atRight(-20);

		menu = new Composite(result, SWT.V_SCROLL);
		menu.setLayout(new FormLayout());
		menu.setData(RWT.CUSTOM_VARIANT, "workspace_content");
       
		FormDatas.attach(menu).atTop().atLeft(-10).atBottom(-10).atRight(-25);

		Label picture = new Label(rightLogo, SWT.NONE);
		picture.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		String src = RWT.getResourceManager().getLocation("opac-main-derecho");
		picture.setText("<img  width='" + 130 + "' height='" + 220 + "' src='" + src + "'></img> ");

		FormDatas.attach(picture).atLeft(-20).atTop(23);

		createPerfilMenu();

		return result;
	}

	public void createPerfilMenu() {

		final ContributorService pageService = serviceProvider.get(ContributorService.class);

		try {

			Control[] temp = ((Composite) result.getChildren()[1]).getChildren();

			for (int i = 0; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
			e.printStackTrace();
		}

		profileLbl = new Label(menu, SWT.NONE);
		profileLbl.setFont(new Font(result.getDisplay(), "Arial", 14, SWT.BOLD));
		FormDatas.attach(profileLbl).atLeft();

		Link horSeparator = new Link(menu, SWT.NORMAL);
		horSeparator.setText("______________________________________________________________________________________________________________________________________________________");
		horSeparator.setFont(new Font(menu.getDisplay(), "Arial", 3, SWT.NONE));
		FormDatas.attach(horSeparator).atTopTo(profileLbl, -5).atLeft();

		image = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("opac-resume"));
		myCurrentState = new Button(menu, 0);
		myCurrentState.setImage(image);
		myCurrentState.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		myCurrentState.setFont(new Font(result.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(myCurrentState).atTopTo(profileLbl, 8).atLeft(-20);

		myHistoryState = new Button(menu, 0);
		myHistoryState.setImage(image);
		myHistoryState.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		myHistoryState.setFont(new Font(result.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(myHistoryState).atTopTo(myCurrentState, -2).atLeft(-20);

		image = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("sign-out"));
		adquisition = new Button(menu, 0);
		adquisition.setImage(image);
		adquisition.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		adquisition.setFont(new Font(result.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(adquisition).atTopTo(myHistoryState, -2).atLeft(-20);

		mySuggestions = new Button(menu, 0);
		mySuggestions.setImage(image);
		mySuggestions.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		mySuggestions.setFont(new Font(result.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(mySuggestions).atTopTo(adquisition, -2).atLeft(-20);

		image = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("tags"));
		myTags = new Button(menu, 0);
		myTags.setImage(image);
		myTags.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		myTags.setFont(new Font(result.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(myTags).atTopTo(mySuggestions, -2).atLeft(-20);
      
		image = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("opac-recommend"));
		myRecommend = new Button(menu, 0);
		myRecommend.setImage(image);
		myRecommend.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		myRecommend.setFont(new Font(result.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(myRecommend).atTopTo(myTags, -2).atLeft(-20);
            
		image = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("opac-add-selection"));
		configurationDataSourcesBtn = new Button(menu, SWT.PUSH | SWT.WRAP);
		configurationDataSourcesBtn.setImage(image);
		configurationDataSourcesBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		configurationDataSourcesBtn.setFont(new Font(result.getDisplay(), "Arial", 12, SWT.BOLD));
		configurationDataSourcesBtn.setVisible(false);
		FormDatas.attach(configurationDataSourcesBtn).atTopTo(myRecommend, -2).atRight(-5).atLeft(-20);

		myCurrentState.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (user.getPerson() != null) {

					circulation = (Circulation) ((OpacContributorServiceImpl) pageService).getContributorMap().get("CirculationID");
					controller = circulation.getController();
					loanUser = ((AllManagementOpacViewController) controller).findLoanUserByPersonIdAndIdLibrary(user.getPerson().getPersonID(), user.getLibrary().getLibraryID());

					if (loanUser != null)
						pageService.selectContributor("MyCurrentStateID");
					else						
						MessageDialogUtil.openError(Display.getCurrent().getActiveShell(), MessageUtil.unescape(AbosMessages.get().MSG_ERROR), MessageUtil.unescape(AbosMessages.get().MSG_ERROR_NEED_BE_LOAN_USER), null);

				} else
					MessageDialogUtil.openError(Display.getCurrent().getActiveShell(), MessageUtil.unescape(AbosMessages.get().MSG_ERROR), MessageUtil.unescape(AbosMessages.get().MSG_ERROR_NEED_BE_LOAN_USER), null);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		myHistoryState.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (user.getPerson() != null) {

					circulation = (Circulation) ((OpacContributorServiceImpl) pageService).getContributorMap().get("CirculationID");
					controller = circulation.getController();
					loanUser = ((AllManagementOpacViewController) controller).findLoanUserByPersonIdAndIdLibrary(user.getPerson().getPersonID(), user.getLibrary().getLibraryID());

					if (loanUser != null)
						pageService.selectContributor("MyHistoryStateID");
					else
						MessageDialogUtil.openError(Display.getCurrent().getActiveShell(), MessageUtil.unescape(AbosMessages.get().MSG_ERROR), MessageUtil.unescape(AbosMessages.get().MSG_ERROR_NEED_BE_LOAN_USER), null);
				} else
					MessageDialogUtil.openError(Display.getCurrent().getActiveShell(), MessageUtil.unescape(AbosMessages.get().MSG_ERROR), MessageUtil.unescape(AbosMessages.get().MSG_ERROR_NEED_BE_LOAN_USER), null);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		adquisition.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				pageService.selectContributor("AdquisicionID");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		mySuggestions.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				pageService.selectContributor("listViewSugerenciaID");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		myTags.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				pageService.selectContributor("MisEtiquetasID");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		myRecommend.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				pageService.selectContributor("MyRecomendationID");

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		configurationDataSourcesBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (user.getUsername().equals("opac"))
					pageService.selectContributor("ConfigurationDataSourcesID");
				else
					MessageDialogUtil.openError(Display.getCurrent().getActiveShell(), MessageUtil.unescape(AbosMessages.get().MSG_ERROR), "Usted no tiene permisos para acceder a esta configuración", null);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		l10n();
		update();
	}

	@Override
	public String getID() {
		return ADQUISITION_MENU_CONTROL;
	}

	@Override
	public void l10n() {

		profileLbl.setText(MessageUtil.unescape((AbosMessages.get().P_PROFILE)));
		myCurrentState.setText(MessageUtil.unescape((AbosMessages.get().P_RESUME)));
		myHistoryState.setText(MessageUtil.unescape((AbosMessages.get().P_HISTORICAL_RESUME)));
		adquisition.setText(MessageUtil.unescape((AbosMessages.get().P_SUGGESTION)));
		mySuggestions.setText(MessageUtil.unescape((AbosMessages.get().P_MY_SUGGESTIONS)));
		myTags.setText(MessageUtil.unescape((AbosMessages.get().UI_MY_TAGS)));
		myRecommend.setText(MessageUtil.unescape((AbosMessages.get().UI_RECOMMENDATION)));
		configurationDataSourcesBtn.setText("Configurar Fuentes de Datos");

		try {
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");

			if (user != null && user.getUsername().equals("opac"))
				configurationDataSourcesBtn.setVisible(true);
			else
				configurationDataSourcesBtn.setVisible(false);

		} catch (Exception e) {
			e.printStackTrace();
		}

		update();

	}
}
