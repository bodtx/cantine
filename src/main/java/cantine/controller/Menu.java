package cantine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cantine.beans.MenuBean;
import cantine.service.MenuReader;

@RestController
public class Menu {

    @Autowired
    MenuReader menuReader;

    @Autowired
    ChoixRestRepository choixRepo;

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ResponseBody
    MenuBean getMenu() throws Exception {
        // final Choix choix = new Choix();
        // choix.setNom("toto");
        // final ArrayList<String> arrayList = new ArrayList<String>();
        // arrayList.add("miam");
        // arrayList.add("beurp");
        // choix.setPlats(arrayList);
        // choix.setDate(new Timestamp(new Date().getTime()));
        // choixRepo.save(choix);
        return menuReader.read();
    }
}
