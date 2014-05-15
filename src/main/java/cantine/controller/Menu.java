package cantine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cantine.beans.MenuBean;
import cantine.service.MenuReader;


@RestController
public class Menu {

    @Autowired
    MenuReader menuReader;

    @RequestMapping("/menu")
    @ResponseBody
    MenuBean home() throws Exception {

        return menuReader.read();
    }

}
