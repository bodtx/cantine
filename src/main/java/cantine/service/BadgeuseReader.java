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
		String presenceAujourdhui="";
		String resteAfaireAujourdhui="";
		String tpsRecupereAujourdhui="";
		String tpsTotalCummuleAujourdhui="";
		String simulationDepart="";
		
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
			//  si nb mouvement pair, on a autant d'entre que de sortie, on prend directement la présence badgé du jour
			if(nbMouvement % 2==0){
				presenceAujourdhui = pbjHeure.toString()+"h"+pbjMin.toString();
			// sinon presence Badgée du jour + période (dernière entrée à maintenant)
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
				Calendar  calPresence = new GregorianCalendar();
				calPresence.add(Calendar.HOUR, -deHeure);
				calPresence.add(Calendar.MINUTE, -deMin);
				calPresence.add(Calendar.HOUR, pbjHeure);
				calPresence.add(Calendar.MINUTE, pbjMin);
				presenceAujourdhui = calPresence.get(Calendar.HOUR)+"h"+calPresence.get(Calendar.MINUTE);
				
				// si j'ai fait plus 7h48
				if(calPresence.get(Calendar.HOUR)>Constantes.HEURE_OBLIGATOIRE || 
						(calPresence.get(Calendar.HOUR)==Constantes.HEURE_OBLIGATOIRE && calPresence.get(Calendar.MINUTE)>Constantes.MIN_OBLIGATOIRE)){
					
					//tps récupéré Aujourd'hui : presence - 7h48
					Calendar calObligatoire = new GregorianCalendar();
					calObligatoire.set(Calendar.AM_PM, Calendar.AM);
					calObligatoire.set(Calendar.HOUR, Constantes.HEURE_OBLIGATOIRE);
					calObligatoire.set(Calendar.MINUTE, Constantes.MIN_OBLIGATOIRE);
					Calendar calTpsRecupereAujourdhui = new GregorianCalendar();
					calTpsRecupereAujourdhui.set(Calendar.HOUR, calPresence.get(Calendar.HOUR));
					calTpsRecupereAujourdhui.set(Calendar.MINUTE, calPresence.get(Calendar.MINUTE));
					calTpsRecupereAujourdhui.add(Calendar.HOUR, -calObligatoire.get(Calendar.HOUR));
					calTpsRecupereAujourdhui.add(Calendar.MINUTE, -calObligatoire.get(Calendar.MINUTE));
					tpsRecupereAujourdhui= calTpsRecupereAujourdhui.get(Calendar.HOUR)+"h"+calTpsRecupereAujourdhui.get(Calendar.MINUTE);
					
					//calcul du tps total cummulé aujourd'hui : tps cummulé de la veille + tps récupéré Aujourd'hui
					Calendar calTpsTotalCumule = new GregorianCalendar();
					calTpsTotalCumule.set(Calendar.AM_PM, Calendar.AM);
					calTpsTotalCumule.set(Calendar.HOUR, dccvHeure);
					calTpsTotalCumule.set(Calendar.MINUTE, dccvMin);
					calTpsTotalCumule.add(Calendar.HOUR, calTpsRecupereAujourdhui.get(Calendar.HOUR));
					calTpsTotalCumule.add(Calendar.MINUTE, calTpsRecupereAujourdhui.get(Calendar.MINUTE));
					tpsTotalCummuleAujourdhui=calTpsTotalCumule.get(Calendar.HOUR)+"h"+calTpsTotalCumule.get(Calendar.MINUTE);
					
				}else{ // si j'ai fait moins de 7h48
					
					//reste à faire aujourd'hui : 7h48 - presence
					Calendar calResteAFaire = new GregorianCalendar();
					calResteAFaire.set(Calendar.AM_PM, Calendar.AM);
					calResteAFaire.set(Calendar.HOUR, Constantes.HEURE_OBLIGATOIRE);
					calResteAFaire.set(Calendar.MINUTE, Constantes.MIN_OBLIGATOIRE);
					calResteAFaire.add(Calendar.HOUR, -calPresence.get(Calendar.HOUR));
					calResteAFaire.add(Calendar.MINUTE, -calPresence.get(Calendar.MINUTE));
					resteAfaireAujourdhui= calResteAFaire.get(Calendar.HOUR)+"h"+calResteAFaire.get(Calendar.MINUTE);
					
					//tps total cummulé aujourd'hui : tps cummulé de la veille 
					Calendar calTpsTotalCummuleVeille = new GregorianCalendar();
					calTpsTotalCummuleVeille.set(Calendar.AM_PM, Calendar.AM);
					calTpsTotalCummuleVeille.set(Calendar.HOUR, dccvHeure);
					calTpsTotalCummuleVeille.set(Calendar.MINUTE, dccvMin);
					tpsTotalCummuleAujourdhui=calTpsTotalCummuleVeille.get(Calendar.HOUR)+"h"+calTpsTotalCummuleVeille.get(Calendar.MINUTE);
					
					//simulation départ : 7h48 - la presence - tps cummulé veille
					Calendar calSimulationDepart = new GregorianCalendar();
					calSimulationDepart.set(Calendar.AM_PM, Calendar.AM);
					calSimulationDepart.set(Calendar.HOUR, Constantes.HEURE_OBLIGATOIRE);
					calSimulationDepart.set(Calendar.MINUTE, Constantes.MIN_OBLIGATOIRE);
					calSimulationDepart.add(Calendar.HOUR, -calPresence.get(Calendar.HOUR));
					calSimulationDepart.add(Calendar.MINUTE, -calPresence.get(Calendar.MINUTE));
					calSimulationDepart.add(Calendar.HOUR, -calTpsTotalCummuleVeille.get(Calendar.HOUR));
					calSimulationDepart.add(Calendar.MINUTE, -calTpsTotalCummuleVeille.get(Calendar.MINUTE));
					simulationDepart=calSimulationDepart.get(Calendar.HOUR)+"h"+calSimulationDepart.get(Calendar.MINUTE);;
				}
			}
		}
		
		b.setPresenceAujourdhui(presenceAujourdhui);
		b.setResteAfaireAujourdhui(resteAfaireAujourdhui);
		b.setTpsRecupereAujourdhui(tpsRecupereAujourdhui);
		b.setTpsTotalCummuleAujourdhui(tpsTotalCummuleAujourdhui);
		b.setSimulationDepart(simulationDepart);
		
		return b;
	}
	
}
