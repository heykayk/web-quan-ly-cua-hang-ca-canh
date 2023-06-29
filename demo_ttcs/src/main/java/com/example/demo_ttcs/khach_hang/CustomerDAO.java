package com.example.demo_ttcs.khach_hang;

import java.util.Calendar;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.demo_ttcs.hang_hoa.Product;

public class CustomerDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/final_ttcs";
	private String jdbcPassword = "password";
	private String jdbcUsername = "root";
	
	public CustomerDAO() {
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
	
	public List<Customer> getAllCustomerByAccountId(int account_id){
		List<Customer> list = new ArrayList<>();
		try{
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("select customer.id, customer.name, customer.phoneNumber, customer.sex, accountcustomer.reward_points, customer.dob\r\n"
					+ "from `final_ttcs`.`customer`\r\n"
					+ "join `final_ttcs`.`accountcustomer` on customer.id = accountcustomer.customerId\r\n"
					+ "where accountcustomer.AccountId = ?;");
			ps.setInt(1, account_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String phoneNuber = rs.getString("phoneNumber");
				String sex = rs.getInt("sex") ==1 ? "Nam":"Nữ";
				int reward_points = rs.getInt("reward_points");
				Date dob = rs.getDate("dob");
				list.add(new Customer(id, name, phoneNuber, sex, reward_points, dob));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Customer> searchCus(String input, int id){
		List<Customer> list = new ArrayList<>();
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("select customer.id, customer.name, customer.phoneNumber, customer.sex, accountcustomer.reward_points, customer.dob\r\n"
					+ "from `final_ttcs`.`customer`\r\n"
					+ "join `final_ttcs`.`accountcustomer` on customer.id = accountcustomer.customerId\r\n"
					+ "where accountcustomer.AccountId = ? and (name like ? or phoneNumber like ?);");
			ps.setInt(1, id);
			ps.setString(2, input);
			ps.setString(3, input);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int cid = rs.getInt("id");
				String name = rs.getString("name");
				String phone_number = rs.getString("phoneNumber");
				int sex = rs.getInt("sex");
				int reward_points = rs.getInt("reward_points");
				Date dob = rs.getDate("dob");
				
				list.add(new Customer(cid, name, phone_number, sex == 1? "Nam":"Nữ", reward_points, dob));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public List<Customer> searchDob(int id){
		List<Customer> list = new ArrayList<>();
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("select customer.id, customer.name, customer.phoneNumber, customer.sex, accountcustomer.reward_points, customer.dob\r\n"
					+ "from `final_ttcs`.`customer`\r\n"
					+ "join `final_ttcs`.`accountcustomer` on customer.id = accountcustomer.customerId\r\n"
					+ "where accountcustomer.AccountId = ? and DATE_FORMAT(customer.dob, '%m') = ?;");
			ps.setInt(1, id);
			
			Calendar calendar = Calendar.getInstance();
			ps.setInt(2, calendar.get(Calendar.MONTH) + 1);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int cid = rs.getInt("id");
				String name = rs.getString("name");
				String phone_number = rs.getString("phoneNumber");
				int sex = rs.getInt("sex");
				int reward_points = rs.getInt("reward_points");
				Date dob = rs.getDate("dob");
				
				list.add(new Customer(cid, name, phone_number, sex == 1? "Nam":"Nữ", reward_points, dob));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public Customer getTopRewardPoint(int AccId) {
		Customer c = new Customer();
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("select customer.id, customer.name, customer.phoneNumber, customer.sex, accountcustomer.reward_points, customer.dob\r\n"
					+ "from `final_ttcs`.`customer`\r\n"
					+ "join `final_ttcs`.`accountcustomer` on customer.id = accountcustomer.customerId\r\n"
					+ "where reward_points = (select max(reward_points) from accountcustomer where accountcustomer.AccountId = ?);");
			ps.setInt(1, AccId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int cid = rs.getInt("id");
				String name = rs.getString("name");
				String phone_number = rs.getString("phoneNumber");
				int sex = rs.getInt("sex");
				int reward_points = rs.getInt("reward_points");
				Date dob = rs.getDate("dob");
				c =  new Customer(cid, name, phone_number, sex == 1?"Nam":"Nữ", reward_points, dob);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public int check(String name, String phoneNumber) {
		int id = 0;
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("select customer.id\r\n"
					+ "from `final_ttcs`.`customer`\r\n"
					+ "where  customer.phoneNumber = ? and customer.name = ? ;");
			ps.setString(1, phoneNumber);
			ps.setString(2, name);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				id = rs.getInt("customer.id");
			}
			
		} catch(SQLException e) {
			System.out.println("không lấy được id");
			e.printStackTrace();
		}
		return id;
	}
	
	public Date parseDate(String dob){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            java.util.Date utilDate = dateFormat.parse(dob);
            Date sqlDate = new Date(utilDate.getTime());
            
            return sqlDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	public String saveCustomer(Customer c, int AccId) {
		try {
			Connection conn = getConnection();
			int cusId = check(c.getName(), c.getPhone_number());
			System.out.println(cusId);
			if(cusId > 0) {
				PreparedStatement ps = conn.prepareStatement("INSERT INTO `final_ttcs`.`accountcustomer` (`AccountId`, `CustomerId`) VALUES (?, ?);");
				ps.setInt(1, AccId);
				ps.setInt(2, cusId);
				ps.executeUpdate();
				ps.close();
				return "redirect:/customer";
			} else {
				PreparedStatement ps = conn.prepareStatement("INSERT INTO `final_ttcs`.`customer` (`name`, `phoneNumber`, `sex`,  `dob`) VALUES (?, ?, ?, ?);");
				ps.setString(1, c.getName());
				ps.setString(2, c.getPhone_number());
				ps.setInt(3, c.getSex().equalsIgnoreCase("nam")?1:0);
				ps.setDate(4, parseDate(c.getDob()));
				ps.executeUpdate();
				ps.close();
				
				ps = conn.prepareStatement("INSERT INTO `final_ttcs`.`accountcustomer` (`AccountId`, `CustomerId`) VALUES (?, ?);");
				ps.setInt(1, AccId);
				ps.setInt(2, check(c.getName(), c.getPhone_number()));
				ps.executeUpdate();
				return "redirect:/customer";
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return "error";
	}
}
