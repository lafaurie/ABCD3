package cu.uci.abcd.acquisition.ui.updateArea;


import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.unesco.jisis.corelib.common.FormattedRecord;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.acquisition.IRegistrationManageAcquisitionService;
import cu.uci.abcd.acquisition.ui.RegisterLogAcquisition;
import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class ViewRegisterAcquisitionAreaConsult extends BaseEditableArea {
	
	private ViewController controller;
	private ContributorService contributorService;
	private RegisterLogAcquisition registerLogAcquisition;
	
	private List<String> dataBaseFormats;
	private String htmlString;
	
	private String dataBaseName;
	private Record lastRecord;
	private Composite parent;
	private Library library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
	private String defhome = library.getIsisDefHome();
	public static final String DataBaseName = "Registro_De_Adquisicion";
	
	public ViewRegisterAcquisitionAreaConsult() {
		
	}

	public ViewRegisterAcquisitionAreaConsult(ViewController controller) {
		this.controller = controller;
	}
	
	@Override
	public Composite createButtons(final Composite arg0, final IGridViewEntity entity,
			final IVisualEntityManager manager) {
		arg0.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return arg0;
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
	public Composite createUI(Composite arg0, IGridViewEntity entity,
			IVisualEntityManager arg2) {
		
		RecordRow recordRow= (RecordRow)entity.getRow();
		if (recordRow!= null) {
			lastRecord = recordRow.record;
		}
		
		parent = new Composite(arg0, 0);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		arg0.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		parent.setLayout(new FormLayout());

		final Composite menuComposite = new Composite(parent, 0);
		menuComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");
		menuComposite.setLayout(new FormLayout());
		FormDatas.attach(menuComposite).atTopTo(parent, 0).atLeftTo(parent, 0).atRightTo(parent,0);

		
		final Composite pftComposite = new Composite(parent, 0);
		pftComposite.setLayout(new FormLayout());
		FormDatas.attach(pftComposite).atTopTo(menuComposite, 0).atLeftTo(parent, 0);

		Browser browser = new Browser(pftComposite, 0);
		browser.setLayout(new FormLayout());
		FormDatas.attach(browser).atTopTo(pftComposite, 0).atLeftTo(pftComposite, 0).withHeight(385).withWidth(1000);

		arg0.getShell().layout(true, true);
		arg0.getShell().redraw();
		arg0.getShell().update();

		
		IRegistrationManageAcquisitionService dataBaseManager = ((AllManagementController)controller).getAcquisition();
		
		
		 List<String> dataBaseFormats = null;

		 try {
			dataBaseFormats = dataBaseManager.getDatabaseFormats(DataBaseName,defhome);
		} catch (JisisDatabaseException e1) {
	
			e1.printStackTrace();
		}

		String format = dataBaseFormats.get(0);

		FormattedRecord formattedRecord;
		try {
			formattedRecord = dataBaseManager.getFormattedRecord(DataBaseName, lastRecord, format,defhome);
			String htmlString = formattedRecord.getRecord();
			
			browser.setText(htmlString);
			
		} catch (JisisDatabaseException e1) {
			e1.printStackTrace();
		}
		
	
		return arg0;
	}
	
	

}
