package cantine.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@RestController
public class User {

    @PersistenceContext
    EntityManager em;

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

}
