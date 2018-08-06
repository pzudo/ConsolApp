/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import brugerautorisation.data.Bruger;
import brugerautorisation.data.Diverse;
import brugerautorisation.transport.rmi.Brugeradmin;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author s145005
 */
public class ServImpl extends UnicastRemoteObject implements ServI  {

    private final List<ClientI> clients = new ArrayList<>();
    Brugeradmin ba;
    Bruger b;
    
    public ServImpl() throws RemoteException {
        super();
    }

    @Override
    public String handshake() throws RemoteException {
        return "handshake";
    }

    @Override
    public String register(String user, String password, ClientI client) throws RemoteException {
        
        
        String loginData = null;

        try {
            ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
            b = ba.hentBruger(user, password);

            loginData = "User: " + b + ", " + "Data: " + Diverse.toString(b);

        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(ServImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(!(clients.contains(client))){
            clients.add(client);
            System.out.print("registered client " + client);
        }
        
        //TODO: callbacks
        
        return "registered";
    }

    @Override
    public void unregister(ClientI client) throws RemoteException {
        if(clients.remove(client)){
            System.out.println("unregistered client " + client);
        }
        else System.err.println("client does not exist");
    }

    @Override
    public String setScore(String user, String password, ClientI client) throws RemoteException {
        
        if (clients.contains(client)){
            try {
                ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
            } catch (NotBoundException | MalformedURLException ex) {
                Logger.getLogger(ServImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            ba.setEkstraFelt(user, password, "score", 244);
                
            Object score = ba.getEkstraFelt(user, password, "score");
            String returnScore = "score: " + score;
            System.out.println(returnScore);
            
            return returnScore;
        }
        else {
            System.out.println("Not a valid client");
            return "client does not exist";
        }
        
    }

}
