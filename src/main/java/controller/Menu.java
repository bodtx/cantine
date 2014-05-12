package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import service.MenuReader;
import beans.MenuBean;

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
