package cu.uci.abos.core.widget.compoundgroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abos.core.util.AbosImageUtil;
import cu.uci.abos.core.util.FormDatas;

public class CompoundGroup {

	public static List<Control> printGroup(Image image, Group group, String header, List<String> denomination, List<String> values, int widthLeft) {
		group.setText(header);
		Image temp = image;
		if (temp == null) {
			temp = new Image(Display.getDefault(), AbosImageUtil.loadImage(null, Display.getCurrent(), "abcdconfig/resources/photo.png", false).getImageData().scaledTo(100, 100));
			return paintLabels(group, denomination, values, paintImage(temp, group), widthLeft);
		} else {
			return paintLabels(group, denomination, values,paintImage(temp, group), widthLeft);
		}
	}
	
	public static List<Control> printGroup(Image image, Group group, String header, List<String> denomination, List<String> values) {
		return printGroup(image, group, header, denomination, values, 200);
	}

	public static List<Control> printGroup(Group group, String header, List<String> denomination, List<String> values, int widthLeft) {
		group.setText(header);
		return paintLabels(group, denomination, values, null, widthLeft);
	}
	
	public static List<Control> printGroup(Group group, String header, List<String> denomination, List<String> values) {
		return printGroup(group, header, denomination, values, 200);
		//group.setText(header);
		//return paintLabels(group, denomination, values, null);
	}

	private static Image paintImage(Image image, Group group) {
		Image picture = new Image(Display.getDefault(), image.getImageData());
		picture.getImageData().scaledTo(100, 100);
		return picture;
	}

	private static synchronized List<Control> paintLabels(Group group, List<String> denominations, List<String> values, Image image, int widthLeft) {
		List<Control> controls = new ArrayList<Control>();
		group.setLayout(new FormLayout());
		int space = 20;
		Iterator<String> denominationIt = denominations.iterator();
		Iterator<String> valuesIt = values.iterator();
		Label topLeft = new Label(group, SWT.NORMAL | SWT.WRAP);
		topLeft.setText(denominationIt.next());
		topLeft.setAlignment(SWT.RIGHT);
		Label labImage= new Label(group, SWT.NONE);
		if (image != null){
			labImage= new Label(group, SWT.NONE);
			labImage.setImage(image);
			FormDatas.attach(topLeft).atTopTo(group, space).atLeftTo(labImage).withWidth(widthLeft);
		}
		else
			FormDatas.attach(topLeft).atTopTo(group, space).atLeftTo(group, 0).withWidth(widthLeft);
		topLeft.setData(RWT.CUSTOM_VARIANT, "groupLeft");
		controls.add(topLeft);

		Label topRigth = new Label(group, SWT.NORMAL | SWT.WRAP);
		topRigth.setText(valuesIt.next());
		FormDatas.attach(topRigth).atTopTo(group, space).atLeftTo(topLeft, 5).withWidth(widthLeft);
		Label separator = null;
		if (denominations.size() > 1) {
			if (image != null) {
				separator = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
				FormDatas.attach(separator).atTopTo(topRigth, 0).atLeftTo(labImage, 2).atRight(1).withHeight(2);
			} else {
				separator = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
				FormDatas.attach(separator).atTopTo(topRigth, 0).atLeft(1).atRight(1).withHeight(2);
			}
		}

		space = 8;
		for (int i = 1; i < denominations.size(); i++) {
			Label leftLabel = new Label(group, SWT.NORMAL | SWT.WRAP);
			leftLabel.setText(denominationIt.next() + " : ");
			leftLabel.setAlignment(SWT.RIGHT);
			if (image != null)
				FormDatas.attach(leftLabel).atTopTo(separator, space).atLeftTo(labImage).withWidth(widthLeft);
			else
				FormDatas.attach(leftLabel).atTopTo(separator, space).atLeftTo(group, 0).withWidth(widthLeft);

			leftLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
			controls.add(leftLabel);

			Label rigthLabel = new Label(group, SWT.NORMAL | SWT.WRAP);
			rigthLabel.setText(valuesIt.next());
			FormDatas.attach(rigthLabel).atTopTo(separator, space).atLeftTo(leftLabel, 5).atRight(1);

			if (!(i == denominations.size() - 1)) {
				if (image != null && i < 3) {
					separator = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
					FormDatas.attach(separator).atTopTo(rigthLabel, 0).atLeftTo(labImage, 2).atRight(1).withHeight(2);
				} else {
					separator = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
					FormDatas.attach(separator).atTopTo(rigthLabel, 0).atLeft(1).atRight(1).withHeight(2);
				}

			}
			topLeft = leftLabel;
			topRigth = rigthLabel;
		}

		group.redraw();
		return controls;
	}

	public static synchronized void l10n(List<Control> controls, List<String> denominations) {
		Iterator<String> denomitationsit = denominations.iterator();
		for (Iterator<?> control = controls.iterator(); control.hasNext();) {
			Label label=(Label) control.next();
			if (label!=null&&!label.isDisposed()) {
				label.setText(denomitationsit.next() + " : ");
			}
		}
	}
}
