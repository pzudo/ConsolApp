package hangman;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import soap.GamedataInterface;

public class Gamelogic {

    private final ArrayList<String> wordlist = new ArrayList<>();
    private String word;
    private final ArrayList<String> usedLetters = new ArrayList<>();
    private String visible;
    private int wrongs;
    private boolean correct;
    private boolean won;
    private boolean lost;

    private URL url;
    private QName qname;
    private Service service;
    private GamedataInterface gamedata;

    public ArrayList<String> getUsedLetters() {
        return usedLetters;
    }

    public String getVisible() {
        return visible;
    }

    public String getWord() {
        return word;
    }

    public int getWrongs() {
        return wrongs;
    }

    public boolean isCorrect() {
        return correct;
    }

    public boolean isWon() {
        return won;
    }

    public boolean isLost() {
        return lost;
    }

    public boolean isGameover() {
        return lost || won;
    }

    public ArrayList<String> getWordlist() {
        return wordlist;
    }

    public Gamelogic() throws MalformedURLException {

    }

    public void reset() throws MalformedURLException {
        url = new URL("http://46.101.82.206:9092/soap?wsdl");
//        url = new URL("http://ubuntu4.saluton.dk:9092/soap?wsdl");
//        url = new URL("http://localhost:9092/soap?wsdl");
        qname = new QName("http://soap/", "GamedataImplementationService");
        service = Service.create(url, qname);
        gamedata = service.getPort(GamedataInterface.class);
        System.out.println(gamedata.handshake("gamelogic handshake"));
        
        usedLetters.clear();
        wrongs = 0;
        won = false;
        lost = false;
        wordlist.addAll(gamedata.getWordlist());
        
        word = wordlist.get(new Random().nextInt(wordlist.size()));
        
        updateVisible();
    }

    public void newWord(int position) {
        usedLetters.clear();
        wrongs = 0;
        won = false;
        lost = false;
        word = wordlist.get(position);
        updateVisible();
    }

    private void updateVisible() {
        visible = "";
        won = true;
        for (int n = 0; n < word.length(); n++) {
            String letter = word.substring(n, n + 1);
            if (usedLetters.contains(letter)) {
                visible = visible + letter;
            } else {
                visible = visible + "*";
                won = false;
            }
        }
    }

    public void check(String letter) {
        if (letter.length() != 1) {
            return;
        }
        System.out.println("guessing on: " + letter);
        if (usedLetters.contains(letter)) {
            return;
        }
        if (won || lost) {
            return;
        }

        usedLetters.add(letter);

        if (word.contains(letter)) {
            correct = true;
            System.out.println("guess was correct: " + letter);
        } else {
            // Vi gættede på et letter der ikke var i word.
            correct = false;
            System.out.println("wrong guesst: " + letter);
            wrongs = wrongs + 1;
            if (wrongs > 6) {
                lost = true;
            }
        }
        updateVisible();
    }

    public void logStatus() {
        System.out.println("---------- ");
        System.out.println("- the word = " + word);
        System.out.println("- visible word = " + visible);
        System.out.println("- wrongs = " + wrongs);
        System.out.println("- usedletters = " + usedLetters);
        if (lost) {
            System.out.println("- GAME IS LOST");
        }
        if (won) {
            System.out.println("- GAME IS WON");
        }
        System.out.println("---------- ");
    }
}
