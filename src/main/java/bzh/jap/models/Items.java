package bzh.jap.models;

import org.springframework.data.mongodb.core.mapping.Field;

public class Items {

	private String movie_id;
	@Field(value = "price")
    private String movie_price;
    private String count;
    
    public Items() {
		// TODO Auto-generated constructor stub
	}
    
	public String getMovie_id() {
		return movie_id;
	}
	public void setMovie_id(String movie_id) {
		this.movie_id = movie_id;
	}
	public String getMovie_price() {
		return movie_price;
	}
	public void setMovie_price(String movie_price) {
		this.movie_price = movie_price;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
}