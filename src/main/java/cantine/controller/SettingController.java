package cantine.controller;

import cantine.db.Setting;
import cantine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
public class SettingController {


	@Autowired
	SettingRestRepository settingRepo;

    @Autowired
    UserService userService;


	@RequestMapping(value = "/saveSetting", method = RequestMethod.POST)
	@Transactional
	public void saveSetting(@RequestBody Setting setting) throws Exception {
        setting.setCer(userService.getUserName());
        settingRepo.save(setting);
	}


    @RequestMapping(value = "/getSetting", method = RequestMethod.GET)
    @Transactional
    public Setting getSetting(@RequestParam String cer) throws Exception {
       return  settingRepo.findOne(cer);
    }




}
