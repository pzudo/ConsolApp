/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import javax.xml.ws.Endpoint;

/**
 *
 * @author s145005
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GamedataInterface r = new GamedataImplementation();
        
        System.out.println("publishing");
        Endpoint.publish("http://[::]:9092/soap", r);
        System.out.println("published");
        
    }
    
}
