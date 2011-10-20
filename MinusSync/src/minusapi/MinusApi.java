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
                
    static private <T> T request(String method, Map<String, String> property, 
                                 String url, String body, Class<T> type) {        
        T res = null;
        try {
            HttpURLConnection con = 
                            (HttpURLConnection)new URL(url).openConnection();
        
            con.setRequestMethod(method);
            
            if (property != null)
                for (Entry<String, String> pair: property.entrySet()) {
                    con.setRequestProperty(pair.getKey(), pair.getValue());
                }
           
            if (body != null) {
                con.setDoOutput(true);
                writeBody(con.getOutputStream(), body);
            }

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
            
    static private MinusAuth authentification(String body) {
        Map<String, String> pr = new HashMap<String, String>();
        pr.put("Content-Type", 
               "application/x-www-form-urlencoded; charset=UTF-8");
        return request("POST", pr, _urls.get(Url.Authentification), 
                       body, MinusAuth.class);
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
    
    static public MinusUser getActiveUser(String accToken)
    {
        String url = _urls.get(Url.ActiveUser) + accToken;
        return request("GET", null, url, null, MinusUser.class);
    }
    
    public MinusUser getActiveUser() {
        return _activeUser;
    }
    
    static public MinusUser getUser(String accToken, String slug) {
        String url = _urls.get(Url.User) + slug + "?bearer_token=" + accToken;
        return request("GET", null, url, null, MinusUser.class);
    }
    
    public MinusUser getUser() {
        return getUser(_auth.getAccessToken(), _activeUser.getSlug());
    }    
    
    static public Collection<MinusFolder> getFolders(String accToken, 
                                                     String slug) {
        String url = _urls.get(Url.User) + slug 
                     + "/folders?bearer_token=" + accToken;
        FolderList lst = request("GET", null, url, null, FolderList.class);
        return lst == null ? null : lst.getResults();
    }
    
    public Collection<MinusFolder> getFolders() {
        return getFolders(_auth.getAccessToken(), _activeUser.getSlug());
    }
    
    static public boolean addFolder(String accToken, String slug, String name, 
                                    Boolean isPublic) {
        // TODO don't work
        boolean res = false;
        try {
            URL url = new URL(_urls.get(Url.User) + slug 
                              + "/folders?bearer_token=" + accToken);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", 
                            "application/x-www-form-urlencoded; charset=UTF-8");
            con.setRequestProperty("Accept-Charse", "UTF-8,*;q=0.5");

            String body = "name=" + name + "&is_public=true";// + isPublic.toString();
            
            writeBody(con.getOutputStream(), body);
            
            InputStreamReader rd = new InputStreamReader(con.getInputStream());
  //          res = new Gson().fromJson(rd, type);
            CharBuffer buf = CharBuffer.allocate(con.getContentLength());
            rd.read(buf);
            System.out.println(buf.array());
            MinusFolder folder = new Gson().fromJson(new String(buf.array()), 
                                                     MinusFolder.class);
            
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
    
    public boolean  addFolder(String name, Boolean isPublic) {
        return addFolder(_auth.getAccessToken(), _activeUser.getSlug(), 
                         name, isPublic);
    }
    
    static public MinusFolder getFolder(String accToken, String folderId) {
        String url = _urls.get(Url.Folder) + folderId 
                     + "?bearer_token=" + accToken;
        return request("GET", null, url, null, MinusFolder.class);
    }
    
    public MinusFolder getFolder(String folderId) {
        return getFolder(_auth.getAccessToken(), folderId);
    }
    
    static public boolean deleteFolder(String accToken, String folderId)
    {
        // TODO don't work
        boolean res = false;
        try {
            URL url = new URL(_urls.get(Url.Folder) + folderId 
                              + "?bearer_token=" + accToken);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("DELETE");
            con.connect();
       //     res = con.getResponseCode() == 200;
       //   возвращает код -1
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
    
    public boolean deleteFolder(String folderId) {
        return deleteFolder(_auth.getAccessToken(), folderId);
    }
    
    static public Collection<MinusFile> getFiles(String accToken, 
                                                 String folderId) {
        String url = _urls.get(Url.Folder) + folderId 
                     + "/files?bearer_token=" + accToken;
        FileList lst = request("GET", null, url, null, FileList.class);
        return lst == null ? null : lst.getResults();
    }
    
    public Collection<MinusFile> getFiles(String folderId) {
        return getFiles(_auth.getAccessToken(), folderId);
    }
    
    static public boolean uploadFile(String accToken, String caption, String filename, 
                                     InputStream in)
    {
        // TODO don't work
        
        boolean res = false;
        try {
            URL url = new URL(_urls.get(Url.Folder)// + fileId 
                              + "/files?bearer_token=" + accToken);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", 
                                   "multipart/form-data; boundary=---example");
            con.setRequestProperty("Accept-Charse", "UTF-8,*;q=0.5");

            String body = "---example\r\n" +
                          "Content-Disposition: form-data; name=\"file\"; "
                          + "filename=\"my.txt\"\r\nContent-Type: text/plain\r\n\r\n";
            
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(body);
            int c;
            while ((c = in.read()) != -1) {
                wr.write(c);
            }
            wr.write("\r\n");
            
            body = "---example\r\n" +
                   "Content-Disposition: form-data; name=\"filename\"\r\n\r\n" +
                   "\"my.txt\"\r\n" +
                   "---example\r\n" + 
                   "Content-Disposition: form-data; name=\"caption\"\r\n\r\n" +
                   "\"mycaption\"\r\n" +
                   "---example";
            
//            wr.flush();       
            wr.close();
            
            InputStreamReader rd = new InputStreamReader(con.getInputStream());
  //          res = new Gson().fromJson(rd, type);
            CharBuffer buf = CharBuffer.allocate(con.getContentLength());
            rd.read(buf);
            System.out.println(buf.array());
            MinusFolder folder = new Gson().fromJson(new String(buf.array()), 
                                                     MinusFolder.class);
            
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
        return request("GET", null, url, null, MinusFile.class);
    }
    
    public MinusFile getFile(String fileId) {
        return getFile(_auth.getAccessToken(), fileId);
    }
    
    static public boolean deleteFile(String accToken, String fileId)
    {
        boolean res = false;
        try {
            URL url = new URL(_urls.get(Url.File) + fileId 
                              + "?bearer_token=" + accToken);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("DELETE");
            con.connect();
       //     res = con.getResponseCode() == 200;
       //   возвращает код -1
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
    
    public boolean deleteFile(String fileId) {
        return deleteFile(_auth.getAccessToken(), fileId);
    }
}
