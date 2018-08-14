package hangman;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import javax.xml.ws.Endpoint;
import rmi.implementions.HangmanClientImplementation;
import rmi.interfaces.HangmanClientInterface;
import soap.GamedataImplementation;
import soap.GamedataInterface;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author s145005
 */
public class Server {

    
    
    /** publishes soap serverprogram and runs and initial setup of data
     * registers the rmi remote registry
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        System.out.println("starting server");
        
        System.out.println("starting soap");
        GamedataInterface gamedata = new GamedataImplementation();
                
        System.out.println("publishing");
        Endpoint.publish("http://46.101.82.206:9092/soap", gamedata);
//        Endpoint.publish("http://ubuntu4.saluton.dk:9092/soap", gamedata);
//        Endpoint.publish("http://[::]:9092/soap", gamedata);
        System.out.println("published");
        
        System.out.println("running initial server setup");
        gamedata.setWordlist();
        
        System.out.println("starting rmi");
        //Remote object for normal users (hangman game)
        HangmanClientInterface hang = new HangmanClientImplementation();
        
        LocateRegistry.createRegistry(9091);
        System.setProperty("java.rmi.server.hostname", "46.101.82.206");
//        System.setProperty("java.rmi.server.hostname", "ubuntu4.saluton.dk");
        Naming.rebind("rmi://46.101.82.206:9091/hangman", hang);
//        Naming.rebind("rmi://localhost:9091/hangman", hang);
        System.out.println("rmi registry success");
        
    }
    

    
}
