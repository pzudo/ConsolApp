/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import java.util.ArrayList;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author s145005
 */
@WebService
public interface GamedataInterface {
    @WebMethod public String handshake(String handshake);
    @WebMethod public void register(String username, String password);
    @WebMethod public void setWordlist();
    @WebMethod public ArrayList getWordlist();
    
}
