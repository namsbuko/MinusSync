/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minussync;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;

/**
 *
 * @author burun
 */
public class HttpUrlConnectionSslInitializer {
    static public boolean init()
    {
        TrustManager tm = new X509TrustManager() {
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, 
                                           String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, 
                                           String authType) {
            }
        };
            
        TrustManager[] trustAllCerts = new TrustManager[] { tm };
        
        boolean res = true;
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(
                                                   new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            } );
        } catch (NoSuchAlgorithmException e) {
            res = false;
        } catch (KeyManagementException e) {
            res = false;
        }
        
        return res;
    }
}
