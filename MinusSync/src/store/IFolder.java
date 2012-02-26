/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

import java.util.Collection;
import java.util.Date;

/**
 *
 * @author burun
 */
public interface IFolder {

    Collection<IFile> getFiles ();

    Collection<IFolder> getFolders ();

    /**
     * @return parent of folder
     */

    IFolder getParent();
    
    /**
     * @return id
     */
    String getId();
    
    /**
     * @return name
     */
    String getName();

    /**
     * @return path ralative to root
     */
    String getPath();
    
    /**
     * @return date of creation
     */
    Date getCreationDate();

    /**
     * @return time in millis since creation
     */
    long getCreationAgo();
    
    /**
     * @return the countFiles
     */
    int getCountFiles();

    /**
     * @return date of last change
     */
    Date getLastChangeDate();
    
    /**
     * @return time in ms since last change
     */
    long getLastChangeAgo();
}
