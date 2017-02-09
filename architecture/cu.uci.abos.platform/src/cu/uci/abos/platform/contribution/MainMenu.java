package cu.uci.abos.platform.contribution;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.PlatformContributor;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.security.LoginService;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.platform.domain.MenuEntity;
import cu.uci.abos.platform.listener.MainMenuResizeListener;
import cu.uci.abos.platform.tracker.TreeMenuContributorTracker;

public class MainMenu implements PlatformContributor {
	public static final String MAIN_MENU_CONTROL = MainMenu.class.getName() + "#MAINMENU";
	static final int MAIN_MENU_WIDTH = 140;
	private final ServiceProvider serviceProvider;

	private Map<String, MenuEntity> menuBuilder;
	private LoginService loginService;
	public ExpandBar bar;

	public MainMenu(ServiceProvider serviceProvider, LoginService loginService) {
		super();
		this.serviceProvider = serviceProvider;
		this.loginService = loginService;
		menuBuilder = new HashMap<String, MenuEntity>();

	}

	@Override
	public Control createUIControl(Composite parent) {

		final Composite composite = new Composite(parent, SWT.V_SCROLL);
		composite.setLayout(new FormLayout());
		composite.setData(RWT.CUSTOM_VARIANT, "main_menu_background");
		final Button button = new Button(composite, SWT.ARROW_LEFT);
		button.setData(RWT.CUSTOM_VARIANT, "hide_expander_button");
		FormDatas.attach(button).withWidth(16).withHeight(16).atRight(0);

		// expander

		bar = new ExpandBar(composite, SWT.V_SCROLL);
		bar.addExpandListener(new ExpandListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void itemExpanded(ExpandEvent e) {
				for (int i = 0; i < bar.getItemCount(); i++) {
					if (!bar.getItem(i).equals(e.item)) {
						bar.getItem(i).setExpanded(false);
					}
				}
			}
			
			@Override
			public void itemCollapsed(ExpandEvent e) {
				
			}
		});
		FormDatas.attach(bar).atRight(0).atTopTo(button, 5).atBottom(10).atLeft(10);
		button.addListener(SWT.Selection, new MainMenuResizeListener());

		Document document = LoadXml("menu");

		NodeList rootChildren = document.getChildNodes();

		@SuppressWarnings("unchecked")
		List<String> permissions = (List<String>) loginService.getPrincipal().getPermission();
	

		for (int i = 0; i < rootChildren.getLength(); i++) {
			if (rootChildren.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element rootElement = (Element) rootChildren.item(i);
				visitor(rootElement, permissions);
			}

		}

		final ContributorService pageService = serviceProvider.get(ContributorService.class);
		pageService.addContributorTracker(new TreeMenuContributorTracker(bar, pageService, menuBuilder));

		return composite;
	}

	@SuppressWarnings("unused")
	void visitor(Node node, List<String> permissionsList) {
		
		
		String lenguage = RWT.getSettingStore().getAttribute("locale");
		int posLanguage = -1;
		
		//if( lenguage==null ){   // Me guio por el navegador
			if( RWT.getLocale().equals(Locale.ENGLISH) ){
				posLanguage = 0;
			}else{
				posLanguage = 1;
			}
			//arreglar con un switch
		//}else{
		//	posLanguage = Integer.parseInt(lenguage);
		//}
		
		
		//espannol es 1 y 2
		//ingles 0 y 1
		
		if (node == null) {
			return;
		}
		NodeList nodes = node.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			String claseObject = n.getClass().toString();
			if (n == null) {
				continue;
			}
			if (claseObject.contains("DeferredTextImpl")) {
				continue;
			}
			if (n.getChildNodes().getLength() == 0) {
				if (menuBuilder.get(n.getNodeName()) == null) {

					/*
                     * FIXME lenguaje
                     */
                    String containerMenu = n.getParentNode().getParentNode().getAttributes().item(posLanguage+1).getNodeValue();
					//String containerMenu = n.getParentNode().getParentNode().getAttributes().item(2).getNodeValue();

					String containerMenuId = n.getParentNode().getParentNode().getAttributes().item(0).getNodeValue();
                    /*
                     * FIXME lenguaje
                    */
					String containerCategory = n.getParentNode().getAttributes().item(posLanguage).getNodeValue();
					//String containerCategory = n.getParentNode().getAttributes().item(1).getNodeValue();
					
					Integer categoryOrder= Integer.valueOf(n.getParentNode().getAttributes().item(2).getNodeValue());

					String containerCategoryId = n.getParentNode().getAttributes().item(3).getNodeValue();

					String functionality = n.getAttributes().item(0).getNodeValue();
					Integer index = Integer.valueOf(n.getParentNode().getParentNode().getAttributes().item(3).getNodeValue());
					MenuEntity entity = new MenuEntity(containerMenu, containerCategory, functionality, containerCategoryId, containerMenuId,index,categoryOrder);

					if (permissionsList.contains(functionality)) {
						menuBuilder.put(functionality, entity);
					}
				}
			}
			visitor(n, permissionsList);
		}
	}

	public static Document LoadXml(String path) {
		InputStream src = RWT.getResourceManager().getRegisteredContent(path);
		Document document = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);
		try {
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		} catch (ParserConfigurationException e2) {
			e2.printStackTrace();
		}
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		try {
			document = builder.parse(src);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}

	@Override
	public String getID() {
		return MAIN_MENU_CONTROL;
	}

	@Override
	public void l10n() {

	}
}
