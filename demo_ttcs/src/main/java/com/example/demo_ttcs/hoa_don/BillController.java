package com.example.demo_ttcs.hoa_don;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo_ttcs.dang_nhap.Account;

import jakarta.servlet.http.HttpSession;

@Controller
public class BillController {
	BillDAO billDAO = new BillDAO();
	
	@GetMapping("/hoa_don")
	public String pageHoaDon(HttpSession session) {
		Account acc = (Account)session.getAttribute("acc");
		if(acc == null) return "redirect:/dang-nhap";
		return "hoa_don";
	}
	
	@GetMapping("/hoa_don/list_bill")
	@ResponseBody
	public List<Bill> getAllBill(HttpSession session){
		Account acc = (Account)session.getAttribute("acc");
		
		return billDAO.getAllBill(acc.getId());
	}
	
	@GetMapping("/hoa_don/{id}")
	@ResponseBody
	public Bill getBillById(@PathVariable String id, HttpSession session) {
		return billDAO.getBillById(Integer.parseInt(id));
	}
	
	@GetMapping("/hoa_don/list_detail/{id}")
	@ResponseBody
	public List<BillDetail> getAllDetaiBill(@PathVariable String id, HttpSession session){
		return billDAO.getAllBillDetailByBillId(Integer.parseInt(id));
	}
	
	@GetMapping("/hoa_don/latest")
	@ResponseBody
	public int getBillLatest(HttpSession session) {
		Account acc = (Account)session.getAttribute("acc");
		return billDAO.getBillLater(acc.getId());
	}
}
