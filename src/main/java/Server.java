
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import javax.xml.ws.Endpoint;
import rmi.implementions.HangmanClientImplementation;
import rmi.interfaces.HangmanClientInterface;
import soap.implementations.GamedataImplementation;
import soap.interfaces.GamedataInterface;

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

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        System.out.println("starting server");
        
        System.out.println("starting soap");
        GamedataInterface gamedata = new GamedataImplementation();
                
        System.out.println("publishing");
        Endpoint.publish("http://[::]:9092/soap", gamedata);
        System.out.println("published");
        
        System.out.println("running initial server setup");
        gamedata.setWordlist();
        
        System.out.println("starting rmi");
        //Remote object for normal users (hangman game)
        HangmanClientInterface hang = new HangmanClientImplementation();
        
        LocateRegistry.createRegistry(9091);
        Naming.rebind("rmi://localhost:9091/hangman", hang);
        System.out.println("rmi registry success");
        
    }
    

    
}
