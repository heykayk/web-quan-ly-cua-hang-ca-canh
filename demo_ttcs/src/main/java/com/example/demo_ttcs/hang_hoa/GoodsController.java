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

import com.example.demo_ttcs.dang_nhap.Account;

import jakarta.servlet.http.HttpSession;



@Controller
public class GoodsController {
	
	GoodsDAO dao = new GoodsDAO();
	
	@GetMapping("/hang-hoa")
	public String getGoods(Model model, HttpSession session) throws IOException {
		Account acc = (Account) session.getAttribute("acc");
		if(acc != null) {
			List <Product> listB = dao.getAllProductByAccountID(acc.getId());
			model.addAttribute("goods", listB);
			return "goods";
		}
		return "redirect:/dang-nhap";
	}
	
	@GetMapping("/product/{id}")
	public String getProduct(Model model, @PathVariable String id, HttpSession session) {
		Account acc = (Account) session.getAttribute("acc");
		model.addAttribute(id);
		model.addAttribute("product", dao.getProductById(Integer.parseInt(id), acc.getId()));
		return "goods-detail";
	}
	
	@GetMapping("/hang-hoa/{t}")
	public String getGoodsByType(Model model, @PathVariable String t, HttpSession session) throws IOException {
		Account acc = (Account) session.getAttribute("acc");
		model.addAttribute("goods", dao.getProductByType(Integer.parseInt(t), acc.getId()));
		return "goods";
	}
	
	@PostMapping("/product/save/{id}")
	public String update(Product product, 
			@PathVariable String id, 
			@RequestParam String _method, 
			@RequestParam String hidden_type,
			@RequestParam("quatity") String quantity,
			HttpSession session) {
		product.setType(Integer.parseInt(hidden_type));
		product.setQuantity(Integer.parseInt(quantity));
		Account acc = (Account) session.getAttribute("acc");
		if(_method.equals("put")) {
			return dao.updateProduct(product, acc.getId());
		}else
		{
			return dao.insertProduct(product, acc.getId());
		}
	}
	
	@GetMapping("/product/deleteP/{id}")
	public String deleteBook(@PathVariable String id, HttpSession session) {
		Account acc = (Account) session.getAttribute("acc");
		dao.deleteProduct(acc.getId(), Integer.parseInt(id));
		return "redirect:/hang-hoa";
	}
}
