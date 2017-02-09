/*
 * @(#)ContributorServiceImpl.java 1.0.0 26/06/2014 
 * Copyright (c) 2014 Universidad de las Ciencias Informaticas
 */
package cu.uci.abos.core.ui.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

import cu.uci.abos.core.ui.internal.listener.TabItemResizeListener;
import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IContributorService;
import cu.uci.abos.ui.api.IContributorTracker;
import cu.uci.abos.ui.api.ILayoutContext;
import cu.uci.abos.ui.api.ILayoutProvider;

/**
 * 
 * @author Jose Rolando Lafaurie Olivares
 * @version 1.0.0
 * 
 * 
 */
public class ContributorServiceImpl implements IContributorService {
	private final ILayoutProvider layoutProvider;
	private final ILayoutContext layoutContext;
	private final Map<String, IContributor> contributorMap;
	private final Set<IContributorTracker> contributorTrackers;
	private final Map<String, CTabItem> openedContributorsID;
	private final Object lockObject;
	private CTabFolder contenTabFolder;

	private Composite contentParentComposite;
	private String currentContributorID;
	private IContributor defaultContributor;

	public ContributorServiceImpl(ILayoutProvider layoutProvider, ILayoutContext layoutContext) {
		super();
		this.layoutProvider = layoutProvider;
		this.layoutContext = layoutContext;
		this.contributorMap = new HashMap<String, IContributor>();
		this.contributorTrackers = new HashSet<IContributorTracker>();
		this.openedContributorsID = new HashMap<String, CTabItem>();
		this.lockObject = new Object();
		this.currentContributorID = "default";
	}

	/**
	 * @see IContributorService
	 */
	@Override
	public void registerContentParent(Composite contentParent) {
		this.contentParentComposite = contentParent;
		contenTabFolder = new CTabFolder(contentParentComposite, SWT.V_SCROLL);
		contenTabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			private static final long serialVersionUID = 1L;

			@Override
			public void close(final CTabFolderEvent event) {

				final String currentItem = (String) event.item.getData("contributorID");

				if (currentContributorID.equalsIgnoreCase(currentItem)) {
					currentContributorID = "default";
				}
				openedContributorsID.remove(currentItem);
				event.item.dispose();
			}
		});
	}

	/**
	 * @see IContributorService
	 */
	@Override
	public void addContributorTracker(IContributorTracker moduleUIContributorTracker) {
		Object[] contributorsObjects;
		synchronized (lockObject) {
			contributorTrackers.add(moduleUIContributorTracker);
			contributorsObjects = contributorMap.values().toArray();
		}
		for (int i = 0; i < contributorsObjects.length; i++) {
			moduleUIContributorTracker.uiContributorAdded((IContributor) contributorsObjects[i]);
		}
	}

	/**
	 * @see IContributorService
	 */
	@Override
	public void removeContributorTracker(IContributorTracker moduleUIContributorTracker) {
		Object[] contributorsObjects;
		synchronized (lockObject) {
			contributorTrackers.remove(moduleUIContributorTracker);
			contributorsObjects = contributorMap.values().toArray();
		}
		for (int i = 0; i < contributorsObjects.length; i++) {
			moduleUIContributorTracker.uiContributorRemoved((IContributor) contributorsObjects[i]);
		}
	}

	/**
	 * @see IContributorService
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
	 * @see IContributorService
	 */
	@Override
	public void selectContributor(String contributorID) {
		if (!this.currentContributorID.equalsIgnoreCase(contributorID)) {
			if (openedContributorsID.get(contributorID) != null) {
				contenTabFolder.setSelection(openedContributorsID.get(contributorID));
				contenTabFolder.getSelection().dispose();
				openedContributorsID.remove(contributorID);
			}
			createNewContent(contributorID);
			layoutShell();
			this.currentContributorID = contributorID;
		}
	}

	/**
	 * @see IContributorService
	 */
	@Override
	public void selectDefaultContributor() {
		if (defaultContributor != null) {
			selectContributor(defaultContributor.getID());
		} else {
			synchronized (lockObject) {
				if (!contributorMap.isEmpty()) {
					selectContributor((String) contributorMap.keySet().toArray()[0]);
				}
			}
		}
	}

	/**
	 * 
	 */
	private void layoutShell() {
		layoutProvider.layout(layoutContext);
		((LayoutContextImpl) layoutContext).layoutShell();
	}

	/**
	 * 
	 * @param contributorID
	 */
	private void createNewContent(String contributorID) {
		IContributor contributor;
		CTabItem item;
		synchronized (lockObject) {
			contributor = contributorMap.get(contributorID);
			item = new CTabItem(contenTabFolder, SWT.NONE, 0);
			item.setText(contributor.contributorName());
			item.setData("contributorID", contributorID);
			openedContributorsID.put(contributorID, item);
		}
		Composite content = new Composite(contenTabFolder, SWT.NONE);
		content.setLayout(new FillLayout());
		ScrolledComposite scrolledComposite = new ScrolledComposite(content, SWT.V_SCROLL | SWT.H_SCROLL);
		scrolledComposite.setMinHeight(200);
		scrolledComposite.setMinWidth(content.getBounds().width);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		Composite contrib = new Composite(scrolledComposite, SWT.NONE);
		contrib.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		contrib.setLayout(new FormLayout());
		contrib.addListener(SWT.Resize, new TabItemResizeListener());

		contributor.createUIControl(contrib);
		scrolledComposite.setContent(contrib);
		item.setControl(content);
		item.setShowClose(true);
		contenTabFolder.setSelection(0);
		contentParentComposite.layout(true, true);
	}

	/**
	 * 
	 * @param defaultContributor
	 */
	public void setDefaultContributor(IContributor defaultContributor) {
		addUIContributor(defaultContributor);
		this.defaultContributor = defaultContributor;
	}

	/**
	 * 
	 * @param contributor
	 */
	public void addUIContributor(IContributor contributor) {
		Object[] trackers;
		synchronized (lockObject) {
			contributorMap.put(contributor.getID(), contributor);
			trackers = contributorTrackers.toArray();
		}
		for (int i = 0; i < trackers.length; i++) {
			((IContributorTracker) trackers[i]).uiContributorAdded(contributor);
		}
	}

	/**
	 * 
	 * @param contributor
	 */
	public void removeUIContributor(IContributor contributor) {
		Object[] trackers;
		synchronized (lockObject) {
			contributorMap.remove(contributor.getID());
			trackers = contributorTrackers.toArray();
		}
		for (int i = 0; i < trackers.length; i++) {
			((IContributorTracker) trackers[i]).uiContributorRemoved(contributor);
		}
	}

	/**
	 * @see IContributorService
	 */
	@Override
	public void l10n() {
		for (String contributorID : openedContributorsID.keySet()) {
			IContributor auxContributor = contributorMap.get(contributorID);
			auxContributor.l10n();
			CTabItem auxCTabItem = openedContributorsID.get(contributorID);
			auxCTabItem.setText(auxContributor.contributorName());
		}
	}

}
