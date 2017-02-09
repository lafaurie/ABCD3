package cu.uci.abos.core.widget.grid;

import cu.uci.abos.core.domain.Row;

public interface IActionDenied {
	boolean isDenied(Column colum, Row row);
}
