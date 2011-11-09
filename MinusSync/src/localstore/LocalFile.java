package localstore;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import store.IFile;
import store.IFolder;

/**
 *
 * @author burun
 */
public class LocalFile extends LocalItem implements IFile {

    private String _type;
    
    public LocalFile(File file, String id, IFolder parent, String path, 
                     String type, Date creationDate) {
        super(file, id, parent, path, creationDate);
        _type = type;
    }
    
    public LocalFile(File file, String id, IFolder parent, String path, 
                     String type) {
        this(file, id, parent, path, type, Calendar.getInstance().getTime());
    }
    
    @Override
    public String getType() {
        return _type;
    }

    @Override
    public int getSize() {
        return (int)getFile().getTotalSpace();
    }
}
