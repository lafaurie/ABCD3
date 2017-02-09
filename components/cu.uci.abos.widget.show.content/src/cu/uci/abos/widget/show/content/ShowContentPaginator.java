package cu.uci.abos.widget.show.content;

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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.widget.show.content.showContentPaginatorEvent.PageChangeEvent;
import cu.uci.abos.widget.show.content.showContentPaginatorEvent.PageChangeListener;
    
@SuppressWarnings("serial")
public class ShowContentPaginator extends Composite {
	
	private int pageSize;
	private int totalPages;
	private int totalElements;
	private int currentPage;
	private int oldPage;
	private int startIndex;
	private int endIndex;
	private List<PageChangeListener> pageChangeListeners;

	private ToolBar paginationToolBar;
	ToolItem firstItem, beforeItem, nextItem, lastItem;
	private Label pageCountItem;
	private Combo comboItem;

	ShowContentPaginator(Composite parent, int style) {
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

		comboItem = new Combo(this, SWT.BORDER //| SWT.READ_ONLY
				);
		FormDatas.attach(comboItem).atTop(5).atRight().withHeight(21).withWidth(90);
		comboItem.setItems(new String[] { "5", "10", "20", "50", "100", "1000" });
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

		comboItem.addListener(SWT.Verify, new Listener() {
		      public void handleEvent(Event e) {
		        String string = e.text;
		        if (!string.isEmpty()) {
		        char[] chars = new char[string.length()];
		        string.getChars(0, chars.length, chars, 0);
		        boolean init = Character.isDigit(string.charAt(0)) && Integer.parseInt(String.valueOf(string.charAt(0)))!=0;
		        if(init){
					for (int i = 1; i < string.length(); i++) {
						char c = string.charAt(i);
						if (!Character.isDigit(c)) {
							e.doit = false;
				            return;
						}
					}
				}else{
					e.doit = false;
		            return;
				}
		      }
		      }
		    });
		
		this.addControlListener(new ControlListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void controlResized(ControlEvent event) {
				ShowContentPaginator.this.controlResized(event);
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
		if (page > 0) {
			this.updatePaginationData(page);
			this.onPageChange();
			this.updatePaginationData(page);
		}
	}

	public void refresh() {
		this.updatePaginationData(this.currentPage);
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
	
	
	public void onPageChange() {
		PageChangeEvent event = new PageChangeEvent();
		event.oldPage = this.oldPage;
		event.currentPage = this.currentPage;
		event.isFirst = this.isFirst();
		event.isLast = this.isLast();
		event.pageSize = this.pageSize;
		event.totalPages = this.totalPages;
		event.totalElements = this.totalElements;
		event.startIndex = this.startIndex;
		event.endIndex = this.endIndex;
		firePageChangedEvent(event);
	}       
	
	public boolean isFirst() {
		return this.currentPage == 1;
	}

	public boolean isLast() {
		return this.currentPage == this.totalPages;
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
	
	public int getCurrentPage() {
		return currentPage;
	}
   
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	private void firePageChangedEvent(PageChangeEvent event) {
		for (PageChangeListener listener : this.pageChangeListeners) {
			listener.pageChanged(event);
		}
	}
	
	public void addPageChangeListener(PageChangeListener listener) {
		this.pageChangeListeners.add(listener);
	}

	public void addAllPageChangedListeners(List<PageChangeListener> listeners) {
		this.pageChangeListeners.addAll(listeners);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
