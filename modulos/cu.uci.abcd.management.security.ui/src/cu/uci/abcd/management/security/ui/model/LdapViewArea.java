package cu.uci.abcd.management.security.ui.model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.domain.management.security.Ldap;
import cu.uci.abcd.management.security.ui.controller.LdapViewController;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class LdapViewArea extends BaseEditableArea {

	private Label header;
	private Group dataGroup;
	private String titleGroup;
	private List<String> leftList;
	private List<Control> grupControls;
	private ViewController controller;

	public LdapViewArea(ViewController controller){
		this.controller = controller;
	}
	
	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager manager) {
		Ldap ldap = ((LdapViewController) controller).getLdapById(((Ldap) entity.getRow()).getLdapID());
		setDimension(parent.getParent().getParent().getBounds().width);
		addComposite(parent);

		Composite topGroup = new Composite(parent, SWT.NORMAL);
		topGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(topGroup);
		header = new Label(topGroup, SWT.NORMAL);
		addHeader(header);
		Label separator = new Label(topGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		dataGroup = new Group(topGroup, SWT.NORMAL);
		dataGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		titleGroup = MessageUtil
				.unescape("Datos del LDAP");
		add(dataGroup);
		leftList = new LinkedList<>();
		leftList.add(MessageUtil.unescape("Dirección IP"));
		leftList.add(MessageUtil.unescape("Puerto"));
		leftList.add(MessageUtil.unescape("Dominio"));
		
		List<String> values = new LinkedList<>();
		values.add((ldap.getHost() != null) ? ldap.getHost() : "-");
		values.add((ldap.getPort() != null) ? ldap.getPort().toString() : "-");
		values.add((ldap.getDomain() != null) ? ldap.getDomain() : "-");
		
		grupControls = CompoundGroup.printGroup(dataGroup, titleGroup,
				leftList, values);
		return parent;
	}

	@Override
	public Composite createButtons(Composite parent,
			IGridViewEntity entity, IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}
	
	@Override
	public void l10n() {
		header.setText(MessageUtil.unescape("VER LDAP"));
		titleGroup = MessageUtil
				.unescape("Datos del LDAP");
		leftList.clear();
		leftList.add(MessageUtil.unescape("Dirección IP"));
		leftList.add(MessageUtil.unescape("Puerto"));
		leftList.add(MessageUtil.unescape("Dominio"));
		dataGroup.setText(titleGroup);
		CompoundGroup.l10n(grupControls, leftList);
		
	}
	
	@Override
	public String getID() {
		return "viewLdapID";
	}

}
