package cu.uci.abcd.demo.domain.common;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "dpostaladdress", schema = "abcd")
@PrimaryKeyJoinColumn(name = "postal_address_id")
@DiscriminatorValue(value = "POSTAL_ADDRESS")
public class PostalAddress extends Address {
	@Column
	private String street;
	@Column
	private String city;
	@Column(name = "state_province")
	private String stateProvince;
	@Column(name = "postal_code")
	private String postalCode;

	public PostalAddress() {
		super();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}
