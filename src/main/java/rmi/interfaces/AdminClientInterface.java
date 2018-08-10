/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author s145005
 */
public interface AdminClientInterface extends Remote {

    
    /**
     * a method for testing connection to remote object
     * @return String with handshake for testing connection
     * @throws RemoteException
     */
    public String handshake() throws RemoteException;

    /**
     * registering clients used in callback and user validation
     * @param user
     * @param password
     * @param client
     * @return String with registration status
     * @throws RemoteException
     */
    public String register(String user, String password, ClientInterface client) throws RemoteException;

    /**
     *
     * @param client
     * @return List of current clients
     * @throws RemoteException
     */
    public List getClients (ClientInterface client) throws RemoteException;
    
    /**
     * unregister a client removing it from clients list
     * @param client
     * @throws RemoteException
     */   
    public void unregister(ClientInterface client) throws RemoteException;
    
    /**
     * test method with validation
     * @param user
     * @param passowrd
     * @param client
     * @param point
     * @throws RemoteException
     */
    public void setScore(String user, String passowrd, ClientInterface client, int point) throws RemoteException;

    /**
     *
     * @param user
     * @param password
     * @param client
     * @return String score of a specific user
     * @throws RemoteException
     */
    public String getScore(String user, String password, ClientInterface client) throws RemoteException;

    /**
     * Get all users on the server white each users username and score
     * @param client
     * @return List of users on server
     * @throws RemoteException
     */
    public List getUsers(ClientInterface client) throws RemoteException; 

    /**
     *
     * @param client
     * @return
     * @throws RemoteException
     */
    public List getWords(ClientInterface client) throws RemoteException;
      
}
