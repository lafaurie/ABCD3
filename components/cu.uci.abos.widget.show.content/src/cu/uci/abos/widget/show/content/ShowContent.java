package cu.uci.abos.widget.show.content;

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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.unesco.jisis.corelib.record.Record;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.widget.show.content.domain.Constants;
import cu.uci.abos.widget.show.content.domain.ShowContentEvent;
import cu.uci.abos.widget.show.content.domain.StandarRecordIsis;
import cu.uci.abos.widget.show.content.l10n.AbosMessages;

/**
 * 
 * @author Alberto Arias
 * 
 */
public class ShowContent extends Composite {

	public ShowContentPaginator showContentPaginator;

	private Composite content;
	private Composite currentRow;

	private Label bookNameLb;
	private Link bookNameLk;
	private Label bookAutorLb;
	private Label publicationLb;
	private Label publicationContent;
	private Label publicationDateLb;
	private Label publicationDateContent;
	private Label actionsLb;

	private List<Image> imageBtnsList;
	private List<String> textBtnsList;

	private int until;
	private int to;

	private Button btn;
	private Button lastButton;

	Label bookAutorContent;

	public ShowContent(Composite parent, int style, List<Record> records) {
		super(parent, style);

		Composite father = new Composite(this, 0);

		content = new Composite(father, 0);

		this.setLayout(new FormLayout());

		father.setLayout(new FormLayout());

		content.setLayout(new FormLayout());

		showContentPaginator = new ShowContentPaginator(content, 0);
	}

	public void makeContent(final Composite parent, final List<Record> records, String dataBaseName) {

		try {
			Control[] temp = content.getChildren();
			for (int i = 1; i < temp.length; i++)
				temp[i].dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Composite before = new Composite(content, 0);
		FormDatas.attach(before).atLeft().atTop().atRight();
         
		until = (showContentPaginator.getCurrentPage() - 1) * showContentPaginator.getPageSize();

		if (!showContentPaginator.isLast())
			to = until + showContentPaginator.getPageSize();
		else
			to = showContentPaginator.getTotalElements();
         
		for (int i = until; i < to; i++) {

			currentRow = row(content, i + 1, new StandarRecordIsis(records.get(i), "marc21"), dataBaseName);
			FormDatas.attach(currentRow).atLeft(20).atRight(0).atTopTo(before, 10);
			before = currentRow;
		}

		FormDatas.attach(showContentPaginator).atLeft(15).atRight(5).atTopTo(before, 10);

		parent.notifyListeners(SWT.Resize, new Event());

	}

	private Composite row(Composite row, int currentNumber, final StandarRecordIsis record, String dataBaseName) {

		Composite content = new Composite(row, 0);
		content.setLayout(new FormLayout());

		final Label number = new Label(content, SWT.NORMAL);
		number.setText(currentNumber + ".");
		FormDatas.attach(number).atTop(0).atLeft(10);

		if (dataBaseName == Constants.MARC21_DATABASE) {

			bookNameLb = new Label(content, SWT.NORMAL);
			bookNameLb.setText(MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_TITLE+ ":"));
			
			bookNameLb.setFont(new Font(content.getDisplay(), "Arial", 11, SWT.BOLD));
			FormDatas.attach(bookNameLb).atTop(1).atLeftTo(number, 10);

			bookNameLk = new Link(content, SWT.WRAP);
			bookNameLk.setText(record.getTitle());
			bookNameLk.setFont(new Font(content.getDisplay(), "Arial", 12, SWT.BOLD));
			FormDatas.attach(bookNameLk).atTop(-3).atLeftTo(bookNameLb, 5).atRight(100);

			bookAutorLb = new Label(content, SWT.NORMAL);
			bookAutorLb.setText(MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_AUTHOR + ":"));
			bookAutorLb.setFont(new Font(content.getDisplay(), "Arial", 11, SWT.BOLD));

			if (record.getTitle().equals(""))
				FormDatas.attach(bookAutorLb).atTopTo(bookNameLb).atLeftTo(number, 10);
			else
				FormDatas.attach(bookAutorLb).atTopTo(bookNameLk).atLeftTo(number, 10);

			bookAutorContent = new Label(content, SWT.NORMAL);

			try {

				bookAutorContent.setText(record.getAuthor().get(0));

				bookAutorContent.setFont(new Font(content.getDisplay(), "Arial", 12, SWT.BOLD));

				if (record.getAuthor().get(0).equals(""))
					FormDatas.attach(bookAutorContent).atTopTo(bookNameLb, -3).atLeftTo(bookAutorLb, 5);
				else
					FormDatas.attach(bookAutorContent).atTopTo(bookNameLk, -3).atLeftTo(bookAutorLb, 5);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (record.getTitle().equals(null) || record.getTitle().equals(""))
				FormDatas.attach(bookAutorContent).atTopTo(bookNameLb).atLeftTo(bookAutorLb, 5);
		

			publicationLb = new Label(content, SWT.NORMAL);
			publicationLb.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PUBLICATION_PLACE + ":"));
			publicationLb.setFont(new Font(content.getDisplay(), "Arial", 11, SWT.BOLD));
			FormDatas.attach(publicationLb).atTopTo(bookAutorLb).atLeftTo(number, 10);

