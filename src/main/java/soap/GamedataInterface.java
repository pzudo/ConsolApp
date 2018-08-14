/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import soap.Score;

/**
 *
 * @author s145005
 */
@WebService
public interface GamedataInterface {

    /** handshake
     * a method for testing soap connection
     * @param handshake takes a String from client alerting server of connection
     * @return String handshake for testing connection
     */
    @WebMethod public String handshake(String handshake);

    /** login
     * used to authenticate admin user
     * @param username used for login with brugerautorisations module
     * @param password used for login with brugerautorisations module
     */
    @WebMethod public void login(String username, String password);

    /** setWordlist (consol cmd: set wordlist)
     * fetches words from dr and adds them to the wordlis
     */
    @WebMethod public void setWordlist();

    /** getWordlist (consol cmd: get wordlist)
     * lets a client (admin og rmi hangman serverprogram get the wordlist
     * @return ArrayList with words used in Hangman
     */
    @WebMethod public ArrayList getWordlist();

    /** setScore (consol cmd: set score)
     * enables setting the score for a specific user (setExtraFelt) "score"
     * @param username used to set a specific users score from brugerdatabase
     * @param password used to set a specific users score from brugerdatabase
     * @param point int stored score in brugerdatabase
     */
    @WebMethod public void setScore(String username, String password, int point);

    /** getScore (consol cmd: get score)
     * enables getting the score for a specific user (getExtraFelt) "score"
     * @param username used to get a specific users score from brugerdatabase
     * @param password used to get a specific users score from brugerdatabase
     * @return Score object with score data (username, password, score)
     */
    @WebMethod public Score getScore(String username, String password);

    /** addScore
     * enables adding a Score object to the highscore list
     * @param score a new Score object with username, password and score
     */
    @WebMethod public void addScore(Score score);

    /** updateScore (consol cmd: update score)
     * enables updating from an old score to a new one
     * by adding the scored point to the old score 
     * saving it in both the highscore and on the brugerdatabase
     * @param username used to get a specific users score from brugerdatabase
     * @param password used to get a specific users score from brugerdatabase
     * @param point takes the scored points
     */
    @WebMethod public void updateScore(String username, String password, int point);

    /** resetScore (consol cmd: reset score)
     * resets a specifik users score to 0
     * @param username used to get a specific users score from brugerdatabase
     * @param password used to get a specific users score from brugerdatabase
     */
    @WebMethod public void resetScore(String username, String password);

    /** getHighscore (consol cmd: get highscore)
     * gets a list with only public score data (username  and points)
     * @return ArrayList of Strings with usernames and scores
     */
    @WebMethod public ArrayList getHighscore();
    
}
