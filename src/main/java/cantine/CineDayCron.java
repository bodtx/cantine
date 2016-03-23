package cantine;

import cantine.service.CineService;
import cantine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by CER3100444 on 24/07/2015.
 */
@Component
public class CineDayCron {

    @Autowired
    CineService cineService;
    @Autowired
    UserService userService;

    //@Scheduled(cron = "*/10 * * * * *")
    public void getCineDay() throws IOException, InterruptedException {
        System.out.println("toto");
        String login = userService.getUserName();
        String mdp = userService.getPassWord();
        cineService.getCineDay(login, mdp);
    }
}
