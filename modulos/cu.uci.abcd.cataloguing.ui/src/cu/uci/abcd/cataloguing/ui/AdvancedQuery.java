package cu.uci.abcd.cataloguing.ui;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.listener.EventAdvanceQuery;
import cu.uci.abcd.cataloguing.listener.EventAllwaysOpen;
import cu.uci.abcd.cataloguing.listener.EventNewAdvanceQuery;
import cu.uci.abcd.cataloguing.listener.EventStartRecord;
import cu.uci.abcd.cataloguing.util.CataloguingUtil;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.l10n.cataloguing.AbosMessages;
import cu.uci.abos.l10n.cataloguing.MessageUtil;
import cu.uci.abos.widget.advanced.query.ColorType;
import cu.uci.abos.widget.advanced.query.QueryComponent;
import cu.uci.abos.widget.template.util.BibliographicConstant;

public class AdvancedQuery extends ContributorPage {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	public AdvancedQuery() {
		super();
		properties = new HashMap<String, Object>();
		properties.put(NOT_SCROLLED, Boolean.TRUE);
	}

	@Override
	public Control createUIControl(Composite arg0) {

		addComposite(arg0);

		int width = arg0.getShell().getBounds().width - 290;
		int height = arg0.getShell().getBounds().height - 151;

		ArrayList<Button> buttons = new ArrayList<>();

		Composite father = new Composite(arg0, 0);
		father.setLayout(new FormLayout());
		father.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(father).withWidth(width).withHeight(height);

		ToolBar bar = new ToolBar(father, SWT.WRAP | SWT.FLAT);

		ToolItem startView = new ToolItem(bar, 0);
		Image startViewImage = new Image(father.getDisplay(), RWT.getResourceManager().getRegisteredContent("list-alt"));
		startView.setImage(startViewImage);
		startView.setToolTipText(AbosMessages.get().TOOL_ITEM_MAIN_VIEW);

		FormDatas.attach(bar).atTop(0).atLeft(5);

		startView.addListener(SWT.Selection, new EventStartRecord(father, (ProxyController) controller));

		ExpandBar expandBar = new ExpandBar(father, SWT.V_SCROLL | SWT.H_SCROLL);
		FormDatas.attach(expandBar).atTopTo(bar, 0).atLeft(0).atRight(0).atBottom(0);
		expandBar.addExpandListener(new EventAllwaysOpen());

		final ExpandItem expandItem = new ExpandItem(expandBar, 0);
		expandItem.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ADVANCE_QUERY));

		final Composite page = new Composite(expandBar, 0);
		page.setLayout(new FormLayout());
		page.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(page).atTop(0).atLeft(0).atRight(0).atBottom(0);

		/*
		 * Label title = new Label(father, 0); title.setText(MessageUtil
		 * .unescape(AbosMessages.get().LABEL_ADVANCE_QUERY));
		 * FormDatas.attach(title).atTopTo(father, 10).atLeftTo(father, 5);
		 */

		final Group search = new Group(page, 0);
		search.setText(AbosMessages.get().GROUP_SEARCH);
		search.setLayout(new FormLayout());
		search.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(search).atTopTo(expandBar, 10);

		Label field = new Label(search, 0);
		field.setText(AbosMessages.get().LABEL_IN_THE_FIELD);
		FormDatas.attach(field).atTopTo(search, 10).atLeftTo(search, 21);

		Label term = new Label(search, 0);
		term.setText(AbosMessages.get().LABEL_SEARCH);
		FormDatas.attach(term).atTopTo(search, 10).atLeftTo(field, 170);

		final Label logic = new Label(search, 0);
		logic.setText("Operador lógico:");
		logic.setVisible(false);
		FormDatas.attach(logic).atTopTo(search, 10).atLeftTo(term, 220);

		String[] values = new String[] { MessageUtil.unescape(AbosMessages.get().VALUE_COMBO_TITLE), MessageUtil.unescape(AbosMessages.get().VALUE_COMBO_AUTHOR), MessageUtil.unescape(AbosMessages.get().VALUE_COMBO_MATERIA), MessageUtil.unescape(AbosMessages.get().VALUE_COMBO_ISBN),
				MessageUtil.unescape(AbosMessages.get().VALUE_COMBO_ISSN), MessageUtil.unescape(AbosMessages.get().VALUE_COMBO_SIG_TOP), MessageUtil.unescape(AbosMessages.get().VALUE_COMBO_INST_NAME), MessageUtil.unescape(AbosMessages.get().VALUE_COMBO_EDITORIAL),
				MessageUtil.unescape(AbosMessages.get().VALUE_COMBO_EDITION), MessageUtil.unescape(AbosMessages.get().VALUE_COMBO_PUB_PLACE), MessageUtil.unescape(AbosMessages.get().VALUE_COMBO_NUM_VOL), MessageUtil.unescape(AbosMessages.get().VALUE_COMBO_SERIE_TITLE) };

		Composite parent = new Composite(search, 0);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		parent.setLayout(new FormLayout());
		FormDatas.attach(parent).atTopTo(term, 10).atLeftTo(search, 20);

		final QueryComponent component = new QueryComponent(parent, 0, values, ColorType.Gray);
		FormDatas.attach(component).atTopTo(parent, 0).atLeftTo(parent, 0);

		search.getShell().layout(true, true);
		search.getShell().redraw();
		search.getShell().update();
		search.pack();

		Group filter = new Group(page, 0);

		filter.setText(AbosMessages.get().GROUP_LIMIT_TO_ANY_OF_THE_FOLLOWING);
		filter.setLayout(new FormLayout());
		filter.setData(RWT.CUSTOM_VARIANT, "gray_background");

		FormDatas.attach(filter).atTopTo(search, 10).atLeft(0).atRight(0);

		RowLayout rowLayout = new RowLayout();
		rowLayout.wrap = true;
		rowLayout.pack = false;
		rowLayout.justify = true;
		rowLayout.type = SWT.HORIZONTAL;

		Composite content = new Composite(filter, 0);
		content.setLayout(rowLayout);
		content.setData(RWT.CUSTOM_VARIANT, "gray_background");

		FormDatas.attach(content).atTopTo(filter, 10).withWidth(width - 28);

		Button book = new Button(content, SWT.CHECK | SWT.WRAP);
		book.setText(AbosMessages.get().BUTTON_BOOK);
		buttons.add(book);

		Button eResource = new Button(content, SWT.CHECK | SWT.WRAP);
		eResource.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ELECTRONIC_RESOURCE));
		buttons.add(eResource);

		Button material = new Button(content, SWT.CHECK | SWT.WRAP);
		material.setText(AbosMessages.get().BUTTON_PROJECTABLE_MATERIAL);
		buttons.add(material);

		Button thesis = new Button(content, SWT.CHECK | SWT.WRAP);
		thesis.setText(AbosMessages.get().BUTTON_THESIS);
		buttons.add(thesis);

		Button kit = new Button(content, SWT.CHECK | SWT.WRAP);
		kit.setText(AbosMessages.get().BUTTON_KIT);
		buttons.add(kit);

		Button music = new Button(content, SWT.CHECK | SWT.WRAP);
		music.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_MUSIC));
		buttons.add(music);

		Button visualMaterials = new Button(content, SWT.CHECK | SWT.WRAP);
		visualMaterials.setText(AbosMessages.get().BUTTON_VISUAL_MATERIAL);
		buttons.add(visualMaterials);

		Button mixerMaterials = new Button(content, SWT.CHECK | SWT.WRAP);
		mixerMaterials.setText(AbosMessages.get().BUTTON_MIXED_MATERIAL);
		buttons.add(mixerMaterials);

		Button captographicMaterials = new Button(content, SWT.CHECK | SWT.WRAP);
		captographicMaterials.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CARTOGRAPHIC_MATERIAL));
		buttons.add(captographicMaterials);

		Button journals = new Button(content, SWT.CHECK | SWT.WRAP);
		journals.setText(AbosMessages.get().BUTTON_JOURNALS);
		buttons.add(journals);

		filter.getShell().layout(true, true);
		filter.getShell().redraw();
		filter.getShell().update();
		filter.pack();

		Composite actions = new Composite(page, 0);
		actions.setData(RWT.CUSTOM_VARIANT, "gray_background");
		actions.setLayout(new FormLayout());

		FormDatas.attach(actions).atTopTo(filter, 10).atRight(1);

		Button searchButton = new Button(actions, SWT.PUSH);
		searchButton.setText(AbosMessages.get().BUTTON_SEARCH);

		FormDatas.attach(searchButton).atTopTo(actions, 0).atLeftTo(actions, 0);

		Button newSearchButton = new Button(actions, SWT.PUSH);
		newSearchButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_NEW_SEARCH));
  
		FormDatas.attach(newSearchButton).atTopTo(actions, 0).atLeftTo(searchButton, 10);

		// events
		EventAdvanceQuery advanceQuery = new EventAdvanceQuery(component, ((ProxyController) controller), BibliographicConstant.BIBLIOGRAPHIC_DATA_BASE, content, father);

		searchButton.addListener(SWT.Selection, advanceQuery);

		newSearchButton.addListener(SWT.Selection, new EventNewAdvanceQuery(component, parent, values, advanceQuery, buttons, logic));

		// SetControl
		expandItem.setHeight(page.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 5);

		expandItem.setControl(page);
		expandItem.setExpanded(true);

		arg0.getShell().layout(true, true);
		arg0.getShell().redraw();
		arg0.getShell().update();

		search.addListener(SWT.Resize, new Listener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				if (component.getChildrens().size() > 1) {
					logic.setVisible(true);
					logic.getShell().layout(true, true);
					logic.getShell().redraw();
					logic.getShell().update();
				} else if (component.getChildrens().size() == 1) {
					logic.setVisible(false);
					logic.getShell().layout(true, true);
					logic.getShell().redraw();
					logic.getShell().update();
				}

				// SetControl
				expandItem.setHeight(page.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 5);
			}
		});

		return arg0;
	}

	@Override
	public String getID() {
		return CataloguingUtil.ADVANCED_QUERY_CONTRIBUTION_ID;
	}

	@Override
	public void l10n() {

	}

	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public String contributorName() {
		return AbosMessages.get().ADVANCED_QUERY;
	}

	@Override
	public void setViewController(ViewController arg0) {
		this.controller = arg0;
	}
}
