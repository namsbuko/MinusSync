/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

/**
 *
 * @author burun
 */
public interface IFile {

    /**
     * @return the folderLink
     */
    IFolder getParentFolder();

    /**
     * @return the id
     */
    String getId();

    /**
     * @return the mimeType
     */
    String getType();

    /**
     * @return the name
     */
    String getName();

    /**
     * @return the originalLink
     */
    String getPath();

    /**
     * @return the size
     */
    int getSize();

    /**
     * @return the uploaded
     */
    String getUploaded();

    /**
     * @return the uploaded_ago
     */
    int getUploadedAgo();

    /**
     * @return the folderLink
     */
    void setParentFolder(IFolder folder);

    /**
     * @param id the id to set
     */
    void setId(String id);

    /**
     * @param mimeType the mimeType to set
     */
    void setType(String mimeType);

    /**
     * @param name the name to set
     */
    void setName(String name);

    /**
     * @param originalLink the originalLink to set
     */
    void setPath(String originalLink);

    /**
     * @param size the size to set
     */
    void setSize(int size);

    /**
     * @param uploaded the uploaded to set
     */
    void setUploaded(String uploaded);

    /**
     * @param uploadedAgo the uploaded_ago to set
     */
    void setUploadedAgo(int uploadedAgo);

    String toString();    
}
