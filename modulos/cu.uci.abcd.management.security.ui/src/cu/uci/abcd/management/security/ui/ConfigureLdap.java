package cu.uci.abcd.management.security.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;

import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abcd.management.security.ui.controller.LdapViewController;
import cu.uci.abcd.management.security.ui.model.LdapAddArea;
import cu.uci.abcd.management.security.ui.model.LdapUpdateArea;
import cu.uci.abcd.management.security.ui.model.LdapViewArea;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.CRUDTreeTableUtils;
import cu.uci.abos.core.widget.grid.IActionCommand;
import cu.uci.abos.core.widget.grid.TreeColumnEvent;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ConfigureLdap extends ContributorPage implements Contributor{

	private ValidatorUtils validator;
	private Label configureLdapLabel;
	@SuppressWarnings("unused")
	private Label ldapHostLabel;
	@SuppressWarnings("unused")
	private Text ldapHostText;
	@SuppressWarnings("unused")
	private Label ldapPortLabel;
	@SuppressWarnings("unused")
	private Text ldapPortText;
	@SuppressWarnings("unused")
	private Label ldapDomainLabel;
	@SuppressWarnings("unused")
	private Text ldapDomainText;
	@SuppressWarnings("unused")
	private Label ldapUserLabel;
	@SuppressWarnings("unused")
	private Text ldapUserText;
	@SuppressWarnings("unused")
	private Label ldapPasswordLabel;
	@SuppressWarnings("unused")
	private Text ldapPasswordText;
	@SuppressWarnings("unused")
	private Button saveButton;
	@SuppressWarnings("unused")
	private Button testButton;
	private Library library;
	//private Ldap ldap;
	
	private Label listado;
	private CRUDTreeTable tableLdap;
	private List<String> searchCriteriaList = new ArrayList<>();
	private String orderByString = "host";
	private int direction = 1024;
	
	//private String orderByString = "host";
	
	
	@Override
	public void setViewController(ViewController controller) {
		super.controller = controller;
	}

	@Override
	public String contributorName() {
		return "LDAP";
	}

	@Override
	public String getID() {
		return "configureLdapID";
	}
	
	@Override
	public Control createUIControl(final Composite parent) {
		library = (Library) SecurityUtils
				.getService().getPrincipal()
				.getByKey("library");
		setValidator(new ValidatorUtils(new CustomControlDecoration()));
		addComposite(parent);
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite scroll = new Composite(parent,SWT.NONE);
		scroll.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(scroll).atTopTo(parent, 25).atRight(0).withWidth(0).withHeight(Display.getCurrent().getBounds().height - 172);
		
		
		configureLdapLabel = new Label(parent, SWT.NONE);
		addHeader(configureLdapLabel);
		
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		
		listado = new Label(parent, SWT.NORMAL);
		addHeader(listado);
		
		tableLdap = new CRUDTreeTable(parent, SWT.NONE);
		add(tableLdap);
		tableLdap.setEntityClass(Ldap.class);
		tableLdap.setSearch(false);
		tableLdap.setSaveAll(false);

		tableLdap.setAdd(true, new LdapAddArea(controller, this, tableLdap));
		tableLdap.setWatch(true, new LdapViewArea(controller));
		tableLdap.setUpdate(true, new LdapUpdateArea(controller, this, tableLdap));
		tableLdap.setDelete(true);
		
		tableLdap.setVisible(true);
		
		tableLdap.addListener(SWT.Resize, new Listener() {
            private static final long serialVersionUID = 8817895862824622805L;
            @Override
            public void handleEvent(Event event) {
                    refresh();
            }
           });
		
		CRUDTreeTableUtils.configUpdate(tableLdap);
		
		CRUDTreeTableUtils.configReports(tableLdap,
				contributorName(), searchCriteriaList, "Listado");
		
		CRUDTreeTableUtils.configRemove(tableLdap,
				new IActionCommand() {
					@Override
					public void execute(TreeColumnEvent event) {
						tableLdap.destroyEditableArea();
						Ldap ldap = (Ldap) event.entity
								.getRow();
						Long idLdap = ldap.getLdapID();
						((LdapViewController) controller)
								.deleteLdapById(idLdap);
						//tableLdap.getPaginator().goToFirstPage();
					}
				});

		TreeTableColumn columns[] = {
				new TreeTableColumn(25, 0, "getHost"),
				new TreeTableColumn(25, 1, "getPort"),
				new TreeTableColumn(25, 2, "getDomain"),
				new TreeTableColumn(25, 3, "getState")};
		
		tableLdap.createTable(columns);
		
		tableLdap.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(final PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
					switch (event.sortData.columnIndex) {
					case 1:
						orderByString = "host";
						break;
					case 2:
						orderByString = "port";
						break;
					case 3:
						orderByString = "domain";
						break;
					case 4:
						orderByString = "motivation";
						break;
					}
				}
				searchLdaps(event.currentPage - 1, event.pageSize);
			}
		});
		
		tableLdap.getPaginator().goToFirstPage();
		//searchLdaps();
		l10n();
		return parent;
	}
	
	public void open(){
		this.notifyListeners(SWT.Dispose, new Event());
		ContributorService contributorService = getContributorService();
		contributorService.selectContributor("configureLdapID");
	}
	
	public void LoadLdapData(){
		//if(ldap!=null){
		//	ldapHostText.setText(ldap.getHost());
		//	ldapPortText.setText(ldap.getPort().toString());
		//	ldapDomainText.setText(ldap.getDomain());
		//}
		
	}
	@Override
	public boolean canClose() {
		return false;
	}

	@Override
	public void l10n() {
		configureLdapLabel.setText("CONFIGURAR LDAP");
		/*
		ldapHostLabel.setText("Host");
		ldapHostText.setMessage("Host");
		ldapPortLabel.setText("Puerto");
		ldapPortText.setMessage("Puerto");
		ldapDomainLabel.setText("Domain");
		ldapDomainText.setMessage("Domain");
		ldapUserLabel.setText("Usuario");
		ldapUserText.setMessage("Usuario");
		ldapPasswordLabel.setText("Contrasenna");
		ldapPasswordText.setMessage("Contrasenna");
		saveButton.setText("Guardar");
		testButton.setText("Probar");
		*/
		listado.setText(MessageUtil.unescape("Listado"));
		tableLdap.setColumnHeaders(Arrays.asList(
				MessageUtil.unescape("Direcci&oacute;n IP"), "Puerto", "Dominio", "Estado"));
		
		//tableLdap.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		tableLdap.setAddButtonText(AbosMessages.get().BUTTON_ADD);
		tableLdap.setActionButtonText("exportPDFButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_PDF));
		tableLdap.setActionButtonText("exportExcelButton", MessageUtil.unescape(AbosMessages.get().BUTTON_EXPORT_TO_EXCEL));
		
		tableLdap.getParent().layout(true, true);
		tableLdap.getParent().redraw();
		
		tableLdap.l10n();
	}

	public ValidatorUtils getValidator() {
		return validator;
	}

	public void setValidator(ValidatorUtils validator) {
		this.validator = validator;
	}
	
	public void searchLdaps(int page, int size) {
		/*
		List<Ldap> ldaps = ((LdapViewController) controller)
				.getAllManagementSecurityViewController().getLdapService()
				.findAllByLibrary(library.getLibraryID());
		tableLdap.clearRows();
		tableLdap.setRows(ldaps);
		tableLdap.refresh();
		*/
		
		Page<Ldap> list = ((LdapViewController) controller)
				.findLdapByParams(library, page, size, direction, orderByString);
		tableLdap.clearRows();
		tableLdap.setTotalElements((int) list.getTotalElements());
		tableLdap.setRows(list.getContent());
		tableLdap.refresh();
	}

}
