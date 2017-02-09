package cu.uci.abcd.opac.ui.auxiliary;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;

import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.contribution.MainContent;
import cu.uci.abcd.opac.ui.controller.ConsultMaterialsController;
import cu.uci.abcd.opac.ui.listener.CirculationDataListener;
import cu.uci.abcd.opac.ui.listener.CreateFindByAutor;
import cu.uci.abcd.opac.ui.listener.CreateRatingListener;
import cu.uci.abcd.opac.ui.listener.CreateRecommendListener;
import cu.uci.abcd.opac.ui.listener.CreateSelectionDataListener;
import cu.uci.abcd.opac.ui.listener.SaveInSelectionListListener;
import cu.uci.abcd.opac.ui.listener.ViewLogDataListener;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.ReportType;
import cu.uci.abos.core.util.URLUtil;

public class ResultComponent extends Composite {

	private static final long serialVersionUID = 1L;

	private ServiceProvider serviceProvider;
	private Label bookNameLb;
	private Label lastLabel;
	private Link bookNameLk;
	private Label bookAutorLb;
	private Label publicationLb;
	private Label publicationContent;
	private Label publicationDateLb;
	private Label publicationDateContent;
	private Label disponibilityLb;
	private Link disponibilityContent;
	private Label actionsLb;

	private Image imageIcon;
   
	private Button reserve;
	private Button addToList;
	private Button addToSelection;
	private Button materialRecommend;
	private Button materialScore;
	private Image actionImg;

	private String tittle;
	private String author;

	Button lastButton;

	Label bookAutorContent;

	public ResultComponent(ViewController controller, Long libraryId, Composite parent, int style, final int resultNum, final RecordIsis record, boolean selection, final ServiceProvider serviceProvider, boolean login) {
		super(parent, style);

		this.serviceProvider = serviceProvider;

		final ContributorService pageService = this.serviceProvider.get(ContributorService.class);

		final MainContent mainContent = (MainContent) ((OpacContributorServiceImpl) pageService).getContributorMap().get("MainContentID");

		final Composite resultList = new Composite(this, 0);

		this.setLayout(new FormLayout());

		resultList.setLayout(new FormLayout());

		FormDatas.attach(resultList).atTop().atRight().atLeft();

		this.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");

		try {

			switch (record.getRecordType()) {
			case "Libro":
				imageIcon = new Image(resultList.getDisplay(), RWT.getResourceManager().getRegisteredContent("opac-book"));
				break;
			case "Tesis":
				imageIcon = new Image(resultList.getDisplay(), RWT.getResourceManager().getRegisteredContent("opac-tesis"));
			case "Revista":
				imageIcon = new Image(resultList.getDisplay(), RWT.getResourceManager().getRegisteredContent("opac-newspaper"));
				break;
			case "Obra de referencia":
				imageIcon = new Image(resultList.getDisplay(), RWT.getResourceManager().getRegisteredContent("opac-reference-works"));
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			record.setRating(((ConsultMaterialsController) controller).ratingByUser(libraryId, record.getControlNumber(), record.getDataBaseName()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		final Button selectionCheck = new Button(resultList, SWT.CHECK);
		FormDatas.attach(selectionCheck).atTop(0).atLeft(0);
		selectionCheck.setSelection(selection);

		final Label number = new Label(resultList, SWT.NORMAL);
		number.setText(resultNum + ".");
		FormDatas.attach(number).atTop(0).atLeftTo(selectionCheck, 5);

		try {
			if (imageIcon != null) {
				Label registerIcon = new Label(resultList, SWT.NORMAL);
				registerIcon.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
				registerIcon.setImage(imageIcon);
				FormDatas.attach(registerIcon).atTopTo(selectionCheck, 10).atLeft(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		bookNameLb = new Label(resultList, SWT.NORMAL);
		bookNameLb.setText(MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_TITLE + ":"));
		bookNameLb.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.BOLD));
		FormDatas.attach(bookNameLb).atTop(0).atLeftTo(number, 10);

		if (record.getTitle().length() > 60) {
			tittle = record.getTitle().substring(0, 60);

			for (int i = tittle.length() - 1; tittle.length() > 1; i--)
				if (tittle.substring(i) != "" || tittle.substring(i) != " ") {
					tittle = tittle + "(...)";
					break;
				}
		} else
			tittle = record.getTitle();

		bookNameLk = new Link(resultList, SWT.WRAP);
		bookNameLk.setText("<a>" + tittle + "</a>");
		bookNameLk.setToolTipText("Ver detalles para este título");
		bookNameLk.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(bookNameLk).atTop(-3).atLeftTo(bookNameLb, 5).atRight(100);

		bookAutorLb = new Label(resultList, SWT.NORMAL);
		bookAutorLb.setText(MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_AUTHOR + ":"));
		bookAutorLb.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.BOLD));

		if (record.getTitle().equals(""))
			FormDatas.attach(bookAutorLb).atTopTo(bookNameLb).atLeftTo(number, 10);
		else
			FormDatas.attach(bookAutorLb).atTopTo(bookNameLk).atLeftTo(number, 10);

		bookAutorContent = new Label(resultList, SWT.NORMAL);

		if (record.getAuthor().length() > 60) {
			author = record.getAuthor().substring(0, 60);
			try {

				for (int i = author.length() - 1; author.length() > 1; i--)
					if (author.substring(i) != "" || author.substring(i) != " ") {
						author = author + "(...)";
						break;
					}

			} catch (Exception e) {				
			}
		} else
			author = record.getAuthor();

		try {
   
			bookAutorContent.setText(author);

			bookAutorContent.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.BOLD));

			if (record.getAuthor().equals(""))
				FormDatas.attach(bookAutorContent).atTopTo(bookNameLb).atLeftTo(bookAutorLb, 5);
			else
				FormDatas.attach(bookAutorContent).atTopTo(bookNameLk).atLeftTo(bookAutorLb, 5);

		} catch (Exception e) {
		}