			publicationContent = new Label(content, SWT.NORMAL);
			publicationContent.setText(record.getPublication());
			publicationContent.setFont(new Font(content.getDisplay(), "Arial", 11, SWT.NORMAL));
			FormDatas.attach(publicationContent).atTopTo(bookAutorLb).atLeftTo(publicationLb, 5);

			publicationDateLb = new Label(content, SWT.NORMAL);
			publicationDateLb.setText(MessageUtil.unescape(AbosMessages.get().LABEL_PUBLICATION_DATE + ":"));
			publicationDateLb.setFont(new Font(content.getDisplay(), "Arial", 11, SWT.BOLD));
			FormDatas.attach(publicationDateLb).atTopTo(publicationLb).atLeftTo(number, 10);
    
			publicationDateContent = new Label(content, SWT.NORMAL);
			publicationDateContent.setText(String.valueOf(record.getPublicationDate()));
			publicationDateContent.setFont(new Font(content.getDisplay(), "Arial", 11, SWT.NORMAL));
			FormDatas.attach(publicationDateContent).atTopTo(publicationLb).atLeftTo(publicationDateLb, 5);

		} else if (dataBaseName == Constants.AUTHORITIES_DATABASE) {

			bookAutorLb = new Label(content, SWT.NORMAL);
			bookAutorLb.setText(MessageUtil.unescape(AbosMessages.get().COMBO_FIRST_FILTER_AUTHOR + ":"));
			bookAutorLb.setFont(new Font(content.getDisplay(), "Arial", 11, SWT.BOLD));
    
			FormDatas.attach(bookAutorLb).atTop(1).atLeftTo(number, 10);
    
			bookAutorContent = new Label(content, SWT.NORMAL);

			if (!record.getAuthor().isEmpty())
				bookAutorContent.setText(record.getAuthor().get(0));

			FormDatas.attach(bookAutorContent).atTop(1).atLeftTo(bookAutorLb, 5);

		}    
		     
		if (textBtnsList != null) {

			actionsLb = new Label(content, SWT.NORMAL);
			actionsLb.setText(MessageUtil.unescape(AbosMessages.get().LABEL_ACTIONS + ":"));
			actionsLb.setFont(new Font(content.getDisplay(), "Arial", 11, SWT.BOLD));
			
			if (dataBaseName == Constants.MARC21_DATABASE)
				FormDatas.attach(actionsLb).atTopTo(publicationDateContent).atLeftTo(number, 10);	
			else				
				FormDatas.attach(actionsLb).atTopTo(bookAutorContent).atLeftTo(number, 10);


			btn = new Button(content, 0);
			btn.setImage(imageBtnsList.get(0));
			btn.setText(textBtnsList.get(0));
			btn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
			FormDatas.attach(btn).atTopTo(actionsLb, -5).atLeftTo(number, 10);

			btn.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent event) {

					ShowContentEvent eventData = new ShowContentEvent();
					eventData.standarRecordIsis = record;
					notifyListeners(3, eventData);

				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub

				}
			});

			lastButton = btn;

			for (int i = 1; i < textBtnsList.size(); i++) {

				btn = new Button(content, 0);
				btn.setImage(imageBtnsList.get(i));
				btn.setText(textBtnsList.get(i));
				btn.setData(RWT.CUSTOM_VARIANT, "opacSearchBtn");
				FormDatas.attach(btn).atTopTo(actionsLb, -5).atLeftTo(lastButton, 8);

				btn.addSelectionListener(new SelectionListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void widgetSelected(SelectionEvent event) {

						ShowContentEvent eventData = new ShowContentEvent();
						eventData.standarRecordIsis = record;
						notifyListeners(3, eventData);

					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub

					}
				});

				lastButton = btn;
			}
		}

		Label separador = new Label(content, 0);
		separador.setText("______________________________________________________________________________________________________________________________________" + "__________________________________________________________________________________"
				+ "____________________________________________________________________________________________________________________________________________________"
				+ "____________________________________________________________________________________________________________________________________________________"
				+ "____________________________________________________________________________________________________________________________________________________"
				+ "____________________________________________________________________________________________________________________________________________________");
		separador.setFont(new Font(content.getDisplay(), "Arial", 5, SWT.NONE));

		if (textBtnsList != null)
			FormDatas.attach(separador).atTopTo(actionsLb, 20).atLeft(0).atRight(0);
		else
			FormDatas.attach(separador).atTopTo(publicationDateLb, 10).atLeft(0).atRight(0);

		return content;

	}

	public void createButton(Image imageBtns, String textBtns) {

		if (textBtnsList == null) {
			imageBtnsList = new ArrayList<Image>();
			textBtnsList = new ArrayList<String>();
		}

		imageBtnsList.add(imageBtns);
		textBtnsList.add(textBtns);
	}

	private static final long serialVersionUID = 1L;
}
