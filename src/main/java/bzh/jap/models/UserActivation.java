package bzh.jap.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "UserActivation")
public class UserActivation {

	@Id
	@Column(name = "user_id", nullable = false)
	private int userId;
	
	@NotBlank
	@Size(max = 25)
	@Column(name = "user_activation_code")
	private String userActivationCode;
	
	@OneToOne
    @JoinColumn(name="user_id")
    @MapsId
    private User user;
	
	public UserActivation() {
	}
	
	public UserActivation(String codeActivation) {
		this.setUserActivationCode(codeActivation);
	}
	
	public int getId() {
		return userId;
	}

	public void setId(int id) {
		this.userId = id;
	}

	public String getUserActivationCode() {
		return userActivationCode;
	}

	public void setUserActivationCode(String userActivationCode) {
		this.userActivationCode = userActivationCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}