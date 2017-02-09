package cu.uci.abcd.dao.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil<T> implements Page<T> {
	Pageable pageable;
	Collection<T> content;

	public PageUtil() {
		super();
		content = new ArrayList<T>();
	}

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	public void setContent(Collection<T> content) {
		this.content = content;
	}

	@Override
	public List<T> getContent() {
		List<T> temp = new ArrayList<T>(content);
		int begin = pageable.getOffset();
		int end = pageable.getOffset() + pageable.getPageSize();
		if (end > content.size()) {
			end = content.size();
			if (begin > end) {
				return Collections.emptyList();
			}
		}
		return temp.subList(begin, end);
	}

	@Override
	public int getNumber() {
		return pageable.getPageNumber();
	}

	@Override
	public int getNumberOfElements() {
		return content.size() < pageable.getPageSize() * pageable.getPageNumber() ? content.size() % pageable.getPageSize() * pageable.getPageNumber() : pageable.getPageSize();
	}

	@Override
	public int getSize() {
		return content.size();
	}

	@Override
	public Sort getSort() {
		return pageable.getSort();
	}

	@Override
	public long getTotalElements() {
		return content.size();
	}

	@Override
	public int getTotalPages() {
		if ((content.size() / pageable.getPageSize()) == (content.size() / new Float(pageable.getPageSize())))
			return content.size() / pageable.getPageSize();
		return content.size() / pageable.getPageSize() + 1;
	}

	@Override
	public boolean hasContent() {
		return !content.isEmpty();
	}

	@Override
	public boolean hasNextPage() {
		return content.size() > pageable.getPageSize() * pageable.getPageNumber();
	}

	@Override
	public boolean hasPreviousPage() {
		return (content.size() > pageable.getPageSize()) && pageable.getPageNumber() > 0;
	}

	@Override
	public boolean isFirstPage() {
		return pageable.getPageNumber() == 0;
	}

	@Override
	public boolean isLastPage() {
		return getTotalPages() == pageable.getPageNumber();
	}

	@Override
	public Iterator<T> iterator() {
		return getContent().iterator();
	}

}
