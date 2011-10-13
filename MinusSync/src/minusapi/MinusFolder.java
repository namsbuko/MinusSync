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
    
    private String thumbnail_url;
    private String name;

    private boolean is_public;
    private int view_count;

    private String creator;

    private int file_count;
    private String files;
    
    private String date_last_updated;
    private String last_updated;
    private int last_updated_ago;
    
    private String item_ordering;
    
    private String url;
    
    @Override
    public String toString()
    {
        return "Id: " + id + "; " +
               "Thumbnail url: " + thumbnail_url + "; " +
               "Name: " + name + "; " +
               "Is public: " + is_public + "; " +
               "View count: " + view_count + "; " +
               "Folder's creator: " + creator + "; " +
               "Count of files: " + file_count + "; " +
               "Link to files: " + files + "; " +
               "Date last updated: " + date_last_updated + "; " + 
               "Last updated: " + last_updated + "; " +
               "Last updated ago: " + last_updated_ago + "; " + 
               "Item ordering: " + item_ordering + "; " +
               "Url: " + url + ".";
    }

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
        return thumbnail_url;
    }

    /**
     * @param thumbnailUrl the thumbnailUrl to set
     */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnail_url = thumbnailUrl;
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
        return is_public;
    }

    /**
     * @param isPublic the isPublic to set
     */
    public void setIsPublic(boolean isPublic) {
        this.is_public = isPublic;
    }

    /**
     * @return the countView
     */
    public int getCountView() {
        return view_count;
    }

    /**
     * @param countView the countView to set
     */
    public void setCountView(int countView) {
        this.view_count = countView;
    }

    /**
     * @return the creatorLink
     */
    public String getCreatorLink() {
        return creator;
    }

    /**
     * @param creatorLink the creatorLink to set
     */
    public void setCreatorLink(String creatorLink) {
        this.creator = creatorLink;
    }

    /**
     * @return the countFiles
     */
    public int getCountFiles() {
        return file_count;
    }

    /**
     * @param countFiles the countFiles to set
     */
    public void setCountFiles(int countFiles) {
        this.file_count = countFiles;
    }

    /**
     * @return the filesLink
     */
    public String getFilesLink() {
        return files;
    }

    /**
     * @param filesLink the filesLink to set
     */
    public void setFilesLink(String filesLink) {
        this.files = filesLink;
    }

    /**
     * @return the lastUploaded
     */
    public String getLastUploaded() {
        return date_last_updated;
    }

    /**
     * @param lastUploaded the lastUploaded to set
     */
    public void setLastUploaded(String lastUploaded) {
        this.date_last_updated = lastUploaded;
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

    /**
     * @return the last_updated
     */
    public String getLastUpdated() {
        return last_updated;
    }

    /**
     * @param last_updated the last_updated to set
     */
    public void setLastUpdated(String lastUpdated) {
        this.last_updated = lastUpdated;
    }

    /**
     * @return the last_updated_ago
     */
    public int getLastUpdatedAgo() {
        return last_updated_ago;
    }

    /**
     * @param last_updated_ago the last_updated_ago to set
     */
    public void setLastUpdatedAgo(int lastUpdatedAgo) {
        this.last_updated_ago = lastUpdatedAgo;
    }

    /**
     * @return the item_ordering
     */
    public String getItemOrdering() {
        return item_ordering;
    }

    /**
     * @param item_ordering the item_ordering to set
     */
    public void setItemOrdering(String itemOrdering) {
        this.item_ordering = itemOrdering;
    }
}
