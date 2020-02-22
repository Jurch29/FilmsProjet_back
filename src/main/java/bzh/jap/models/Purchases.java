package bzh.jap.models;

import java.util.List;

public class Purchases {
	
	private String purchase_date;
	private List<Items> items;
	
	public Purchases() {
		// TODO Auto-generated constructor stub
	}
	
	public String getPurchase_date() {
		return purchase_date;
	}
	public void setPurchase_date(String purchase_date) {
		this.purchase_date = purchase_date;
	}
	public List<Items> getItems() {
		return items;
	}
	public void setItems(List<Items> items) {
		this.items = items;
	}
	
}
