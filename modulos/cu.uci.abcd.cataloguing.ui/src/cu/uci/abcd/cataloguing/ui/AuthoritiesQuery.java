package cu.uci.abcd.cataloguing.ui;

import java.util.HashMap;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.listener.EventKeySimpleAuthoritiesQuery;
import cu.uci.abcd.cataloguing.listener.EventSimpleAuthoritiesQuery;
import cu.uci.abcd.cataloguing.listener.EventStartRecord;
import cu.uci.abcd.cataloguing.util.CataloguingUtil;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.l10n.cataloguing.AbosMessages;
import cu.uci.abos.l10n.cataloguing.MessageUtil;
import cu.uci.abos.widget.template.util.BibliographicConstant;

public class AuthoritiesQuery extends ContributorPage {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */
	
	private String dataBaseName = BibliographicConstant.AUTHORITIES_DATABASE;
	
	public AuthoritiesQuery() {
		super();
		properties = new HashMap<String, Object>();
		properties.put(NOT_SCROLLED, Boolean.TRUE);
	}

	@Override
	public String contributorName() {
		// TODO Auto-generated method stub
		return AbosMessages.get().AUTHORITIES_QUERY;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return CataloguingUtil.AUTHORITIES_QUERY_CONTRIBUTION_ID;
	}

	@Override
	public Control createUIControl(Composite parent) {
		
		addComposite(parent);

		Composite father = new Composite(parent, 0);
		father.setLayout(new FormLayout());
		father.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(father).atLeft(0).atRight(0);
		
        ToolBar bar = new ToolBar(father, SWT.WRAP|SWT.FLAT);
		
		ToolItem startView = new ToolItem(bar, 0);
		Image startViewImage = new Image(father.getDisplay(), RWT.getResourceManager().getRegisteredContent("list-alt"));
		startView.setImage(startViewImage);
		startView.setToolTipText(AbosMessages.get().TOOL_ITEM_MAIN_VIEW);
		
		FormDatas.attach(bar).atTop(0).atLeft(5);
		
		startView.addListener(SWT.Selection, new EventStartRecord(father, (ProxyController) controller));
		
		Text text = new Text(father, SWT.SEARCH | SWT.ICON_SEARCH);
		text.setMessage(MessageUtil
				.unescape(AbosMessages.get().TEXT_AUTHORITIES_SEARCH));
		FormDatas.attach(text).atTopTo(father, 5).atLeftTo(bar, 10)
		.withWidth(450).withHeight(30);

		Button accept = new Button(father, SWT.PUSH);
		accept.setText(AbosMessages.get().BUTTON_SEARCH);
		FormDatas.attach(accept).atTopTo(father, 20).atLeftTo(text, 10)
		.withHeight(25);
		
		accept.addListener(SWT.Selection, new EventSimpleAuthoritiesQuery((ProxyController) controller, dataBaseName,father,
				text));

		text.addKeyListener(new EventKeySimpleAuthoritiesQuery((ProxyController) controller, dataBaseName, father, text));
		
		parent.layout(true);
		return parent;
	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {
	}

	@Override
	public void setViewController(ViewController arg0) {
		this.controller = arg0;
	}
	
	public void setController(ProxyController controller){
		this.controller = controller;
	}
}
