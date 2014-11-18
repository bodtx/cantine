package cantine.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
public class PsNext {

    /**
     * Si c'est vendredi il faut faire son PsNext !!!
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/psNext", method = RequestMethod.GET)
	@ResponseBody
	public boolean getPsNext() throws Exception {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            return true;
        }
        return false;
    }

}
