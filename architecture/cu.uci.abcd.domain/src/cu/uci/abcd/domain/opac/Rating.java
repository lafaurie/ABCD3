package cu.uci.abcd.domain.opac;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "drating", schema = "abcdn")
@PrimaryKeyJoinColumn(name = "opacaction")
@DiscriminatorValue("Rating")
public class Rating extends OPACAction {

	private static final long serialVersionUID = 8906457779164570484L;

	@Column(name = "ratingvalue")
	private float rating;

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
}