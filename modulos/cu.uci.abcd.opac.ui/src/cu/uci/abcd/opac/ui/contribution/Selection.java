package cu.uci.abcd.opac.ui.contribution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
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

import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.auxiliary.Auxiliary;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abcd.opac.ui.listener.ViewLogDataListener;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.AbosImageUtil;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;

public class Selection extends ContributorPage {

	private ServiceProvider serviceProvider;
	private ContributorService pageService;
	private OpacMenuBarProvider opacMenuBarProvider;
	private SendEmail sendEmail;
	private Auxiliary auxiliary;

	private Composite registers;
	public Composite result;
	public Composite row;

	public List<RecordIsis> records = new ArrayList<RecordIsis>();
	private List<RecordIsis> mySelectedRecord = new ArrayList<RecordIsis>();

	private String bookTitle;
	private String bookAuthor;
	private String publication;
	private String recordType;

	private Label txtIconLb;
	private Label pdfIconLb;
	private Label htmlIconLb;

	private Button sendBtn;
	private Button selectionExportBtn;
	private Button deleteElementBtn;
	private Button clearBtn;
	private Button select;

	private Label sub;
	private Link htmlLk;
	private Link pdf;
	private Link plainText;
	private Link headerCopyCant;
	private Link headerType;
	private Link headerName;

	public Selection(ServiceProvider service) {
		this.serviceProvider = service;
	}

	public void update() {
		result.layout(true, true);
		result.redraw();
		result.update();
	}

	@Override
	public Control createUIControl(final Composite parent) {

		pageService = serviceProvider.get(ContributorService.class);

		opacMenuBarProvider = serviceProvider.get(OpacMenuBarProvider.class);
		sendEmail = (SendEmail) ((OpacContributorServiceImpl) pageService).getContributorMap().get("sendEmailID");
		auxiliary = new Auxiliary(controller);

		result = parent;

		sub = new Label(result, 0);
		sub.setFont(new Font(parent.getDisplay(), "Arial", 16, SWT.BOLD));
		FormDatas.attach(sub).atTop(10).atLeft(30);

		Link horSeparator = new Link(result, SWT.NORMAL);
		horSeparator
				.setText("_______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
		horSeparator.setFont(new Font(parent.getDisplay(), "Arial", 4, SWT.NONE));
		FormDatas.attach(horSeparator).atTopTo(sub, -7).atLeft(28);

		sendBtn = new Button(result, SWT.PUSH);
		Image sendImage = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("opac-send"));
		sendBtn.setImage(sendImage);
		sendBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(sendBtn).atTopTo(sub, 10).atLeft(30);

		selectionExportBtn = new Button(result, SWT.PUSH);
		Image selectionExportImg = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("save"));
		selectionExportBtn.setImage(selectionExportImg);
		selectionExportBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(selectionExportBtn).atTopTo(sub, 10).atLeftTo(sendBtn, 5);

		deleteElementBtn = new Button(result, SWT.PUSH);
		Image deleteImg = AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/trash.png", false);
		deleteElementBtn.setImage(deleteImg);
		deleteElementBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(deleteElementBtn).atTopTo(sub, 10).atLeftTo(selectionExportBtn, 5);

		clearBtn = new Button(result, SWT.PUSH);
		Image clearImg = new Image(parent.getDisplay(), RWT.getResourceManager().getRegisteredContent("trash"));
		clearBtn.setImage(clearImg);
		clearBtn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
		FormDatas.attach(clearBtn).atTopTo(sub, 10).atLeftTo(deleteElementBtn, 5);

		final Composite exportToCompo = new Composite(result, SWT.BORDER);
		exportToCompo.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		exportToCompo.setLayout(new FormLayout());
		FormDatas.attach(exportToCompo).atTopTo(sendBtn).atLeft(10).atRight(10);
		exportToCompo.setVisible(false);

		htmlIconLb = new Label(exportToCompo, SWT.NORMAL);
		htmlIconLb.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		String srcHtml = RWT.getResourceManager().getLocation("opac-file-html");
		htmlIconLb.setText("<img  width='" + 16 + "' height='" + 16 + "' src='" + srcHtml + "'></img> ");
		FormDatas.attach(htmlIconLb).atTop(3).atBottom(3).atLeft(300);

