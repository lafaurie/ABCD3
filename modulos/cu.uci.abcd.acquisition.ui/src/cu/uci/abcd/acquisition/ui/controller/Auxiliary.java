package cu.uci.abcd.acquisition.ui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Auxiliary {

	public static Pageable getPage(int page, int size, int direction, String orderByString) {
		return new PageRequest(page, size, sort(direction, orderByString));

	}

	public static Sort sort(int direction, String orderByString) {
		return new Sort(new Sort.Order((direction == 1024) ? Sort.Direction.ASC : Sort.Direction.DESC, orderByString));
	}
	
	public static String FormatDate(Date date) {
		return new SimpleDateFormat("dd-MM-yyyy").format(date);

	}
}
