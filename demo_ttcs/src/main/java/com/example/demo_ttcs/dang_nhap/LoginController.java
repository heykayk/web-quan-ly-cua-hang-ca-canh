package com.example.demo_ttcs.dang_nhap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	private loginDAO dao = new loginDAO();
	
	@GetMapping("/dang-nhap")
	public String dangNhap() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(Model model, 
			HttpSession session,
		@RequestParam("username") String username, 
		@RequestParam("password") String password) 
	{
		Account acc = dao.getAccount(username, password);
		if(acc == null) {
			model.addAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!!!");
			return "redirect:/dang-nhap";
		} else {
			session.setAttribute("acc", acc);
			return "redirect:/trang_chu";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate(); 
	    return "redirect:/dang-nhap"; 
	}
}
