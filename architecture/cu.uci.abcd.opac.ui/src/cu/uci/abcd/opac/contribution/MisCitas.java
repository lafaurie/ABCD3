package cu.uci.abcd.opac.contribution;

import java.util.Arrays;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
//import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.widgets.TreeItem;
import cu.uci.abcd.opac.listener.SelectionListViewEntity;
import cu.uci.abcd.opac.listener.TabItemResizeListener;
import cu.uci.abos.ui.api.IContributor;
import cu.uci.abos.ui.api.IViewController;
import cu.uci.abos.util.api.FormDatas;
import cu.uci.abos.widgets.grid.CRUDTreeTable;
import cu.uci.abos.widgets.grid.Column;
import cu.uci.abos.widgets.grid.TreeColumnEvent;
import cu.uci.abos.widgets.grid.TreeColumnListener;

public class MisCitas implements IContributor {

	Composite result;
	
	
	@Override
public Control createUIControl(final Composite parent) {
		
		
		
		result = new Composite(parent, SWT.NONE);
		result.setLayout(new FormLayout());
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(result,
				SWT.V_SCROLL | SWT.H_SCROLL);
		scrolledComposite.setMinHeight(200);
		scrolledComposite.setMinWidth(result.getBounds().width);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		Composite contrib = new Composite(scrolledComposite, SWT.NONE);
		contrib.setData(RWT.CUSTOM_VARIANT, "workspace_content_area");
		contrib.setLayout(new FormLayout());
		contrib.addListener(SWT.Resize, new TabItemResizeListener());
		
		
		
		parent.setLayout(new FormLayout());		
		final Composite container = new Composite(result, SWT.NONE);
		container.setLayout(new FormLayout());
		Rectangle navigatorBounds = result.getDisplay().getBounds();
		final FormData formLayoutData = new FormData();		
		formLayoutData.top = new FormAttachment(0, 5);
		formLayoutData.left = new FormAttachment(0, 5);
		formLayoutData.width = navigatorBounds.width - 10;
		formLayoutData.height = navigatorBounds.height - 50;
		container.setLayoutData(formLayoutData);
		
		final CRUDTreeTable fdt = new CRUDTreeTable(container, SWT.NONE);
		FormDatas.attach(fdt).atRight(0).atLeft(0);
		fdt.setEntityClass(SelectionListViewEntity.class);			
		fdt.setSearch(true);
		fdt.setSaveAll(true);
		fdt.addActionColumn(new Column(parent.getDisplay(), "arrow_down.png", new TreeColumnListener() {
			public void handleEvent(TreeColumnEvent event) {
				final Shell shell = new Shell(parent.getDisplay());
				shell.setLayout(new GridLayout());
				shell.setText("Hacer algo");
				shell.setSize(400, 100);
				shell.setLocation(600, 200);
				Label lbl = new Label(shell, SWT.NONE);
				lbl.setText("Hacer algo");
				Button closeBtn = new Button(shell, SWT.PUSH);
				closeBtn.setText("Cerrar");
				closeBtn.addSelectionListener(new SelectionListener() {
					private static final long serialVersionUID = 1L;
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						shell.close();
						shell.dispose();
					}						
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});
				shell.open();
			}
		}));   
		
		fdt.setSearchHintText("Buscar");		 //For the internationalization. If non set, it will show up blank.
		fdt.setAddButtonText("Adicionar");       //For the internationalization. If it is non set, only the icon will show up.
		fdt.setSaveAllButtonText("Salvar todo"); //For the internationalization. If it is non set, only the icon will show up.
		fdt.setCancelButtonText("Cancelar");     //For the internationalization. If it is non set, only the icon will show up.
		fdt.setColumnHeaders(Arrays.asList("Etiqueta", "Nombre", "Tipo", "Subcapmpos/Patrón"));	//For the internationalization. If non set, it will show up blank.
		
		try {
			fdt.createTable();
		}
		catch(Exception e) {
		}/*

		TreeItem root = fdt.addRow(new SelectionListViewEntity(0, "nombre", 0 ,  "ab","nombre"));
		fdt.addRow(new SelectionListViewEntity(1, "apellidouug", 0, "ab","apellidouug"));
		fdt.addRow(new SelectionListViewEntity(2, "identificación", 0,  "ab","identificación"));
		fdt.addRow(new SelectionListViewEntity(3, "sexo", 0, "ab","sexo"));			
		fdt.addInnerRow(root, new SelectionListViewEntity(1, "apodo", 0, "ab","apodo"));
			*/
		fdt.addUpdateListener(new TreeColumnListener() {
			public void handleEvent(TreeColumnEvent event) {
				//IGridViewEntity entity = (FDTViewEntity) event.entity;
				//do something with the data
				//event.showEditableArea = false;
			}
		});
		
		fdt.addDeleteListener(new TreeColumnListener() {
			public void handleEvent(final TreeColumnEvent event) {
				event.performDelete = true;
			}
		});
		
		Button i10nSimulatorEnBtn = new Button(container, SWT.PUSH);
		FormDatas.attach(i10nSimulatorEnBtn).atTopTo(fdt, 10);
		i10nSimulatorEnBtn.setText("Idioma inglés");
		i10nSimulatorEnBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent event) {
				fdt.setSearchHintText("Search");		 //For the internationalization. If non set, it will show up blank.
				fdt.setAddButtonText("Add");       //For the internationalization. If it is non set, only the icon will show up.
				fdt.setSaveAllButtonText("Save all"); //For the internationalization. If it is non set, only the icon will show up.
				fdt.setCancelButtonText("Cancel");     //For the internationalization. If it is non set, only the icon will show up.
				fdt.setColumnHeaders(Arrays.asList("Tag", "Name", "Type", "Subfields/Patterns"));	//For the internationalization. If non set, it will show up blank.
				
			}			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Button i10nSimulatorEsBtn = new Button(container, SWT.PUSH);
		FormDatas.attach(i10nSimulatorEsBtn).atTopTo(fdt, 10).atLeftTo(i10nSimulatorEnBtn, 10);
		i10nSimulatorEsBtn.setText("Idioma español");
		i10nSimulatorEsBtn.addSelectionListener(new SelectionListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void widgetSelected(SelectionEvent event) {
				fdt.setSearchHintText("Buscar");		 //For the internationalization. If non set, it will show up blank.
				fdt.setAddButtonText("Adicionar");       //For the internationalization. If it is non set, only the icon will show up.
				fdt.setSaveAllButtonText("Salvar todo"); //For the internationalization. If it is non set, only the icon will show up.
				fdt.setCancelButtonText("Cancelar");     //For the internationalization. If it is non set, only the icon will show up.
				fdt.setColumnHeaders(Arrays.asList("Etiqueta", "Nombre", "Tipo", "Subcapmpos/Patrón"));	//For the internationalization. If non set, it will show up blank.
			}			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		return parent;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return "MisCitasID";
	}

	@Override
	public void l10n() {
		// TODO Auto-generated method stub

	}

	@Override
	public String contributorName() {
		// TODO Auto-generated method stub
		return "Mis Citas";
	}

	@Override
	public boolean canClose() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setViewController(IViewController controller) {
		// TODO Auto-generated method stub

	}

}
