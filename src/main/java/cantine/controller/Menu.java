package cantine.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.io.IOUtils;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cantine.beans.CopainBean;
import cantine.beans.MenuBean;
import cantine.db.Choix;
import cantine.db.Plat;
import cantine.service.MenuReader;
import cantine.service.UserService;

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

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	@ResponseBody
	public MenuBean getMenu() throws Exception {
		return menuReader.read();
	}

	@RequestMapping(value = "/menuDuJour", method = RequestMethod.GET)
	@ResponseBody
	public String[] getMenuDuJour() throws Exception {
		return menuReader.menuDuJour();
	}

	@RequestMapping(value = "/platImage/{nom}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPlatImgJour(@PathVariable String nom) throws Exception {
		File img = new File(System.getProperty("user.home")+"/img/" + nom + ".jpg");
		InputStream in;
		if (img.exists())
			in = new FileInputStream(img);
		else
			in = this.getClass().getResourceAsStream("/static/img/ko.jpg");
		return IOUtils.toByteArray(in);
	}

	@RequestMapping(value = "/menu", method = RequestMethod.POST)
	@Transactional
	public void setChoix(@RequestBody Choix choix) throws Exception {

		final Set<Plat> plats = choix.getPlats();
		for (Plat plat : plats) {
			em.merge(plat);
		}
		choixRepo.save(choix);
	}

	@RequestMapping(value = "/copains", method = RequestMethod.GET)
	@ResponseBody
	CopainBean[] getCopains() throws Exception {
		return userService.getCopains();
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(
			@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(name + "-uploaded")));
				stream.write(bytes);
				stream.close();
				return "You successfully uploaded " + name + " into " + name
						+ "-uploaded !";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name
					+ " because the file was empty.";
		}
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
