package cu.uci.abos.widgets.grid;

public interface IVisualEntityManager {

	void add(IGridViewEntity entity);

	void save(IGridViewEntity entity);

	void delete(IGridViewEntity entity);

	void refresh();

	void goToLastPage();
}
