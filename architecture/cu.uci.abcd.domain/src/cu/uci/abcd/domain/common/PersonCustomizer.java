package cu.uci.abcd.domain.common;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;

public class PersonCustomizer implements DescriptorCustomizer {

	public void customize(ClassDescriptor descriptor) {
		descriptor.getInheritancePolicy().setShouldOuterJoinSubclasses(true);
		descriptor.getInheritancePolicy().setShouldReadSubclasses(true);
	}

}
