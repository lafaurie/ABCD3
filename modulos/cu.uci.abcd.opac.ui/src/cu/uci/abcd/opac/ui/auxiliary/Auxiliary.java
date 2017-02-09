package cu.uci.abcd.opac.ui.auxiliary;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Schedule;
import cu.uci.abcd.opac.EmailConfigService;
import cu.uci.abcd.opac.RecordIsis;
import cu.uci.abcd.opac.ui.contribution.SendEmail;
import cu.uci.abcd.opac.ui.controller.AllManagementOpacViewController;
import cu.uci.abcd.opac.ui.controller.ConsultMaterialsController;
import cu.uci.abcd.opac.ui.controller.SelectionListViewController;
import cu.uci.abos.api.ui.ViewController;
import cu.uci.abos.core.util.MessageDialogUtil;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.ReportType;
import cu.uci.abcd.opac.ui.SecurityUtils;
import cu.uci.abos.core.util.URLUtil;
import cu.uci.abos.reports.DocumentParam;
import cu.uci.abos.reports.ParagraphParam;
import cu.uci.abos.reports.ReportParam;

public class Auxiliary {

	private ViewController controller;
	private EmailConfigService emailConfigService;

	private User user;

	RecordIsis pivote;
	RecordIsis aux;
	boolean flagRating = true;

	public Auxiliary(ViewController controller) {
		this.controller = controller;

	}

