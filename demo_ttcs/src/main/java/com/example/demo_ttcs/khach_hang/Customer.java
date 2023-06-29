package com.example.demo_ttcs.khach_hang;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Customer {
	private int id;
	private String name = "";
	private String phone_number = "";
	private String sex;
	private int reward_points = 0;
	private Date dob;
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}
	
	public Customer(int id, String name, String phone_number, String sex, int reward_points, Date dob) {
		this.id = id;
		this.name = name;
		this.phone_number = phone_number;
		this.sex = sex;
		this.reward_points = reward_points;
		this.dob = dob;
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

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getReward_points() {
		return reward_points;
	}

	public void setReward_points(int reward_points) {
		this.reward_points = reward_points;
	}

	public String getDob() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return  dateFormat.format(this.dob);
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", phone_number=" + phone_number + ", sex=" + sex
				+ ", reward_points=" + reward_points + ", dob=" + dob + "]";
	}
}
