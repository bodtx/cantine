package service;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import beans.MenuBean;

@Service
public class MenuReader {
    synchronized public MenuBean read() throws Exception {
        final String emlPath = "C:/Users/CER3190252/Documents/cantine";
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
            trtd[i] = new String[tds.size()];
            for (int j = 0; j < tds.size(); j++) {
                // if (tds.size() == 6 )
                trtd[i][j] = tds.get(j).text();
                // else
                // trtd[i][j] = "";
            }
        }
        return trtd;
    }
}
