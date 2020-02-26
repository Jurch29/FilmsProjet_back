package bzh.jap.payload;

import java.util.List;

public class UserDetailsResponse {
	
	private long userId;
	private String userLogin;
	private String userFirstname;
	private String userLastname;
	private String userEmail;
	private List<String> roles;
    private String token;
    
    public UserDetailsResponse() {
		// TODO Auto-generated constructor stub
	}
    
    public UserDetailsResponse(String token, long id, String userlogin, String firstname, String lastname, String email, List<String> roles) {
		// TODO Auto-generated constructor stub
    	this.userId = id;
    	this.userLogin = userlogin;
    	this.userFirstname = firstname;
    	this.userLastname = lastname;
    	this.userEmail = email;
    	this.roles = roles;
    	this.token = token;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserFirstname() {
		return userFirstname;
	}

	public void setUserFirstname(String userFirstname) {
		this.userFirstname = userFirstname;
	}

	public String getUserLastname() {
		return userLastname;
	}

	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
