/*
 * @(#)ContributorServiceImpl.java 1.0.0 26/06/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abcd.opac.ui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.opac.ui.listener.TabItemResizeListener;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ContributorTracker;
import cu.uci.abos.api.ui.LayoutContext;
import cu.uci.abos.api.ui.LayoutProvider;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.MessageDialogUtil;

/**
 * 
 * @author Jose Rolando Lafaurie Olivares
 * @version 1.0.0
 * 
 * 
 */
public class OpacContributorServiceImpl implements ContributorService {
	private final LayoutProvider layoutProvider;
	private final LayoutContext layoutContext;
	private final Map<String, Contributor> contributorMap;

	private final Set<ContributorTracker> contributorTrackers;
	private final Map<String, CTabItem> openedContributorsID;

	private final Object lockObject;
	private CTabFolder contenTabFolder;

	private Composite contentParentComposite;
	private String currentContributorID;
	private Contributor defaultContributor;

	public OpacContributorServiceImpl(LayoutProvider layoutProvider, LayoutContext layoutContext) {
		super();
		this.layoutProvider = layoutProvider;
		this.layoutContext = layoutContext;
		this.contributorMap = new HashMap<String, Contributor>();
		this.contributorTrackers = new HashSet<ContributorTracker>();
		this.openedContributorsID = new HashMap<String, CTabItem>();
		this.lockObject = new Object();
		this.currentContributorID = "default";
	}

	public Map<String, Contributor> getContributorMap() {
		return contributorMap;
	}

	public Map<String, CTabItem> getOpenedContributorsID() {
		return openedContributorsID;
	}

