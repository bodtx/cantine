package cantine.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cantine.beans.MenuBean;
import cantine.db.Choix;
import cantine.service.MenuReader;

@RestController
public class Menu {

    @Autowired
    MenuReader menuReader;

    @Autowired
    CrudRepository<Choix, Long> choixRepo;

    @RequestMapping(value="/menu", method= RequestMethod.GET)
    @ResponseBody
    MenuBean getMenu( @CookieValue("nom") String cookie ) throws Exception {
         choixRepo.save(new Choix(cookie));
         System.out.println(choixRepo.count());
        return menuReader.read();
    }
    
    @RequestMapping(value="/menu", method= RequestMethod.POST)
    @ResponseBody
    MenuBean getMenu( @RequestBody Choix plats ) throws Exception {
        // choixRepo.save(new Choix(cookie));
        // System.out.println(choixRepo.count());
        return menuReader.read();
    }

    @RequestMapping(value = "/setUtilisateur/{nom}")
    @ResponseBody
    void setName(@PathVariable("nom") String nom, HttpServletResponse response) throws Exception {
        Cookie cookie = new Cookie("nom", nom);
        cookie.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(cookie);

    }
    
    @RequestMapping(value="/choix", method= RequestMethod.GET)
    @ResponseBody
    List<String> getChoix() throws Exception {
		return new ArrayList<String>(){/**
			 * 
			 */
			private static final long serialVersionUID = 7230125055874274795L;

		{add("toto");add("tutu");}};
    }
}
