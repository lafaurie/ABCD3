package cu.uci.abos.core.util;

import org.eclipse.rap.rwt.service.ServerPushSession;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import cu.uci.abos.core.l10n.AbosMessages;
import cu.uci.abos.core.messages.InformationMessage;

public class RetroalimentationUtils {

	public static void showErrorMessage(String message) {
		showErrorShellMessage(message);
	}

	public static void showInformationMessage(final Composite parent, String message) {
		showInformationShellMessage(message);
		/*
		final InformationMessage messageBox = new InformationMessage(parent, MessageUtil.unescape(message),Display.getDefault().getSystemImage(SWT.ICON_INFORMATION));
		parent.pack(true);
		parent.setFocus();
		messageBox.setFocus();
		final ServerPushSession pushSession = new ServerPushSession();
		Runnable bgRunnable = new Runnable() {
			@Override
			public void run() {
				if ("SMSI".equalsIgnoreCase(Thread.currentThread().getName())) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				parent.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						messageBox.dispose();
						parent.pack(true);
						parent.redraw();
						parent.update();
						pushSession.stop();
					}
				});
			}
		};

		pushSession.start();
		
		Thread bgThread = new Thread(bgRunnable, "SMSI");
		bgThread.setDaemon(true);
		bgThread.setName("SMSI");
		bgThread.start();
		*/
	}
	
	public static void showInformationMessage(final Composite parent, final Control reference, String message) {
		final InformationMessage messageBox = new InformationMessage(parent, reference,MessageUtil.unescape(message),Display.getDefault().getSystemImage(SWT.ICON_INFORMATION));
		parent.pack(true);
		
		final ServerPushSession pushSession = new ServerPushSession();
		Runnable bgRunnable = new Runnable() {
			@Override
			public void run() {
				if ("SMSI".equalsIgnoreCase(Thread.currentThread().getName())) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				parent.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						messageBox.dispose();
						parent.pack(true);
						parent.redraw();
						parent.update();
						pushSession.stop();
					}
				});
			}
		};

		pushSession.start();
		
		Thread bgThread = new Thread(bgRunnable, "SMSI");
		bgThread.setDaemon(true);
		bgThread.setName("SMSI");
		bgThread.start();
	}
	
	
	public static void showInformationShellMessage(String message) {
		final InformationMessage messageBox = new InformationMessage(MessageUtil.unescape(message),Display.getDefault().getSystemImage(SWT.ICON_INFORMATION));
		messageBox.setFocus();
		final ServerPushSession pushSession = new ServerPushSession();
		Runnable bgRunnable = new Runnable() {
			@Override
			public void run() {
				if ("SMSIS".equalsIgnoreCase(Thread.currentThread().getName())) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				messageBox.getShell().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						messageBox.disposeShell();
						pushSession.stop();
					}
				});
			}
		};

		pushSession.start();
		
		Thread bgThread = new Thread(bgRunnable, "SMSIS");
		bgThread.setDaemon(true);
		bgThread.setName("SMSIS");
		bgThread.start();
	}
	
	
	public static void showErrorMessage(final Composite parent, String message) {
		showErrorShellMessage(message);
		/*
		final InformationMessage messageBox = new InformationMessage(parent, MessageUtil.unescape(message),Display.getDefault().getSystemImage(SWT.ICON_ERROR));
		parent.pack(true);
		
		final ServerPushSession pushSession = new ServerPushSession();
		Runnable bgRunnable = new Runnable() {
			@Override
			public void run() {
				if ("SMSE".equalsIgnoreCase(Thread.currentThread().getName())) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				parent.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						messageBox.dispose();
						parent.pack(true);
						parent.redraw();
						parent.update();
						pushSession.stop();
					}
				});
			}
		};

		pushSession.start();
		
		Thread bgThread = new Thread(bgRunnable, "SMSE");
		bgThread.setDaemon(true);
		bgThread.setName("SMSE");
		bgThread.start();
		*/
	}
	
	
	public static void showErrorShellMessage(String message) {
		final InformationMessage messageBox = new InformationMessage( MessageUtil.unescape(message),Display.getDefault().getSystemImage(SWT.ICON_ERROR));
		messageBox.setFocus();
		final ServerPushSession pushSession = new ServerPushSession();
		Runnable bgRunnable = new Runnable() {
			@Override
			public void run() {
				if ("SMSES".equalsIgnoreCase(Thread.currentThread().getName())) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				messageBox.getShell().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						messageBox.disposeShell();
						pushSession.stop();
					}
				});
			}
		};

		pushSession.start();
		
		Thread bgThread = new Thread(bgRunnable, "SMSES");
		bgThread.setDaemon(true);
		bgThread.setName("SMSES");
		bgThread.start();
	}


	public static void showInformationMessage(String message) {
		showInformationShellMessage(message);
	}

	public static void showWarningMessage(String message) {
		MessageDialogUtil.openWarning(Display.getCurrent().getActiveShell(), MessageUtil.unescape(AbosMessages.get().MESSAGE_WARNING), MessageUtil.unescape(message), null);
	}

	public static void showQuestionMessage(String message, DialogCallback callback) {
		MessageDialogUtil.openWarning(Display.getCurrent().getActiveShell(), MessageUtil.unescape(AbosMessages.get().MESSAGE_QUESTION), MessageUtil.unescape(message), null);
	}

	public static void showConfirmationMessage(String message, DialogCallback callback) {
		MessageDialogUtil.openConfirm(Display.getCurrent().getActiveShell(), MessageUtil.unescape(AbosMessages.get().MESSAGE_CONFIRMATION), MessageUtil.unescape(message), null);
	}
	

}
