package com.example.demo_ttcs.hang_hoa;

public class Product {
	private int id = 0;
	private String name = "";
	private String image = "";
	private int type = 1;
	private int quantity;
	private int import_price;
	private int sales_price;
	private int amountSold;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}
	
	public Product(int id, String name, String image, int type, int quantity, int import_price, int sales_price, int amountSold) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.type = type;
		this.quantity = quantity;
		this.import_price = import_price;
		this.sales_price = sales_price;
		this.amountSold = amountSold;
	}
	
	public Product(int id, String name, int amountSold) {
		this.id = id;
		this.name = name;
		this.amountSold = amountSold;
	}
	

	public int getAmountSold() {
		return amountSold;
	}

	public void setAmountSold(int amountSold) {
		this.amountSold = amountSold;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getImport_price() {
		return import_price;
	}

	public void setImport_price(int import_price) {
		this.import_price = import_price;
	}

	public int getSales_price() {
		return sales_price;
	}

	public void setSales_price(int sales_price) {
		this.sales_price = sales_price;
	}
}
