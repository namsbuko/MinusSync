/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minussync;

import minusapi.*;
import java.net.MalformedURLException;

/**
 *
 * @author burun
 */
public class MinusSync {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
      
        if (args.length != 2) {
            System.out.println("using:\n MinusSync client_id client_key");
            return;
        }
        
        if (!HttpUrlConnectionSslInitializer.init()) {
            System.out.println("error for ssl initialization");
            return;
        }
            
        String id = args[0];
        String secret = args[1];      
       
        MinusApi api = new MinusApi(id, secret);
                
        AuthResponse auth = 
                    api.authentification("namsbuko", "kdn868995", "modify_all");
        if (auth == null) return; 

        System.out.println(auth.toString());  
        
        auth = api.refresh("modify_all", auth.getRefreshToken());
        if (auth == null) return; 

        System.out.println(auth.toString());  
        
        MinusUser user = api.getActiveUser(auth.getAccessToken());    
        if (user == null) return;
        System.out.println(user.toString());
        
        user = api.getUser(auth.getAccessToken(), user.getSlug());    
        if (user == null) return;
        System.out.println(user.toString());
    }
}
