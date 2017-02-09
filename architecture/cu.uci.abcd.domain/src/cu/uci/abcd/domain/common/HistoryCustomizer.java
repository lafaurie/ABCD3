package cu.uci.abcd.domain.common;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.history.HistoryPolicy;

public class HistoryCustomizer implements DescriptorCustomizer {

	public void customize(ClassDescriptor descriptor) {
		HistoryPolicy policy = new HistoryPolicy();
		policy.setShouldUseDatabaseTime(true);
		policy.addHistoryTableName("abcdn.hnomenclator");
		policy.addStartFieldName("START_DATE");
		policy.addEndFieldName("END_DATE");
		descriptor.setHistoryPolicy(policy);
	}
}
