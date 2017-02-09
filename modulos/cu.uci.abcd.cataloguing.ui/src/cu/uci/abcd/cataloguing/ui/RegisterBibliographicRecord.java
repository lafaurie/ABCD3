package cu.uci.abcd.cataloguing.ui;

import java.util.HashMap;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DropDown;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abcd.cataloguing.IDataBaseManager;
import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.listener.EventAcquisitionIntegration;
import cu.uci.abcd.cataloguing.listener.EventAllRecords;
import cu.uci.abcd.cataloguing.listener.EventKeyRecordQuery;
import cu.uci.abcd.cataloguing.listener.EventRecordQuery;
import cu.uci.abcd.cataloguing.util.CataloguingUtil;
import cu.uci.abcd.cataloguing.util.QueryStructure;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.l10n.cataloguing.AbosMessages;
import cu.uci.abos.l10n.cataloguing.MessageUtil;
import cu.uci.abos.widget.template.util.BibliographicConstant;

public class RegisterBibliographicRecord extends ContributorPage{

	/**
	 * Created by Basilio Puentes Rodríguez
	 */
	
	private String marc21DataBase = BibliographicConstant.BIBLIOGRAPHIC_DATA_BASE;

	public RegisterBibliographicRecord() {
		super();
		properties = new HashMap<String, Object>();
		properties.put(NOT_SCROLLED, Boolean.TRUE);
	}
	
