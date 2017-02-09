package cu.uci.abcd.acquisition.ui.updateArea;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.acquisition.ui.ConsultSuggestions;
import cu.uci.abcd.acquisition.ui.controller.Auxiliary;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.Percent;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class ViewSuggestionArea extends BaseEditableArea {
	
	private ViewController controller;
	private ConsultSuggestions consultSuggestions;

	private int dimension;
	private CRUDTreeTable tabla;
	private static String orderByStringSuggestion = "suggestionID";
	private static int direction = 1024;
	private Label lblViewSuggestion;
	private Group personData;
	private List<Control> grupControlsSuggestion = new ArrayList<>();
	private List<String> leftList = new LinkedList<>();
	private String lastString;
	
	public ViewSuggestionArea(ViewController controller, ConsultSuggestions consultSuggestions, CRUDTreeTable tabla) {
		this.controller = controller;
		this.consultSuggestions = consultSuggestions;
		this.tabla = tabla;

	}
	
	public ViewSuggestionArea(ViewController controller, CRUDTreeTable tabla) {
		this.controller = controller;
		this.tabla = tabla;

	}

	public Composite createUI(Composite parent, IGridViewEntity entity, IVisualEntityManager arg2) {
		dimension = parent.getParent().getParent().getBounds().width;
		setDimension(dimension);
		
		Composite group = new Composite(parent, SWT.NONE);
		addComposite(group);
	
		Suggestion suggestionToView = (Suggestion) entity.getRow();
	
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		group.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		lblViewSuggestion  = new Label(group, 0);
		addHeader(lblViewSuggestion);
		
		Label separator = new Label(group,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
	
		add(new Label(parent, 0),Percent.W100);
		br();
		
		lastString = MessageUtil.unescape(AbosMessages.get().LABEL_DATA_SUGGESTION);

		personData = new Group(parent, SWT.NORMAL);
		add(personData);

        leftList = new LinkedList<>();
        leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
        leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR));
        leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_EDITORIAL));
        leftList.add(AbosMessages.get().LABEL_SUGGESTED_BY);
        leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_REGISTRATION));
        
        List<String> rightList = new LinkedList<>();
        rightList.add(suggestionToView.getTitle());
		if (suggestionToView.getAuthor() == null) {
			rightList.add("-");	
		}
		else
		rightList.add(suggestionToView.getAuthor());
		
		if (suggestionToView.getEditorial() == null) {
			rightList.add("-");	
		}
		else
		rightList.add(suggestionToView.getEditorial());
		
		if (suggestionToView.getUser().getUsername() == null) {
			rightList.add("-");	
		}
		else
		rightList.add(suggestionToView.getUser().getUsername());
		rightList.add(Auxiliary.FormatDate(suggestionToView.getRegisterDate()));
		
		grupControlsSuggestion=CompoundGroup.printGroup( personData, lastString, leftList, rightList);
		
		l10n();
		return parent;
	}

	public Composite createButtons(Composite parent, IGridViewEntity arg1, IVisualEntityManager arg2) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public void l10n() {
		lblViewSuggestion.setText(MessageUtil.unescape(AbosMessages.get().VIEW_SUGGESTION));
		 leftList.clear();;
	     leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
	     leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR));
	     leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_EDITORIAL));
	     leftList.add(AbosMessages.get().LABEL_SUGGESTED_BY);
	     leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_REGISTRATION));
	     
	 	CompoundGroup.l10n(grupControlsSuggestion, leftList);
	 	lastString = MessageUtil.unescape(AbosMessages.get().LABEL_DATA_SUGGESTION);

		personData.setText(lastString);
	}

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}

	public ConsultSuggestions getConsultSuggestions() {
		return consultSuggestions;
	}

	public void setConsultSuggestions(ConsultSuggestions consultSuggestions) {
		this.consultSuggestions = consultSuggestions;
	}

	public CRUDTreeTable getTabla() {
		return tabla;
	}

	public void setTabla(CRUDTreeTable tabla) {
		this.tabla = tabla;
	}
	
	
}