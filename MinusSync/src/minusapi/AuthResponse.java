/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minusapi;

/**
 *
 * @author burun
 */
public class AuthResponse {
    private String access_token;
    
    private String token_type;
    
    private int expire_in;
    
    private String refresh_token;

    private String scope;

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return access_token;
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken) {
        this.access_token = accessToken;
    }

    /**
     * @return the tokenType
     */
    public String getTokenType() {
        return token_type;
    }

    /**
     * @param tokenType the tokenType to set
     */
    public void setTokenType(String tokenType) {
        this.token_type = tokenType;
    }

    /**
     * @return the expireIn
     */
    public int getExpireIn() {
        return expire_in;
    }

    /**
     * @param expireIn the expireIn to set
     */
    public void setExpireIn(int expireIn) {
        this.expire_in = expireIn;
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refresh_token;
    }

    /**
     * @param refreshToken the refreshToken to set
     */
    public void setRefreshToken(String refreshToken) {
        this.refresh_token = refreshToken;
    }

    /**
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }
    
    @Override
    public String toString()
    {
        return "Access token: " + access_token + "; " + 
               "Token type: " + token_type + "; " +
               "Expire in: " + expire_in + "; " +
               "Refresh token: " + refresh_token + "; " +
               "Scope: " + scope + ".";
    }
}
