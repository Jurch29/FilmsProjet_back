package bzh.jap.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ResetPasswordRequest {
	
	@NotBlank
    @Size(min = 3, max = 50)
    private String token;

	@NotBlank
    @Size(min = 3, max = 20)
    private String password;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
