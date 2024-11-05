package com.spring.office.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.office.binding.DashboardResp;
import com.spring.office.binding.EnquiryForm;
import com.spring.office.binding.EnquirySearch;
import com.spring.office.constants.AppConstants;
import com.spring.office.entity.StudentEnqEntity;
import com.spring.office.servcie.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private EnquiryService enqService;
	
	@GetMapping("/logout")
	public String logout() {
		//destroying session when user logout
		session.invalidate();
		return "index";
	}
	
	@GetMapping("/dashboard")
	public String dashboardPage( Model model) {
		Integer userId = (Integer) session.getAttribute(AppConstants.USER_ID);
		DashboardResp dashboard = enqService.dashboard(userId);
		model.addAttribute("dashboardData", dashboard);
		
		return "dashboard";
	}
	
	@GetMapping("/add")
	public String addEnquiryPage(Model model) {
		init(model);
		model.addAttribute("enquiryData", new EnquiryForm());
		return AppConstants.RETURN_ADD_ENQUIRY;
	}

	public void init(Model model) {
		List<String> courseNames = enqService.getCourseNames();
		List<String> enqStatus = enqService.getEnqStatus();
		model.addAttribute("enqStatus", enqStatus);
		model.addAttribute("courseNames", courseNames);
	}
	
	@PostMapping("/add")
	public String handleEnquiryForm(@ModelAttribute("enquiryData") EnquiryForm form, Model model) {
		String string = enqService.upsertEnquiry(form);
		model.addAttribute("msg", string);
		return AppConstants.RETURN_ADD_ENQUIRY;
	}
	
	@GetMapping("/view")
	public String viewPage(Model model) {
		init(model);
		Integer userId = (Integer) session.getAttribute(AppConstants.USER_ID);
		List<StudentEnqEntity> enquiries = enqService.getEnquiries(userId);

		model.addAttribute("enquiries", enquiries); 
		return "view-enquiries";
	}
	
	@GetMapping("/filter-enquiries")
	public String filterEnquiries(@RequestParam String name,
								@RequestParam String mode,
								@RequestParam String status,
								Model model) {
		
		EnquirySearch search = new EnquirySearch();
		search.setCourseName(name);
		search.setClassMode(mode);
		search.setEnquiryStatus(status);
		
		Integer userId = (Integer) session.getAttribute(AppConstants.USER_ID);
		List<StudentEnqEntity> filteredEnquiries = enqService.getFilteredEnquiries(userId, search);
		model.addAttribute("enquiries", filteredEnquiries);
		return "filtered-enquiries";
		
	}
	
	
	
	@GetMapping("/edit-enquiry")
	public String updateEnquiry(@RequestParam Integer enqId, Model model) {
		StudentEnqEntity enquiry = enqService.getEnquiry(enqId);
		
		init(model);
		model.addAttribute("enquiryData", enquiry);
		
		return AppConstants.RETURN_ADD_ENQUIRY;
	}
	

}
