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
    
    private int size;
    private String mimeType;
    
    private String folderLink;

    private String url;

    private Date uploaded;

    private String originalLink;
    private String tumbnailLink;

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
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @param mimeType the mimeType to set
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * @return the folderLink
     */
    public String getFolderLink() {
        return folderLink;
    }

    /**
     * @param folderLink the folderLink to set
     */
    public void setFolderLink(String folderLink) {
        this.folderLink = folderLink;
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
    public Date getUploaded() {
        return uploaded;
    }

    /**
     * @param uploaded the uploaded to set
     */
    public void setUploaded(Date uploaded) {
        this.uploaded = uploaded;
    }

    /**
     * @return the originalLink
     */
    public String getOriginalLink() {
        return originalLink;
    }

    /**
     * @param originalLink the originalLink to set
     */
    public void setOriginalLink(String originalLink) {
        this.originalLink = originalLink;
    }

    /**
     * @return the link
     */
    public String getTumbnailLink() {
        return tumbnailLink;
    }

    /**
     * @param link the link to set
     */
    public void setTumbnailLink(String link) {
        this.tumbnailLink = link;
    }
}
