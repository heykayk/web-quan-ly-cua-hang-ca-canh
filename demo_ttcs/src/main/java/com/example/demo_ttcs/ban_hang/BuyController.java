package com.example.demo_ttcs.ban_hang;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo_ttcs.dang_nhap.Account;
import com.example.demo_ttcs.hang_hoa.GoodsDAO;
import com.example.demo_ttcs.hang_hoa.Product;
import com.example.demo_ttcs.hoa_don.Bill;
import com.example.demo_ttcs.khach_hang.Customer;
import com.example.demo_ttcs.khach_hang.CustomerDAO;

import jakarta.servlet.http.HttpSession;

@Controller
public class BuyController {
	GoodsDAO goodDAO = new GoodsDAO();
	BuyDAO buyDAO = new BuyDAO();
	CustomerDAO cusDAO = new CustomerDAO();
	
	@GetMapping("/ban_hang")
	public String hanghoa(HttpSession session, Model model) {
		Account acc = (Account)session.getAttribute("acc");
		if(acc == null) return "redirect:/dang-nhap";
		return "banhang";
	}
	
	@GetMapping("/ban_hang/list_product")
	@ResponseBody
	public List<Product> getListProduct(HttpSession session){
		Account acc = (Account)session.getAttribute("acc");
		
		return goodDAO.getAllProductByAccountID(acc.getId());
	}
	
	@GetMapping("/ban_hang/list_product/{t}")
	@ResponseBody
	public List<Product> getListProductByType(HttpSession sessoin, @PathVariable String t){
		Account acc = (Account)sessoin.getAttribute("acc");
		
		if(t.equals("0")) return goodDAO.getAllProductByAccountID(acc.getId());
		return goodDAO.getProductByType(Integer.parseInt(t), acc.getId());
	}
	
	@GetMapping("/searchProduct")
	@ResponseBody
	public List<Product> getSearchProduct(HttpSession session, @RequestParam("q") String value){
		Account acc = (Account)session.getAttribute("acc");
		value = "%" + value + "%";
		
		if(value.equals("") || value == null) {
			return goodDAO.getAllProductByAccountID(acc.getId());
		}
		return goodDAO.searchProductByName(value, acc.getId());
	}
	
	private int check(List<Buy> list, int id) {
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getId() == id) {
				return i;
			}
		}
		
		return -1;
	}
	
	private void printList(List<Buy> list) {
		System.out.println("----------------------------");
		for(Buy i:list)
			System.out.println(i);
		System.out.println("----------------------------");
	}
	
	@GetMapping("/subProduct/{id}")
	@ResponseBody
	public void subProductOnSession(@PathVariable String id, HttpSession session) {
		Account acc = (Account)session.getAttribute("acc");
	    List<Buy> productList = (List<Buy>) session.getAttribute("productList");
	    
	    int vtri = check(productList, Integer.parseInt(id));
	    if(productList.get(vtri).getQuantity() - 1 == 0) {
	    	productList.remove(vtri);
	    } else {
	    	productList.get(vtri).setQuantity(productList.get(vtri).getQuantity() - 1);
	    }
	    session.setAttribute("productList", productList);
	}
	
	@GetMapping("/delProduct/{id}")
	@ResponseBody
	public void delProductOnSession(@PathVariable String id, HttpSession session) {
		Account acc = (Account)session.getAttribute("acc");
	    List<Buy> productList = (List<Buy>) session.getAttribute("productList");
	    
	    int vtri = check(productList, Integer.parseInt(id));
	    productList.remove(vtri);
	    session.setAttribute("productList", productList);
	}
	
	@GetMapping("/addProduct/{id}")
	@ResponseBody
	public int addProductOnSession(@PathVariable String id, HttpSession session) {
		Account acc = (Account)session.getAttribute("acc");
	    List<Buy> productList = (List<Buy>) session.getAttribute("productList");
	    
	    if (productList == null) {
	    	productList = new ArrayList<>();
	    }
	    
	    int vtri = check(productList, Integer.parseInt(id));
	    
	    if(vtri >= 0) {
	    	if(productList.get(vtri).getQuantity() < productList.get(vtri).getQuantity_remaining()) {
	    		productList.get(vtri).setQuantity(productList.get(vtri).getQuantity() + 1);
	    	}
	    } else {
	    	productList.add(buyDAO.getProductBuy(acc.getId(), Integer.parseInt(id)));
	    }
//		printList(productList);
    	session.setAttribute("productList", productList);
    	
    	return productList.size();
	}
	
	@GetMapping("/listBuy")
	@ResponseBody
	public List<Buy> getListBuy(HttpSession session){
		List<Buy> productList = (List<Buy>) session.getAttribute("productList");
		
		return productList;
	}
	
	@GetMapping("/search_customer")
	@ResponseBody
	public List<Customer> searchCustomer(@RequestParam("q") String value, HttpSession session){
		Account acc = (Account) session.getAttribute("acc");
		value = "%" + value.trim() + "%";
		
		if(value.equals("") || value == null) {
			return cusDAO.getAllCustomerByAccountId(acc.getId());
		}
		
		return cusDAO.searchCus(value.trim().toLowerCase(), acc.getId());
	}
	
	@PostMapping("/save_customer")
	@ResponseBody
	public void saveCustomer(@RequestBody Customer c, HttpSession session) {
		Account acc = (Account) session.getAttribute("acc");
		
		System.out.println(c);
	}
	
	public Timestamp getTimeNow() {
		Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate.getTime());
        long roundedTime = Math.round((double) timestamp.getTime() / 1000) * 1000;
        timestamp.setTime(roundedTime);
		return timestamp;
	}
	
	@PostMapping("/saveBill")
	@ResponseBody
	public ResponseEntity<Map<String, String>> saveBill(@RequestBody Bill b, HttpSession session) {
		List<Buy> productList = (List<Buy>) session.getAttribute("productList");
		Account acc = (Account) session.getAttribute("acc");
		Bill bill = new Bill(b.getCustomerID(), getTimeNow(), b.getTotal(), acc.getId());
	
		buyDAO.addBill(bill, productList);
		productList.clear();
		session.setAttribute("productList", productList);
		
		Map<String, String> response = new HashMap<>();
		response.put("message", "OK");
		return ResponseEntity.ok(response);
	}
	
}
