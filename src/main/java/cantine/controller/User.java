package cantine.controller;

import cantine.db.Setting;
import cantine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@RestController
public class User {

    @PersistenceContext
    EntityManager em;

    @Autowired
    UserService userService;

    @Autowired
    SettingRestRepository settingRepo;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @Transactional
    public void setUser(@RequestBody cantine.beans.User user) throws Exception {

        Query queryUser = em.createNativeQuery("insert into users values (?,?,true)");
        queryUser.setParameter(1, user.username);
        queryUser.setParameter(2, new BCryptPasswordEncoder().encode(user.password));
        queryUser.executeUpdate();
        
        Query queryAuth = em.createNativeQuery("insert into authorities values (?,'USER')");
        queryAuth.setParameter(1, user.username);
        queryAuth.executeUpdate();
    }


    @RequestMapping(value="/userName")
    @ResponseBody
    String getUserName() throws Exception {
        String cer = userService.getUserName();
        Setting setting = settingRepo.findOne(cer);
        if(setting!=null && !"".equals(setting.getNom())){
            return setting.getNom();
        }
        return userService.getUserName();
    }

    @RequestMapping(value="/cer")
    @ResponseBody
    String getCer() throws Exception {
        return userService.getUserName();
    }

}
