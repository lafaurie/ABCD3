package cu.uci.abcd.domain.management.library;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.util.Auxiliary;
import cu.uci.abos.core.domain.Row;

@Entity
@Table(name = "dschedule", schema = "abcdn")
@SequenceGenerator(name = "seq_dschedule", sequenceName = "sq_dschedule", schema = "abcdn", allocationSize = 1)
public class Schedule implements Serializable, Row {
	private static final long serialVersionUID = 7307067672765836269L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dschedule")
	@Column(name = "id", nullable = false)
	private Long workScheduleID;

	@Column(name = "starthour", nullable = false)
	private Timestamp startHour;

	@Column(name = "endhour", nullable = false)
	private Timestamp endhour;

	@ManyToOne
	@JoinColumn(name = "dayofweek", nullable = false)
	private Nomenclator dayOfWeek;

	@OneToOne
	@JoinColumn(name = "library", nullable = false)
	private Library library;

	public Long getWorkScheduleID() {
		return workScheduleID;
	}

	public void setWorkScheduleID(Long workScheduleID) {
		this.workScheduleID = workScheduleID;
	}

	public Timestamp getStartHour() {
		return startHour;
	}

	public void setStartHour(Timestamp startHour) {
		this.startHour = startHour;
	}

	public Timestamp getEndhour() {
		return endhour;
	}

	public void setEndhour(Timestamp endhour) {
		this.endhour = endhour;
	}

	public Nomenclator getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(Nomenclator dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public String getFromTimeToString() {
		return Auxiliary.FormatMilitareHour(getStartHour());
	}
	
	public String getToTimeToString() {
		return Auxiliary.FormatMilitareHour(getEndhour());
	}
	
	@Override
	public Object getRowID() {
		return getWorkScheduleID();
	}
	
	
	public int compareTo(Schedule o) {
        if (this.dayOfWeek.getNomenclator().getNomenclatorID() < o.getDayOfWeek().getNomenclator().getNomenclatorID() ) {
            return -1;
        }
        if (this.dayOfWeek.getNomenclator().getNomenclatorID() > o.getDayOfWeek().getNomenclator().getNomenclatorID() ) {
            return 1;
        }
        return 0;
    }

}
