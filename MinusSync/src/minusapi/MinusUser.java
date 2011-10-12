/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minusapi;

/**
 *
 * @author burun
 */
public class MinusUser {
    private String username;
    private String display_name;
      
    private String slug;

    private String description;
    
    private String email;
    
    private String fb_profile_link;
    private String fb_username;
    
    private String twitter_screen_name;
    
    private Integer visits;
    
    private Integer karma;
    
    private Integer shared;
    
    private Long storage_quota;
    private Long storage_used;
 
    private String  folders;

    private String url;
    private String avatar;
    
    private String[] followers_slug_list;
    private String[] following_slug_list;
    
    private String followers;
    private String following;
    
    @Override
    public String toString()
    {
        return "Username: " + username + "; " +
               "Display name: " + display_name + "; " + 
               "Slug: " + slug + "; " + 
               "Description: " + description + "; " + 
               "Email: " + email + "; " + 
               "Facebook profile: " + fb_profile_link + "; " + 
               "Facebook username: " + fb_username + "; " + 
               "Twitter screen name: " + twitter_screen_name + "; " + 
               "Count visits: " + visits + "; " + 
               "Karma: " + karma + "; " + 
               "Count shared: " + shared + "; " + 
               "Storage quota: " + storage_quota + "; " + 
               "Storage used: " + storage_used + "; " + 
               "Folders link: " + folders + "; " + 
               "Url: " + url + "; " + 
               "Avatar: " + avatar + ";" +
        
                // TODO add correct toString for arrays
        //       "Followers slug list: " + followers_slug_list.toString() + "; " +
        //       "Following slug list: " + following_slug_list.toString() + "; " +
               "Followers: " + followers + "; " +
               "Following: " + following + ".";        
    }
    /**
     * @return the _userName
     */
    public String getUserName() {
        return username;
    }

    /**
     * @param userName the _userName to set
     */
    public void setUserName(String userName) {
        this.username = userName;
    }

    /**
     * @return the _displayName
     */
    public String getDisplayName() {
        return display_name;
    }

    /**
     * @param displayName the _displayName to set
     */
    public void setDisplayName(String displayName) {
        this.display_name = displayName;
    }

    /**
     * @return the _slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     * @param slug the _slug to set
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * @return the _description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the _description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the _email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the _email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the _fbLink
     */
    public String getFbLink() {
        return fb_profile_link;
    }

    /**
     * @param fbLink the _fbLink to set
     */
    public void setFbLink(String fbLink) {
        this.fb_profile_link = fbLink;
    }

    /**
     * @return the _fbUserName
     */
    public String getFbUserName() {
        return fb_username;
    }

    /**
     * @param fbUserName the _fbUserName to set
     */
    public void setFbUserName(String fbUserName) {
        this.fb_username = fbUserName;
    }

    /**
     * @return the _twScreenName
     */
    public String getTwScreenName() {
        return twitter_screen_name;
    }

    /**
     * @param twScreenName the _twScreenName to set
     */
    public void setTwScreenName(String twScreenName) {
        this.twitter_screen_name = twScreenName;
    }

    /**
     * @return the _countVisits
     */
    public Integer getCountVisits() {
        return visits;
    }

    /**
     * @param countVisits the _countVisits to set
     */
    public void setCountVisits(Integer countVisits) {
        this.visits = countVisits;
    }

    /**
     * @return the _karma
     */
    public Integer getKarma() {
        return karma;
    }

    /**
     * @param karma the _karma to set
     */
    public void setKarma(Integer karma) {
        this.karma = karma;
    }

    /**
     * @return the _countShared
     */
    public Integer getCountShared() {
        return shared;
    }

    /**
     * @param countShared the _countShared to set
     */
    public void setCountShared(Integer countShared) {
        this.shared = countShared;
    }

    /**
     * @return the _storageQuota
     */
    public Long getStorageQuota() {
        return storage_quota;
    }

    /**
     * @param storageQuota the _storageQuota to set
     */
    public void setStorageQuota(Long storageQuota) {
        this.storage_quota = storageQuota;
    }

    /**
     * @return the _storageUsed
     */
    public Long getStorageUsed() {
        return storage_used;
    }

    /**
     * @param storageUsed the _storageUsed to set
     */
    public void setStorageUsed(Long storageUsed) {
        this.storage_used = storageUsed;
    }

    /**
     * @return the _foldersLink
     */
    public String getFoldersLink() {
        return folders;
    }

    /**
     * @param foldersLink the _foldersLink to set
     */
    public void setFoldersLink(String foldersLink) {
        this.folders = foldersLink;
    }

    /**
     * @return the _url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the _url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the _avatarUrl
     */
    public String getAvatarUrl() {
        return avatar;
    }

    /**
     * @param avatarUrl the _avatarUrl to set
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatar = avatarUrl;
    }

    /**
     * @return the followers_slug_list
     */
    public String[] getFollowersSlugList() {
        return followers_slug_list;
    }

    /**
     * @param followers_slug_list the followers_slug_list to set
     */
    public void setFollowersSlugList(String[] followers_slug_list) {
        this.followers_slug_list = followers_slug_list;
    }

    /**
     * @return the following_slug_list
     */
    public String[] getFollowingSlugList() {
        return following_slug_list;
    }

    /**
     * @param following_slug_list the following_slug_list to set
     */
    public void setFollowingSlugList(String[] following_slug_list) {
        this.following_slug_list = following_slug_list;
    }

    /**
     * @return the followers
     */
    public String getFollowers() {
        return followers;
    }

    /**
     * @param followers the followers to set
     */
    public void setFollowers(String followers) {
        this.followers = followers;
    }

    /**
     * @return the following
     */
    public String getFollowing() {
        return following;
    }

    /**
     * @param following the following to set
     */
    public void setFollowing(String following) {
        this.following = following;
    }
}
