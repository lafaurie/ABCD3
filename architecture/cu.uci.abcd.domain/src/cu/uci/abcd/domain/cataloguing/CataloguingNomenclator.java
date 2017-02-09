package cu.uci.abcd.domain.cataloguing;

public interface CataloguingNomenclator {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	public static final long LOANOBJECT_STATE = 5L;
	public static final long ACQUISITION_TYPE = 43L;
	public static final long SITUATION = 44L;
	public static final long LOAN_OBJECT_TYPE = 4L;

	public static final long LOANOBJECT_STATE_BORROWED = 769L;
	public static final long LOANOBJECT_STATE_AVAILABLE = 770L;
	public static final long LOANOBJECT_STATE_LOST = 771L;
	public static final long LOANOBJECT_STATE_NOT_AVAILABLE = 772L;

	public static final long ACQUISITION_COIN_TYPE = 16L;
	public static final long ACQUISITION_COIN_TYPE_CUP = 514L;
	public static final long ACQUISITION_COIN_TYPE_CUC = 513L;

	public static final long TYPE_OF_REGISTER = 11L;

	public static final long TYPE_OF_MATERIAL_BOOK = 517L;
	public static final long TYPE_OF_MATERIAL_NEWSPAPER = 519L;
	public static final long TYPE_OF_MATERIAL_THESIS = 5021L;

	public static final long ACQUISITION_TYPE_BUY = 522L;
	public static final long ACQUISITION_TYPE_DONATION = 523L;
	public static final long ACQUISITION_TYPE_EXCHANGE = 524L;

	public static final long SITUATION_PRECATALOGUING = 1707L;
	public static final long SITUATION_CHECKED = 1710L;
	public static final long SITUATION_LOANOBJECT = 1708L;
	public static final long SITUATION_LOST = 1711L;
	public static final long SITUATION_DESINCORPORATED = 1709L;
}
