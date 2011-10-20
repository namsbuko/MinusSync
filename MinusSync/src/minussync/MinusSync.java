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
                                               "modify_all");
        if (api == null) return; 
        System.out.println(api.getAuthentification().toString());  
        
        if (!api.refresh()) return; 
        System.out.println(api.getAuthentification().toString());  
        
        MinusUser user = api.getActiveUser();    
        if (user == null) return;
        System.out.println(user.toString());
        
        user = api.getUser();   
        if (user == null) return;
        System.out.println(user.toString());
        
        Collection<MinusFolder> folders = api.getFolders();
        for(MinusFolder folder: folders){
            System.out.print("Folder: ");
            System.out.println(folder.toString());
            System.out.print("Folder: ");
            System.out.println(api.getFolder(folder.getId()).toString());
            Collection<MinusFile> files = api.getFiles(folder.getId());
            for(MinusFile file: files){
                System.out.print("File: ");
                System.out.println(file.toString());
                System.out.print("File: ");
                System.out.println(api.getFile(file.getId()).toString());
//                try {
//                    FileOutputStream fw = 
//                                 new FileOutputStream(new File(file.getName()));
//                    api.downloadFile(file, fw);
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(MinusSync.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
        }
//        user = api.getUser(auth.getAccessToken(), user.getSlug());    
//        if (user == null) return;
//        System.out.println(user.toString());
        
 //       api.addFolder("somefolder", Boolean.TRUE);
//        FileOutputStream fs = new FileOutputStream("my.txt");
//        fs.write("test test test".getBytes());
//        fs.close();
//        FileInputStream inFile = new FileInputStream("my.txt");
        
//        Collection<MinusFolder> folders = 
//                          api.getFolders(auth.getAccessToken(), user.getSlug());
//        for(MinusFolder f: folders){
//            
//            System.out.print("Folder: ");
//            System.out.println(f.toString());
//            api.uploadFile(auth.getAccessToken(), "", "", f.getId(), inFile);
////            api.deleteFolder(auth.getAccessToken(), f.getId());
//            break;
//            Collection<MinusFile> files = 
//                                 api.getFiles(auth.getAccessToken(), f.getId());
//            for(MinusFile file: files){
//                System.out.print("File: ");
//                System.out.println(file.toString());
//                api.deleteFile(auth.getAccessToken(), file.getId());
//            }
//        }
        
 //       api.addFolder(auth.getAccessToken(), user.getSlug(), 
   //                   "testfolder", Boolean.TRUE);
    }
}
