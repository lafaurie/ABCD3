package cu.uci.abcd.cataloguing.ui;

import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.cataloguing.controller.ProxyController;
import cu.uci.abcd.cataloguing.listener.EventAddCopies;
import cu.uci.abcd.cataloguing.listener.EventBackRecord;
import cu.uci.abcd.cataloguing.listener.EventChangeMFN;
import cu.uci.abcd.cataloguing.listener.EventChangeView;
import cu.uci.abcd.cataloguing.listener.EventDeleteRecord;
import cu.uci.abcd.cataloguing.listener.EventEditRecord;
import cu.uci.abcd.cataloguing.listener.EventFirstRecord;
import cu.uci.abcd.cataloguing.listener.EventLastRecord;
import cu.uci.abcd.cataloguing.listener.EventNewRecord;
import cu.uci.abcd.cataloguing.listener.EventNextRecord;
import cu.uci.abcd.cataloguing.listener.EventProcessingExemplary;
import cu.uci.abcd.cataloguing.listener.EventSearch;
import cu.uci.abcd.cataloguing.listener.EventStartRecord;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.l10n.cataloguing.AbosMessages;
import cu.uci.abos.l10n.cataloguing.MessageUtil;
import cu.uci.abos.widget.template.util.BibliographicConstant;
import cu.uci.abos.widget.template.util.Util;

public class AllRecordsView extends ContributorPage{
	
	private String dataBaseName;
	private boolean isExemplaryDataBase = false;
	private boolean isAuthoritiesDataBase = false;
	private IJisisDataProvider service;
	private int width;
	private int height;
	private Record currentRecord;
	private Record firstRecord;
	private Record lastRecord;
	private ProxyController controller;
	private boolean integration = false;
	private String currentView;
	public static Label recordsCountLabel;
	
	//options
	ToolItem editRecord = null;
	ToolItem addCopies = null;
	ToolItem processingExemplary = null;
	ToolItem deleteRecord = null;
	ToolItem newRecord = null;
	ToolItem searchRecord = null;
	ToolItem startView = null;

