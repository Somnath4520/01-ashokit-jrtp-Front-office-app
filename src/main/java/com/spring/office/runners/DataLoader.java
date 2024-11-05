package com.spring.office.runners;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.spring.office.entity.CourseEntity;
import com.spring.office.entity.EnqStatusEntity;
import com.spring.office.repo.CourseRepo;
import com.spring.office.repo.EnqStatusRepo;

@Component
public class DataLoader implements ApplicationRunner{
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo statusRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		courseRepo.deleteAll();
		
		CourseEntity c1 = new CourseEntity();
		c1.setCourseName("Java");
		
		CourseEntity c2 = new CourseEntity();
		c2.setCourseName("Python");
		CourseEntity c3 = new CourseEntity();
		c3.setCourseName("AWS");
		CourseEntity c4 = new CourseEntity();
		c4.setCourseName("DevOps");
		
		courseRepo.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		statusRepo.deleteAll();
		
		EnqStatusEntity s1 = new EnqStatusEntity();
		s1.setStatusName("New");
		
		EnqStatusEntity s2 = new EnqStatusEntity();
		s2.setStatusName("Enrolled");
		
		EnqStatusEntity s3 = new EnqStatusEntity();
		s3.setStatusName("Lost");
		
		statusRepo.saveAll(Arrays.asList(s1,s2,s3));
		
		
		
	}

}
