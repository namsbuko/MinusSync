/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minussync;

import java.io.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import minusapi.*;
import java.util.Collection;

/**
 *
 * @author burun
 */
public class MinusSync {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException{
      
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
        
        MinusApi api = MinusApi.createMinusApi(id, secret, login, password, 
                                               "read_public+read_all+upload_new+modify_all+modify_user");
        if (api == null) return; 
        System.out.println(api.getAuthentification().toString());  
        
//        if (!api.refresh()) return; 
//        System.out.println(api.getAuthentification().toString());  
        
        MinusUser user = api.getActiveUser();    
        if (user == null) return;
        System.out.println(user.toString());
        
//        user = api.getUser();   
//        if (user == null) return;
//        System.out.println(user.toString());
      
       FileInputStream in = new FileInputStream("testl.rar");
        Collection<MinusFolder> folders = api.getFolders();
        for(MinusFolder folder: folders){
            System.out.print("Folder: ");
            System.out.println(folder.toString());
    //        System.out.print("Folder: ");
    //        System.out.println(api.getFolder(folder.getId()).toString());
      //      api.deleteFolder(folder.getId());
          //     api.addFolder("bum\\subfolder", Boolean.TRUE);
            
            api.uploadFile("caption", "test1asfsaf2.rar", in, folder);
            break;
//            Collection<MinusFile> files = api.getFiles(folder.getId());
//            for(MinusFile file: files){
//                System.out.print("File: ");
//                System.out.println(file.toString());
//         
//                api.deleteFile(file.getId());
//                try {
//                    FileOutputStream fw = 
//                                 new FileOutputStream(new File(file.getName()));
//         //           api.downloadFile(file, fw);
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(MinusSync.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
        }
        
        
    }
}