	public void exportToPlainText(List<RecordIsis> mySelectedRecord) {

		String lista = new String();

		for (int i = 0; i < mySelectedRecord.size(); i++) {
			// Datos a mostrar
			// Primer apellido, Nombre. Título. Responsabilidad.
			// Edición. Lugar: editorial, año. Páginas p. Serie o
			// colección. Notas. ISBN.

			String lista_aux = "";

			try {

				lista_aux = mySelectedRecord.get(i).getAuthor() + ". ";

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				lista_aux = lista_aux.concat(mySelectedRecord.get(i).getTitle() + ". ");

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				lista_aux = lista_aux.concat(mySelectedRecord.get(i).getPublication() + "\n");

			} catch (Exception e) {
				e.printStackTrace();
			}

			lista = lista.concat(lista_aux);
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintWriter wr = new PrintWriter(out);
		wr.write(lista);// escribimos en el archivo
		// wr.append(" - y aqui continua"); //concatenamos en el
		// archivo
		// sin borrar lo existente
		// ahora cerramos los flujos de canales de datos, al
		// cerrarlos
		// el archivo quedará guardado con información escrita
		// de no hacerlo no se escribirá nada en el archivo
		wr.close();
		URLUtil.generateDownloadReport(ReportType.TXT, out);

	}

	public void exportToHTML(List<RecordIsis> mySelectedRecord) {

		String html_begin = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n";
		html_begin += "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">";
		html_begin += "\n<head>";
		html_begin += "\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>";
		html_begin += "\n<title>Bibliograf&iacute;a Seleccionada</title>";
		html_begin += "\n</head>";
		html_begin += "\n<body>";
		html_begin += "\n<div class=\"csl-bib-body\" style=\"line-height: 1.35; \">";

		String html_end = "\n</div>";
		html_end += "\n</body>";
		html_end += "\n</html>";

		String html_middle = "";
		String html_final = html_begin;

		for (int i = 0; i < mySelectedRecord.size(); i++) {
			// Datos a mostrar
			// Primer apellido, Nombre. Título. Responsabilidad.
			// Edición. Lugar: editorial, año. Páginas p. Serie o
			// colección. Notas. ISBN.
			try {

				int count_record = i + 1;
				String html_middle_temp = "";
				html_middle_temp += "<div class=\"csl-entry\" style=\"clear: left; margin-bottom: 1em; background: none repeat scroll 0 0 #EEEEEE;border: 1px solid #888888;\">";
				html_middle_temp += "\n<div class=\"csl-left-margin\" style=\"float: left; padding-right: 1em; text-align: right; width: 1em;\">" + count_record + '.' + "</div>";
				html_middle_temp += "\n<div class=\"csl-right-inline\" style=\"margin: 0 0.5em 0 1.5em;\">";
				try {
					html_middle_temp += mySelectedRecord.get(i).getAuthor() + ",";
				} catch (Exception e) {
					html_middle_temp = "";

				}
				html_middle_temp += "<em>&quot;" + mySelectedRecord.get(i).getTitle() + "&quot;</em>" + ",";
				html_middle_temp += mySelectedRecord.get(i).getPublication() + ",";
				html_middle_temp += mySelectedRecord.get(i).getPublicationDate() + ",";
				html_middle_temp += "\n</div>";

				if (i != mySelectedRecord.size() - 1) {
					html_middle = html_middle + html_middle_temp + "\n";
				} else {
					html_middle = html_middle + html_middle_temp;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		html_final = html_final + html_middle + html_end;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintWriter wr = new PrintWriter(out);
		wr.write(html_final);// escribimos en el archivo
		wr.close();
		URLUtil.generateDownloadReport(ReportType.HTML, out);

	}

	public void exportToPdf(List<RecordIsis> mySelectedRecord) {

		List<ReportParam> bibliography = new ArrayList<ReportParam>();

		for (int i = 0; i < mySelectedRecord.size(); i++) {
			// Datos a mostrar
			// Primer apellido, Nombre. Título. Responsabilidad.
			// Edición. Lugar: editorial, año. Páginas p. Serie o
			// colección. Notas. ISBN.

			String lista_aux = "";

			try {

				lista_aux = mySelectedRecord.get(i).getAuthor() + ". ";

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				lista_aux = lista_aux.concat(mySelectedRecord.get(i).getTitle() + ". ");

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				lista_aux = lista_aux.concat(mySelectedRecord.get(i).getPublication() + "\n");

			} catch (Exception e) {
				e.printStackTrace();
			}

			bibliography.add(new ParagraphParam("", lista_aux));

		}

		try {

			ByteArrayOutputStream outputStream;

			if (controller instanceof AllManagementOpacViewController)
				outputStream = ((AllManagementOpacViewController) controller).getPdfGenerator().generateReport(((AllManagementOpacViewController) controller).getPdfGenerator().getStyle(), "Bibliografía", ((AllManagementOpacViewController) controller).getPdfGenerator().getReportTemplate(),
						new DocumentParam("Bibliografía", bibliography));
			else
				outputStream = ((SelectionListViewController) controller).getPdfGenerator().generateReport(((SelectionListViewController) controller).getPdfGenerator().getStyle(), "Bibliografía", ((SelectionListViewController) controller).getPdfGenerator().getReportTemplate(),
						new DocumentParam("Bibliografía", bibliography));

			URLUtil.generateDownloadReport(ReportType.PDF, outputStream);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendEmail(List<RecordIsis> mySelectedRecord, String addressTo, String comment, SendEmail sendEmail) {

		try {
			user = (User) SecurityUtils.getService().getPrincipal().getByKey("user");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (controller instanceof AllManagementOpacViewController)
			emailConfigService = ((AllManagementOpacViewController) controller).getEmailConfig();

		// Obtenemos las propiedades del sistema
		Properties propiedades = new Properties();

		// Configuramos el servidor de correo
		propiedades.put("mail.smtp.host", emailConfigService.getSmtpHost());
		propiedades.put("mail.smtp.starttls.required", emailConfigService.getStarttlsRequired());
		propiedades.put("mail.smtp.starttls.enable", emailConfigService.getStarttlsEnable());
		propiedades.put("mail.smtp.ssl.enable", emailConfigService.getSslEnable());
		propiedades.put("mail.smtp.ssl.trust", emailConfigService.getSslTrust());
		propiedades.put("mail.smtp.socketFactory.port", emailConfigService.getSocketFactoryPort());
		propiedades.put("mail.smtp.port", emailConfigService.getPort());
		propiedades.put("mail.smtp.mail.sender", emailConfigService.getEmail());
		propiedades.put("mail.smtp.user", emailConfigService.getUserEmail());
		propiedades.put("mail.smtp.password", emailConfigService.getPasswordEmail());
		propiedades.put("mail.smtp.auth", emailConfigService.getAuth());
		propiedades.put("mail.transport.protocol", emailConfigService.getTransportProtocol());
		propiedades.put("mail.email", emailConfigService.getEmail());

		Session session = null;

		try {
			session = Session.getInstance(propiedades, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emailConfigService.getEmail(), emailConfigService.getPasswordEmail());
				}
			});

			session.setDebug(true);
		} catch (Exception e) {
			MessageDialogUtil.openError(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_ERROR), "Configuración de conexión erronea", null);
		}

		// Que enviar
		String lista = new String();

		for (int i = 0; i < mySelectedRecord.size(); i++) {
			// Datos a mostrar
			// Primer apellido, Nombre. Título. Responsabilidad.
			// Edición. Lugar: editorial, año. Páginas p. Serie o
			// colección. Notas. ISBN.

			String lista_aux = "";

			try {

				lista_aux = mySelectedRecord.get(i).getAuthor() + ". ";

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				lista_aux = lista_aux.concat(mySelectedRecord.get(i).getTitle() + ". ");

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				lista_aux = lista_aux.concat(mySelectedRecord.get(i).getPublication() + "\n");

			} catch (Exception e) {
				e.printStackTrace();
			}

			lista = lista.concat(lista_aux);
		}

		try {

			String text = "";

			if (user != null)
				text = "Hola, " + user.getPerson().getFullName() + " te ha enviado una selección desde nuestro catálogo público en línea." + "\n \nComentario: " + comment + "\n \n \n" + lista;
			else
				text = "Hola, te han enviado una selección desde nuestro catálogo público en línea." + "\n \nComentario: " + comment + "\n \n \n" + lista;

			File f = new File("selection.txt");

			// Escritura
			FileWriter w = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(w);
			PrintWriter wr = new PrintWriter(bw);
			wr.write(lista);// escribimos en el archivo
			wr.close();
			bw.close();

			// Creando el mensaje con adjunto
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailConfigService.getEmail()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
			message.setSubject("Selección de registros bibliográficos");
			message.setText(text);

			// Enviando el mensaje
			Transport t = session.getTransport("smtp");
			t.connect(emailConfigService.getEmail(), emailConfigService.getPasswordEmail());
			message.saveChanges();
			t.sendMessage(message, message.getAllRecipients());
			t.close();

			MessageDialogUtil.openInformation(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_INFORMATION), "Se envió el correo.", null);
			sendEmail.notifyListeners(SWT.Dispose, new Event());

		} catch (MessagingException | IOException me) {
			MessageDialogUtil.openError(Display.getCurrent().getActiveShell(), MessageUtil.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MESSAGE_ERROR), "No se pudo enviar el correo.", null);

		}

	}

	@SuppressWarnings("deprecation")
	public Date sumar_dias(Date startDate, int dias, Long idLibrary) {
		java.util.Date fechaSalida = startDate;
		java.util.Date fechaSalida1 = null;

		@SuppressWarnings("unused")
		java.util.Date lastDateWork = null;

		int dayWeek = 0;
		boolean date;
		boolean test = false;

		List<cu.uci.abcd.domain.management.library.Calendar> listCalendar = ((AllManagementOpacViewController) controller).findCalendar(idLibrary);
		List<Schedule> listHorary = ((AllManagementOpacViewController) controller).findHorarybyLibrary(idLibrary);

		List<Integer> list = new ArrayList<>();

		Calendar calendario = Calendar.getInstance();
		calendario.setTime(startDate);

		int DiaActual = calendario.get(Calendar.DAY_OF_WEEK);

		for (int i = 0; i < listHorary.size(); i++) {
			if (!list.contains((int) (long) listHorary.get(i).getDayOfWeek().getNomenclatorID())) {
				list.add((int) (long) listHorary.get(i).getDayOfWeek().getNomenclatorID());
			}
		}

		date = false;
		while (!date) {
			switch (DiaActual) {
			case 1:
				dayWeek = (int) Nomenclator.DAY_WEEK_SUNDAY;
				break;
			case 2:
				dayWeek = (int) Nomenclator.DAY_WEEK_MONDAY;
				break;
			case 3:
				dayWeek = (int) Nomenclator.DAY_WEEK_TUESDAY;
				break;
			case 4:
				dayWeek = (int) Nomenclator.DAY_WEEK_WEDNESDAY;
				break;
			case 5:
				dayWeek = (int) Nomenclator.DAY_WEEK_THURSDAY;
				break;
			case 6:
				dayWeek = (int) Nomenclator.DAY_WEEK_FRIDAY;
				break;
			case 7:
				dayWeek = (int) Nomenclator.DAY_WEEK_SATURDAY;
				break;
			}

			for (int i = 0; i < listCalendar.size(); i++) {

				if (listCalendar.get(i).getDaytype().getNomenclatorID().equals(Nomenclator.DAY_TYPE_THIS_YEAR)) {
					String a = new SimpleDateFormat("dd-MM-yyyy").format(listCalendar.get(i).getCalendarDay());
					String b = new SimpleDateFormat("dd-MM-yyyy").format(fechaSalida);

					if (a.equals(b)) {
						if (list.contains(dayWeek)) {
							test = true;
							break;
						} else
							test = false;

					} else
						test = false;

				} else if (listCalendar.get(i).getDaytype().getNomenclatorID().equals(Nomenclator.DAY_TYPE_ALL_YEAR)) {
					String a1 = FormatDateDayMonth(listCalendar.get(i).getCalendarDay());
					String b2 = FormatDateDayMonth(fechaSalida);

					if (a1.equals(b2)) {
						if (list.contains(dayWeek)) {
							test = true;
							break;
						} else
							test = false;

					} else
						test = false;
				}
			}

			if (date != true) {
				if (list.contains(dayWeek) && test == false) {
					fechaSalida1 = calendario.getTime();
					lastDateWork = calendario.getTime();
					dias--;
					calendario.add(Calendar.DATE, 1);
					DiaActual = calendario.get(Calendar.DAY_OF_WEEK);
					fechaSalida = calendario.getTime();
				} else {
					fechaSalida1 = calendario.getTime();
					calendario.add(Calendar.DATE, 1);
					DiaActual = calendario.get(Calendar.DAY_OF_WEEK);
					fechaSalida = calendario.getTime();
				}

			}

			if (dias == 0) {
				date = true;
			}
		}
		return new Date(fechaSalida1.getYear(), fechaSalida1.getMonth(), fechaSalida1.getDate());
	}

	public String FormatDateDayMonth(java.util.Date fechaSalida) {
		return new SimpleDateFormat("dd-MM").format(fechaSalida);
	}

	public List<RecordIsis> quickSortByTitle(List<RecordIsis> records, int izq, int der) {

		int i = izq;
		int j = der;
		pivote = records.get((izq + der) / 2);

		do {
			while (records.get(i).getTitle().compareTo(pivote.getTitle()) < 0)
				i++;
			while (records.get(j).getTitle().compareTo(pivote.getTitle()) > 0)
				j--;
			if (i <= j) {
				try {
					aux = records.get(i);
					records.add(i, records.get(j));
					records.remove(i + 1);
					records.add(j, aux);
					records.remove(j + 1);
					i++;
					j--;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} while (i <= j);

		if (izq < j)
			quickSortByTitle(records, izq, j);
		if (i < der)
			quickSortByTitle(records, i, der);

		return records;
	}

	public List<RecordIsis> quickSortByAuthor(List<RecordIsis> records, int izq, int der) {

		try {

			int i = izq;
			int j = der;
			pivote = records.get((izq + der) / 2);

			do {
				while (records.get(i).getAuthor().compareTo(pivote.getAuthor()) < 0)
					i++;
				while (records.get(j).getAuthor().compareTo(pivote.getAuthor()) > 0)
					j--;
				if (i <= j) {
					try {
						aux = records.get(i);
						records.add(i, records.get(j));
						records.remove(i + 1);
						records.add(j, aux);
						records.remove(j + 1);
						i++;
						j--;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} while (i <= j);

			if (izq < j)
				quickSortByAuthor(records, izq, j);
			if (i < der)
				quickSortByAuthor(records, i, der);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return records;
	}

	public List<RecordIsis> quickSortByRelevance(List<RecordIsis> records, int izq, int der) {

		Collections.sort(records, new Comparator<RecordIsis>() {

			@Override
			public int compare(RecordIsis o1, RecordIsis o2) {
				if (o1.getRating() > o2.getRating())
					return -1;
				if (o1.getRating() < o2.getRating())
					return 1;

				return 0;

			}
		});

		return records;
	}

	public List<RecordIsis> quickSortByPublicationDate(List<RecordIsis> records, int izq, int der) {

		if (flagRating)
			for (int i = 0; i < records.size(); i++) {
				records.get(i).setRating(((ConsultMaterialsController) controller).ratingByUser(records.get(i).getLibrary().getLibraryID(), records.get(i).getControlNumber(), records.get(i).getDataBaseName()));
				flagRating = false;
			}

		int i = izq;
		int j = der;
		pivote = records.get((izq + der) / 2);

		do {
			while (records.get(i).getPublicationDate() < pivote.getPublicationDate())
				i++;
			while (records.get(j).getPublicationDate() > pivote.getPublicationDate())
				j--;
			if (i <= j) {
				try {
					aux = records.get(i);
					records.add(i, records.get(j));
					records.remove(i + 1);
					records.add(j, aux);
					records.remove(j + 1);
					i++;
					j--;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} while (i <= j);

		if (izq < j)
			quickSortByPublicationDate(records, izq, j);
		if (i < der)
			quickSortByPublicationDate(records, i, der);

		return records;
	}

}
