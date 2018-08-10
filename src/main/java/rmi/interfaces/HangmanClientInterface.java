/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.interfaces;

import rmi.interfaces.ClientInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author s145005
 */
public interface HangmanClientInterface extends Remote {
    /**
     * a method for testing connection to remote object
     * @return String with handshake for testing connection
     * @throws RemoteException
     */
    public String handshake() throws RemoteException;

    /**
     * registering clients used in callback and user validation
     * @param username
     * @param user
     * @param password
     * @param client
     * @return String with registration status
     * @throws RemoteException
     */
    public void register(String username, String password, ClientInterface client) throws RemoteException;
    
    /**
     * unregister a client removing it from clients list
     * @param client
     * @throws RemoteException
     */   
    public void unregister(ClientInterface client) throws RemoteException;
}
