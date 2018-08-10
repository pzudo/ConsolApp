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

/**
 *
 * @author s145005
 */
public class ClientImplementation extends UnicastRemoteObject implements ClientInterface {

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
    public String input() throws RemoteException {
        Scanner input = new Scanner(System.in);
        System.out.println("test input request");
        String response = input.nextLine();
        return response;
    }

    
}
