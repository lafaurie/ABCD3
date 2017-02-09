package cu.uci.abcd.opac.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import cu.uci.abcd.opac.ui.contribution.OpacBackgroundProvider;
import cu.uci.abcd.opac.ui.contribution.OpacContentProvider;
import cu.uci.abcd.opac.ui.contribution.OpacFilterMenu;
import cu.uci.abcd.opac.ui.contribution.OpacFooterProvider;
import cu.uci.abcd.opac.ui.contribution.OpacHeaderProvider;
import cu.uci.abcd.opac.ui.contribution.OpacLoginProvider;
import cu.uci.abcd.opac.ui.contribution.OpacMenuBarProvider;
import cu.uci.abcd.opac.ui.contribution.OpacPerfilMenu;
import cu.uci.abos.api.ui.LayoutContext;
import cu.uci.abos.api.ui.LayoutProvider;
import cu.uci.abos.core.util.FormDatas;

public class OpacLayoutProviderImpl implements LayoutProvider {

	private static final int OFFSET = 20;
	private static final int SEPARATOR = 5;
	
	//private static final int CONTENT_OFFSET = OpacHeaderProvider.HEADER_HEIGHT
		//	+ OpacMenuBarProvider.MENU_BAR_HEIGHT;

	private Control content;
	private Control header;
	private Control menuBar;
	private Control filterMenu;
	private Control adquisitionMenu;	
	private Control footer;
	private Control background;
	private Control login;

	private void readControls(LayoutContext context) {
		header = context.getControl(OpacHeaderProvider.HEADER_CONTROL);
		menuBar = context.getControl(OpacMenuBarProvider.MENU_BAR_CONTROL);
		filterMenu = context.getControl(OpacFilterMenu.MAIN_MENU_CONTROL);
		content = context.getControl(OpacContentProvider.CONTENT_CONTROL);
		footer = context.getControl(OpacFooterProvider.FOOTER_CONTROL);
		background = context
				.getControl(OpacBackgroundProvider.BACKGROUND_CONTROL);
		adquisitionMenu = context
				.getControl(OpacPerfilMenu.ADQUISITION_MENU_CONTROL);
		login=context.getControl(OpacLoginProvider.LOGIN_MENU_CONTROL);
	}
  
	private void configureLayout() {
		layoutHeader();
		layoutLoginMenu();
		layoutMenuBar();
		layoutfilterMenu();
		layoutAdquisitionMenu();		
		layoutContent();
		layoutFooter();
		layoutBackground();
		
	}
    

	
	private void layoutLoginMenu() {

		FormDatas.attach(login).atLeftTo(content,-20)
				.atTopTo(menuBar,0).withHeight(calculateHeight()/3)
				.withWidth(OpacLoginProvider.LOGIN_MENU_WIDTH).withHeight(OpacLoginProvider.LOGIN_MENU_HEIGHT)
				.atRight(SEPARATOR);
	}
	
	private void layoutAdquisitionMenu() {

		FormDatas.attach(adquisitionMenu).atLeftTo(content, -20)
				.atTopTo(menuBar, 0).withHeight(calculateHeight() / 2)
				.withWidth(OpacPerfilMenu.ADQUISITION_MENU_WIDTH)
				.atRight(SEPARATOR);
	}	

	private void layoutfilterMenu() {
		FormDatas.attach(filterMenu).atTopTo(menuBar, SEPARATOR)
				.withWidth(OpacFilterMenu.MAIN_MENU_WIDTH)
				.withHeight(calculateHeight() - 5).atLeft(SEPARATOR);

	}  
       
	private void layoutHeader() {
		FormData layoutData = new FormData();
		header.setLayoutData(layoutData);
		layoutData.top = new FormAttachment(0, 0);
		layoutData.left = new FormAttachment(0, 0);
		layoutData.right = new FormAttachment(100, 0);
		layoutData.height = OpacHeaderProvider.HEADER_HEIGHT;
	}  

	private void layoutMenuBar() {
		FormData layoutData = new FormData();
		menuBar.setLayoutData(layoutData);
		layoutData.top = new FormAttachment(0, OpacHeaderProvider.HEADER_HEIGHT);
		layoutData.left = new FormAttachment(0, 0);
		layoutData.right = new FormAttachment(100, 0);
		layoutData.height = OpacMenuBarProvider.MENU_BAR_HEIGHT;
	}

	private void layoutContent() {
		FormData layoutData = new FormData();
		content.setLayoutData(layoutData);
		layoutData.top = new FormAttachment(menuBar, SEPARATOR);
		layoutData.left = new FormAttachment(filterMenu, -20);
		layoutData.right = new FormAttachment(85, 0);
		layoutData.bottom = new FormAttachment(footer, 0, SWT.TOP);
		layoutData.width = calculateWithd();
		layoutData.height = calculateHeight();
		RWT.getApplicationContext().setAttribute("width", layoutData.width);
	}

	private int calculateHeight() {
		int height = content.getSize().y;
		int maximumHeight = getMaximumHeight();
		if (height < maximumHeight) {
			height = maximumHeight;
		}
		return height;
	}

	private int calculateWithd() {
		int width = content.getSize().x;
		int maximumWidth = getMaximumWidth();
		if (width < maximumWidth) {
			width = maximumWidth;
		}
		return width;
	}

	private int getMaximumWidth() {

		int width;
		Rectangle bounds = Display.getCurrent().getBounds();
		width = bounds.width - OpacFilterMenu.MAIN_MENU_WIDTH
				- OpacPerfilMenu.ADQUISITION_MENU_WIDTH - SEPARATOR * 2;
		return width;
	}
   
	private int getMaximumHeight() {
		int height;
		Rectangle bounds = Display.getCurrent().getBounds();
		height = bounds.height - OpacFooterProvider.FOOTER_HEIGHT
				- OpacHeaderProvider.HEADER_HEIGHT
				- OpacMenuBarProvider.MENU_BAR_HEIGHT - OFFSET;
		return height;
	}

	private void layoutFooter() {
		FormDatas.attach(footer).atTopTo(filterMenu).atLeft(0).atRight(0)
				.withHeight(OpacFooterProvider.FOOTER_HEIGHT).atBottom(0);
	}

	private void layoutBackground() {
		FormData layoutData = new FormData();
		background.setLayoutData(layoutData);
		layoutData.top = new FormAttachment(0, 0);
		layoutData.left = new FormAttachment(0, 0);
		layoutData.right = new FormAttachment(100, 0);
		layoutData.bottom = new FormAttachment(100, 0);
	}

	@Override
	public void layout(LayoutContext layoutContext) {
		readControls(layoutContext);
		configureLayout();
	}
}