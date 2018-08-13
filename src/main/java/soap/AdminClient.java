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

    static String user = "s145005";
    static String password = "s145005";
    static ArrayList<String> highscore;

    /**
     * @param args the command line arguments
     * @throws java.net.MalformedURLException
     */
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:9092/soap?wsdl");
        QName qname = new QName("http://soap/", "GamedataImplementationService");
        Service service = Service.create(url, qname);
        GamedataInterface gamedata = service.getPort(GamedataInterface.class);

        System.out.println(gamedata.handshake("soap admin handshake"));
//        System.out.println("-------------------- login --------------------");
//        System.out.println("type username then enter");
//        user = input.nextLine();
//        System.out.println("type password then enter");
//        password = input.nextLine();
        gamedata.login(user, password);

        gamedata.addScore(gamedata.getScore(user, password));


        System.out.println("initial score");
        
        highscore = gamedata.getHighscore();
        for (String score : highscore) {
            System.out.println(score);
        }
        
        System.out.println("update score");
        gamedata.setScore(user, password, 1);

        highscore = gamedata.getHighscore();
        for (String score : highscore) {
            System.out.println(score);
        }
        
        System.out.println("reset score");
        gamedata.resetScore(user, password);
        
        highscore = gamedata.getHighscore();
        for (String score : highscore) {
            System.out.println(score);
        }

//        System.out.println(gamedata.getWordlist());
//        gamedata.setWordlist();
//        ArrayList wordlist = gamedata.getWordlist();
//        for (Object word : wordlist) {
//            System.out.println(word);
//        }

        input.close();
        exit(0);
    }

}
