package com.example.demo_ttcs.khach_hang;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo_ttcs.dang_nhap.Account;

import jakarta.servlet.http.HttpSession;

@Controller
public class CustomerController {
	
	private CustomerDAO dao = new CustomerDAO();
	
	@GetMapping("/customer")
	public String getCutomers(Model model, HttpSession session) {
		Account acc = (Account)session.getAttribute("acc");
		if(acc == null) return "redirect:/dang-nhap";
		
		Customer c = new Customer();
		c = dao.getTopRewardPoint(acc.getId());

		model.addAttribute("name", c.getName());
		model.addAttribute("phoneNumber", c.getPhone_number());
		model.addAttribute("rewardPoint", c.getReward_points());
//		model.addAttribute("ca_id", acc.getId());
		return "customer";
	}
	
	@GetMapping("/list_cus")
	@ResponseBody
	public List<Customer> getCus(HttpSession session){
		Account acc = (Account) session.getAttribute("acc");
		return dao.getAllCustomerByAccountId(acc.getId());
	}
	
	@GetMapping("/search")
	@ResponseBody
	public List<Customer> searchCustomer(@RequestParam("q") String value, HttpSession session){
		Account acc = (Account) session.getAttribute("acc");
		value = "%" + value.trim() + "%";
		
		if(value.equals("") || value == null) {
			return dao.getAllCustomerByAccountId(acc.getId());
		}
		
		return dao.searchCus(value.trim().toLowerCase(), acc.getId());
	}
	
	@GetMapping("/searchDob")
	@ResponseBody
	public List<Customer> searchDobCustomer(HttpSession session){
		Account acc = (Account) session.getAttribute("acc");
		return dao.searchDob(acc.getId());
	}
	
	@PostMapping("/saveCustomer")
	@ResponseBody
	public String saveCustomer(@RequestBody Customer c, HttpSession session) {
		Account acc = (Account) session.getAttribute("acc");
		if(acc == null) return "redirect:/dang-nhap";
		System.out.println(c);
		return dao.saveCustomer(c, acc.getId());
	}
	
	
	
}
