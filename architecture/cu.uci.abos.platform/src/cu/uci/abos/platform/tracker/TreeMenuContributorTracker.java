package cu.uci.abos.platform.tracker;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.api.ui.ContributorTracker;
import cu.uci.abos.platform.domain.MenuEntity;

public class TreeMenuContributorTracker implements ContributorTracker {

	private ExpandBar expandBar;
	private ContributorService service;
	private Map<String, ExpandItem> expandItems;
	private Map<String, MenuEntity> menuStructure;
	private Map<String, TreeItem> treeItems;
	private Map<String, TreeItem> treeItemsLeaf;
	private Map<String, Tree> treeMap;

	public TreeMenuContributorTracker(ExpandBar expandBar, ContributorService service, Map<String, MenuEntity> MenuStructure) {
		super();
		this.expandBar = expandBar;
		this.service = service;
		this.menuStructure = MenuStructure;
		this.expandItems = new HashMap<String, ExpandItem>();
		treeItems = new HashMap<String, TreeItem>();
		treeMap = new HashMap<String, Tree>();
		treeItemsLeaf = new HashMap<String, TreeItem>();

	}

	@Override
	public void uiContributorAdded(final Contributor contributor) {
		expandBar.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {

				String contribution = contributor.getID();

				if (menuStructure.get(contribution) != null) {
					MenuEntity menuEntity = (MenuEntity) menuStructure.get(contribution);
					String module = menuEntity.containerMenu;

					ExpandItem menuItem = expandItems.get(module);
					// pregunto si el modulo ya existe
					if (menuItem == null) {
						// en caso de que el modulo no se haya creado, lo
						// creamos
						creatExpandItem(menuEntity);

						// adicionamos las categorias al modulo, asi como sus
						// funcionalidades
						createCategories(menuEntity, contributor);

					} else {

						createCategories(menuEntity, contributor);

					}

				}

			}
		});
	}

	@Override
	public void uiContributorRemoved(Contributor contributor) {

	}

	Integer calculateHeigth(TreeItem[] items) {
		Integer functionality = 0;
		Integer category = 0;

		for (int i = 0; i < items.length; i++) {
			Integer childrens = items[i].getItemCount();
			category = category + 40;
			functionality = functionality + (childrens * 30);

		}
		return functionality + category;
	}

	void TreeSelectionListener(Tree tree, final Contributor contributor) {
		tree.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TreeItem treeItem = (TreeItem) arg0.item;
				Contributor contributorId = (Contributor) treeItem.getData("contributor");
				if (contributorId != null && contributor.getID().equals(contributorId.getID())) {
					ocultar();
					service.selectContributor(contributor.getID());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

	}

	private void ocultar() {
		if (Display.getCurrent().getBounds().width < 720) {
			Composite composite = (Composite) expandBar.getParent();
			Control button = (Control) composite.getChildren()[0];
			button.notifyListeners(SWT.Selection, new Event());
		}
	}

	private void createCategories(MenuEntity menuEntity, Contributor contributor) {
		String category = menuEntity.containerCategory;
		String module = menuEntity.containerMenu;
		final String contribution = menuEntity.functionality;

		// pregunto si la categoria ya esta creada
		TreeItem treeItemCategory = treeItems.get(category + module);
		if (treeItemCategory == null) {
			// si la categoria no esta creada se adiciona al árbol.
			Tree root = treeMap.get(module);

			boolean added = false;
			for (int i = 0; i < root.getItems().length && added == false; i++) {
				if (((Integer) root.getItems()[i].getData("categoryOrder")).compareTo(menuEntity.categoryOrder) > 0) {
					treeItemCategory = new TreeItem(root, SWT.NORMAL | SWT.FILL, i);
					added = true;
				}
			}
			if (!added) {
				treeItemCategory = new TreeItem(root, SWT.NORMAL | SWT.FILL);
			}
			treeItemCategory.setData("categoryOrder", menuEntity.categoryOrder);

			treeItemCategory.setData(RWT.CUSTOM_VARIANT, "menu_item");
			treeItemCategory.setText(category);
			treeItemCategory.setData(category, menuEntity.containerCategoryId);

			// por último se debe adicionar la funcionalidad dentro de la
			// categoria y el módulo que corresponde
			if (treeItemsLeaf.get(contributor.getID()) == null) {
				TreeItem functionItem = new TreeItem(treeItemCategory, SWT.NORMAL | SWT.FILL);
				functionItem.setData(RWT.CUSTOM_VARIANT, "menu_item");
				functionItem.setText(contributor.contributorName());
				functionItem.setData("contributor", contributor);
				functionItem.getParentItem().setExpanded(true);
				TreeSelectionListener(functionItem.getParent(), contributor);

				treeItems.put(category + module, treeItemCategory);
				treeItemsLeaf.put(contributor.getID(), functionItem);
				TreeItem[] childItems = functionItem.getParent().getItems();
				Integer sizeInteger = calculateHeigth(childItems);
				expandItems.get(module).setHeight(sizeInteger);

			}

		} else {

			if (treeItemsLeaf.get(contribution) == null) {
				TreeItem functionItem = new TreeItem(treeItemCategory, SWT.NORMAL);
				functionItem.setData(RWT.CUSTOM_VARIANT, "menu_item");
				functionItem.setText(contributor.contributorName());
				functionItem.setData("contributor", contributor);
				functionItem.getParentItem().setExpanded(true);
				TreeSelectionListener(functionItem.getParent(), contributor);

				treeItems.put(category + module, treeItemCategory);
				treeItemsLeaf.put(contributor.getID(), functionItem);
				TreeItem[] childItems = functionItem.getParent().getItems();
				Integer sizeInteger = calculateHeigth(childItems);
				expandItems.get(module).setHeight(sizeInteger);
			}
		}

	}

	private void creatExpandItem(MenuEntity menuEntity) {

		String module = menuEntity.containerMenu;
		ExpandItem expandItem = null;

		boolean added = false;
		for (int i = 0; i < expandBar.getItems().length && added == false; i++) {
			if (((Integer) expandBar.getItems()[i].getData("index")).compareTo(menuEntity.index) > 0) {
				expandItem = new ExpandItem(expandBar, SWT.WRAP | SWT.FILL, i);
				added = true;
			}
		}
		if (!added) {
			expandItem = new ExpandItem(expandBar, SWT.WRAP | SWT.FILL);
		}
		expandItem.setData("index", menuEntity.index);
		expandItem.setText(module);
		expandItem.setData(module, menuEntity.containerMenuId);

		Tree tree = treeMap.get(module);
		// pregunto si ya se creo el arbol del modulo
		if (tree == null) {
			tree = new Tree(expandBar, SWT.NORMAL | SWT.FILL);
			expandItem.setControl(tree);
			treeMap.put(module, tree);

		} else {

			expandItem.setControl(tree);
		}

		expandItem.setExpanded(false);
		expandItems.put(module, expandItem);

	}

}
