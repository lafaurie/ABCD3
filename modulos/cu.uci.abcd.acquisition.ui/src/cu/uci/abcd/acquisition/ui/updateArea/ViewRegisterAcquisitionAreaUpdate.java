package cu.uci.abcd.acquisition.ui.updateArea;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.unesco.jisis.corelib.exceptions.DbException;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abcd.acquisition.ui.RegisterLogAcquisition;
import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.dataprovider.jisis.IJisisDataProvider;
import cu.uci.abcd.dataprovider.jisis.exception.JisisDatabaseException;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;
import cu.uci.abos.widget.repeatable.field.util.FieldStructure;
import cu.uci.abos.widget.template.component.TemplateCompound;

public class ViewRegisterAcquisitionAreaUpdate extends BaseEditableArea {

	private ViewController controller;
	private ContributorService contributorService;
	private RegisterLogAcquisition registerLogAcquisition;
	private ArrayList<FieldStructure> children;
	private List<String> dataBaseFormats;
	private String htmlString;

	private String dataBaseName;
	private Record lastRecord;
	private Composite parent;

	private Library library;

	public static final String DataBaseName = "Registro_De_Adquisicion";

	public ViewRegisterAcquisitionAreaUpdate() {

	}

	public ViewRegisterAcquisitionAreaUpdate(ViewController controller) {
		this.controller = controller;
	}

	@Override
	public Composite createButtons(final Composite arg0,
			final IGridViewEntity entity, final IVisualEntityManager manager) {
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
		this.dataBaseName = DataBaseName;
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

		library = (Library) SecurityUtils.getService().getPrincipal()
				.getByKey("library");

		RecordRow recordRow = (RecordRow) entity.getRow();
		if (recordRow != null) {
			lastRecord = recordRow.record;
		}
		parent = new Composite(arg0, 0);

		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		arg0.setData(RWT.CUSTOM_VARIANT, "gray_background");

		parent.setLayout(new FormLayout());

		AllManagementController cont = (AllManagementController) controller;

		IJisisDataProvider service = cont.getAcquisition().getService();

		TemplateCompound compound;
		try {
			compound = new TemplateCompound(parent, 0, lastRecord,
					DataBaseName, service);

			String workSheetName = lastRecord.getField(23)
					.getStringFieldValue();

			compound.setWorkSheetName(workSheetName);
			compound.createEditComponent();

			compound.setComboLabel(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_WAY));
			compound.setRecordLabel("");

			compound.setLayout(new FormLayout());

			FormDatas.attach(compound).atTopTo(parent, 0).atLeftTo(parent, 0)
					.withHeight(385).withWidth(1000);

			Button buttonSave;
			buttonSave = compound.getButtonSave();

			Shell shell = parent.getShell();

			Library userLogLibrary = compound.getUserLogLibrary();
			children = new ArrayList<FieldStructure>();
			children = compound.getChildrens();

			buttonSave.addListener(SWT.Selection, new EventButtonSave(compound,
					controller, library, DataBaseName, this,
					contributorService, parent, lastRecord));

		} catch (JisisDatabaseException e) {
			e.printStackTrace();
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arg0;
	}

}
