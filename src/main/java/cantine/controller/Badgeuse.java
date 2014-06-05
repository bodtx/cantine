package cantine.controller;

import cantine.beans.BadgeuseBean;
import cantine.service.BadgeuseReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Badgeuse {

	@Autowired
	BadgeuseReader badgeuseReader;

    @RequestMapping(value="/badgeInfo")
    @ResponseBody
    BadgeuseBean badgeInfo() throws Exception {
    	return badgeuseReader.getBadgeInfos();
    }
	
    @RequestMapping(value="/heure")
    @ResponseBody
    BadgeuseBean home(@RequestParam(value = "heure") String heure) throws Exception {
    	return badgeuseReader.read();
    }

}
