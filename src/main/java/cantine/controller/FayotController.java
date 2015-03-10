package cantine.controller;

import cantine.db.Fayot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class FayotController {


	@Autowired
	FayotRestRepository fayotRepo;

	@RequestMapping(value = "/saveFayot", method = RequestMethod.POST)
	@Transactional
	public void saveFayot(@RequestBody Fayot f) throws Exception {
        fayotRepo.save(f);
	}


    @RequestMapping(value = "/getFayot", method = RequestMethod.GET)
    @Transactional
    public Fayot getFayot(@RequestParam String cer) throws Exception {
       return  fayotRepo.findOne(cer);
    }

    @RequestMapping(value = "/getFayots", method = RequestMethod.GET)
    @Transactional
    public Fayot[] getFayots() throws Exception {
        Fayot[] result = new Fayot[3];
        int i = 0;
        for (Fayot fayot : fayotRepo.findAll()) {
            result[i]=fayot;
        }
        return result ;
    }
}
