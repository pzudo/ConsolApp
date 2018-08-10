/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import rmi.implementions.ClientImplementation;
import rmi.interfaces.ClientInterface;
import rmi.interfaces.AdminClientInterface;
import static java.lang.System.exit;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 *
 * @author s145005
 */
public class AdminClient {
   
    static Scanner input = new Scanner(System.in);
    
    static String user;
    static String password;

    static ClientInterface client;
    static AdminClientInterface serv;
    
    /**
     * @param args the command line arguments
     * @throws java.rmi.NotBoundException
     * @throws java.net.MalformedURLException
     * @throws java.rmi.RemoteException
     */
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {

        input = new Scanner(System.in);

        client = new ClientImplementation();
        serv = (AdminClientInterface) Naming.lookup("rmi://localhost:3097/admin");

        System.out.println("type username then enter");
        user = input.nextLine();
        System.out.println("type password then enter");
        password = input.nextLine();

        try {
            System.out.println(serv.register(user, password, client));
            consol(client);
        } catch (RemoteException e) {
            System.err.println(e);
        } finally {
            serv.unregister(client);
            exit(0);
        }
    }
    
    public static void consol(ClientInterface client) throws RemoteException{
        while (true) {
            System.out.println("enter command");
            String cmd = input.nextLine();

            if (null == cmd) {
                System.err.println("unknown command");
            } else {
                switch (cmd) {
                    case "exit":
                        serv.unregister(client);
                        exit(0);
                        break;
                    case "set score":
                        System.out.println("enter points to add");
                        int point = input.nextInt(0);
                        serv.setScore(user, password, client, point);
                        break;
                    case "get score*":
                        System.out.println(serv.getScore(user, password, client));
                        break;
                    case "get users":
                        System.out.println(serv.getUsers(client));
                        break;
                    case "get words":
                        System.out.println(serv.getWords(client));
                        break;
                    default:
                        System.err.println("unknown command");
                        break;
                }
            }
        }
    }
}
