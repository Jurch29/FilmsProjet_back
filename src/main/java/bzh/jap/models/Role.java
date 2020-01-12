package bzh.jap.models;

import javax.persistence.*;

@Entity
@Table(name = "Role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer role_id;

	@Enumerated(EnumType.STRING)
	@Column(name = "role_title", length = 255)
	private ERole roleTitle;

	public Role() {

	}

	public Role(ERole name) {
		this.roleTitle = name;
	}

	public Integer getId() {
		return role_id;
	}

	public void setId(Integer id) {
		this.role_id = id;
	}

	public ERole getName() {
		return roleTitle;
	}

	public void setName(ERole name) {
		this.roleTitle = name;
	}
}