	@Override
	public Control createUIControl(Composite arg0) {
		
		Composite parent = new Composite(arg0, 0);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		parent.setLayout(new FormLayout());
	
		long recordsCount = 0;
		String isisDefHome = "";
		
		try {
			
			isisDefHome = Util.getDefHome();
			
			firstRecord = service.getFirstRecord(dataBaseName, isisDefHome);
			lastRecord = service.getLastRecord(dataBaseName, isisDefHome);
			recordsCount = service.totalRecords(dataBaseName, isisDefHome);
			
		} catch (JisisDatabaseException e) {
			e.printStackTrace();
		}
		
		Composite optionMenu = new Composite(parent, 0);
		optionMenu.setData(RWT.CUSTOM_VARIANT, "gray_background");
		optionMenu.setLayout(new FormLayout());
		
		ToolBar toolbar = new ToolBar(optionMenu, SWT.CENTER|SWT.WRAP|SWT.FLAT);
		FormDatas.attach(toolbar).atTopTo(optionMenu, 0).atLeftTo(optionMenu, 0);

		new ToolItem(toolbar, SWT.SEPARATOR);

		editRecord = new ToolItem(toolbar, 0);
		Image editImage = new Image(arg0.getDisplay(), RWT.getResourceManager().getRegisteredContent("pencil"));
		editRecord.setImage(editImage);
		editRecord.setToolTipText(AbosMessages.get().TOOL_ITEM_EDIT_RECORD);

		new ToolItem(toolbar, SWT.SEPARATOR);

		if(isExemplaryDataBase){
			addCopies = new ToolItem(toolbar, 0);
			Image gearImage = new Image(arg0.getDisplay(), RWT.getResourceManager().getRegisteredContent("gear"));
			addCopies.setImage(gearImage);
			addCopies.setToolTipText(AbosMessages.get().TOOL_ITEM_COPIES_MANAGEMENT);
			
			new ToolItem(toolbar, SWT.SEPARATOR);
		
			addCopies.addListener(SWT.Selection, new EventAddCopies(parent, controller,
					width, dataBaseName, height, this));
		}
		
		if(integration){
			processingExemplary = new ToolItem(toolbar, 0);
			Image associateImage = new Image(arg0.getDisplay(), RWT.getResourceManager().getRegisteredContent("tag"));
			processingExemplary.setImage(associateImage);
			processingExemplary.setToolTipText(AbosMessages.get().TOOL_ITEM_EXEMPLARY_PROCESSING);
			
			new ToolItem(toolbar, SWT.SEPARATOR);
			
			processingExemplary.addListener(SWT.Selection, new EventProcessingExemplary(parent, controller, dataBaseName));
		}

		deleteRecord = new ToolItem(toolbar, 0);
		Image deleteImage = new Image(arg0.getDisplay(), RWT.getResourceManager().getRegisteredContent("minus"));
		deleteRecord.setImage(deleteImage);
		deleteRecord.setToolTipText(AbosMessages.get().TOOL_ITEM_DELETE_RECORD);

		new ToolItem(toolbar, SWT.SEPARATOR);

		newRecord = new ToolItem(toolbar, 0);
		Image plusImage = new Image(arg0.getDisplay(), RWT.getResourceManager().getRegisteredContent("plus"));
		newRecord.setImage(plusImage);
		newRecord.setToolTipText(AbosMessages.get().TOOL_ITEM_NEW_RECORD);

		new ToolItem(toolbar, SWT.SEPARATOR);
		
		if(isExemplaryDataBase || isAuthoritiesDataBase){
			searchRecord = new ToolItem(toolbar, 0);
			Image searchImage = new Image(arg0.getDisplay(), RWT.getResourceManager().getRegisteredContent("search"));
			searchRecord.setImage(searchImage);
			searchRecord.setToolTipText(AbosMessages.get().TOOL_ITEM_SEARCH_RECORDS);

			new ToolItem(toolbar, SWT.SEPARATOR);
			
			searchRecord.addListener(SWT.Selection, new EventSearch(parent, controller, dataBaseName));
		}
		
		startView = new ToolItem(toolbar, 0);
		Image startViewImage = new Image(arg0.getDisplay(), RWT.getResourceManager().getRegisteredContent("list-alt"));
		startView.setImage(startViewImage);
		startView.setToolTipText(AbosMessages.get().TOOL_ITEM_MAIN_VIEW);
		
		new ToolItem(toolbar, SWT.SEPARATOR);
		
		newRecord.addListener(SWT.Selection, new EventNewRecord(parent, controller,
        		dataBaseName, integration, this));
		
		 startView.addListener(SWT.Selection, new EventStartRecord(parent, controller));
		
		if(firstRecord == null){
			Browser label = new Browser(parent, 0);
			label.setText(dataBaseName+AbosMessages.get().LABEL_HAS_NOT_RECORDS);
			label.setLayout(new FormLayout());
			FormDatas.attach(label).atTopTo(toolbar, 30).atLeftTo(parent, 5).withHeight(height).withWidth(width);
			
			//allow only the option Add Register
			editRecord.setEnabled(false);
			
			if(addCopies != null)
				addCopies.setEnabled(false);
			
			if(processingExemplary != null)
				processingExemplary.setEnabled(false);
			
			deleteRecord.setEnabled(false);
			
			if(searchRecord != null)
			searchRecord.setEnabled(false);
		}
		else{
			Composite navegateMenu = new Composite(parent, SWT.BORDER);
			navegateMenu.setData(RWT.CUSTOM_VARIANT, "gray_background");
			navegateMenu.setLayout(new FormLayout());
			FormDatas.attach(navegateMenu).atLeft(5).atRight(0).atTopTo(optionMenu, 0);
			
			Label dataBaseNameLabel = new Label(navegateMenu, 0);
			dataBaseNameLabel.setText(AbosMessages.get().LABEL_DATA_BASE);
			dataBaseNameLabel.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.BOLD));
			FormDatas.attach(dataBaseNameLabel).atTop(2).atLeft(5);
			
			Label dataBaseNameAnswer = new Label(navegateMenu, 0);
			dataBaseNameAnswer.setText(dataBaseName);
			FormDatas.attach(dataBaseNameAnswer).atTop(2).atLeftTo(dataBaseNameLabel, 2);
			
