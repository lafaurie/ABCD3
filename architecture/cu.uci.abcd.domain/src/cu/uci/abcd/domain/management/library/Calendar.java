package cu.uci.abcd.domain.management.library;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.util.Auxiliary;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dcalendar", schema = "abcdn")
@SequenceGenerator(name = "seq_dcalendar", sequenceName = "sq_dcalendar", schema = "abcdn", allocationSize = 1)
public class Calendar implements Serializable, Row {
	private static final long serialVersionUID = 6968273373099180704L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dcalendar")
	@Column(name = "id", nullable = false)
	private Long calendarID;

	@Column(name = "calendarname", nullable = false, length = 10)
	private String calendarName;

	@Column(name = "description", nullable = true, length = 500)
	private String description;

	@Column(name = "calendarday", nullable = false)
	private Date calendarDay;
	
	@Column(name = "year", nullable = false)
	private Integer year;

	@ManyToOne
	@JoinColumn(name = "daytype", nullable = false)
	private Nomenclator daytype;

	@ManyToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;

	public Long getCalendarID() {
		return calendarID;
	}

	public void setCalendarID(Long calendarID) {
		this.calendarID = calendarID;
	}

	public String getCalendarName() {
		return calendarName;
	}

	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCalendarDay() {
		return calendarDay;
	}

	public void setCalendarDay(Date calendarDay) {
		this.calendarDay = calendarDay;
	}

	public Nomenclator getDaytype() {
		return daytype;
	}

	public void setDaytype(Nomenclator daytype) {
		this.daytype = daytype;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
	public String getDayAndMonthToString() {
		//return new SimpleDateFormat("dd-MM-yyyy").format(getCalendarDay());
		int monthNumber = Integer.parseInt(new SimpleDateFormat("MM").format(getCalendarDay()));
		String month = Auxiliary.getMonth(monthNumber);//new SimpleDateFormat("MM").format(getCalendarDay());
		//month = month.substring(0, 1).toUpperCase() + month.substring(1);
		String day = new SimpleDateFormat("dd").format(getCalendarDay());
		return day + " " + month;
		
	}
	
	public String getDateToString() {
		String day = new SimpleDateFormat("dd").format(getCalendarDay());
		int monthNumber = Integer.parseInt(new SimpleDateFormat("MM").format(getCalendarDay()));
		String month = Auxiliary.getMonth(monthNumber);
		String year = new SimpleDateFormat("yyyy").format(getCalendarDay());
		return day + " " + month + " " + year ;
	}
	
	@Override
	public Object getRowID() {
		return getCalendarID();
	}

}
