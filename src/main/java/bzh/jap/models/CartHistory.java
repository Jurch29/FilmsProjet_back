package bzh.jap.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "CartHistory")
public class CartHistory {
	
	@Id
    private String id;
	
	@Field(value = "user_id")
    private String userId;
	
	@Field(value = "purchases")
	private List<Purchases> orders;
	
	public CartHistory() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Purchases> getOrders() {
		return orders;
	}

	public void setOrders(List<Purchases> orders) {
		this.orders = orders;
	}
	
}
