/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author s145005
 */
public class ClientImpl extends UnicastRemoteObject implements ClientI {

    public ClientImpl() throws RemoteException {
        super();
    }

    @Override
    public String response(String msg) throws RemoteException {
        System.out.println(msg);
        String recived = "client recevied: " + msg;
        return recived;
    }
    
}
