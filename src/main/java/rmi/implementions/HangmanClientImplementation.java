/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.implementions;

import rmi.implementions.AdminClientImplementation;
import rmi.interfaces.HangmanClientInterface;
import rmi.interfaces.ClientInterface;
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
import rmi.User;

/**
 *
 * @author s145005
 */
public class HangmanClientImplementation extends UnicastRemoteObject implements HangmanClientInterface {

    private final List<ClientInterface> clients = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<String> words = new ArrayList<>();
    Brugeradmin ba;
    Bruger b;

    public HangmanClientImplementation() throws RemoteException {
        super();
    }
       
    @Override
    public String handshake() throws RemoteException {
        return "handshake";
    }

    @Override
    public void register(String username, String password, ClientInterface client) throws RemoteException {
        String loginData = null;

        try {
            ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
            b = ba.hentBruger(username, password);

            loginData = "User: " + b + ", " + "Data: " + Diverse.toString(b);

        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(AdminClientImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!(clients.contains(client))) {
            clients.add(client);
            Object score = ba.getEkstraFelt(username, password, "score");
            String scoreString = score.toString();
            User user = new User(username, scoreString);
            users.add(user);
            System.out.print("registered client " + client);
        }
        
        doCallback(client);
        
    }

    @Override
    public void unregister(ClientInterface client) throws RemoteException {
        if (clients.remove(client)) {
            System.out.println("unregistered client " + client);
        } else {
            System.err.println("client does not exist");
        }
    }
    
    public void doCallback(ClientInterface client) throws RemoteException{
        System.out.println("request input from client");
        System.out.println(client.input());
    }

}
