package cantine.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	public String getUserName() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SecurityContextHolder.getContext().getAuthentication().getCredentials();
		return user.getUsername();
	}
	
	public String getPassWord() {
		String password = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
		return password;
	}
}