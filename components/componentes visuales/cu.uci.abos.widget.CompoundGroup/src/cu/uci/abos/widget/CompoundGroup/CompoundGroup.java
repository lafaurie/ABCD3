package cu.uci.abos.widget.CompoundGroup;

import java.util.List;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cu.uci.abos.util.api.FormDatas;

public class CompoundGroup {

	public static void printGroup(String src, Group group, String header, List<String> leftList, List<String> rigthList) {

		group.setText(header);
		int count = 20;
		int aux = 20;
		int max = 0;
		int position = 0;

		for (int i = 0; i < leftList.size(); i++) {
			if (max < leftList.get(i).length()) {
				max = leftList.get(i).length();
				position = i;
			}
		}
		Label leftLabel = new Label(group, SWT.RIGHT);
		Label rigthLabel = new Label(group, SWT.NORMAL | SWT.WRAP);
		if (src.equals("")) {

			for (int i = 0; i < leftList.size(); i++) {
				if (i == position) {
					count = count * i + 15;

					leftLabel.setText(leftList.get(i));
					FormDatas.attach(leftLabel).atTopTo(group, count).atLeftTo(group, 0);
					leftLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
					rigthLabel.setText(rigthList.get(i));
					FormDatas.attach(rigthLabel).atTopTo(group, count).atLeftTo(leftLabel, 5).withWidth(200);
					if (!(i == leftList.size() - 1)) {
						Label separator = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
						FormDatas.attach(separator).atTopTo(rigthLabel, 0).atLeft(1).atRight(1).withHeight(2);
					}

				}
			}

			for (int i = 0; i < leftList.size(); i++) {
				if (!(i == position)) {
					aux = aux * i + 15;
					Label leftLabel1 = new Label(group, SWT.RIGHT);
					leftLabel1.setText(leftList.get(i));
					FormDatas.attach(leftLabel1).atTopTo(group, aux).atRightTo(rigthLabel, 5);
					leftLabel1.setData(RWT.CUSTOM_VARIANT, "groupLeft");
					Label rigthLabel1 = new Label(group, SWT.NORMAL | SWT.WRAP);
					rigthLabel1.setText(rigthList.get(i));
					FormDatas.attach(rigthLabel1).atTopTo(group, aux).atLeftTo(leftLabel1, 5).withWidth(200);
					if (!(i == leftList.size() - 1)) {
						Label separator = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
						FormDatas.attach(separator).atTopTo(rigthLabel1, 0).atLeft(1).atRight(1).withHeight(2);
					}

				}
				aux = 20;

			}
		} else if (!(src.equals(""))) {

			Label pictureLabel = new Label(group, SWT.NONE);
			pictureLabel.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
			pictureLabel.setText("<img width='100' height='100' src='" + src + "'/> ");
			for (int i = 0; i < leftList.size(); i++) {
				if (i == position) {
					count = count * i + 15;

					leftLabel.setText(leftList.get(i));
					FormDatas.attach(leftLabel).atTopTo(group, count).atLeftTo(pictureLabel, 10);
					leftLabel.setData(RWT.CUSTOM_VARIANT, "groupLeft");
					rigthLabel.setText(rigthList.get(i));
					FormDatas.attach(rigthLabel).atTopTo(group, count).atLeftTo(leftLabel, 5).withWidth(200);
					if (!(i == leftList.size() - 1)) {
						Label separator = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
						FormDatas.attach(separator).atTopTo(rigthLabel, 0).atLeftTo(pictureLabel, 10).atRight(1).withHeight(2);
					}
				}
			}
			for (int i = 0; i < leftList.size(); i++) {
				if (!(i == position)) {
					aux = aux * i + 15;
					Label leftLabel1 = new Label(group, SWT.RIGHT);
					leftLabel1.setText(leftList.get(i));
					FormDatas.attach(leftLabel1).atTopTo(group, aux).atRightTo(rigthLabel, 5);
					leftLabel1.setData(RWT.CUSTOM_VARIANT, "groupLeft");
					Label rigthLabel1 = new Label(group, SWT.NORMAL | SWT.WRAP);
					rigthLabel1.setText(rigthList.get(i));
					FormDatas.attach(rigthLabel1).atTopTo(group, aux).atLeftTo(leftLabel1, 5).withWidth(200);
					if (!(i == leftList.size() - 1)) {
						Label separator = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
						FormDatas.attach(separator).atTopTo(rigthLabel1, 0).atLeftTo(pictureLabel, 10).atRight(1).withHeight(2);
					}
				}
				aux = 20;
			}

		}
		FormLayout formLayout = new FormLayout();
		group.setLayout(formLayout);
		group.redraw();

	}

}
