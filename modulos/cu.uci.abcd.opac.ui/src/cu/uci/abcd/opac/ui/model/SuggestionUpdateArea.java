package cu.uci.abcd.opac.ui.model;

import java.sql.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.controller.SuggestionViewController;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

public class SuggestionUpdateArea extends BaseEditableArea {

	private User user;
	
	private Suggestion suggestion;
	private ViewController controller;
	private CRUDTreeTable table;
	private Boolean booleanDate = false;
	private int year;
	private int month;
	private int day;

	private Label bookTitleLabel;
	private Text bookTitleText;
	private Label bookAutorLabel;
	private Text bookAutorText;
	private Label publicationDateLabel;
	private DateTime toDateTime;
	private Label bookEditorLabel;
	private Text bookEditorText;
	private Label noteLabel;
	private Text noteText;
	private ValidatorUtils validator;

	public SuggestionUpdateArea(ViewController controller, CRUDTreeTable table) {
		this.controller = controller;
		this.table = table;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Composite createUI(final Composite parent, IGridViewEntity entity, IVisualEntityManager manager) {

		try {
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		suggestion = entity.getRow();
		validator = new ValidatorUtils(new CustomControlDecoration());
		addComposite(parent);

		bookTitleLabel = new Label(parent, SWT.NORMAL);
		add(bookTitleLabel);

		bookTitleText = new Text(parent, SWT.NORMAL);
		add(bookTitleText);
		validator.applyValidator(bookTitleText, "bookTitleText", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(bookTitleText, 50);
		bookTitleText.setText(suggestion.getTitle());

		br();

		bookAutorLabel = new Label(parent, SWT.NORMAL);
		add(bookAutorLabel);

		bookAutorText = new Text(parent, SWT.NORMAL);
		add(bookAutorText);
		validator.applyValidator(bookAutorText, 50);
		bookAutorText.setText(suggestion.getAuthor());

		br();

		publicationDateLabel = new Label(parent, SWT.NORMAL);
		add(publicationDateLabel);

		toDateTime = new DateTime(parent, SWT.BORDER | SWT.DROP_DOWN);
		add(toDateTime);

		try {
			toDateTime.setYear(suggestion.getPublicationDate().getYear() + 1900);
			toDateTime.setMonth(suggestion.getPublicationDate().getMonth());
			toDateTime.setDay(suggestion.getPublicationDate().getDate());
			toDateTime.setDate(year, month - 1, day);

		} catch (Exception e) {
			e.printStackTrace();
		}

		br();

		bookEditorLabel = new Label(parent, SWT.NORMAL);		
		add(bookEditorLabel);
		

		bookEditorText = new Text(parent, SWT.NORMAL);
		add(bookEditorText);
		bookEditorText.setText(suggestion.getEditorial());
		validator.applyValidator(bookEditorText, "bookEditorTxt1", DecoratorType.ALPHA_NUMERICS_SPACES, true, 50);


		br();

		noteLabel = new Label(parent, SWT.NORMAL);
		add(noteLabel);

		noteText = new Text(parent, SWT.NONE | SWT.WRAP | SWT.V_SCROLL);
		add(noteText);
		validator.applyValidator(noteText, "noteText", DecoratorType.REQUIRED_FIELD, true);
		noteText.setText(suggestion.getNote());

		toDateTime.addMouseListener(new MouseListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void mouseUp(MouseEvent arg0) {

				booleanDate = true;
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				booleanDate = true;
			}
		});

		l10n();
		return parent;
	}

	@Override
	@SuppressWarnings("deprecation")
	public Composite createButtons(final Composite parent, final IGridViewEntity entity, final IVisualEntityManager manager) {

		final Button aceptBtn = new Button(parent, SWT.PUSH);
		aceptBtn.setText((AbosMessages.get().ACCEPT));

		aceptBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent event) {
				if (validator.decorationFactory.isRequiredControlDecorationIsVisible()) {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				}
				else if (validator.decorationFactory.AllControlDecorationsHide()) {
					       
					boolean	suggestionBoolean = ((SuggestionViewController) controller).findAllSuggestionByName(suggestion.getSuggestionID() ,bookTitleText.getText().replaceAll(" +", " ").trim(), user.getUserID().longValue());
					if (suggestionBoolean == true) {
						showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_MODIF_SELECTION_LIST_EXISTS));
					} else {
     
					suggestion.setTitle(bookTitleText.getText().replaceAll(" +", " ").trim());
					suggestion.setAuthor(bookAutorText.getText().replaceAll(" +", " ").trim());
					suggestion.setEditorial(bookEditorText.getText().replaceAll(" +", " ").trim());
					if (booleanDate == true) {
						
						java.util.Date fecha = new java.util.Date();
					    Date fechaSQL = new Date(fecha.getTime());

					    int fromYear = toDateTime.getYear() - 1900;
					    int fromMonth = toDateTime.getMonth();
					    int fromDay = toDateTime.getDay();

					    Date temptoDateTime = new Date(fromYear, fromMonth, fromDay);
						
						if (fechaSQL.after(temptoDateTime)) {
							suggestion.setPublicationDate(temptoDateTime);
							suggestion.setRegisterDate(new Date(new java.util.Date().getTime()));
							
							suggestion.setNote(noteText.getText().replaceAll(" +", " ").trim());

							((SuggestionViewController) controller).addSuggestion(suggestion);
							
							BaseGridViewEntity<Suggestion> suggestionGridViewEntity = new BaseGridViewEntity<Suggestion>(suggestion);
							manager.save(suggestionGridViewEntity);
							table.destroyEditableArea();
							table.refresh();
							showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA);
						}
						else {
							showErrorMessage(MessageUtil.unescape(AbosMessages.get().INFORMATION_ACQUISITION_DATE_SUGGEST));
						}					
					}
					else{
					suggestion.setNote(noteText.getText().replaceAll(" +", " ").trim());

					((SuggestionViewController) controller).addSuggestion(suggestion);

					BaseGridViewEntity<Suggestion> suggestionGridViewEntity = new BaseGridViewEntity<Suggestion>(suggestion);
					manager.save(suggestionGridViewEntity);
					table.destroyEditableArea();
					table.refresh();
					showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA);
					}
					}
				} else {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		l10n();
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

		bookTitleLabel.setText(MessageUtil.unescape(AbosMessages.get().COMBO_ORDER_BY_TITLE));
		bookAutorLabel.setText(MessageUtil.unescape(AbosMessages.get().COMBO_ORDER_BY_AUTHOR));
		publicationDateLabel.setText(MessageUtil.unescape(AbosMessages.get().COMBO_ORDER_PUBLICATION_DATE));
		bookEditorLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_EDITOR));
		noteLabel.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_NOTE));

	}
}
