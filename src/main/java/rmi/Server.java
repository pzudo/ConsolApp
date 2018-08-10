/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import rmi.implementions.HangmanClientImplementation;
import rmi.implementions.AdminClientImplementation;
import rmi.interfaces.HangmanClientInterface;
import rmi.interfaces.AdminClientInterface;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author s145005
 */
public class Server {

    /**
     * @param args the command line arguments
     * @throws java.rmi.RemoteException
     * @throws java.net.MalformedURLException
     */
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        
        LocateRegistry.createRegistry(3097);
        
        //Remote object for administration
        AdminClientInterface serv = new AdminClientImplementation();
        Naming.rebind("rmi://localhost:3097/admin", serv);
        
        //Remote object for normal users (hangman game)
        HangmanClientInterface hang = new HangmanClientImplementation();
        Naming.rebind("rmi://localhost:3097/hangman", hang);
        
        
    }
    
}
