package cantine.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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
import cantine.utils.Constantes;

@Service
public class BadgeuseReader {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public BadgeuseBean read() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(
				"http://gtabadge.rh.recouv/webquartzacq/acq/badge.do");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("USERID", "CER3100444"));
		nvps.add(new BasicNameValuePair("PASSWORD", "AuriDij974"));
		nvps.add(new BasicNameValuePair("Cpts", "Afficher+compteurs"));
		nvps.add(new BasicNameValuePair("Connexion", "y"));
		nvps.add(new BasicNameValuePair("DECALHOR", "-120"));
		nvps.add(new BasicNameValuePair("TIME", "1401711007956"));
		CloseableHttpResponse response2 = null;
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));

		BadgeuseBean b = new BadgeuseBean();

		boolean connexionok = false;
		
		int nbTentative = 0;

		while (!connexionok && nbTentative<100) {
			response2 = httpclient.execute(httpPost);

			// System.out.println(response2.getStatusLine());

			HttpEntity entity2 = response2.getEntity();
			final String body = EntityUtils.toString(entity2);
			// System.out.println(body);

			String[][] trtd = null;
			Document doc = Jsoup.parse(body);
			Elements tables = doc.getElementsByClass("acqArray");
			if (tables.size() > 0) {
				connexionok = true;
				// premier tableau de la badgeuse
				Elements trs = tables.get(0).select("tr");
				trtd = new String[trs.size()][];
				for (int i = 0; i < trs.size(); i++) {
					Elements tds = trs.get(i).select("td");

					trtd[i] = new String[tds.size()];
					for (int j = 0; j < tds.size(); j++) {
						trtd[i][j] = tds.get(j).text();
					}
				}

				b.setMouvements(trtd);

				// 2eme tableau de la badgeuse
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

				b.setPresenceBadgeJour(trtd2[0][1]);
				b.setDCJour(trtd2[1][1]);
				b.setDCCumuleVeille(trtd2[2][1]);
				b.setDCCumule(trtd2[3][1]);
				b.setDCPeriodeVeille(trtd2[4][1]);
				b.setDCPeriodique(trtd2[5][1]);

				EntityUtils.consume(entity2);
			}else{
				nbTentative++;
			}

		}
		response2.close();
		
		b.setNbTentativeConnexion(nbTentative);

		return b;
	}

	public BadgeuseBean getBadgeInfos() {
		BadgeuseBean b = null;
		try {
			b = read();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//traitement badge Infos
		//presenceAujourd'hui :
		// - si c'est paire, on a autant d'entre que de sortie, on prend directement la présence badgé du jour
		// - sinon presence Badgée du jour + période (dernière entrée à maintenant)
		
		String presenceAujourdhui="";
		String resteAfaireAujourdhui="";
		String tpsRecupereAujourdhui="";
		String tpsTotalCummuleAujourdhui="";
		
		//recuperation de la presence badgée du jour
		String pbj = b.getPresenceBadgeJour();
		StringTokenizer st = new StringTokenizer(pbj, ".");
		Integer pbjHeure=0;
		Integer pbjMin=0;
		
		while (st.hasMoreElements()) {
			pbjHeure =  new Integer((String) st.nextElement());
			pbjMin= new Integer((String) st.nextElement());
		}
		
		//recuperation du temps cummulé
		String dccv = b.getDCCumuleVeille();
		StringTokenizer st3 = new StringTokenizer(dccv, ".");
		Integer dccvHeure=0;
		Integer dccvMin=0;
		
		while (st3.hasMoreElements()) {
			dccvHeure =  new Integer((String) st3.nextElement());
			dccvMin= new Integer((String) st3.nextElement());
		}
		
		int nbMouvement = b.getMouvements().length;
		if(nbMouvement>0){
			if(nbMouvement % 2==0){
				presenceAujourdhui = pbjHeure.toString()+"h"+pbjMin.toString();
			}else{
				//recupéraion de la dernière heure d'entrée
				String derniereEntree="";
				derniereEntree = b.getMouvements()[nbMouvement-1][1];
				
				StringTokenizer st2 = new StringTokenizer(derniereEntree, ".");
				Integer deHeure=0;
				Integer deMin=0;
				
				while (st2.hasMoreElements()) {
					deHeure =  new Integer((String) st2.nextElement());
					deMin= new Integer((String) st2.nextElement());
				}
				
				//Calcul de la présence 
				Calendar  c = new GregorianCalendar();
				c.add(Calendar.HOUR, -deHeure);
				c.add(Calendar.MINUTE, -deMin);
				c.add(Calendar.HOUR, pbjHeure);
				c.add(Calendar.MINUTE, pbjMin);
				presenceAujourdhui = c.get(Calendar.HOUR)+"h"+c.get(Calendar.MINUTE);
				
				//Calcul du reste à faire et du tps récupéré Aujourd'hui
				// si j'ai déjà fait 7h48
				if(c.get(Calendar.HOUR)>Constantes.HEURE_OBLIGATOIRE || 
						(c.get(Calendar.HOUR)==Constantes.HEURE_OBLIGATOIRE && c.get(Calendar.MINUTE)>Constantes.MIN_OBLIGATOIRE)){
					Calendar c3 = new GregorianCalendar();
					c3.set(Calendar.AM_PM, Calendar.AM);
					c3.set(Calendar.HOUR, Constantes.HEURE_OBLIGATOIRE);
					c3.set(Calendar.MINUTE, Constantes.MIN_OBLIGATOIRE);
					c.add(Calendar.HOUR, -c3.get(Calendar.HOUR));
					c.add(Calendar.MINUTE, -c3.get(Calendar.MINUTE));
					tpsRecupereAujourdhui= c.get(Calendar.HOUR)+"h"+c.get(Calendar.MINUTE);
					
					
					//calcul du tps total cummulé aujourd'hui
					Calendar c4 = new GregorianCalendar();
					c4.set(Calendar.AM_PM, Calendar.AM);
					c4.set(Calendar.HOUR, dccvHeure);
					c4.set(Calendar.MINUTE, dccvMin);
					c4.add(Calendar.HOUR, c.get(Calendar.HOUR));
					c4.add(Calendar.MINUTE, c.get(Calendar.MINUTE));
					tpsTotalCummuleAujourdhui=c4.get(Calendar.HOUR)+"h"+c4.get(Calendar.MINUTE);
					
				}else{
					Calendar c2 = new GregorianCalendar();
					c2.set(Calendar.AM_PM, Calendar.AM);
					c2.set(Calendar.HOUR, Constantes.HEURE_OBLIGATOIRE);
					c2.set(Calendar.MINUTE, Constantes.MIN_OBLIGATOIRE);
					c2.add(Calendar.HOUR, -c.get(Calendar.HOUR));
					c2.add(Calendar.MINUTE, -c.get(Calendar.MINUTE));
					resteAfaireAujourdhui= c2.get(Calendar.HOUR)+"h"+c2.get(Calendar.MINUTE);
					
					//calcul du tps total cummulé aujourd'hui
					Calendar c4 = new GregorianCalendar();
					c4.set(Calendar.AM_PM, Calendar.AM);
					c4.set(Calendar.HOUR, dccvHeure);
					c4.set(Calendar.MINUTE, dccvMin);
					tpsTotalCummuleAujourdhui=c4.get(Calendar.HOUR)+"h"+c4.get(Calendar.MINUTE);
				}
			}
		}
		
		b.setPresenceAujourdhui(presenceAujourdhui);
		b.setResteAfaireAujourdhui(resteAfaireAujourdhui);
		b.setTpsRecupereAujourdhui(tpsRecupereAujourdhui);
		b.setTpsTotalCummuleAujourdhui(tpsTotalCummuleAujourdhui);
		
		return b;
	}
	
}
