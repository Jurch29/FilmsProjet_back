package bzh.jap.payload;

public class UserActivationResponse {
	
	private long userId;
	private boolean isToActivate;
	
	public UserActivationResponse(long id, boolean isToActivate) {
		this.userId = id;
		this.isToActivate = isToActivate;
	}

	public long getuserId() {
		return userId;
	}

	public void setuserId(long id) {
		this.userId = id;
	}

	public boolean isToActivate() {
		return isToActivate;
	}

	public void setActivation(boolean isToActivate) {
		this.isToActivate = isToActivate;
	}
}
