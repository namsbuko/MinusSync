/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package minusstore;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import minusapi.MinusApi;
import minusapi.MinusFolder;
import store.IFile;
import store.IFolder;
import store.IStore;

/**
 *
 * @author Admin
 */
public class MinusStore implements IStore{
     private ArrayList<IFile> listOfFile = new ArrayList<IFile> ();
     private ArrayList<IFolder> listOfFolder = new ArrayList<IFolder>();
     minusapi.MinusApi apiObject;
     /*
      * сценраий инициализации
      * 1) берем БД
      *
      *
        3) построекние мапы на основе бд
      *     3.1 все папки без сваязей
      *     3.2 проставляем связи
      * 4) получаем все файлы нашей папки из веба
      * 5) 5.1 берем каждый minusFile из списка
      *     5.2 созадем minus storefile
      *     5.3 по Id находим parent folder
      *     5.4 делаем addFile (по ссылке) в IFolder
      *
      *
      */

     /**
     * @param name folder's name
     * @return if folder is added true, otherwise false
     */
    public boolean addFolder(IFolder name)
    {
      listOfFolder.add(name);
      return null!=apiObject.addFolder(name.getName(), Boolean.TRUE);
      //return true;
    }

    /**
     *
     * @param folder = null - root folder
     * @return files collection of folder
     */
    public Collection<IFolder> getFolders(IFolder folder)
    {
        
        Collection<MinusFolder> minusFolderList = apiObject.getFolders();
        listOfFolder = minusFolderList;
    }

    /**
     * @param folder for deleting
     * @return if folder is deleted true, otherwise false
     */
    public  boolean deleteFolder(IFolder folder)
    {
        
         apiObject.deleteFolder(null);
    }

    /**
     * @param file for deleting
     * @return if file is deleted true, otherwise false
     */
    public boolean deleteFile(IFile file)
    {

    }

    /**
     * @param file
     * @return outputstream of file
     */
    public OutputStream getFileStream(IFile file)
    {

    }

    /**
     * @param if folder = null - root folder
     * @return files of folder
     */
    public Collection<IFile> getFiles(IFolder folder)
    {

    }

    /**
     * @param file
     * @param input stream of file
     * @return true if file is added, otherwise false
     */
    public boolean addFile(IFile file, InputStream in)
    {

    }
}


