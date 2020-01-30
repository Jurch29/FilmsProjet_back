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
@Table(name = "Author")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long author_id;

	@NotBlank
	@Size(max = 255)
	@Column(name = "author_lastname")
	private String authorLastName;
	
	@NotBlank
	@Size(max = 255)
	@Column(name = "author_firstname")
	private String authorFirstName;
	
	public Author() {
		// TODO Auto-generated constructor stub
	}
	
	public Author(String lastname, String firstname) {
		// TODO Auto-generated constructor stub
		this.authorLastName = lastname;
		this.authorFirstName = firstname;
	}

	public Long getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(Long author_id) {
		this.author_id = author_id;
	}

	public String getAuthorLastName() {
		return authorLastName;
	}

	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}

	public String getAuthorFirstName() {
		return authorFirstName;
	}

	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}
}
