package cantine.service;

import java.io.File;
import java.io.FileFilter;

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
            throw new Exception("il devrait y avoir un fichier .eml dans le répertoire " + emlPath);
        } else {
            String fileToString = FileUtils.readFileToString(files[0]);
            fileToString = StringUtils.remove(fileToString, "=" + StringUtils.CR);
            fileToString = StringUtils.remove(fileToString, StringUtils.LF);
            fileToString = StringUtils.replace(fileToString, "=E9", "é");
            fileToString = StringUtils.replace(fileToString, "=E0", "à");
            fileToString = StringUtils.replace(fileToString, "=F4", "ô");
            menuBean.setSemaine(files[0].getName());
            String[][] trtd = readTable(fileToString);
            menuBean.setPlats(trtd);
        }
        return menuBean;

    }

    /**
     * @param fileToString
     * @return
     */
    public String[][] readTable(String fileToString) {
        String[][] trtd = null;
        Document doc = Jsoup.parse(fileToString);
        Elements tables = doc.select("table");
        Elements trs = tables.get(0).select("tr");
        trtd = new String[trs.size()][];
        for (int i = 0; i < trs.size(); i++) {
            Elements tds = trs.get(i).select("td");

            trtd[i] = new String[6];
            for (int j = 0; j < tds.size(); j++) {
                trtd[i][j] = tds.get(j).text();
            }

            // Cas du colspan des grillades
            switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
                trtd[i][2] = trtd[i][1];
                trtd[i][3] = trtd[i][1];
                trtd[i][4] = trtd[i][1];
                trtd[i][5] = trtd[i][1];
                break;

            }

        }

        // lignes vident
        trtd = ArrayUtils.removeAll(trtd, 3, 8);

        return trtd;
    }
}
