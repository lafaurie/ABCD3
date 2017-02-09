package cu.uci.abcd.administration.library.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import cu.uci.abcd.administration.library.communFragment.RegisterProviderFragment;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.ui.controller.ProviderViewController;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.Person;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Provider;
import cu.uci.abcd.management.security.IPersonService;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;
import cu.uci.abos.core.util.SecurityUtils;
import cu.uci.abos.core.util.ServiceProviderUtil;
import cu.uci.abos.core.util.UiUtils;
import cu.uci.abos.core.util.ValidatorUtils;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.BaseGridViewEntity;
import cu.uci.abos.core.widget.grid.CRUDTreeTable;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;
import cu.uci.abos.core.widget.grid.SecurityCRUDTreeTable;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ProviderUpdateArea extends BaseEditableArea {

	private ViewController controller;
	private RegisterProviderFragment updateProviderFragment;
	private Composite parentComposite;
	private Map<String, Control> controlsMaps;
	private Provider provider;
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	private Button saveBtn;
	private CRUDTreeTable tableProvider;
	private Person representant;
	private IPersonService personService;
	private int dimension;

	public ProviderUpdateArea(ViewController controller,
			CRUDTreeTable tableProvider, SecurityCRUDTreeTable tableProvider2) {
		this.controller = controller;
		this.tableProvider = tableProvider;
		this.personService = ServiceProviderUtil
				.getService(IPersonService.class);
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager manager) {
		dimension = parent.getParent().getParent().getBounds().width;
		addComposite(parent);
		Provider providerToUpdate = (Provider) entity.getRow();
		provider = ((ProviderViewController) controller)
				.getProviderById(providerToUpdate.getProviderID());
		representant = provider.getRepresentative();
		updateProviderFragment = new RegisterProviderFragment(provider,
				controller, representant, dimension, 2);
		parentComposite = (Composite) updateProviderFragment
				.createUIControl(parent);
		controlsMaps = updateProviderFragment.getControls();
		return parentComposite;
	}

	@Override
	public Composite createButtons(final Composite parent,
			IGridViewEntity entity, final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveBtn = new Button(parent, SWT.PUSH);
		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 2221836775265284914L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				//final Provider initProvider = getProvider();
				//String initProviderName = new String(getProvider().);
				//obj.origen=(Punto)obj.origen.clone();
				//Provider initProvider = (Provider) getProvider().clone();
				
				Provider providerSaved = null;
				//Composite msg = updateProviderFragment.getMsg();
				ValidatorUtils validatorUtils;
				if(updateProviderFragment.isInstitutional()){
					validatorUtils = updateProviderFragment.getValidatorIntitutional();
				}else{
					validatorUtils = updateProviderFragment.getValidatorPersonal();
				}
				if (validatorUtils.decorationFactory
						.isRequiredControlDecorationIsVisible()) {
					RetroalimentationUtils.showErrorShellMessage(
							//msg, 
							MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_ERROR_FIELD_REQUIRED));
				} else {
				if( validatorUtils.decorationFactory
						.AllControlDecorationsHide() ){
					
					Library library = (Library) SecurityUtils
							.getService().getPrincipal()
							.getByKey("library");

					Person represent = (updateProviderFragment.getRepresentant()!=null)?personService.findOnePerson(updateProviderFragment.getRepresentant().getPersonID()):null;
					
					List<Nomenclator> listProviderType = new ArrayList<Nomenclator>();
					if (((Button) controlsMaps.get("canjeCheckButton"))
							.getSelection()) {
						Nomenclator type = ((ProviderViewController) controller)
								.getAllManagementLibraryViewController()
								.getLibraryService()
								.findNomenclatorById( 
										Nomenclator.PROVIDER_TYPE_CANGE);
						listProviderType.add(type);
					}

					if (((Button) controlsMaps
							.get("commercialCheckButton"))
							.getSelection()) {
						Nomenclator type = ((ProviderViewController) controller)
								.getAllManagementLibraryViewController()
								.getLibraryService()
								.findNomenclatorById( 
										Nomenclator.PROVIDER_TYPE_COMMERCIAL);
						listProviderType.add(type);
					}

					if (((Button) controlsMaps.get("donateCheckButton"))
							.getSelection()) {
						Nomenclator type = ((ProviderViewController) controller)
								.getAllManagementLibraryViewController()
								.getLibraryService()
								.findNomenclatorById( 
										Nomenclator.PROVIDER_TYPE_DONATION);
						listProviderType.add(type);
					}
					
					//Provider provider = new Provider();
					provider.setRepresentative(represent);
					provider.setLibrary(library);
					boolean flag = true;
					if (listProviderType.size() > 0) {
						provider.setTypes(listProviderType);
						//boolean flag = false;
						if(updateProviderFragment.isInstitutional()){
							if( represent==null ){
								RetroalimentationUtils.showErrorShellMessage(
										//msg, 
										MessageUtil
										.unescape(AbosMessages.get().LABEL_INDICATE_PERSON_AS_REPRESENTANT));
								flag = false;
							}else{
							
							String providerName = ((Text) controlsMaps
									.get("providerNameText")).getText().replaceAll(" +", " ").trim();
							Provider existIntitutionalProviderWithThisName = ((ProviderViewController) controller).findProviderByLibraryAndTypeAndName(library.getLibraryID(), true, providerName);
							
							if( existIntitutionalProviderWithThisName==null || provider.getProviderID()==existIntitutionalProviderWithThisName.getProviderID()  ){
								
							String rif = ((Text) controlsMaps.get("rifText"))
									.getText().replaceAll(" +", " ").trim();
							String nit = ((Text) controlsMaps.get("nitText"))
									.getText().replaceAll(" +", " ").trim();
							
							String firstPhoneNumber = ((Text) controlsMaps.get("firstPhoneNumberText")).getText();
							String secondPhoneNumber = ((Text) controlsMaps.get("secondPhoneNumberText")).getText();
							String fax = ((Text) controlsMaps.get("faxText")).getText();
							String email = ((Text) controlsMaps.get("emailText")).getText();
							String webPage = ((Text) controlsMaps.get("webPageText")).getText();
							String addrees = ((Text) controlsMaps.get("addreesText")).getText().replaceAll(" +", " ").trim();
							Nomenclator country = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps
									.get("countryCombo"));
							Nomenclator state = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps
									.get("providerStateCombo"));
							
							provider.setProviderName(providerName);
							provider.setNit(nit);
							provider.setRif(rif);
							
							provider.setFirstPhoneNumber(firstPhoneNumber);
							provider.setSecondPhoneNumber(secondPhoneNumber);
							provider.setFax(fax);
							provider.setEmail(email);
							provider.setWebPage(webPage);
							provider.setAddress(addrees);
							
							provider.setProviderState(state);
							provider.setCountry(country);
							provider.setIntitutional(true);
							provider.setPerson(null);
							
							providerSaved = ((ProviderViewController) controller)
									.saveProvider(provider);
							
							//tableProvider.getPaginator().goToFirstPage();
							provider = null;
							//tableProvider.destroyEditableArea();
							
	                        //manager.add(new BaseGridViewEntity<Provider>(providerSaved));
							//manager.refresh();
							
							//Composite viewSmg = ((ProviderViewArea)tableProvider.getActiveArea()).getMsg();
							
							//RetroalimentationUtils.showInformationMessage(viewSmg, MessageUtil
								//	.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA));
							
									//flag = true;
							}else{
								RetroalimentationUtils.showErrorShellMessage(
										//msg,
										MessageUtil.unescape(AbosMessages.get().ELEMENT_EXIST));
							}
						}
							
							
						}else{
							
							if( updateProviderFragment.getRepresentantPerson()==null ){
								RetroalimentationUtils.showErrorShellMessage(
										//msg, 
										MessageUtil
										.unescape(AbosMessages.get().LABEL_INDICATE_PERSON));
								flag = false;
							}else{
								Person person = updateProviderFragment.getRepresentantPerson();
								Provider existPersonProviderWithThisName = ((ProviderViewController) controller).findProviderByLibraryAndTypeAndPerson(library.getLibraryID(), false, person.getPersonID());
								if (existPersonProviderWithThisName == null ||  provider.getProviderID()==existPersonProviderWithThisName.getProviderID()  ) {
								
								String firstPhoneNumber = ((Text) controlsMaps.get("firstPhoneNumberText1")).getText();
								String secondPhoneNumber = ((Text) controlsMaps.get("secondPhoneNumberText1")).getText();
								String fax = ((Text) controlsMaps.get("faxText1")).getText();
								String email = ((Text) controlsMaps.get("emailText1")).getText();
								String webPage = ((Text) controlsMaps.get("webPageText1")).getText();
								String addrees = ((Text) controlsMaps.get("addreesText1")).getText().replaceAll(" +", " ").trim();
								Nomenclator country = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps
										.get("countryCombo1"));
								Nomenclator state = (Nomenclator) UiUtils.getSelected((Combo) controlsMaps
										.get("providerStateCombo1"));
								
								provider.setProviderName(null);
								provider.setNit(null);
								provider.setRif(null);
								
								provider.setFirstPhoneNumber(firstPhoneNumber);
								provider.setSecondPhoneNumber(secondPhoneNumber);
								provider.setFax(fax);
								provider.setEmail(email);
								provider.setWebPage(webPage);
								provider.setAddress(addrees);
								
								provider.setProviderState(state);
								provider.setCountry(country);
								provider.setIntitutional(false);
								provider.setPerson(person);
								provider.setProviderName(person.getFullName());
								
								providerSaved = ((ProviderViewController) controller)
										.saveProvider(provider);
								//tableProvider.getPaginator().goToFirstPage();
								provider = null;
								//tableProvider.destroyEditableArea();
								
		                        //manager.add(new BaseGridViewEntity<Provider>(providerSaved));
								//manager.refresh();
								
								//RetroalimentationUtils.showInformationMessage(MessageUtil
										//.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA));
								//flag = true;
							}else{
								RetroalimentationUtils.showErrorShellMessage(
										//msg,
										MessageUtil.unescape(AbosMessages.get().ELEMENT_EXIST));
							}
							}
						}
						
						
						
						
						if (providerSaved!=null) {
							String msgShow = MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_UPDATE_DATA);
							/*
							List<Provider> allProviders = ((ProviderViewController) controller)
									.findProviderByLibrary(library
											.getLibraryID());
							String pickList = allProviders.toString();
							pickList = pickList.replace("[", "");
							pickList = pickList.replace("]", "");
							String databaseName = "Registro_De_Adquisicion";
							String isisDefHome = library.getIsisDefHome();
							String workSheetNameDonation = "Donación";
							String workSheetNameCompra = "Compra";
							String workSheetNameDefault = "Default worksheet";
							String workSheetNameCanje = "Canje";
							try {
								
								WorksheetDef workSheetDonation = ((ProviderViewController) controller).getAllManagementLibraryViewController().getLibraryService()
										.getWorksheet(
												workSheetNameDonation,
												databaseName, isisDefHome);
								WorksheetField fieldProviderDonation = workSheetDonation
										.getFieldByTag(25);
								fieldProviderDonation.setPickList(pickList);
								((ProviderViewController) controller).getAllManagementLibraryViewController().getLibraryService()
										.updateWorksheet(workSheetDonation);

								
								WorksheetDef workSheetCompra = ((ProviderViewController) controller).getAllManagementLibraryViewController().getLibraryService()
										.getWorksheet(workSheetNameCompra,
												databaseName, isisDefHome);
								WorksheetField fieldProviderCompra = workSheetCompra
										.getFieldByTag(24);
								fieldProviderCompra.setPickList(pickList);
								((ProviderViewController) controller).getAllManagementLibraryViewController().getLibraryService()
										.updateWorksheet(workSheetCompra);

								
								WorksheetDef workSheetDefault = ((ProviderViewController) controller).getAllManagementLibraryViewController().getLibraryService()
										.getWorksheet(workSheetNameDefault,
												databaseName, isisDefHome);
								WorksheetField fieldProviderDefault = workSheetDefault
										.getFieldByTag(24);
								fieldProviderDefault.setPickList(pickList);
								((ProviderViewController) controller).getAllManagementLibraryViewController().getLibraryService()
										.updateWorksheet(workSheetDefault);

								
								WorksheetDef workSheetCanje = ((ProviderViewController) controller).getAllManagementLibraryViewController().getLibraryService()
										.getWorksheet(workSheetNameCanje,
												databaseName, isisDefHome);
								WorksheetField fieldProviderCanje = workSheetCanje
										.getFieldByTag(31);
								fieldProviderCanje.setPickList(pickList);
								((ProviderViewController) controller).getAllManagementLibraryViewController().getLibraryService()
										.updateWorksheet(workSheetCanje);
								
								manager.add(new BaseGridViewEntity<Provider>(providerSaved));
								manager.refresh();
								
								Composite viewSmg = ((ProviderViewArea)tableProvider.getActiveArea()).getMsg();
								RetroalimentationUtils.showInformationMessage(viewSmg, msgShow);
								
							} catch (Exception e1) {
								
								((ProviderViewController) controller).saveProvider(initProvider);
								
								msgShow = "RESTORE";
								RetroalimentationUtils
									.showErrorMessage(msg, msgShow);
							}
							*/
							//Composite viewSmg = ((ProviderViewArea)tableProvider.getActiveArea()).getMsg();
							//RetroalimentationUtils.showInformationMessage(viewSmg, msgShow);
							
							manager.save(new BaseGridViewEntity<Provider>(providerSaved));
							manager.refresh();
							
							Composite viewSmg = ((ProviderViewArea)tableProvider.getActiveArea()).getMsg();
							RetroalimentationUtils.showInformationMessage(viewSmg, msgShow);
							
						}else{
							if(flag==false  && !updateProviderFragment.isInstitutional() ){
							RetroalimentationUtils.showErrorShellMessage(
									//msg, 
									MessageUtil
									.unescape(AbosMessages.get().LABEL_INDICATE_PERSON));
							}else{
							if(flag==true && !updateProviderFragment.isInstitutional()){
							String msgShow = cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NOT_SAVED_ITEM;
							
							RetroalimentationUtils
								.showErrorShellMessage(
										//msg, 
										msgShow);
							}
							}
						}
						
						
						
						
					} else {
						RetroalimentationUtils.showErrorShellMessage(
								//msg, 
								MessageUtil
								.unescape(AbosMessages.get().SELECT_A_PROVIDER));
					}
					
				}else{
					RetroalimentationUtils.showErrorShellMessage(
							//msg, 
							MessageUtil
							.unescape(cu.uci.abos.core.l10n.AbosMessages
									.get().MSG_ERROR_INCORRECT_DATA));
				}
				}

			
			}
		});
		return parent;

	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public boolean closable() {
		return true;
	}

	@Override
	public void l10n() {
		saveBtn.setText(MessageUtil.unescape(AbosMessages.get().BUTTON_ACEPT));
		saveBtn.getParent().layout(true, true);
		saveBtn.getParent().redraw();
		saveBtn.getParent().update();
		updateProviderFragment.l10n();
	}
	
	@Override
	public String getID() {
		return "updateProviderID";
	}

}
