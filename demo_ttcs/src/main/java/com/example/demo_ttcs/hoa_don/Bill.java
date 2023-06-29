package com.example.demo_ttcs.hoa_don;

import java.sql.Timestamp;

public class Bill {
	private int id;
	private int customerID;
	private Timestamp timestamp;
	private String name;
	private int total;
	private int accountID;
	private String phone_numer;
	
	public Bill() {
		// TODO Auto-generated constructor stub
	}
	
	public Bill(int customerID, int total) {
		this.customerID = customerID;
		this.total = total;
	}
	
	public Bill(int customerID, Timestamp timesamp, int total, int accountID) {
		this.customerID = customerID;
		this.timestamp = timesamp;
		this.total = total;
		this.accountID = accountID;
	}
	
	public Bill(int id, Timestamp timestamp, String name, int total) {
		this.id = id;
		this.timestamp = timestamp;
		this.name = name;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public String getPhone_numer() {
		return phone_numer;
	}

	public void setPhone_numer(String phone_numer) {
		this.phone_numer = phone_numer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", timestamp=" + timestamp + ", name=" + name + ", total=" + total + "]";
	}
}
