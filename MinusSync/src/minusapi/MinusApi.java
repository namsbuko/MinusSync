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
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map.Entry;
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
  
    static private final String CRLF = "\r\n";
    static private final String Charset = "UTF-8"; 
  
    private final String _clientId;
    private final String _clientKey;
    private final MinusUser _activeUser;
    private MinusAuth _auth;

    static private final Map<Url, String> _urls = 
         new HashMap<MinusApi.Url, String>(){{
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
                
    static private HttpURLConnection request(String method, 
                          Map<String, String> property, String url, String body) 
            throws IOException {
        HttpURLConnection con = null;
        con = (HttpURLConnection)new URL(url).openConnection();
        
        con.setRequestMethod(method);

        if (property != null)
            for (Entry<String, String> pair: property.entrySet()) {
                con.setRequestProperty(pair.getKey(), pair.getValue());
            }

        if (body != null) {
            con.setDoOutput(true);
            writeBody(con.getOutputStream(), body);
        }
        
        return con;     
    }
    
    static private int requestResponseCode(String method, 
                      Map<String, String> properties, String url, String body) {    
        int res = -1;
        HttpURLConnection con = null;
        try {
            con = request(method, properties, url, body);
            con.connect();
            res = con.getResponseCode();
        } catch (IOException ex) {
            Logger.getLogger(MinusApi.class.getName())
                      .log(Level.WARNING, "requestResponseCode", ex);
        } finally {
            if (con != null) con.disconnect();
        }
        
        return res;
    }
    
    static private <T> T requestJson(String method, Map<String, 
                    String> properties, String url, String body, Class<T> type) {        
        T res = null;
        HttpURLConnection con = null;
        try {
            con = request(method, properties, url, body);
            if (con != null) {
                InputStreamReader rd = 
                                    new InputStreamReader(con.getInputStream());
                res = new Gson().fromJson(rd, type);
            }
        } catch (IOException e) {
            Logger.getLogger(MinusApi.class.getName())
                     .log(Level.WARNING, "requestJson", e);
        } finally {
            if (con != null) con.disconnect();
        }
        return res;        
    }
      
    static private MinusAuth authentification(String body) {
        Map<String, String> pr = new HashMap<String, String>();
        pr.put("Content-Type", 
               "application/x-www-form-urlencoded; charset=" + Charset);
        return requestJson("POST", pr, _urls.get(Url.Authentification), 
                           body, MinusAuth.class);
    }
    
    private MinusApi(String clientId, String clientKey, MinusAuth auth, 
                     MinusUser activeUser) {
        _clientId = clientId;
        _clientKey = clientKey;
        _auth = auth;
        _activeUser = activeUser;
    }

    
    // public members
     
    /**
     * @param clientId - developer's id is provided by minus
     * @param clientKey - developers's key is provided by minus
     * @param uname - user name for minus
     * @param password - user password for minus
     * @param scope - scope for session
     * 
     * @return class with authentification's info
     * 
     * @see MinusAuth
     */
    static public MinusAuth authentification
                               (String clientId, String clientKey, String uname, 
                                String password, String scope){
        return authentification(
                   getAuthRequest(clientId, clientKey, uname, password, scope));
    }
 
    /**
     * 
     * @return class with authentification's info
     */
    public MinusAuth getAuthentification() {
        return _auth;
    }
    
    /**
     * Allocate MinusApi's Object
     * @param clientId - developer's id is provided by minus
     * @param clientKey - developers's key is provided by minus
     * @param uname - user name for minus
     * @param password - user password for minus
     * @param scope - scope for session
     * @return MinusApi's object
     */
    static public MinusApi createMinusApi(String clientId, String clientKey, 
                                  String uname, String password, String scope) {
        MinusAuth auth = authentification(clientId, clientKey, uname, 
                                             password, scope);
        MinusUser actUser = getActiveUser(auth.getAccessToken());
        return (auth == null || actUser == null) 
                ? null : new MinusApi(clientId, clientKey, auth, actUser);
    }
    
     /**
     * Static member
     * @param clientId - developer's id is provided by minus
     * @param clientKey - developers's key is provided by minus
     * @param scope - scope for session
     * @param refreshToken - token for refreshing
     * 
     * @return class with authentification's info
     * 
     * @see MinusAuth
     * @see authentification()
     */
    static public MinusAuth refresh(String clientId, String clientKey, 
                                    String scope, String refreshToken) {
        return authentification(
                   getRefreshRequest(clientId, clientKey, refreshToken, scope));
    }
    
    /**
     * @return result of refreshing
     * 
     * @see MinusAuth
     * @see authentification()
     */
    public boolean refresh() {
        _auth = refresh(_clientId, _clientKey, _auth.getScope(), 
                        _auth.getRefreshToken());
        
        return _auth != null;
    }
    
    /**
     * 
     * @param accToken access token
     * @return active user
     */
    static public MinusUser getActiveUser(String accToken) {
        String url = _urls.get(Url.ActiveUser) + accToken;
        return requestJson("GET", null, url, null, MinusUser.class);
    }
    
    /**
     * 
     * @return active user
     */
    public MinusUser getActiveUser() {
        return _activeUser;
    }
    
    /**
     * Static member
     * @param accToken access token
     * @param slug users's slug
     * @return user
     */
    static public MinusUser getUser(String accToken, String slug) {
        String url = _urls.get(Url.User) + slug + "?bearer_token=" + accToken;
        return requestJson("GET", null, url, null, MinusUser.class);
    }
    
    /**
     * 
     * @return user for current authentification 
     */
    public MinusUser getUser() {
        return getUser(_auth.getAccessToken(), _activeUser.getSlug());
    }    
    
    // TASK add modify info meybe
    
    /**
     * Static member
     * @param accToken access token
     * @param slug users's slug
     * @return collection of user's folder
     */
    static public Collection<MinusFolder> getFolders(String accToken, 
                                                     String slug) {
        String url = _urls.get(Url.User) + slug 
                     + "/folders?bearer_token=" + accToken;
        FolderList lst = requestJson("GET", null, url, null, FolderList.class);
        return lst == null ? null : lst.getResults();
    }
    
    /**
     * @return collection of user's folder
     */
    public Collection<MinusFolder> getFolders() {
        return getFolders(_auth.getAccessToken(), _activeUser.getSlug());
    }
    
    /**
     * Static member
     * @param accToken access token
     * @param slug users's slug
     * @param name folder's name
     * @param isPublic public folder's property
     * @return MinusFolder object - folder has been added, null - else
     */
    static public MinusFolder addFolder(String accToken, String slug, 
                                        String name, Boolean isPublic) {
        String url = _urls.get(Url.User) + slug 
                     + "/folders?bearer_token=" + accToken;
        String body = "name=" + name + "&is_public=" + isPublic.toString();
        Map<String, String> pr = new HashMap<String, String>();
        pr.put("Content-Type", 
               "application/x-www-form-urlencoded; charset=" + Charset);
        pr.put("Accept-Charse", Charset + ",*;q=0.5");
       
        return requestJson("POST", pr, url, body, MinusFolder.class);
    }
    
    /**
     * @param name folder's name
     * @param isPublic public folder's property
     * @return MinusFolder object - folder has been added, null - else
     */
    public MinusFolder addFolder(String name, Boolean isPublic) {
        return addFolder(_auth.getAccessToken(), _activeUser.getSlug(), 
                         name, isPublic);
    }
    
    /**
     * Static member
     * @param accToken access token
     * @param folderId folder's id provided by minus
     * @return MinusFolder - correct, null - else
     */
    static public MinusFolder getFolder(String accToken, String folderId) {
        String url = _urls.get(Url.Folder) + folderId 
                     + "?bearer_token=" + accToken;
        return requestJson("GET", null, url, null, MinusFolder.class);
    }
    
    /**
     * @param folderId folder's id provided by minus
     * @return MinusFolder - correct, null - else
     */
    public MinusFolder getFolder(String folderId) {
        return getFolder(_auth.getAccessToken(), folderId);
    }
    
    /**
     * Static member
     * @param accToken access token
     * @param folderId folder's id provided by minus
     * @return always true
     */
    static public boolean deleteFolder(String accToken, String folderId) {
        String url = _urls.get(Url.Folder) + folderId  
                     + "?bearer_token=" + accToken;
        int respCode = requestResponseCode("DELETE", null, url, null);
     //   return HttpURLConnection.HTTP_OK == respCode;
     // because api don't return code
        return true;  
    }
    
    /**
     * @param folderId folder's id provided by minus
     * @return always true
     */
    public boolean deleteFolder(String folderId) {
        return deleteFolder(_auth.getAccessToken(), folderId);
    }
    
    /**
     * Static member
     * @param accToken access token
     * @param folderId folder's id provided by minus
     * @return folder's files
     */
    static public Collection<MinusFile> getFiles(String accToken, 
                                                 String folderId) {
        String url = _urls.get(Url.Folder) + folderId 
                     + "/files?bearer_token=" + accToken;
        FileList lst = requestJson("GET", null, url, null, FileList.class);
        return lst == null ? null : lst.getResults();
    }
    
    /**
     * @param folderId folder's id provided by minus
     * @return folder's files
     */
    public Collection<MinusFile> getFiles(String folderId) {
        return getFiles(_auth.getAccessToken(), folderId);
    }
    
    /**
     * Static member
     * @param accToken access token
     * @param caption caption of file
     * @param filename filename of file
     * @param in stream with uploading data
     * @param folder folder for uploading
     * @return MinusFile - uploading success, null - else
     */
    static public MinusFile uploadFile(String accToken, String caption, 
                          String filename, InputStream in, MinusFolder folder) {
        MinusFile res = null;
        PrintWriter writer = null;
        HttpURLConnection con = null;
        try {
            String boundary = Long.toHexString(System.currentTimeMillis());
            String type = URLConnection.guessContentTypeFromStream(in);

            URL url = new URL(_urls.get(Url.Folder) + folder.getId() 
                              + "/files?bearer_token=" + accToken);
   
            con = (HttpURLConnection)url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", 
                                   "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("Accept-Charse", Charset + ",*; q=0.5");

            OutputStream output = con.getOutputStream();
            // true = autoFlush, important!
            writer = 
                 new PrintWriter(new OutputStreamWriter(output, Charset), true); 
            
            String contDisp = "Content-Disposition: form-data; ";
            writer.append("--" + boundary).append(CRLF);
            writer.append(contDisp + "name=\"file\"; filename=\"" 
                          + filename + "\"")
                  .append(CRLF);
              
            writer.append("Content-Type: " + type + "; charset=" + Charset)
                  .append(CRLF);
          //  writer.append("Content-Transfer-Encoding: binary").append(CRLF);
            writer.append(CRLF).flush();
            byte[] buffer = new byte[1024];
            for (int length = 0; (length = in.read(buffer)) > 0;) {
                output.write(buffer, 0, length);
            }
            output.flush(); // Important! Output cannot be closed. Close of writer will close output as well.
            writer.append(CRLF).flush();

            writer.append("--" + boundary).append(CRLF);
            writer.append(contDisp + "name=\"filename\"")
                  .append(CRLF);
            writer.append(CRLF);
            writer.append(filename).append(CRLF).flush();

            writer.append("--" + boundary).append(CRLF);
            writer.append(contDisp + "name=\"caption\"")
                  .append(CRLF);
            writer.append(CRLF);
            writer.append(caption).append(CRLF).flush();
            writer.append("--" + boundary + "--").append(CRLF).flush();
            writer.close();
            
            InputStreamReader rd = new InputStreamReader(con.getInputStream());
            res = new Gson().fromJson(rd, MinusFile.class);         
            
        } catch (IOException e) {
            Logger.getLogger(MinusApi.class.getName())
                        .log(Level.WARNING, "uploadFile", e);
        } finally {
            if (writer != null) writer.close();
            if (con != null) con.disconnect();
        }
        return res;
    }
    
    /**
     * @param caption caption of file
     * @param filename filename of file
     * @param in stream with uploading data
     * @param folder folder for uploading
     * @return MinusFile - uploading success, null - else
     */
    public MinusFile uploadFile(String caption, String name, InputStream in, 
                                MinusFolder folder) {
        return uploadFile(_auth.getAccessToken(), caption, name, in, folder);
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
        
    /**
     * Static member
     * @param accToken access token
     * @param fileId file's id by minus
     * @return MinusFile - correct, null - else
     */
    static public MinusFile getFile(String accToken, String fileId) {
        String url = _urls.get(Url.File) + fileId + "?bearer_token=" + accToken;
        return requestJson("GET", null, url, null, MinusFile.class);
    }
    
    /**
     * Static member
     * @param fileId file's id by minus
     * @return MinusFile - correct, null - else
     */
    public MinusFile getFile(String fileId) {
        return getFile(_auth.getAccessToken(), fileId);
    }
    
    /**
     * Static member
     * @param accToken access token
     * @param fileId file's id by minus
     * @return always true
     */
    static public boolean deleteFile(String accToken, String fileId) {
        String url = _urls.get(Url.File) + fileId + "?bearer_token=" + accToken;
        int respCode = requestResponseCode("DELETE", null, url, null);
     //   return HttpURLConnection.HTTP_OK == respCode;
     // because api don't return code
        return true; 
    }
    
    /**
     * @param fileId file's id by minus
     * @return always true
     */
    public boolean deleteFile(String fileId) {
        return deleteFile(_auth.getAccessToken(), fileId);
    }
}