	@Override
	public Control createUIControl(Composite superArg0){

		addComposite(superArg0);
		
		Composite view = new Composite(superArg0, 0);

		view.setLayout(new FormLayout());
		view.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(view).atLeft(0).atRight(0).atTop(0).atBottom(0);

		Composite menuComposite = new Composite(view, 0);
		menuComposite.setLayout(new FormLayout());
		menuComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");

		ToolBar toolBar = new ToolBar(menuComposite, SWT.WRAP|SWT.FLAT);
		FormDatas.attach(toolBar).atTopTo(menuComposite, 0).atLeftTo(menuComposite, 0);

		new ToolItem(toolBar, SWT.SEPARATOR);
		
		Image listImage = new Image(view.getDisplay(), RWT.getResourceManager().getRegisteredContent("selection-list"));
		ToolItem seeRecords = new ToolItem(toolBar, SWT.DROP_DOWN);
		seeRecords.setImage(listImage);
		seeRecords.setText(AbosMessages.get().TOOL_ITEM_SEE_REGISTERS);
		seeRecords.setToolTipText(AbosMessages.get().TOOL_ITEM_DATA_BASES);
		
		DropDown drop2 = new DropDown(toolBar);
		
		new ToolItem(toolBar, SWT.SEPARATOR);

		ToolItem search = new ToolItem(toolBar, 0);
		Image searchImage = new Image(view.getDisplay(), RWT.getResourceManager().getRegisteredContent("search"));
		search.setImage(searchImage);
		search.setText(AbosMessages.get().TOOL_ITEM_SEARCH_RECORD_FOR_PROCESSING);
		
		new ToolItem(toolBar, SWT.SEPARATOR);
		
		List<String> dataBases = null;

		try {
			IDataBaseManager dataBaseManager = ((ProxyController)controller).getDataBaseManagerService();
			dataBases = dataBaseManager.getDatabaseNames();

		} catch (JisisDatabaseException e) {

			Shell shell = view.getShell();
			MessageDialogUtil.openError(shell, "Error", "Problema de Conección con JISIS", null);
		}

		if(dataBases != null){
			String[] values = new String[dataBases.size()];
			String priority0 = BibliographicConstant.BIBLIOGRAPHIC_DATA_BASE;
			String priority1 = BibliographicConstant.AUTHORITIES_DATABASE;
			
			int pos0 = -1;
			int pos1 = -2;

			for (int i = 0; i < dataBases.size(); i++) {
				values[i] = dataBases.get(i);
				if(values[i].equals(priority0))
					pos0 = i;
				else if(values[i].equals(priority1))
					pos1 = i;
			}
			
			if(pos0 != -1){
				String aux1 = values[0];
				values[0] = values[pos0];
				values[pos0] = aux1;
			}
			
			if(pos1 != -2){
				String aux2 = values[1];
				values[1] = values[pos1];
				values[pos1] = aux2;
			}
			drop2.setItems(values);
			drop2.setVisible(false);
		}

		Composite downdContent = new Composite(view, 0);
		downdContent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		downdContent.setLayout(new FormLayout());
		FormDatas.attach(downdContent).atTopTo(menuComposite, 40).atLeftTo(view, 10);

		Label recordSearch = new Label(downdContent, 0);
		recordSearch.setText(AbosMessages.get().LABEL_SEARCH_RECORDS_FILTERED_BY);
		FormDatas.attach(recordSearch).atTop(0).atLeft(0);

		Group materialData = new Group(downdContent, 0);
		materialData.setLayout(new FormLayout());
		materialData.setData(RWT.CUSTOM_VARIANT, "gray_background");
		materialData.setText(AbosMessages.get().GROUP_DATA_MATERIAL);
		FormDatas.attach(materialData).atTopTo(recordSearch, 2).atLeftTo(superArg0,1);

		QueryStructure queryStructure = new QueryStructure();

		Text titleText = new Text(materialData, 0);
		queryStructure.setTitleText(titleText);
		titleText.addKeyListener(new EventKeyRecordQuery((ProxyController) controller, marc21DataBase, view, queryStructure));

		Text authorText = new Text(materialData, 0);
		queryStructure.setAuthorText(authorText);
		authorText.addKeyListener(new EventKeyRecordQuery((ProxyController) controller, marc21DataBase, view, queryStructure));

		Text editorialText = new Text(materialData, 0);
		queryStructure.setEditorialText(editorialText);
		editorialText.addKeyListener(new EventKeyRecordQuery((ProxyController) controller, marc21DataBase, view, queryStructure));

		Text publicationYearText = new Text(materialData, 0);
		queryStructure.setPublicationYearText(publicationYearText);
		publicationYearText.addKeyListener(new EventKeyRecordQuery((ProxyController) controller, marc21DataBase, view, queryStructure));

		Combo recordTypeCombo = new Combo(materialData, SWT.READ_ONLY);
		recordTypeCombo.add(AbosMessages.get().VALUE_COMBO_SELECT);
		recordTypeCombo.add(AbosMessages.get().VALUE_COMBO_BOOK);
		recordTypeCombo.add(MessageUtil
				.unescape(AbosMessages.get().VALUE_COMBO_ELECTRONIC_RESOURCE));
		recordTypeCombo.add(AbosMessages.get().VALUE_COMBO_PROJECTABLE_MATERIAL);
		recordTypeCombo.add(AbosMessages.get().VALUE_COMBO_THESIS);
		recordTypeCombo.add(AbosMessages.get().VALUE_COMBO_KIT);
		recordTypeCombo.add(MessageUtil
				.unescape(AbosMessages.get().VALUE_COMBO_MUSIC));
		recordTypeCombo.add(AbosMessages.get().VALUE_COMBO_VISUAL_MATERIAL);
		recordTypeCombo.add(AbosMessages.get().VALUE_COMBO_MIXED_MATERIAL);
		recordTypeCombo.add(MessageUtil
				.unescape(AbosMessages.get().VALUE_COMBO_CARTOGRAPHIC_MATERIAL));
		recordTypeCombo.add(AbosMessages.get().VALUE_COMBO_JOURNALS);
		recordTypeCombo.select(0);
		queryStructure.setRecordTypeCombo(recordTypeCombo);
		recordTypeCombo.addKeyListener(new EventKeyRecordQuery((ProxyController) controller, marc21DataBase, view, queryStructure));

		Label titleLabel = new Label(materialData, 0);
		titleLabel.setText(MessageUtil
				.unescape(AbosMessages.get().LABEL_TITLE));

		Label authorLabel = new Label(materialData, 0);
		authorLabel.setText(AbosMessages.get().LABEL_AUTHOR);

		Label editorialLabel = new Label(materialData, 0);
		editorialLabel.setText(AbosMessages.get().LABEL_EDITORIAL);

		Label publicationPlaceLabel = new Label(materialData, 0);
		publicationPlaceLabel.setText(MessageUtil
				.unescape(AbosMessages.get().LABEL_PUBLICATION_PLACE));

		Label recordTypeLabel = new Label(materialData, 0);
		recordTypeLabel.setText(AbosMessages.get().LABEL_TYPE_OF_RECORD);

		FormDatas.attach(titleText).withWidth(250).withHeight(10).atLeftTo(materialData, 200).atTopTo(materialData, 15);
		FormDatas.attach(authorText).withWidth(250).withHeight(10).atLeftTo(materialData, 200).atTopTo(titleText, 10);
		FormDatas.attach(editorialText).withWidth(250).withHeight(10).atLeftTo(materialData, 200).atTopTo(authorText, 10);
		FormDatas.attach(publicationYearText).withWidth(250).withHeight(10).atLeftTo(materialData, 200).atTopTo(editorialText, 10);
		FormDatas.attach(recordTypeCombo).withWidth(270).withHeight(23).atLeftTo(materialData, 200).atTopTo(publicationYearText, 10);

		FormDatas.attach(titleLabel).atRightTo(titleText, 10).atTopTo(materialData, 19);
		FormDatas.attach(authorLabel).atRightTo(authorText, 10).atTopTo(titleLabel, 16);
		FormDatas.attach(editorialLabel).atRightTo(editorialText, 10).atTopTo(authorLabel, 16);
		FormDatas.attach(publicationPlaceLabel).atRightTo(publicationYearText, 10).atTopTo(editorialLabel, 16);
		FormDatas.attach(recordTypeLabel).atRightTo(recordTypeCombo, 10).atTopTo(publicationPlaceLabel, 18);

		Button searchButton = new Button(downdContent, SWT.PUSH);
		searchButton.setText(AbosMessages.get().BUTTON_SEARCH);
		FormDatas.attach(searchButton).atTopTo(materialData, 5).atLeftTo(view, 1);

		searchButton.addListener(SWT.Selection, new EventRecordQuery((ProxyController) controller, marc21DataBase,
				view, queryStructure));
		
		seeRecords.addListener(SWT.Selection, new EventAllRecords(drop2, view, controller));

		search.addListener(SWT.Selection, new EventAcquisitionIntegration(view, marc21DataBase, 
				(ProxyController) controller));

		return superArg0;
	}

	@Override
	public String getID() {
		return CataloguingUtil.REGISTER_BIBLIOGRAPHIC_RECORD_CONTRIBUTION_ID;
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
		return MessageUtil
				.unescape(AbosMessages.get().CONTRIBUTOR_REGISTER_BIBLIOGRAPHIC_RECORD);
	}
}