			Label mfnLabel = new Label(navegateMenu, 0);
			mfnLabel.setText(AbosMessages.get().LABEL_MFN);
			mfnLabel.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.BOLD));
			FormDatas.attach(mfnLabel).atTopTo(dataBaseNameLabel, 20).atLeft(5);
			
			Text mfnNumberText = new Text(navegateMenu, 0);
			mfnNumberText.setText(String.valueOf(currentRecord.getMfn()));
			FormDatas.attach(mfnNumberText).atTopTo(dataBaseNameLabel, 10).atLeftTo(mfnLabel, 2).withWidth(80).withHeight(20);
			
			ToolBar navegateBar = new ToolBar(navegateMenu, SWT.CENTER|SWT.WRAP|SWT.FLAT);
			FormDatas.attach(navegateBar).atLeftTo(mfnNumberText, 10).atTopTo(dataBaseNameLabel, 10);
			
			ToolItem start = new ToolItem(navegateBar, 0);
			Image startImage = new Image(arg0.getDisplay(), RWT.getResourceManager().getRegisteredContent("left-arrow"));
			start.setImage(startImage);
			start.setToolTipText(AbosMessages.get().TOOL_ITEM_FIRST);
			
			ToolItem back = new ToolItem(navegateBar, 0);
			Image backImage = new Image(arg0.getDisplay(), RWT.getResourceManager().getRegisteredContent("sleft-arrow"));
			back.setImage(backImage);
			back.setToolTipText(AbosMessages.get().TOOL_ITEM_BACK);
			
			if(firstRecord.getMfn() == currentRecord.getMfn()){
				start.setEnabled(false);
				back.setEnabled(false);
			}
				
			ToolItem toward = new ToolItem(navegateBar, 0);
			Image towardImage = new Image(arg0.getDisplay(), RWT.getResourceManager().getRegisteredContent("sright-arrow"));
			toward.setImage(towardImage);
			toward.setToolTipText(AbosMessages.get().TOOL_ITEM_NEXT);
			
			ToolItem last = new ToolItem(navegateBar, 0);
			Image lastImage = new Image(arg0.getDisplay(), RWT.getResourceManager().getRegisteredContent("right-arrow"));
			last.setImage(lastImage);
			last.setToolTipText(MessageUtil.unescape(AbosMessages.get().TOOL_ITEM_LAST));
			
			if(lastRecord.getMfn() == currentRecord.getMfn()){
				toward.setEnabled(false);
				last.setEnabled(false);
			}
			
			Label totalRecords = new Label(navegateMenu, SWT.WRAP|SWT.RIGHT);
			totalRecords.setText(AbosMessages.get().LABEL_TOTAL_RECORDS);
			totalRecords.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.BOLD));
			FormDatas.attach(totalRecords).atTopTo(dataBaseNameLabel, 20).atLeftTo(navegateBar, 5);
			
			recordsCountLabel = new Label(navegateMenu, SWT.WRAP|SWT.RIGHT);
			recordsCountLabel.setText(String.valueOf(recordsCount));
			FormDatas.attach(recordsCountLabel).atTopTo(dataBaseNameLabel, 20).atLeftTo(totalRecords, 2);
			
            String htmlString = null;
			
			List<String> dataBaseFormats = null;
			try {
				dataBaseFormats = service.getDatabaseFormats(dataBaseName, isisDefHome);
				String format = dataBaseFormats.get(0);
				
				FormattedRecord formattedRecord = service.getFormattedRecord(dataBaseName, currentRecord, format, isisDefHome);
				htmlString = formattedRecord.getRecord();
				
			} catch (JisisDatabaseException e) {
				e.printStackTrace();
			}
			
			Label setView = new Label(navegateMenu, 0);
			setView.setText(AbosMessages.get().LABEL_CHANGE_VIEW);

			Combo changeCombo = new Combo(navegateMenu, SWT.READ_ONLY);
			int dataBasesFormatCount = dataBaseFormats.size();

			for (int i = 0; i < dataBasesFormatCount; i++) {
				if(dataBaseFormats.get(i).equals("RAW"))
					dataBaseFormats.set(i, AbosMessages.get().VALUE_COMBO_MARC_VIEW);

				changeCombo.add(dataBaseFormats.get(i), i);
			}

			changeCombo.select(0);

			FormDatas.attach(changeCombo).atTop(0).withWidth(250).atRight(0).withHeight(30);
			FormDatas.attach(setView).atTop(10).atRightTo(changeCombo, 5);
				
			//RecordView
			Composite content = new Composite(parent, 0);
			content.setData(RWT.CUSTOM_VARIANT, "gray_background");
			content.setLayout(new FormLayout());
			FormDatas.attach(content).atLeft(5).atRight(0).atTopTo(navegateMenu, 0).atBottom(0);
			
			Browser browser = new Browser(content, 0);
			browser.setLayout(new FormLayout());
			FormDatas.attach(browser).atTopTo(content, 0).atRight(0).atLeft(0).withWidth(width).withHeight(height - 80);

			browser.setText(htmlString);

			parent.getShell().layout(true, true);
			parent.getShell().redraw();
			parent.getShell().update();
			
			//service references
			toward.addListener(SWT.Selection, new EventNextRecord(browser, dataBaseName, service, mfnNumberText, start,
					toward, last, back, this));
			
			back.addListener(SWT.Selection, new EventBackRecord(browser, dataBaseName, service, mfnNumberText, start,
					toward, last, back, this));
			
			start.addListener(SWT.Selection, new EventFirstRecord(browser, dataBaseName, service, mfnNumberText, start,
					toward, last, back, this));
			
			last.addListener(SWT.Selection, new EventLastRecord(browser, dataBaseName, service, mfnNumberText, start,
					toward, last, back, this));
			
			changeCombo.addListener(SWT.Selection, new EventChangeView(browser, service, dataBaseName, changeCombo, this));
			
            mfnNumberText.addKeyListener(new EventChangeMFN(browser, dataBaseName, service, mfnNumberText, changeCombo, start,
					toward, last, back, this));
            
            //options Events
            editRecord.addListener(SWT.Selection, new EventEditRecord(parent, controller,
            		dataBaseName, integration, this));
            
            deleteRecord.addListener(SWT.Selection, new EventDeleteRecord(controller, dataBaseName, browser,
            		mfnNumberText, changeCombo, recordsCountLabel, editRecord, addCopies, processingExemplary,
            		deleteRecord, searchRecord, start, back, toward, last, this));
		}
		
		return arg0;
	}
	
	public void setDataBaseName(String dataBaseName){
		this.dataBaseName = dataBaseName;
		String exemplaryDataBaseName = BibliographicConstant.BIBLIOGRAPHIC_DATA_BASE;
		String authoritiesDataBaseName = BibliographicConstant.AUTHORITIES_DATABASE;
		
		if(dataBaseName.equals(exemplaryDataBaseName))
			this.isExemplaryDataBase = true;
		else{
			this.isExemplaryDataBase = false;
			if(dataBaseName.equals(authoritiesDataBaseName))
				this.isAuthoritiesDataBase = true;
			else
				this.isAuthoritiesDataBase = false;
		}
	}
	
	public void setService(IJisisDataProvider service){
		this.service = service;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
	public void setProxyController(ProxyController controller){
		this.controller = controller;
	}
	
	public void setIntegration(boolean integration){
		this.integration = integration;
	}
	
	public String getCurrentView(){
		return this.currentView;
	}
	
	public void setCurrentView(String currentView){
		this.currentView = currentView;
	}
	
	public Record getCurrentRecord(){
		return this.currentRecord;
	}
	
	public void setCurrentRecord(Record currentRecord){
		this.currentRecord = currentRecord;
	}
	
	public Record getFirstRecord(){
		return this.firstRecord;
	}
	
	public void setFirstRecord(Record firstRecord){
		this.firstRecord = firstRecord;
	}
	
	public Record getLastRecord(){
		return this.lastRecord;
	}
	
	public void setLastRecord(Record lastRecord){
		this.lastRecord = lastRecord;
	}
}
