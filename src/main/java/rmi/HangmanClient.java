/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import static java.lang.System.exit;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import static rmi.AdminClient.input;
import rmi.implementions.ClientImplementation;
import rmi.interfaces.AdminClientInterface;
import rmi.interfaces.ClientInterface;
import rmi.interfaces.HangmanClientInterface;

/**
 *
 * @author s145005
 */
public class HangmanClient {

    static Scanner input = new Scanner(System.in);
    
    static String user;
    static String password;

    static ClientInterface client;
    static HangmanClientInterface serv;
    
    /**
     * @param args the command line arguments
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.net.MalformedURLException
     */
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        input = new Scanner(System.in);

        client = new ClientImplementation();
        serv = (HangmanClientInterface) Naming.lookup("rmi://localhost:3097/hangman");

        System.out.println("type username then enter");
        user = input.nextLine();
        System.out.println("type password then enter");
        password = input.nextLine();

        try {
            serv.register(user, password, client);
        } catch (RemoteException e) {
            System.err.println(e);
        } finally {
            serv.unregister(client);
            input.close();
            exit(0);
        }
    }
    
}
