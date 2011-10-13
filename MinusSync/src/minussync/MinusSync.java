/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minussync;

import minusapi.*;
import java.net.MalformedURLException;
import java.util.Collection;

/**
 *
 * @author burun
 */
public class MinusSync {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
      
        if (args.length != 4) {
            System.out.println("using:\n MinusSync client_id client_key login password");
            return;
        }
        
        if (!HttpUrlConnectionSslInitializer.init()) {
            System.out.println("error for ssl initialization");
            return;
        }
            
        String id = args[0];
        String secret = args[1];      
        String login = args[2];
        String password = args[3];
        
        MinusApi api = new MinusApi(id, secret);
                
        AuthResponse auth = api.authentification(login, password, "modify_all");
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
        
        Collection<MinusFolder> folders = 
                          api.getFolders(auth.getAccessToken(), user.getSlug());
        for(MinusFolder f: folders){
            System.out.print("Folder: ");
            System.out.println(f.toString());
            Collection<MinusFile> files = 
                                 api.getFiles(auth.getAccessToken(), f.getId());
            for(MinusFile file: files){
                System.out.print("File: ");
                System.out.println(file.toString());
                MinusFile t = api.getFile(auth.getAccessToken(), file.getId());
                System.out.print("File: ");
                System.out.println(t.toString());
            }
        }
    }
}
