/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

/**
 *
 * @author burun
 */
public interface IFolder {

    /**
     * @return the countFiles
     */
    int getCountFiles();

    /**
     * @return the id
     */
    String getId();

    /**
     * @return the last_updated
     */
    String getLastUpdated();

    /**
     * @return the last_updated_ago
     */
    int getLastUpdatedAgo();

    /**
     * @return the lastUploaded
     */
    String getLastUploaded();

    /**
     * @return the name
     */
    String getName();
    
    IFolder getParentFolder();
    
    void setParentFolder(IFolder folder);

    /**
     * @param countFiles the countFiles to set
     */
    void setCountFiles(int countFiles);

    /**
     * @param id the id to set
     */
    void setId(String id);

    /**
     * @param lastUpdated the last_updated to set
     */
    void setLastUpdated(String lastUpdated);

    /**
     * @param lastUpdatedAgo the last_updated_ago to set
     */
    void setLastUpdatedAgo(int lastUpdatedAgo);

    /**
     * @param lastUploaded the lastUploaded to set
     */
    void setLastUploaded(String lastUploaded);

    /**
     * @param name the name to set
     */
    void setName(String name);

    /**
     * @param url the url to set
     */
    void setUrl(String url);

    String toString();
    
}
