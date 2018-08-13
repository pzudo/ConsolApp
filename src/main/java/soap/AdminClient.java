/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import static java.lang.System.exit;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author s145005
 */
public class AdminClient {

    static Scanner input = new Scanner(System.in);

    static String username;
    static String password;
    static String score;
    static int point;
    static ArrayList<String> highscore;
    static ArrayList<Object> wordlist;

    /**
     * @param args the command line arguments
     * @throws java.net.MalformedURLException
     */
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://ubuntu4.saluton.dk:9092/soap?wsdl");
//        URL url = new URL("http://localhost:9092/soap?wsdl");
        QName qname = new QName("http://soap/", "GamedataImplementationService");
        Service service = Service.create(url, qname);
        GamedataInterface gamedata = service.getPort(GamedataInterface.class);

        System.out.println(gamedata.handshake("soap admin handshake"));
        System.out.println("-------------------- login --------------------");
        System.out.println("type username then enter");
        username = input.nextLine();
        System.out.println("type password then enter");
        password = input.nextLine();
        gamedata.login(username, password);

        String cmd;

        while (true) {
            System.err.println("enter a command");

            cmd = input.nextLine();

            if (input == null) {
                System.err.println("unknown command");
            } else {
                switch (cmd) {
                    case "set wordlist":
                        gamedata.setWordlist();
                        break;
                    case "get wordlist":
                        wordlist = gamedata.getWordlist();
                        for (Object word : wordlist) {
                            System.out.println(word);
                        }
                        break;

                    case "set score":
                        System.out.println("type username then enter");
                        username = input.nextLine();

                        System.out.println("type password then enter");
                        password = input.nextLine();

                        System.out.println("type points then enter");
                        point = input.nextInt();

                        gamedata.setScore(username, password, point);
                        break;
                    case "get score":
                        System.out.println("type username then enter");
                        username = input.nextLine();

                        System.out.println("type password then enter");
                        password = input.nextLine();

                        score
                                = gamedata.getScore(username, password).getUsername()
                                + " has "
                                + gamedata.getScore(username, password).getPoint()
                                + " points";
                        System.out.println(score);
                        break;
                    case "add score":
                        System.out.println("type username then enter");
                        username = input.nextLine();

                        System.out.println("type password then enter");
                        password = input.nextLine();

                        gamedata.addScore(gamedata.getScore(username, password));
                        break;
                    case "update score":
                        System.out.println("type username then enter");
                        username = input.nextLine();

                        System.out.println("type password then enter");
                        password = input.nextLine();

                        System.out.println("type points then enter");
                        point = input.nextInt();

                        gamedata.updateScore(username, password, point);
                        break;
                    case "reset score":
                        System.out.println("type username then enter");
                        username = input.nextLine();

                        System.out.println("type password then enter");
                        password = input.nextLine();

                        gamedata.resetScore(username, password);
                        break;
                    case "get highscore":
                        highscore = gamedata.getHighscore();
                        for (String score : highscore) {
                            System.out.println(score);
                        }
                        break;

                    case "exit":
                        input.close();
                        exit(0);
                        break;
                    default:
                        System.err.println("unknown command \n");
                        break;

                }
            }
        }
    }
}
