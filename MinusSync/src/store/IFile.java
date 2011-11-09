/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

import java.util.Date;

/**
 *
 * @author burun
 */
public interface IFile {

    /**
     * @return parent of file
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
     * @return mime type of file
     */
    String getType();

    /**
     * @return size of file
     */
    int getSize();
}
