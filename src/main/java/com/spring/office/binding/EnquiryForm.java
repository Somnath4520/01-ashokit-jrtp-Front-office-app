package com.spring.office.binding;

import lombok.Data;

@Data
public class EnquiryForm {
	private Integer enquiryId;
	private String studentName;
	
	private Long studentPhno;
	private String classMode;
	private String courseName;
	
	private String enquiryStatus;
	

}
