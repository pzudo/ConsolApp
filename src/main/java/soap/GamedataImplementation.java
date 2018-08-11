/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;

/**
 *
 * @author s145005
 */
@WebService(endpointInterface = "soap.GamedataInterface")
public class GamedataImplementation implements GamedataInterface {

    private final ArrayList<String> wordlist;

    public GamedataImplementation() {
        wordlist = new ArrayList<>();
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
        System.out.println("fetching words from dr.dk");
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
        wordlist.clear();
        wordlist.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

    }

    @Override
    public String handshake(String handshake) {
        System.out.println(handshake);
        return "handshake";
    }

    @Override
    public void setWordlist() {
        try {
            fetchWordsFromDR();
            System.out.println("word succesfully fetched");
        } catch (Exception ex) {
            Logger.getLogger(GamedataImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList getWordlist() {
        return wordlist;
    }

}
