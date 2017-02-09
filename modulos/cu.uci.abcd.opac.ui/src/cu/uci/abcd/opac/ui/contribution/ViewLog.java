package cu.uci.abcd.opac.ui.contribution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.domain.Page;
import org.unesco.jisis.corelib.common.FormattedRecord;

import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.opac.Comment;
import cu.uci.abcd.domain.opac.Tag;
import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abcd.opac.ui.listener.CreateFindByAutor;
import cu.uci.abcd.opac.ui.listener.OpacLoginListener;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.AbosImageUtil;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.validation.CustomControlDecoration;
import cu.uci.abos.core.validation.DecoratorType;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.TreeTableColumn;
import cu.uci.abos.core.widget.paginator.PageChangeListener;
import cu.uci.abos.core.widget.paginator.PageChangedEvent;

public class ViewLog extends ContributorPage {

	private User user;

	private ValidatorUtils validator;

	private Composite result;
	private Composite tagsCompo;
	private Composite addTagsCompo;

	private TabFolder views;
	private TabFolder tabFolder;
	private TabItem normalView;
	private TabItem isbdView;
	private TabItem marcView;

	private Composite compoNormalView;
	private Composite compoIsbdView;
	private Composite compoMarcView;

	private Button addNewTagBtn;
	private Button acceptTagBtn;
	private Button cancelTagBtn;

	private RecordIsis record;
	private String recordControlNumber;

	private Label state;
	private Label titleContent;
	private Label by;
	private Label publicationLabel;
	private Label topicsLabel;
	private Label dateLabel;
	private TabItem tabItemExistences;
	private TabItem tabItemDescription;
	private TabItem tabItemComment;
	private ServiceProvider serviceProvider;
	private Map<Integer, String> l10nCategory;
	private Map<Integer, String> l10nModule;

	// TAGS
	private Label newTagLabel;

	public RecordIsis getRecord() {
		return record;
	}

	public void setRecord(RecordIsis record) {
		this.record = record;
	}

	public ViewLog(ServiceProvider serviceProvider) {

		this.serviceProvider = serviceProvider;
		initializel10n();
	}

	void initializel10n() {
		l10nCategory = new HashMap<Integer, String>();
		l10nCategory.put(0, "categoryEs");
		l10nCategory.put(1, "categoryEn");

		l10nModule = new HashMap<Integer, String>();
		l10nModule.put(0, "moduleNameEs");
		l10nModule.put(1, "moduleNameEn");

	}

	public String title;
	public String autor;
	public String publication;
	public String fisicalDetail;
	public String topics = "";
	public String date;
	public List<Tag> tags = new ArrayList<Tag>();
	public String description;
	public String srcOnline;

	private Link onlineSrcLk;
	private Link logginOffTag;

	private Label topicTag;
	private Label firstTagLk;
	private Label tagLk;
	private Label lastLeftTagLink;
	private Label tagTopLk;

	private Text newTagtext;

	private Image searchImage;

	private int rowCount = 5;

	private Label bookAutor;

	private List<Comment> comments = new ArrayList<Comment>();
	private Composite row;
	private Button createComment;
	private Button cancel;
	private Button addcomment;
	private Composite compoComment;
	private Composite commentComposite;
	private Label noCommentsLb;
	private Composite lastCompo;
	private Image imageIcon;
	private Image imageIconcancel;
	private Image imageIconAddComment;
	private Text commentText;
	private Label addCommentLb;
	private Comment tempComment;
	private Composite commentCompositeFirst;
	private Composite listComposite;
	private Button editcomment;
	private Button deletecomment;

	private Text commentTextList;
	private Label userLabelList;
	private Label dateLabelList;
	private Label idLabel;
	private Boolean userLogin = false;
	private Boolean editado = false;
	private Link singIn;

	private Browser marcBrowser;
	private Browser isbdBrowser;

	// Existencias
	private Composite compoExistences;
	private CRUDTreeTable existencesTable;
	public String controlNumber;
	private Link countAvailability;

	private static String orderByString = "controlNumber";
	private static int direction = 1024;

