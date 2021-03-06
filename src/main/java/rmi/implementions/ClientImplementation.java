/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.implementions;

import rmi.interfaces.ClientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/** ClientImplementation
 * this class implements the remote ClientInterface
 * @author s145005
 */
public class ClientImplementation extends UnicastRemoteObject implements ClientInterface {

    Scanner input = new Scanner(System.in);
    
    public ClientImplementation() throws RemoteException {
        super();
    }

    @Override
    public String callback(String msg) throws RemoteException {
        System.out.println(msg);
        String recived = "client recevied: " + msg;
        return recived;
    }

    @Override
    public String input(String request) throws RemoteException {
        System.out.println(request);
        String response = input.nextLine();
        return response;
    }

    
}
