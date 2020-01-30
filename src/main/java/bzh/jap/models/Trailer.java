package bzh.jap.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Trailer")
public class Trailer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trailer_id;

	@NotBlank
	@Size(max = 255)
	@Column(name = "trailer_path")
	private String trailerPath;

	public Trailer() {

	}

	public Trailer(String trailerPath) {
		this.trailerPath = trailerPath;
	}

	public Long getTrailer_id() {
		return trailer_id;
	}

	public void setTrailer_id(Long trailer_id) {
		this.trailer_id = trailer_id;
	}

	public String getTrailerPath() {
		return trailerPath;
	}

	public void setTrailerPath(String trailerPath) {
		this.trailerPath = trailerPath;
	}
}
