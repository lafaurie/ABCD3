package cu.uci.abos.widgets.paginator;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.widgets.grid.SortData;

@SuppressWarnings("serial")
public class PaginatorBar extends Composite {

	private int pageSize;
	private int totalPages;
	private int totalElements;
	private int currentPage;
	private int oldPage;
	private int startIndex;
	private int endIndex;
	private SortData sortData;
	private List<PageChangeListener> pageChangeListeners;

	private ToolBar paginationToolBar;
	ToolItem firstItem, beforeItem, nextItem, lastItem;
	private Label pageCountItem;
	private Combo comboItem;

	public PaginatorBar(Composite parent, int style) {
		super(parent, style);
		this.pageSize = 10;
		this.currentPage = 0;
		this.totalPages = 0;
		this.totalElements = 0;
		this.pageChangeListeners = new LinkedList<>();
		this.createPagination();
	}

	private void createPagination() {
		this.setLayout(new FormLayout());
		paginationToolBar = new ToolBar(this, SWT.NONE);
		FormDatas.attach(paginationToolBar).atTop().atLeft();

		firstItem = new ToolItem(paginationToolBar, SWT.PUSH);
		Image doubleLeftImg = new Image(this.getDisplay(), RWT.getResourceManager().getRegisteredContent("left-arrow"));
		firstItem.setImage(doubleLeftImg);
		firstItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				goToFirstPage();
			}
		});

		beforeItem = new ToolItem(paginationToolBar, SWT.PUSH);
		Image singleLeftImg = new Image(this.getDisplay(), RWT.getResourceManager().getRegisteredContent("sleft-arrow"));
		beforeItem.setImage(singleLeftImg);
		beforeItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				goToBeforePage();
			}
		});

		nextItem = new ToolItem(paginationToolBar, SWT.PUSH);
		Image singleRightImg = new Image(this.getDisplay(), RWT.getResourceManager().getRegisteredContent("sright-arrow"));
		nextItem.setImage(singleRightImg);
		nextItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				goToNextPage();
			}
		});

		lastItem = new ToolItem(paginationToolBar, SWT.PUSH);
		Image doubleRightImg = new Image(this.getDisplay(), RWT.getResourceManager().getRegisteredContent("right-arrow"));
		lastItem.setImage(doubleRightImg);
		lastItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				goToLastPage();
			}
		});

		pageCountItem = new Label(this, SWT.NONE);

		comboItem = new Combo(this, SWT.BORDER);
		FormDatas.attach(comboItem).atTop(5).atRight().withHeight(21).withWidth(75);
		comboItem.setItems(new String[] { "5", "10", "20", "50", "100" });
		comboItem.setText(String.valueOf(this.pageSize));
		comboItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				pageSize = Integer.parseInt(comboItem.getText());
				goToFirstPage();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});

		comboItem.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				try {
					pageSize = Integer.parseInt(comboItem.getText());
					goToFirstPage();
				} catch (Exception e) {
				}
			}
		});

		this.addControlListener(new ControlListener() {
			@Override
			public void controlResized(ControlEvent event) {
				PaginatorBar.this.controlResized(event);
			}

			@Override
			public void controlMoved(ControlEvent arg0) {
			}
		});
	}

	protected void controlResized(ControlEvent event) {
		int middleTotalWidth = this.getSize().x / 2;
		int middleLabelWidht = pageCountItem.getSize().x / 2;
		FormDatas.attach(pageCountItem).atTop(8).atLeft(middleTotalWidth - middleLabelWidht);
	}

	public void goToFirstPage() {
		this.goToPage(1);
	}

	public void goToBeforePage() {
		this.goToPage(this.currentPage - 1);
	}

	public void goToNextPage() {
		this.goToPage(this.currentPage + 1);
	}

	public void goToLastPage() {
		this.goToPage(this.totalPages);
	}

	public void goToPage(int page) {
		// if(page > 0 && page <= this.totalPages && this.totalElements > 0) {
		if (page > 0) {
			this.updatePaginationData(page);
			this.onPageChange();
			this.updatePaginationData(page);
		}
	}

	public void refresh() {
		updatePaginationData(this.currentPage);
	}

	private void updatePaginationData(int page) {
		this.oldPage = this.currentPage;
		this.currentPage = page;
		this.totalPages = this.totalElements / this.pageSize;
		if (this.totalElements % this.pageSize > 0) {
			this.totalPages++;
		}
		this.startIndex = Math.max((this.currentPage - 1) * this.pageSize, -1);
		this.endIndex = Math.min(startIndex + this.pageSize, totalElements);
		if (this.totalElements > 0) {

			this.pageCountItem.setText(this.currentPage + "/" + this.totalPages + "  (" + (this.startIndex + 1) + "-" + this.endIndex + ")/" + this.totalElements);

			this.firstItem.setEnabled(!this.isFirst());
			this.beforeItem.setEnabled(!this.isFirst());
			this.nextItem.setEnabled(!this.isLast());
			this.lastItem.setEnabled(!this.isLast());
		} else {
			this.pageCountItem.setText("0" + "/" + "0" + "  (" + "0" + "-" + "0" + ")/" + "0");

			this.firstItem.setEnabled(false);
			this.beforeItem.setEnabled(false);
			this.nextItem.setEnabled(false);
			this.lastItem.setEnabled(false);

		}

		this.pageCountItem.getParent().layout(true, true);
		this.pageCountItem.getParent().redraw();
		this.pageCountItem.getParent().update();

	}

	public void addPageChangeListener(PageChangeListener listener) {
		this.pageChangeListeners.add(listener);
	}

	public void addAllPageChangedListeners(List<PageChangeListener> listeners) {
		this.pageChangeListeners.addAll(listeners);
	}

	private void firePageChangedEvent(PageChangedEvent event) {
		for (PageChangeListener listener : this.pageChangeListeners) {
			listener.pageChanged(event);
		}
	}

	public void onPageChange() {
		PageChangedEvent event = new PageChangedEvent();
		event.oldPage = this.oldPage;
		event.currentPage = this.currentPage;
		event.isFirst = this.isFirst();
		event.isLast = this.isLast();
		event.pageSize = this.pageSize;
		event.totalPages = this.totalPages;
		event.totalElements = this.totalElements;
		event.startIndex = this.startIndex;
		event.endIndex = this.endIndex;
		event.sortData = this.sortData;
		firePageChangedEvent(event);
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getOldPage() {
		return oldPage;
	}

	public void setOldPage(int oldPage) {
		this.oldPage = oldPage;
	}

	public SortData getSortData() {
		return sortData;
	}

	public void setSortData(SortData sortData) {
		this.sortData = sortData;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize>0) {
			this.pageSize = pageSize;
			if (this.comboItem != null && !this.comboItem.isDisposed() && !this.comboItem.getText().equals(String.valueOf(pageSize))) {
				this.comboItem.setText(String.valueOf(pageSize));
			}	
		}
	}

	public int getTotalPages() {
		return this.totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public boolean isFirst() {
		return this.currentPage == 1;
	}

	public boolean isLast() {
		return this.currentPage == this.totalPages;
	}
}
