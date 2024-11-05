package com.spring.office.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.office.binding.LoginForm;
import com.spring.office.binding.SignupForm;
import com.spring.office.binding.UnlockForm;
import com.spring.office.constants.AppConstants;
import com.spring.office.entity.UserDtlsEntity;
import com.spring.office.helper.EmailUtils;
import com.spring.office.helper.PwdUtils;
import com.spring.office.repo.UserDtlsRepo;
import com.spring.office.servcie.UserService;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDtlsRepo userRepo;
	
	@Autowired
	private EmailUtils emailSender;
	
	@Autowired
	private HttpSession session;

	@Override
	public String login(LoginForm form) {
		
			UserDtlsEntity user = userRepo.findByEmailAndPassword(form.getEmail(), form.getPassword());
			
			if(user==null) {
				return AppConstants.INAVLID_MSG;
			}
			if(user.getAccStatus().equals(AppConstants.STR_LOCKED)) {
				return AppConstants.ACC_LOCKED_MSG;
			}
			
			//set user data to session
			session.setAttribute("userId", user.getUserId());
			
			return AppConstants.SUCCESS_MSG;
		
	}

	@Override
	public boolean signup(SignupForm signupData) {
		//check user is present in db with given mail
		UserDtlsEntity user = userRepo.findByEmail(signupData.getEmail());
		if(user!=null) {
			return false;
		}		
		
		//copy the form data to entity object
		UserDtlsEntity entity = new UserDtlsEntity();
		entity.setUserName(signupData.getUserName());
		entity.setEmail(signupData.getEmail());
		entity.setPhno(signupData.getPhno());
		
		//generate random pwd and set to object
		String tempPwd = PwdUtils.generatePassword();
		entity.setPassword(tempPwd);
		
		//set acc status as locked
		entity.setAccStatus(AppConstants.STR_LOCKED);
		//save the user
		userRepo.save(entity);
		//send a mail  to unlock your account
		
		String subject = AppConstants.UNLOCK_EMAIL_SUBJECT;
		String to = signupData.getEmail();
		StringBuilder body =  new StringBuilder("");
		body.append("<h1>Use below temporary password to unlock your account</h1>");
		
		body.append("Temporary password: "+ tempPwd);
		body.append("</br>");
		body.append("<a href=\"http://localhost:8080/unlock?email="+ to +"\">click here to unlock your account</a>");
		
		emailSender.sendMail(subject, body.toString(), to);
		return true;
		
		
	}

	@Override
	public boolean unlockAccount(UnlockForm formData) {
		//fetch the user by form email
		UserDtlsEntity user = userRepo.findByEmail(formData.getEmail());
		//check temp pwd is valid or not
		if(user.getPassword().equals(formData.getTempPwd())) {
				//set new pwd
				user.setPassword(formData.getNewPwd());
				//set acc status as unlocked
				user.setAccStatus(AppConstants.STR_UNLOCKED);
				//save the user
				userRepo.save(user);
				return true;
		}else {
			
			return false;
		}
	}

	@Override
	public boolean forgotPwd(String email) {
		//check record present in db with given email
		UserDtlsEntity entity = userRepo.findByEmail(email);
		
		//if record not available return false
		if(entity==null) {
			return false;
		}
		//if record available send mail with pwd and return true
		else {
			String subject = AppConstants.FORGOT_PW_EMAIL_SUBJECT;
			String to = email;
			StringBuilder body =  new StringBuilder("");
			body.append("<h1>Use below temporary password to login your account</h1>");
			body.append("</br>");
			body.append("Your password is :: "+ entity.getPassword());
			emailSender.sendMail(subject, body.toString(), to);
			return true;
		}
		
	}

}
