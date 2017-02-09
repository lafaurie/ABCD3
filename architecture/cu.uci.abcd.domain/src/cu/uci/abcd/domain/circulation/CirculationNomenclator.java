package cu.uci.abcd.domain.circulation;

public interface CirculationNomenclator {

	public static final long LOANOBJECT_STATE = 5L;
	public static final long LOANUSER_TYPE = 14L;
	public static final long LOANOBJECT_TYPE = 4L;
	public static final long LOANUSER_STATE = 18L;
	public static final long LOAN_TYPE = 19L;
	public static final long PENALTY_TYPE = 20L;
	public static final long LOAN_STATE = 21L;
	public static final long PENALTY_STATE = 22L;
	public static final long RESERVATION_STATE = 23L;

	public static final long MODALITY_STUDENT = 28L;
	public static final long MODALITY_STUDENT_POSTGRADUATE = 41L;
	public static final long SPECIALITY = 29L;

	public static final long LOANUSER_TYPE_STUDENT = 753L;
	public static final long LOANUSER_TYPE_PROFESOR = 754L;
	public static final long LOANUSER_TYPE_POSTGRADUATE = 755L;
	public static final long LOANUSER_TYPE_EXECUTIVE = 790L;
	public static final long LOANUSER_TYPE_INVESTIGATOR = 791L;
	public static final long LOANUSER_TYPE_LIBRARIAN = 792L;
	public static final long LOANUSER_TYPE_LOAN_INTERLIBRARY = 793L;
	public static final long LOANUSER_TYPE_OTHER_WORKERS = 794L;
	
	public static final long LOANUSER_STATE_ACTIVE = 751L;
	public static final long LOANUSER_STATE_INACTIVE = 752L;

	public static final long PENALTY_STATE_ACTIVE = 776L;
	public static final long PENALTY_STATE_INACTIVE = 777L;
	public static final long PENALTY_STATE_PENDING_PAYMENT = 778L;
	public static final long PENALTY_STATE_PAID = 779L;

	public static final long PENALTY_TYPE_SUSPENCION = 759L;
	public static final long PENALTY_TYPE_FINE = 758L;

	public static final long LOAN_STATE_BORROWED = 760L;
	public static final long LOAN_STATE_RETURN = 761L;
	public static final long LOAN_STATE_NOT_DELIVERED = 762L;
	public static final long LOAN_STATE_LATE = 763L;
	public static final long LOAN_STATE_RENEW = 920L;
	
	public static final long LOANOBJECT_STATE_BORROWED = 769L;
	public static final long LOANOBJECT_STATE_AVAILABLE = 770L;
	public static final long LOANOBJECT_STATE_LOST = 771L;
	public static final long LOANOBJECT_STATE_EXPURGO = 772L;

	public static final long LOAN_TYPE_INTERN = 756L;
	public static final long LOAN_TYPE_EXTERN = 757L;
	public static final long LOAN_TYPE_INTER_LIBRARY = 5009L;

	public static final long RESERVATION_STATE_PENDING = 764L;
	public static final long RESERVATION_STATE_EXECUTED = 766L;
	public static final long RESERVATION_STATE_CANCELLED = 767L;

	public static final long RESERVATION_TYPE = 37L;
	public static final long RESERVATION_TYPE_EXTERN = 601L;
	public static final long RESERVATION_TYPE_INTERN = 600L;
	
	public static final long RESERVATION_REASON_CANCELLED = 27L;

	public static final long CENTER_INVESTIGATION = 54L;
}
