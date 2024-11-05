package com.spring.office.servcie;

import com.spring.office.binding.LoginForm;
import com.spring.office.binding.SignupForm;
import com.spring.office.binding.UnlockForm;

public interface UserService {
	
	public String login(LoginForm loginData);
	
	public boolean signup(SignupForm signupData);
	
	public boolean unlockAccount(UnlockForm unlockData);
	
	public boolean forgotPwd(String email);
	

}
