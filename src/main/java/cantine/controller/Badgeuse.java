package cantine.controller;

import cantine.beans.BadgeuseBean;
import cantine.service.BadgeuseReader;
import cantine.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value="/asTuBadge")
    @ResponseBody
    boolean asTuBadge() throws Exception {
        String login = userService.getUserName();
        String mdp = userService.getPassWord();
        BadgeuseBean b = badgeuseReader.read(login, mdp);
        return  b.isAstuBadge();
    }
    
    @RequestMapping(value="/userName")
    @ResponseBody
    String getUserName() throws Exception {
    	return userService.getUserName();
    }


    @RequestMapping(value = "/openTemptation", method = RequestMethod.GET)
    @ResponseBody
    public void openTemptation() throws Exception {
        Runtime runtime = Runtime.getRuntime();
        runtime.exec(new String[] { "C:\\Program Files (x86)\\Internet Explorer\\iexplore.exe", "https://ressources.rh.recouv/index.php?module=1140"} );
    }
	

}
