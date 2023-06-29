package com.example.demo_ttcs.trang_chu;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo_ttcs.hang_hoa.Product;

public class HomeDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/final_ttcs";
	private String jdbcPassword = "password";
	private String jdbcUsername = "root";
	
	public HomeDAO() {
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
	
	public int getSaleDate(int accountID) {
		int total = 0;
		
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT DATE(bill.saleDate) AS saleDay, COUNT(*) AS total\r\n"
					+ "FROM final_ttcs.bill\r\n"
					+ "JOIN final_ttcs.accountcustomer ON bill.AccCusID = accountcustomer.id\r\n"
					+ "JOIN final_ttcs.customer ON accountcustomer.CustomerId = customer.id\r\n"
					+ "WHERE accountcustomer.AccountId = ?\r\n"
					+ "  AND DATE(bill.saleDate) = CURDATE()\r\n"
					+ "GROUP BY saleDay;");
			ps.setInt(1, accountID);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Date day = rs.getDate("saleDay");
				total = rs.getInt("total");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	public int getTotalProduct(int accountID) {
		int total = 0;
		
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT SUM(quantity) AS total_sold\r\n"
					+ "FROM final_ttcs.BillDetail\r\n"
					+ "JOIN final_ttcs.Bill ON BillDetail.BillId = Bill.id\r\n"
					+ "JOIN final_ttcs.accountcustomer on bill.AccCusID = accountcustomer.id\r\n"
					+ "where accountcustomer.AccountId = ?\r\n"
					+ "and DATE(Bill.saleDate) = CURDATE();");
			ps.setInt(1, accountID);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				total = rs.getInt("total_sold");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	public int getTotalAmount(int accountID) {
		int total = 0;
		
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT SUM(total) AS total_amount\r\n"
					+ "FROM final_ttcs.Bill\r\n"
					+ "JOIN final_ttcs.accountcustomer on bill.AccCusID = accountcustomer.id\r\n"
					+ "where accountcustomer.AccountId = ? and\r\n"
					+ "DATE(saleDate) = CURDATE();");
			ps.setInt(1, accountID);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				total = rs.getInt("total_amount");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	public List<Product> getTop10Product(int accountID){
		List<Product> list = new ArrayList<>();
		
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT Product.id, Product.name, AccountProduct.amountSold\r\n"
					+ "FROM final_ttcs.Product\r\n"
					+ "JOIN final_ttcs.AccountProduct ON Product.id = AccountProduct.ProId\r\n"
					+ "WHERE accountproduct.AccId = ?\r\n"
					+ "ORDER BY AccountProduct.amountSold DESC\r\n"
					+ "LIMIT 10;");
			ps.setInt(1, accountID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int amountSold = rs.getInt("amountSold");
				
				list.add(new Product(id, name, amountSold));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	private Date getDateBefor(int d) {
		LocalDate currentDate = LocalDate.now();
        Date today = Date.valueOf(currentDate);
        
        LocalDate dateBefor = currentDate.minusDays(d);
		return Date.valueOf(dateBefor);
	}
	
	private int getTotalAmoutByDate(Date date, int accountID) {
		int total = 0;
		
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT SUM(total) AS total_amount\r\n"
					+ "FROM final_ttcs.Bill\r\n"
					+ "JOIN final_ttcs.accountcustomer on bill.AccCusID = accountcustomer.id\r\n"
					+ "where accountcustomer.AccountId = ? and\r\n"
					+ "DATE(saleDate) = ?;");
			ps.setInt(1, accountID);
			ps.setDate(2, date);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				total = rs.getInt("total_amount");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public Map<Integer, Integer> get15Day(int accountID){
		Map<Integer, Integer> hm = new HashMap<>();
		
		for(int i=0; i<15; i++) {
			hm.put(i, getTotalAmoutByDate(getDateBefor(i), accountID));
		}
		
		return hm;
	}
	
	
	public Map<Integer, Integer> getAmoutSoldByType(int accountID){
		Map<Integer, Integer> hm = new HashMap<>();
		int i = 0;
		
		try {
			Connection conn  = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT SUM(AccountProduct.amountSold) AS totalAmountSold\r\n"
					+ "FROM Type\r\n"
					+ "JOIN Product ON Type.id = Product.type\r\n"
					+ "JOIN AccountProduct ON Product.id = AccountProduct.ProId\r\n"
					+ "where accountproduct.AccId = ?\r\n"
					+ "GROUP BY Type.name;");
			ps.setInt(1, accountID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int type = rs.getInt("totalAmountSold");
				hm.put(i, type);
				i++;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return hm;
	}
}
