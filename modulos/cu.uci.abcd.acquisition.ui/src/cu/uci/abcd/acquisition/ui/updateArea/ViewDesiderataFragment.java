package cu.uci.abcd.acquisition.ui.updateArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.Desiderata;
import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.ui.FragmentPage;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ViewDesiderataFragment extends FragmentPage {

	private Desiderata desiderata;

	private CRUDTreeTable orderTable;
	private ViewController controller;

	private List<Suggestion> suggestionList = new ArrayList<Suggestion>();
	private int page = 0;
	private int size = 10;
	private Label associatedSuggestions;
	static int direction = 1024;
	private Desiderata desiderataToView;
	private Button btnClose;
	private Button btnNew;
	private Label lblViewDesiderata;
	private int dimension;
	private ContributorPage  pageContributor;
	private Composite viewButton;
	private List<Control> grupControlsDesiderata = new ArrayList<>();
	private List<String> leftList = new LinkedList<>();
	private String lastString;
	private Group desiderataGroup;
	
	public ViewDesiderataFragment(Desiderata desiderata, ContributorPage  pageContributor,Composite viewButton) {
		this.controller=pageContributor.getController();
		this.setDesiderata(desiderata);
		this.setDimension(pageContributor.getDimension());	
		this.viewButton=viewButton;
	}
	
	public ViewDesiderataFragment(Desiderata desiderata,ViewController controller, int dimension) {
		this.setController(controller);
		this.setDesiderata(desiderata);	
		this.dimension= dimension;
		
	}
	
	@Override
	public Control createUIControl(Composite parent) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		Composite group = new Composite(parent, SWT.NONE);
		addComposite(group);
		
		desiderataToView = desiderata;
		
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		group.setData(RWT.CUSTOM_VARIANT, "gray_background");

		lblViewDesiderata  = new Label(group, 0);
		addHeader(lblViewDesiderata);
		
		Label separator = new Label(group,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
			
		desiderataGroup = new Group(group, SWT.NORMAL);
		add(desiderataGroup);
		
		lastString = MessageUtil.unescape(AbosMessages.get().LABEL_DATA_DESIDERATA);
			
		leftList = new LinkedList<>();
		
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		if (!desiderataToView.getAuthor().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR));
		}
		if (!desiderataToView.getEditorial().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_EDITORIAL));
		}
		if (!desiderataToView.getPublicationCity().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_CITY));
		}
		if (!desiderataToView.getEditionNumber().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NUMBER_OF_EDITION));
		}
		if (desiderataToView.getPublicationYear()!=null) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_PUBLICATION_YEAR));
		}
		if (!desiderataToView.getTome().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TOME));
		}
		if (!desiderataToView.getVolume().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_VOLUME));
		}
		if (!desiderataToView.getItemNumber().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NUMBER));
		}
		if (!desiderataToView.getIsbn().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ISBN));
		}
		if (!desiderataToView.getIssn().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ISSN));
		}
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_COST));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_COIN));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_CREATED_BY));
		if (!desiderataToView.getMotive().equals("")) 
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_SUGGESTION_REASON));
		
		List<String> rigthList = new LinkedList<>();
			rigthList.add(desiderataToView.getTitle());
		if (!desiderataToView.getAuthor().equals(""))
			rigthList.add(desiderataToView.getAuthor());
		if (!desiderataToView.getEditorial().equals(""))
			rigthList.add(desiderataToView.getEditorial());
		if (!desiderataToView.getPublicationCity().equals("")) 
			rigthList.add(desiderataToView.getPublicationCity());
		if (!desiderataToView.getEditionNumber().equals(""))
			rigthList.add(desiderataToView.getEditionNumber());
		if (desiderataToView.getPublicationYear()!=null)
			rigthList.add(String.valueOf(desiderataToView.getPublicationYear()));	
		if (!desiderataToView.getTome().equals("")) 
			rigthList.add(desiderataToView.getTome());
		if (!desiderataToView.getVolume().equals("")) 
			rigthList.add(desiderataToView.getVolume());
		if (!desiderataToView.getItemNumber().equals("")) 
			rigthList.add(desiderataToView.getItemNumber());	
		if (!desiderataToView.getIsbn().equals("")) 
			rigthList.add(desiderataToView.getIsbn());		
		if (!desiderataToView.getIssn().equals("")) 
			rigthList.add(desiderataToView.getIssn());		
			
		rigthList.add(String.valueOf(desiderataToView.getPrice()));
		rigthList.add(desiderataToView.getCointype().getNomenclatorName());
		rigthList.add(String.valueOf(desiderataToView.getQuantity()));
		rigthList.add(desiderataToView.getCreator().getPerson().getFullName());
		if (!desiderataToView.getMotive().equals("")) 
		rigthList.add(desiderataToView.getMotive());	
		grupControlsDesiderata=CompoundGroup.printGroup( desiderataGroup, lastString, leftList,rigthList);
		
		// --------------------TABLA DE SUGERENCIAS APROBADAS----------------------
		associatedSuggestions = new Label(group, SWT.NONE);
		addHeader(associatedSuggestions);
		associatedSuggestions.setVisible(true);
		
		orderTable = new CRUDTreeTable(group, SWT.NONE);
		orderTable.setEntityClass(Suggestion.class);
		add(orderTable);
		orderTable.setVisible(true);
		
		TreeTableColumn column[] = {
				new TreeTableColumn(20, 0, "getTitle"),
				new TreeTableColumn(20, 1, "getAuthor"),
				new TreeTableColumn(20, 2, "getUser.getPerson.getFullName"),
				new TreeTableColumn(20, 3, "getPublicationDate"),
		        new TreeTableColumn(20, 4, "getAcceptanceMotive")};
		
		orderTable.createTable(column);
		
		orderTable.addPageChangeListener(new PageChangeListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				if (event.sortData != null) {
					direction = event.sortData.sortDirection;
				}
				searchCurrentReservationTable(event.currentPage - 1, event.pageSize);
			}
		});

		orderTable.setPageSize(10);

		searchCurrentReservationTable(0, orderTable.getPageSize());
		
		orderTable.getPaginator().goToFirstPage();
			
		if (orderTable.getRows().isEmpty()) {
			associatedSuggestions.setVisible(false);
			orderTable.setVisible(false);
		}
		
		btnClose = new Button(group, SWT.PUSH);
		add(btnClose);
		
		btnNew = new Button(group, SWT.PUSH);
		add(btnNew);
		
		if (viewButton!= null) {
			btnNew.setVisible(true);
			btnClose.setVisible(true);
		} else
		{
			btnNew.setVisible(false);
			btnClose.setVisible(false);
		}
		
		l10n();
		return parent;
	}

	@Override
	public void l10n() {
		orderTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE),MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR),MessageUtil.unescape(AbosMessages.get().LABEL_SUGGESTED_BY),MessageUtil.unescape(AbosMessages.get().LABEL_DATE),MessageUtil.unescape(AbosMessages.get().LABEL_REASON)));
		associatedSuggestions.setText(MessageUtil.unescape(AbosMessages.get().LABEL_LIST_OF_ASSOCIATED_SUGGESTIONS));
		lblViewDesiderata.setText(MessageUtil.unescape(AbosMessages.get().VIEW_DESIDERATA));
		btnClose.setText(AbosMessages.get().BUTTON_CLOSE);
		btnNew.setText(AbosMessages.get().BUTTON_NEW);
		
		lastString = MessageUtil.unescape(AbosMessages.get().LABEL_DATA_DESIDERATA);
			
		leftList.clear();
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		if (!desiderataToView.getAuthor().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR));
		}
		if (!desiderataToView.getEditorial().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_EDITORIAL));
		}
		if (!desiderataToView.getPublicationCity().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_CITY));
		}
		if (!desiderataToView.getEditionNumber().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NUMBER_OF_EDITION));
		}
		if (desiderataToView.getPublicationYear()!=null) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_PUBLICATION_YEAR));
		}
		if (!desiderataToView.getTome().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TOME));
		}
		if (!desiderataToView.getVolume().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_VOLUME));
		}
		if (!desiderataToView.getItemNumber().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NUMBER));
		}
		if (!desiderataToView.getIsbn().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ISBN));
		}
		if (!desiderataToView.getIssn().equals("")) {
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ISSN));
		}
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_COST));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_COIN));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_QUANTITY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_CREATED_BY));
		if (!desiderataToView.getMotive().equals("")) 
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_SUGGESTION_REASON));
		CompoundGroup.l10n(grupControlsDesiderata, leftList);
		desiderataGroup.setText(lastString);
	}

	public void searchCurrentReservationTable(int page, int size) {
		suggestionList.clear();
		
		suggestionList = ((AllManagementController) controller).getSuggestion().findSuggestionByIdDesiderata(desiderataToView.getDesidertaID());
		
		orderTable.setTotalElements(suggestionList.size());
		if (suggestionList.size() <= page * size + size) {
			orderTable.setRows(suggestionList.subList(page * size, suggestionList.size()));
		} else {
			orderTable.setRows(suggestionList.subList(page * size, page * size + size));
		}
	
		orderTable.refresh();
	}
	
	public void addListenerNew(SelectionListener listener){
		btnNew.addSelectionListener(listener);
	}
	
	public void addListenerCancel(SelectionListener listener){
		btnClose.addSelectionListener(listener);
	}
	
	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}
	
	public Desiderata getDesiderata() {
		return desiderata;
	}

	public void setDesiderata(Desiderata desiderata) {
		this.desiderata = desiderata;
	}
}