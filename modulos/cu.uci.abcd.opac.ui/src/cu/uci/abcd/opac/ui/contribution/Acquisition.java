package cu.uci.abcd.opac.ui.contribution;

import java.sql.Date;

import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abcd.opac.ui.controller.SuggestionViewController;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;

public class Acquisition extends ContributorPage {

	private ServiceProvider serviceProvider;

	private User user;

	private Label bookTitleLb;
	private Label bookAuthorLb;
	private Label publicationDateLb;
	private Label bookEditorLb;
	private Label noteLb;

	private Text bookTitleTxt;
	private Text bookAutorTxt;
	private DateTime toDateTime;
	private Text bookEditorTxt;
	private Text noteTxt;

	private Date temptoDateTime;

	private Button aceptBtn;
	private Button cancelBtn;
	private boolean booleanDate = false;
	private boolean suggestionBoolean;

	private int fromYear;
	private int fromMonth;
	private int fromDay;
	private ValidatorUtils validator;
	private Group agregar;

	public Acquisition(ServiceProvider serviceProbider) {
		this.serviceProvider = serviceProbider;
	}

	@Override
	public Control createUIControl(final Composite parent) {

		final ContributorService pageService = this.serviceProvider.get(ContributorService.class);

		try {
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
		} catch (Exception e) {
			e.printStackTrace();
		}

		validator = new ValidatorUtils(new CustomControlDecoration());

		addComposite(parent);

		agregar = new Group(parent, SWT.NONE);
		agregar.setBackground(parent.getBackground());
		agregar.setLayout(new FormLayout());
		FormDatas.attach(agregar).atTop(0).atLeft(10).atRight(10);

		bookTitleLb = new Label(agregar, SWT.NORMAL);
		add(bookTitleLb);
		bookTitleTxt = new Text(agregar, SWT.NORMAL);
		add(bookTitleTxt);
		validator.applyValidator(bookTitleTxt, "bookTitleText", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(bookTitleTxt, 100);
		br();

		bookAuthorLb = new Label(agregar, SWT.NORMAL);
		add(bookAuthorLb);
		bookAutorTxt = new Text(agregar, SWT.NORMAL);
		validator.applyValidator(bookAutorTxt, 50);

		add(bookAutorTxt);

		br();
   
		publicationDateLb = new Label(agregar, SWT.NORMAL);
		add(publicationDateLb);
		toDateTime = new DateTime(agregar, SWT.BORDER | SWT.DROP_DOWN);
		add(toDateTime);
		br();

		bookEditorLb = new Label(agregar, SWT.NORMAL);
		add(bookEditorLb);
		bookEditorTxt = new Text(agregar, SWT.NORMAL);
		validator.applyValidator(bookEditorTxt, "bookEditorTxt1", DecoratorType.ALPHA_NUMERICS_SPACES, true, 50);
		validator.applyValidator(bookEditorTxt, 500);
		add(bookEditorTxt);

		br();

		noteLb = new Label(agregar, SWT.NORMAL);
		add(noteLb);
		noteTxt = new Text(agregar, SWT.NONE | SWT.WRAP | SWT.V_SCROLL);
		add(noteTxt);
		validator.applyValidator(noteTxt, "noteText", DecoratorType.REQUIRED_FIELD, true);

		br();
		add(new Label(agregar, 0));
		br();
		
		

		cancelBtn = new Button(agregar, SWT.PUSH);
		add(cancelBtn);
		aceptBtn = new Button(agregar, SWT.PUSH);
		add(aceptBtn);

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
   
		aceptBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("deprecation")
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				synchronized (this) {

					if (validator.decorationFactory.isRequiredControlDecorationIsVisible()) {
						showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
					} else if (validator.decorationFactory.AllControlDecorationsHide()) {

						suggestionBoolean = ((SuggestionViewController) controller).findAllSuggestionByName(bookTitleTxt.getText().replaceAll(" +", " ").trim(), user.getUserID().longValue());
						if (suggestionBoolean == true) {
							showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_INF_SELECTION_LIST_EXISTS));
						} else {

							Suggestion suggestion = new Suggestion();

							try {

								user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");

								suggestion.setUser(user);
								suggestion.setLibrary(user.getLibrary());
								suggestion.setTitle(bookTitleTxt.getText().replaceAll(" +", " ").trim());
								suggestion.setAuthor(bookAutorTxt.getText().replaceAll(" +", " ").trim());
								suggestion.setEditorial(bookEditorTxt.getText().replaceAll(" +", " ").trim());

								java.util.Date date = new java.util.Date();
								Date registerDate = new Date(date.getTime());

								suggestion.setRegisterDate(registerDate);

								if (booleanDate == true) {

									java.util.Date fecha = new java.util.Date();
									Date fechaSQL = new Date(fecha.getTime());

									fromYear = toDateTime.getYear() - 1900;
									fromMonth = toDateTime.getMonth();
									fromDay = toDateTime.getDay();

									temptoDateTime = new Date(fromYear, fromMonth, fromDay);

									if (fechaSQL.after(temptoDateTime)) {
										suggestion.setPublicationDate(temptoDateTime);
										suggestion.setRegisterDate(new Date(new java.util.Date().getTime()));

										suggestion.setNote(noteTxt.getText().replaceAll(" +", " ").trim());

										suggestion.setState(((SuggestionViewController) controller).findNomenclator(Nomenclator.SUGGESTION_STATE_PENDING));

										((SuggestionViewController) controller).addSuggestion(suggestion);

										showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));

										Acquisition.this.notifyListeners(SWT.Dispose, new Event());
										pageService.selectContributor("AdquisicionID");
									} else {
										showErrorMessage(MessageUtil.unescape(AbosMessages.get().INFORMATION_ACQUISITION_DATE_SUGGEST));
									}
								} else {
									suggestion.setNote(noteTxt.getText().replaceAll(" +", " ").trim());

									suggestion.setState(((SuggestionViewController) controller).findNomenclator(Nomenclator.SUGGESTION_STATE_PENDING));

									((SuggestionViewController) controller).addSuggestion(suggestion);

									showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));

									Acquisition.this.notifyListeners(SWT.Dispose, new Event());
									pageService.selectContributor("AdquisicionID");
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else
						showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		cancelBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION), new DialogCallback() {
					private static final long serialVersionUID = 1L;

					@Override
					public void dialogClosed(int returnCode) {
						if (returnCode == 0) {
							Acquisition.this.notifyListeners(SWT.Dispose, new Event());
						}
					}
				});

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		l10n();
		return parent;
	}

	@Override
	public String getID() {
		return "AdquisicionID";
	}

	@Override
	public void l10n() {

		// header.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_HEADER));

		bookTitleLb.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_TITLE) + ":");
		bookAuthorLb.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_AUTHOR) + ":");
		publicationDateLb.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_PUBLICATION_DATE) + ":");
		bookEditorLb.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_EDITOR) + ":");
		noteLb.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ACQUISITION_BOOK_NOTE) + ":");
		agregar.setText(MessageUtil.unescape(AbosMessages.get().NEW_SUGGESTION));
		aceptBtn.setText(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().BUTTON_ACCEPT));
		cancelBtn.setText(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().BUTTON_CANCEL));

		refresh();
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_ACQUISITION);
	}

}