	/**
	 * @see ContributorService
	 */
	@Override
	public void registerContentParent(Composite contentParent) {
		this.contentParentComposite = contentParent;
		contenTabFolder = new CTabFolder(contentParentComposite, SWT.V_SCROLL);
		contenTabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void close(final CTabFolderEvent event) {
				CTabItem current = (CTabItem) event.item;
				Contributor currentContributor = contributorMap.get(current.getData("contributorID"));
				final String currentItem = (String) event.item.getData("contributorID");
				if (!currentContributor.canClose()) {
					String title = "Confirmation";
					String message = "Nothing will be done. Ok?";
					DialogCallback callback = new DialogCallback() {
						private static final long serialVersionUID = 1L;

						public void dialogClosed(int returnCode) {
							if (returnCode == 0) {
								if (currentContributorID.equalsIgnoreCase(currentItem)) {
									currentContributorID = "default";
								}
								openedContributorsID.remove(currentItem);
								event.item.dispose();
							}
						}
					};

					MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), title, message, callback);
				} else {
					if (currentContributorID.equalsIgnoreCase(currentItem)) {
						currentContributorID = "default";
					}
					openedContributorsID.remove(currentItem);
					event.item.dispose();
				}
				event.doit = false;
			}

		});
	}

	/**
	 * @see ContributorService
	 */
	@Override
	public void addContributorTracker(ContributorTracker moduleUContributorTracker) {
		Object[] contributorsObjects;
		synchronized (lockObject) {
			contributorTrackers.add(moduleUContributorTracker);
			contributorsObjects = contributorMap.values().toArray();
		}
		for (int i = 0; i < contributorsObjects.length; i++) {
			moduleUContributorTracker.uiContributorAdded((Contributor) contributorsObjects[i]);
		}
	}

	/**
	 * @see ContributorService
	 */
	@Override
	public void removeContributorTracker(ContributorTracker moduleUContributorTracker) {
		Object[] contributorsObjects;
		synchronized (lockObject) {
			contributorTrackers.remove(moduleUContributorTracker);
			contributorsObjects = contributorMap.values().toArray();
		}
		for (int i = 0; i < contributorsObjects.length; i++) {
			moduleUContributorTracker.uiContributorRemoved((Contributor) contributorsObjects[i]);
		}
	}

	/**
	 * @see ContributorService
	 */
	@Override
	public String[] getContributorsID() {
		synchronized (lockObject) {
			String[] result = new String[contributorMap.values().size()];
			contributorMap.values().toArray(result);
			return result;
		}
	}

	/**
	 * @see ContributorService
	 */
	@Override
	public void selectContributor(String contributorID) {

		if (!this.currentContributorID.equalsIgnoreCase(contributorID)) {
			if (openedContributorsID.get(contributorID) != null) {

				contenTabFolder.setSelection(openedContributorsID.get(contributorID));
				layoutShell();
				this.currentContributorID = contributorID;
			} else {
				createNewContent(contributorID);
				layoutShell();
				this.currentContributorID = contributorID;
			}
		}
	}

	/**
	 * @see ContributorService
	 */
	@Override
	public void selectDefaultContributor() {
		if (defaultContributor != null) {
			selectContributor(defaultContributor.getID());
		} else {
			synchronized (lockObject) {
				if (!contributorMap.isEmpty()) {

					String defaultContrib = (String) contributorMap.keySet().toArray()[0];

					selectContributor(defaultContrib);

				}
			}
		}

	}

	/**
	 * 
	 */
	private void layoutShell() {
		layoutProvider.layout(layoutContext);
		((OpacLayoutContextImpl) layoutContext).layoutShell();
	}

	/**
	 * 
	 * @param contributorID
	 */
	private void createNewContent(String contributorID) {
		final Contributor contributor;
		final CTabItem item;
		synchronized (lockObject) {
			contributor = contributorMap.get(contributorID);
			Integer index = contenTabFolder.getItemCount();
			item = new CTabItem(contenTabFolder, SWT.NONE, index);
			item.setText(contributor.contributorName());
			item.setData("contributorID", contributorID);
			openedContributorsID.put(contributorID, item);
		}
		final Composite content = new Composite(contenTabFolder, SWT.NONE);
		content.setLayout(new FillLayout());

		((ContributorPage) contributor).setDimension(contenTabFolder.getBounds().width);
		((ContributorPage) contributor).setHeigth(contenTabFolder.getBounds().height);
		((ContributorPage) contributor).setContributorService(this);

		((ContributorPage) contributor).addListener(SWT.Dispose, new Listener() {
			private static final long serialVersionUID = 8109504985121440028L;

			@Override
			public void handleEvent(Event event) {
				openedContributorsID.remove(contributor.getID());
				currentContributorID = "default";
				item.dispose();
			}

		});

		if (((ContributorPage) contributor).getProperties() != null && ((ContributorPage) contributor).getProperties().containsKey(ContributorPage.NOT_SCROLLED)) {
			((ContributorPage) contributor).build(content);
			item.setData("content", content);
		} else {

			final ScrolledComposite scrolledComposite = new ScrolledComposite(content, SWT.V_SCROLL | SWT.H_SCROLL);
			scrolledComposite.setMinHeight(10);
			scrolledComposite.setMinWidth(content.getBounds().width);
			scrolledComposite.setExpandVertical(true);
			scrolledComposite.setExpandHorizontal(true);
			final Composite contrib = new Composite(scrolledComposite, SWT.NONE);
			contrib.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
			contrib.setLayout(new FormLayout());
			scrolledComposite.setContent(contrib);
			scrolledComposite.addControlListener(new ControlAdapter() {
				private static final long serialVersionUID = -536889160264809162L;

				@Override
				public void controlResized(final ControlEvent e) {
					final Rectangle r = scrolledComposite.getClientArea();
					scrolledComposite.setMinSize(contrib.computeSize(r.width, SWT.DEFAULT));
					contrib.layout();
				}
			});

			contrib.addListener(SWT.Resize, new TabItemResizeListener());
			((ContributorPage) contributor).build(contrib);
			scrolledComposite.setContent(contrib);
			item.setData("content", contrib);
		}

		item.setControl(content);
		if (contributor.getID().equals("MainContentID")) {
			setDefaultContributor(contributor);
		}

		item.setShowClose((defaultContributor.getID().equals(contributorID)) ? false : true);
		contenTabFolder.setSelection(openedContributorsID.get(contributorID));
		contentParentComposite.computeSize(contentParentComposite.getClientArea().width, SWT.DEFAULT);
		contentParentComposite.layout(true, true);
	}

	/**
	 * 
	 * @param defaultContributor
	 */
	public void setDefaultContributor(Contributor defaultContributor) {
		addUContributor(defaultContributor);
		this.defaultContributor = defaultContributor;
	}

	/**
	 * 
	 * @param contributor
	 */
	public void addUContributor(Contributor contributor) {
		Object[] trackers;
		synchronized (lockObject) {
			contributorMap.put(contributor.getID(), contributor);
			trackers = contributorTrackers.toArray();
		}
		for (int i = 0; i < trackers.length; i++) {
			((ContributorTracker) trackers[i]).uiContributorAdded(contributor);
		}
	}

	/**
	 * 
	 * @param contributor
	 */
	public void removeUContributor(Contributor contributor) {
		Object[] trackers;
		synchronized (lockObject) {
			contributorMap.remove(contributor.getID());
			trackers = contributorTrackers.toArray();
		}
		for (int i = 0; i < trackers.length; i++) {
			((ContributorTracker) trackers[i]).uiContributorRemoved(contributor);
		}
	}

	/**
	 * @see ContributorService
	 */
	@Override
	public void l10n() {
		for (String contributorID : openedContributorsID.keySet()) {
			Contributor auxContributor = contributorMap.get(contributorID);
			auxContributor.l10n();
			CTabItem auxCTabItem = openedContributorsID.get(contributorID);
			auxCTabItem.setText(auxContributor.contributorName());
		}
	}

	@Override
	public void selectContributor(String arg0, Object... arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCurrentContributorID() {
		return currentContributorID;
	}

	@Override
	public void refresh() {
		if (contenTabFolder.getSelection() != null) {
			Composite parent = (Composite) contenTabFolder.getSelection().getData("content");
			final Rectangle r = parent.getClientArea();
			parent.setSize(parent.computeSize(r.width, SWT.DEFAULT));
			parent.layout(true, true);
		}
	}

}
