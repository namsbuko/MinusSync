/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

/**
 *
 * @author burun
 */
public interface IStore {

    /**
     * @param name folder's name
     * @param isPublic public folder's property
     * @return MinusFolder object - folder has been added, null - else
     */
    IFolder addFolder(String name, IFolder parent);

    /**
     * 
     * @param folder = null - root folder
     * @return 
     */
    Collection<IFolder> getFolders(IFolder folder);
    
    /**
     * @param folderId folder's id provided by minus
     * @return always true
     */
    boolean deleteFolder(IFolder folder);

    /**
     * @param fileId file's id by minus
     * @return always true
     */
    boolean deleteFile(IFile file);

    OutputStream getFileStream(IFile file);
    
    /**
     * @param if folder = null - root folder
     * @return folder's files
     */
    Collection<IFile> getFiles(IFolder folder);

    boolean addFile(IFile file, InputStream in, IFolder folder);      
}
