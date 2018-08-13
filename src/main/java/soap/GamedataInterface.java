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
    @WebMethod public String handshake(String handshake);
    @WebMethod public void login(String username, String password);
    @WebMethod public void setWordlist();
    @WebMethod public ArrayList getWordlist();
    @WebMethod public void setScore(String username, String password, int point);
    @WebMethod public Score getScore(String username, String password);
    @WebMethod public void addScore(Score score);
    @WebMethod public void updateScore(String username, String password, int point);
    @WebMethod public void resetScore(String username, String password);
    @WebMethod public void updateHighscore(Score score);
    @WebMethod public ArrayList getHighscore();
    
}
