/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.implementions;

import rmi.interfaces.ClientInterface;
import rmi.interfaces.AdminClientInterface;
import brugerautorisation.data.Bruger;
import brugerautorisation.data.Diverse;
import brugerautorisation.transport.rmi.Brugeradmin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.User;

/**
 *
 * @author s145005
 */
public class AdminClientImplementation extends UnicastRemoteObject implements AdminClientInterface {

    private final List<ClientInterface> admins = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<String> words = new ArrayList<>();
    Brugeradmin ba;
    Bruger b;

    public AdminClientImplementation() throws RemoteException {
        super();
    }

    @Override
    public String handshake() throws RemoteException {
        return "handshake";
    }

    @Override
    public String register(String username, String password, ClientInterface client) throws RemoteException {

        String loginData = null;

        try {
            ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
            b = ba.hentBruger(username, password);

            loginData = "User: " + b + ", " + "Data: " + Diverse.toString(b);

        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(AdminClientImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!(admins.contains(client))) {
            admins.add(client);
            Object score = ba.getEkstraFelt(username, password, "score");
            String scoreString = score.toString();
            User user = new User(username, scoreString);
            users.add(user);
            System.out.print("registered client " + client);
        }

        doCallbacks();

        return "registered";
    }

    @Override
    public List getClients(ClientInterface client) throws RemoteException {
        if (admins.contains(client)) {
            return admins;
        } else {
            return null;
        }
    }

    @Override
    public void unregister(ClientInterface client) throws RemoteException {
        if (admins.remove(client)) {
            System.out.println("unregistered client " + client);
        } else {
            System.err.println("client does not exist");
        }
    }

    @Override
    public void setScore(String user, String password, ClientInterface client, int point) throws RemoteException {

        if (admins.contains(client)) {
            try {
                ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
            } catch (NotBoundException | MalformedURLException ex) {
                Logger.getLogger(AdminClientImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
            int temp = (int) ba.getEkstraFelt(user, password, "score");

            ba.setEkstraFelt(user, password, "score", (temp + point));

            Object score = ba.getEkstraFelt(user, password, "score");
            String returnScore = "score: " + score;
            System.out.println(returnScore);
        } else {
            System.err.println("client does not exist");
        }

    }

    @Override
    public String getScore(String user, String password, ClientInterface client) throws RemoteException {
        if (admins.contains(client)) {
            try {
                ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
            } catch (NotBoundException | MalformedURLException ex) {
                Logger.getLogger(AdminClientImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }

            Object score = ba.getEkstraFelt(user, password, "score");
            String returnScore = "score: " + score;
            System.out.println(returnScore);

            return returnScore;

        } else {
            System.err.println("client does not exist");
            return "client does not exist";
        }
    }

    @Override
    public List getUsers(ClientInterface client) throws RemoteException {
        if (admins.contains(client)) {
            return users;
        } else {
            return null;
        }
    }

    private void doCallbacks() throws RemoteException {
        System.out.println("callbacks initiated");

        for (ClientInterface callback : admins) {
            callback.callback("a new client has been rigistered");
        }
    }
    
    @Override
    public List getWords(ClientInterface client) throws RemoteException {

        if (admins.contains(client)) {
            try {
                fetchWordsFromDR();
            } catch (Exception ex) {
                Logger.getLogger(AdminClientImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
            return words;
        } else {
            return null;
        }
    }

    public static String fetchUrl(String url) throws IOException {
        System.out.println("fetching data from url " + url);
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line + "\n");
            line = br.readLine();
        }
        return sb.toString();
    }

    public void fetchWordsFromDR() throws Exception {
        String data = fetchUrl("https://dr.dk");
        //System.out.println("data = " + data);

        data = data.substring(data.indexOf("<body")). // fjern headere
                replaceAll("<.+?>", " ").toLowerCase(). // fjern tags
                replaceAll("&#198;", "æ"). // erstat HTML-tegn
                replaceAll("&#230;", "æ"). // erstat HTML-tegn
                replaceAll("&#216;", "ø"). // erstat HTML-tegn
                replaceAll("&#248;", "ø"). // erstat HTML-tegn
                replaceAll("&oslash;", "ø"). // erstat HTML-tegn
                replaceAll("&#229;", "å"). // erstat HTML-tegn
                replaceAll("[^a-zæøå]", " "). // fjern tegn der ikke er bogstaver
                replaceAll(" [a-zæøå] ", " "). // fjern 1-bogstavsord
                replaceAll(" [a-zæøå][a-zæøå] ", " "); // fjern 2-bogstavsord

        System.out.println("data = " + data);
        System.out.println("data = " + Arrays.asList(data.split("\\s+")));
        words.clear();
        words.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

        System.out.println("words = " + words);
    }
}
