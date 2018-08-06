/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author s145005
 */
public class Client {

    /**
     * @param args the command line arguments
     * @throws java.rmi.NotBoundException
     * @throws java.net.MalformedURLException
     * @throws java.rmi.RemoteException
     */
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        
        String user = "s145005";
        String password = "s145005";
        int point = 3;
        
        ClientI client = new ClientImpl();
        
        ServI serv = (ServI) Naming.lookup("rmi://localhost:3097/RmiServer");
        System.out.println(serv.handshake());
        System.out.println(serv.register(user, password, client));
        
        System.out.println(client);
        
        serv.setScore(user, password, client, point);
        System.out.println(serv.getScore(user, password, client));
        
    }
    
}
