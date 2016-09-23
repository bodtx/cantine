package cantine.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import cantine.beans.BadgeuseBean;
import cantine.beans.Temptation;

@Service
public class BadgeuseReader {

	Temptation bms = null;

	// TODO voir les exceptions
	public BadgeuseBean read(String login, String mdp) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://gtabadgepc.rh.recouv/webquartz/acq/badge.do");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("USERID", login));
		nvps.add(new BasicNameValuePair("PASSWORD", mdp));
		nvps.add(new BasicNameValuePair("Cpts", "Afficher+compteurs"));
		nvps.add(new BasicNameValuePair("Connexion", "y"));
		nvps.add(new BasicNameValuePair("DECALHOR", "-120"));
		nvps.add(new BasicNameValuePair("TIME", "1401711007956"));
		CloseableHttpResponse response2 = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		BadgeuseBean b = new BadgeuseBean();

		boolean connexionok = false;

		int nbTentative = 0;

		while (!connexionok && nbTentative < 100) {
			try {
				response2 = httpclient.execute(httpPost);

				HttpEntity entity2 = response2.getEntity();
				final String body = EntityUtils.toString(entity2);
				String[][] trtd = null;
				Document doc = Jsoup.parse(body);
				Elements tables = doc.getElementsByClass("acqArray");

				if (tables.size() > 0) {
					connexionok = true;
					// premier tableau de la controllers
					Elements trs = tables.get(0).select("tr");
					trtd = new String[trs.size()][];
					for (int i = 0; i < trs.size(); i++) {
						Elements tds = trs.get(i).select("td");

						trtd[i] = new String[tds.size()];
						for (int j = 0; j < tds.size(); j++) {
							trtd[i][j] = tds.get(j).text();
						}
					}

					// si le nombre de mouvements est pair, il manque un badge d'entrée
					if (trtd == null || trtd.length == 0 || trtd.length % 2 == 0) {
							b.setAstuBadge(false);
					}else{
                        b.setAstuBadge(true);
                    }

					// 2eme tableau de la controllers
					Elements trs2 = tables.get(2).select("tr");
					String[][] trtd2 = null;
					trtd2 = new String[trs2.size()][];
					for (int i = 0; i < trs2.size(); i++) {
						Elements tds2 = trs2.get(i).select("td");

						trtd2[i] = new String[tds2.size()];
						for (int j = 0; j < tds2.size(); j++) {
							trtd2[i][j] = tds2.get(j).text();
						}
					}
					bms = new Temptation(trtd, trtd2);

					try {
						EntityUtils.consume(entity2);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					nbTentative++;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			response2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bms == null) {
			// TODO Pb si mot de passe de notre application différent du mot de passe Anais il faut envoyer une
			// exception et afficher un message
		}
		b.setNbTentativeConnexion(nbTentative);
		b.setEntreeSortieList(bms.getBMouvList());
		return b;
	}

	public BadgeuseBean getBadgeInfos(String login, String mdp) {
		BadgeuseBean b = read(login, mdp);

		// traitement badge Infos
		updateBeanWithcalcul(b);

		return b;
	}

	public void updateBeanWithcalcul(BadgeuseBean b) {

		b.setPresenceAujourdhui(bms.getPresenceBadgeJour().toString());
		if (bms.getTempsPauseMidi() != null) {
			b.setPauseMidi(bms.getTempsPauseMidi().toString());
		}

		if (bms.getResteAfaireAujourdhui().isNegative()) {
			// si j'ai fait plus 7h48
			// tps récupéré Aujourd'hui : presence - 7h48
			b.setTpsRecupereAujourdhui(bms.getResteAfaireAujourdhui().abs().toString());
			// tmp recup veille plus temps fait aujourd'hui
			b.setTpsTotalCummuleAujourdhui(bms.getDcCumuleVeille().toString());
			// rien à faire
			b.setResteAfaireAujourdhui("");
		} else {
			// si j'ai fait moins de 7h48
			// reste à faire aujourd'hui : 7h48 - presence
			b.setResteAfaireAujourdhui(bms.getResteAfaireAujourdhui().toString());
			// tps total cummulé aujourd'hui : tps cummulé de la veille
			b.setTpsTotalCummuleAujourdhui(bms.getDcCumuleVeille().toString());
			b.setTpsRecupereAujourdhui("");
		}
		// simulation départ : la presence + tps cummulé veille - 7h48
		b.setSimulationDepart(
				bms.getDcCumuleVeille().plus(bms.getPresenceBadgeJour()).minus(bms.getJOURNEEMIN()).toString());
	}

}
