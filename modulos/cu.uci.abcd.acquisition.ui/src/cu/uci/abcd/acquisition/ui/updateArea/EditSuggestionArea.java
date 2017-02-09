package cu.uci.abcd.acquisition.ui.updateArea;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.acquisition.ui.ConsultSuggestions;
import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.AdquisitionNomenclator;
import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class EditSuggestionArea extends BaseEditableArea {

	private CRUDTreeTable tabla;
	private ViewController controller;
	
	private ConsultSuggestions consultSuggestions;
	private int page,size;
	private List<Nomenclator> nomenclatorList=new ArrayList<>();

	private Label suggestionData;
	private Label lblAuthor;
	private Label lblTitle;
	private Label lblEditorial;
	private Label lblDate;
	private Label lblNote;
	private Label lblState;
	private Label lblReason;

	private Text txtAuthor ;
	private Text txtTitle;
	private Text txtEditorial;
	private Text txtNote;

	private DateTime dt_date;

	private Combo cbState;
	private Combo cbReason;;

	private Button accept;
	private Suggestion suggestionToEdit;
	private Library library;

	private ValidatorUtils validator;
	
	public EditSuggestionArea(ViewController controller,CRUDTreeTable tabla,ConsultSuggestions c,int page, int size) {
		this.tabla=tabla;
		this.controller=controller;
		this.consultSuggestions=c;
		this.page=page;
		this.size=size;
	}

	@Override
	public Composite createButtons(Composite parent, final IGridViewEntity entity,
			final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
	
		accept = new Button (parent,SWT.None);
		accept.setText(AbosMessages.get().BUTTON_ACCEPT);

		accept.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				Suggestion suggestion= (Suggestion) entity.getRow();
				
				Nomenclator state = (Nomenclator) UiUtils.getSelected(cbState);
				Nomenclator reason = (Nomenclator) UiUtils.getSelected(cbReason);
				
				if (validator.decorationFactory.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorShellMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else
					if (validator.decorationFactory.AllControlDecorationsHide()) {				

				suggestion.setTitle(txtTitle.getText().replaceAll(" +", " ").trim());
				suggestion.setAuthor(txtAuthor.getText().replaceAll(" +", " ").trim());
				suggestion.setEditorial(txtEditorial.getText().replaceAll(" +", " ").trim());
				suggestion.setNote(txtNote.getText().replaceAll(" +", " ").trim());
				suggestion.setState(state);
				suggestion.setRejectMotive(reason);
				suggestion.setUser(suggestion.getUser());			
			
				Suggestion suggestionSave =((AllManagementController)controller).getSuggestion().addSuggestion(suggestion);
			
				showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA);
				
				//consultSuggestions.searchCurrentSuggestionTableByParameters(page, size);
				
				manager.save(new BaseGridViewEntity<Suggestion>(suggestionSave));
				manager.refresh();		
				
			} else
				RetroalimentationUtils.showErrorShellMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
	
				
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		return parent;
	}

	@Override
	public Composite createUI(final Composite parent, final IGridViewEntity entity,
			IVisualEntityManager manager) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		validator = new ValidatorUtils(new CustomControlDecoration());

		setDimension(parent.getParent().getParent().getBounds().width);
		suggestionToEdit=(Suggestion)entity.getRow();
		
		Composite group = new Composite(parent, SWT.NONE);
		addComposite(group);
		
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		group.setData(RWT.CUSTOM_VARIANT, "gray_background");
	
		suggestionData = new Label(group,SWT.NONE);
		addHeader(suggestionData);
		
		Label separator = new Label(group,  SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
	
		lblAuthor = new Label(group,SWT.NONE);
		add(lblAuthor);
		
		txtAuthor = new Text(group,SWT.NONE);
		if (suggestionToEdit.getAuthor().equals(null)) {
			txtAuthor.setText("-");
		}else
		txtAuthor.setText(suggestionToEdit.getAuthor());
		
		add(txtAuthor);

		lblTitle=new Label(group, SWT.NONE);
		add(lblTitle);
		
		txtTitle = new Text(group, SWT.NONE);
		validator.applyValidator(txtTitle, "txtTitle", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtTitle, 100);
		txtTitle.setText(suggestionToEdit.getTitle()); 
		add(txtTitle);
		
		br();
		
		lblEditorial=new Label(group,SWT.NONE);
		add(lblEditorial);
		
		txtEditorial = new Text(group, SWT.NONE);
		if (suggestionToEdit.getEditorial() == null) {
			txtEditorial.setText("-");
			validator.applyValidator(txtEditorial, "txtEditorial", DecoratorType.ALPHA_NUMERICS_SPACES, true,100);
		}else
		txtEditorial.setText(suggestionToEdit.getEditorial());
		add(txtEditorial);

		lblDate=new Label(group, SWT.NONE);
		add(lblDate);
		
		dt_date = new DateTime(group, SWT.BORDER|SWT.DROP_DOWN);
		add(dt_date);

		java.util.Date utilDate = new java.util.Date(suggestionToEdit.getRegisterDate().getTime());

		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(utilDate));
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(utilDate));
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(utilDate));
    
		dt_date.setDate(year, month - 1, day);

		br();
		
		lblNote=new Label(group, SWT.NONE);
		add(lblNote);

		txtNote=new Text(group,SWT.NONE);
		validator.applyValidator(txtNote, "txtNote", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(txtNote, 500);
		txtNote.setText(suggestionToEdit.getNote());
		add(txtNote);
		
		lblState=new Label(group, SWT.NONE);
		add(lblState);

		cbState=new Combo(group, SWT.READ_ONLY);
		add(cbState);
		
		UiUtils.initialize(cbState, ((AllManagementController)controller).getSuggestion().findAllNomenclators(library.getLibraryID(), AdquisitionNomenclator.SUGGESTION_STATE));
		UiUtils.selectValue(cbState, suggestionToEdit.getState());
		
		br();
		
		lblReason=new Label(group, SWT.NONE);
		add(lblReason);
		lblReason.setVisible(false);
		
		cbReason=new Combo(group, SWT.READ_ONLY);
		add(cbReason);
		cbReason.setVisible(false);
		
		if (suggestionToEdit.getState().getNomenclatorID().equals(AdquisitionNomenclator.SUGGESTION_STATE_APROVED)) {
			UiUtils.initialize(cbReason, ((AllManagementController)controller).getSuggestion().findAllNomenclators(library.getLibraryID(), AdquisitionNomenclator.APPROVAL_REASON));
			UiUtils.selectValue(cbReason, suggestionToEdit.getAcceptanceMotive());	
			lblReason.setVisible(true);
			cbReason.setVisible(true);
		}
		else if(suggestionToEdit.getState().getNomenclatorID().equals(AdquisitionNomenclator.SUGGESTION_STATE_REJECTED)){
			UiUtils.initialize(cbReason, ((AllManagementController)controller).getSuggestion().findAllNomenclators(library.getLibraryID(), AdquisitionNomenclator.REASON_FOR_REJECTION));
			UiUtils.selectValue(cbReason, suggestionToEdit.getRejectMotive());
			lblReason.setVisible(true);
			cbReason.setVisible(true);
		}
		else
		{
			lblReason.setVisible(false);
			cbReason.setVisible(false);
		}
		br();	

		cbState.addModifyListener(new ModifyListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void modifyText(ModifyEvent event) {
				if(cbState.getText().equals("Rechazada")){
					cbReason.select(0);
					listOfRejectReasons(cbReason);}
				else if(cbState.getText().equals("Aprobada")){
					cbReason.select(0);
					UiUtils.initialize(cbReason, ((AllManagementController)controller).getSuggestion().findAllNomenclators(library.getLibraryID(), AdquisitionNomenclator.APPROVAL_REASON));
				UiUtils.selectValue(cbReason, suggestionToEdit.getState());
				}
			}
		});

		return parent;

	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public boolean closable() {
		return true;
	}
	@Override
	public void l10n() {
		lblReason.setText(MessageUtil.unescape(AbosMessages.get().LABEL_REASON));
		suggestionData.setText(MessageUtil.unescape(AbosMessages.get().EDIT_SUGGESTION));
		lblAuthor.setText(MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR));
		lblTitle.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		lblEditorial.setText(MessageUtil.unescape(AbosMessages.get().LABEL_EDITORIAL));
		lblDate.setText(MessageUtil.unescape(AbosMessages.get().LABEL_DATE));
		accept.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACCEPT));
		lblState.setText(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		lblNote.setText("Nota");
		
	}


	void listOfRejectReasons(Combo c){
		nomenclatorList=((AllManagementController)controller).getSuggestion().findAllNomenclators(library.getLibraryID(), Nomenclator.REASON_FOR_REJECTION);
		for(Nomenclator nomenclator:nomenclatorList)
			cbReason.add(nomenclator.getNomenclatorDescription());

	}
}