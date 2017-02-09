package cu.uci.abcd.administration.library.ui.model;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.domain.management.library.Provider;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.widget.compoundgroup.CompoundGroup;
import cu.uci.abos.core.widget.grid.BaseEditableArea;
import cu.uci.abos.core.widget.grid.IGridViewEntity;
import cu.uci.abos.core.widget.grid.IVisualEntityManager;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ProviderViewArea extends BaseEditableArea {

	private Label header;
	private Group dataGroup;
	private Group dataPersonGroup;
	private String titleGroup;
	private String titlePersonGroup;
	private List<String> leftList;
	private List<String> leftPersonList;
	private List<Control> grupControls;
	private List<Control> personGroupControls;
	private Provider provider;
	private Composite msg;
	public Composite getMsg() {
		return msg;
	}
	@Override
	public Composite createButtons(Composite parent, IGridViewEntity arg1,
			IVisualEntityManager arg2) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		return parent;
	}

	@Override
	public Composite createUI(Composite parent, IGridViewEntity entity,
			IVisualEntityManager manager) {
		provider = (Provider) entity.getRow();
		setDimension(parent.getParent().getParent().getBounds().width);
		addComposite(parent);
		Composite topGroup = new Composite(parent, SWT.NORMAL);
		topGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		addComposite(topGroup);
		
		msg  = new Composite(topGroup, SWT.NONE);
		msg.setLayout(new FormLayout());
		msg.setData(RWT.CUSTOM_VARIANT, "gray_background");
		FormDatas.attach(msg).atTopTo(topGroup).withWidth(320).withHeight(50).atRight(0);
		
		header = new Label(topGroup, SWT.NORMAL);
		addHeader(header);
		Label separator = new Label(topGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		addSeparator(separator);
		
		if(!provider.isIntitutional()){
			dataPersonGroup = new Group(topGroup, SWT.NORMAL);
			dataPersonGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
			titlePersonGroup = MessageUtil
					.unescape(AbosMessages.get().LABEL_DATA_PERSON);
			add(dataPersonGroup);
			
			leftPersonList = new LinkedList<>();
			leftPersonList.add(MessageUtil.unescape(AbosMessages.get().NAME_AND_SURNAME));
			leftPersonList.add(MessageUtil.unescape(AbosMessages.get().BIRTHDAY));
			leftPersonList.add(MessageUtil.unescape(AbosMessages.get().AGE));
			leftPersonList.add(MessageUtil.unescape(AbosMessages.get().SEX));
			leftPersonList.add(MessageUtil.unescape(AbosMessages.get().ID_CARNE));
			leftPersonList.add(MessageUtil.unescape(AbosMessages.get().EMAIL));
			leftPersonList.add(MessageUtil.unescape(AbosMessages.get().ADDRESS));
			
			
			List<String> values = new LinkedList<>();
			values.add(provider.getPerson().getFullName());
			values.add(new SimpleDateFormat("dd-MM-yyyy").format(provider.getPerson().getBirthDate()));
			values.add(provider.getPerson().getAge());
			values.add(provider.getPerson().getSex().getNomenclatorName());
			values.add(provider.getPerson().getDNI());
			values.add(provider.getPerson().getEmailAddress() != null ? provider.getPerson().getEmailAddress() : "");
			values.add(provider.getPerson().getAddress() != null ? provider.getPerson().getAddress() : "-");
			
			Image image = provider.getPerson().getPhoto().getImage();
			
			
			personGroupControls = CompoundGroup.printGroup(image, dataPersonGroup, titlePersonGroup,
					leftPersonList, values);
		}else{
			
		}
		
		dataGroup = new Group(topGroup, SWT.NORMAL);
		dataGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		titleGroup = MessageUtil
				.unescape(AbosMessages.get().LABEL_DATA_PROVIDER);
		add(dataGroup);
		leftList = new LinkedList<>();
		leftList.add(MessageUtil.unescape(AbosMessages.get().CLASSIFICATION));
		if(provider.isIntitutional()){
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_RIF));
		    leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NIT));
		}
		
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_SERVICE));
		
		leftList.add(MessageUtil.unescape(AbosMessages.get().FIRST_PHONE_NUMBER));
		leftList.add(MessageUtil.unescape(AbosMessages.get().SECOND_PHONE_NUMBER));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_EMAIL));
		leftList.add(MessageUtil.unescape(AbosMessages.get().FAX));
		leftList.add(MessageUtil.unescape(AbosMessages.get().WEB_PAGE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ADDREES));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_COUNTRY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_REPRESENTANT));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		
		List<String> values = new LinkedList<>();
		values.add((provider.isIntitutional())?MessageUtil.unescape(AbosMessages.get().INTITUTIONAL):MessageUtil.unescape(AbosMessages.get().PERSON));
		
		
		
		
		if(provider.isIntitutional()){
		values.add((provider.getProviderName()!=null)?provider.getProviderName():"-");
		values.add((provider.getRif() != null) ? provider.getRif() : "-");
		values.add((provider.getNit() != null) ? provider.getNit(): "-");
		}
		
		values.add(provider.getService());
		values.add((provider.getFirstPhoneNumber() != null) ? provider.getFirstPhoneNumber() : "-");
		values.add((provider.getSecondPhoneNumber() != null) ? provider.getSecondPhoneNumber() : "-");
		values.add((provider.getEmail() != null) ? provider.getEmail() : "-");
		values.add((provider.getFax() != null) ? provider.getFax() : "-");
		values.add((provider.getWebPage() != null) ? provider.getWebPage() : "-");
		values.add((provider.getAddress()!= null) ? provider.getAddress() : "-");
		values.add((provider.getCountry()!= null) ? provider.getCountry().getNomenclatorName() : "-");
		values.add((provider.getRepresentative()!=null)?provider.getRepresentative().getFullName():"-");
		values.add(provider.getProviderState().getNomenclatorName());
		
		grupControls = CompoundGroup.printGroup(dataGroup, titleGroup,
				leftList, values);
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
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_PROVIDER));
		
		if(!provider.isIntitutional()){
			titlePersonGroup = MessageUtil
					.unescape(AbosMessages.get().LABEL_DATA_PERSON);
			leftPersonList.clear();
			
			leftPersonList.add(MessageUtil.unescape(AbosMessages.get().NAME_AND_SURNAME));
			leftPersonList.add(MessageUtil.unescape(AbosMessages.get().BIRTHDAY));
			leftPersonList.add(MessageUtil.unescape(AbosMessages.get().AGE));
			leftPersonList.add(MessageUtil.unescape(AbosMessages.get().SEX));
			leftPersonList.add(MessageUtil.unescape(AbosMessages.get().ID_CARNE));
			leftPersonList.add(MessageUtil.unescape(AbosMessages.get().EMAIL));
			leftPersonList.add(MessageUtil.unescape(AbosMessages.get().ADDRESS));
			
			
			dataPersonGroup.setText(titlePersonGroup);
			CompoundGroup.l10n(personGroupControls, leftPersonList);
			
		}
		
		titleGroup = MessageUtil
				.unescape(AbosMessages.get().LABEL_DATA_PROVIDER);
		leftList.clear();
		leftList.add(MessageUtil.unescape(AbosMessages.get().CLASSIFICATION));
		if(provider.isIntitutional()){
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
			leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_RIF));
		    leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NIT));
		}
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_SERVICE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().FIRST_PHONE_NUMBER));
		leftList.add(MessageUtil.unescape(AbosMessages.get().SECOND_PHONE_NUMBER));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_EMAIL));
		leftList.add(MessageUtil.unescape(AbosMessages.get().FAX));
		leftList.add(MessageUtil.unescape(AbosMessages.get().WEB_PAGE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_ADDREES));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_COUNTRY));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_REPRESENTANT));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_STATE));
		dataGroup.setText(titleGroup);
		CompoundGroup.l10n(grupControls, leftList);
	}

	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}

	public ProviderViewArea(ViewController controller) {
		this.controller = controller;
	}
	
	@Override
	public String getID() {
		return "viewProviderID";
	}

}
