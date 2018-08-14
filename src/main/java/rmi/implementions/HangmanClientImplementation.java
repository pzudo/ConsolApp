/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.implementions;

import rmi.interfaces.HangmanClientInterface;
import rmi.interfaces.ClientInterface;
import brugerautorisation.data.Bruger;
import brugerautorisation.data.Diverse;
import brugerautorisation.transport.rmi.Brugeradmin;
import hangman.Gamelogic;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import soap.GamedataInterface;

/** HangmanClientInteface
 * this class implements the remote HangmanClientInterface
 * @author s145005
 */
public class HangmanClientImplementation extends UnicastRemoteObject implements HangmanClientInterface {

    private final List<ClientInterface> clients = new ArrayList<>();
    private Brugeradmin ba;
    private Bruger b;
    private final URL url;
    private final QName qname;
    private final Service service;
    private final GamedataInterface gamedata;

    private String username;
    private String password;

    public HangmanClientImplementation() throws RemoteException, MalformedURLException {
        super();
        url = new URL("http://46.101.82.206:9092/soap?wsdl");
//        url = new URL("http://ubuntu4.saluton.dk:9092/soap?wsdl");
//        url = new URL("http://localhost:9092/soap?wsdl");
        qname = new QName("http://soap/", "GamedataImplementationService");
        service = Service.create(url, qname);
        gamedata = service.getPort(GamedataInterface.class);
        System.out.println(gamedata.handshake("hangman client handshake"));
    }

    @Override
    public String handshake() throws RemoteException {

        return "handshake";
    }

    @Override
    public void register(String username, String password, ClientInterface client) throws RemoteException {
//         String loginData = null;
         this.username = username;
         this.password = password;

        try {
            ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
            b = ba.hentBruger(username, password);

//            loginData = "User: " + b + ", " + "Data: " + Diverse.toString(b);

        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(HangmanClientImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!(clients.contains(client))) {
            clients.add(client);
            
            System.out.println("adding user to highscore");
            gamedata.addScore(gamedata.getScore(username, password));
            

            System.out.println("registered client " + client);
        }

        try {
            doCallback(client);
        } catch (MalformedURLException ex) {
            Logger.getLogger(HangmanClientImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void unregister(ClientInterface client) throws RemoteException {
        if (clients.remove(client)) {
            System.out.println("unregistered client " + client);
        } else {
            System.err.println("client does not exist");
        }
    }

    public void doCallback(ClientInterface client) throws MalformedURLException, RemoteException {

        Gamelogic game = new Gamelogic();

        client.callback("\nNEW GAME");
        game.reset();
        System.out.println("word is: " + game.getWord());

        int round = 0;

        while (!game.isGameover()) {
            round = round + 1;
            client.callback("\n--------- round " + round + " ---------");
            client.callback("Your nummer of wrongs is: " + game.getWrongs());
            client.callback("The current visible word is: " + game.getVisible());
            client.callback("Used letters: " + game.getUsedLetters());

            String letter = client.input("Guess a letter: ");
            game.check(letter);

            if (game.isCorrect()) {
                client.callback(letter + " was correct");
                client.callback("--------- round " + round + " ---------");
            } else {
                client.callback(letter + " is not in the word try again");
                client.callback("--------- round " + round + " ---------");
            }

            if (game.isGameover()) {
                if (game.isWon()) {
                    client.callback("GRATZ YOU WON");
                    client.callback("You guessed the word " + game.getVisible());

                    int point = game.getWord().length() - game.getWrongs();
                    client.callback("You scored " + point + " point");
                    gamedata.updateScore(username, password, point);
                    
                    alertAll();
                } else {
                    client.callback("YOU LOST BETTER LUCK NEXT TIME");
                    client.callback("the word was " + game.getWord());

                    int point = 0 - game.getWrongs();
                    client.callback("You scored " + point + " point");
                    gamedata.updateScore(username, password, point);
                    
                    alertAll();
                }
            }
        }

        String choice = client.input("\nDo you want to play a new game (y/n)");
        if ("y".equals(choice)) {
            doCallback(client);
        } else {
            client.callback("\nGoodbye");
        }

    }

    public void alertAll() throws RemoteException {
        for (ClientInterface client : clients) {
            updateHighscore(client);
        }
    }

    private void updateHighscore(ClientInterface client) throws RemoteException {
        ArrayList<String> highscore = gamedata.getHighscore();
        for (String score : highscore) {
            client.callback(score);
        }
    }
}
