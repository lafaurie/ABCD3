package cu.uci.abcd.demo.domain.common;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "RECORDTYPE")
public class RecordType extends Nomenclator {

}
