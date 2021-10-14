import java.io.Serializable;

public class item implements Serializable {
	private String name ;
	private double price ;
	private int quantity ;
	
	public item(String name ,double price ,int quantity) {
		this.name = name ;
		this.price = price ;
		this.quantity = quantity ;
		
	}
	
	public item(String name ,int quantity ) {
		this.name = name ;
		this.quantity = quantity ;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}