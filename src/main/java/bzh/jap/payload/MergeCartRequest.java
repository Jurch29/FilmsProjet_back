package bzh.jap.payload;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import bzh.jap.models.MovieUserCart;

public class MergeCartRequest {

	private long userId;
	
	@NotEmpty(message = "The localCart field must not be blank.")
    private List<MovieUserCart> localCart;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<MovieUserCart> getLocalCart() {
		return localCart;
	}

	public void setLocalCart(List<MovieUserCart> localCart) {
		this.localCart = localCart;
	}
	
}
