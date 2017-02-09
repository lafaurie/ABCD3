package cu.uci.abcd.acquisition.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

import cu.uci.abcd.acquisition.ui.updateArea.ViewDesiderataFragment;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.Desiderata;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;

public class ViewDesiderataContributer extends ContributorPage{
	
	private ViewDesiderataFragment viewDesiderataFragment;
	private Composite compoParent;
	private Desiderata desiderata;
	
	

	@Override
	public void setParameters(Object... parameters) {
		super.setParameters(parameters);
		desiderata = ((Desiderata)parameters[0]);
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().CONTRIBUTOR_VIEW_DESIDERATA);
	}
	
	@Override
	public String getID() {
		return "viewDesiderataContributor";
	}


	@Override
	public Control createUIControl(Composite parent) {
		addComposite(parent);
	
		compoParent= new Composite(parent, SWT.NORMAL);
		addComposite(compoParent);
		compoParent.setData(RWT.CUSTOM_VARIANT, "gray_background");
	
		Composite resize = new Composite(compoParent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 370);
		
		viewDesiderataFragment = new ViewDesiderataFragment(desiderata,this,compoParent);
		Composite a = (Composite)viewDesiderataFragment.createUIControl(compoParent);
		viewDesiderataFragment.addListenerNew(new SelectionAdapter() {
			private static final long serialVersionUID = 1165599495280305203L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				navigate("addDesiderataID");
				ViewDesiderataContributer.this.notifyListeners(SWT.Dispose, new Event());
			}
		
		});
		viewDesiderataFragment.addListenerCancel(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewDesiderataContributer.this.notifyListeners(SWT.Dispose, new Event());
			}
		});
		
		return parent;
	}
	
	
	@Override
	public void l10n() {
		viewDesiderataFragment.l10n();
		
	}

	public Desiderata getDesiderata() {
		return desiderata;
	}

	public void setDesiderata(Desiderata desiderata) {
		this.desiderata = desiderata;
	}
	
	

}
