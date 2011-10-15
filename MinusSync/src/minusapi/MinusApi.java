/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minusapi;

import java.net.URL;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.Map;
import com.google.gson.*;
import java.nio.CharBuffer;
import java.util.HashMap;

/**
 *
 * @author burun
 * using for deserializing json list object
 */

class MinusList <T> {
    private Collection<T> results;

    /**
     * @return the results
     */
    public Collection<T> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(Collection<T> results) {
        this.results = results;
    }
}

/*
 * because don't work Type.class, if Type using with generic
 */
class FolderList extends MinusList<MinusFolder>{  
}

/*
 * because don't work Type.class, if Type using with generic
 */
class FileList extends MinusList<MinusFile>{
}


public class MinusApi {
  
    private final String _clientId;
    private final String _clientSecret;

    private final Map<Url, String> _urls = new HashMap<MinusApi.Url, String>(){{
            put(Url.ActiveUser, 
                "https://minus.com/api/v2/activeuser?bearer_token=");
            put(Url.Authentification, "http://minus.com/oauth/token");
            put(Url.User, "https://minus.com/api/v2/users/");    
            put(Url.Folder, "https://minus.com/api/v2/folders/");
            put(Url.File, "https://minus.com/api/v2/files/");
        }};
          
    public enum Url {
        Authentification,
        ActiveUser,
        User,
        Folder,
        File
    }
    
    private String getRequestAuthString(String uname, String password, 
                                        String scope)
    {
        return "grant_type=password&client_id=" + _clientId  + 
               "&client_secret=" + _clientSecret + 
               "&scope=" + scope + "&username=" + uname + 
               "&password=" + password;
    }
    
    private String getRequestRefreshString(String refreshToken, String scope)
    {
        return "grant_type=refresh_token&client_id=" + _clientId  + 
               "&client_secret=" + _clientSecret + 
               "&scope=" + scope + "&refresh_token=" + refreshToken;
    }
    
    private void writeBody(OutputStream out, String str) 
            throws IOException {
        OutputStreamWriter wr = new OutputStreamWriter(out);
        wr.write(str);
        wr.close();
    }
       
    public MinusApi(String clientId, String clientSecret)
    {
        _clientId = clientId;
        _clientSecret = clientSecret;
    }
    
    private AuthResponse authentification(String body)
    {
        AuthResponse res = null;
        try {
            URL url = new URL(_urls.get(Url.Authentification));
            
            HttpURLConnection con = (HttpURLConnection)url.openConnection();                              
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", 
                            "application/x-www-form-urlencoded; charset=UTF-8");
       
            writeBody(con.getOutputStream(), body);
           
            Gson json = new Gson();
            res = json.fromJson(new InputStreamReader(con.getInputStream()), 
                                AuthResponse.class);
            con.disconnect();            
        } catch (IOException e) {
            // TODO add report error
             e.printStackTrace();
        } catch (JsonSyntaxException e) {
            // TODO add report error
             e.printStackTrace();
        }
        
        return res;
    }
     
    public AuthResponse authentification(String uname, String password, 
                                         String scope){
        return authentification(getRequestAuthString(uname, password, scope));
    }
 
    public AuthResponse refresh(String scope, String refreshToken)
    {
        return authentification(getRequestRefreshString(refreshToken, scope));
    }
    
    private <T extends Object> T minusApiRequest(String urlStr, Class<T> type)
    {
        T res = null;
        try {
            // on default request - GET
            HttpURLConnection con = 
                            (HttpURLConnection)new URL(urlStr).openConnection();
              
            InputStreamReader rd = new InputStreamReader(con.getInputStream());
  //          res = new Gson().fromJson(rd, type);
            CharBuffer buf = CharBuffer.allocate(con.getContentLength());
            rd.read(buf);
            System.out.println(buf.array());
            res = new Gson().fromJson(new String(buf.array()), type);
            
            con.disconnect();
        } catch (IOException e) {
            // TODO add report error            
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            // TODO add report error            
            e.printStackTrace();
        }
        return res;
    }
    
    public MinusUser getActiveUser(String accToken)
    {
        String url = _urls.get(Url.ActiveUser) + accToken;
        return minusApiRequest(url, MinusUser.class);
    }
    
    public MinusUser getUser(String accToken, String slug)
    {
        String url = _urls.get(Url.User) + slug + "?bearer_token=" + accToken;
        return minusApiRequest(url, MinusUser.class);
    }

    public Collection<MinusFolder> getFolders(String accToken, String slug)
    {
        String url = _urls.get(Url.User) + slug 
                     + "/folders?bearer_token=" + accToken;
        FolderList lst = minusApiRequest(url, FolderList.class);
        return lst.getResults();
    }
    
    public boolean addFolder(String accToken, String name, boolean isPublic)
    {
        // see http://miners.github.com/MinusAPIv2/v2/list_user_folders.html
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public MinusFolder getFolder(String accToken, String folderId)
    {
        String url = _urls.get(Url.Folder) + folderId 
                     + "?bearer_token=" + accToken;
        return minusApiRequest(url, MinusFolder.class);
    }
    
    public boolean deleteFolder(String accToken, String folderId)
    {
        // see http://miners.github.com/MinusAPIv2/v2/get_folder.html DELETE
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public Collection<MinusFile> getFiles(String accToken, String folderId)
    {
        String url = _urls.get(Url.Folder) + folderId 
                     + "/files?bearer_token=" + accToken;
        FileList lst = minusApiRequest(url, FileList.class);
        return lst.getResults();
    }
    
    public boolean uploadFile(String accToken, String caption, String filename, 
                              String file, InputStream in)
    {
        // see http://miners.github.com/MinusAPIv2/v2/list_folder_files.html POST
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public boolean downloadFile(String accToken, MinusFile file, OutputStream out)
    {
        //это код с ноута
        // using direct link to file from MinusFile objects: file.getOriginalLink();        
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public MinusFile getFile(String accToken, String fileId)
    {
        String url = _urls.get(Url.File) + fileId + "?bearer_token=" + accToken;
        return minusApiRequest(url, MinusFile.class);
    }
    
    public boolean deleteFile(int fileId)
    {
        // see http://miners.github.com/MinusAPIv2/v2/get_file.html DELETE
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
