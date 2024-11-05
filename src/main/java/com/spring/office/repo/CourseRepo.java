package com.spring.office.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.office.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer>{
	
	

}
