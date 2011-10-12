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
import java.util.HashMap;
/**
 *
 * @author burun
 */
public class MinusApi {
  
    private final String _clientId;
    private final String _clientSecret;

    private final Map<Url, String> _urls = new HashMap<MinusApi.Url, String>(){{
            put(Url.ActiveUser, 
                "https://minus.com/api/v2/activeuser?bearer_token=");
            put(Url.Authentification, "http://minus.com/oauth/token");
            put(Url.User, "https://minus.com/api/v2/users/");
        }};
          
    public enum Url {
        Authentification,
        ActiveUser,
        User
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
    
    private MinusUser getMinusUser(String urlStr)
    {
        MinusUser user = null;
        try {
            // on default request - GET
            HttpURLConnection con = 
                            (HttpURLConnection)new URL(urlStr).openConnection();
        
            
            Gson json = new Gson();
            user = json.fromJson(new InputStreamReader(con.getInputStream()), 
                                 MinusUser.class);
            
            con.disconnect();
        } catch (IOException e) {
            // TODO add report error            
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            // TODO add report error            
            e.printStackTrace();
        }
        return user;
    }
    
    public MinusUser getActiveUser(String accessToken)
    {
        return getMinusUser(_urls.get(Url.ActiveUser) + accessToken);
    }
    
    public MinusUser getUser(String accessToken, String slug)
    {
        return getMinusUser(_urls.get(Url.User) + slug 
                            + "?bearer_token=" + accessToken);
    }

    public Collection<MinusFolder> getFolders(String slug)
    {
        return null;
    }
    
    public boolean addFolder(String name, boolean isPublic)
    {
        return true;
    }
    
    public MinusFolder getFolder(int folderId)
    {
        return null;
    }
    
    public boolean deleteFolder(int folderId)
    {
        return true;
    }
    
    public Collection<MinusFile> getFiles(int folderId)
    {
        return null;
    }
    
    public boolean uploadFile(String caption, String filename, 
                              String file, InputStream in)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public boolean downloadFile(MinusFile file, OutputStream out)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public MinusFile getFile(int fileId)
    {
        return null;
    }
    
    public boolean deleteFile(int fileId)
    {
        return true;
    }
}
