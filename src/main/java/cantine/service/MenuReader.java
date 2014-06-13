package cantine.service;

import java.io.File;
import java.io.FileFilter;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import cantine.beans.MenuBean;

@Service
public class MenuReader {
	synchronized public MenuBean read() throws Exception {

		final String emlPath = System.getProperty("user.home");
		File dir = new File(emlPath);
		FileFilter fileFilter = new RegexFileFilter("^.*eml$");
		File[] files = dir.listFiles(fileFilter);
		final MenuBean menuBean = new MenuBean();
		if (files.length != 1) {
			throw new Exception(
					"il devrait y avoir un fichier .eml dans le répertoire "
							+ emlPath);
		} else {
			String fileToString = FileUtils.readFileToString(files[0]);
			fileToString = StringUtils.remove(fileToString, "="
					+ StringUtils.CR);
			fileToString = StringUtils.remove(fileToString, StringUtils.LF);
			fileToString = StringUtils.replace(fileToString, "=E9", "é");
			fileToString = StringUtils.replace(fileToString, "=E8", "é");
			fileToString = StringUtils.replace(fileToString, "=E0", "à");
			fileToString = StringUtils.replace(fileToString, "=F4", "ô");
			fileToString = StringUtils.replace(fileToString, "=EA", "ê");
			fileToString = StringUtils.replace(fileToString, "=E7", "ç");
			menuBean.setSemaine(files[0].getName().substring(12,
					files[0].getName().length() - 4));
			String[][] trtd = readMenu(fileToString);
			menuBean.setPlats(trtd);
		}
		return menuBean;

	}

	/**
	 * @param fileToString
	 * @return
	 */
	public String[][] readSalade(String fileToString) {
		String[][] trtd = null;
		Document doc = Jsoup.parse(fileToString);
		Elements tables = doc.select("table");
		Elements trs = tables.get(1).select("tr");
		trtd = new String[trs.size()][];
		for (int i = 0; i < trs.size(); i++) {
			Elements tds = trs.get(i).select("td");

			trtd[i] = new String[tds.size()];
			for (int j = 0; j < tds.size(); j++) {
				trtd[i][j] = tds.get(j).text();
			}
		}
		return trtd;
	}

	/**
	 * @param fileToString
	 * @return
	 */
	public String[][] readMenu(String fileToString) {
		String[] legumeCarte = null;
		String[][] trtd = null;
		Document doc = Jsoup.parse(fileToString);
		Elements tables = doc.select("table");
		Elements trs = tables.get(0).select("tr");
		int nbLignes = trs.size() + 1 + 5;
		trtd = new String[nbLignes][]; // ligne de légume vapeur
										// + 5 salades
		for (int i = 0; i < nbLignes; i++) {
			trtd[i] = new String[6];

			if (i < trs.size()) {
				Elements tds = trs.get(i).select("td");

				for (int j = 0; j < tds.size(); j++) {
					trtd[i][j] = tds.get(j).text();
				}
			}

			switch (i) {
			// Cas du colspan des grillades
			case 4:
			case 5:
			case 6:
			case 7:
				trtd[i][2] = trtd[i][1];
				trtd[i][3] = trtd[i][1];
				trtd[i][4] = trtd[i][1];
				trtd[i][5] = trtd[i][1];
				break;
			case 11:
				legumeCarte=trtd[i][1].split("/");
				setPlatSpan(trtd, i, legumeCarte[0], "Légume vapeur");
				break;
			case 12:
				setPlatSpan(trtd, i, StringUtils.remove(legumeCarte[1], "au choix"), "Légume vapeur");
				break;
			case 13:
				setPlatSpan(trtd, i, "AMERICAINE", "Salade");
				break;
			case 14:
				setPlatSpan(trtd, i, "MONTAGNARDE", "Salade");
				break;
			case 15:
				setPlatSpan(trtd, i, "DANOISE", "Salade");
				break;
			case 16:
				setPlatSpan(trtd, i, "CATALANE", "Salade");
				break;
			case 17:
				setPlatSpan(trtd, i, "NICOISE", "Salade");
				break;
			}

		}

		// lignes vident
		trtd = ArrayUtils.removeAll(trtd, 3, 8);

		return trtd;
	}

	private void setPlatSpan(String[][] trtd, int idxLigne, String intitule, String nomIdx0) {
		trtd[idxLigne][0] = nomIdx0;
		trtd[idxLigne][1] = intitule;
		trtd[idxLigne][2] = intitule;
		trtd[idxLigne][3] = intitule;
		trtd[idxLigne][4] = intitule;
		trtd[idxLigne][5] = intitule;
	}

	
}
