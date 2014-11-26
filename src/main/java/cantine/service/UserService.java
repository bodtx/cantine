package cantine.service;

import cantine.beans.CopainBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	public String getUserName() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getUsername();
	}
	
	public String getPassWord() {
		String password = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
		return password;
	}

    //TODO récupérer les copains de l'utilisateur
    public  CopainBean[] getCopains(){
        CopainBean[] copains = new CopainBean[3];

        CopainBean aurelien = new CopainBean("DIJOUX", true);
        CopainBean patboc = new CopainBean("PATIES", true);
        CopainBean alex = new CopainBean("KRIER", false);

        copains[0] = aurelien;
        copains[1]= patboc;
        copains[2]= alex;

        return  copains;
    }
}
