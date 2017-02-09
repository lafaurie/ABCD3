package cu.uci.abcd.demo.domain.common;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "demailaddress", schema = "abcd")
@PrimaryKeyJoinColumn(name = "email_address_id")
@DiscriminatorValue(value = "EMAIL_ADDRESS")
public class EmailAddress extends Address {
	@Column(name = "email_address")
	private String emailAddress;

	public EmailAddress() {
		super();
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
