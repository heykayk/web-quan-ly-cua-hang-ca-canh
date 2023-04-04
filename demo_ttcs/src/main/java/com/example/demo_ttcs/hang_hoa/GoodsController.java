package com.example.demo_ttcs.hang_hoa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class GoodsController {
	@GetMapping("/hang-hoa")
	public String getGoods(Model model) throws IOException {
		Connection connection =null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Product> listB = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ttcs", "root", "password");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from hang_hoa");

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				int type = resultSet.getInt("type");
				int quatity = resultSet.getInt("quatity");
				int import_price = resultSet.getInt("import_price");
				int slaes_price = resultSet.getInt("slaes_price");
				listB.add(new Product(id, name, description, type, quatity, import_price, slaes_price));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		model.addAttribute("goods", listB);
		return "goods";
	}
	
	@GetMapping("/product/{id}")
	public String getProduct(Model model, @PathVariable String id) {
		model.addAttribute(id);
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		Product product = new Product();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ttcs", "root", "password");
			ps = connection.prepareStatement("select * from hang_hoa where id = ?");
			ps.setInt(1, Integer.parseInt(id));
			resultSet = ps.executeQuery();
			
			while(resultSet.next()) {
				product.setId(resultSet.getInt("id"));
				product.setName(resultSet.getString("name"));
				product.setDescription(resultSet.getString("description"));
				product.setType(resultSet.getInt("type"));
				product.setQuatity(resultSet.getInt("quatity"));
				product.setImport_price(resultSet.getInt("import_price"));
				product.setSlaes_price(resultSet.getInt("slaes_price"));
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		model.addAttribute("product", product);
		return "goods-detail";
	}
	
	@GetMapping("/hang-hoa/{t}")
	public String getGoodsByType(Model model, @PathVariable String t) throws IOException {
		Connection connection =null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Product> listB = new ArrayList<>();
		try {
			String query = "select * from hang_hoa where type = " + t;
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ttcs", "root", "password");
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				int type = resultSet.getInt("type");
				int quatity = resultSet.getInt("quatity");
				int import_price = resultSet.getInt("import_price");
				int slaes_price = resultSet.getInt("slaes_price");
				listB.add(new Product(id, name, description, type, quatity, import_price, slaes_price));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		model.addAttribute("goods", listB);
		return "goods";
	}
	
	@PostMapping("/product/save/{id}")
	public String update(Product product, @PathVariable String id, @RequestParam String _method, @RequestParam String hidden_type) {
		product.setType(Integer.parseInt(hidden_type));
		System.out.println("den day roi!!");
		if(_method.equals("put")) {
			System.out.println("put");
			Connection connection = null;
			PreparedStatement ps = null;
			int result = 0;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ttcs", "root", "password");
				ps = connection.prepareStatement("UPDATE `ttcs`.`hang_hoa` SET `name` = ?, `description` = ?, `type` = ?, `quatity` = ?, `import_price` = ?, `slaes_price` = ? WHERE (`id` = ?)");
				ps.setString(1, product.getName());
				ps.setString(2, product.getDescription());
				ps.setInt(3, product.getType());
				ps.setInt(4, product.getQuatity());
				ps.setInt(5, product.getImport_price());
				ps.setInt(6, product.getSlaes_price());
				ps.setInt(7, product.getId());
				result = ps.executeUpdate();
				ps.close();
				
				return "redirect:/hang-hoa";
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else
		{
			System.out.println("post");
			Connection connection = null;
			PreparedStatement ps = null;
			int result = 0;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ttcs", "root", "password");
				ps = connection.prepareStatement("INSERT INTO `ttcs`.`hang_hoa` (`name`, `description`, `type`, `quatity`, `import_price`, `slaes_price`) VALUES (?, ?, ?, ?, ?, ?);\r\n"
						+ "");
				ps.setString(1, product.getName());
				ps.setString(2, product.getDescription());
				ps.setInt(3, product.getType());
				ps.setInt(4, product.getQuatity());
				ps.setInt(5, product.getImport_price());
				ps.setInt(6, product.getImport_price());

				result = ps.executeUpdate();
				ps.close();

				return "redirect:/hang-hoa";

			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return "error";
	}
	
	@GetMapping("/product/deleteP/{id}")
	public String deleteBook(@PathVariable String id) {
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ttcs", "root", "password");
			ps = connection.prepareStatement("DELETE FROM `ttcs`.`hang_hoa` WHERE (`id` = ?);");
			ps.setInt(1, Integer.parseInt(id));
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/hang-hoa";
	}
}
