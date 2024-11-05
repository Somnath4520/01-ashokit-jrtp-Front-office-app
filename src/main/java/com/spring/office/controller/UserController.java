package com.spring.office.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.office.binding.LoginForm;
import com.spring.office.binding.SignupForm;
import com.spring.office.binding.UnlockForm;
import com.spring.office.constants.AppConstants;
import com.spring.office.servcie.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String loginForm(Model model) {
		LoginForm form = new LoginForm();
		model.addAttribute("loginData", form);
		return "login";
	}
	
	@PostMapping("/login")
	public String handleLoginForm(@ModelAttribute("loginData") LoginForm form, Model model) {
		
			String loginMsg = userService.login(form);
			if(loginMsg.contains("success")) {
				return "redirect:/dashboard";
		}else {
			model.addAttribute(AppConstants.ERROR_MSG, "Email or password invalid!");
			return "login";
		}
	}
	
	@GetMapping("/signup")
	public String signupform(Model model) {
		SignupForm form = new SignupForm();
		model.addAttribute("user", form);
		return "signup";
	}
	
	@PostMapping("/signup")
	public String handleSignup(@ModelAttribute("user") SignupForm formData, Model model) {
		
		boolean status = userService.signup(formData);
		if(status) {
			model.addAttribute("msg", "Account created... Check your mail");
		}else {
			model.addAttribute(AppConstants.ERROR_MSG, "Choose unique mail!");
		}
		
		return "signup";
	}
	
	
	@GetMapping("/unlock")
	public String unlockForm(@RequestParam String email,Model model) {
		
		//take email from query param and set to obj
		UnlockForm objForm = new UnlockForm();
		objForm.setEmail(email);
		model.addAttribute("unlockObj", objForm);
		
		return "unlock";
	}
	
	@PostMapping("/unlock")
	public String handleUnlockForm(@ModelAttribute("unlockObj") UnlockForm form, Model model) {
		if(form.getNewPwd().equals(form.getReNewPwd())) {
		boolean status = userService.unlockAccount(form);
		if(status) {
			model.addAttribute("successMsg", "Your account is unlocked successfully..");
			
		}else {
			model.addAttribute(AppConstants.ERROR_MSG, "Invalid temporary password! Check your email");
		}
		}else {
			model.addAttribute(AppConstants.ERROR_MSG, " New password and Confirm password does not match!");
		}
		
		return "unlock";
	}

	@GetMapping("/forgot")
	public String forgotForm() {
		return "forgotPwd";
	}
	
	@PostMapping("/forgot")
	public String handleForgotPage(@RequestParam("email") String email, Model model) {
		boolean status = userService.forgotPwd(email);
		if(status) {
			
			model.addAttribute("successMsg", "Email sent to your mail with your password");
			
		}else {
			model.addAttribute(AppConstants.ERROR_MSG, "No account found!");
			
		}
		return "forgotPwd";
		
	}
}