	@Override
	public Control createUIControl(Composite parent) {

		result = parent;

		validator = new ValidatorUtils(new CustomControlDecoration());

		try {
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
		} catch (Exception e) {
			user = null;
		}

		Composite resize = new Composite(parent, 0);
		resize.setVisible(false);
		FormDatas.attach(resize).atTop().atRight().withWidth(0).withHeight(Display.getCurrent().getBounds().height - 100);

		views = new TabFolder(result, SWT.None);
		FormDatas.attach(views).atTop(0).atLeft(10).atRight(10);

		compoNormalView = new Composite(views, SWT.V_SCROLL | SWT.H_SCROLL);
		compoNormalView.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");

		normalView = new TabItem(views, SWT.None);

		compoNormalView.setLayout(new FormLayout());
		FormDatas.attach(compoNormalView).atTopTo(views, 1).atLeft(0).atRight(0);

		// MARC VIEW
		compoMarcView = new Composite(views, SWT.None);
		compoMarcView.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");

		marcView = new TabItem(views, SWT.None);

		compoMarcView.setLayout(new FormLayout());
		FormDatas.attach(compoMarcView).atTopTo(views, 1).atLeft(0).atRight(0);

		marcBrowser = new Browser(compoMarcView, 0);
		marcBrowser.setLayout(new FormLayout());
		FormDatas.attach(marcBrowser).atTopTo(compoMarcView, 0).atLeftTo(compoMarcView, 0).atRight(10).withHeight(385).withWidth(1070).atBottom(20);

		// ISBD VIEW
		compoIsbdView = new Composite(views, SWT.None);
		compoIsbdView.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");

		isbdView = new TabItem(views, SWT.None);

		compoIsbdView.setLayout(new FormLayout());
		FormDatas.attach(compoIsbdView).atTopTo(views, 1).atLeft(0).atRight(0);

		isbdBrowser = new Browser(compoIsbdView, 0);
		isbdBrowser.setLayout(new FormLayout());
		FormDatas.attach(isbdBrowser).atTopTo(compoIsbdView, 0).atLeftTo(compoIsbdView, 0).atRight(10).withHeight(385).withWidth(1070).atBottom(20);

		if (record != null) {
			try {

				List<String> dataBaseFormats = ((AllManagementOpacViewController) controller).getDatabaseFormats("marc21", record.getLibrary().getIsisDefHome());

				for (int i = 0; i < dataBaseFormats.size(); i++) {
					if (dataBaseFormats.get(i).equals("RAW")) {

						String format = dataBaseFormats.get(i);

						FormattedRecord formattedRecord = ((AllManagementOpacViewController) controller).getFormattedRecord(record.getDataBaseName(), record.getRecord(), format, record.getLibrary().getIsisDefHome());

						String htmlString = formattedRecord.getRecord();

						marcBrowser.setText(htmlString);
					}
					if (dataBaseFormats.get(i).equals("ISBD")) {

						String format = dataBaseFormats.get(i);

						FormattedRecord formattedRecord = ((AllManagementOpacViewController) controller).getFormattedRecord("marc21", record.getRecord(), format, record.getLibrary().getIsisDefHome());

						String htmlString = formattedRecord.getRecord();

						isbdBrowser.setText(htmlString);
					}
				}

				compoMarcView.update();
				marcView.setControl(compoMarcView);

				compoIsbdView.update();
				isbdView.setControl(compoIsbdView);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		compoMarcView.update();
		marcView.setControl(compoMarcView);

		compoIsbdView.update();
		isbdView.setControl(compoIsbdView);

		state = new Label(compoNormalView, SWT.NORMAL);
		state.setFont(new Font(parent.getDisplay(), "Arial", 13, SWT.BOLD));
		FormDatas.attach(state).atTop(10).atLeft(30);

		Link horSeparator = new Link(compoNormalView, SWT.NORMAL);
		horSeparator
				.setText("_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
		horSeparator.setFont(new Font(parent.getDisplay(), "Arial", 4, SWT.NONE));
		FormDatas.attach(horSeparator).atTopTo(state, -7).atLeft(28);

		titleContent = new Label(compoNormalView, SWT.WRAP);
		titleContent.setText(title);
		titleContent.setFont(new Font(parent.getDisplay(), "Arial", 17, SWT.BOLD));
		FormDatas.attach(titleContent).atTopTo(horSeparator, 20).atLeft(30).atRight();

		by = new Label(compoNormalView, SWT.NORMAL);
		by.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.BOLD));
		FormDatas.attach(by).atTopTo(titleContent, 8).atLeft(30);
  
		bookAutor = new Label(compoNormalView, SWT.NORMAL);
		bookAutor.setText(autor);
		bookAutor.setFont(new Font(parent.getDisplay(), "Arial", 14, SWT.BOLD));
		FormDatas.attach(bookAutor).atTopTo(titleContent, 5).atLeftTo(by, 5);

		bookAutor.addListener(SWT.Selection, new CreateFindByAutor());
     
		publicationLabel = new Label(compoNormalView, SWT.NORMAL);
		publicationLabel.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.BOLD));
		FormDatas.attach(publicationLabel).atTopTo(bookAutor, 3).atTopTo(by, 3).atLeft(30);

