package cantine.controller;

import cantine.beans.BadgeuseBean;
import cantine.service.BadgeuseReader;
import cantine.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Badgeuse {

	@Autowired
	BadgeuseReader badgeuseReader;
	@Autowired
	UserService userService;

    @RequestMapping(value="/badgeInfo")
    @ResponseBody
    BadgeuseBean badgeInfo() throws Exception {
    	String login = userService.getUserName();
    	String mdp = userService.getPassWord();
    	return badgeuseReader.getBadgeInfos(login, mdp);
    }
    
    @RequestMapping(value="/userName")
    @ResponseBody
    String getUserName() throws Exception {
    	return userService.getUserName();
    }
	
//    @RequestMapping(value="/heure")
//    @ResponseBody
//    BadgeuseBean home(@RequestParam(value = "heure") String heure) throws Exception {
//    }

}
