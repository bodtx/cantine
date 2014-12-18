package cantine.controller;

import cantine.beans.Departs;
import cantine.service.TisseoService;
import cantine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Tisseo {

	@Autowired
    TisseoService tisseoService;
	@Autowired
	UserService userService;

    @RequestMapping(value="/prochainsPassages")
    @ResponseBody
    Departs prochainsPassages() throws Exception {
    	String login = userService.getUserName();
    	String mdp = userService.getPassWord();
        return tisseoService.prochainsPassages(login, mdp);
    }



}
