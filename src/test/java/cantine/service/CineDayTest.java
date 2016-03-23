package cantine.service;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class CineDayTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

//    @Test
    public final void testEncryptMdp() throws Exception {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("toto");
        String mdpEncrypt=textEncryptor.encrypt(""); //Mets ton mot de passe Orange en clair :/
        System.out.println(mdpEncrypt);
    }


    @Test
    public final void testCineDay() throws Exception {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("toto");

        try (final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38, "proxy1.cer31.recouv", 8000)) {

            // set proxy username and password
            final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
            credentialsProvider.addCredentials("CER3100441", "Toulouse01");

            // 1. Authentification
            webClient.getOptions().setJavaScriptEnabled(false);
            final HtmlPage page1 = webClient.getPage("http://id.orange.fr/auth_user/bin/auth_user.cgi?cas=nowg&return_url=http%3A%2F%2Fmdsp.orange.fr%2Fcineday%2Fcommande.xhtml");
            final HtmlForm form = page1.getForms().get(0);
            HtmlSubmitInput submitButton = (HtmlSubmitInput) form.getElementsByAttribute("input", "type", "submit").get(0);
            final HtmlTextInput userId = form.getInputByName("credential");
            final HtmlPasswordInput passwd = form.getInputByName("password");
            // Change the value of the text field
            userId.setValueAttribute("dijoux.aurelien@orange.fr");
            passwd.setValueAttribute(textEncryptor.decrypt("/NnHJSQeX0V2HsI7+6LdRKbEqAt0pg2b")); //mot de passe crypté
            submitButton.click();

            // 2. Commande des codes
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            final HtmlPage pageCommandeCineDays = webClient.getPage("http://mdsp.orange.fr/cineday/commande.xhtml");

            HtmlPage pageCineDays = null;
            List<HtmlAnchor> anchors = pageCommandeCineDays.getAnchors();
            for (HtmlAnchor htmlAnchor : anchors) {
                if (htmlAnchor.getAttribute("id").contains("form_compte_0:j_idt")) {
                    htmlAnchor.click();
                    Thread.sleep(5000);
                    pageCineDays = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
                    break;
                }
            }


            if (pageCineDays != null) {
                String codes = pageCineDays.asText().substring(pageCineDays.asText().indexOf("compte Internet+Mobile") + "compte Internet+Mobile".length(),
                        pageCineDays.asText().indexOf("› Envoyer ces codes par sms au"));

                //envoie du code
                final HtmlForm formEnvoieCode = pageCineDays.getForms().get(1);

                //envoie du code par sms
                HtmlSubmitInput sendSms = (HtmlSubmitInput) formEnvoieCode
                        .getElementsByAttribute("input", "type", "submit").get(0);
                final HtmlTextInput telephone = formEnvoieCode.getInputByName("form_compte_0:input_phone_b_0");
                telephone.setValueAttribute("0604501870");
                sendSms.click();
                Thread.sleep(5000); //refresh de la page apres l'envoie par sms

                //envoie du code par mail
                HtmlSubmitInput sendMail = (HtmlSubmitInput) formEnvoieCode.getElementsByAttribute("input", "type", "submit").get(0);
                final HtmlTextInput mail = formEnvoieCode.getInputByName("form_compte_0:input_mail_b0");
                mail.setValueAttribute("dijoux.aurelien@gmail.com");
                sendMail.click();
            }
        }
    }
}
