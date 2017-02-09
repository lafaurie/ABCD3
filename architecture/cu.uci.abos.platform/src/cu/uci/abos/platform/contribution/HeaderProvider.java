/*
 * @(#)HeaderProvider.java 1.0.0 12/09/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.platform.contribution;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.PlatformContributor;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.security.LoginException;
import cu.uci.abos.core.security.LoginService;
import cu.uci.abos.core.util.AbosImageUtil;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.Language;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.platform.l10n.AbosMessages;
import cu.uci.abos.platform.listener.DropDownSelectionListener;
import cu.uci.abos.platform.listener.LocaleResetListener;

public class HeaderProvider implements PlatformContributor {
	private  PlatformContributor footer;

	private final class LocaleMenuItemSelectionListener implements Listener {
		private static final long serialVersionUID = 3996024991064201932L;
		private final ToolItem dropDown;
		

		public LocaleMenuItemSelectionListener(ToolItem dropDown) {
			this.dropDown = dropDown;
		}

		public void handleEvent(Event event) {
			MenuItem item = (MenuItem) event.widget;
			Language language = (Language) item.getData();
			dropDown.setText(language.name);
			dropDown.setImage(language.flag);
			RWT.setLocale(language.locale);
			try {
				RWT.getSettingStore().setAttribute("locale", String.valueOf(language.index));
			} catch (IOException e) {
				e.printStackTrace();
			}
			selectedLocale = language.index;
			createContentArea();
			updateMainMenu();
			if (footer!=null) {
				footer.l10n();
			}
			service.get(ContributorService.class).l10n();
		}
	}

	public static final String HEADER_CONTROL = HeaderProvider.class.getName() + "#HEADER";
	public static final int HEADER_HEIGHT = 60;
	private Composite contentContainer;
	private Map<Integer, String> l10nCategory;
	private Map<Integer, String> l10nModule;
	private Language[] languages;
	private Composite parentContainer;
	private int selectedLocale;
	private ServiceProvider service;

	public HeaderProvider(ServiceProvider serviceProvider) {
		this.service = serviceProvider;
		initializel10n();
	}

	private Composite createAccessibillityMenu(Composite parent) {
		Composite result = new Composite(parent, SWT.None);
		result.setLayout(new FormLayout());
		FormDatas.attach(result).atRight(240).atBottom(0).atTop(0);
		ToolBar toolBar = new ToolBar(result, SWT.WRAP | SWT.FLAT);
		FormDatas.attach(toolBar).atTop(10).atRight(100).atBottom(10);
		ToolItem dropDownItem = new ToolItem(toolBar, SWT.DROP_DOWN);
		dropDownItem.setImage(languages[selectedLocale].flag);
		String[] lenguajes = { MessageUtil.unescape(AbosMessages.get().ENGLISH), MessageUtil.unescape(AbosMessages.get().SPANISH) };
		Menu dropDownMenu = createDropDownMenu(dropDownItem, lenguajes);
		dropDownItem.addListener(SWT.Selection, new DropDownSelectionListener(dropDownMenu));
		ToolBar toolBarAyuda = new ToolBar(result, SWT.WRAP | SWT.FLAT);
		FormDatas.attach(toolBarAyuda).withWidth(100).atTop(10).atLeftTo(toolBar, 10).atRight(2);
		
		ToolItem dropDownHelpItem = new ToolItem(toolBarAyuda, SWT.DROP_DOWN);
		String[] Ayuda = { AbosMessages.get().HEADER_MANUAL, AbosMessages.get().HEADER_ABOUT_US };
		dropDownHelpItem.setText(AbosMessages.get().HEADER_HELP);
		Menu dropDownMenuHelp = createDropDownMenuHelp(dropDownHelpItem, Ayuda);
		dropDownHelpItem.addListener(SWT.Selection, new DropDownSelectionListener(dropDownMenuHelp));
		return result;
	}

	private void createBanner() {
		Label logo = new Label(contentContainer, SWT.NONE);
		logo.setData(RWT.CUSTOM_VARIANT, "Banner");
		FormDatas.attach(logo).withWidth(140).withHeight(60).atLeft(20);
		if (contentContainer.getDisplay().getBounds().width >= 800) {
			Label grafica = new Label(contentContainer, SWT.NONE);
			grafica.setData(RWT.CUSTOM_VARIANT, "applicationName");
			grafica.setText(MessageUtil.unescape(AbosMessages.get().HEADER_SYSTEM_NAME));
			FormDatas.attach(grafica).atLeftTo(logo, 15).atTop(15);
			if (contentContainer.getDisplay().getBounds().width > 1000) {
				Label gradient = new Label(contentContainer, SWT.NONE);
				gradient.setData(RWT.CUSTOM_VARIANT, "gradient");
				FormDatas.attach(gradient).withHeight(60).withWidth(200).atLeftTo(grafica, contentContainer.getDisplay().getBounds().width - 1000);
			}
		}
	}

	private void createContentArea() {
		if (contentContainer != null) {
			Control[] controls = contentContainer.getChildren();
			for (Control auxControl : controls) {
				auxControl.dispose();
			}
		} else if (contentContainer == null) {
			contentContainer = new Composite(parentContainer, SWT.INHERIT_DEFAULT);
			contentContainer.setLayout(new FormLayout());
		}
		createBanner();
		Composite accessibillity = createAccessibillityMenu(contentContainer);
		createProfileMenu(contentContainer, accessibillity);
		contentContainer.layout();
	}

	private Menu createDropDownMenu(ToolItem dropDown, String[] lenguajes) {
		Menu menu = new Menu(dropDown.getParent().getShell(), SWT.POP_UP);
		for (int i = 0; i < languages.length; i++) {
			MenuItem item = new MenuItem(menu, SWT.PUSH);
			item.setText(lenguajes[i]);
			item.setImage(languages[i].flag);
			item.setData(languages[i]);
			item.addListener(SWT.Selection, new LocaleMenuItemSelectionListener(dropDown));
		}
		menu.getItem(selectedLocale).setSelection(true);
		return menu;
	}

	
	private Menu createDropDownMenuHelp(ToolItem dropDown, String[] options) {
		Menu menu = new Menu(dropDown.getParent().getShell(), SWT.POP_UP);
		for (int i = 0; i < options.length; i++) {
			MenuItem item = new MenuItem(menu, SWT.PUSH);
			item.setText(options[i]);
			item.setData(options[i]);
			if (i == 1) {
				item.addSelectionListener(new SelectionAdapter() {
					private static final long serialVersionUID = 1L;
					@Override
					public void widgetSelected(SelectionEvent e) {
						HeaderProvider.this.service.get(ContributorService.class).selectContributor("aboutUsID");
						
						
						//try {
							//LoginService loginService = service.get(LoginService.class);
							//if (loginService.isEnabled()) {
							//	if (loginService != null) {
							//		loginService.logout();
							//	}
							//}
						//} //catch (LoginException e1) {
							// do nothing
							// FIXME OIGRES ARREGLAR ESTO, respecto a cuantos
							// fallos hacer algo....como enviar correo al
							// usuario
						//}
					}
				});
			} else {
				
				item.addSelectionListener(new SelectionAdapter() {
					private static final long serialVersionUID = 1L;
					@Override
					public void widgetSelected(SelectionEvent e) {
						HeaderProvider.this.service.get(ContributorService.class).selectContributor("manualsID");
						/*
						ServiceProvider providerService = ServiceProviderUtil.getService(ServiceProvider.class);
						ContributorService pageService = providerService.get(ContributorService.class);
						String permission = pageService.getCurrentContributorID();
						if(!permission.equals("default")){
						IPermissionService permissionService = ServiceProviderUtil.getService(IPermissionService.class);
						Long idNomenclator = permissionService.readPermissionByName(permission).getModule().getNomenclatorID();
						File folder = new File(System.getProperty("java.io.tmpdir"));
						
						String urlManuales = folder.getAbsolutePath().substring(0, folder.getAbsolutePath().length()-8)+"abcdconfig/manuales/";
							switch (idNomenclator.intValue()) {
							case 911:
                                //Catalogación
								//URLUtil.download(urlManuales, "CIGED_ABCD_3.0_Manual_Usuario_Módulo_Administracion_Biblioteca.pdf", ReportType.PDF);
								break;
							case 912:
                                //Adquisición
								//URLUtil.download(urlManuales, "CIGED_ABCD_3.0_Manual_Usuario_Módulo_Administracion_Biblioteca.pdf", ReportType.PDF);
								break;
							case 913:
                                //Circulación
								URLUtil.download(urlManuales, "CIGED_ABCD_3.0_Manual_Usuario_Módulo_Circulación.pdf","Módulo Circulación", ReportType.PDF);
								break;
							case 915:
                                //Biblioteca
						        URLUtil.download(urlManuales, "CIGED_ABCD_3.0_Manual_Usuario_Módulo_Administracion_Biblioteca.pdf", "Módulo Administración de Biblioteca", ReportType.PDF);
								break;
							case 916:
                                //Seguridad
								URLUtil.download(urlManuales, "CIGED_ABCD_3.0_Manual_Usuario_Módulo_Seguridad.pdf", "Módulo Seguridad", ReportType.PDF);
								break;
							case 917:
                                //Nomencladores
								URLUtil.download(urlManuales, "CIGED_ABCD_3.0_Manual_Usuario_Módulo_Nomencladores.pdf", "Módulo Nomencladores", ReportType.PDF);
								break;
							case 919:
                                //Estadística
								URLUtil.download(urlManuales, "CIGED_ABCD_3.0_Manual_Usuario_Módulo_Estadísticas.pdf", "Módulo Estadísticas", ReportType.PDF);
								break;

							default:
								break;
							}
						}
						*/
					}
				});
			}
		}

		return menu;
	}
	
	private Menu createDropDownMenuProfile(ToolItem dropDown, String[] options) {
		Menu menu = new Menu(dropDown.getParent().getShell(), SWT.POP_UP);
		for (int i = 0; i < options.length; i++) {
			MenuItem item = new MenuItem(menu, SWT.PUSH);
			item.setText(options[i]);
			item.setData(options[i]);
			if (i == 1) {
				item.addSelectionListener(new SelectionAdapter() {
					private static final long serialVersionUID = 1L;
					@Override
					public void widgetSelected(SelectionEvent e) {
						try {
							LoginService loginService = service.get(LoginService.class);
							if (loginService.isEnabled()) {
								if (loginService != null) {
									loginService.logout();
								}
							}
						} catch (LoginException e1) {
							// do nothing
							// FIXME OIGRES ARREGLAR ESTO, respecto a cuantos
							// fallos hacer algo....como enviar correo al
							// usuario
						}
						
					}
				});
			} else {
				item.addSelectionListener(new SelectionAdapter() {
					private static final long serialVersionUID = 1L;
					@Override
					public void widgetSelected(SelectionEvent e) {
						HeaderProvider.this.service.get(ContributorService.class).selectContributor("myPerfilID");
					}
				});
			}
		}

		return menu;
	}

	private Composite createProfileMenu(Composite parent, Composite accessibillity) {
		Composite result = new Composite(parent, SWT.NONE);
		result.setLayout(new FormLayout());
		result.setData(RWT.CUSTOM_VARIANT, "Profile");
		FormDatas.attach(result).atRightTo(accessibillity).atRight().withWidth(240);

		Label usuario = new Label(result, SWT.NONE);
		usuario.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		FormDatas.attach(usuario).withWidth(60).withHeight(60);
		Image photo = SecurityUtils.getService().getPrincipal().getPhoto();
		if (photo == null) {
			photo = new Image(Display.getDefault(), AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/photo.png", false).getImageData().scaledTo(60, 60));
		}
		usuario.setImage(photo);
		String[] User = { AbosMessages.get().HEADER_PROFILE, AbosMessages.get().HEADER_LOG_OFF };
		ToolBar toolBarPerfil = new ToolBar(result, SWT.WRAP | SWT.FLAT);
		FormDatas.attach(toolBarPerfil).withWidth(140).atTopTo(usuario, 0, SWT.CENTER).atLeftTo(usuario, 12);
		ToolItem dropDownProfileItem = new ToolItem(toolBarPerfil, SWT.DROP_DOWN);
		dropDownProfileItem.setWidth(132);
		LoginService loginService = service.get(LoginService.class);

		if (loginService.isEnabled()) {
			String usuarioA = (String) loginService.getPrincipal().getName();
			if (usuarioA == null) {
				usuarioA = "No database";
			}
			dropDownProfileItem.setText(usuarioA);
		} else {
			// FIXME OIGRES ARREGLAR ESTO
			dropDownProfileItem.setText("Deshabilitado");
		}
		Menu dropDownMenuProfile = createDropDownMenuProfile(dropDownProfileItem, User);
		dropDownProfileItem.addListener(SWT.Selection, new DropDownSelectionListener(dropDownMenuProfile));
		return result;
	}

	@Override
	public Control createUIControl(Composite parent) {
		initLanguages(parent.getDisplay());
		parentContainer = parent;
		parentContainer.addListener(SWT.Dispose, new LocaleResetListener());
		createContentArea();
		return contentContainer;
	}

	@Override
	public String getID() {
		return HEADER_CONTROL;
	}

	void initializel10n() {
		l10nCategory = new HashMap<Integer, String>();
		l10nCategory.put(0, "categoryEn");
		l10nCategory.put(1, "categoryEs");

		l10nModule = new HashMap<Integer, String>();
		l10nModule.put(0, "moduleNameEn");
		l10nModule.put(1, "moduleNameEs");
	}

	private void initLanguages(Display display) {
		languages = new Language[] { new Language(Locale.ENGLISH, AbosImageUtil.loadImage(null, display, "abcdconfig/resources/en-flag.png", false), 0),
				new Language(new Locale("es", "ES", ""), AbosImageUtil.loadImage(null, display, "abcdconfig/resources/es-flag.png", false), 1) };
			if (RWT.getLocale().equals(Locale.ENGLISH)) {
				selectedLocale = 0;
			} else {
				selectedLocale = 1;
			}
	}

	@Override
	public void l10n() {
		
	}
	
	void updateMainMenu() {
		Document xmlDocument = MainMenu.LoadXml("menu");
		Integer localeInteger = selectedLocale;
		MainMenu mainMenu = service.get(MainMenu.class);
		ExpandBar expandBarControl = mainMenu.bar;
		ExpandItem[] moduleExpandItems = expandBarControl.getItems();
		Control[] childTrees = expandBarControl.getChildren();
		
		for (int i = 0; i < childTrees.length; i++) {

			ExpandItem item = moduleExpandItems[i];
			String moduleName = item.getText();
			String moduleId = (String) item.getData(moduleName);
			Element moduleElement = xmlDocument.getElementById(moduleId);
			String updatedModuleName = moduleElement.getAttribute(l10nModule.get(localeInteger));
			item.setText(updatedModuleName);
			item.setData(updatedModuleName, moduleId);
			
			if (item.getExpanded()) {
				item.setExpanded(false);
				item.setExpanded(true);
			} else {
				item.setExpanded(true);
				item.setExpanded(false);
			}

			Tree itemTree = (Tree) childTrees[i];
			TreeItem[] leaf = itemTree.getItems();
			for (int j = 0; j < leaf.length; j++) {
				visitor(leaf[j], xmlDocument, localeInteger);
			}
		}
	}

	void visitor(TreeItem item, Document xml, Integer locale) {
		if (item == null) {
			return;
		}
		if (item.getItemCount() == 0) {
			Contributor contributor = (Contributor) item.getData("contributor");
			item.setText(contributor.contributorName());
			item.setData(RWT.CUSTOM_VARIANT, "menu_item");
			return;
		}
		String category = item.getText();
		String id = (String) item.getData(category);
		Element element = (Element) xml.getElementById(id);
		String text = element.getAttribute(l10nCategory.get(locale));
		item.setText(text);
		item.setData(text, id);

		for (int i = 0; i < item.getItemCount(); i++) {
			TreeItem item2 = item.getItem(i);
			visitor(item2, xml, locale);
		}
	}
	
	public PlatformContributor getFooter() {
		return footer;
	}

	public void setFooter(PlatformContributor footer) {
		this.footer = footer;
	}

}