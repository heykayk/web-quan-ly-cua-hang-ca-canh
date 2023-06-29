package com.example.demo_ttcs.hang_hoa;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo_ttcs.khach_hang.Customer;

public class GoodsDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/final_ttcs";
	private String jdbcPassword = "password";
	private String jdbcUsername = "root";
	
	public GoodsDAO() {
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
	
	public List<Product> searchProductByName(String input, int id){
		List<Product> list = new ArrayList<>();
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT Product.id, Product.name, Product.image, Product.type, AccountProduct.quantity, AccountProduct.inport_price, AccountProduct.sales_price, accountproduct.amountSold \r\n"
					+ "FROM Product\r\n"
					+ "JOIN AccountProduct ON Product.id = AccountProduct.ProId\r\n"
					+ "WHERE AccountProduct.AccId = ? and name like ?;");
			ps.setInt(1, id);
			ps.setString(2, input);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int Proid = rs.getInt("id");
				String name = rs.getString("name");
				String image = rs.getString("image");
				int type = rs.getInt("type");
				int quantity = rs.getInt("quantity");
				int import_price = rs.getInt("inport_price");
				int sales_price = rs.getInt("sales_price");
				int amountSold = rs.getInt("amountSold");
				list.add(new Product(Proid, name, image, type, quantity, import_price, sales_price, amountSold));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Product> getAllProductByAccountID(int account_id){
		List <Product> goods = new ArrayList<>();
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT Product.id, Product.name, Product.image, Product.type, AccountProduct.quantity, AccountProduct.inport_price, AccountProduct.sales_price, accountproduct.amountSold  \r\n"
					+ "FROM Product\r\n"
					+ "JOIN AccountProduct ON Product.id = AccountProduct.ProId\r\n"
					+ "WHERE AccountProduct.AccId = ?;");
			ps.setInt(1, account_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String image = rs.getString("image");
				int type = rs.getInt("type");
				int quantity = rs.getInt("quantity");
				int import_price = rs.getInt("inport_price");
				int sales_price = rs.getInt("sales_price");
				int amountSold = rs.getInt("amountSold");
				goods.add(new Product(id, name, image, type, quantity, import_price, sales_price, amountSold));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return goods;
	}
	
	public Product getProductById(int id, int Accid) {
		Product p = new Product();
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT Product.id, Product.name, Product.image, Product.type, AccountProduct.quantity, AccountProduct.inport_price, AccountProduct.sales_price \r\n"
					+ "FROM Product\r\n"
					+ "JOIN AccountProduct ON Product.id = AccountProduct.ProId\r\n"
					+ "WHERE AccountProduct.AccId = ? and product.id = ?;");
			ps.setInt(1, Accid);
			ps.setInt(2, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setImage(rs.getString("image"));
				p.setType( rs.getInt("type"));
				p.setQuantity(rs.getInt("quantity")) ;
				p.setImport_price(rs.getInt("inport_price"));
				p.setSales_price(rs.getInt("sales_price"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	public List<Product> getProductByType(int t, int AccId){
		List<Product> list = new ArrayList<>();
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT Product.id, Product.name, Product.image, Product.type, AccountProduct.quantity, AccountProduct.inport_price, AccountProduct.sales_price, accountproduct.amountSold  \r\n"
					+ "FROM Product\r\n"
					+ "JOIN AccountProduct ON Product.id = AccountProduct.ProId\r\n"
					+ "WHERE AccountProduct.AccId = ? and product.type = ?;");
			ps.setInt(1, AccId);
			ps.setInt(2, t);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String image = rs.getString("image");
				int type = rs.getInt("type");
				int quantity = rs.getInt("quantity");
				int import_price = rs.getInt("inport_price");
				int sales_price = rs.getInt("sales_price");
				int amountSold = rs.getInt("amountSold");
				list.add(new Product(id, name, image, type, quantity, import_price, sales_price, amountSold));
			}
			ps.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String updateProduct(Product p, int AccId) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("UPDATE `final_ttcs`.`product` SET `name` = ?, `image` = ?, `type` = ? WHERE (`id` = ?);");
			ps.setString(1, p.getName());
			ps.setString(2, p.getImage());
			ps.setInt(3, p.getType());
			ps.setInt(4, p.getId());
			int result = ps.executeUpdate();
			
			PreparedStatement ps1 = conn.prepareStatement("UPDATE `final_ttcs`.`accountproduct` SET `quantity` = ?, `inport_price` = ?, `sales_price` = ? WHERE (`AccId` = ? and `ProId` = ?);");
			
			ps1.setInt(1, p.getQuantity());
			ps1.setInt(2, p.getImport_price());
			ps1.setInt(3, p.getSales_price());
			ps1.setInt(4, AccId);
			ps1.setInt(5, p.getId());
			ps1.executeUpdate();
			
			ps1.close();
			ps.close();
			
			return "redirect:/hang-hoa";
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public int getIdProduct(String s) {
		int id = 0;
		System.out.println(s);
		try{
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("select product.id from `final_ttcs`.`product` where name = ?;");
			ps.setString(1, s);
			ResultSet rs = ps.executeQuery();
			System.out.println(1);
			while(rs.next()) {
				id = rs.getInt("id");
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("KHông lấy được id");
		}
		
		return id;
	}
	
	public String insertProduct(Product p, int AccId) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO `final_ttcs`.`product` (`name`, `image`, `type`) VALUES (?, ?, ?);");
			ps.setString(1, p.getName());
			ps.setString(2, p.getImage());
			ps.setInt(3, p.getType());
			int result = ps.executeUpdate();
			
			PreparedStatement ps1 = conn.prepareStatement("INSERT INTO `final_ttcs`.`accountproduct` (`AccId`, `ProId`, `quantity`, `inport_price`, `sales_price`) VALUES (?, ?, ?, ?, ?);\r\n"
					+ "");
			
			ps1.setInt(1, AccId);
			ps1.setInt(2, getIdProduct(p.getName()));
			ps1.setInt(3, p.getQuantity());
			ps1.setInt(4, p.getImport_price());
			ps1.setInt(5, p.getSales_price());
			
			ps1.executeUpdate();
			
			ps1.close();
			ps.close();
			
			return "redirect:/hang-hoa";
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public void deleteProduct(int AccId, int id) {
		try {
			Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM `final_ttcs`.`accountproduct` WHERE (`Accid` = ? and `ProId` = ?);");
			ps.setInt(1, AccId);
			ps.setInt(2, id);
			ps.execute();
			ps.close();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
