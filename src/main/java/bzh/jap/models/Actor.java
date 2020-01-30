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
@Table(name = "Actor")
public class Actor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long actor_id;

	@NotBlank
	@Size(max = 255)
	@Column(name = "actor_lastname")
	private String actorLastName;
	
	@NotBlank
	@Size(max = 255)
	@Column(name = "actor_firstname")
	private String actorFirstName;
	
	public Actor() {
		// TODO Auto-generated constructor stub
	}
	
	public Actor(String lastname, String firstname) {
		// TODO Auto-generated constructor stub
		this.actorLastName = lastname;
		this.actorFirstName = firstname;
	}

	public Long getActor_id() {
		return actor_id;
	}

	public void setActor_id(Long actor_id) {
		this.actor_id = actor_id;
	}

	public String getActorLastName() {
		return actorLastName;
	}

	public void setActorLastName(String actorLastName) {
		this.actorLastName = actorLastName;
	}

	public String getActorFirstName() {
		return actorFirstName;
	}

	public void setActorFirstName(String actorFirstName) {
		this.actorFirstName = actorFirstName;
	}
}
