package cu.uci.abos.core.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.TreeItem;

public class TooltipUtils {


    final static int TOOLTIP_HIDE_DELAY = 300;   // 0.3s
    final static int TOOLTIP_SHOW_DELAY = 1000;  // 1.0s

    public static void tooltip(final Control c, String tooltipText, String tooltipMessage) {

        final ToolTip tip = new ToolTip(c.getShell(), SWT.BALLOON);
        tip.setText(tooltipText);
        tip.setMessage(tooltipMessage);
        tip.setAutoHide(false);

        c.addListener(SWT.MouseEnter, new Listener() {
			private static final long serialVersionUID = -8718154176823261020L;

			public void handleEvent(Event event) {
                tip.getDisplay().timerExec(TOOLTIP_SHOW_DELAY, new Runnable() {
                    public void run() {
                        tip.setVisible(true);
                    }
                });             
            }
        });

        c.addListener(SWT.MouseExit, new Listener() {
			private static final long serialVersionUID = -5146956130694456096L;

			public void handleEvent(Event event) {
                tip.getDisplay().timerExec(TOOLTIP_HIDE_DELAY, new Runnable() {
                    public void run() {
                        tip.setVisible(false);
                    }
                });
            }
        });
    }
    
    public static void tooltip(final TreeItem c, String tooltipText, String tooltipMessage) {

        final ToolTip tip = new ToolTip(c.getDisplay().getActiveShell(), SWT.BALLOON);
        tip.setText(tooltipText);
        tip.setMessage(tooltipMessage);
        tip.setAutoHide(false);

        c.addListener(SWT.MouseEnter, new Listener() {
			private static final long serialVersionUID = -8718154176823261020L;

			public void handleEvent(Event event) {
                tip.getDisplay().timerExec(TOOLTIP_SHOW_DELAY, new Runnable() {
                    public void run() {
                        tip.setVisible(true);
                    }
                });             
            }
        });

        c.addListener(SWT.MouseExit, new Listener() {
			private static final long serialVersionUID = -5146956130694456096L;

			public void handleEvent(Event event) {
                tip.getDisplay().timerExec(TOOLTIP_HIDE_DELAY, new Runnable() {
                    public void run() {
                        tip.setVisible(false);
                    }
                });
            }
        });
    }

}