		if (record.getTitle().equals(null) || record.getTitle().equals(""))
			FormDatas.attach(bookAutorContent).atTopTo(bookNameLb, 2).atLeftTo(bookAutorLb, 5);

		bookAutorContent.addListener(SWT.Selection, new CreateFindByAutor());

		publicationLb = new Label(resultList, SWT.NORMAL);
		publicationLb.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PUBLICATION_PLACE + ":"));
		publicationLb.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.BOLD));
		FormDatas.attach(publicationLb).atTopTo(bookAutorLb).atLeftTo(number, 10);

		publicationContent = new Label(resultList, SWT.NORMAL);
		publicationContent.setText(record.getPublication());
		publicationContent.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.NORMAL));
		FormDatas.attach(publicationContent).atTopTo(bookAutorLb).atLeftTo(publicationLb, 5);

		publicationDateLb = new Label(resultList, SWT.NORMAL);
		publicationDateLb.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PUBLICATION_DATE + ":"));
		publicationDateLb.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.BOLD));
		FormDatas.attach(publicationDateLb).atTopTo(publicationLb).atLeftTo(number, 10);

		publicationDateContent = new Label(resultList, SWT.NORMAL);
		publicationDateContent.setText(String.valueOf(record.getPublicationDate()));
		publicationDateContent.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.NORMAL));
		FormDatas.attach(publicationDateContent).atTopTo(publicationLb).atLeftTo(publicationDateLb, 5);

		lastLabel = publicationDateLb;

		if (record.getUrl() != null & record.getUrl() != "") {

			disponibilityLb = new Label(resultList, SWT.NORMAL);
			disponibilityLb.setText(MessageUtil.unescape(AbosMessages.get().LABEL_RESULT_COMPONENT_LOCATION + ":"));
			disponibilityLb.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.BOLD));
			FormDatas.attach(disponibilityLb).atTopTo(publicationDateLb).atLeftTo(number, 10);

			disponibilityContent = new Link(resultList, SWT.WRAP);
			disponibilityContent.setText("<a>" + record.getUrl() + "</a>");
			disponibilityContent.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.BOLD));
			FormDatas.attach(disponibilityContent).atTopTo(publicationDateContent, -2).atLeftTo(disponibilityLb, 5);

			disponibilityContent.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					URLUtil.download(record.getUrl(), record.getFileName() + ".pdf", ReportType.PDF);

				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});

			lastLabel = disponibilityLb;

		}

		actionsLb = new Label(resultList, SWT.NORMAL);
		actionsLb.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ACTIONS + ":"));
		actionsLb.setFont(new Font(parent.getDisplay(), "Arial", 11, SWT.BOLD));
		FormDatas.attach(actionsLb).atTopTo(lastLabel).atLeftTo(number, 10);

		// ** .. Buttons .. ** \\

		actionImg = new Image(resultList.getDisplay(), RWT.getResourceManager().getRegisteredContent("opac-reserved"));
		reserve = new Button(resultList, 0);
		reserve.setImage(actionImg);
		reserve.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		reserve.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CIRCULATION_RESERVE));
		FormDatas.attach(reserve).atTopTo(actionsLb, -5).atLeftTo(number);

		reserve.addListener(SWT.Selection, new CirculationDataListener(serviceProvider, record, mainContent));

		lastButton = reserve;

		if (login) {

			actionImg = new Image(resultList.getDisplay(), RWT.getResourceManager().getRegisteredContent("list-alt"));
			addToList = new Button(resultList, 0);
			addToList.setImage(actionImg);
			addToList.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
			addToList.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SAVE_TO_LIST));
			FormDatas.attach(addToList).atTopTo(actionsLb, -5).atLeftTo(reserve, 1);

			addToList.addListener(SWT.Selection, new SaveInSelectionListListener(serviceProvider, record));

			lastButton = addToList;
		}

		actionImg = new Image(resultList.getDisplay(), RWT.getResourceManager().getRegisteredContent("opac-add-selection"));
		addToSelection = new Button(resultList, 0);
		addToSelection.setImage(actionImg);
		addToSelection.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		addToSelection.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ADD_TO_SELECTION));
		FormDatas.attach(addToSelection).atTopTo(actionsLb, -5).atLeftTo(lastButton, 1);

		lastButton = addToSelection;

		if (login) {

			actionImg = new Image(resultList.getDisplay(), RWT.getResourceManager().getRegisteredContent("opac-recommend"));
			materialRecommend = new Button(resultList, 0);
			materialRecommend.setImage(actionImg);
			materialRecommend.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
			materialRecommend.setText(MessageUtil.unescape(AbosMessages.get().RECOMMEND));
			FormDatas.attach(materialRecommend).atTopTo(actionsLb, -5).atLeftTo(lastButton, 1);

			materialRecommend.addListener(SWT.Selection, new CreateRecommendListener(serviceProvider, record));

			actionImg = new Image(resultList.getDisplay(), RWT.getResourceManager().getRegisteredContent("opac-rating"));
			materialScore = new Button(resultList, 0);
			materialScore.setImage(actionImg);
			materialScore.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
			materialScore.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_RATE));
			FormDatas.attach(materialScore).atTopTo(actionsLb, -5).atLeftTo(materialRecommend, 1);
			materialScore.addListener(SWT.Selection, new CreateRatingListener(serviceProvider, record, record.getRating()));

			lastButton = materialScore;
		}

		String srcStar = RWT.getResourceManager().getLocation("star");
		String srcGrayStar = RWT.getResourceManager().getLocation("gray-star");

		Label star = new Label(resultList, SWT.NONE);
		star.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		star.setText("<img  width='" + 20 + "' height='" + 20 + "' src='" + srcStar + "'></img> ");
		FormDatas.attach(star).atTop(5).atRight(100);
		star.setVisible(false);

		Label lastStar = star;

		for (int i = 1; i <= 5; i++) {

			star = new Label(resultList, SWT.NONE);
			star.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
			FormDatas.attach(star).atTop(5).atLeftTo(lastStar);
			lastStar = star;

			if (record.getRating() >= i)
				star.setText("<img  width='" + 20 + "' height='" + 20 + "' src='" + srcStar + "'></img> ");
			else
				star.setText("<img  width='" + 20 + "' height='" + 20 + "' src='" + srcGrayStar + "'></img> ");
		}

		Label separador = new Label(resultList, 0);
		separador.setText("______________________________________________________________________________________________________________________________________" 
		                + "______________________________________________________________________________________________________________________________________"
			          	+ "______________________________________________________________________________________________________________________________________");
		separador.setFont(new Font(parent.getDisplay(), "Arial", 5, SWT.NONE));
		FormDatas.attach(separador).atTopTo(reserve, -5).atLeft(0);

		bookNameLk.addListener(SWT.Selection, new ViewLogDataListener(serviceProvider, record));

		addToSelection.addListener(SWT.Selection, new CreateSelectionDataListener(serviceProvider, record));

		selectionCheck.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (selectionCheck.getSelection())
					mainContent.tempRecord.add(record);
				else
					mainContent.tempRecord.remove(record);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

	}
}
