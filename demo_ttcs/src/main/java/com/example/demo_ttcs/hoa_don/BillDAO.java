package com.example.demo_ttcs.hoa_don;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/final_ttcs";
	private String jdbcPassword = "password";
	private String jdbcUsername = "root";
	
	public BillDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public Connection getConnection(){
		Connection connection = null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
			
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public List<Bill> getAllBill(int accountID){
		List<Bill> list = new ArrayList<>();
		try {
			Connection conn= getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT bill.id, bill.saleDate, bill.total, customer.name\r\n"
					+ "FROM final_ttcs.bill\r\n"
					+ "JOIN final_ttcs.accountcustomer ON bill.AccCusID = accountcustomer.id\r\n"
					+ "JOIN final_ttcs.customer ON accountcustomer.CustomerId = customer.id\r\n"
					+ "WHERE accountcustomer.AccountId = ?\r\n"
					+ "ORDER BY bill.saleDate DESC;");
			ps.setInt(1, accountID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				Timestamp timestamp = rs.getTimestamp("saleDate");
				int total = rs.getInt("total");
				String name = rs.getString("name");
				Bill b = new Bill(id, timestamp, name, total);
				list.add(b);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getBillLater(int accountID) {
		int id = -1;
		
		try {
			Connection conn= getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT bill.id\r\n"
					+ "FROM final_ttcs.bill\r\n"
					+ "JOIN final_ttcs.accountcustomer ON bill.AccCusID = accountcustomer.id\r\n"
					+ "JOIN final_ttcs.customer ON accountcustomer.CustomerId = customer.id\r\n"
					+ "WHERE accountcustomer.AccountId = ?\r\n"
					+ "ORDER BY bill.saleDate DESC\r\n"
					+ "LIMIT 1;\r\n"
					+ "");
			ps.setInt(1, accountID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println(id);
		return id;
	}
	
	public Bill getBillById(int billID) {
		Bill bill = new Bill();
		
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT bill.id, bill.saleDate, bill.total, customer.name\r\n"
					+ "FROM final_ttcs.bill\r\n"
					+ "JOIN final_ttcs.accountcustomer ON bill.AccCusID = accountcustomer.id\r\n"
					+ "JOIN final_ttcs.customer ON accountcustomer.CustomerId = customer.id\r\n"
					+ "WHERE bill.id = ?;");
			ps.setInt(1, billID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				bill.setId(rs.getInt("id"));
				bill.setTimestamp(rs.getTimestamp("saleDate"));
				bill.setTotal(rs.getInt("total"));
				bill.setName(rs.getString("name"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return bill;
	}
	
	public List<BillDetail> getAllBillDetailByBillId(int billID){
		List<BillDetail> list = new ArrayList<>();
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT product.name, billdetail.quantity, accountproduct.sales_price\r\n"
					+ "FROM final_ttcs.bill\r\n"
					+ "JOIN final_ttcs.billdetail ON bill.id = billdetail.BillId\r\n"
					+ "JOIN final_ttcs.accountproduct ON billdetail.ProductId = accountproduct.ProId\r\n"
					+ "JOIN final_ttcs.product ON accountproduct.ProId = product.id\r\n"
					+ "WHERE bill.id = ?;");
			ps.setInt(1, billID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String name = rs.getString("name");
				int quantity = rs.getInt("quantity");
				int price = rs.getInt("sales_price");
				
				list.add(new BillDetail(name, quantity, price, price));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
