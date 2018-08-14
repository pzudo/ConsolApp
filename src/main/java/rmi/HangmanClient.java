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
import rmi.implementions.ClientImplementation;
import rmi.interfaces.ClientInterface;
import rmi.interfaces.HangmanClientInterface;

/**
 * This is the client for playing hangman in console
 * @author s145005
 */
public class HangmanClient {

    static Scanner input = new Scanner(System.in);
    
    static String user;
    static String password;

    static ClientInterface client;
    static HangmanClientInterface hang;
    
    /**
     * @param args the command line arguments
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.net.MalformedURLException
     */
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        input = new Scanner(System.in);

        client = new ClientImplementation();
        hang = (HangmanClientInterface) Naming.lookup("rmi://46.101.82.206:9091/hangman");
//        hang = (HangmanClientInterface) Naming.lookup("rmi://ubuntu4.saluton.dk:9091/hangman");
//        hang = (HangmanClientInterface) Naming.lookup("rmi://localhost:9091/hangman");

        System.out.println(hang.handshake());
        System.out.println("-------------------- login --------------------");
        System.out.println("type username then enter");
        user = input.nextLine();
        System.out.println("type password then enter");
        password = input.nextLine();

        try {
            hang.register(user, password, client);
        } catch (RemoteException e) {
            System.err.println(e);
        } finally {
            hang.unregister(client);
            input.close();
            exit(0);
        }
    }
    
}
