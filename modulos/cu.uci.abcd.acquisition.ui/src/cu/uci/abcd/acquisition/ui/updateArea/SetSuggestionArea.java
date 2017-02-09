package cu.uci.abcd.acquisition.ui.updateArea;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.acquisition.ui.ConsultSuggestions;
import cu.uci.abcd.acquisition.ui.controller.AllManagementController;
import cu.uci.abcd.acquisition.ui.controller.Auxiliary;
import cu.uci.abcd.adquisition.l10n.AbosMessages;
import cu.uci.abcd.domain.acquisition.AdquisitionNomenclator;
import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class SetSuggestionArea extends BaseEditableArea{
	
	private String valor;
	private CRUDTreeTable tabla;
	private ViewController controller;

	private ConsultSuggestions a;
	private Suggestion auxSuggestion;
	private int page = 0;
	private int size = 10;
	private Nomenclator state;
	private String lastString;

	private Label lblReason;
	private Combo cbReason;
	private Button setSuggestion;
	private Suggestion suggestionToView;
	private ValidatorUtils validator;
	private Library library;
	private Group personData;
	private List<Control> grupControlsSuggestion = new ArrayList<>();
	private List<String> leftList = new LinkedList<>();
	
	public SetSuggestionArea(String valor,ViewController controller, CRUDTreeTable tabla,ConsultSuggestions a){
		this.valor=valor;
		this.tabla=tabla;
		this.controller=controller;
		this.a=a;
	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity arg1,
			IVisualEntityManager arg2) {
	
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		setSuggestion = new Button(parent, SWT.PUSH);
		
		setSuggestion.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if(validator.decorationFactory.AllControlDecorationsHide()){
					auxSuggestion = new Suggestion();
					auxSuggestion = suggestionToView;
					
					if(setSuggestion.getText().equals(MessageUtil.unescape(AbosMessages.get().BUTTON_ACCEPT)))
					{
						state=((AllManagementController)controller).getSuggestion().getNomenclator(AdquisitionNomenclator.SUGGESTION_STATE_APROVED);
						auxSuggestion.setState(state);
						auxSuggestion.setAcceptanceMotive((Nomenclator) UiUtils.getSelected((Combo) cbReason));
						((AllManagementController) controller).getSuggestion().addSuggestion(auxSuggestion);
						
						showInformationMessage(AbosMessages.get().MESSAGES_SUGGESTED_ACCEPTED);
				           
					}
					else if(setSuggestion.getText().equals(MessageUtil.unescape(AbosMessages.get().BUTTON_REJECT)))
					{
						state=((AllManagementController)controller).getSuggestion().getNomenclator(AdquisitionNomenclator.SUGGESTION_STATE_REJECTED);
						auxSuggestion.setState(state);
						auxSuggestion.setRejectMotive((Nomenclator) UiUtils.getSelected((Combo) cbReason));
						((AllManagementController) controller).getSuggestion().addSuggestion(auxSuggestion);
						
						showInformationMessage(AbosMessages.get().MESSAGES_SUGGESTED_REJECTED);
					       
					}
				
					a.searchCurrentSuggestionTableByParameters(page, size);
					tabla.destroyEditableArea();
				}else{
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
					
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});	

		if(!valor.equals("ver")){
			if(valor.equals("Aceptar") || valor.equals("Accept")){
				lblReason.setText(MessageUtil.unescape(AbosMessages.get().LABEL_REASON_FOR_ACCEPTANCE));
				setSuggestion.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACCEPT));
			}
			if(valor.equals("Reject")|| valor.equals("Rechazar")){
				lblReason.setText(MessageUtil.unescape(AbosMessages.get().LABEL_REASON_FOR_REJECTION));
				setSuggestion.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_REJECT));
			}
		}
		return parent;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager arg2) {
		library = (Library) SecurityUtils.getService().getPrincipal().getByKey("library");
		
		validator = new ValidatorUtils(new CustomControlDecoration());
		Composite group = new Composite(parent, SWT.NONE);
		addComposite(group);
		

		suggestionToView=(Suggestion)entity.getRow();
		suggestionToView.getSuggestionID();

		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		group.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		personData = new Group(parent, SWT.NORMAL);
		add(personData);

		lastString=MessageUtil.unescape(AbosMessages.get().LABEL_DATA_SUGGESTION);
	
		leftList = new LinkedList<>();
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_EDITORIAL));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_SUGGESTED_BY));
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

		grupControlsSuggestion=CompoundGroup.printGroup(personData, lastString, leftList, rightList);

		br();
		
		lblReason = new Label(parent, SWT.NONE);
		lblReason.setVisible(false);
		add(lblReason);
		
		cbReason = new Combo(parent, SWT.READ_ONLY);
		cbReason.setVisible(false);
		add(cbReason);
		
		if(!valor.equals(MessageUtil.unescape(AbosMessages.get().BUTTON_VIEW))){
				
			lblReason.setVisible(true);
			cbReason.setVisible(true);
			
			if(valor.equals("Rechazar") || valor.equals("Reject"))
				listOfRejectReasons(cbReason);

			if(valor.equals("Aceptar")|| valor.equals("Accept"))
				listOfAcceptanceReasons(cbReason);		
			
			validator.applyValidator(cbReason, "cbReason", DecoratorType.REQUIRED_FIELD, true);
			
		}
		l10n();
		
		return parent;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public void l10n() {
		lastString=MessageUtil.unescape(AbosMessages.get().LABEL_DATA_SUGGESTION);
		leftList.clear();
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_AUTHOR));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_EDITORIAL));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_SUGGESTED_BY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_DATE_OF_REGISTRATION));
		CompoundGroup.l10n(grupControlsSuggestion, leftList);
		personData.setText(lastString);
	}

	void listOfRejectReasons(Combo c){
		initialize(cbReason, ((AllManagementController)controller).getSuggestion().findAllNomenclators(library.getLibraryID(), Nomenclator.REASON_FOR_REJECTION));
		
	}

	void listOfAcceptanceReasons(Combo c){
		initialize(cbReason, ((AllManagementController)controller).getSuggestion().findAllNomenclators(library.getLibraryID(), Nomenclator.APPROVAL_REASON));

	}
}
