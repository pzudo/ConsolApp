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

    /**
     *
     * @param response
     * @return
     * @throws RemoteException
     */
    public String callback(String response) throws RemoteException;

    /**
     *
     * @param request
     * @return
     * @throws RemoteException
     */
    public String input(String request) throws RemoteException;
    
}
