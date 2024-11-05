package com.spring.office.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "AIT_STUDENT_ENQUIRY")
@Data
public class StudentEnqEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer enquiryId;
	private String studentName;
	private Long studentPhno;
	private String classMode;
	private String enquiryStatus;
	private String courseName;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate createDate;
	
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDate updatedDate;
	
	@ManyToOne()
	@JoinColumn(name = "user_id")
	private UserDtlsEntity user;

}
