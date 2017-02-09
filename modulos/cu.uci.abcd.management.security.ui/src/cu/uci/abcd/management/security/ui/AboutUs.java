package cu.uci.abcd.management.security.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import cu.uci.abcd.management.security.l10n.AbosMessages;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.AbosImageUtil;
import cu.uci.abos.core.util.FormDatas;
import cu.uci.abos.core.util.MessageUtil;

public class AboutUs extends ContributorPage implements Contributor{
	
	Label imageLabel;
	
	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().ABOUT_US);
	}

	@Override
	public String getID() {
		return "aboutUsID";
	}
	@Override
	public Control createUIControl(Composite parent) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		Composite all = new Composite(parent,SWT.NONE);
		FormLayout form = new FormLayout();
		all.setData(RWT.CUSTOM_VARIANT, "gray_background");
		all.setLayout(form);
		FormDatas.attach(all).atTopTo(parent, 15).atRight(0).atLeft(0).atBottom(0);
		imageLabel = new Label(all, SWT.NONE);
		FormDatas.attach(imageLabel).atTopTo(all, 0);
		l10n();
		return parent;
	}
	
	@Override
	public void l10n() {
		imageLabel.setImage(new Image(Display.getDefault(), AbosImageUtil
				.loadImage(null, Display.getCurrent(),
						"abcdconfig/resources/acercaDe-"+MessageUtil.unescape(AbosMessages.get().LANGUAGE)+".png", false).getImageData()
			.scaledTo(700, 403)));
	}
	
	

}
