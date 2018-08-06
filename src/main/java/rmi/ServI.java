/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author s145005
 */
public interface ServI extends Remote {

    
    /**
     *
     * @return String - handshake for testing connection
     * @throws RemoteException
     */
    public String handshake() throws RemoteException;

    /**
     * registering clients used in callback and user validation
     * @param user
     * @param password
     * @param client
     * @return String - registration status
     * @throws RemoteException
     */
    public String register(String user, String password, ClientI client) throws RemoteException;

    /**
     * unregister a client removing it from clients list
     * @param client
     * @throws RemoteException
     */
    public void unregister(ClientI client) throws RemoteException;
    
    /**
     * test method with validation
     * @param user
     * @param passowrd
     * @param client
     * @return
     * @throws RemoteException
     */
    public String setScore(String user, String passowrd, ClientI client) throws RemoteException;
      
}
