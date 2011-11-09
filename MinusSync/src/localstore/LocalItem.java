/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package localstore;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import store.IFolder;

/**
 *
 * @author burun
 */
public class LocalItem {
    private File _file;
    private IFolder _parent;
    private String _id;
    private String _path;
    private Date _creationDate;
    
    public LocalItem(File file, String id, IFolder parent, String path, 
                     Date creationDate) {
        _file = file;
        _id = id;
        _parent = parent;
        _path = path;
        _creationDate = creationDate;
    }
    
    public LocalItem(File file, String id, IFolder parent, String path) {
        this(file, id, parent, path, Calendar.getInstance().getTime());
    }
    
    public void setFile(File file) {
        _file = file;
    }
    
    public File getFile() {
        return _file;
    }
    
    public IFolder getParent() {
        return _parent;
    }
    
    public String getId() {
        return _id;
    }

    public String getName() {
        return _file.getName();
    }

    public String getPath() {
        return _path;
    }

    public Date getCreationDate() {
        return _creationDate;
    }

    public long getCreationAgo() {
        Calendar creationTime = Calendar.getInstance();
        creationTime.setTime(_creationDate);
        
        return System.currentTimeMillis() - creationTime.getTimeInMillis();
    }

    /**
     * @param folder is parent of item
     */ 
    public void setParent(IFolder folder) {
        _parent = folder;
    }

    /**
     * @param id the _id to set
     */
    public void setId(String id) {
        this._id = id;
    }

    /**
     * @param path the _path to set
     */
    public void setPath(String path) {
        this._path = path;
    }

    /**
     * @param creationDate the _creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this._creationDate = creationDate;
    }
}
