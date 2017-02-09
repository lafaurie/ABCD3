package cu.uci.abcd.demo.domain.common;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="dapplication", schema="abcd")
@PrimaryKeyJoinColumn(name = "application_id")
@DiscriminatorValue(value = "APPLICATION")
public class Application extends TangibleActor {

	public Application() {
		super();
	}

}
