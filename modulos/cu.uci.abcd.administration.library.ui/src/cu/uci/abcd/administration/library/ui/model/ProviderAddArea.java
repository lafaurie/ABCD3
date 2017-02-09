package cu.uci.abcd.administration.library.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ProviderAddArea extends BaseEditableArea {

	private ViewController controller;
	private RegisterProviderFragment registerProviderFragment;
	private Composite parentComposite;
	private Map<String, Control> controlsMaps;
	private CRUDTreeTable tableProvider;
	private Button saveBtn;
	private int dimension;
	private IPersonService personService;

	public ProviderAddArea(ViewController controller,
			CRUDTreeTable tableProvider) {
		this.controller = controller;
		this.tableProvider = tableProvider;
		this.personService = ServiceProviderUtil
				.getService(IPersonService.class);
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager manager) {
		dimension = parent.getParent().getParent().getBounds().width;
		registerProviderFragment = new RegisterProviderFragment(null, controller,
				dimension, 1);
		parentComposite = (Composite) registerProviderFragment
				.createUIControl(parent);
		
		//registerProviderFragment.ajustRezise(((Composite) controlsMaps.get("personComposite")), 0);
		//refresh(((Composite) controlsMaps.get("intitutionalComposite")).getParent().getShell());
		
		
		controlsMaps = registerProviderFragment.getControls();
		
		return parentComposite;
	}

	@Override
	public Composite createButtons(Composite parent, IGridViewEntity entity,
			final IVisualEntityManager manager) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		saveBtn = new Button(parent, SWT.PUSH);
		saveBtn.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -7204282042997560772L;
			@Override
			public void widgetSelected(SelectionEvent e) {
				Library library = (Library) SecurityUtils
						.getService().getPrincipal()
						.getByKey("library");
				Provider providerSaved = null;
				
				//Composite msg = registerProviderFragment.getMsg();
				ValidatorUtils validatorUtils;
				
				if(registerProviderFragment.isInstitutional()){
					validatorUtils = registerProviderFragment.getValidatorIntitutional();
				}else{
					validatorUtils = registerProviderFragment.getValidatorPersonal();
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
					
					

					Person represent = (registerProviderFragment.getRepresentant()!=null)?personService.findOnePerson(registerProviderFragment.getRepresentant().getPersonID()):null;
					
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
					
					Provider provider = new Provider();
					provider.setRepresentative(represent);
					provider.setLibrary(library);
					
					if (listProviderType.size() > 0) {
						provider.setTypes(listProviderType);
						//boolean flag = false;
						
						if(registerProviderFragment.isInstitutional()){
							if( represent==null ){
									RetroalimentationUtils.showErrorShellMessage(
											//msg,
											MessageUtil.unescape(AbosMessages
													.get().LABEL_INDICATE_PERSON_AS_REPRESENTANT));
							}else{
								String providerName = ((Text) controlsMaps
										.get("providerNameText")).getText().replaceAll(" +", " ").trim();
							Provider existIntitutionalProviderWithThisName = ((ProviderViewController) controller).findProviderByLibraryAndTypeAndName(library.getLibraryID(), true, providerName);
							if (existIntitutionalProviderWithThisName == null) {
							
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
							
							providerSaved = ((ProviderViewController) controller)
									.saveProvider(provider);
							//tableProvider.getPaginator().goToFirstPage();
							provider = null;
							//tableProvider.destroyEditableArea();
							
							if (providerSaved!=null) {
								String msgShow = MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT);
									manager.add(new BaseGridViewEntity<Provider>(providerSaved));
									manager.refresh();
									Composite viewSmg = ((ProviderViewArea)tableProvider.getActiveArea()).getMsg();
									RetroalimentationUtils.showInformationMessage(viewSmg, msgShow);
									providerSaved = null;
									tableProvider.getPaginator().goToFirstPage();
							}else{
								String msgShow = cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NOT_SAVED_ITEM;
								RetroalimentationUtils
									.showErrorShellMessage(
											msgShow);
							}
							
	                        
							
							
							
							
							
							//flag = true;
							}else{
								RetroalimentationUtils.showErrorShellMessage(
										//msg,
										MessageUtil.unescape(AbosMessages.get().ELEMENT_EXIST));	
							}	
							
						}
							
							
						}else{
							if( registerProviderFragment.getRepresentantPerson()==null ){
								RetroalimentationUtils.showErrorShellMessage(
										//msg,
										MessageUtil.unescape(AbosMessages
												.get().LABEL_INDICATE_PERSON));
							}else{
								
								Person person = registerProviderFragment.getRepresentantPerson();
								
								Provider existPersonProviderWithThisName = ((ProviderViewController) controller).findProviderByLibraryAndTypeAndPerson(library.getLibraryID(), false, person.getPersonID());
								if (existPersonProviderWithThisName == null) {
									
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
								
								if (providerSaved!=null) {
									String msgShow = MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT);
										manager.add(new BaseGridViewEntity<Provider>(providerSaved));
										manager.refresh();
										Composite viewSmg = ((ProviderViewArea)tableProvider.getActiveArea()).getMsg();
										RetroalimentationUtils.showInformationMessage(viewSmg, msgShow);
										providerSaved = null;
										tableProvider.getPaginator().goToFirstPage();
								}else{
									String msgShow = cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_NOT_SAVED_ITEM;
									RetroalimentationUtils
										.showErrorShellMessage(
												msgShow);
								}
								//tableProvider.destroyEditableArea();
								
		                       // manager.add(new BaseGridViewEntity<Provider>(providerSaved));
								//manager.refresh();
								
								//RetroalimentationUtils.showInformationMessage(viewSmg, MessageUtil
									//	.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));
								//flag = true;
							}else{
								RetroalimentationUtils.showErrorShellMessage(
										//msg,
										MessageUtil.unescape(AbosMessages.get().ELEMENT_EXIST));
							}
							}
						}
						
						
						
							
							
					} else {
						RetroalimentationUtils.showErrorShellMessage(
								//msg, 
								MessageUtil
								.unescape(AbosMessages
										.get().SELECT_A_PROVIDER));
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
		//saveBtn.getShell().setDefaultButton(saveBtn);
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
		registerProviderFragment.l10n();
	}
	
	@Override
	public String getID() {
		return "addProviderID";
	}

}
