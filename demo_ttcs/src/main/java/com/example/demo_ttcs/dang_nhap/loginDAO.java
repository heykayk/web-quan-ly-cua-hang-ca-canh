package com.example.demo_ttcs.dang_nhap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/final_ttcs";
	private String jdbcPassword = "password";
	private String jdbcUsername = "root";
	
	
	public loginDAO() {
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
	
	public Account getAccount(String username, String password) {
		String query = "SELECT * FROM final_ttcs.account\r\n"
				+ "where username = ? and password = ?;";
		try(Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query);) {
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return new Account(rs.getInt("id"),
									rs.getString("username"),
									rs.getString("password"),
									rs.getString("email"),
									rs.getString("phoneNumber"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