		htmlLk = new Link(exportToCompo, 0);
		htmlLk.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.ITALIC | SWT.BOLD));
		FormDatas.attach(htmlLk).atTop(3).atBottom(3).atLeftTo(htmlIconLb);

		txtIconLb = new Label(exportToCompo, SWT.NORMAL);
		txtIconLb.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		String srcTxt = RWT.getResourceManager().getLocation("opac-file-txt");
		txtIconLb.setText("<img  width='" + 16 + "' height='" + 16 + "' src='" + srcTxt + "'></img> ");
		FormDatas.attach(txtIconLb).atTop(3).atBottom(3).atLeftTo(htmlLk, 10);

		plainText = new Link(exportToCompo, 0);
		plainText.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.ITALIC | SWT.BOLD));
		FormDatas.attach(plainText).atTop(3).atBottom(3).atLeftTo(txtIconLb);

		pdfIconLb = new Label(exportToCompo, SWT.NORMAL);
		pdfIconLb.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		String srcPdf = RWT.getResourceManager().getLocation("opac-file-pdf");
		pdfIconLb.setText("<img  width='" + 16 + "' height='" + 16 + "' src='" + srcPdf + "'></img> ");
		FormDatas.attach(pdfIconLb).atTop(3).atBottom(3).atLeftTo(plainText, 10);

		pdf = new Link(exportToCompo, 0);
		pdf.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.ITALIC | SWT.BOLD));
		FormDatas.attach(pdf).atTop(3).atBottom(3).atLeftTo(pdfIconLb);

		registers = new Composite(result, SWT.BORDER);
		registers.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		registers.setLayout(new FormLayout());
		FormDatas.attach(registers).atTopTo(exportToCompo, -2).atLeft(10).atRight(10).atBottom(10);

		// ///// my Selection \\\\\ \\

		final Composite headers = new Composite(registers, SWT.BORDER);
		headers.setData(RWT.CUSTOM_VARIANT, "workspace_content");
		headers.setLayout(new FormLayout());
		FormDatas.attach(headers).atTop(-2).atLeft(-2).atRight(-2).withHeight(20);

		Composite headerCopyCantCompo = new Composite(headers, SWT.BORDER);
		headerCopyCantCompo.setBackground(headers.getBackground());
		headerCopyCantCompo.setLayout(new FormLayout());
		FormDatas.attach(headerCopyCantCompo).atTop(-10).atRight(-20).atBottom(-12).withWidth(200);

		headerCopyCant = new Link(headerCopyCantCompo, 0);
		headerCopyCant.setFont(new Font(parent.getDisplay(), "Arial", 13, SWT.BOLD));
		FormDatas.attach(headerCopyCant).atTop(7).atBottom(5).atLeft(15);

		Composite headerTypeCompo = new Composite(headers, SWT.BORDER);
		headerTypeCompo.setBackground(headers.getBackground());
		headerTypeCompo.setLayout(new FormLayout());
		FormDatas.attach(headerTypeCompo).atTop(-10).atBottom(-12).atRightTo(headerCopyCantCompo, -2).withWidth(100);

		headerType = new Link(headerTypeCompo, 0);
		headerType.setFont(new Font(parent.getDisplay(), "Arial", 13, SWT.BOLD));
		FormDatas.attach(headerType).atTop(7).atBottom(5).atLeft(15);

		Composite headerWhiteSpace = new Composite(headers, SWT.BORDER);
		headerWhiteSpace.setBackground(headers.getBackground());
		headerWhiteSpace.setLayout(new FormLayout());
		FormDatas.attach(headerWhiteSpace).atTop(-10).atBottom(-12).atLeft(-24).withWidth(42);

		select = new Button(headerWhiteSpace, SWT.CHECK);
		FormDatas.attach(select).atTop(5).atLeft(15);

		Composite headerNameCompo = new Composite(headers, SWT.BORDER);
		headerNameCompo.setBackground(headers.getBackground());
		headerNameCompo.setLayout(new FormLayout());
		FormDatas.attach(headerNameCompo).atTop(-10).atBottom(-12).atLeftTo(headerWhiteSpace, -2).atRightTo(headerTypeCompo, -2);

		headerName = new Link(headerNameCompo, 0);
		headerName.setFont(new Font(parent.getDisplay(), "Arial", 13, SWT.BOLD));
		FormDatas.attach(headerName).atTop(7).atBottom(5).atLeft(15);

		// //////////////////////

		Composite last = headers;

		cleanRegisters();

		for (int i = 0; i < records.size(); i++) {
			row = CreateRows(registers, false, records.get(i));
			FormDatas.attach(row).atTopTo(last, -2).atLeft(-2).atRight(-2);

			last = row;
		}

		select.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (select.getSelection()) {

					for (int i = 0; i < records.size(); i++)
						if (!mySelectedRecord.contains(records.get(i)))
							mySelectedRecord.add(records.get(i));

					cleanRegisters();

					Composite last = headers;
					for (int i = 0; i < records.size(); i++) {
						row = CreateRows(registers, true, records.get(i));
						FormDatas.attach(row).atTopTo(last, -2).atLeft(-2).atRight(-2).withHeight(60);

						last = row;
					}
				} else {

					mySelectedRecord = new ArrayList<RecordIsis>();
					cleanRegisters();

					Composite last = headers;
					for (int i = 0; i < records.size(); i++) {
						row = CreateRows(registers, false, records.get(i));
						FormDatas.attach(row).atTopTo(last, -2).atLeft(-2).atRight(-2).withHeight(60);

						last = row;
					}

				}

				update();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		deleteElementBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (!mySelectedRecord.isEmpty()) {

					for (int i = 0; i < mySelectedRecord.size(); i++)
						records.remove(mySelectedRecord.get(i));

					cleanRegisters();

					Composite last = headers;

					for (int i = 0; i < records.size(); i++) {
						row = CreateRows(registers, false, records.get(i));
						FormDatas.attach(row).atTopTo(last, -2).atLeft(-2).atRight(-2).withHeight(60);

						last = row;
					}

					update();

					if (records.isEmpty()) {
						opacMenuBarProvider.cantSelection = "";
						Selection.this.notifyListeners(SWT.Dispose, new Event());
					} else
						opacMenuBarProvider.cantSelection = "" + records.size();
					
					mySelectedRecord.clear();

					showInformationMessage(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_DELETE_ONE_ITEM);
					opacMenuBarProvider.l10n();

				} else
					showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_MUST_SELECT_ONE_ELEMENT));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		clearBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				records.clear();
				mySelectedRecord.clear();

				cleanRegisters();

				opacMenuBarProvider.cantSelection = "";
				opacMenuBarProvider.l10n();

				Selection.this.notifyListeners(SWT.Dispose, new Event());

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		selectionExportBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (exportToCompo.getVisible())
					exportToCompo.setVisible(false);
				else
					exportToCompo.setVisible(true);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		plainText.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (mySelectedRecord.size() != 0)
					auxiliary.exportToPlainText(mySelectedRecord);
				else
					showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_MUST_SELECT_ONE_ELEMENT));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		htmlLk.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (mySelectedRecord.size() != 0)
					auxiliary.exportToHTML(mySelectedRecord);
				else
					showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_MUST_SELECT_ONE_ELEMENT));

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		pdf.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (mySelectedRecord.size() != 0)
					auxiliary.exportToPdf(mySelectedRecord);
				else
					showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_MUST_SELECT_ONE_ELEMENT));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		sendBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (mySelectedRecord.size() != 0) {
					sendEmail.setMySelectedRecord(mySelectedRecord);
					pageService.selectContributor("sendEmailID");
				} else
					showErrorMessage(MessageUtil.unescape(AbosMessages.get().MSG_ERROR_MUST_SELECT_ONE_ELEMENT));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		l10n();
		return result;
	}

	public Composite CreateRows(Composite parent, boolean select, final RecordIsis record) {

		parent = new Composite(parent, SWT.BORDER);
		parent.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		parent.setLayout(new FormLayout());

		bookTitle = record.getTitle();
		try {
			bookAuthor = record.getAuthor();
		} catch (Exception e) {
			bookAuthor = "";
		}

		publication = record.getPublication();
		recordType = record.getRecordType();

		Composite copyCantCompo = new Composite(parent, SWT.BORDER);
		copyCantCompo.setBackground(parent.getBackground());
		copyCantCompo.setLayout(new FormLayout());
		FormDatas.attach(copyCantCompo).atTop(-2).atRight().atBottom(-2).withWidth(200);

		Label copyCantLabel = new Label(copyCantCompo, 0);

		// OJO
		copyCantLabel.setText(((AllManagementOpacViewController) controller).findLoanObjectByControlNumberAndLibrary(record.getControlNumber(), record.getLibrary().getLibraryID()) + " " + MessageUtil.unescape(AbosMessages.get().COPIES));
		FormDatas.attach(copyCantLabel).atTop(25).atBottom(5).atLeft(10);

		Composite typeCompo = new Composite(parent, SWT.BORDER);
		typeCompo.setBackground(parent.getBackground());
		typeCompo.setLayout(new FormLayout());
		FormDatas.attach(typeCompo).atTop(-2).atBottom(-2).atRightTo(copyCantCompo, -2).withWidth(100);

		Label typeLabel = new Label(typeCompo, 0);
		typeLabel.setText(recordType);
		FormDatas.attach(typeLabel).atTop(25).atBottom(5).atLeft(10);

		Composite buttonCompo = new Composite(parent, SWT.BORDER);
		buttonCompo.setBackground(parent.getBackground());
		buttonCompo.setLayout(new FormLayout());
		FormDatas.attach(buttonCompo).atTop(-2).atBottom(-2).atLeft(-2).withWidth(40);

		final Button check = new Button(buttonCompo, SWT.CHECK);
		check.setSelection(select);
		FormDatas.attach(check).atTop(20).atLeft(10);

		Composite nameCompo = new Composite(parent, SWT.BORDER);
		nameCompo.setBackground(parent.getBackground());
		nameCompo.setLayout(new FormLayout());
		FormDatas.attach(nameCompo).atTop(-2).atBottom(-2).atLeftTo(buttonCompo, -2).atRightTo(typeCompo, -2).withWidth(140);

		Link titleLink = new Link(nameCompo, SWT.WRAP);
		titleLink.setText("<a>" + bookTitle + "</a>");
		titleLink.setFont(new Font(parent.getDisplay(), "Arial", 13, SWT.BOLD));
		FormDatas.attach(titleLink).atTop(10).atLeft(15).atRight();

		titleLink.addListener(SWT.Selection, new ViewLogDataListener(serviceProvider, record));

		Label autorLabel = new Label(nameCompo, 0);
		autorLabel.setText(MessageUtil.unescape(AbosMessages.get().BY_SELECTION) + ": " + bookAuthor);
		FormDatas.attach(autorLabel).atTopTo(titleLink).atLeft(15);

		Label others = new Label(nameCompo, 0);
		others.setText(publication);
		FormDatas.attach(others).atTopTo(autorLabel).atLeft(15);

		check.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (check.getSelection())
					mySelectedRecord.add(record);
				else
					mySelectedRecord.remove(record);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		return parent;
	}

	private void cleanRegisters() {
		try {
			Control[] temp = registers.getChildren();
			for (int i = 1; i < temp.length; i++)
				temp[i].dispose();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getID() {
		return "SelectionID";
	}

	@Override
	public void l10n() {
		sub.setText(MessageUtil.unescape((AbosMessages.get().MY_SELECTION)));
		sendBtn.setText(MessageUtil.unescape((AbosMessages.get().BUTTON_SEND)));
		selectionExportBtn.setText(MessageUtil.unescape((AbosMessages.get().EXPORT_SELECTION)));
		deleteElementBtn.setText(MessageUtil.unescape((AbosMessages.get().DELETE_ITEM)));
		clearBtn.setText(MessageUtil.unescape((AbosMessages.get().BUTTON_EMPTY_LIST)));
		htmlLk.setText("<a>HTML</a>");
		plainText.setText("<a>" + MessageUtil.unescape((AbosMessages.get().PLAIN_TEXT + "</a>")));
		pdf.setText("<a>PDF</a>");
		headerCopyCant.setText(MessageUtil.unescape(AbosMessages.get().COPIES_NUMBER));
		headerType.setText(MessageUtil.unescape(AbosMessages.get().KIND));
		headerName.setText(MessageUtil.unescape(AbosMessages.get().ITEM_NAME));
	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_SELECTION);
	}

}
