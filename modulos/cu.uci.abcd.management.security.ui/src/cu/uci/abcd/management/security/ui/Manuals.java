package cu.uci.abcd.management.security.ui;

import java.io.File;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abos.core.util.ReportType;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.URLUtil;

public class Manuals extends ContributorPage implements Contributor{
	
	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().MANUALS);
	}

	@Override
	public String getID() {
		return "manualsID";
	}
	
	Link adquisition;
	Link cataloguing;
	Link circulation;
	Link libraryAdmin;
	Link securityAdmin;
	Link nomenclatorAdmin;
	Link stadistic;
	Link opac;
	
	@Override
	public Control createUIControl(Composite parent) {
		
		File folder = new File(System.getProperty("java.io.tmpdir"));
		final String urlManuales = folder.getAbsolutePath().substring(0, folder.getAbsolutePath().length()-8)+"abcdconfig/manuals/";
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		
		Composite all = new Composite(parent,SWT.NONE);
		FormLayout form = new FormLayout();
		all.setData(RWT.CUSTOM_VARIANT, "gray_background");
		all.setLayout(form);
		FormDatas.attach(all).atTopTo(parent, 15).atRight(0).atLeft(0).atBottom(0);
		
		adquisition = new Link(all, SWT.NONE);
		FormDatas.attach(adquisition).atTopTo(all, 20).atRight(0).atLeft(25);
		adquisition.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void handleEvent(Event arg0) {
				try {
					 URLUtil.download(urlManuales +
					 MessageUtil.unescape(AbosMessages.get().LANGUAGE)+"/",
					 "CIGED_ABCD_3.0_Manual_Usuario_Módulo_Adquisición.pdf",
					 MessageUtil.unescape(AbosMessages.get().ADQUISITION_MODULE),
					 ReportType.PDF);
				} catch (Exception e) {
					RetroalimentationUtils.showErrorMessage(MessageUtil
							.unescape(AbosMessages.get().NOT_EXIST_FILE));
				}
			}
		});
		cataloguing = new Link(all, SWT.NONE);
		FormDatas.attach(cataloguing).atTopTo(adquisition, 20).atRight(0).atLeft(25);
		cataloguing.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void handleEvent(Event arg0) {
				try {
					 URLUtil.download(urlManuales +
					 MessageUtil.unescape(AbosMessages.get().LANGUAGE)+"/",
					 "CIGED_ABCD_3.0_Manual_Usuario_Módulo_Catalogación.pdf",
					 MessageUtil.unescape(AbosMessages.get().CATALOGUING_MODULE),
					 ReportType.PDF);
				} catch (Exception e) {
					RetroalimentationUtils.showErrorMessage(MessageUtil
							.unescape(AbosMessages.get().NOT_EXIST_FILE));
				}
			}
		});
		circulation = new Link(all, SWT.NONE);
		FormDatas.attach(circulation).atTopTo(cataloguing, 20).atRight(0).atLeft(25);
		circulation.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void handleEvent(Event arg0) {
				try{
				URLUtil.download(
						urlManuales
								+ MessageUtil
										.unescape(AbosMessages.get().LANGUAGE)
								+ "/",
						"CIGED_ABCD_3.0_Manual_Usuario_Módulo_Circulación.pdf",
						MessageUtil.unescape(AbosMessages.get().CIRCULATION_MODULE),
						ReportType.PDF);
				}catch(Exception e){
					RetroalimentationUtils.showErrorMessage(MessageUtil
							.unescape(AbosMessages.get().NOT_EXIST_FILE));
				}
			}
		});
		libraryAdmin = new Link(all, SWT.NONE);
		FormDatas.attach(libraryAdmin).atTopTo(circulation, 20).atRight(0).atLeft(25);
		libraryAdmin.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void handleEvent(Event arg0) {
				try {
					URLUtil.download(
							urlManuales
									+ MessageUtil
											.unescape(AbosMessages.get().LANGUAGE)
									+ "/",
							"CIGED_ABCD_3.0_Manual_Usuario_Módulo_Administracion_Biblioteca.pdf",
							MessageUtil.unescape(AbosMessages.get().LIBRARY_ADMINISTRATION_MODULE),
							ReportType.PDF);
				} catch (Exception e) {
					RetroalimentationUtils.showErrorMessage(MessageUtil
							.unescape(AbosMessages.get().NOT_EXIST_FILE));

				}
			}
		});
		securityAdmin = new Link(all, SWT.NONE);
		FormDatas.attach(securityAdmin).atTopTo(libraryAdmin, 20).atRight(0).atLeft(25);
		securityAdmin.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void handleEvent(Event arg0) {
				try{
				URLUtil.download(
						urlManuales
								+ MessageUtil
										.unescape(AbosMessages.get().LANGUAGE)
								+ "/",
						"CIGED_ABCD_3.0_Manual_Usuario_Módulo_Seguridad.pdf",
						MessageUtil.unescape(AbosMessages.get().SECURITY_ADMINISTRATION_MODULE),
						ReportType.PDF);
				}catch(Exception e){
					RetroalimentationUtils.showErrorMessage(MessageUtil
							.unescape(AbosMessages.get().NOT_EXIST_FILE));
				}
			}
		});
		
		nomenclatorAdmin = new Link(all, SWT.NONE);
		FormDatas.attach(nomenclatorAdmin).atTopTo(securityAdmin, 20).atRight(0).atLeft(25);
		nomenclatorAdmin.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void handleEvent(Event arg0) {
				try{
				URLUtil.download(
						urlManuales
								+ MessageUtil
										.unescape(AbosMessages.get().LANGUAGE)
								+ "/",
						"CIGED_ABCD_3.0_Manual_Usuario_Módulo_Nomencladores.pdf",
						MessageUtil.unescape(AbosMessages.get().NOMENCLATOR_ADMINISTRATION_MODULE),
						ReportType.PDF);
			}catch(Exception e){
				RetroalimentationUtils.showErrorMessage(MessageUtil
						.unescape(AbosMessages.get().NOT_EXIST_FILE));
			}
			}
		});
	
		stadistic = new Link(all, SWT.NONE);
		FormDatas.attach(stadistic).atTopTo(nomenclatorAdmin, 20).atRight(0).atLeft(25);
		stadistic.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void handleEvent(Event arg0) {
				try {
					URLUtil.download(
							urlManuales
									+ MessageUtil
											.unescape(AbosMessages.get().LANGUAGE)
									+ "/",
							"CIGED_ABCD_3.0_Manual_Usuario_Módulo_Estadísticas.pdf",
							MessageUtil.unescape(AbosMessages.get().STADISTIC_MODULE),
							ReportType.PDF);
				} catch (Exception e) {
					RetroalimentationUtils.showErrorMessage(MessageUtil
							.unescape(AbosMessages.get().NOT_EXIST_FILE));
				}
			}
		});
		
		opac = new Link(all, SWT.NONE);
		FormDatas.attach(opac).atTopTo(stadistic, 20).atRight(0).atLeft(25);
		opac.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void handleEvent(Event arg0) {
				try {
					URLUtil.download(
							urlManuales
									+ MessageUtil
											.unescape(AbosMessages.get().LANGUAGE)
									+ "/",
							"CIGED_ABCD_3.0_Manual_Usuario_Módulo_OPAC.pdf",
							MessageUtil.unescape(AbosMessages.get().OPAC_MODULE),
							ReportType.PDF);
				} catch (Exception e) {
					RetroalimentationUtils.showErrorMessage(MessageUtil
							.unescape(AbosMessages.get().NOT_EXIST_FILE));
				}
			}
		});
		l10n();
		return parent;
	}
	
	@Override
	public void l10n() {
		adquisition.setText("1- " + "<a>"+MessageUtil.unescape(AbosMessages.get().ADQUISITION_MODULE)+"</a>");
		cataloguing.setText("2- " + "<a>"+MessageUtil.unescape(AbosMessages.get().CATALOGUING_MODULE)+"</a>");
		circulation.setText("3- " + "<a>"+MessageUtil.unescape(AbosMessages.get().CIRCULATION_MODULE)+"</a>");
		libraryAdmin.setText("4- " + "<a>"+MessageUtil.unescape(AbosMessages.get().LIBRARY_ADMINISTRATION_MODULE)+"</a>");
		securityAdmin.setText("5- " + "<a>"+MessageUtil.unescape(AbosMessages.get().SECURITY_ADMINISTRATION_MODULE)+"</a>");
		nomenclatorAdmin.setText("6- " + "<a>"+MessageUtil.unescape(AbosMessages.get().NOMENCLATOR_ADMINISTRATION_MODULE)+"</a>");
		stadistic.setText("7- " + "<a>"+MessageUtil.unescape(AbosMessages.get().STADISTIC_MODULE)+"</a>");
		opac.setText("8- " + "<a>"+MessageUtil.unescape(AbosMessages.get().OPAC_MODULE)+"</a>");
		
		adquisition.getParent().layout(true, true);
		adquisition.getParent().redraw();
		adquisition.getParent().update();
	}

}
