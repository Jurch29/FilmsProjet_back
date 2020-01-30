package bzh.jap.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(	name = "User", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "user_login"),
			@UniqueConstraint(columnNames = "user_email") 
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	private long userId;
	
	@NotBlank
	@Size(max = 255)
	@Column(name = "user_lastname")
	private String userLastname;

	@NotBlank
	@Size(max = 255)
	@Column(name = "user_firstname")
	private String userFirstname;
	
	@NotBlank
	@Size(max = 255)
	@Email
	@Column(name = "user_email")
	private String userEmail;
	
	@NotBlank
	@Size(max = 255)
	@Column(name = "user_login")
	private String userLogin;

	@NotBlank
	@Size(max = 255)
	@Column(name = "user_password")
	private String userPassword;
	
	@Basic
	@Column(name = "user_last_connection")
	private Timestamp userLastConnection;
	
	@Column(name = "user_is_deleted", nullable = false, columnDefinition = "TINYINT", length = 1)
	private boolean userIsDeleted;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "UserRole", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonBackReference
    private UserActivation userActivation;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonBackReference
    private List<MovieUserComment> movieUserComments = new ArrayList<MovieUserComment>();

	public User() {
	}

	public User(String lastname, String firstname, String email, String login, String password) {
		this.userLastname = lastname;
		this.userFirstname = firstname;
		this.userEmail = email;
		this.userLogin = login;
		this.userPassword = password;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserLastname() {
		return userLastname;
	}

	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}

	public String getUserFirstname() {
		return userFirstname;
	}

	public void setUserFirstname(String userFirstname) {
		this.userFirstname = userFirstname;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Timestamp getUserLastConnection() {
		return userLastConnection;
	}

	public void setUserLastConnection(Timestamp userLastConnection) {
		this.userLastConnection = userLastConnection;
	}

	public boolean isUserIsDeleted() {
		return userIsDeleted;
	}

	public void setUserIsDeleted(boolean userIsDeleted) {
		this.userIsDeleted = userIsDeleted;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public UserActivation getUserActivation() {
		return userActivation;
	}

	public void setUserActivation(UserActivation userActivation) {
		this.userActivation = userActivation;
	}

	public List<MovieUserComment> getMovieUserComments() {
		return movieUserComments;
	}

	public void setMovieUserComments(List<MovieUserComment> movieUserComments) {
		this.movieUserComments = movieUserComments;
	}
	
}
