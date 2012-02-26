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
     * @return if folder is added true, otherwise false
     */
    boolean addFolder(IFolder name);

    /**
     * 
     * @param folder = null - root folder
     * @return files collection of folder 
     */
    Collection<IFolder> getFolders(IFolder folder);
    
    /**
     * @param folder for deleting
     * @return if folder is deleted true, otherwise false
     */
    boolean deleteFolder(IFolder folder);

    /**
     * @param file for deleting
     * @return if file is deleted true, otherwise false
     */
    boolean deleteFile(IFile file);

    /**
     * @param file
     * @return outputstream of file
     */
    OutputStream getFileStream(IFile file);
    
    /**
     * @param if folder = null - root folder
     * @return files of folder
     */
    Collection<IFile> getFiles(IFolder folder);

    /**
     * @param file
     * @param input stream of file
     * @return true if file is added, otherwise false
     */
    boolean addFile(IFile file, InputStream in);

    void refresh ();
}
