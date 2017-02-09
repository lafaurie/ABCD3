package cu.uci.abcd.acquisition.ui.updateArea;


import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.acquisition.ui.RegisterLogAcquisition;
import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class ViewRegisterAcquisitionArea extends BaseEditableArea {

	private ViewController controller;
	private ContributorService contributorService;
	private RegisterLogAcquisition registerLogAcquisition;

	private List<String> dataBaseFormats;
	private String htmlString;
	private String viaAdquisicion;
	private String dataBaseName;
	private Record lastRecord;
	private Composite parent;
	private ViewRegisterAcquisitionAreaUpdate viewRegisterAcquisitionAreaUpdate;

	public ViewRegisterAcquisitionArea() {

	}

	public ViewRegisterAcquisitionArea(ViewController controller, ContributorService contributorService,RegisterLogAcquisition registerLogAcquisition) {
		this.controller = controller;
		this.contributorService= contributorService;
		this.registerLogAcquisition = registerLogAcquisition;
	}
	
	public ViewRegisterAcquisitionArea(ViewController controller, ContributorService contributorService,ViewRegisterAcquisitionAreaUpdate viewRegisterAcquisitionAreaUpdate) {
		this.controller = controller;
		this.contributorService= contributorService;
		this.viewRegisterAcquisitionAreaUpdate = viewRegisterAcquisitionAreaUpdate;
	}
	
	public Control createUIControl(final Composite arg0) {

		parent = new Composite(arg0, 0);

		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		arg0.setData(RWT.CUSTOM_VARIANT, "gray_background");

		parent.setLayout(new FormLayout());

		final Composite menuComposite = new Composite(parent, 0);

		menuComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");

		menuComposite.setLayout(new FormLayout());

		FormDatas.attach(menuComposite).atTopTo(parent, 0).atLeftTo(parent, 0);

		final Composite pftComposite = new Composite(parent, 0);

		pftComposite.setLayout(new FormLayout());

		FormDatas.attach(pftComposite).atTopTo(menuComposite, 0).atLeftTo(parent, 0);

		Browser browser = new Browser(pftComposite, 0);

		browser.setLayout(new FormLayout());

		FormDatas.attach(browser).atTopTo(pftComposite, 20).atLeftTo(pftComposite, 0).withHeight(385).withWidth(1000);

		browser.setText(htmlString);

		arg0.getShell().layout(true, true);
		arg0.getShell().redraw();
		arg0.getShell().update();
		return browser;		 
	
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public void l10n() {

	}

	@Override
	public void setViewController(ViewController arg0) {
	}

	public ViewController getController() {
		return controller;
	}

	public void setController(AllManagementController controller) {
		this.controller = controller;
	}

	public List<String> getDataBaseFormats() {
		return dataBaseFormats;
	}

	public void setDataBaseFormats(List<String> dataBaseFormats) {
		this.dataBaseFormats = dataBaseFormats;
	}

	public String getHtmlString() {
		return htmlString;
	}

	public void setHtmlString(String htmlString) {
		this.htmlString = htmlString;
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public Record getLastRecord() {
		return lastRecord;
	}

	public void setLastRecord(Record lastRecord) {
		this.lastRecord = lastRecord;
	}

	@Override
	public boolean closable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Composite createButtons(Composite arg0, IGridViewEntity arg1,
			IVisualEntityManager arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Composite createUI(Composite arg0, IGridViewEntity arg1,
			IVisualEntityManager arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	public void initialize() {
		// TODO Auto-generated method stub

	}



}
