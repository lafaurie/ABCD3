package cu.uci.abcd.circulation.ui.auxiliary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.widgets.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import cu.uci.abcd.domain.circulation.Reservation;
import cu.uci.abcd.domain.circulation.Transaction;
import cu.uci.abcd.domain.common.LoanObject;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.management.library.Schedule;

public class Auxiliary {

	public static <T> Set<T> getCommonElements(Collection<? extends Collection<T>> collections) {

		Set<T> common = new LinkedHashSet<T>();
		if (!collections.isEmpty()) {
			Iterator<? extends Collection<T>> iterator = collections.iterator();
			common.addAll(iterator.next());
			while (iterator.hasNext()) {
				common.retainAll(iterator.next());
			}
		}
		return common;
	}

	public static String FormatDate(Date date) {
		return new SimpleDateFormat("dd-MM-yyyy").format(date);

	}

	public static Pageable ppp(int page, int size) {
		return new PageRequest(page, size);
	}
	
	public static java.util.Date addDays(
			List<cu.uci.abcd.domain.management.library.Calendar> listCalendar,
			List<Schedule> listHorary, Date startDate, int dias,
			Long idLibrary, List<Reservation> listDateReservations) {
		
		java.util.Date fechaSalida = startDate;
		java.util.Date fechaSalida1 = null;
		java.util.Date lastDateWork = null;
		int dayWeek = 0;
		boolean date;
		boolean test = false;

	
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

				if (listCalendar.get(i).getDaytype().getNomenclatorID().equals(Nomenclator.DAY_TYPE_THIS_YEAR) ) {
					String a = Auxiliary.FormatDate(listCalendar.get(i).getCalendarDay());
					String b = Auxiliary.FormatDate(fechaSalida);

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
			
			if (listDateReservations!=null) {		
			for (int i = 0; i < listDateReservations.size(); i++) {
				String aR = Auxiliary.FormatDate(listDateReservations.get(i).getReservationDate());
				String bR = Auxiliary.FormatDate(fechaSalida);

				if (aR.equals(bR)) {
					lastDateWork = calendario.getTime();
					fechaSalida1 = lastDateWork;
					date = true;
					break;
				}
			}
			}
			
			if (date != true) {
				if (list.contains(dayWeek) && test == false) {
					fechaSalida1 = calendario.getTime();
					lastDateWork = calendario.getTime();
					dias--;
					calendario.add(Calendar.DATE, 1);
					DiaActual = calendario.get(Calendar.DAY_OF_WEEK);
					fechaSalida =  calendario.getTime();
				} else {
					fechaSalida1 =  calendario.getTime();
					calendario.add(Calendar.DATE, 1);
					DiaActual = calendario.get(Calendar.DAY_OF_WEEK);
					fechaSalida = calendario.getTime();
				}
			}

			if (dias == 0) {
				date = true;
			}
		}
		return fechaSalida1;
	}
	
	public static String FormatDateDayMonth(java.util.Date fechaSalida) {
		return new SimpleDateFormat("dd-MM").format(fechaSalida);
	}

	public static List<LoanObject> TransactionBD(List<Transaction> lista_de_objetos_BD) {
		List<LoanObject> loanObjects = new ArrayList<LoanObject>();
		for (int i = 0; i < lista_de_objetos_BD.size(); i++) {
			loanObjects.add(lista_de_objetos_BD.get(i).getLoanObject());
		}

		return loanObjects;
	}

	public static int cantidad_de_elementos(List<LoanObject> loanObjects, Long IdNomenclator) {
		int contador = 0;
		for (int i = 0; i < loanObjects.size(); i++) {
			if (loanObjects.get(i).getRecordType().getNomenclatorID().equals(IdNomenclator)) {
				contador++;
			}
		}
		return contador;
	}

	public static List<LoanObject> concatenar(List<LoanObject> loanObjects, List<LoanObject> loanObjects1) {

		List<LoanObject> listaUnificada = new ArrayList<LoanObject>();
		if (loanObjects.isEmpty() == false) {
			listaUnificada.addAll(loanObjects); // add first arraylist

		}
		if (loanObjects1.isEmpty() == false) {

			listaUnificada.addAll(loanObjects1); // add Second arraylist
		}

		return listaUnificada;
	}
	
	public static List<Reservation> listReservationLoanObject(List<Reservation> listR, Long idLoanObject){
		
		List<Reservation> listReturn = new ArrayList<>();
		
		for (int i = 0; i < listR.size(); i++) {
			for (int j = 0; j < listR.get(i).getReservationList().size(); j++) {
				if (listR.get(i).getReservationList().get(j).getLoanObjectID().equals(idLoanObject)) {
					listReturn.add(listR.get(i));
				}
			}			
		}
		return listReturn;
	}
	
	public static void goDateTimeToBeforeOneMonth(DateTime dataTime) {
		// arreglar, invento
		java.util.Date d = new java.util.Date();
		Date a = new Date(d.getTime() - 899999999);
		Date b = new Date(a.getTime() - 899999999);
		Date c = new Date(b.getTime() - 899999999);

		int fromYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(c));
		int fromMonth = Integer.parseInt(new SimpleDateFormat("MM").format(c));
		int fromDay = Integer.parseInt(new SimpleDateFormat("dd").format(c));

		dataTime.setDate(fromYear, fromMonth - 1, fromDay);
	}
	
	public static void goDateTimeToToday(DateTime dataTime) {
		// arreglar, invento
		java.util.Date d = new java.util.Date();
		Date a = new Date(d.getTime());
		Date b = new Date(a.getTime());
		Date c = new Date(b.getTime());

		int fromYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(c));
		int fromMonth = Integer.parseInt(new SimpleDateFormat("MM").format(c));
		int fromDay = Integer.parseInt(new SimpleDateFormat("dd").format(c));

		dataTime.setDate(fromYear, fromMonth - 1, fromDay);
	}

}
