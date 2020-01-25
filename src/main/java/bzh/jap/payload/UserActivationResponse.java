package bzh.jap.payload;

public class UserActivationResponse {
	
	private long id;
	private boolean isToActivate;
	
	public UserActivationResponse(long id, boolean isToActivate) {
		this.id = id;
		this.isToActivate = isToActivate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isToActivate() {
		return isToActivate;
	}

	public void setActivation(boolean isToActivate) {
		this.isToActivate = isToActivate;
	}
}
