/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author s145005
 */
public interface HangmanClientInterface extends Remote {
    /** handshake
     * a method for testing connection to remote object
     * @return String with handshake for testing connection
     * @throws RemoteException
     */
    public String handshake() throws RemoteException;

    /** register
     * registering clients used in callback (list) and user validation
     * when clients is successfully this method invokes the doCallback method which run hangman using callbacks
     * @param username used for login with brugerautorisations module
     * @param password used for login with brugerautorisations module
     * @param client adds the verified client remoteobject in a list used for callbacks
     * @throws RemoteException
     */
    public void register(String username, String password, ClientInterface client) throws RemoteException;
    
    /** unregister
     * unregister a client removing it from clients list
     * the serverprogram server program wont be able to callback this client anymore
     * @param client used to remove the correct client from list
     * @throws RemoteException
     */   
    public void unregister(ClientInterface client) throws RemoteException;
}
