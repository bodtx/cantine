package cantine.controller;

import cantine.beans.CopainBean;
import cantine.beans.MenuBean;
import cantine.db.Choix;
import cantine.db.Plat;
import cantine.service.MenuReader;
import cantine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
public class Menu {

    @Autowired
    MenuReader menuReader;

    @Autowired
    UserService userService;

    @Autowired
    ChoixRestRepository choixRepo;

    @PersistenceContext
    EntityManager em;

    // @Autowired
    // PlatsRepository platRepo;

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ResponseBody
    public MenuBean getMenu() throws Exception {
        return menuReader.read();
    }

    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public void setChoix(@RequestBody Choix choix) throws Exception {

        final Set<Plat> plats = choix.getPlats();
        for (Plat plat : plats) {
            em.merge(plat);
        }
        choixRepo.save(choix);
    }


    @RequestMapping(value="/copains", method = RequestMethod.GET)
    @ResponseBody
    CopainBean[] getCopains() throws Exception {
        return userService.getCopains();
    }


    @RequestMapping(value = "/menutest", method = RequestMethod.GET)
    @ResponseBody
    Choix getMenuTest() throws Exception {
        final Choix choix = new Choix();
        choix.setNom("toto");
        final HashSet<Plat> arrayList = new HashSet<Plat>();
        final Plat plat = new Plat();
        plat.setNom("miam");
        final Plat plat2 = new Plat();
        plat2.setNom("beurp");
        arrayList.add(plat);
        arrayList.add(plat2);
        choix.setPlats(arrayList);
        choix.setDate(new Timestamp(new Date().getTime()));
        return choix;
    }
}
