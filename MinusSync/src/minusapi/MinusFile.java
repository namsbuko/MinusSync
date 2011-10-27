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
public class MinusFile {
    private String id;
    
    private String name;
    private String title;
    private String caption;
    
    private int width;
    private int height;
    
    private int filesize;
    private String mimetype;
    
    private String folder;

    private String url;

    private String uploaded;

    private String url_rawfile;
   
    private String url_thumbnail;
    private String url_thumbnail_medium;
    
    private int uploaded_ago;
    
    @Override
    public String toString()
    {
        return "Id: " + id + "; " +
               "Name: " + name + "; " +
               "Title: " + title + "; " +
               "Caption: " + caption + "; " +
               "Width: " + width + "; " +
               "Height: " + height + "; " +
               "File's size: " + filesize + "; " +
               "Mime type: " + mimetype + "; " +
               "Folder: " + folder + "; " + 
               "Url: " + url + "; " +
               "Uploaded: " + uploaded + "; " +
               "Uploaded ago: " + uploaded_ago + "; " +
               "Url of rawfile: " + url_rawfile + "; " + 
               "Url of thumbnail medium: " + url_thumbnail_medium + "; " +
               "Url of thumbnail: " + url_thumbnail + ".";
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     * @param caption the caption to set
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return filesize;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.filesize = size;
    }

    /**
     * @return the mimeType
     */
    public String getMimeType() {
        return mimetype;
    }

    /**
     * @param mimeType the mimeType to set
     */
    public void setMimeType(String mimeType) {
        this.mimetype = mimeType;
    }

    /**
     * @return the folderLink
     */
    public String getFolderLink() {
        return folder;
    }

    /**
     * @param folderLink the folderLink to set
     */
    public void setFolderLink(String folderLink) {
        this.folder = folderLink;
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
     * @return the uploaded
     */
    public String getUploaded() {
        return uploaded;
    }

    /**
     * @param uploaded the uploaded to set
     */
    public void setUploaded(String uploaded) {
        this.uploaded = uploaded;
    }

    /**
     * @return the originalLink
     */
    public String getOriginalLink() {
        return url_rawfile;
    }

    /**
     * @param originalLink the originalLink to set
     */
    public void setOriginalLink(String originalLink) {
        this.url_rawfile = originalLink;
    }

    /**
     * @return the link
     */
    public String getTumbnailLink() {
        return url_thumbnail;
    }

    /**
     * @param link the link to set
     */
    public void setTumbnailLink(String link) {
        this.url_thumbnail = link;
    }

    /**
     * @return the url_thumbnail_medium
     */
    public String getUrlThumbnailMedium() {
        return url_thumbnail_medium;
    }

    /**
     * @param urlThumbnailMedium the url_thumbnail_medium to set
     */
    public void setUrlThumbnailMedium(String urlThumbnailMedium) {
        this.url_thumbnail_medium = urlThumbnailMedium;
    }

    /**
     * @return the uploaded_ago
     */
    public int getUploadedAgo() {
        return uploaded_ago;
    }

    /**
     * @param uploadedAgo the uploaded_ago to set
     */
    public void setUploadedAgo(int uploadedAgo) {
        this.uploaded_ago = uploadedAgo;
    }
}
