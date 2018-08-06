/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import javax.jws.WebService;

/**
 *
 * @author s145005
 */
@WebService(endpointInterface = "soap.RemoteI")
public class RemoteImpl implements RemoteI {

    @Override
    public String handshake() {
        System.out.println("handshake");
        return "handshake";
    }
    
}
