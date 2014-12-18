package cantine.controller;

import cantine.beans.Departs;
import cantine.beans.StationVelib;
import cantine.service.TisseoService;
import cantine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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

    @RequestMapping(value="/problemeTisseo")
    @ResponseBody
    boolean problemeTisseo() throws Exception {
        String login = userService.getUserName();
        String mdp = userService.getPassWord();
        return tisseoService.problemeTisseo(login, mdp);
    }

    @RequestMapping(value="/velib")
    @ResponseBody
    List<StationVelib> velib() throws Exception {
        String login = userService.getUserName();
        String mdp = userService.getPassWord();
        return tisseoService.velib(login, mdp);
    }


}
