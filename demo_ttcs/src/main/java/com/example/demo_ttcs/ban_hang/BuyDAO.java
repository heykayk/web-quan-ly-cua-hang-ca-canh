package com.example.demo_ttcs.ban_hang;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.example.demo_ttcs.hoa_don.Bill;

public class BuyDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/final_ttcs";
	private String jdbcPassword = "password";
	private String jdbcUsername = "root";
	
	public BuyDAO() {
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
	
	public Buy getProductBuy(int AccId, int id) {
		Buy b = new Buy();
		
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT Product.id, Product.name, AccountProduct.sales_price , accountproduct.quantity\r\n"
					+ "FROM Product\r\n"
					+ "JOIN AccountProduct ON Product.id = AccountProduct.ProId\r\n"
					+ "WHERE AccountProduct.AccId = ? and accountproduct.ProId = ?;");
			ps.setInt(1, AccId);
			ps.setInt(2, id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				b.setId(rs.getInt("id"));
				b.setName(rs.getString("name"));
				b.setPrice(rs.getInt("sales_price"));
				b.setQuantity_remaining(rs.getInt("quantity"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return b;
	}
	
	public int getAccountCustomerID(int accountID, int customerID) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("select accountcustomer.id\r\n"
					+ "from accountcustomer\r\n"
					+ "where accountcustomer.AccountId = ? and accountcustomer.CustomerId = ?");
			ps.setInt(1, accountID);
			ps.setInt(2, customerID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				return rs.getInt("id");
			}
		}catch(SQLException e) {
			System.out.println("lỗi khi lấy id của AccountCustomer");
			e.printStackTrace();
		}
		return -1;
	}
	
	public int getBillId(Timestamp timestamp) {
		int id = -1;
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT bill.id FROM `final_ttcs`.`bill` WHERE saleDate = ?;");
			ps.setTimestamp(1, timestamp);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				id = rs.getInt("id");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public void addBill(Bill b, List<Buy> list) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO `final_ttcs`.`bill` (`saleDate`, `total`, `AccCusID`) VALUES (?, ?, ?);");
			ps.setTimestamp(1, b.getTimestamp());
			ps.setInt(2, b.getTotal());
			int id = getAccountCustomerID(b.getAccountID(), b.getCustomerID());
			System.out.println(id);
			ps.setInt(3, id);
			ps.executeUpdate();
			ps.close();
			
			int billID = getBillId(b.getTimestamp());
			for(Buy i:list) {
				PreparedStatement p = conn.prepareStatement("INSERT INTO `final_ttcs`.`billdetail` (`ProductId`, `BillId`, `quantity`) VALUES (?, ?, ?);");
				p.setInt(1, i.getId());
				p.setInt(2, billID);
				p.setInt(3, i.getQuantity());
				p.executeUpdate();
				p.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
