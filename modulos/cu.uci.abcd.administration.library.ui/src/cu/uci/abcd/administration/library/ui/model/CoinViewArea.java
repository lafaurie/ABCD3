package cu.uci.abcd.administration.library.ui.model;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.domain.management.library.Coin;
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

public class CoinViewArea extends BaseEditableArea {
	private Label header;
	private Group dataGroup;
	private String titleGroup;
	private List<String> leftList;
	private List<Control> grupControls;
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
		Coin coin = (Coin) entity.getRow();
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
		dataGroup = new Group(topGroup, SWT.NORMAL);
		dataGroup.setData(RWT.CUSTOM_VARIANT, "gray_background");
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_COIN_DATA);
		add(dataGroup);
		leftList = new LinkedList<>();
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFIER_COIN));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_EXCHANGE_RATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_LAST_UPDATE));
		List<String> values = new LinkedList<>();
		values.add((coin.getCoinName() != null) ? coin.getCoinName() : "-");
		values.add((coin.getCoinType() != null) ? coin.getCoinType().getNomenclatorName() : "-");
		values.add((coin.getExchangeRate() != null) ? coin.getExchangeRate() : "-");
		values.add(new SimpleDateFormat("dd-MM-yyyy").format(coin
				.getUpdatedDate()));

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
		header.setText(MessageUtil.unescape(AbosMessages.get().VIEW_COIN));
		titleGroup = MessageUtil.unescape(AbosMessages.get().LABEL_COIN_DATA);
		leftList.clear();
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_NAME));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_IDENTIFIER_COIN));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_EXCHANGE_RATE));
		leftList.add(MessageUtil.unescape(AbosMessages.get().LABEL_LAST_UPDATE));
		dataGroup.setText(titleGroup);
		CompoundGroup.l10n(grupControls, leftList);
	}
	
	@Override
	public String getID() {
		return "viewCoinID";
	}

}
