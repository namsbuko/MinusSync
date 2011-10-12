/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minusapi;

import java.util.Date;

/**
 *
 * @author burun
 */
public class MinusFolder {
    private String id; 
    
    private String thumbnailUrl;
    private String name;

    private boolean isPublic;
    private int countView;

    private String creatorLink;

    private int countFiles;
    private String filesLink;
    
    private Date lastUploaded;
    
    private String url;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the thumbnailUrl
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * @param thumbnailUrl the thumbnailUrl to set
     */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the isPublic
     */
    public boolean isIsPublic() {
        return isPublic;
    }

    /**
     * @param isPublic the isPublic to set
     */
    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * @return the countView
     */
    public int getCountView() {
        return countView;
    }

    /**
     * @param countView the countView to set
     */
    public void setCountView(int countView) {
        this.countView = countView;
    }

    /**
     * @return the creatorLink
     */
    public String getCreatorLink() {
        return creatorLink;
    }

    /**
     * @param creatorLink the creatorLink to set
     */
    public void setCreatorLink(String creatorLink) {
        this.creatorLink = creatorLink;
    }

    /**
     * @return the countFiles
     */
    public int getCountFiles() {
        return countFiles;
    }

    /**
     * @param countFiles the countFiles to set
     */
    public void setCountFiles(int countFiles) {
        this.countFiles = countFiles;
    }

    /**
     * @return the filesLink
     */
    public String getFilesLink() {
        return filesLink;
    }

    /**
     * @param filesLink the filesLink to set
     */
    public void setFilesLink(String filesLink) {
        this.filesLink = filesLink;
    }

    /**
     * @return the lastUploaded
     */
    public Date getLastUploaded() {
        return lastUploaded;
    }

    /**
     * @param lastUploaded the lastUploaded to set
     */
    public void setLastUploaded(Date lastUploaded) {
        this.lastUploaded = lastUploaded;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
