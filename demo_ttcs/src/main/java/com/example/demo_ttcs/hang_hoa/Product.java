package com.example.demo_ttcs.hang_hoa;

public class Product {
	private int id = 0;
	private String name = "";
	private String description = "";
	private int type = 1;
	private int quatity = 0;
	private int import_price = 0;
	private int slaes_price = 0;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}
	
	public Product(int id, String name, String description, int type, int quatity, int import_price, int slaes_price) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.quatity = quatity;
		this.import_price = import_price;
		this.slaes_price = slaes_price;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getQuatity() {
		return quatity;
	}

	public void setQuatity(int quatity) {
		this.quatity = quatity;
	}

	public int getImport_price() {
		return import_price;
	}

	public void setImport_price(int import_price) {
		this.import_price = import_price;
	}

	public int getSlaes_price() {
		return slaes_price;
	}

	public void setSlaes_price(int slaes_price) {
		this.slaes_price = slaes_price;
	}
	
	
}
