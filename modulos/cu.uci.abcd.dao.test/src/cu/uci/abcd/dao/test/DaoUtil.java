package cu.uci.abcd.dao.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public class DaoUtil<T> implements PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T> {
	protected List<T> data = new ArrayList<T>();

	@Override
	public long count() {
		return data.size();
	}

	@Override
	public void delete(Long key) {
		data.remove(key);
	}

	@Override
	public void delete(T o) {
		data.remove(o);
	}

	@Override
	public void delete(Iterable<? extends T> c) {
		data.removeAll((Collection<?>) c);
	}

	@Override
	public void deleteAll() {
		data.clear();
	}

	@Override
	public boolean exists(Long arg0) {
		return data.size() > arg0;
	}

	@Override
	public Iterable<T> findAll() {
		return data;
	}

	@Override
	public Iterable<T> findAll(Iterable<Long> iterable) {
		Collection<T> result = new ArrayList<T>(data);
		return result;
	}

	@Override
	public T findOne(Long key) {
	
		try {
			return data.get(new Integer(key.toString()));
		} catch (Exception e) {
			throw new DaoTestException("Data Not Found.Key it is out side of collection range of positions.");
		}
		
	}

	@Override
	public <S extends T> S save(S value) {
		data.add(value);
		return value;
	}

	@Override
	public <S extends T> Iterable<S> save(Iterable<S> iterable) {
		for (S s : iterable) {
			save(s);
		}
		return iterable;
	}

	@Override
	public long count(Specification<T> arg0) {
		return data.size();
	}

	@Override
	public List<T> findAll(Specification<T> arg0) {
		return new ArrayList<T>(data);
	}

	@Override
	public Page<T> findAll(Specification<T> arg0, Pageable pageable) {
		PageUtil<T> page = new PageUtil<>();
		page.setContent(data);
		page.setPageable(pageable);
		return page;
	}

	@Override
	public List<T> findAll(Specification<T> arg0, Sort arg1) {
		return data;
	}

	@Override
	public T findOne(Specification<T> s) {
		if (data.isEmpty()) {
			return null;
		}
		return data.iterator().next();
	}

	@Override
	public Iterable<T> findAll(Sort arg0) {
		return data;
	}

	@Override
	public Page<T> findAll(Pageable arg0) {
		PageUtil<T> page = new PageUtil<>();
		page.setContent(data);
		page.setPageable(arg0);
		return page;
	}

}
