package com.example.demo_ttcs.trang_chu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo_ttcs.dang_nhap.Account;
import com.example.demo_ttcs.hang_hoa.Product;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	HomeDAO homeDAO = new HomeDAO();
	
	@GetMapping("/trang_chu")
	public String trangChu(HttpSession session, Model model) {
		Account acc = (Account)session.getAttribute("acc");
		if(acc == null) return "redirect:/dang-nhap";
		
		model.addAttribute("billDay", homeDAO.getSaleDate(acc.getId()));
		model.addAttribute("total_sold", homeDAO.getTotalProduct(acc.getId()));
		model.addAttribute("total_amount", homeDAO.getTotalAmount(acc.getId()));
		
		return "home";
	}
	
	@GetMapping("/trang_chu/top10Product")
	@ResponseBody
	public List<Product> getTop10Product(HttpSession session){
		Account acc = (Account)session.getAttribute("acc");
		return homeDAO.getTop10Product(acc.getId());
	}
	
	@GetMapping("/trang_chu/get15day")
	@ResponseBody
	public Map<Integer, Integer> get15Day(HttpSession session){
		Account acc = (Account)session.getAttribute("acc");
		return homeDAO.get15Day(acc.getId());
	}
	
	@GetMapping("/trang_chu/totalAmountSold")
	@ResponseBody
	public Map<Integer, Integer> getTotalAmountSold(HttpSession session){
		Account acc = (Account)session.getAttribute("acc");
		
	
		return homeDAO.getAmoutSoldByType(acc.getId());
	}
}
