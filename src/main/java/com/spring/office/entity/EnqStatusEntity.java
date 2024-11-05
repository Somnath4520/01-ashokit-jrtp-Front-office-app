package com.spring.office.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "AIT_ENQUIRY_STATUS")
@Data
public class EnqStatusEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer statusId;
	
	private String statusName;

}
