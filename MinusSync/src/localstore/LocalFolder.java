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
public class LocalFolder extends LocalItem implements IFolder{
 
    public LocalFolder(File folder, String id, IFolder parent, String path, 
                       Date creationDate) {
        super(folder, id, parent, path, creationDate);
    }
    
    public LocalFolder(File folder, String id, IFolder parent, String path) {
        this(folder, id, parent, path, Calendar.getInstance().getTime());
    }    
    
    @Override
    public int getCountFiles() {
        return getFile().listFiles().length;
    }

    @Override
    public Date getLastChangeDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getFile().lastModified());
        
        return cal.getTime();
    }

    @Override
    public long getLastChangeAgo() {
        Calendar lastChangeTime = Calendar.getInstance();
        lastChangeTime.setTime(getLastChangeDate());
        return System.currentTimeMillis() - lastChangeTime.getTimeInMillis();
    }
}
