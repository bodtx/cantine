package cantine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Badgeuse {


    @RequestMapping(value="/heure")
    @ResponseBody
    String home(@RequestParam(value = "heure") String heure) throws Exception {
    	
        return heure;
    }

}
