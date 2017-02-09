package cu.uci.abcd.domain.acquisition;

public interface AdquisitionNomenclator {

	public static final long USER_TYPE = 6L;
	public static final long SUGGESTION_STATE = 7L;
	public static final long PURCHASEORDER_STATE = 8L;
	public static final long PURCHASEREQUEST_STATE = 15L;
	public static final long DESIDERATA_STATE = 32L;

	public static final long SUGGESTION_STATE_PENDING = 701L;
	public static final long SUGGESTION_STATE_APROVED = 702L;
	public static final long SUGGESTION_STATE_REJECTED = 703L;
	public static final long SUGGESTION_STATE_EXECUTED = 910L;
	
	public static final long PURCHASEREQUEST_STATE_PENDING = 1706L;
	public static final long PURCHASEREQUEST_STATE_APROVED= 1704L;
	public static final long PURCHASEREQUEST_STATE_REJECTED  = 1705L;
	public static final long PURCHASEREQUEST_STATE_EXECUTED = 1000L;
	
	public static final long PURCHASEORDER_STATE_PENDING = 704L;
	public static final long PURCHASEORDER_STATE_APROVED = 705L;
	public static final long PURCHASEORDER_STATE_REJECTED = 706L;
	public static final long PURCHASEORDER_STATE_EXECUTED = 1001L;
		
	public static final long DESIDERATA_STATE_PENDING = 707L;
	public static final long DESIDERATA_STATE_EXECUTED = 709L;
	
	public static final long REASON_FOR_REJECTION = 34L;
	public static final long APPROVAL_REASON = 33L;
	
	public static final long ACQUISITION_TYPE = 43L;
	
	public static final long LOANOBJECT_SITUATION = 44L;	
	public static final long LOANOBJECT_SITUATION_PRECATALOGUING = 1707L;
	
	/**
	 * Fixed
	 */
	public static final long SITUATION_PRECATALOGUING = 525L;


	


	
}
