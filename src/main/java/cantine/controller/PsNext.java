package cantine.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

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

    @RequestMapping(value = "/openPs", method = RequestMethod.GET)
    @ResponseBody
    public void openPsNext() throws Exception {
        Runtime runtime = Runtime.getRuntime();
        //runtime.exec(new String[] { "C:\\Program Files (x86)\\Internet Explorer\\iexplore.exe", "https://ressources.rh.recouv/index.php?module=1140"} );
        //TODO a paramétrer
        runtime.exec(new String[] { "C:\\Windows\\SysWOW64\\javaws.exe ", "-localfile", "-J-Djnlp.application.href=http://sciforma.altair.recouv/sciforma/128/WebStart_fr.jnlp",
                "D:\\UTILISATEURS\\CER3100444\\AppData\\LocalLow\\Sun\\Java\\Deployment\\cache\\6.0\\42\\2e7526a-55dccc3d"} );
    }

}
