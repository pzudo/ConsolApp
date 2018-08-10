package hangman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class Gamelogic {

    ArrayList<String> words = new ArrayList<>();
    private String word;
    private final ArrayList<String> usedLetters = new ArrayList<>();
    private String visible;
    private int wrongs;
    private boolean correct;
    private boolean won;
    private boolean lost;

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

    public ArrayList<String> getWords() {
        return words;
    }

    public Gamelogic() {
        words.add("bil");
        words.add("computer");
        words.add("programmering");
        words.add("motorvej");
        words.add("busrute");
        words.add("gangsti");
        words.add("skovsnegl");
        words.add("solsort");
        words.add("seksten");
        words.add("sytten");
    }

    public void reset() {
        usedLetters.clear();
        wrongs = 0;
        won = false;
        lost = false;
        word = words.get(new Random().nextInt(words.size()));
        updateVisible();
    }

    public void newWord(int position) {
        usedLetters.clear();
        wrongs = 0;
        won = false;
        lost = false;
        word = words.get(position);
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
        reset();
    }
}
