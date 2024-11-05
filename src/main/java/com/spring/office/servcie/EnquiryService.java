package com.spring.office.servcie;

import java.util.List;

import com.spring.office.binding.DashboardResp;
import com.spring.office.binding.EnquiryForm;
import com.spring.office.binding.EnquirySearch;
import com.spring.office.entity.StudentEnqEntity;


public interface EnquiryService {
	
	//get all courses
	public List<String> getCourseNames();
	
	//get all status
	public List<String> getEnqStatus();
	
	//get the data for dashboard page
	public DashboardResp dashboard(Integer userId);
	
	//add or update enquiry
	public String upsertEnquiry(EnquiryForm formData);
	
	//get all enquiries based on user
	public List<StudentEnqEntity> getEnquiries(Integer userId);
	
	//get particular enquiry based on enq id for update
	public StudentEnqEntity getEnquiry(Integer enqId);
	
	//get all enquiries based on search criteria
	public List<StudentEnqEntity> getFilteredEnquiries(Integer userId,EnquirySearch search);
	

}
