package bzh.jap.models;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "PasswordResetToken")
public class PasswordResetToken {

	@Id
	@Column(name = "user_id", nullable = false)
	private long userId;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "user_reset_token")
	private String userResetToken;
	
	@Basic
	@Column(name = "token_expiration")
	private Timestamp tokenExpiration;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    @MapsId
    @JsonManagedReference
    private User user;
	
	public PasswordResetToken() {
		// TODO Auto-generated constructor stub
	}
	
	public PasswordResetToken(String token, Timestamp expiration) {
		// TODO Auto-generated constructor stub
		this.tokenExpiration = expiration;
		this.userResetToken = token;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserResetToken() {
		return userResetToken;
	}

	public void setUserResetToken(String userResetToken) {
		this.userResetToken = userResetToken;
	}

	public Timestamp getTokenExpiration() {
		return tokenExpiration;
	}

	public void setTokenExpiration(Timestamp tokenExpiration) {
		this.tokenExpiration = tokenExpiration;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
