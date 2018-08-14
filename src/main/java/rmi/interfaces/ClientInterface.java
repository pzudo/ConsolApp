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
public interface ClientInterface extends Remote {

    /** callback
     * this method is used by the serverprogram 
     * sends messages to a client
     * @param response String message from serverprogram
     * @return String acknowledgment of response
     * @throws java.rmi.RemoteException
     */
    public String callback(String response) throws RemoteException;

    /** input
     * this method is used by the serverprogram
     * requests a input from client (Scanner)
     * @param request String to the user showing the needed input
     * @return String with the Scanner input to the server 
     * @throws RemoteException
     */
    public String input(String request) throws RemoteException;
    
}
