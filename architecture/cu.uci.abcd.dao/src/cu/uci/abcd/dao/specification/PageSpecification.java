package cu.uci.abcd.dao.specification;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageSpecification {
	public static Pageable getPage(int page, int size, int direction, String orderByString) {
		return new PageRequest(page, size
				//, sort(direction, orderByString)
				);

	}
	
	public static Pageable getPageInOrder(int page, int size, int direction, String orderByString) {
		return new PageRequest(page, size);
	}

	public static Sort sort(int direction, String orderByString) {
		return new Sort(new Sort.Order((direction == 1024) ? Sort.Direction.ASC : Sort.Direction.DESC, orderByString));
	}
	
	public static Pageable getPage(cu.uci.abos.core.domain.Sort sort) {
		return new PageRequest(sort.getPage(), sort.getSize(), sort(sort.getDirection(), sort.getOrderByString()));

	}
}
