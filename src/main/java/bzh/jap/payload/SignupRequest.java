package bzh.jap.payload;

import java.util.Set;

import javax.validation.constraints.*;
 
public class SignupRequest {
	@NotBlank
    @Size(min = 1, max = 20)
    private String userLastname;
	
	@NotBlank
    @Size(min = 1, max = 20)
    private String userFirstname;
	
    @NotBlank
    @Size(min = 2, max = 20)
    private String userLogin;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String userEmail;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String userPassword;
    
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

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return "lastname : "+this.userLastname+" firstname: "+this.userFirstname+" username: "+this.userLogin+" email: "+this.userEmail+" role: "+this.role;
    }
}
