/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import java.net.MalformedURLException;
import java.net.URL;
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
        URL url = new URL("http://localhost:2512/soap?wsdl");
        QName qname = new QName("http://soap/", "RemoteImplService");
        Service service = Service.create(url, qname);
        RemoteI r = service.getPort(RemoteI.class);
        
        System.out.println(r.handshake());
    }
    
}
