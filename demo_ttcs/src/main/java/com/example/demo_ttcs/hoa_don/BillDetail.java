package com.example.demo_ttcs.hoa_don;

public class BillDetail {
	private String name;
	private int quantity;
	private int price;
	private int total;
	
	public BillDetail() {
		// TODO Auto-generated constructor stub
	}

	public BillDetail(String name, int quantity, int price, int total) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.total = this.quantity * this.price;
		// TODO Auto-generated constructor stub
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
