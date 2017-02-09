package cu.uci.abos.core.widget.repeatable.field.util;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import cu.uci.abos.core.l10n.AbosMessages;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundlabel.CompoundLabel;
import cu.uci.abos.core.widget.compoundlabel.CompoundLabelType;

public class AttachDigitalFileComposite extends Composite {

	public AttachDigitalFileComposite(Composite parent, int style, CTabFolder tabFolder) {
		super(parent, style);

		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");

		Composite father = new Composite(parent, 0);
		Composite radioGroup = new Composite(father, SWT.CENTER);

		father.setLayout(new FormLayout());
		parent.setLayout(new FormLayout());
		radioGroup.setLayout(new FormLayout());

		father.setData(RWT.CUSTOM_VARIANT, "gray_background");
		radioGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");

		FormDatas.attach(father).atLeft(0).atRight(0);

		Button temporaryRepositoryRadio = new Button(radioGroup, SWT.RADIO);
		temporaryRepositoryRadio.setText(MessageUtil.unescape(AbosMessages.get().RADIO_TEMPORARY_REPOSITORY));

		FormDatas.attach(temporaryRepositoryRadio).atTopTo(radioGroup, 10);

		Button localRadio = new Button(radioGroup, SWT.RADIO);
		localRadio.setText(AbosMessages.get().RADIO_LOCAL);

		FormDatas.attach(localRadio).atTopTo(radioGroup, 10).atLeftTo(temporaryRepositoryRadio, 10);

		Group fileDataGroup = new Group(father, SWT.NONE);
		fileDataGroup.setText(AbosMessages.get().GROUP_FILE_DATA);
		fileDataGroup.setLayout(new FormLayout());

		FormDatas.attach(fileDataGroup).atTopTo(radioGroup, 10).atLeftTo(parent, 120);

		CompoundLabel titleCompound = new CompoundLabel(fileDataGroup, 0, CompoundLabelType.TEXT);
		titleCompound.setText(MessageUtil.unescape(AbosMessages.get().LABEL_TITLE_PTS));

		CompoundLabel authorCompound = new CompoundLabel(fileDataGroup, 0, CompoundLabelType.TEXT);
		authorCompound.setText(AbosMessages.get().LABEL_AUTHOR_PTS);

		CompoundLabel fileNameCompound = new CompoundLabel(fileDataGroup, 0, CompoundLabelType.TEXT);
		fileNameCompound.setText(AbosMessages.get().LABEL_FILE_NAME);

		int moreBig = fileNameCompound.getLabel().getSize().x;

		FormDatas.attach(titleCompound).atTopTo(fileDataGroup, 10);
		FormDatas.attach(authorCompound).atTopTo(titleCompound, 15);
		FormDatas.attach(fileNameCompound).atTopTo(authorCompound, 15);

		fileNameCompound.setPosition(SWT.LEFT, 10, 170);
		authorCompound.setPosition(SWT.LEFT, moreBig - authorCompound.getLabel().getSize().x + 10, 170);
		titleCompound.setPosition(SWT.LEFT, moreBig - titleCompound.getLabel().getSize().x + 10, 170);

		fileDataGroup.layout(true, true);
		fileDataGroup.getShell().redraw();
		fileDataGroup.getShell().update();

		Composite buttonComposite = new Composite(father, SWT.NONE);
		buttonComposite.setLayout(new FormLayout());

		buttonComposite.setData(RWT.CUSTOM_VARIANT, "gray_background");

		FormDatas.attach(buttonComposite).atTopTo(fileDataGroup, 10).atLeftTo(parent, 120);

		Button searchButton = new Button(buttonComposite, SWT.PUSH);
		searchButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_SEARCH));

		FormDatas.attach(searchButton).atTopTo(buttonComposite, 5).withHeight(25);

		Button cancelButton = new Button(buttonComposite, SWT.PUSH);
		cancelButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_CANCEL));

		FormDatas.attach(cancelButton).atTopTo(buttonComposite, 5).atLeftTo(searchButton, 5).withHeight(25);

		Group proposedFileGroup = new Group(father, SWT.NONE);
		proposedFileGroup.setText(MessageUtil.unescape(AbosMessages.get().GROUP_PROPOSED_FILE));
		proposedFileGroup.setLayout(new FormLayout());

		FormDatas.attach(proposedFileGroup).atTopTo(buttonComposite, 10).atLeftTo(parent, 120);

		Button attachButton = new Button(proposedFileGroup, SWT.PUSH);
		attachButton.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ATTACH));

		FormDatas.attach(attachButton).atTopTo(proposedFileGroup, 5).withHeight(25);

		Link checkAllLink = new Link(proposedFileGroup, SWT.END);
		checkAllLink.setText(MessageUtil.unescape(AbosMessages.get().LINK_CHECK_ALL));

		FormDatas.attach(checkAllLink).atTopTo(proposedFileGroup, 10).atLeftTo(attachButton, 10);

		Link uncheckAllLink = new Link(proposedFileGroup, SWT.BEGINNING);
		uncheckAllLink.setText(MessageUtil.unescape(AbosMessages.get().LINK_UNCHECK_ALL));

		FormDatas.attach(uncheckAllLink).atTopTo(proposedFileGroup, 10).atLeftTo(checkAllLink, 10);

		Table table = new Table(proposedFileGroup, SWT.BORDER | SWT.FULL_SELECTION);

		table.setTouchEnabled(true);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tableColum1 = new TableColumn(table, SWT.CENTER);
		tableColum1.setWidth(150);
		tableColum1.setText(MessageUtil.unescape(AbosMessages.get().TABLE_TITLE));

		TableColumn tableColum2 = new TableColumn(table, SWT.CENTER);
		tableColum2.setWidth(150);
		tableColum2.setText(MessageUtil.unescape(AbosMessages.get().TABLE_AUTHOR));

		TableColumn tableColum3 = new TableColumn(table, SWT.CENTER);
		tableColum3.setWidth(150);
		tableColum3.setText(MessageUtil.unescape(AbosMessages.get().TABLE_FILE));

		TableColumn tableColum4 = new TableColumn(table, SWT.CENTER);
		tableColum4.setText(MessageUtil.unescape(AbosMessages.get().TABLE_USER));
		tableColum4.setWidth(150);

		TableColumn tableColum5 = new TableColumn(table, SWT.CENTER);
		tableColum5.setText(MessageUtil.unescape(AbosMessages.get().TABLE_SELECT));
		tableColum5.setWidth(150);

		TableItem tableItem = new TableItem(table, SWT.NONE);

		TableEditor tableEditor = new TableEditor(table);
		tableEditor.grabHorizontal = true;

		Button checkButton = new Button(table, SWT.CHECK | SWT.CENTER);

		tableEditor.setEditor(checkButton, tableItem, 4);

		FormDatas.attach(table).atTopTo(attachButton, 10);

		parent.layout(true);

	}
	private static final long serialVersionUID = 1L;

}
