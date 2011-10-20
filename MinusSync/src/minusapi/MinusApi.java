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
import java.net.MalformedURLException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final String _clientKey;
    private final MinusUser _activeUser;
    private MinusAuth _auth;

    static private final Map<Url, String> _urls = new HashMap<MinusApi.Url, String>(){{
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
    
    static private String getAuthRequest(String clId, String clKey,
                                  String uname, String password, String scope) {
        return "grant_type=password&client_id=" + clId  + 
               "&client_secret=" + clKey + 
               "&scope=" + scope + "&username=" + uname + 
               "&password=" + password;
    }
    
    static private String getRefreshRequest(String clId, String clKey, 
                                            String refreshToken, String scope) {
        return "grant_type=refresh_token&client_id=" + clId  + 
               "&client_secret=" + clKey + 
               "&scope=" + scope + "&refresh_token=" + refreshToken;
    }
    
    static private void writeBody(OutputStream out, String str) 
            throws IOException {
        OutputStreamWriter wr = new OutputStreamWriter(out);
        wr.write(str);
        wr.close();
    }
           
    static private MinusAuth authentification(String body) {
        MinusAuth res = null;
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
                                MinusAuth.class);
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
     
    static public MinusAuth authentification
                               (String clientId, String clientKey, String uname, 
                                String password, String scope){
        return authentification(
                   getAuthRequest(clientId, clientKey, uname, password, scope));
    }
 
    public MinusAuth getAuthentification() {
        return _auth;
    }
    
    private MinusApi(String clientId, String clientKey, MinusAuth auth, 
                     MinusUser activeUser) {
        _clientId = clientId;
        _clientKey = clientKey;
        _auth = auth;
        _activeUser = activeUser;
    }
    
    static public MinusApi createMinusApi(String clientId, String clientKey, 
                                  String uname, String password, String scope) {
        MinusAuth auth = authentification(clientId, clientKey, uname, 
                                             password, scope);
        MinusUser actUser = getActiveUser(auth.getAccessToken());
        return (auth == null || actUser == null) 
                ? null : new MinusApi(clientId, clientKey, auth, actUser);
    }
    
    static public MinusAuth refresh(String clientId, String clientKey, 
                                       String scope, String refreshToken) {
        return authentification(
                   getRefreshRequest(clientId, clientKey, refreshToken, scope));
    }
    
    public boolean refresh() {
        _auth = refresh(_clientId, _clientKey, _auth.getScope(), 
                        _auth.getRefreshToken());
        
        return _auth != null;
    }
    
    static private <T extends Object> T get(String urlStr, Class<T> type)
    {
        T res = null;
        try {
            // on default request - GET
            HttpURLConnection con = 
                            (HttpURLConnection)new URL(urlStr).openConnection();
              
            InputStreamReader rd = new InputStreamReader(con.getInputStream());
            res = new Gson().fromJson(rd, type);
            
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
    
    static public MinusUser getActiveUser(String accToken)
    {
        String url = _urls.get(Url.ActiveUser) + accToken;
        return get(url, MinusUser.class);
    }
    
    public MinusUser getActiveUser() {
        return _activeUser;
    }
    
    static public MinusUser getUser(String accToken, String slug) {
        String url = _urls.get(Url.User) + slug + "?bearer_token=" + accToken;
        return get(url, MinusUser.class);
    }
    
    public MinusUser getUser() {
        return getUser(_auth.getAccessToken(), _activeUser.getSlug());
    }    
    
    static public Collection<MinusFolder> getFolders(String accToken, 
                                                     String slug) {
        String url = _urls.get(Url.User) + slug 
                     + "/folders?bearer_token=" + accToken;
        FolderList lst = get(url, FolderList.class);
        return lst.getResults();
    }
    
    public Collection<MinusFolder> getFolders() {
        return getFolders(_auth.getAccessToken(), _activeUser.getSlug());
    }
    
    static public boolean addFolder(String accToken, String name, 
                                    boolean isPublic) {
        // see http://miners.github.com/MinusAPIv2/v2/list_user_folders.html
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public boolean  addFolder(String name, boolean isPublic) {
        return addFolder(_auth.getAccessToken(), name, isPublic);
    }
    
    static public MinusFolder getFolder(String accToken, String folderId) {
        String url = _urls.get(Url.Folder) + folderId 
                     + "?bearer_token=" + accToken;
        return get(url, MinusFolder.class);
    }
    
    public MinusFolder getFolder(String folderId) {
        return getFolder(_auth.getAccessToken(), folderId);
    }
    
    static public boolean deleteFolder(String accToken, String folderId)
    {
        // see http://miners.github.com/MinusAPIv2/v2/get_folder.html DELETE
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public boolean deleteFolder(String folderId) {
        return deleteFolder(_auth.getAccessToken(), folderId);
    }
    
    static public Collection<MinusFile> getFiles(String accToken, 
                                                 String folderId) {
        String url = _urls.get(Url.Folder) + folderId 
                     + "/files?bearer_token=" + accToken;
        FileList lst = get(url, FileList.class);
        return lst.getResults();
    }
    
    public Collection<MinusFile> getFiles(String folderId) {
        return getFiles(_auth.getAccessToken(), folderId);
    }
    
    static public boolean uploadFile(String accToken, String caption, 
                                 String filename, InputStream in) {
        // see http://miners.github.com/MinusAPIv2/v2/list_folder_files.html POST
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public boolean uploadFile(String caption, String filename, InputStream in) {
        return uploadFile(_auth.getAccessToken(), caption, filename, in);
    }
    
    /*
     * Download file from Minus to your PC
     * params: 
     *          MinusFile file - direct file from Minus
     *          OutputStream out - stream to file on PC
     *          
     * returns:
     *          true - if file had been download
     *          false - error
     */
    static public boolean downloadFile(MinusFile file, OutputStream out) {
        try {
           
            URL url = new URL (file.getOriginalLink());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            BufferedInputStream bis = 
                                new BufferedInputStream(conn.getInputStream());

            byte[] b = new byte[1024];
            int count = 0;

            while ((count=bis.read(b)) != -1) 
            out.write(b,0,count);

            out.close();
        
        } catch (MalformedURLException ex) {
            return false;
        } catch (IOException ex) {
            return false;       
        }
        return true;
    }    
        
    static public MinusFile getFile(String accToken, String fileId) {
        String url = _urls.get(Url.File) + fileId + "?bearer_token=" + accToken;
        return get(url, MinusFile.class);
    }
    
    public MinusFile getFile(String fileId) {
        return getFile(_auth.getAccessToken(), fileId);
    }
    
    static public boolean deleteFile(String accToken, String fileId) {
        // see http://miners.github.com/MinusAPIv2/v2/get_file.html DELETE
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public boolean deleteFile(String fileId) {
        return deleteFile(_auth.getAccessToken(), fileId);
    }
}
