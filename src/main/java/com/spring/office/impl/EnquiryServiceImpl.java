package com.spring.office.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.office.binding.DashboardResp;
import com.spring.office.binding.EnquiryForm;
import com.spring.office.binding.EnquirySearch;
import com.spring.office.entity.CourseEntity;
import com.spring.office.entity.EnqStatusEntity;
import com.spring.office.entity.StudentEnqEntity;
import com.spring.office.entity.UserDtlsEntity;
import com.spring.office.repo.CourseRepo;
import com.spring.office.repo.EnqStatusRepo;
import com.spring.office.repo.StudentEnqRepo;
import com.spring.office.repo.UserDtlsRepo;
import com.spring.office.servcie.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Service
public class EnquiryServiceImpl implements EnquiryService{
	
	@Autowired
	private UserDtlsRepo userRepo;
	
	@Autowired
	private StudentEnqRepo enqRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo statusRepo;
	
	@Autowired
	HttpSession session;

	@Override
	public List<String> getCourseNames() {
		List<CourseEntity> list = courseRepo.findAll();
		List<String> names=new ArrayList<>();
		for(CourseEntity name:list) {
			 names.add(name.getCourseName());
		}
		return names;
	}

	@Override
	public List<String> getEnqStatus() {
		List<EnqStatusEntity> all = statusRepo.findAll();
		
		List<String> status=new ArrayList<>();
		for(EnqStatusEntity name:all) {
			 status.add(name.getStatusName());
		}
		return status;
	}

	@Override
	public DashboardResp dashboard(Integer userId) {
		
		DashboardResp response = new DashboardResp();
		Optional<UserDtlsEntity> byId = userRepo.findById(userId);
		
		
		if(byId.isPresent()) {
			UserDtlsEntity user = byId.get();
			List<StudentEnqEntity> enquiries = user.getStudentEnquiries();
			Integer total = enquiries.size();
			
			Integer enrolledCnt = enquiries.stream().filter(e-> e.getEnquiryStatus().equals("Enrolled"))
								.collect(Collectors.toList()).size();
			
			Integer lostCnt = enquiries.stream().filter(e-> e.getEnquiryStatus().equals("Lost"))
								.collect(Collectors.toList()).size();
			response.setTotalenquiriesCnt(total);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);
		}
		
		return response;
	}

	@Override
	public String upsertEnquiry(EnquiryForm formData) {
		//get the user based on userId
		Integer userId = (Integer) session.getAttribute("userId");
		Optional<UserDtlsEntity> user = userRepo.findById(userId);
		
		if(user.isPresent()) {
		//copy form data to entity object 
			
		StudentEnqEntity entity = new StudentEnqEntity();
		BeanUtils.copyProperties(formData, entity);
		entity.setUser(user.get());
		
		//save enquiry
		enqRepo.save(entity);
		
		}
		return "enquiry saved";
	}

	@Override
	public List<StudentEnqEntity> getEnquiries(Integer userId) {
		Optional<UserDtlsEntity> userEntity = userRepo.findById(userId);
		if(userEntity.isPresent()) {
			UserDtlsEntity user = userEntity.get();
			return user.getStudentEnquiries();
		}
		
		return Collections.emptyList();
	}

	@Override
	public StudentEnqEntity getEnquiry(Integer enqId) {
		//serach enquiry data by enqid
		Optional<StudentEnqEntity> enqOptional = enqRepo.findById(enqId);
		
		if(enqOptional.isPresent()) {
			 return  enqOptional.get();
		}else {
			return null;
		} 
	}

	@Override
	public List<StudentEnqEntity> getFilteredEnquiries(Integer userId, EnquirySearch search) {
		Optional<UserDtlsEntity> userEntity = userRepo.findById(userId);
		if(userEntity.isPresent()) {
			UserDtlsEntity entity = userEntity.get();
			List<StudentEnqEntity> enquiries = entity.getStudentEnquiries();
			
			//filter logic
			if(search.getCourseName()!=null && !search.getCourseName().equals("")) {
				enquiries = enquiries.stream().filter(e-> e.getCourseName().equals(search.getCourseName()
						)).collect(Collectors.toList());
			}
			
			if(search.getClassMode()!=null && !search.getClassMode().equals("")) {
				enquiries = enquiries.stream().filter(e-> e.getClassMode().equals(search.getClassMode()
						)).collect(Collectors.toList());
			}
			
			if(search.getEnquiryStatus()!=null && !search.getEnquiryStatus().equals("")) {
				enquiries = enquiries.stream().filter(e-> e.getEnquiryStatus().equals(search.getEnquiryStatus()
						)).collect(Collectors.toList());
			}
			return enquiries;
		}
		return Collections.emptyList();
	}


}
