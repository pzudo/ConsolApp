/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author s145005
 */
public class Client {

    /**
     * @param args the command line arguments
     * @throws java.net.MalformedURLException
     */
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:9092/soap?wsdl");
        QName qname = new QName("http://soap/", "GamedataImplementationService");
        Service service = Service.create(url, qname);
        GamedataInterface gamedata = service.getPort(GamedataInterface.class);
        
        System.out.println(gamedata.handshake("soap admin handshake"));
        System.out.println(gamedata.getWordlist());
        gamedata.setWordlist();
        ArrayList wordlist = gamedata.getWordlist();
        for(Object word : wordlist){
            System.out.println(word);
        }
 
    }
    
}
