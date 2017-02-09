package cu.uci.abcd.opac.ui.contribution;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.PlatformContributor;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.util.AbosImageUtil;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abcd.opac.ui.SecurityUtils;

public class OpacMenuBarProvider implements PlatformContributor {
	public static final String MENU_BAR_CONTROL = OpacMenuBarProvider.class.getName() + "#MENUBAR";
	// NOTE: this value reflects the height of the menubar_background image set
	// via css
	public static final int MENU_BAR_HEIGHT = 35;
	private static final String MENUBAR_BACKGROUND = "menubar_background";

	private ServiceProvider serviceProvider;
	private ContributorService pageService;

	Composite result;
	User user;
	Button recentAcquisitionBtn;
	Button selectionListBtn;
	Button selectionBtn;
	Button settingsBtn;
     
	//private ToolBar statisticTb;
	//private ToolItem dropDownStatisticItem;

	public String cantSelection = "";

	Image image;

	public OpacMenuBarProvider(ServiceProvider serviceProvider) {
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
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = new Composite(parent, SWT.None);
		result.layout(true, true);
		result.setData(RWT.CUSTOM_VARIANT, MENUBAR_BACKGROUND);
		result.setLayout(new FormLayout());

		createMenuBar();
		return result;
	}

	public void createMenuBar() {

		pageService = serviceProvider.get(ContributorService.class);

		Control[] temp = result.getChildren();

		for (Control control : temp)
			control.dispose();

		image = AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/sign-in.png", false);
		recentAcquisitionBtn = new Button(result, SWT.PUSH);
		recentAcquisitionBtn.setImage(image);
		recentAcquisitionBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(recentAcquisitionBtn).atTop(3).atLeft(300);

		image = AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/list-alt.png", false);
		selectionListBtn = new Button(result, SWT.PUSH);
		selectionListBtn.setImage(image);
		selectionListBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(selectionListBtn).atTop(3).atLeftTo(recentAcquisitionBtn, 5);

		image = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("opac-add-selection"));
		selectionBtn = new Button(result, SWT.PUSH);
		selectionBtn.setImage(image);
		selectionBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		selectionBtn.setEnabled(false);
		FormDatas.attach(selectionBtn).atTop(3).atLeftTo(selectionListBtn, 5);

		/*image = AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/opac_statistic.png", false);
		String[] options = { MessageUtil.unescape(AbosMessages.get().UI_MOST_CONSULTED_TOPICS), MessageUtil.unescape(AbosMessages.get().UI_MOST_BORROWED_TITLES) };

		Label statisticImg = new Label(result, 0);
		statisticImg.setImage(image);
		FormDatas.attach(statisticImg).atTop(10).withHeight(15).withWidth(15).atLeftTo(selectionBtn, 3);

		statisticTb = new ToolBar(result, 0);
		statisticTb.setData(RWT.CUSTOM_VARIANT, "opac-statistic");
		FormDatas.attach(statisticTb).atTop(8).atLeftTo(statisticImg);
    
		dropDownStatisticItem = new ToolItem(statisticTb, SWT.DROP_DOWN);
		dropDownStatisticItem.setData(RWT.CUSTOM_VARIANT, "opac-statistic");
   
		Menu dropDownMenuProfile = createDropDownMenuText1(dropDownStatisticItem, options);
		dropDownStatisticItem.addListener(SWT.Selection, new DropDownSelectionListener(dropDownMenuProfile));
*/
		image = AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/gear.png", false);
		settingsBtn = new Button(result, SWT.PUSH);
		settingsBtn.setImage(image);
		settingsBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(settingsBtn).atTop(3).atLeftTo(selectionBtn, 3);

		recentAcquisitionBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				pageService.selectContributor("RecentAcquisitionsID");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		selectionListBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				pageService.selectContributor("ListasDeSeleccionID");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		selectionBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				Selection seleccion = (Selection) ((OpacContributorServiceImpl) pageService).getContributorMap().get("SelectionID");

				if (seleccion.records.size() != 0) {

					pageService.selectContributor("SelectionID");
					seleccion.l10n();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		settingsBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				pageService.selectContributor("ConfigurationOptionID");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		l10n();
	}

	@Override
	public String getID() {
		return MENU_BAR_CONTROL;
	}

	@Override
	public void l10n() {
		recentAcquisitionBtn.setText(MessageUtil.unescape(AbosMessages.get().UI_RECENT_ACQUISITIONS));
		selectionListBtn.setText(MessageUtil.unescape(AbosMessages.get().UI_MY_SELECTION_LIST));
		selectionBtn.setText(MessageUtil.unescape(AbosMessages.get().UI_SELECTION + " " + cantSelection));
	//	dropDownStatisticItem.setText(MessageUtil.unescape(AbosMessages.get().UI_STATISTIC));

		if (cantSelection == "") {
			selectionBtn.setEnabled(false);
		} else {
			selectionBtn.setEnabled(true);

		}

		settingsBtn.setText(MessageUtil.unescape(AbosMessages.get().UI_CONFIGURATION_OPTIONS));

		update();
	}
/*
	private Menu createDropDownMenuText1(ToolItem dropDown, String[] options) {
		Menu menu = new Menu(dropDown.getParent().getShell(), SWT.POP_UP);

		for (int i = 0; i < options.length; i++) {
			MenuItem item = new MenuItem(menu, SWT.PUSH);
			item.setText(options[i]);
			item.setData(options[i]);
			if (i == 0) {
				item.addSelectionListener(new SelectionAdapter() {
					private static final long serialVersionUID = 1L;
   
					@Override
					public void widgetSelected(SelectionEvent e) {
   
					//	pageService.selectContributor("MostConsultedTopicsID");
					}
				});
			} else if (i == 1) {
				item.addSelectionListener(new SelectionAdapter() {
					private static final long serialVersionUID = 1L;

					@Override
					public void widgetSelected(SelectionEvent e) {

					//	pageService.selectContributor("MostBorrowedTitlesID");
					}
				});
			}
		}

		return menu;
	}*/
}