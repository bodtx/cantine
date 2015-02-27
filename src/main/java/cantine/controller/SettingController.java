package cantine.controller;

import cantine.db.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class SettingController {


	@Autowired
	SettingRestRepository settingRepo;

	@RequestMapping(value = "/saveSetting", method = RequestMethod.POST)
	@Transactional
	public void saveSetting(@RequestBody Setting setting) throws Exception {
        settingRepo.save(setting);
	}


    @RequestMapping(value = "/getSetting", method = RequestMethod.GET)
    @Transactional
    public Setting getSetting(@RequestParam String cer) throws Exception {
       return  settingRepo.findOne(cer);
    }




}
