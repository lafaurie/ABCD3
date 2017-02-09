package cu.uci.abcd.opac.ui.contribution;

import java.util.Date;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;

import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.opac.Rating;
import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.l10n.AbosMessages;
import cu.uci.abcd.opac.ui.OpacContributorServiceImpl;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.util.ServiceProvider;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abcd.opac.ui.SecurityUtils;

public class RegisterScore extends ContributorPage {

	private ServiceProvider service;
	private User user;
	private RecordIsis record;
	private float ponderation;

	private Composite result;
	private Group scoreGroup;

	private String title = "";
	private String author = "";

	private Label star;
	private Label bound;
	private boolean second = false;

	private String srcStar = RWT.getResourceManager().getLocation("star");
	private String srcGrayStar = RWT.getResourceManager().getLocation("gray-star");

	private Label lastStar;

	private Label sub;
	private Link titleContent;
	private Label autorLabel;
	private Label autorContent;
	private Label criteriaLabel;
	private Button save;

	public RegisterScore(ServiceProvider service) {
		this.service = service;
	}

	public void setRecord(RecordIsis record) {
		this.record = record;
	}

	public void setPonderation(float ponderation) {
		this.ponderation = ponderation;
	}

	public void update() {
		result.layout(true, true);
		result.redraw();
		result.update();
	}

	@Override
	public Control createUIControl(final Composite parent) {

		final ContributorService pageService = service.get(ContributorService.class);
		final MainContent mainContent = (MainContent) ((OpacContributorServiceImpl) pageService).getContributorMap().get("MainContentID");

		try {
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
		} catch (Exception e) {
			e.printStackTrace();

		}

		title = record.getTitle();
		try {
			author = record.getAuthor();

		} catch (Exception e) {
			author = "";
		}

		result = parent;

		sub = new Label(result, 0);
		sub.setFont(new Font(parent.getDisplay(), "Arial", 16, SWT.BOLD));
		FormDatas.attach(sub).atTop(15).atLeft(30);

		Link horSeparator = new Link(result, SWT.NORMAL);
		horSeparator
				.setText("______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________");
		horSeparator.setFont(new Font(parent.getDisplay(), "Arial", 4, SWT.NONE));
		FormDatas.attach(horSeparator).atTopTo(sub, -7).atLeft(28);

		titleContent = new Link(result, SWT.WRAP);
		titleContent.setFont(new Font(parent.getDisplay(), "Arial", 14, SWT.BOLD));
		FormDatas.attach(titleContent).atTopTo(sub, 10).atLeft(40).atRight();

		autorLabel = new Label(result, SWT.NORMAL);
		autorLabel.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.None));
		FormDatas.attach(autorLabel).atTopTo(titleContent, 5).atLeft(40);

		autorContent = new Label(result, SWT.NORMAL);
		autorContent.setFont(new Font(parent.getDisplay(), "Arial", 13, SWT.BOLD));
		FormDatas.attach(autorContent).atTopTo(titleContent, 5).atLeftTo(autorLabel, 5);

		scoreGroup = new Group(result, 0);
		scoreGroup.setBackground(result.getBackground());
		scoreGroup.setLayout(new FormLayout());
		FormDatas.attach(scoreGroup).atTopTo(autorLabel, 30).atLeft(100).atRight(200);

		bound = new Label(scoreGroup, SWT.NORMAL);
		FormDatas.attach(bound).atLeft(160);

		criteriaLabel = new Label(scoreGroup, SWT.NORMAL);
		criteriaLabel.setFont(new Font(parent.getDisplay(), "Arial", 12, SWT.BOLD));
		FormDatas.attach(criteriaLabel).atTop(20).atRightTo(bound);

		save = new Button(scoreGroup, 0);
		FormDatas.attach(save).atTopTo(criteriaLabel, 15).atLeftTo(bound, 5);

		DrawStar();

		save.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {

					Rating rating;

					rating = ((AllManagementOpacViewController) controller).findRatingByControlNumAndUser(record.getControlNumber(), record.getDataBaseName(), record.getLibrary().getLibraryID(), user.getUserID());

					if (rating != null) {

						rating.setRating(ponderation);
						((AllManagementOpacViewController) controller).addRating(rating, user.getLibrary());

					} else {
						rating = new Rating();
						rating.setDuser(user);
						rating.setLibrary(user.getLibrary());
						rating.setRating(ponderation);
						rating.setMaterial(record.getControlNumber());
						rating.setDatabasename(record.getDataBaseName());

						rating.setActionDate(new java.sql.Date(new Date().getTime()));

						((AllManagementOpacViewController) controller).addRating(rating, user.getLibrary());
					}
					mainContent.createPaged();

					RegisterScore.this.notifyListeners(SWT.Dispose, new Event());

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		l10n();
		return parent;
	}

	private void DrawStar() {

		lastStar = bound;

		if (second) {
			Control[] tempScore = scoreGroup.getChildren();
			for (int j = 3; j <= 7; j++)
				tempScore[j].dispose();

			second = false;
		}

		for (int i = 1; i <= 5; i++) {

			star = new Label(scoreGroup, SWT.NONE);
			star.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
			star.setToolTipText("" + i);
			FormDatas.attach(star).atTop(17).atLeftTo(lastStar);

			if (ponderation >= i)
				star.setText("<img  width='" + 20 + "' height='" + 20 + "' src='" + srcStar + "'></img> ");
			else
				star.setText("<img  width='" + 20 + "' height='" + 20 + "' src='" + srcGrayStar + "'></img> ");

			star.addMouseListener(new MouseListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void mouseUp(MouseEvent event) {

					ponderation = Integer.parseInt(((Label) (event.widget)).getToolTipText());
					second = true;
					DrawStar();
				}

				@Override
				public void mouseDown(MouseEvent arg0) {
				}

				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
				}
			});

			lastStar = star;

		}

		update();
	}

	@Override
	public String getID() {
		return "RegisterScoreID";
	}

	@Override
	public void l10n() {
		sub.setText(MessageUtil.unescape(AbosMessages.get().TASE_BIBLIOGRAPHIC_RECORD));
		titleContent.setText(title);
		autorLabel.setText(MessageUtil.unescape(AbosMessages.get().AUTHOR));
		autorContent.setText(author);
		scoreGroup.setText(MessageUtil.unescape(AbosMessages.get().TASE));
		criteriaLabel.setText(MessageUtil.unescape(AbosMessages.get().CRITERIA));
		save.setText(MessageUtil.unescape(AbosMessages.get().SAVE));

		refresh();

	}

	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().UI_REGISTER_SCORE);
	}

}