		final Label publicationContent = new Label(compoNormalView, SWT.NORMAL);
		publicationContent.setText(publication);
		publicationContent.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.NORMAL));
		FormDatas.attach(publicationContent).atTopTo(bookAutor, 3).atTopTo(by, 3).atLeftTo(publicationLabel, 5);

		topicsLabel = new Label(compoNormalView, SWT.NORMAL);
		topicsLabel.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.BOLD));
		FormDatas.attach(topicsLabel).atTopTo(publicationLabel, 3).atLeft(30);

		final Label topicsContent = new Label(compoNormalView, SWT.NORMAL);
		topicsContent.setText(topics);
		topicsContent.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.NORMAL));
		FormDatas.attach(topicsContent).atTopTo(publicationLabel, 3).atLeftTo(topicsLabel, 5);

		dateLabel = new Label(compoNormalView, SWT.NORMAL);
		dateLabel.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.BOLD));
		FormDatas.attach(dateLabel).atTopTo(topicsLabel, 3).atLeft(30);

		final Label dateContent = new Label(compoNormalView, SWT.NORMAL);
		dateContent.setText(date);
		dateContent.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.NORMAL));
		FormDatas.attach(dateContent).atTopTo(topicsLabel, 3).atLeftTo(dateLabel, 5);

		onlineSrcLk = new Link(compoNormalView, SWT.NORMAL);
		onlineSrcLk.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.NONE));
		FormDatas.attach(onlineSrcLk).atTopTo(dateLabel, 3).atLeft(29);

		try {
			srcOnline = record.getUrl();
		} catch (Exception e) {
		}

		if (srcOnline != null & srcOnline != "") {
			onlineSrcLk.setText("<a>" + MessageUtil.unescape((AbosMessages.get().RESOURCE)) + "</a>");

			Link disponibilityContent = new Link(compoNormalView, SWT.WRAP);
			disponibilityContent.setText(srcOnline);
			disponibilityContent.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.BOLD));
			FormDatas.attach(disponibilityContent).atTopTo(dateLabel, 3).atLeftTo(onlineSrcLk, 5);

		} else
			onlineSrcLk.setText(MessageUtil.unescape((AbosMessages.get().NO_ONLINE_RESOURCES)));

		addTagsCompo = new Composite(compoNormalView, 0);
		addTagsCompo.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		addTagsCompo.setLayout(new FormLayout());
		FormDatas.attach(addTagsCompo).atTopTo(onlineSrcLk).atLeft(25);
		addTagsCompo.setVisible(false);

		newTagLabel = new Label(addTagsCompo, 0);
		newTagLabel.setFont(new Font(result.getDisplay(), "Arial", 11, SWT.BOLD));
		newTagLabel.setText(MessageUtil.unescape((AbosMessages.get().NEW_TAG)));
		FormDatas.attach(newTagLabel).atTop(9).atLeft();

		newTagtext = new Text(addTagsCompo, 0);
		newTagtext.setFont(new Font(result.getDisplay(), "Arial", 11, SWT.BOLD));
		FormDatas.attach(newTagtext).atTop(5).atLeftTo(newTagLabel, 5).withWidth(100).withHeight(8);

		validator.applyValidator(newTagtext, "newTagtext1", DecoratorType.REQUIRED_FIELD, true, 20);
		validator.applyValidator(newTagtext, 20);

		searchImage = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("opac-reserved"));
		acceptTagBtn = new Button(addTagsCompo, SWT.PUSH);
		acceptTagBtn.setImage(searchImage);
		acceptTagBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		acceptTagBtn.setText(MessageUtil.unescape((AbosMessages.get().ACCEPT)));
		acceptTagBtn.setFont(new Font(result.getDisplay(), "Arial", 11, SWT.BOLD));

		FormDatas.attach(acceptTagBtn).atTop().atLeftTo(newTagtext, 5);

		FormDatas.attach(acceptTagBtn).atTop().atLeftTo(newTagtext, 10);

		searchImage = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("redcross"));
		cancelTagBtn = new Button(addTagsCompo, SWT.PUSH);
		cancelTagBtn.setImage(searchImage);
		cancelTagBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		cancelTagBtn.setText(MessageUtil.unescape((AbosMessages.get().CANCEL)));
		cancelTagBtn.setFont(new Font(result.getDisplay(), "Arial", 11, SWT.BOLD));
		FormDatas.attach(cancelTagBtn).atTop().atLeftTo(acceptTagBtn);

		acceptTagBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (validator.decorationFactory.isRequiredControlDecorationIsVisible()) {
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else if (validator.decorationFactory.AllControlDecorationsHide()) {

					try {

						if (newTagtext.getText().replaceAll(" +", " ").trim() != null && newTagtext.getText().replaceAll(" +", " ").trim() != "") {
							Tag tag = new Tag();
							tag.setDuser(user);
							tag.setMaterial(record.getControlNumber());
							tag.setDatabasename(record.getDataBaseName());
							tag.setLibrary(user.getLibrary());
							tag.setTagName(newTagtext.getText().replaceAll(" +", " ").trim());
							tag.setActionDate(new java.sql.Date(new Date().getTime()));
							tag.setTitle(title);

							/**
							 * revisar añadir el id material
							 * 
							 */

							if (!((AllManagementOpacViewController) controller).checkUniqueTag(user.getUserID(), tag.getTagName(), tag.getMaterial(), tag.getDatabasename(), tag.getLibrary().getLibraryID())) {

								((AllManagementOpacViewController) controller).addtag(tag);

								addTagsCompo.setVisible(false);
								newTagtext.setText("");
								printTags();
								l10n();
								showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT);
							} else
								showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_TAG_ALREADY_EXISTS));

						} else
							showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else
					showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		cancelTagBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				addTagsCompo.setVisible(false);
				newTagtext.setText("");
				addNewTagBtn.setVisible(true);
				refresh();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		logginOffTag = new Link(compoNormalView, 0);
		logginOffTag.setText(MessageUtil.unescape((AbosMessages.get().LOGIN_TO_ADD_TAGS)));
		logginOffTag.setFont(new Font(result.getDisplay(), "Arial", 11, SWT.NONE));
		FormDatas.attach(logginOffTag).atTopTo(onlineSrcLk, 5).atLeft(27);

		tagsCompo = new Composite(compoNormalView, 0);
		tagsCompo.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		tagsCompo.setLayout(new FormLayout());
		FormDatas.attach(tagsCompo).atTopTo(addTagsCompo).atLeft(27);

		compoNormalView.update();
		normalView.setControl(compoNormalView);

		printTags();

		tabFolder = new TabFolder(result, SWT.NONE);
		FormDatas.attach(tabFolder).atTopTo(views, 10).atLeft(10).atRight(10);

		compoExistences = new Composite(tabFolder, SWT.None);
		compoExistences.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");

		tabItemExistences = new TabItem(tabFolder, SWT.None);
		tabItemExistences.setControl(compoExistences);

		compoExistences.setLayout(new FormLayout());
		FormDatas.attach(compoExistences).atTopTo(tabFolder, 1).atLeft(0).atRight(0);

		Composite compoDescription = new Composite(tabFolder, SWT.None);
		compoDescription.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");

		tabItemDescription = new TabItem(tabFolder, SWT.None);
		tabItemDescription.setControl(compoDescription);

		compoDescription.setLayout(new FormLayout());
		FormDatas.attach(compoDescription).atTopTo(tabFolder, 1).atLeft(0).atRight(0);

		compoComment = new Composite(tabFolder, SWT.None);
		compoComment.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");

		tabItemComment = new TabItem(tabFolder, SWT.None);
		tabItemComment.setControl(compoComment);

		compoComment.setLayout(new FormLayout());
		FormDatas.attach(compoComment).atTopTo(tabFolder, 1).atLeft(0).atRight(0);

		Label descriptionContent = new Label(compoDescription, SWT.None | SWT.WRAP);
		descriptionContent.setText(description);
		descriptionContent.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.NORMAL));
		FormDatas.attach(descriptionContent).atTop(10).atLeft(20);

		// GESTIONAR COMENTARIO \\

		commentCompositeFirst = new Composite(compoComment, SWT.NONE);
		commentCompositeFirst.setBackground(compoComment.getBackground());
		commentCompositeFirst.setLayout(new FormLayout());
		FormDatas.attach(commentCompositeFirst).atLeft(10).atRight(10).atTop(5);

		commentComposite = new Composite(commentCompositeFirst, SWT.BORDER);
		commentComposite.setBackground(commentCompositeFirst.getBackground());
		commentComposite.setLayout(new FormLayout());
		commentComposite.setVisible(false);

		createComment = new Button(commentCompositeFirst, SWT.PUSH);
		imageIcon = AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/plus-square.png", false);
		createComment.setImage(imageIcon);
		createComment.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(createComment).atTop(16).atLeft(25);

		lastCompo = new Composite(compoComment, SWT.NORMAL);
		lastCompo.setBackground(compoComment.getBackground());
		lastCompo.setLayout(new FormLayout());
		FormDatas.attach(lastCompo).atTopTo(commentCompositeFirst).atLeft(10).atRight(10);

		noCommentsLb = new Label(commentCompositeFirst, 0);
		FormDatas.attach(noCommentsLb).atTop(10).atLeft(30);

		addCommentLb = new Label(commentCompositeFirst, 0);
		FormDatas.attach(addCommentLb).atTopTo(noCommentsLb, 8).atLeft(30).atBottom(50);

		commentText = new Text(commentComposite, SWT.WRAP);
		commentText.setBackground(commentComposite.getBackground());
		commentText.setMessage(cu.uci.abcd.opac.l10n.AbosMessages.get().POST_COMMENT);
		commentText.setData(RWT.CUSTOM_VARIANT, "comment");

		validator.applyValidator(commentText, "commentText1", DecoratorType.REQUIRED_FIELD, true);
		validator.applyValidator(commentText, 500);

		cancel = new Button(commentComposite, SWT.PUSH);
		imageIconcancel = AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/remove.png", false);
		cancel.setImage(imageIconcancel);
		cancel.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(cancel).atTopTo(commentText).atRight(10).atBottom(-10);
		cancel.setVisible(false);

		addcomment = new Button(commentComposite, SWT.PUSH);
		imageIconAddComment = AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/opac-reserved.png", false);
		addcomment.setImage(imageIconAddComment);
		addcomment.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(addcomment).atTopTo(commentText).atRightTo(cancel, 2).atBottom(-10);
		addcomment.setVisible(false);

		findByControlNumber();

		if (user != null) {

			userLogin = true;
			noCommentsLb.setVisible(false);
			addCommentLb.setVisible(false);

			createComment.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent arg0) {

					FormDatas.attach(commentCompositeFirst).atLeft(10).atRight(10).atTop(5);
					FormDatas.attach(commentComposite).atTop(1).atLeft(10).atRight(10).withHeight(100);
					createComment.setVisible(false);
					commentComposite.setVisible(true);
					cancel.setVisible(true);
					addcomment.setVisible(true);
					// FormDatas.attach(commentText).atTop(1).atLeft(1).atRight(1).atBottomTo(addcomment,
					// 5).withHeight(50);
					FormDatas.attach(commentText).atTop(1).atLeft(1).atRight(15).atBottomTo(addcomment, 5).withHeight(50);
					commentText.setVisible(true);
					noCommentsLb.setVisible(false);
					refresh();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});

			cancel.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent arg0) {

					MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_CANCEL_OPERATION), new DialogCallback() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {
								try {
									FormDatas.attach(commentCompositeFirst).atLeft(10).atRight(10).atTop(5);

									idLabel.setText("");
									createComment.setVisible(true);
									commentComposite.setVisible(false);
									commentText.setText("");
									Print();
									refresh();

								} catch (Exception e2) {
									RetroalimentationUtils.showInformationMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_ERROR_USED_DATA));
								}
							}
						}
					});
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});

			idLabel = new Label(commentComposite, SWT.NORMAL);
			idLabel.setFont(new Font(parent.getDisplay(), "Arial", 13, SWT.BOLD));
			FormDatas.attach(idLabel).atTop().atLeft(5);
			idLabel.setVisible(false);

			addcomment.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent arg0) {

					if (validator.decorationFactory.isRequiredControlDecorationIsVisible()) {
						showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
					} else if (validator.decorationFactory.AllControlDecorationsHide()) {

						tempComment = new Comment();

						if (editado == true) {
							showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA);
							editado = false;
						}

						if (!idLabel.getText().isEmpty()) {
							tempComment.setId(Long.parseLong(idLabel.getText()));
							createComment.setVisible(false);
						}

						tempComment.setDuser(user);
						tempComment.setDescription(commentText.getText().replaceAll(" +", " ").trim());
						tempComment.setActionDate(new java.sql.Date(new Date().getTime()));
						tempComment.setLibrary(user.getLibrary());
						tempComment.setDatabasename(record.getDataBaseName());
						tempComment.setState(901);

						try {
							recordControlNumber = (record.getControlNumber());
							tempComment.setMaterial(recordControlNumber);
						} catch (Exception e) {
							e.printStackTrace();
						}

						createComment.setVisible(true);

						if (commentText.getText().isEmpty()) {

							ViewLog.this.showWarningMessage(cu.uci.abcd.opac.l10n.AbosMessages.get().WRITE_COMMENTS);
							Print();
							refresh();
						}

						else {
							((AllManagementOpacViewController) controller).addCommentIntoDB(tempComment);

							comments.add(tempComment);

							Print();
							FormDatas.attach(commentCompositeFirst).atLeft(10).atRight(10).atTop(5);

							idLabel.setText("");
							commentComposite.setVisible(false);
							commentText.setText("");
							refresh();
						}

					} else
						showErrorMessage(MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_INCORRECT_DATA));
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});

			try {
				Print();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {

			if (userLogin == false) {
				noCommentsLb.setVisible(false);
				createComment.setVisible(false);

				addCommentLb.setVisible(true);
				singIn = new Link(commentCompositeFirst, SWT.NONE);
				FormDatas.attach(singIn).atTopTo(addCommentLb, 15).atLeft(30);
				singIn.addListener(SWT.Selection, new OpacLoginListener(serviceProvider));

				Print();
			}

			if (comments.isEmpty()) {
				if (userLogin == true) {

					FormDatas.attach(commentCompositeFirst).atLeft(10).atRight(10).atTop(5);
					FormDatas.attach(commentComposite).atTop(1).atLeft(10).atRight(10).withHeight(100);
					noCommentsLb.setVisible(true);
					FormDatas.attach(createComment).atTopTo(noCommentsLb, 5).atLeft(20);
					createComment.setVisible(true);
					addcomment.setVisible(true);
					cancel.setVisible(true);
					l10n();
				} else {

					createComment.setVisible(false);
					addCommentLb.setVisible(true);
					noCommentsLb.setVisible(true);
					l10n();

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// GESTIONAR EXISTENCIAS \\

		countAvailability = new Link(compoExistences, SWT.NORMAL);
		countAvailability.setFont(new Font(parent.getDisplay(), "Arial", 14, SWT.BOLD));
		FormDatas.attach(countAvailability).atTop(8).atLeft(30);

		existencesTable = new CRUDTreeTable(compoExistences, SWT.NONE);
		existencesTable.setEntityClass(LoanObject.class);
		TreeTableColumn existencesColumns[] = { new TreeTableColumn(25, 0, "getRecordType"), new TreeTableColumn(25, 1, "getRoom"), new TreeTableColumn(25, 2, "getInventorynumber"), new TreeTableColumn(25, 4, "getLoanObjectState") };

		existencesTable.createTable(existencesColumns);
		existencesTable.setPageSize(10);

		existencesTable.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = -2654809274899010553L;

			@Override
			public void handleEvent(Event arg0) {
				refresh();
			}
		});

		FormDatas.attach(existencesTable).atTopTo(countAvailability).atRight(5).atLeft(5);

		try {
			searchCoppys(0, existencesTable.getPaginator().getPageSize());
			existencesTable.getPaginator().goToFirstPage();
		} catch (Exception e) {
			e.printStackTrace();
		}

		existencesTable.addPageChangeListener(new PageChangeListener() {
			public void pageChanged(final PageChangedEvent event) {

				searchCoppys(event.currentPage - 1, event.pageSize);
				refresh();

			}
		});

		l10n();
		return parent;
	}

	private void searchCoppys(int page, int size) {

		existencesTable.clearRows();
		Page<LoanObject> list = ((AllManagementOpacViewController) controller).findAllCoppysByLoanObjectAndLibrary(controlNumber, record.getLibrary().getLibraryID(), page, size, direction, orderByString);
		existencesTable.getPaginator().setTotalElements((int) list.getTotalElements());
		countAvailability
				.setText((MessageUtil.unescape(AbosMessages.get().AVAILABILITY) + " (" + " " + ((AllManagementOpacViewController) controller).findLoanObjectByControlNumberAndLibrary(controlNumber, record.getLibrary().getLibraryID()) + " " + MessageUtil.unescape(AbosMessages.get().LABEL_TOTAL_LIST
						+ " )")));
		existencesTable.setRows(list.getContent());
		existencesTable.refresh();

	}

	public void Print() {

		Control[] temp = compoComment.getChildren();
		try {

			for (int i = 1; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
			e.printStackTrace();
		}

		findByControlNumber();

		lastCompo = new Composite(compoComment, SWT.NORMAL);
		lastCompo.setBackground(compoComment.getBackground());
		lastCompo.setLayout(new FormLayout());
		FormDatas.attach(lastCompo).atTopTo(commentCompositeFirst).atLeft(10).atRight(10);

		for (int i = 0; i < comments.size(); i++) {

			row = CreateRows(comments.get(i));
			row.notifyListeners(SWT.Resize, new Event());

			int testSize = (comments.get(i).getDescription().split(" ")).length;

			if (testSize < 100)
				testSize = 100;

			FormDatas.attach(row).atTopTo(lastCompo, 10).atLeft(10).atRight(10).withHeight(testSize);

			lastCompo = row;
		}

		compoComment.notifyListeners(SWT.Resize, new Event());
		compoComment.layout(true, true);
		compoComment.redraw();
		refresh();

	}

	public Composite CreateRows(Comment comment) {

		listComposite = new Composite(compoComment, SWT.BORDER);
		listComposite.setBackground(compoComment.getBackground());
		listComposite.setLayout(new FormLayout());
		FormDatas.attach(listComposite).atLeft(10).atRight(10);

		userLabelList = new Label(listComposite, SWT.NORMAL);
		userLabelList.setFont(new Font(listComposite.getDisplay(), "Arial", 13, SWT.BOLD));
		FormDatas.attach(userLabelList).atLeft(5);
		userLabelList.setText(comment.getDuser().getUsername() + " " + cu.uci.abcd.opac.l10n.AbosMessages.get().LABEL_USER_SAID);
		userLabelList.setVisible(true);

		dateLabelList = new Label(listComposite, SWT.NORMAL);
		dateLabelList.setFont(new Font(listComposite.getDisplay(), "Arial", 13, SWT.BOLD));
		FormDatas.attach(dateLabelList).atRight(3);
		dateLabelList.setText(comment.getActionDate().toString());
		dateLabelList.setVisible(true);

		commentTextList = new Text(listComposite, SWT.WRAP);
		commentTextList.setBackground(listComposite.getBackground());
		commentTextList.setData(RWT.CUSTOM_VARIANT, "comment");
		commentTextList.setText(comment.getDescription());
		commentTextList.setEditable(false);
		FormDatas.attach(commentTextList).atTopTo(userLabelList, 10).atLeft(10).atRight(10).atBottom(50);

		deletecomment = new Button(listComposite, SWT.PUSH);
		FormDatas.attach(deletecomment).atTopTo(commentTextList, 5).atRight(2).atBottom(5);
		imageIconAddComment = AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/trash-o.png", false);
		deletecomment.setImage(imageIconAddComment);
		deletecomment.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		deletecomment.setText(cu.uci.abcd.opac.l10n.AbosMessages.get().DELETE_COMMENT);
		deletecomment.setVisible(false);

		editcomment = new Button(listComposite, SWT.PUSH);
		FormDatas.attach(editcomment).atTopTo(commentTextList, 5).atRightTo(deletecomment, 2).atBottom(5);
		imageIconAddComment = AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/pencil.png", false);
		editcomment.setImage(imageIconAddComment);
		editcomment.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		editcomment.setText(cu.uci.abcd.opac.l10n.AbosMessages.get().EDIT_COMMENT);
		editcomment.setVisible(false);

		try {

			if (comment.getDuser().getUserID().equals(user.getUserID())) {
				deletecomment.setVisible(true);
				editcomment.setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		final long id = comment.getId();
		deletecomment.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_WARN_DELET_DATA), new DialogCallback() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void dialogClosed(int returnCode) {
						if (returnCode == 0) {
							try {
								showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_DELETE_ONE_ITEM);
								((AllManagementOpacViewController) controller).deleteCommentIntoDB(id);
								Print();
								refresh();

							} catch (Exception e2) {
								showInformationMessage((cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_ERROR_USED_DATA));
							}

						}
					}

				});
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		final Comment edit = comment;

		editcomment.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				editado = true;
				idLabel.setText(edit.getId().toString());
				userLabelList.setVisible(true);
				dateLabelList.setVisible(true);

				FormDatas.attach(commentComposite).atTop(1).atLeft(10).atRight(10).atBottom(5);
				FormDatas.attach(cancel).atTopTo(commentText).atRight(1).atBottom(-1);
				FormDatas.attach(addcomment).atTopTo(commentText).atRightTo(cancel, 2).atBottom(-1);
				FormDatas.attach(commentText).atTop(1).atLeft(1).atRight(1).atBottomTo(cancel, 1).withHeight(50);

				refresh();

				createComment.setVisible(false);
				noCommentsLb.setVisible(false);
				commentComposite.setVisible(true);

				commentText.setText(edit.getDescription().replaceAll(" +", " ").trim());
				commentText.setVisible(true);
				commentText.setEditable(true);

				cancel.setVisible(true);
				addcomment.setVisible(true);

				((AllManagementOpacViewController) controller).upComment(edit);
				refresh();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		l10n();
		refresh();
		return listComposite;
	}

	private void printTags() {

		cleanTags();

		try {

			recordControlNumber = (record.getControlNumber());
			tags = ((AllManagementOpacViewController) controller).findTagsByRegister(recordControlNumber, record.getDataBaseName(), record.getLibrary().getLibraryID());

		} catch (Exception e) {
			e.printStackTrace();
		}

		topicTag = new Label(tagsCompo, SWT.NORMAL);
		topicTag.setFont(new Font(tagsCompo.getDisplay(), "Arial", 12, SWT.NONE));

		FormDatas.attach(topicTag).atTop().atLeft();

		if (!tags.isEmpty()) {

			topicTag.setText(MessageUtil.unescape((AbosMessages.get().TAGS)));

			firstTagLk = new Label(tagsCompo, SWT.NORMAL);
			firstTagLk.setFont(new Font(tagsCompo.getDisplay(), "Arial", 12, SWT.NONE));

			FormDatas.attach(firstTagLk).atTop().atLeftTo(topicTag, 7);

			lastLeftTagLink = firstTagLk;

			tagTopLk = new Label(tagsCompo, SWT.NORMAL);
			FormDatas.attach(tagTopLk).atBottomTo(topicTag, -1);

			for (int i = 1; i <= tags.size(); i++) {

				tagLk = new Label(tagsCompo, SWT.NORMAL);
				tagLk.setText(tags.get(i - 1).getTagName());
				tagLk.setFont(new Font(result.getDisplay(), "Arial", 11, SWT.BOLD));
				FormDatas.attach(tagLk).atTopTo(tagTopLk).atLeftTo(lastLeftTagLink, 7);

				lastLeftTagLink = tagLk;

				tagLk.addListener(SWT.Selection, new CreateFindByAutor());

				if ((i % rowCount) == 0) {
					tagTopLk = tagLk;
					lastLeftTagLink = firstTagLk;
				}
			}

		} else {

			topicTag.setText(MessageUtil.unescape((AbosMessages.get().NO_TAGS_TITLE)));
		}

		if (user != null) {

			searchImage = new Image(result.getDisplay(), RWT.getResourceManager().getRegisteredContent("plus"));

			addNewTagBtn = new Button(tagsCompo, SWT.PUSH);
			addNewTagBtn.setImage(searchImage);
			addNewTagBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
			addNewTagBtn.setText(MessageUtil.unescape((AbosMessages.get().ADD_TAGS)));
			addNewTagBtn.setFont(new Font(result.getDisplay(), "Arial", 11, SWT.BOLD));

			if (tags.isEmpty())
				FormDatas.attach(addNewTagBtn).atTopTo(tagTopLk, -8).atLeftTo(topicTag, 5);
			else
				FormDatas.attach(addNewTagBtn).atTopTo(tagTopLk, -8).atLeftTo(lastLeftTagLink, 5);

			addNewTagBtn.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent arg0) {

					addNewTagBtn.setVisible(false);
					addTagsCompo.setVisible(true);
					newTagtext.setFocus();
					refresh();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});

			refresh();
		}

		refresh();

	}

	private void cleanTags() {
		try {
			Control[] temp = tagsCompo.getChildren();

			for (int i = 0; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public String getID() {
		return "ViewLogID";
	}

	@Override
	public void l10n() {

		try {
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
		} catch (Exception e) {
		}

		if (user != null) {
			createComment.setText(MessageUtil.unescape((AbosMessages.get().ADD_COMMENT)));
			createComment.setVisible(true);
			logginOffTag.setVisible(false);
		} else {
			createComment.setVisible(false);
			logginOffTag.setVisible(true);

			try {
				singIn.setVisible(true);
			} catch (Exception e) {
			}
		}

		normalView.setText(MessageUtil.unescape((AbosMessages.get().TAG_NORMAL_VIEW)));
		isbdView.setText(MessageUtil.unescape((AbosMessages.get().TAG_ISBD_VIEW)));
		marcView.setText(MessageUtil.unescape((AbosMessages.get().TAG_MARC_VIEW)));
		state.setText(MessageUtil.unescape((AbosMessages.get().NORMAL_VIEW)));
		by.setText(MessageUtil.unescape((AbosMessages.get().BY)));
		publicationLabel.setText(MessageUtil.unescape((AbosMessages.get().PUBLICATION)));
		topicsLabel.setText(MessageUtil.unescape((AbosMessages.get().SUBJECT)));
		dateLabel.setText(MessageUtil.unescape((AbosMessages.get().YEAR)));
		tabItemExistences.setText((MessageUtil.unescape(AbosMessages.get().AVAILABILITY)));
		tabItemDescription.setText(MessageUtil.unescape((AbosMessages.get().SUMMARY)));
		tabItemComment.setText(MessageUtil.unescape((AbosMessages.get().COMMENTS)));

		if (existencesTable != null)
			existencesTable.setColumnHeaders(Arrays.asList(MessageUtil.unescape(AbosMessages.get().LABEL_KIND_ITEM), MessageUtil.unescape(AbosMessages.get().LABEL_LOCATION), MessageUtil.unescape(AbosMessages.get().LABEL_INVENTORY_NUMBER), MessageUtil.unescape(AbosMessages.get().LABEL_STATUS)));

		addcomment.setText(MessageUtil.unescape((AbosMessages.get().ACCEPT)));
		cancel.setText(MessageUtil.unescape((AbosMessages.get().CANCEL)));
		noCommentsLb.setText(MessageUtil.unescape((AbosMessages.get().NO_COMMENTS)));
		addCommentLb.setText(MessageUtil.unescape(AbosMessages.get().LOGIN_COMMENTS));

		if (singIn != null && !singIn.isDisposed())
			singIn.setText("<a>" + MessageUtil.unescape(AbosMessages.get().HEADER_LOGIN_TEXT) + "</a>");

		refresh();
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_VIEW_LOG);
	}

	private void findByControlNumber() {

		try {
			comments = ((AllManagementOpacViewController) controller).findAllCommentsByMaterial(record.getControlNumber(), record.getDataBaseName(), record.getLibrary().getIsisDefHome());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
