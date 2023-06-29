package com.example.demo_ttcs.ban_hang;

public class Buy {
	private int id;
	private String name;
	private int price;
	private int quantity = 1;
	private int total;
	private int quantity_remaining;
	
	public Buy() {
		// TODO Auto-generated constructor stub
	}
	
	public Buy(int id, String name, int price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public Buy(int id, String name, int price, int quantity_remaining) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity_remaining = quantity_remaining;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTotal() {
		return getPrice() * getQuantity();
	}
	

	public int getQuantity_remaining() {
		return quantity_remaining;
	}

	public void setQuantity_remaining(int quantity_remaining) {
		this.quantity_remaining = quantity_remaining;
	}

	@Override
	public String toString() {
		return "Buy [id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ", total=" + total
				+ "]";
	}
}
