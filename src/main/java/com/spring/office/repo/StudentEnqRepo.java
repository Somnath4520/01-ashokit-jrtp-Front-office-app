package com.spring.office.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.office.entity.StudentEnqEntity;
import com.spring.office.entity.UserDtlsEntity;


public interface StudentEnqRepo  extends JpaRepository<StudentEnqEntity, Integer>{
	
	List<StudentEnqEntity> findByUser(UserDtlsEntity user);

}
