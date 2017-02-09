package cu.uci.abcd.demo.domain.common;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "dphonenumber", schema = "abcd")
@PrimaryKeyJoinColumn(name = "phone_number_id")
@DiscriminatorValue(value = "PHONE_NUMBER")
public class PhoneNumber extends Address {
	@Column(name = "cuntry_code")
	private String countryCode;
	@Column(name = "area_code")
	private String areaCode;
	@Column(name = "local_phone_number")
	private String localPhoneNumber;

	public PhoneNumber() {
		super();
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getLocalPhoneNumber() {
		return localPhoneNumber;
	}

	public void setLocalPhoneNumber(String localPhoneNumber) {
		this.localPhoneNumber = localPhoneNumber;
	}

}
