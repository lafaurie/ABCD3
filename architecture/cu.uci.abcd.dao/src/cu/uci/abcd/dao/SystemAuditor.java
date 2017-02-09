package cu.uci.abcd.dao;

import org.springframework.data.domain.AuditorAware;

public class SystemAuditor implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		return "System_ABCD";
	}

	

	



}